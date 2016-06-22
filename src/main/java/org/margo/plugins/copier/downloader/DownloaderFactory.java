package org.margo.plugins.copier.downloader;

import org.margo.plugins.copier.Parser;
import org.margo.plugins.copier.annotations.Reader;
import org.margo.plugins.copier.exception.CopierException;

public class DownloaderFactory {

    public Downloader createDownloader(String scheme) throws CopierException {
        Class c = Parser
                .getDownloaders()
                .stream()
                .filter(x -> x.getAnnotation(Reader.class).value().equals(scheme))
                .findFirst()
                .orElse(null);

        Downloader downloader;
        if (c == null) {
            throw new IllegalArgumentException(scheme + " is unknown scheme");
        } else {
            try {
                downloader = (Downloader) c.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw  new CopierException(e);
            }
        }

        return downloader;
    }
}
