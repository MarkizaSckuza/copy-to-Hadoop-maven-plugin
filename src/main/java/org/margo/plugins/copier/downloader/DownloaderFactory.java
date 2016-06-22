package org.margo.plugins.copier.downloader;

import com.google.common.base.Preconditions;
import org.margo.plugins.copier.Parser;
import org.margo.plugins.copier.annotation.Reader;
import org.margo.plugins.copier.exception.CopierException;

public class DownloaderFactory {

    public Downloader createDownloader(String scheme) throws CopierException {
        Class c = Parser
                .getDownloaders()
                .stream()
                .filter(x -> {
                    Preconditions.checkNotNull(x.getAnnotation(Reader.class).value(), "Value on Reader annotation can not be null");
                    return x.getAnnotation(Reader.class).value().equals(scheme);
                })
                .findFirst()
                .orElse(null);

        if (c == null) {
            throw new IllegalArgumentException(scheme + " is unknown scheme");
        } else {
            try {
                return (Downloader) c.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw  new CopierException(e);
            }
        }
    }
}
