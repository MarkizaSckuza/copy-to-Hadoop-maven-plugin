package org.margo.plugins.copier.uploader;

import com.jcraft.jsch.*;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.io.FilenameUtils;
import org.margo.plugins.copier.URICommons;

import java.net.URI;

public class SFTPUploader implements Uploader {

    private JSch jsch = new JSch();

    private SftpProgressMonitor monitor;

    public SFTPUploader() {
    }

    public SFTPUploader(SftpProgressMonitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public byte[] upload(URI uri, byte[] data) {
        Session session = null;
        ChannelSftp sftp = null;
        try {
            session = jsch.getSession(URICommons.getUsername(uri), uri.getHost(), uri.getPort());
            session.setPassword(URICommons.getPassword(uri));
            sftp = ((ChannelSftp) session.openChannel("sftp"));
            sftp.cd(URICommons.getPath(uri));
            sftp.put(new ByteInputStream(data, data.length), uri.getPath(), monitor);
        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.disconnect();
            }
            if (sftp != null) {
                sftp.disconnect();
            }
        }

        return new byte[0];
    }
}
