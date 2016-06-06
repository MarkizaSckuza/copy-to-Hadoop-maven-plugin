package org.margo.plugins.copier.downloader;

public class DownloaderFactory {

    public Downloader createDownloader(String scheme) {
        Downloader downloader;
        switch (scheme) {
            case "hdfs":
                downloader = new HDFSDownloader();
                break;
            case "sftp":
                downloader = new SFTPDownloader();
                break;
            case "file":
                downloader = new LocalFileDownloader();
                break;
            case "http":
                downloader = new HTTPDownloader();
                break;
            default:
                throw new IllegalArgumentException(scheme + "is unknown scheme");
        }

        return downloader;
    }
}
