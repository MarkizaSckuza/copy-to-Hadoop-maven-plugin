package org.margo.plugins.copier.downloader;

import org.apache.commons.io.IOUtils;
import org.margo.plugins.copier.exception.DownloaderException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class LocalFileDownloader implements Downloader {
    @Override
    public byte[] download(URI uri) throws DownloaderException {
        try (InputStream input = new FileInputStream(uri.getPath())){
            return IOUtils.toByteArray(input);
        } catch (IOException e) {
            throw new DownloaderException(e);
        }
    }
}
