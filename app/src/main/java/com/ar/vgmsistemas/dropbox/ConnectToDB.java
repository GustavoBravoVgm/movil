package com.ar.vgmsistemas.dropbox;

import android.content.Context;
import android.os.AsyncTask;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.users.FullAccount;

public class ConnectToDB extends AsyncTask<String, Void, FullAccount> {
    private final Context mContext;
    private DbxClientV2 mDbxClient;
    private Exception mException;
    private String token;
    private String folder;
    private Callback mCallback;
    private int methodToRun;

    public interface Callback {
        void onLoginComplete(FullAccount result, DbxClientV2 client, int method) throws DbxException;

        void onError(Exception e);
    }

    ConnectToDB(Context context, String token, String clientName, Callback callback, int method) {
        mContext = context;
        this.token = token;
        mCallback = callback;
        methodToRun = method;
        folder = clientName;
    }

    @Override
    protected void onPostExecute(FullAccount result) {
        super.onPostExecute(result);
        if (mException != null) {
            mCallback.onError(mException);
        } else if (result == null) {
            mCallback.onError(null);
        } else {
            try {
                mCallback.onLoginComplete(result, mDbxClient, methodToRun);
            } catch (DbxException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected FullAccount doInBackground(String... params) {
        try {
            DbxRequestConfig config = new DbxRequestConfig(folder);
            mDbxClient = new DbxClientV2(config, this.token);
            // Get current account info
            return mDbxClient.users().getCurrentAccount();
        } catch (Exception e) {
            mException = e;
        }
        return null;
    }
}