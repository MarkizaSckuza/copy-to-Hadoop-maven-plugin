package org.margo.plugins.copier.impl;

import com.jcraft.jsch.*;
import org.margo.plugins.copier.api.JCraftFileCopier;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;

public class SFTPFileCopier extends JCraftFileCopier {

    private SftpProgressMonitor sftpProgressMonitor;

    public SFTPFileCopier() {
    }

    public SFTPFileCopier(SftpProgressMonitor sftpProgressMonitor) {
        this.sftpProgressMonitor = sftpProgressMonitor;
    }

    @Override
    protected void process(Session session, Channel channel, File source, URI target) throws Exception {
        ChannelSftp sftp = (ChannelSftp) channel;
        sftp.cd("");
        sftp.put(new FileInputStream(source), source.getName(), sftpProgressMonitor);
    }

    @Override
    protected Channel getChannel(Session session) throws JSchException {
        return session.openChannel("sftp");
    }

    public SftpProgressMonitor getSftpProgressMonitor() {
        return sftpProgressMonitor;
    }

    public SFTPFileCopier setSftpProgressMonitor(SftpProgressMonitor sftpProgressMonitor) {
        this.sftpProgressMonitor = sftpProgressMonitor;
        return this;
    }
}
