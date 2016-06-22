import org.junit.Before;
import org.junit.Test;
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

    private DownloaderFactory downloaderFactory;
    private UploaderFactory uploaderFactory;

    @Before
    public void setUp() {
        downloaderFactory = new DownloaderFactory();
        uploaderFactory = new UploaderFactory();
    }

    @Test
    public void checkLocal() throws URISyntaxException, CopierException {

        URI source = new URI("file://D:/target/test.txt");
        URI target = new URI("file://D:/test.txt");

        perform(source, target);
    }

    @Test
    public void checkHDFSUploader() throws URISyntaxException, CopierException {

        URI source = new URI("file:///D:/test.txt");
        URI target = new URI("hdfs://192.168.0.105:8020/user/Margo/target/test.txt");

        perform(source, target);
    }

    @Test
    public void checkHDFSDownloader() throws URISyntaxException, CopierException {

        URI source = new URI("hdfs://192.168.0.105:8020/user/Margo/target/test.txt");
        URI target = new URI("file://D:/target/test.txt");

        perform(source, target);
    }

    @Test
    public void checkSFTPUploader() throws URISyntaxException, CopierException {

        URI source = new URI("file://D:/test.txt");
        URI target = new URI("sftp://root:margo300194@192.168.0.105:22/root/user/Margo/test.txt");

        perform(source, target);
    }

    @Test
    public void checkSFTPDownloader() throws URISyntaxException, CopierException {

        URI source = new URI("sftp://root:margo300194@192.168.0.105:22/root/user/Margo/test.txt");
        URI target = new URI("file://D:/sftp.txt");

        perform(source, target);
    }

    private void perform(URI source, URI target) throws CopierException {
        try {
            Downloader downloader = downloaderFactory.createDownloader(source.getScheme());
            byte[] data = downloader.download(source);

            Uploader uploader = uploaderFactory.createUploader(target.getScheme());
            uploader.upload(target, data);
        } catch (UploaderException | DownloaderException e) {
            throw new CopierException(e);
        }
    }
}
