package org.margo.plugins;

import com.jcraft.jsch.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class JarCopier {

    public void copy(String hostname, String username, String password, File copyFrom, String destDir, SftpProgressMonitor monitor) throws JSchException, SftpException, FileNotFoundException {
        JSch jsch = new JSch();
        Session session = null;
        ChannelSftp channel = null;
        try {
            session = jsch.getSession(username, hostname, 22);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect();

            channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();
            channel.cd(destDir);
            channel.put(new FileInputStream(copyFrom), copyFrom.getName(), monitor);
        } finally {
            if (channel != null) {
                channel.exit();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }
}
