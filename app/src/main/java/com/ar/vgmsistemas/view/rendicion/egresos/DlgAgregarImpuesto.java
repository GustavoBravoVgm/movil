package com.ar.vgmsistemas.view.rendicion.egresos;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.ImpuestoBo;
import com.ar.vgmsistemas.bo.RendicionBo;
import com.ar.vgmsistemas.entity.ComprasImpuestos;
import com.ar.vgmsistemas.entity.Impuesto;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.view.dialogs.BaseDialogFragment;

import java.util.List;

public class DlgAgregarImpuesto extends BaseDialogFragment {

    private Spinner cmbImpuestos;
    private EditText etGravado;
    private EditText etImporte;
    private Button btnAceptar;
    private Button btnCancelar;
    private static OkListener mListener;
    TextWatcher textWatcherGravado;
    TextWatcher textWatcherImporte;
    //KeyListener keyListener;

    public static DlgAgregarImpuesto newInstance(OkListener listener) {
        mListener = listener;
        return new DlgAgregarImpuesto();
    }

    @Override
    public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        View view = View.inflate(getActivity(), R.layout.lyt_dialog_add_impuestos, null);
        alertDialogBuilder.setView(view);
        initComponents(view);
        loadImpuestos();
        alertDialogBuilder.setTitle(getString(R.string.agregarImpuesto));
        return alertDialogBuilder.show();
    }

    private void initComponents(View view) {
        // cmbImpuestos

        this.cmbImpuestos = (Spinner) view.findViewById(R.id.cmbImpuestos);
        cmbImpuestos.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (!(cmbImpuestos.getSelectedItem() != null && ((Impuesto) cmbImpuestos.getSelectedItem()).getTaImpuesto() == 0)) {
                    etGravado.setVisibility(View.VISIBLE);
                    Impuesto impuesto = (Impuesto) cmbImpuestos.getSelectedItem();
                    etGravado.setText(RendicionBo.getGravadoFromImporte(Double.parseDouble(etImporte.getText().toString()), impuesto.getTaImpuesto()) + "");
                } else {
                    etGravado.setVisibility(View.INVISIBLE);
                    etGravado.setText(0.0 + "");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        // rbEnviados
        this.etGravado = (EditText) view.findViewById(R.id.idGravado);
        addTextChangeListenerGravado();
        this.etImporte = (EditText) view.findViewById(R.id.idImporte);
        addTextChangeListenerImporte();


        // btnAceptar
        this.btnAceptar = (Button) view.findViewById(R.id.btnAceptar);
        btnAceptar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ComprasImpuestos comprasImpuestos = new ComprasImpuestos();
                comprasImpuestos.setImpuesto((Impuesto) cmbImpuestos.getSelectedItem());
                comprasImpuestos.setPrImpGravado(Double.parseDouble(etGravado.getText().toString()));
                comprasImpuestos.setPrImpuesto(Double.parseDouble(etImporte.getText().toString()));
                comprasImpuestos.setTaImpuesto(((Impuesto) cmbImpuestos.getSelectedItem()).getTaImpuesto());
                if (mListener instanceof OkListener) {
                    ((OkListener) mListener).onOkSelected(comprasImpuestos);
                } else if (mListener == null) {
                    dismiss();
                }
                dismiss();
                btnAceptarOnClick();
            }
        });

        // btnCancelar
        this.btnCancelar = (Button) view.findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                if (mListener instanceof OkListener) {

                }
                btnCancelarOnClick();
            }
        });

    }

    private void addTextChangeListenerGravado() {
        textWatcherGravado = new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                //etImporte.addTextChangedListener(null);
                etImporte.removeTextChangedListener(textWatcherImporte);
                double importeDouble = 0d;
                if (!s.toString().equals(""))
                    importeDouble = Double.parseDouble(s.toString());

                Impuesto impuesto = (Impuesto) cmbImpuestos.getSelectedItem();
                String importe = 0 + "";
                if (impuesto.getTaImpuesto() != 0)
                    importe = RendicionBo.getImporteFromGravado(importeDouble, impuesto.getTaImpuesto()) + "";

                etImporte.setText(importe);
                addTextChangeListenerImporte();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        };

        if (!(cmbImpuestos.getSelectedItem() != null && ((Impuesto) cmbImpuestos.getSelectedItem()).getTaImpuesto() == 0))
            etGravado.addTextChangedListener(textWatcherGravado);
        else
            etGravado.setText(0.0 + "");
    }

    private void addTextChangeListenerImporte() {
        textWatcherImporte = new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                //if(!(((Impuesto)cmbImpuestos.getSelectedItem()).getTaImpuesto() == 0))
                etGravado.removeTextChangedListener(textWatcherGravado);
                double gravadoDouble = 0d;
                if (!s.toString().equals(""))
                    gravadoDouble = Double.parseDouble(s.toString());
                Impuesto impuesto = (Impuesto) cmbImpuestos.getSelectedItem();
                String gravado = 0 + "";
                if (impuesto.getTaImpuesto() != 0)
                    gravado = RendicionBo.getGravadoFromImporte(gravadoDouble, impuesto.getTaImpuesto()) + "";
                etGravado.setText(gravado);
                addTextChangeListenerGravado();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        };
        etImporte.addTextChangedListener(textWatcherImporte);
    }

    protected void btnCancelarOnClick() {
        this.dismiss();
    }

    protected void btnAceptarOnClick() {

        this.dismiss();

    }

    private void loadImpuestos() {

        ImpuestoBo impuestoBo = new ImpuestoBo(RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM));
        List<Impuesto> impuestos = null;
        try {
            impuestos = impuestoBo.recoveryAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayAdapter<Impuesto> adapterImpuestos = new ArrayAdapter<>(getContext(),
                R.layout.simple_spinner_item, impuestos);
        cmbImpuestos.setAdapter(adapterImpuestos);
    }

    public interface OkListener {
        void onOkSelected(ComprasImpuestos ci);
    }
}
