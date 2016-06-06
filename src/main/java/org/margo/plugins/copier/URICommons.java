package org.margo.plugins.copier;

import java.net.URI;

public class URICommons {
    public static final int UNDEFINED_PORT = -1;
    public static final int DEFAULT_SSH_PORT = 22;

    public static String getUsername(URI uri) {
        String username = null;
        String userInfo = uri.getUserInfo();
        if (userInfo != null) {
            username = userInfo.split(":")[0];

        }
        return username;
    }

    public static String getPassword(URI uri) {
        String pass = null;
        String userInfo = uri.getUserInfo();
        if (userInfo != null) {
            String[] splittedUserInfo = userInfo.split(":");
            if (splittedUserInfo.length == 2)
                pass = splittedUserInfo[1];

        }

        return pass;
    }

}
