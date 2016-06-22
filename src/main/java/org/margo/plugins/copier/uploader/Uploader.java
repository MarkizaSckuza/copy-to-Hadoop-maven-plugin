package org.margo.plugins.copier.uploader;

import org.margo.plugins.copier.exception.UploaderException;

import java.net.URI;

public interface Uploader {
    boolean upload(URI uri, byte[] data) throws UploaderException;
}
