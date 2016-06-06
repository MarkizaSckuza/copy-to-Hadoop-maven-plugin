package org.margo.plugins.copier.impl;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.margo.plugins.copier.api.JCraftFileCopier;

import java.io.File;
import java.net.URI;

public class SCPFileCopier extends JCraftFileCopier {
    @Override
    protected void process(Session session, Channel channel, File source, URI target) throws Exception {
        ChannelExec channelExec = (ChannelExec) channel;
        channelExec.start();
    }

    @Override
    protected Channel getChannel(Session session) throws JSchException {
        ChannelExec exec = (ChannelExec) session.openChannel("exec");
        exec.setCommand("");
        return exec;
    }
}
