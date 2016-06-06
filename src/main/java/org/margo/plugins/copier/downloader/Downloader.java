package org.margo.plugins.copier.downloader;

import org.margo.plugins.copier.exception.DownloaderException;

import java.net.URI;

public interface Downloader {
    byte[] download(URI uri) throws DownloaderException;
}
