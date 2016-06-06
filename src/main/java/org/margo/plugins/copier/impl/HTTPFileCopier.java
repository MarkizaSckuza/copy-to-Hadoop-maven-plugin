package org.margo.plugins.copier.impl;

import org.margo.plugins.copier.api.AbstractRemoteFileCopier;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

public class HTTPFileCopier extends AbstractRemoteFileCopier {

    private Map<String, Object> additionalParams;
    private Map<String, String> headers;

    public HTTPFileCopier() {
    }

    public HTTPFileCopier(Map<String, Object> additionalParams) {
        this.additionalParams = additionalParams;
    }

    public HTTPFileCopier(Map<String, Object> additionalParams, Map<String, String> headers) {
        this.additionalParams = additionalParams;
        this.headers = headers;
    }

    @Override
    protected void doCopy(File source, URI targetPath) throws IOException {

    }

    public Map<String, Object> getAdditionalParams() {
        return additionalParams;
    }

    public HTTPFileCopier setAdditionalParams(Map<String, Object> additionalParams) {
        this.additionalParams = additionalParams;
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public HTTPFileCopier setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }
}
