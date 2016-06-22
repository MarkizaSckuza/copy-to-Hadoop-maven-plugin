package org.margo.plugins.copier.uploader;

import org.apache.commons.io.IOUtils;
import org.margo.plugins.copier.URICommons;
import org.margo.plugins.copier.annotations.Writer;
import org.margo.plugins.copier.exception.UploaderException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Writer(value = "file")
public class LocalFileUploader implements Uploader {
    @Override
    public boolean upload(URI uri, byte[] data) throws UploaderException {
        try {
            Path path = Paths.get(URICommons.getPath(uri));

            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            OutputStream out = new FileOutputStream(uri.getPath());
            IOUtils.write(data, out);
        } catch (IOException e) {
            throw new UploaderException(e);
        }

        return true;
    }
}
