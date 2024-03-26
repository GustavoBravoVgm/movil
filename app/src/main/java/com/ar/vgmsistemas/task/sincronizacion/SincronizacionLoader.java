package com.ar.vgmsistemas.task.sincronizacion;

import android.content.Context;

import com.ar.vgmsistemas.bo.SincronizacionBo;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;

public class SincronizacionLoader extends BaseAsyncTaskLoader<Integer> {

    public static final int MODE_SINCRONIZACION = 1;
    public static final int MODE_ENVIO = 2;

    private String error;
    int mMode;

    public SincronizacionLoader(Context context, int mode) {
        super(context);
        mMode = mode;
    }

    public void setMode(int mode) {
        mMode = mode;
    }

    @Override
    public Integer loadInBackground() {
        RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        SincronizacionBo sincronizacionBo = new SincronizacionBo(getContext(), repoFactory);
        boolean done = false;
        setIsDownloading(true);
        switch (mMode) {
            case MODE_SINCRONIZACION:
                try {
                    done = sincronizacionBo.descargarDatos();
                } catch (Exception e) {
                    done = false;
                    error = formatearErrorDescarga(e.getMessage());
                }

                break;

            case MODE_ENVIO:
                try {
                    done = sincronizacionBo.enviarDatos();
                    if (!done) {
                        error = ErrorManager.ErrorEnvioDatosServidor;
                    }
                } catch (Exception e) {
                    done = false;
                    error = formatearErrorDescarga(e.getMessage());
                }

                break;
        }
        setIsDownloading(false);
        result = (done) ? RESULT_OK : RESULT_ERROR;
        return result;
    }

    public String getMessageError() {
        return error;
    }

    private String formatearErrorDescarga(String message) {
        if (!message.equals(ErrorManager.ErrorAccesoSdCard)
                && !message.equals(ErrorManager.ErrorConexionDatos)
                && !message.equals(ErrorManager.ErrorConexionServidor)
                && !message.equals(ErrorManager.ErrorTarjetaSDLlena)
                && !message.equals(ErrorManager.ErrorPedidosDeshabilitados)
                && !message.equals(ErrorManager.ErrorVendedorNoValido)) {
            return ErrorManager.ErrorVinculoConServidor;
        } else {
            return message;
        }
    }

}
