package org.margo.plugins.copier.api;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.File;
import java.net.URI;

public abstract class JCraftFileCopier extends AbstractRemoteFileCopier {

    protected JSch jsch;

    public JCraftFileCopier() {
        jsch = new JSch();
    }

    @Override
    protected void doCopy(File source, URI targetPath) throws Exception {
        Session session = null;
        Channel channel = null;
        try {
            session = getSession();
            session.connect();

            channel = getChannel(session);
            channel.connect();
            process(session, channel, source, targetPath);
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }


//        JSch jSch = new JSch();
//        Session session = jSch.getSession(config.getUsername(), config.getHost(), config.getPort());
//        session.setPassword(config.getPassword());
//        session.setConfig("StrictHostKeyChecking", "no");
//        session.connect();
//
//        boolean ptimestamp = true;
//
//        // exec 'scp -t rfile' remotely
//        String command = "scp " + (ptimestamp ? "-p" : "") + " -t " + targetPath.getPath();
//        ChannelExec channel = (ChannelExec) session.getChannel("exec");
//        // get I/O streams for remote scp
//        OutputStream out = channel.getOutputStream();
//        InputStream in = channel.getInputStream();
//
//        channel.connect();
//
//        if (ptimestamp) {
//            command = "T " + (_lfile.lastModified() / 1000) + " 0";
//            // The access time should be sent here,
//            // but it is not accessible with JavaAPI ;-<
//            command += (" " + (_lfile.lastModified() / 1000) + " 0\n");
//            out.write(command.getBytes());
//            out.flush();
//            if (checkAck(in) != 0) {
//                System.exit(0);
//            }
//        }
//
//        // send "C0644 filesize filename", where filename should not include '/'
//        long filesize = _lfile.length();
//        command = "C0644 " + filesize + " ";
//        if (lfile.lastIndexOf('/') > 0) {
//            command += lfile.substring(lfile.lastIndexOf('/') + 1);
//        } else {
//            command += lfile;
//        }
//        command += "\n";
//        out.write(command.getBytes());
//        out.flush();
//        if (checkAck(in) != 0) {
//            System.exit(0);
//        }
//
//        // send a content of lfile
//        fis = new FileInputStream(lfile);
//        byte[] buf = new byte[1024];
//        while (true) {
//            int len = fis.read(buf, 0, buf.length);
//            if (len <= 0) break;
//            out.write(buf, 0, len); //out.flush();
//        }
//        fis.close();
//        fis = null;
//        // send '\0'
//        buf[0] = 0;
//        out.write(buf, 0, 1);
//        out.flush();
//        if (checkAck(in) != 0) {
//            System.exit(0);
//        }
//        out.close();
//
//        channel.disconnect();
//        session.disconnect();


    }

    protected abstract void process(Session session, Channel channel, File source, URI target) throws Exception;

    protected Session getSession() throws JSchException {
        Session session;
        session = jsch.getSession(getConfig().getUsername(), getConfig().getHost(), getConfig().getPort());
        session.setConfig("StrictHostKeyChecking", "no");
        session.setPassword(getConfig().getPassword());
        return session;
    }

    protected abstract Channel getChannel(Session session) throws JSchException;
}
