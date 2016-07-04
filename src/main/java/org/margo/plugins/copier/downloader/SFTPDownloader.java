package org.margo.plugins.copier.downloader;

import com.jcraft.jsch.*;
import org.apache.hadoop.io.IOUtils;
import org.margo.plugins.copier.URICommons;
import org.margo.plugins.copier.annotation.Reader;
import org.margo.plugins.copier.exception.CopierException;
import org.margo.plugins.copier.exception.DownloaderException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Vector;

@Reader("sftp")
public class SFTPDownloader implements Downloader {

    private JSch jsch = new JSch();

    @Override
    public void download(URI uri, DownloaderHandler downloaderHandler) throws DownloaderException {
        Session session = null;
        ChannelSftp sftp = null;
        try {
            session = jsch.getSession(URICommons.getUsername(uri), uri.getHost(), uri.getPort());
            session.setPassword(URICommons.getPassword(uri));
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            sftp = ((ChannelSftp) session.openChannel("sftp"));
            sftp.connect();

            SftpATTRS stat;
            try {
                stat = sftp.stat(uri.getPath());
            } catch (SftpException e) {
                throw new IOException("There is no such file/directory on the specified path " + uri.getPath()
                        + ". Please, make sure that you have specified the correct path to the file(s) you want to be copied.");
            }

            if (stat.isDir()) {
                downloadDirectory(sftp, uri, downloaderHandler);
            } else {
                downloadFile(sftp, uri, downloaderHandler);
            }

        } catch (JSchException | SftpException | IOException | CopierException | URISyntaxException e ) {
            throw new DownloaderException(e);
        } finally {
            if (session != null) {
                session.disconnect();
            }
            if (sftp != null) {
                sftp.disconnect();
            }
        }
    }

    protected void downloadDirectory(ChannelSftp sftp, URI uri, DownloaderHandler downloaderHandler) throws SftpException, IOException, CopierException, URISyntaxException {
        Vector<ChannelSftp.LsEntry> files = sftp.ls(uri.getPath());
        for (ChannelSftp.LsEntry file : files) {
            if (file.getFilename().equals(".") || file.getFilename().equals("..")) continue;
            URI currURI = URI.create(uri + "/" + file.getFilename()).normalize();
            if (file.getAttrs().isDir()) {
                downloadDirectory(sftp, currURI, downloaderHandler);
            } else {
                downloadFile(sftp, currURI, downloaderHandler);
            }
        }
    }

    private void downloadFile(ChannelSftp sftp, URI uri, DownloaderHandler downloaderHandler) throws SftpException, IOException, CopierException {
        InputStream in = sftp.get(uri.getPath());
        ByteArrayOutputStream  out = new ByteArrayOutputStream();
        IOUtils.copyBytes(in, out, 2048);
        downloaderHandler.handle(out.toByteArray(), uri);
    }
}
