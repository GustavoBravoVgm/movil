package com.ar.vgmsistemas.view.cobranza;

import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.ReciboBo;
import com.ar.vgmsistemas.bo.VendedorBo;
import com.ar.vgmsistemas.entity.Recibo;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.Formatter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CabeceraReciboFragment extends Fragment {
    private static final int DATE_DIALOG = 0;
    private static final int DIALOG_ERROR_COBRANZA_NO_DISPONIBLE = 1;
    private static final String TAG = CabeceraReciboFragment.class.getCanonicalName();

    private Recibo _recibo;
    private Spinner cmbPuntoVenta;
    private EditText txtCliente;
    private EditText txtNumero;
    private EditText txtFecha;
    private EditText txtObservacion;
    private FrmRecibo mFrmRecibo;

    private RepositoryFactory _repoFactory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        _recibo = (Recibo) b.getSerializable(FrmRecibo.EXTRA_RECIBO);
        mFrmRecibo = (FrmRecibo) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lyt_recibo_cabecera, null);
        initComponents(view);
        initValues();
        return view;
    }

    private void initComponents(View view) {
        //cmbPuntoVenta
        this.cmbPuntoVenta = (Spinner) view.findViewById(R.id.cmbPuntVenta);
        this.cmbPuntoVenta.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cmbPuntoVentaOnItemSelected(parent, view, position, id);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //txtNumero
        this.txtNumero = (EditText) view.findViewById(R.id.txtNumero);
        this.txtNumero.setKeyListener(null);

        //txtCliente
        this.txtCliente = (EditText) view.findViewById(R.id.txtCliente);

        //txtFecha
        this.txtFecha = (EditText) view.findViewById(R.id.txtFecha);
        txtFecha.setKeyListener(null);

        //txtObservacion
        this.txtObservacion = (EditText) view.findViewById(R.id.txtObservacion);
    }

    private void cmbPuntoVentaOnItemSelected(AdapterView<?> adapter, View view, int position, long id) {
        int idPuntoVenta = (Integer) adapter.getItemAtPosition(position);
        this._recibo.getId().setIdPuntoVenta(idPuntoVenta);
        actualizarNumeroRecibo();
        determinarTipoImpresionRecibo();
    }

    private void actualizarNumeroRecibo() {
        long numeroRecibo = -1;
        ReciboBo reciboBo = new ReciboBo(_repoFactory);
        try {
            numeroRecibo = reciboBo.getSiguienteNumeroRecibo(this._recibo.getId().getIdPuntoVenta());
            this._recibo.getId().setIdRecibo(numeroRecibo);
            this.txtNumero.setText(String.valueOf(numeroRecibo));
        } catch (Exception ex) {
            ErrorManager.manageException(TAG, "actualizarNumeroRecibo", ex);
        }
    }

    private void initValues() {
        this._repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        this.txtCliente.setText(this._recibo.getCliente().getRazonSocial());
        this.txtNumero.setText(String.valueOf(this._recibo.getId().getIdRecibo()));
        actualizarFecha();
        loadPuntosVenta();
        determinarTipoImpresionRecibo();
    }

    private void loadPuntosVenta() {
        ReciboBo reciboBo = new ReciboBo(_repoFactory);
        try {
            List<Integer> puntosVenta = reciboBo.getPuntosVenta(VendedorBo.getVendedor(), _recibo.getCliente().getId().getIdSucursal());
            if (puntosVenta.size() > 0) {
                ArrayAdapter<Integer> adapterPuntoVenta = new ArrayAdapter<>(mFrmRecibo, R.layout.simple_spinner_item, puntosVenta);
                cmbPuntoVenta.setAdapter(adapterPuntoVenta);
            } else {
                showDialog(DIALOG_ERROR_COBRANZA_NO_DISPONIBLE);
            }
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "loadPuntosVenta", e, mFrmRecibo);
        }
    }

    private void actualizarFecha() {
        String fecha = Formatter.formatDate(this._recibo.getFechaMovil());
        this.txtFecha.setText(fecha);
    }

    protected void showDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case DIALOG_ERROR_COBRANZA_NO_DISPONIBLE:
                Builder builder = new Builder(mFrmRecibo);
                builder.setTitle(R.string.tituloError);
                builder.setMessage(ErrorManager.ErrorSinCobranza);
                builder.setPositiveButton(R.string.btnAceptar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mFrmRecibo.finish();
                    }
                });
                dialog = builder.create();
                break;
            case DATE_DIALOG:
                Calendar cal = Calendar.getInstance();
                cal.setTime(this._recibo.getFechaMovil());

                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                dialog = new DatePickerDialog(mFrmRecibo, new OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        txtFechaOnDateSet(view, year, monthOfYear, dayOfMonth);
                    }
                }, year, month, day);
                break;
        }
        dialog.show();
    }

    public void txtFechaOnDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);

        Date fecha = cal.getTime();
        this._recibo.setFechaMovil(fecha);
        actualizarFecha();
    }

    private void determinarTipoImpresionRecibo() {
        ReciboBo recBo = new ReciboBo(_repoFactory);
        try {
            //busco si se imprime o no el recibo si es = 0 no se imprime
            int tipoImpresionRC = recBo.tipoImpresionRecibo(_recibo.getId().getIdPuntoVenta());
            _recibo.setTipoImpresionRecibo(tipoImpresionRC);
        } catch (Exception ex1) {
            ex1.printStackTrace();
            ErrorManager.manageException(TAG, "actualizarTipoImpresionRecibo", ex1);
        }
    }

    @Override
    public void onPause() {
        _recibo.setObservacion(txtObservacion.getText().toString());
        super.onPause();
    }
}
