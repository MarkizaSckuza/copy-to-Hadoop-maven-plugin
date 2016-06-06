package org.margo.plugins.copier.downloader;

import com.jcraft.jsch.*;
import org.apache.hadoop.io.IOUtils;
import org.margo.plugins.copier.URICommons;
import org.margo.plugins.copier.exception.DownloaderException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class SFTPDownloader implements Downloader {

    private JSch jsch = new JSch();

    @Override
    public byte[] download(URI uri) throws DownloaderException {
        Session session = null;
        ChannelSftp sftp = null;
        try {
            session = jsch.getSession(URICommons.getUsername(uri), uri.getHost(), uri.getPort());
            session.setPassword(URICommons.getPassword(uri));
            sftp = ((ChannelSftp) session.openChannel("sftp"));

            InputStream in = sftp.get(uri.getPath());
            ByteArrayOutputStream  out = new ByteArrayOutputStream();
            IOUtils.copyBytes(in, out, 2048);
            return out.toByteArray();
        } catch (JSchException | SftpException | IOException e ) {
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
}
