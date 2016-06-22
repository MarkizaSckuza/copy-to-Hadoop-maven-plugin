package org.margo.plugins.copier.uploader;

import com.google.common.base.Preconditions;
import org.margo.plugins.copier.Parser;
import org.margo.plugins.copier.annotation.Writer;
import org.margo.plugins.copier.exception.CopierException;

public class UploaderFactory {

    public Uploader createUploader(String scheme) throws CopierException {
        Class c = Parser
                .getUploaders()
                .stream()
                .filter(x -> {
                    Preconditions.checkNotNull(x.getAnnotation(Writer.class).value(), "Value on Writer annotation can not be null");
                    return x.getAnnotation(Writer.class).value().equals(scheme);
                })
                .findFirst()
                .orElse(null);

        if (c == null) {
            throw new IllegalArgumentException(scheme + " is unknown scheme");
        } else {
            try {
                return (Uploader) c.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw  new CopierException(e);
            }
        }
    }
}
