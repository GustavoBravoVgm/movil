package com.ar.vgmsistemas.view.sincronizacion;


import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.task.sincronizacion.ReenvioPedidosTask;
import com.ar.vgmsistemas.view.BaseActivity;

public class FrmReenvioPedidos extends BaseActivity {

    private ProgressDialog progressDialog;
    private ReenvioPedidosTask task;
    private static final int PROGRESS_DIALOG = 0;
    private static final int ERROR_DIALOG = 1;
    String error;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_reenvio_pedidos);
        updateMovimientos();
    }


    protected void updateMovimientos() {
        showDialog(PROGRESS_DIALOG);
        this.task = new ReenvioPedidosTask(this);
        this.task.execute((Void) null);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case PROGRESS_DIALOG:
                dialog = getProgressDialog();
                break;
            case ERROR_DIALOG:
                dialog = getErrorDialog();
                break;
        }
        return dialog;
    }

    private Dialog getProgressDialog() {
        String titulo = this.getString(R.string.msjReenvioPedidos);
        String mensaje = this.getString(R.string.actualizandoMovimientos);

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle(titulo);
        progressDialog.setMessage(mensaje);
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    private Dialog getErrorDialog() {
        Builder builder = new Builder(this);
        builder.setTitle(R.string.tituloError);
        builder.setMessage(error);
        builder.setPositiveButton(R.string.btnAceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }

    public void markAsDone() {
        dismissDialog(PROGRESS_DIALOG);

        // Mostrar un dialog

        Toast.makeText(
                getApplicationContext(),
                R.string.msjActualizacionExitosa, //cambio karen
                Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, FrmSincronizacion.class);
        startActivity(intent);


        this.finish();

    }

    public void markAsError(String error) {
        this.error = error;
        dismissDialog(PROGRESS_DIALOG);
        showDialog(ERROR_DIALOG);
    }


}
