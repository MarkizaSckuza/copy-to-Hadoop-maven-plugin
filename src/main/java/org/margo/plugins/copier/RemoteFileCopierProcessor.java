package org.margo.plugins.copier;

import org.margo.plugins.copier.downloader.Downloader;
import org.margo.plugins.copier.downloader.DownloaderFactory;
import org.margo.plugins.copier.exception.CopierException;
import org.margo.plugins.copier.exception.DownloaderException;
import org.margo.plugins.copier.exception.UploaderException;
import org.margo.plugins.copier.uploader.Uploader;
import org.margo.plugins.copier.uploader.UploaderFactory;

import java.net.URI;
import java.net.URISyntaxException;

public class RemoteFileCopierProcessor implements RemoteFileCopier {

    private DownloaderFactory downloaderFactory;

    private UploaderFactory uploaderFactory;

    public RemoteFileCopierProcessor(DownloaderFactory downloaderFactory, UploaderFactory uploaderFactory) {
        this.downloaderFactory = downloaderFactory;
        this.uploaderFactory = uploaderFactory;
    }

    @Override
    public void copy(URI source, URI target) throws CopierException {
        try {
            final URI normalizedSource = normalizeURI(source);
            final URI normalizedTarget = normalizeURI(target);
            Downloader downloader = downloaderFactory.createDownloader(normalizedSource.getScheme());
            Uploader uploader = uploaderFactory.createUploader(normalizedTarget.getScheme());
            downloader.download(normalizedSource, (data, dataURI) -> {
                String targetPathFragment = dataURI.toString().substring(normalizedSource.toString().length());
                URI newTarget = URI.create(normalizedTarget.toString() + targetPathFragment);
                uploader.upload(newTarget, data);
            });
        } catch (UploaderException | DownloaderException | URISyntaxException e) {
            throw new CopierException(e);
        }
    }

    protected URI normalizeURI(URI uri) throws URISyntaxException {
        String uriStr = uri.normalize().toString();
        return uriStr.endsWith("/") ? new URI(uriStr.substring(0, uriStr.length() - 1)) : new URI(uriStr);
    }

    public DownloaderFactory getDownloaderFactory() {
        return downloaderFactory;
    }

    public void setDownloaderFactory(DownloaderFactory downloaderFactory) {
        this.downloaderFactory = downloaderFactory;
    }

    public UploaderFactory getUploaderFactory() {
        return uploaderFactory;
    }

    public void setUploaderFactory(UploaderFactory uploaderFactory) {
        this.uploaderFactory = uploaderFactory;
    }
}
