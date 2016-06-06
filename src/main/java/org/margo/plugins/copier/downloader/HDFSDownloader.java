package org.margo.plugins.copier.downloader;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.margo.plugins.copier.exception.DownloaderException;

import java.io.IOException;
import java.net.URI;

public class HDFSDownloader implements Downloader {
    @Override
    public byte[] download(URI uri) throws DownloaderException {
        try {
            Path path = new Path(uri);
            FileSystem hdfs = FileSystem.get(uri, new Configuration());
            return IOUtils.toByteArray(hdfs.open(path));
        } catch (IOException e) {
            throw new DownloaderException(e);
        }
    }
}
