package org.margo.plugins.copier;

import org.margo.plugins.copier.api.RemoteFileCopier;
import org.margo.plugins.copier.exception.CopierException;
import org.margo.plugins.copier.impl.*;

import java.io.File;
import java.net.URI;

public class RemoteFileCopierProcessor implements RemoteFileCopier {

    private RemoteFileCopierProcessor() {
    }

    public static RemoteFileCopierProcessor getInstance() {
        return CopierProcessorInstanceHolder.INSTANCE;
    }

    @Override
    public void copy(File source, URI targetPath) throws CopierException {
        RemoteFileCopier remoteFileCopier;
        String scheme = targetPath.getScheme();
        switch (scheme) {
            case "hdfs":
                remoteFileCopier = new HDFSFileCopier();
                break;
            case "http":
            case "https":
                remoteFileCopier = new HTTPFileCopier();
                break;
            case "ssh":
                remoteFileCopier = new SSHFileCopier();
                break;
            case "sftp":
                remoteFileCopier = new SFTPFileCopier();
                break;
            case "scp":
                remoteFileCopier = new SCPFileCopier();
                break;
            case "telnet":
                remoteFileCopier = new TelnetFileCopier();
                break;
            case "ftp":
                remoteFileCopier = new FTPFileCopier();
                break;
            default:
                throw new IllegalArgumentException(scheme + "is unknown scheme");
        }

        remoteFileCopier.copy(source, targetPath);
    }

    private static class CopierProcessorInstanceHolder {
        private static final RemoteFileCopierProcessor INSTANCE = new RemoteFileCopierProcessor();
    }
}
