package org.margo.plugins.copier.uploader;

import com.jcraft.jsch.*;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.margo.plugins.copier.URICommons;
import org.margo.plugins.copier.annotation.Writer;

import java.net.URI;

@Writer("sftp")
public class SFTPUploader implements Uploader {

    private JSch jsch = new JSch();

    private SftpProgressMonitor monitor;

    public SFTPUploader() {
    }

    public SFTPUploader(SftpProgressMonitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public boolean upload(URI uri, byte[] data) {
        Session session = null;
        ChannelSftp sftp = null;
        try {
            session = jsch.getSession(URICommons.getUsername(uri), uri.getHost(), uri.getPort());
            session.setPassword(URICommons.getPassword(uri));
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            sftp = ((ChannelSftp) session.openChannel("sftp"));
            sftp.connect();

            String path = URICommons.getPath(uri);

            try {
                sftp.stat(path);
            } catch (SftpException e) {
                sftp.mkdir(path);
            }

            sftp.cd(path);
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

        return true;
    }
}
