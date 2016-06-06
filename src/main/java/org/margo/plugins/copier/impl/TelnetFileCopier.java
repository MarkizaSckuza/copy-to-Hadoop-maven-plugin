package org.margo.plugins.copier.impl;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.margo.plugins.copier.api.JCraftFileCopier;

import java.io.File;
import java.net.URI;

public class TelnetFileCopier extends JCraftFileCopier {
    @Override
    protected void process(Session session, Channel channel, File source, URI target) throws Exception {

    }

    @Override
    protected Channel getChannel(Session session) throws JSchException {
        return null;
    }
}
