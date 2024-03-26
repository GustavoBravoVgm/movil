package com.ar.vgmsistemas.task.actualizacion;

import android.content.Context;
import android.os.AsyncTask;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.entity.Version;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.UpdateManager;
import com.ar.vgmsistemas.view.FrmActualizar;

import java.io.File;

public class ActualizarVersionTask extends AsyncTask<Void, Void, Void> {

    public static final int MODO_TRAER_APK = 0;
    public static final int MODO_DESCARGAR_APK = 1;

    private ActualizarVersionListener mListener;
    int modo;
    FrmActualizar _fragment = null;
    boolean done = false;
    String error;
    Version version;
    private Context mContext;

    UpdateManager updateManager;

    public ActualizarVersionTask(FrmActualizar fragment, int modo, ActualizarVersionListener listener) {
        _fragment = fragment;
        this.modo = modo;
        mContext = fragment.getActivity();
        mListener = listener;
        updateManager = new UpdateManager(mContext);
    }

    private boolean traerVersionApk() {
        boolean trajo = false;

        try {
            version = updateManager.getLastVersion();
            if (version == null) {
                error = mContext.getString(R.string.msjErrorAlConectarPorFTP);
            } else {
                trajo = true;
            }

        } catch (Exception e) {
            trajo = false;
            error = mContext.getString(R.string.msjErrorAlConectarPorFTP);
        }
        return trajo;
    }

    @Override
    protected Void doInBackground(Void... unused) {
        done = false;
        try {
            switch (modo) {
                case MODO_TRAER_APK:
                    done = traerVersionApk();
                    break;
                case MODO_DESCARGAR_APK:
                    done = descargarApk();
                    break;
            }
        } catch (Exception e) {
            ErrorManager.manageException("FrmConfiguracionesAvanzadas", "doInBackground", e);
        }
        return (null);
    }

    private boolean descargarApk() {

        boolean descargo = false;

        try {
            updateManager.update();

        } catch (Exception e) {
            descargo = false;
        }

        String fileName = Preferencia.getPathSistema() + "/PreventaMovil.apk";
        File file = new File(fileName);
        if (file.exists()) {
            descargo = true;
        } else {
            descargo = false;
        }
        return descargo;
    }

    @Override
    protected void onProgressUpdate(Void... unused) {
    }

    @Override
    protected void onPostExecute(Void unused) {
        if (_fragment != null)
            if (done) {
                if (modo == MODO_TRAER_APK) {
                    _fragment.recibirVersion(version);
                } else {
                    _fragment.instalarActualizacion();
                }
            } else {
                mListener.onError(error);
            }
        detach();
    }

    public void detach() {
        _fragment = null;
    }

    public void attach(FrmActualizar activity) {
        this._fragment = activity;
    }

    public interface ActualizarVersionListener {
        void onDone();

        void onError(String error);
    }

}
