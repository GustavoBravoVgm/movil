package com.ar.vgmsistemas.view.reparto.hojas.detalles.Multiples;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatDialog;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.ArticuloBo;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.dialogs.BaseDialogFragment;

public class DevolucionMultipleDialogFragment extends BaseDialogFragment {
    public static final String EXTRA_VENTA_DETALLE = "extraVentaDetalle";
    private DevolucionMultipleListener mListener;
    private VentaDetalle mVentaDetalle;
    private EditText etBultos;
    private EditText etUnidades;
    private TextView tvTotalDevuelto;
    private AlertDialog appCompatDialog;
    private TextWatcher watcher;

    public static DevolucionMultipleDialogFragment newInstance(VentaDetalle ventaDetalle, DevolucionMultipleListener listener) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_VENTA_DETALLE, ventaDetalle);


        DevolucionMultipleDialogFragment devolucionDialogFragment = new DevolucionMultipleDialogFragment();
        devolucionDialogFragment.setArguments(bundle);
        devolucionDialogFragment.mListener = listener;

        return devolucionDialogFragment;
    }

    @Override
    public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {
        Builder builder = new Builder(getActivity());
        mVentaDetalle = (VentaDetalle) getArguments().getSerializable(EXTRA_VENTA_DETALLE);
        final View view = View.inflate(getActivity(), R.layout.layout_devolucion_dialog, null);
        String stBultos = (mVentaDetalle.getBultosDevueltos() == 0) ? "" : String.valueOf(mVentaDetalle.getBultosDevueltos());
        String stUnidades = (mVentaDetalle.getUnidadesDevueltas() == 0) ? "" : String.valueOf(mVentaDetalle.getUnidadesDevueltas());

        etBultos = (EditText) view.findViewById(R.id.etBultos);
        etUnidades = (EditText) view.findViewById(R.id.etUnidades);
        final CheckBox cbDevolverTodo = (CheckBox) view.findViewById(R.id.cbDevolverTodo);
        ((TextView) view.findViewById(R.id.tvCantidadTotal)).setText(String.valueOf(mVentaDetalle.getCantidad()));
        tvTotalDevuelto = (TextView) view.findViewById(R.id.tvTotalDevuelto);
        etBultos.setText(stBultos);
        etUnidades.setText(stUnidades);
        watcher = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cbDevolverTodo.setChecked(false);
                updateTotal();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        };

        etUnidades.addTextChangedListener(watcher);
        etBultos.addTextChangedListener(watcher);

        ((TextView) view.findViewById(R.id.tvUnidadesActuales)).setText(String.valueOf(mVentaDetalle.getUnidades()));
        ((TextView) view.findViewById(R.id.tvBultosActuales)).setText(String.valueOf(mVentaDetalle.getBultos()));
        ((TextView) view.findViewById(R.id.tvUxB)).setText(String.valueOf(mVentaDetalle.getArticulo().getUnidadPorBulto()));


        cbDevolverTodo.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etBultos.removeTextChangedListener(watcher);
                    etUnidades.removeTextChangedListener(watcher);

                    etBultos.setText(String.valueOf(mVentaDetalle.getBultos()));
                    etUnidades.setText(Double.toString(mVentaDetalle.getUnidades()));
                    mVentaDetalle.setUnidadesDevueltas(mVentaDetalle.getUnidades());
                    mVentaDetalle.setBultosDevueltos(mVentaDetalle.getBultos());

                    etBultos.addTextChangedListener(watcher);
                    etUnidades.addTextChangedListener(watcher);

                    tvTotalDevuelto.setText(String.valueOf(mVentaDetalle.getCantidadDevuelta()));
                    checkPossitiveButton();
                }

            }
        });


        builder.setPositiveButton("Aceptar", new OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                mListener.onAccepted(getBultos(), getUnidades());

            }
        });
        builder.setNegativeButton("Cancelar", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onCloseDialog();
            }
        });

        //KeyboardUtils.showKeyboard(etBultos, getActivity());

        builder.setTitle("Devolucion");
        builder.setView(view);

        appCompatDialog = builder.show();
        appCompatDialog.setCancelable(false);

        tvTotalDevuelto.setText(String.valueOf(mVentaDetalle.getCantidadDevuelta()));
        checkPossitiveButton();

        return appCompatDialog;
    }

    private void checkPossitiveButton() {
        if (mVentaDetalle.getCantidad() >= ArticuloBo.getCantidad(mVentaDetalle.getArticulo().getUnidadPorBulto(), getBultos(), getUnidades())
                && (getBultos() > 0 || getUnidades() > 0)) {
            appCompatDialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(true);
        } else {
            appCompatDialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(false);
        }
    }

    private void updateTotal() {
        tvTotalDevuelto.setText(String.valueOf(ArticuloBo.getCantidad(mVentaDetalle.getArticulo().getUnidadPorBulto(), getBultos(), getUnidades())));
        checkPossitiveButton();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mListener.onCloseDialog();
    }

    public interface DevolucionMultipleListener {
        void onAccepted(int bultosDev, double unidDev);

        void onCloseDialog();
    }

    private int getBultos() {
        return Formatter.parseInt(etBultos.getText().toString());
    }

    private double getUnidades() {
        return Formatter.parseDouble(etUnidades.getText().toString());
    }
}
