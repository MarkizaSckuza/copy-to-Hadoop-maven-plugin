package org.margo.plugins.copier.uploader;

import org.margo.plugins.copier.exception.UploaderException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

public class LocalFileUploader implements Uploader {
    @Override
    public byte[] upload(URI uri, byte[] data) throws UploaderException {
        try (OutputStream output = new FileOutputStream(uri.getPath())){
            output.write(data);
        } catch (IOException e) {
            throw new UploaderException(e);
        }

        return new byte[] {};
    }
}
