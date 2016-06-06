package org.margo.plugins.copier.api;

import org.margo.plugins.copier.exception.CopierException;

import java.io.File;
import java.net.URI;

public interface RemoteFileCopier {

    //targetPath example: sftp://user:password@host:port/absolute-path
    void copy(File source, URI targetPath) throws CopierException;
}
