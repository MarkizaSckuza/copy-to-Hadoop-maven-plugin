package org.margo.plugins.copier.downloader;

import org.margo.plugins.copier.exception.DownloaderException;

import java.net.URI;

public class SFTPDownloader implements Downloader {
    @Override
    public byte[] download(URI uri) throws DownloaderException {
        return new byte[0];
    }
}
