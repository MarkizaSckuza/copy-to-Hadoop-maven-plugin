package org.margo.plugins.copier.uploader;

import org.margo.plugins.copier.Parser;
import org.margo.plugins.copier.annotations.Writer;
import org.margo.plugins.copier.exception.CopierException;

public class UploaderFactory {

    public Uploader createUploader(String scheme) throws CopierException {
        Class c = Parser
                .getUploaders()
                .stream()
                .filter(x -> x.getAnnotation(Writer.class).value().equals(scheme))
                .findFirst()
                .orElse(null);

        Uploader uploader;
        if (c == null) {
            throw new IllegalArgumentException(scheme + " is unknown scheme");
        } else {
            try {
                uploader = (Uploader) c.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw  new CopierException(e);
            }
        }
        return uploader;
    }
}
