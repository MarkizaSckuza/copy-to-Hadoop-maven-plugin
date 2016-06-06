package org.margo.plugins.copier.api;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.net.URI;

public class Config {

    private static final int UNDEFINED_PORT = -1;
    private static final int DEFAULT_SSH_PORT = 22;

    private URI uri;

    private String host;

    private int port;

    private String schema;

    private String username;

    private String password;

    public Config() {
    }

    public Config(URI uri) {
        this.uri = uri;
        buildByURI(getUri());
    }

    public String getHost() {
        return host;
    }

    public Config setHost(String host) {
        this.host = host;
        return this;
    }

    public int getPort() {
        return port;
    }

    public Config setPort(int port) {
        this.port = port;
        return this;
    }

    public String getSchema() {
        return schema;
    }

    public Config setSchema(String schema) {
        this.schema = schema;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Config setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Config setPassword(String password) {
        this.password = password;
        return this;
    }

    public URI getUri() {
        return uri;
    }

    public Config setUri(URI uri) {
        this.uri = uri;
        return this;
    }

    public String parseUsername(URI uri) {
        String username = null;
        String userInfo = uri.getUserInfo();
        if (userInfo != null) {
            username = userInfo.split(":")[0];

        }
        return username;
    }

    public String parsePassword(URI uri) {
        String pass = null;
        String userInfo = uri.getUserInfo();
        if (userInfo != null) {
            String[] splittedUserInfo = userInfo.split(":");
            if (splittedUserInfo.length == 2)
                pass = splittedUserInfo[1];

        }
        return pass;
    }

    public Config buildByURI(URI uri) {
        return setHost(uri.getHost())
                .setPort(uri.getPort() == UNDEFINED_PORT ? DEFAULT_SSH_PORT : uri.getPort())
                .setSchema(uri.getScheme())
                .setUsername(parseUsername(uri))
                .setPassword(parsePassword(uri));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("host", host)
                .append("port", port)
                .append("schema", schema)
                .append("username", username)
                .append("password", password)
                .toString();
    }
}
