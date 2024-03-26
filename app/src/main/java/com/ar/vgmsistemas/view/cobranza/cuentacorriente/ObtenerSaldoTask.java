package com.ar.vgmsistemas.view.cobranza.cuentacorriente;

import android.content.Context;
import android.os.AsyncTask;

import com.ar.vgmsistemas.bo.ClienteBo;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.List;

public class ObtenerSaldoTask extends AsyncTask<Void, Void, Void> {
    private ObtenerSaldoListener mListener;
    private List<Cliente> _mClientes;
    private ClienteBo mClienteBo;
    String error;
    private boolean isError;

    public ObtenerSaldoTask(ObtenerSaldoListener listener, Context context) {
        mListener = listener;
        RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(context, RepositoryFactory.ROOM);
        mClienteBo = new ClienteBo(repoFactory);
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            _mClientes = mClienteBo.recoverySaldoClientes();
            isError = false;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            error = "Error: " + e;
            isError = true;
            e.printStackTrace();
        }
        return (null);
    }

    @Override
    protected void onProgressUpdate(Void... unused) {
    }

    @Override
    protected void onPostExecute(Void unused) {
        if (mListener != null) {
            if (isError) {
                mListener.onError(error);
            } else {
                mListener.onDone(_mClientes);
            }
        }
    }

    public interface ObtenerSaldoListener {
        void onDone(List<Cliente> mClientes);

        void onError(String error);
    }
}
