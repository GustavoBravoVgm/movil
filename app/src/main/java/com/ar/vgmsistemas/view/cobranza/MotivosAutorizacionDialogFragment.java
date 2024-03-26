package com.ar.vgmsistemas.view.cobranza;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog.Builder;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.MotivoAutorizacionBo;
import com.ar.vgmsistemas.entity.MotivoAutorizacion;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.view.dialogs.BaseDialogFragment;

import java.util.List;

public class MotivosAutorizacionDialogFragment extends BaseDialogFragment {
    private List<MotivoAutorizacion> listMotivos;
    protected MotivoAutorizacionListener listener;
    private static String tiAutorizacionMotivo;

    public static MotivosAutorizacionDialogFragment newInstance(String tiAutorizacion, MotivoAutorizacionListener listener) {
        MotivosAutorizacionDialogFragment dialogFragment = new MotivosAutorizacionDialogFragment();
        dialogFragment.listener = listener;
        tiAutorizacionMotivo = tiAutorizacion;
        return dialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        MotivoAutorizacionBo autorizacionBo = new MotivoAutorizacionBo(repoFactory);
        try {
            if (tiAutorizacionMotivo.equals(MotivoAutorizacion.TI_AUTORIZACION_CUENTA_CORRIENTE) || tiAutorizacionMotivo.equals(MotivoAutorizacion.TI_AUTORIZACION_CUENTA_CORRIENTE_REPARTO)) {
                listMotivos = autorizacionBo.recoveryForCtaCte();
            } else if (tiAutorizacionMotivo.equals(MotivoAutorizacion.TI_AUTORIZACION_PEDIDO_RENTABLE)) {
                listMotivos = autorizacionBo.recoveryForPedidoRentable();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Builder builder = new Builder(getContext());
        View view = View.inflate(getContext(), R.layout.lyt_dialog_motivo_autorizacion, null);

        final Spinner spinner = (Spinner) view.findViewById(R.id.spMotivos);
        ArrayAdapter<MotivoAutorizacion> adapter = new ArrayAdapter<MotivoAutorizacion>(getContext(), android.R.layout.simple_list_item_1, listMotivos);

        spinner.setAdapter(adapter);
        final EditText observacion = (EditText) view.findViewById(R.id.etObservacion);
        if (tiAutorizacionMotivo.equals(MotivoAutorizacion.TI_AUTORIZACION_CUENTA_CORRIENTE)) {
            observacion.setVisibility(View.VISIBLE);
        } else if (tiAutorizacionMotivo.equals(MotivoAutorizacion.TI_AUTORIZACION_PEDIDO_RENTABLE) || tiAutorizacionMotivo.equals(MotivoAutorizacion.TI_AUTORIZACION_CUENTA_CORRIENTE_REPARTO)) {
            observacion.setVisibility(View.GONE);
        }

        builder.setView(view);
        builder.setPositiveButton("Aceptar", new OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                listener.onAccept((MotivoAutorizacion) spinner.getSelectedItem(), observacion.getText().toString());

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

    public interface MotivoAutorizacionListener {
        void onAccept(MotivoAutorizacion autorizacion, String observacion);
//		void onCancel();
    }
}
