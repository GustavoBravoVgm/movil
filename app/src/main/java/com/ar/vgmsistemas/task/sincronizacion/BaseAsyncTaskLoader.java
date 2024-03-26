package com.ar.vgmsistemas.task.sincronizacion;

import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;


public abstract class BaseAsyncTaskLoader<C> extends AsyncTaskLoader<C> {
    protected C result;
    public static final int RESULT_OK = 0;
    public static final int RESULT_ERROR = 1;
    private boolean isDownloading = false;

    public BaseAsyncTaskLoader(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public C loadInBackground() {
        // TODO Auto-generated method stub
        return result;
    }

    public boolean isDownloading() {
        return isDownloading;
    }

    public void setIsDownloading(boolean downloading) {
        isDownloading = downloading;
    }

    @Override
    protected void onStopLoading() {
        // TODO Auto-generated method stub
        super.onStopLoading();
        cancelLoad();
    }

    @Override
    protected void onStartLoading() {
        // TODO Auto-generated method stub
        super.onStartLoading();
        if (result == null && !isDownloading) {
            forceLoad();
        }
        ;
        if (result != null) {
            deliverResult(result);

        }
    }

    @Override
    protected void onReset() {
        // TODO Auto-generated method stub
        super.onReset();
        onStopLoading();

    }

    @Override
    public void deliverResult(C data) {
        if (isReset()) {
            return;
        }
        if (isStarted()) {

            super.deliverResult(data);

        }


    }

}
