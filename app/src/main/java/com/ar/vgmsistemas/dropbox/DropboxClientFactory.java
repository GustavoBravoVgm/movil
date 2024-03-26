package com.ar.vgmsistemas.dropbox;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.http.StandardHttpRequestor;
import com.dropbox.core.v2.DbxClientV2;

/**
 * Singleton instance of {@link DbxClientV2} and friends
 */
public class DropboxClientFactory {

    private static DbxClientV2 sDbxClient;

    public static void init(String client, String accessToken) {
        if (sDbxClient == null) {
            DbxRequestConfig requestConfig = DbxRequestConfig.newBuilder(client)
                    .withHttpRequestor(new StandardHttpRequestor(StandardHttpRequestor.Config.DEFAULT_INSTANCE))
                    .build();

            sDbxClient = new DbxClientV2(requestConfig, accessToken);
        }
    }

    public static DbxClientV2 getClient() {
        if (sDbxClient == null) {
            throw new IllegalStateException("Client not initialized.");
        }
        return sDbxClient;
    }
}