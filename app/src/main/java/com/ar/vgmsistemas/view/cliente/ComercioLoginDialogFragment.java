package com.ar.vgmsistemas.view.cliente;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog.Builder;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.MotivoAutorizacion;
import com.ar.vgmsistemas.view.dialogs.BaseDialogFragment;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class ComercioLoginDialogFragment extends BaseDialogFragment {
    private List<MotivoAutorizacion> listMotivos;
    protected ComercioLoginListener listener;
    private static String tiAutorizacionMotivo;

    public static ComercioLoginDialogFragment newInstance(ComercioLoginListener listener) {
        ComercioLoginDialogFragment dialogFragment = new ComercioLoginDialogFragment();
        dialogFragment.listener = listener;
        return dialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Builder builder = new Builder(getContext());
        View view = View.inflate(getContext(), R.layout.lyt_dialog_comercio_login, null);
        final EditText clave = (EditText) view.findViewById(R.id.etClave);
        builder.setView(view);
        builder.setPositiveButton("Aceptar", new OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                try {
                    try {
                        listener.onAccept(clave.getText().toString());
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        });
        builder.setNegativeButton("Cancelar", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();

            }
        });
        return builder.show();
    }

    public interface ComercioLoginListener {
        void onAccept(String comercioLog) throws NoSuchAlgorithmException, UnsupportedEncodingException;
    }
}
