package com.ar.vgmsistemas.view.cobranza.retencion;

import static com.ar.vgmsistemas.view.informes.FrmListadoVenta.EXTRA_CLIENTE;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.DocumentoBo;
import com.ar.vgmsistemas.bo.PlanCuentaBo;
import com.ar.vgmsistemas.bo.RetencionBo;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.Documento;
import com.ar.vgmsistemas.entity.PlanCuenta;
import com.ar.vgmsistemas.entity.Retencion;
import com.ar.vgmsistemas.entity.key.PkDocumento;
import com.ar.vgmsistemas.entity.key.PkVenta;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.AlertDialog;
import com.ar.vgmsistemas.view.cobranza.BaseFrmAgregar;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FrmAgregarRetencion extends BaseFrmAgregar {

    private static final String TAG = FrmAgregarRetencion.class.getCanonicalName();
    private static final int DATE_DIALOG = 0;

    private Spinner cmbTipoDocumento;
    private EditText txtPuntoVenta;
    private EditText txtNumero;
    private EditText txtFecha;
    private Spinner cmbCuenta;
    private EditText txtObservacion;
    private EditText txtImporte;
    private Button btnAceptar;
    private Button btnCancelar;
    private Retencion _retencion;
    //BO´s
    private DocumentoBo documentoBo;
    private PlanCuentaBo planCuentaBo;

    private Documento documento;
    private Cliente cliente;

    private RepositoryFactory _repoFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        cliente = (Cliente) bundle.getSerializable(EXTRA_CLIENTE);

        setTitle(R.string.agregar_retencion);
        setContentView(R.layout.lyt_retencion_agregar);
        getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);

        Object data = getLastNonConfigurationInstance();
        if (data != null) {
            this._retencion = (Retencion) data;
        } else {
            this._retencion = new Retencion();
            this._retencion.setFechaMovil(Calendar.getInstance().getTime());
        }

        initComponents();
        initVar();
    }

    @Override
    public void initComponents() {
        super.initComponents();
        //cmbTipoDocumento
        this.cmbTipoDocumento = (Spinner) findViewById(R.id.cmbTipoDocumento);
        this.cmbTipoDocumento.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
                cmbTipoDocumentoOnItemSelected(adapter, view, position, id);
            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        //txtPuntoVenta
        this.txtPuntoVenta = (EditText) findViewById(R.id.txtPuntoVenta);
        this.txtPuntoVenta.setKeyListener(null);

        //txtNumero
        this.txtNumero = (EditText) findViewById(R.id.txtNumero);

        //txtFecha
        this.txtFecha = (EditText) findViewById(R.id.txtFecha);
        this.txtFecha.setKeyListener(null);
        this.txtFecha.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG);
            }
        });

        //cmbCuenta
        this.cmbCuenta = (Spinner) findViewById(R.id.cmbCuenta);
        this.cmbCuenta.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
                PlanCuenta planCuenta = (PlanCuenta) adapter.getItemAtPosition(position);
                _retencion.setPlanCuenta(planCuenta);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //txtObservacion
        this.txtObservacion = (EditText) findViewById(R.id.txtObservacion);

        //txtMonto
        this.txtImporte = (EditText) findViewById(R.id.txtImporte);
        setControlEditText(txtImporte, new ControlMontoListener() {

            @Override
            public void dataOk() {
                btnAceptar.setEnabled(true);

            }

            @Override
            public void dataInvalid() {
                btnAceptar.setEnabled(false);

            }
        });

        //btnAceptar
        this.btnAceptar = (Button) findViewById(R.id.btnAceptar);
        this.btnAceptar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btnAceptarOnClick();
            }
        });

        //btnCancelar
        this.btnCancelar = (Button) findViewById(R.id.btnCancelar);
        this.btnCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    private void initVar() {
        this._repoFactory = RepositoryFactory.getRepositoryFactory(getApplicationContext(), RepositoryFactory.ROOM);
        planCuentaBo = new PlanCuentaBo(_repoFactory);
        documentoBo = new DocumentoBo(_repoFactory);
        documento = new Documento();
        documento.setId(new PkDocumento());
        documento.getId().setIdLetra("X");

        actualizarFecha();

        try {
            List<String> documentos = documentoBo.recoveryTiposDocumentos(Documento.RETENCION);
            ArrayAdapter<String> adapterDocumento = new ArrayAdapter<String>(this, R.layout.simple_spinner_item, documentos);
            cmbTipoDocumento.setAdapter(adapterDocumento);
        } catch (Exception ex) {
            ErrorManager.manageException(TAG, "initVar", ex, this);
        }
    }

    private void cmbTipoDocumentoOnItemSelected(AdapterView<?> adapter, View view, int position, long id) {
        String tipoDocumento = adapter.getSelectedItem().toString();
        try {
            List<Integer> puntosVenta = documentoBo.recoveryPuntosVenta(tipoDocumento);
            Integer puntoVenta = 0;
            if (puntosVenta.size() > 0) {
                puntoVenta = puntosVenta.get(0);
            }
            txtPuntoVenta.setText(puntoVenta.toString());
            documento.getId().setIdDocumento(tipoDocumento);
            documento.getId().setPuntoVenta(puntoVenta);

            loadPlanCuenta();
        } catch (Exception ex) {
            ErrorManager.manageException(TAG, "cmbTipoDocumentoOnItemSelected", ex, this);
        }
    }

    private void loadPlanCuenta() {
        try {
            List<PlanCuenta> planesCuenta = planCuentaBo.recoveryByDocumento(documento);
            ArrayAdapter<PlanCuenta> adapterPlanCuenta = new ArrayAdapter<>(this, R.layout.simple_spinner_item, planesCuenta);
            cmbCuenta.setAdapter(adapterPlanCuenta);
        } catch (Exception ex) {
            ErrorManager.manageException(TAG, "loadPlanCuenta", ex, this);
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG: {
                Calendar cal = Calendar.getInstance();
                cal.setTime(this._retencion.getFechaMovil());

                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialogFecha = new DatePickerDialog(this, new OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        txtFechaOnDateSet(view, year, monthOfYear, dayOfMonth);
                    }
                }, year, month, day);
                return dialogFecha;
            }
            default:
                return null;
        }
    }

    public void txtFechaOnDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);

        Date fechaVenta = cal.getTime();
        this._retencion.setFechaMovil(fechaVenta);
        actualizarFecha();
    }

    private void actualizarFecha() {
        String fecha = Formatter.formatDate(this._retencion.getFechaMovil());
        this.txtFecha.setText(fecha);
    }

    private void btnAceptarOnClick() {
        if (validarDatos()) {
            String sPuntoVenta = this.txtPuntoVenta.getText().toString();
            String sFecha = this.txtFecha.getText().toString();
            String observacion = txtObservacion.getText().toString();
            String sNumero = this.txtNumero.getText().toString();
            String sMonto = this.txtImporte.getText().toString();
            try {
                int puntoVenta = Integer.valueOf(sPuntoVenta);
                long numero = Long.valueOf(sNumero);
                float monto = Float.valueOf(sMonto);

                PkVenta id = new PkVenta();
                id.setIdLetra(documento.getId().getIdLetra());
                id.setIdDocumento(documento.getId().getIdDocumento());
                id.setIdNumeroDocumento(numero);
                id.setPuntoVenta(puntoVenta);

                this._retencion.setId(id);
                this._retencion.setImporte(monto);
                this._retencion.setObservacion(observacion);

                Intent intent = new Intent(this, FrmGestionRetencion.class);
                intent.putExtra("retencion", this._retencion);
                setResult(RESULT_OK, intent);
                this.finish();
            } catch (Exception ex) {
                ErrorManager.manageException(TAG, "btnAceptarOnClick", ex, this);
            }
        }
    }

    private boolean validarDatos() {
        boolean valido = false;
        String sPuntoVenta = this.txtPuntoVenta.getText().toString();
        String sNumero = this.txtNumero.getText().toString();
        String sImporte = this.txtImporte.getText().toString();
        RetencionBo retencionBo = new RetencionBo(_repoFactory);

        if (sPuntoVenta == null || sPuntoVenta.trim().length() == 0) {
            AlertDialog alert = new AlertDialog(this, getString(R.string.tituloError), ErrorManager.IngresarPuntoVenta);
            alert.show();
        } else if (sNumero == null || sNumero.trim().length() == 0) {
            AlertDialog alert = new AlertDialog(this, getString(R.string.tituloError), ErrorManager.IngresarNumero);
            alert.show();
        } else if (sImporte == null || sImporte.trim().length() == 0 || Float.parseFloat(sImporte) <= 0) {
            AlertDialog alert = new AlertDialog(this, getString(R.string.tituloError), ErrorManager.ImporteInvalido);
            alert.show();
        } else {
            valido = true;
        }
        return valido;
    }

    @Override
    public Object getLastNonConfigurationInstance() {
        // TODO Auto-generated method stub
        return this._retencion;
    }


}
