package org.margo.plugins.copier.uploader;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.margo.plugins.copier.exception.UploaderException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

public class HDFSUploader implements Uploader {
    @Override
    public byte[] upload(URI uri, byte[] data) throws UploaderException {
        try {
            FileSystem hdfs = FileSystem.get(uri, new Configuration());
            InputStream in = new BufferedInputStream(new ByteInputStream(data, data.length));
            OutputStream out = hdfs.create(new Path(uri));
            IOUtils.copyBytes(in, out, 4096, true);
        } catch (IOException e) {
            throw new UploaderException(e);
        }

        return new byte[] {};
    }
}
