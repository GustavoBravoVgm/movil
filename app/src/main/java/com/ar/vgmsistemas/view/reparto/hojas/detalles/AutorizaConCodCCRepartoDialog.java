package com.ar.vgmsistemas.view.reparto.hojas.detalles;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatDialog;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.HojaDetalleBo;
import com.ar.vgmsistemas.entity.HojaDetalle;
import com.ar.vgmsistemas.view.dialogs.BaseDialogFragment;

public class AutorizaConCodCCRepartoDialog extends BaseDialogFragment {
    AutorizacionConCodListener mListener;
    private HojaDetalle mHojaDetalle;
    private EditText mEditTextCodAutCCReparto;

    public static AutorizaConCodCCRepartoDialog newInstance(AutorizacionConCodListener listener, HojaDetalle hojaDet) {
        AutorizaConCodCCRepartoDialog dialog = new AutorizaConCodCCRepartoDialog();
        dialog.mListener = listener;
        dialog.mHojaDetalle = hojaDet;
        return dialog;
    }

    @Override
    public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {
        Builder builder = new Builder(getActivity());

        builder.setTitle("Autorizacion");

        View view = View.inflate(getActivity(), R.layout.lyt_spinner_dialog, null);
        Spinner mSpinner = view.findViewById(R.id.spnDialog);//(Spinner)

        mEditTextCodAutCCReparto = view.findViewById(R.id.txtCodigoAutorizaCCReparto);//(EditText)
        /*determino visibilidad*/
        mSpinner.setVisibility(View.INVISIBLE);
        builder.setMessage(
                "La condicion de venta de la factura no permite que se genere movimiento en la cuenta corriente del cliente, es necesario ingresar el codigo de autorizacion proveído por la empresa para realizar la operacion.");
        builder.setView(view);


        builder.setPositiveButton("Autorizar", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.autorizacionAccepted(mEditTextCodAutCCReparto.getText());
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

    @Override
    public void onStart() {
        super.onStart();
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (HojaDetalleBo.ctrlCodCuentaCorriente(mHojaDetalle, mEditTextCodAutCCReparto.getText().toString())) {
                            mListener.autorizacionAccepted(mEditTextCodAutCCReparto.getText().toString());
                            dismiss();
                        } else {
                            mEditTextCodAutCCReparto.setError("Codigo incorrecto");
                        }
                    }
                });

    }

    public interface AutorizacionConCodListener {
        void autorizacionAccepted(Object o);
    }
}
