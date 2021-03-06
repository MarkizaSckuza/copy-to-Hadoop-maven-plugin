package org.margo.plugins.copier.downloader;


import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.margo.plugins.copier.annotation.Reader;
import org.margo.plugins.copier.exception.CopierException;
import org.margo.plugins.copier.exception.DownloaderException;

import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

@Reader("http")
public class HTTPDownloader implements Downloader {

    private static final String USER_AGENT = "Mozilla/5.0";

    private Set<Header> headers = new HashSet<Header>() {{
        add(new BasicHeader("User-Agent", USER_AGENT));
    }};

    public HTTPDownloader() {
    }

    public HTTPDownloader(Set<Header> headers) {
        this.headers.addAll(headers);
    }

    @Override
    public void download(URI uri, DownloaderHandler downloaderHandler) throws DownloaderException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()){
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setHeaders(headers.toArray(new Header[this.headers.size()]));
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            downloaderHandler.handle(IOUtils.toByteArray(httpResponse.getEntity().getContent()), uri);
        } catch (IOException | CopierException e) {
            throw new DownloaderException(e);
        }
    }
}
