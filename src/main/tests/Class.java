package tests;

import org.margo.plugins.copier.downloader.Downloader;
import org.margo.plugins.copier.downloader.DownloaderFactory;
import org.margo.plugins.copier.exception.CopierException;
import org.margo.plugins.copier.exception.DownloaderException;
import org.margo.plugins.copier.exception.UploaderException;
import org.margo.plugins.copier.uploader.Uploader;
import org.margo.plugins.copier.uploader.UploaderFactory;

import java.net.URI;
import java.net.URISyntaxException;

public class Class {

    public static void main(String[] args) throws URISyntaxException, CopierException {

        URI source = new URI("file:///D:/test.txt");
        URI target = new URI("file:///D:/target/test.txt");

        DownloaderFactory downloaderFactory = new DownloaderFactory();
        UploaderFactory uploaderFactory = new UploaderFactory();

        try {
            Downloader downloader = downloaderFactory.createDownloader(source.getScheme());
            byte[] data = downloader.download(source);
            Uploader uploader = uploaderFactory.createUploader(target.getScheme());
            uploader.upload(target, data);
        } catch (UploaderException | DownloaderException e) {
            throw new CopierException(e);
        }
    }

    @Test
    public void checkHDFSUploader() {}
}
