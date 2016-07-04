package org.margo.plugins.copier.downloader;

import org.apache.commons.io.FileUtils;
import org.margo.plugins.copier.annotation.Reader;
import org.margo.plugins.copier.exception.CopierException;
import org.margo.plugins.copier.exception.DownloaderException;

import java.io.File;
import java.io.IOException;
import java.net.URI;

@Reader("file")
public class LocalFileDownloader implements Downloader {
    @Override
    public void download(URI uri, DownloaderHandler downloaderHandler) throws DownloaderException {
        try {
            File file = new File(uri);

            if (!file.exists())
                throw new IOException("There is no such file/directory on the specified path " + file.getPath()
                        + ". Please, make sure that you have specified the correct path to the file(s) you want to be copied.");

            if (file.isDirectory()) {
                downloadDirectory(file, downloaderHandler);
            } else {
                downloadFile(file, downloaderHandler);
            }
        } catch (IOException | CopierException e) {
            throw new DownloaderException(e);
        }
    }

    protected void downloadDirectory(File file, DownloaderHandler downloaderHandler) throws IOException, CopierException {
        File[] files = file.listFiles();
        for (File currFile : files) {
            if (currFile.isDirectory()) {
                downloadDirectory(currFile, downloaderHandler);
            } else {
                downloadFile(currFile, downloaderHandler);
            }
        }
    }

    protected void downloadFile(File file, DownloaderHandler downloaderHandler) throws IOException, CopierException {
        downloaderHandler.handle(FileUtils.readFileToByteArray(file), file.toURI());
    }
}
