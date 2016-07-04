package org.margo.plugins.copier.uploader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.margo.plugins.copier.annotation.Writer;
import org.margo.plugins.copier.exception.UploaderException;

import java.io.*;
import java.net.URI;

@Writer(value = "hdfs")
public class HDFSUploader implements Uploader {
    @Override
    public boolean upload(URI uri, byte[] data) throws UploaderException {
        try {
            FileSystem hdfs = FileSystem.get(uri, new Configuration());
            InputStream in = new ByteArrayInputStream(data);
            OutputStream out = hdfs.create(new Path(uri));
            IOUtils.copyBytes(in, out, 4096, true);
        } catch (IOException e) {
            throw new UploaderException(e);
        }

        return true;
    }
}