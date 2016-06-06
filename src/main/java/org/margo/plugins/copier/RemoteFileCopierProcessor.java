package org.margo.plugins.copier;

import org.margo.plugins.copier.downloader.Downloader;
import org.margo.plugins.copier.downloader.DownloaderFactory;
import org.margo.plugins.copier.exception.CopierException;
import org.margo.plugins.copier.exception.DownloaderException;
import org.margo.plugins.copier.exception.UploaderException;
import org.margo.plugins.copier.uploader.Uploader;
import org.margo.plugins.copier.uploader.UploaderFactory;

import java.net.URI;

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
            Downloader downloader = downloaderFactory.createDownloader(source.getScheme());
            byte[] data = downloader.download(source);
            Uploader uploader = uploaderFactory.createUploader(target.getScheme());
            uploader.upload(target, data);
        } catch (UploaderException | DownloaderException e) {
            throw new CopierException(e);
        }
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
