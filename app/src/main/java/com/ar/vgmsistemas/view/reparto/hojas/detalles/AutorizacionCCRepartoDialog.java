package com.ar.vgmsistemas.view.reparto.hojas.detalles;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatDialog;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.CuentaCorrienteBo;
import com.ar.vgmsistemas.bo.MotivoAutorizacionBo;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.entity.Empresa;
import com.ar.vgmsistemas.entity.MotivoAutorizacion;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.view.dialogs.BaseDialogFragment;

import java.util.List;

public class AutorizacionCCRepartoDialog extends BaseDialogFragment {
    AutorizacionListener mListener;
    private EditText mEditText;
    private Spinner mSpinner;
    private List<MotivoAutorizacion> listMotivos;

    public static AutorizacionCCRepartoDialog newInstance(AutorizacionListener listener) {
        AutorizacionCCRepartoDialog dialog = new AutorizacionCCRepartoDialog();
        dialog.mListener = listener;
        return dialog;
    }

    @Override
    public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {
        Builder builder = new Builder(getActivity());

        builder.setTitle("Autorizacion");
        if (PreferenciaBo.getInstance().getPreferencia().getIsCobranzaEstricta().equals(Empresa.SI)) {
            mEditText = new EditText(getActivity());
            // Specify the type of input expected; this, for example, sets the input
            // as a password, and will mask the text
            mEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
            mEditText.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    mEditText.setError(null);

                }
            });
            builder.setView(mEditText);
            builder.setMessage(
                    "La condicion de venta de la factura no permite que se genere movimiento en la cuenta corriente del cliente, es necesario ingresar un codigo de autorizacion para realizar la operacion.");
        } else {
            RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
            MotivoAutorizacionBo autorizacionBo = new MotivoAutorizacionBo(repoFactory);
            View view = View.inflate(getActivity(), R.layout.lyt_spinner_dialog, null);
            mSpinner = view.findViewById(R.id.spnDialog);//(Spinner)

            /*No hago Visible lo de cod autorizacion por comercio*/
            TextView mTextCodAutCCReparto = view.findViewById(R.id.tvMsgCodigoAutorizaCCReparto);//(TextView)
            EditText mEditTextCodAutCCReparto = view.findViewById(R.id.txtCodigoAutorizaCCReparto);//(EditText)
            mTextCodAutCCReparto.setVisibility(View.INVISIBLE);
            mEditTextCodAutCCReparto.setVisibility(View.INVISIBLE);
            try {
                listMotivos = autorizacionBo.recoveryForCtaCte();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ArrayAdapter<MotivoAutorizacion> adapter = new ArrayAdapter<MotivoAutorizacion>(getContext(), android.R.layout.simple_spinner_item, listMotivos) {
                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    if (position % 2 == 0) { // we're on an even row
                        view.setBackgroundColor(getResources().getColor(R.color.white));
                    } else {
                        view.setBackgroundColor(getResources().getColor(R.color.background_gray));
                    }
                    return view;
                }
            };
            mSpinner.setAdapter(adapter);
            builder.setMessage(
                    "La condicion de venta de la factura no permite que se genere movimiento en la cuenta corriente del cliente, es necesario ingresar un motivo de autorizacion para realizar la operacion.");
            builder.setView(view);
        }


        builder.setPositiveButton("Autorizar", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.autorizacionAccepted(listMotivos.get(which));
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
                        if (PreferenciaBo.getInstance().getPreferencia().getIsCobranzaEstricta().equals(Empresa.SI)) {
                            RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
                            CuentaCorrienteBo cuentaCorrienteBo = new CuentaCorrienteBo(repoFactory);
                            if (cuentaCorrienteBo.validarCodigoAutorizacion(mEditText.getText().toString())) {
                                mListener.autorizacionAccepted(mEditText.getText().toString());
                                dismiss();
                            }
                            mEditText.setError("Codigo incorrecto");
                        } else {
                            mListener.autorizacionAccepted(mSpinner.getSelectedItem());
                        }
                    }
                });

    }

    public interface AutorizacionListener {
        void autorizacionAccepted(Object o);
    }
}
