package org.margo.plugins.copier.downloader;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.margo.plugins.copier.annotation.Reader;
import org.margo.plugins.copier.exception.CopierException;
import org.margo.plugins.copier.exception.DownloaderException;

import java.io.IOException;
import java.net.URI;

@Reader(value = "hdfs")
public class HDFSDownloader implements Downloader {
    @Override
    public void download(URI uri, DownloaderHandler downloaderHandler) throws DownloaderException {
        try {
            Path path = new Path(uri);
            FileSystem hdfs = FileSystem.get(uri, new Configuration());

            if (!hdfs.exists(path))
                throw new IOException("There is no such file/directory on the specified path " + path
                        + ". Please, make sure that you have specified the correct path to the file(s) you want to be copied.");

            if (hdfs.isDirectory(path)) {
                downloadDirectory(hdfs, path, downloaderHandler);
            } else {
                downloadFile(hdfs, path, downloaderHandler);
            }
        } catch (IOException | CopierException e) {
            throw new DownloaderException(e);
        }
    }

    protected void downloadDirectory(FileSystem hdfs, Path path, DownloaderHandler downloaderHandler) throws IOException, CopierException {
        FileStatus[] fileStatuses = hdfs.listStatus(path);
        for (FileStatus fileStatus : fileStatuses) {
            if (fileStatus.isDirectory()) {
                downloadDirectory(hdfs, fileStatus.getPath(), downloaderHandler);
            } else {
                downloadFile(hdfs, fileStatus.getPath(), downloaderHandler);
            }
        }
    }

    protected void downloadFile(FileSystem hdfs, Path path, DownloaderHandler downloaderHandler) throws IOException, CopierException {
        downloaderHandler.handle(IOUtils.toByteArray(hdfs.open(path)), path.toUri());
    }
}
