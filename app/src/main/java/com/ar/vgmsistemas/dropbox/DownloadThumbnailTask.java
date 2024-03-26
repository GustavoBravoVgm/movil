package com.ar.vgmsistemas.dropbox;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.ar.vgmsistemas.entity.Preferencia;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ThumbnailFormat;
import com.dropbox.core.v2.files.ThumbnailSize;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DownloadThumbnailTask extends AsyncTask<FileMetadata, Void, String> {
    private final Context mContext;
    private final DbxClientV2 mDbxClient;
    private final Callback mCallback;
    private Exception mException;

    public interface Callback {
        void onDownloadComplete(String path);

        void onError(Exception e);
    }

    public DownloadThumbnailTask(Context context, DbxClientV2 dbxClient, Callback callback) {
        mContext = context;
        mDbxClient = dbxClient;
        mCallback = callback;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (mException != null) {
            mCallback.onError(mException);
        } else {
            mCallback.onDownloadComplete(result);
        }
    }

    @Override
    protected String doInBackground(FileMetadata... params) {
        FileMetadata metadata = params[0];
        try {
            File path;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                path = new File(mContext.getExternalFilesDir(null) + "" +
                        File.separator + Preferencia.getPathImages());
            } else {
                path = new File(Environment.getExternalStorageDirectory() +
                        File.separator + Preferencia.getPathImages());
            }
            // Make sure the Downloads directory exists.
            if (!path.exists()) {
                if (!path.mkdirs()) {
                    mException = new RuntimeException("Unable to create directory: " + path);
                }
            } else if (!path.isDirectory()) {
                mException = new IllegalStateException("Download path is not a directory: " + path);
                return null;
            } else {
                File pathThumb;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    pathThumb = new File(mContext.getExternalFilesDir(null) + "" +
                            File.separator + Preferencia.getPathImages() + File.separator + "Thumb_" + metadata.getName());
                } else {
                    pathThumb = new File(Environment.getExternalStorageDirectory() +
                            File.separator + Preferencia.getPathImages() + File.separator + "Thumb_" + metadata.getName());
                }
                if (pathThumb.exists()) {
                    Log.v("Fenibi", "Ya estaba:" + metadata.getName());
                    return "ok";
                }
            }

            // Download the file.
            OutputStream outputStream = new FileOutputStream(path.getAbsolutePath() + "/Thumb_" + metadata.getName());
            Log.v("Fede", "Trying:" + path.getAbsolutePath() + "/Thumb_" + metadata.getName());
            try {
                mDbxClient.files().getThumbnailBuilder(metadata.getPathLower())
                        .withFormat(ThumbnailFormat.JPEG)
                        .withSize(ThumbnailSize.W256H256)
                        .download(outputStream);
            } catch (Exception e) {
                Log.v("Fenibi", e.getMessage());
                throw e;
            } finally {
                outputStream.close();
            }

            // Tell android about the file
            /*Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(file));
            mContext.sendBroadcast(intent);*/

            return path.getAbsolutePath() + "/Thumb_" + metadata.getName();
        } catch (DbxException | IOException e) {
            mException = e;
        }

        return null;
    }
}
