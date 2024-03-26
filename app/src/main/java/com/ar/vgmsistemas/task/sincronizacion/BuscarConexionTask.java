package com.ar.vgmsistemas.task.sincronizacion;

import android.content.Context;
import android.os.AsyncTask;

import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.ws.ConnectionMannager;

public class BuscarConexionTask extends AsyncTask<Void, Void, Void> {

    private BuscarConexionListener mListener;
    private Context mContext;
    boolean done = false;
    String error;
    private String url;

    public BuscarConexionTask(Context context, BuscarConexionListener listener, String url) {
        mListener = listener;
        mContext = context;
        error = ErrorManager.ErrorConexion;
        this.url = url;
    }

    @Override
    protected Void doInBackground(Void... params) {
        ConnectionMannager cm = new ConnectionMannager(mContext);
        done = cm.checkConnection(url);

        return (null);
    }

    @Override
    protected void onProgressUpdate(Void... unused) {
    }

    @Override
    protected void onPostExecute(Void unused) {
        if (mListener != null)
            if (done) {
                mListener.onDone();
                ;
            } else {
                mListener.onError(ErrorManager.ErrorConexion);

            }
    }

    public interface BuscarConexionListener {
        void onDone();

        void onError(String error);
    }
}
