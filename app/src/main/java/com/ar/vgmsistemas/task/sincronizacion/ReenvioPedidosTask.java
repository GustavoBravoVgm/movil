package com.ar.vgmsistemas.task.sincronizacion;

import android.os.AsyncTask;

import com.ar.vgmsistemas.bo.MovimientoBo;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.view.sincronizacion.FrmReenvioPedidos;

public class ReenvioPedidosTask extends AsyncTask<Void, Void, Void> {
    FrmReenvioPedidos activity = null;
    boolean done = false;
    String error;

    public ReenvioPedidosTask(FrmReenvioPedidos activity) {
        attach(activity);
    }

    @Override
    protected Void doInBackground(Void... params) {
        RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(activity.getApplicationContext(), RepositoryFactory.ROOM);
        MovimientoBo movimientoBo = new MovimientoBo(repoFactory);
        try {
            movimientoBo.reenvioPedidos();
            done = true;
        } catch (Exception e) {
            done = false;
            error = e.getMessage();
        }
        return (null);
    }

    @Override
    protected void onPostExecute(Void result) {
        if (activity != null)
            if (done) {
                activity.markAsDone();
            } else {
                activity.markAsError(error);
            }
        activity = null;
    }

    public void attach(FrmReenvioPedidos activity) {
        this.activity = activity;
    }
}
