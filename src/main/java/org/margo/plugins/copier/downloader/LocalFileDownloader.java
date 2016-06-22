package org.margo.plugins.copier.downloader;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.margo.plugins.copier.annotation.Reader;
import org.margo.plugins.copier.exception.DownloaderException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Reader("file")
public class LocalFileDownloader implements Downloader {
    @Override
    public byte[] download(URI uri) throws DownloaderException {
        try {
            Path path = Paths.get(FilenameUtils.getPath(uri.getPath()));

            if (!Files.exists(path))
                throw new IOException("There is no specified path " + path);

            InputStream input = new FileInputStream(uri.getPath());
            return IOUtils.toByteArray(input);
        } catch (IOException e) {
            throw new DownloaderException(e);
        }
    }
}
