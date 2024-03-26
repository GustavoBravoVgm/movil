package com.ar.vgmsistemas.task.sincronizacion;

import android.content.Context;
import android.os.AsyncTask;

import com.ar.vgmsistemas.bo.SincronizacionBo;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;

public class SincronizacionTask extends AsyncTask<Void, Void, Void> {
    public static final int DESCARGA = 0;
    public static final int ENVIO = 1;
    int modoSincronizacion;
    boolean done = false;
    private SincronizacionListener mListener;
    private Context mContext;
    private String error;

    public SincronizacionTask(Context context, int modo, SincronizacionListener listener) {
        mListener = listener;
        modoSincronizacion = modo;
        mContext = context;
    }

    @Override
    protected Void doInBackground(Void... unused) {
        RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(mContext, RepositoryFactory.ROOM);
        SincronizacionBo sincronizacionBo = new SincronizacionBo(mContext, repoFactory);
        switch (modoSincronizacion) {
            case DESCARGA:
                try {
                    done = sincronizacionBo.descargarDatos();
                } catch (Exception e) {
                    e.printStackTrace();
                    done = false;
                    error = formatearErrorDescarga(e.getMessage());
                }
                break;
            case ENVIO:
                try {
                    done = sincronizacionBo.enviarDatos();
                    if (!done) {
                        error = ErrorManager.ErrorEnvioDatosServidor;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    error = formatearErrorDescarga(e.getMessage());
                }
                break;

        }
        return (null);
    }

    private String formatearErrorDescarga(String message) {
        if (message != ErrorManager.ErrorAccesoSdCard
                && message != ErrorManager.ErrorConexionDatos
                && message != ErrorManager.ErrorConexionServidor
                && message != ErrorManager.ErrorTarjetaSDLlena
                && message != ErrorManager.ErrorPedidosDeshabilitados
                && message != ErrorManager.ErrorVendedorNoValido
                && message != ErrorManager.ErrorHojasDetalles) {
            return ErrorManager.ErrorVinculoConServidor;
        } else {
            return message;
        }
    }

    @Override
    protected void onProgressUpdate(Void... unused) {
    }

    @Override
    protected void onPostExecute(Void unused) {
        if (mListener != null) {
            if (done) {
                mListener.onDone();
            } else {
                mListener.onError(error);
            }
        }
    }


    public interface SincronizacionListener {
        void onDone();

        void onError(String error);
    }
}	