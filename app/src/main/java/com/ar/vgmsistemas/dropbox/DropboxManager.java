package com.ar.vgmsistemas.dropbox;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.ar.vgmsistemas.bo.EmpresaBo;
import com.ar.vgmsistemas.entity.Empresa;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;

public class DropboxManager {
    String accessToken = "";
    String folder = "";
    Context context;
    EmpresaBo empresaBo;

    long imagesSize;
    long imagesSizeTotal;
    boolean downloadError = false;
    boolean hasMore = false;
    DownloadImageListener downloadImageListener;


    public interface DownloadImageListener {
        void onDone(boolean downloadError);

        void refreshLoader(long size, long ok);
    }

    public DropboxManager(Context context) {
        empresaBo = new EmpresaBo(RepositoryFactory.getRepositoryFactory(context, RepositoryFactory.ROOM));
        Empresa empresa;
        try {
            empresa = empresaBo.recoveryEmpresa();
            this.setAccessToken(empresa.getDropboxToken());
            this.setFolder(empresa.getDropboxAppName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getImagesSize() {
        return imagesSize;
    }

    public void setImagesSize(long imagesSize) {
        this.imagesSize = imagesSize;
    }

    public long getImagesSizeTotal() {
        return imagesSizeTotal;
    }

    public void setImagesSizeTotal(long imagesSizeTotal) {
        this.imagesSizeTotal = imagesSizeTotal;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String access_token) {
        this.accessToken = access_token;
    }

    public DownloadImageListener getDownloadImageListener() {
        return downloadImageListener;
    }

    public void setDownloadImageListener(DownloadImageListener downloadImageListener) {
        this.downloadImageListener = downloadImageListener;
    }

    public boolean isDownloadError() {
        return downloadError;
    }

    public void setDownloadError(boolean downloadError) {
        this.downloadError = downloadError;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public void downloadAllImages(final DownloadImageListener downloadImageListener) {
        this.setDownloadImageListener(downloadImageListener);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    setHasMore(true);
                    ListFolderResult result = null;
                    while (isHasMore()) {
                        if (result == null) {
                            result = DropboxClientFactory.getClient().files().listFolder("");
                            imagesSize = result.getEntries().size();
                            setImagesSize(imagesSize);
                            setImagesSizeTotal(imagesSize);
                            setHasMore(result.getHasMore());
                        } else {
                            result = DropboxClientFactory.getClient().files().listFolderContinue(result.getCursor());
                            imagesSize = result.getEntries().size();
                            Log.v("Prueba", "Actualizo la cantidad");
                            setImagesSize(getImagesSize() + imagesSize);
                            setImagesSizeTotal(getImagesSizeTotal() + imagesSize);
                        }

                        for (Metadata metadata : result.getEntries()) {
                            Log.v("Fede", metadata.getPathLower());
                            downloadThumbnail((FileMetadata) metadata);
                        }
                        setHasMore(result.getHasMore());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.run();
    }

    private void downloadThumbnail(FileMetadata fileMetadata) {
        DownloadThumbnailTask.Callback callback = new DownloadThumbnailTask.Callback() {

            @Override
            public void onDownloadComplete(String path) {
                Log.v("Fede", "Descarga Correcta" + path);
                setImagesSize(getImagesSize() - 1);
                if (getImagesSize() == 0 && !isHasMore()) {
                    Log.v("Prueba", "Termino");
                    getDownloadImageListener().onDone(isDownloadError());
                } else {
                    getDownloadImageListener().refreshLoader(getImagesSizeTotal(), getImagesSize());
                }

            }

            @Override
            public void onError(Exception e) {
                Log.v("Fede", "Error en la descarga" + e.getMessage());
                setImagesSize(getImagesSize() - 1);
                setDownloadError(true);
                if (getImagesSize() == 0) {
                    getDownloadImageListener().onDone(isDownloadError());
                } else {
                    getDownloadImageListener().refreshLoader(getImagesSizeTotal(), getImagesSize());
                }
            }
        };

        DownloadThumbnailTask downloadThumbnailTask = new DownloadThumbnailTask(this.context, DropboxClientFactory.getClient(), callback);
        downloadThumbnailTask.execute(fileMetadata);
    }

}

