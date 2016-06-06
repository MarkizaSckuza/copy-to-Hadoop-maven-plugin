package org.margo.plugins.copier.uploader;

public class UploaderFactory {

    public Uploader createUploader(String scheme) {
        Uploader uploader;
        switch (scheme) {
            case "hdfs":
                uploader =  new HDFSUploader();
                break;
            case "sftp":
                uploader =  new SFTPUploader();
                break;
            case "http":
                uploader = new HTTPUploader();
                break;
            case "file":
                uploader = new LocalFileUploader();
                break;
            default:
                throw new IllegalArgumentException(scheme + "is unknown scheme");
        }
        return uploader;
    }
}
