package org.margo.plugins.copier;

import org.margo.plugins.copier.exception.CopierException;

import java.net.URI;

public interface RemoteFileCopier {

    //target example: sftp://user:password@host:port/absolute-path
    void copy(URI source, URI target) throws CopierException;
}
