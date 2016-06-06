package org.margo.plugins.copier.uploader;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.margo.plugins.copier.exception.UploaderException;

import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

public class HTTPUploader implements Uploader {

    private static final String USER_AGENT = "Mozilla/5.0";

    private Set<Header> headers = new HashSet<Header>() {{
        add(new BasicHeader("User-Agent", USER_AGENT));
    }};

    public HTTPUploader() {
    }

    public HTTPUploader(Set<Header> headers) {
        this.headers = headers;
    }

    @Override
    public byte[] upload(URI uri, byte[] data) throws UploaderException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()){
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setHeaders(headers.toArray(new Header[this.headers.size()]));
            httpPost.setEntity(new ByteArrayEntity(data));
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            return IOUtils.toByteArray(httpResponse.getEntity().getContent());
        } catch (IOException e) {
            throw new UploaderException(e);
        }
    }
}
