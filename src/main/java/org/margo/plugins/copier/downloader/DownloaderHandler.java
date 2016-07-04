package org.margo.plugins.copier.downloader;

import org.margo.plugins.copier.exception.CopierException;

import java.net.URI;

public interface DownloaderHandler {
    void handle(byte[] data, URI dataURI) throws CopierException;
}
