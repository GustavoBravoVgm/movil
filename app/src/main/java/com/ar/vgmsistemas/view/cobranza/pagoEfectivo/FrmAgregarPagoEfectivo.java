package com.ar.vgmsistemas.view.cobranza.pagoEfectivo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.TipoMonedaBo;
import com.ar.vgmsistemas.entity.PagoEfectivo;
import com.ar.vgmsistemas.entity.TipoMoneda;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.AlertDialog;
import com.ar.vgmsistemas.view.cobranza.BaseFrmAgregar;
import com.ar.vgmsistemas.view.cobranza.FrmPagos;

import java.util.List;

public class FrmAgregarPagoEfectivo extends BaseFrmAgregar {

    private static final String TAG = FrmAgregarPagoEfectivo.class.getCanonicalName();
    private CheckBox chbTotal;
    private EditText txtImporte;
    private EditText txtImporteFinal;
    private Spinner cmbTipoMoneda;
    private EditText txtCotizacion;
    private Button btnAceptar;
    private Button btnCancelar;
    private TipoMonedaBo tipoMonedaBo;
    private PagoEfectivo _entregaMoneda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setTitle(R.string.agregar_efectivo);
        setContentView(R.layout.lyt_moneda_agregar);
        getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        Object data = getLastNonConfigurationInstance();
        if (data != null) {
            this._entregaMoneda = (PagoEfectivo) data;
        } else {
            this._entregaMoneda = new PagoEfectivo();
        }
        initComponents();
        initVar();


    }

    @Override
    public void initComponents() {
        super.initComponents();
        //cmbTipoMoneda
        this.cmbTipoMoneda = (Spinner) findViewById(R.id.cmbTipoMoneda);
        this.cmbTipoMoneda.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
                cmbTipoMonedaOnItemSelected(adapter, view, position, id);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //txtCotizacion
        this.txtCotizacion = (EditText) findViewById(R.id.txtCotizacion);

        //txtMonto
        this.txtImporte = (EditText) findViewById(R.id.txtImporte);
        setControlEditText(txtImporte, new ControlMontoListener() {

            @Override
            public void dataOk() {
                btnAceptar.setEnabled(true);
                calcularTotal();

            }

            @Override
            public void dataInvalid() {
                btnAceptar.setEnabled(false);

            }
        });

        //txtMontoMonedaCorriente
        this.txtImporteFinal = (EditText) findViewById(R.id.txtImporteFinal);

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
                btnCancelarOnClick();
            }
        });
        this.chbTotal = (CheckBox) findViewById(R.id.chbTotal);
        this.chbTotal.setOnCheckedChangeListener(chkTotalOnCheckedChangeListener());
    }

    private CompoundButton.OnCheckedChangeListener chkTotalOnCheckedChangeListener() {
        return new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.getId() == R.id.chbTotal) {
                    if (isChecked) {
                        txtImporte.setText(String.valueOf(getMontoMaximo() / Double.parseDouble(txtCotizacion.getText().toString())));
                        calcularTotal();
                        cmbTipoMoneda.setEnabled(false);
                        txtCotizacion.setEnabled(false);
                        txtImporte.setEnabled(false);
                        txtImporteFinal.setEnabled(false);

                    } else {
                        txtImporte.setText("0");
                        calcularTotal();
                        cmbTipoMoneda.setEnabled(true);
                        txtCotizacion.setEnabled(true);
                        txtImporte.setEnabled(true);
                        txtImporteFinal.setEnabled(true);

                    }
                }

            }
        };
    }


    private void initVar() {
        RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(getApplicationContext(), RepositoryFactory.ROOM);
        tipoMonedaBo = new TipoMonedaBo(repoFactory);
        try {
            List<TipoMoneda> tiposMonedas = tipoMonedaBo.recoveryAll();
            ArrayAdapter<TipoMoneda> adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, tiposMonedas);
            cmbTipoMoneda.setAdapter(adapter);
        } catch (Exception ex) {
            ErrorManager.manageException(TAG, "initVar", ex, this);
        }
    }

    private void cmbTipoMonedaOnItemSelected(AdapterView<?> adapter, View view, int position, long id) {
        TipoMoneda tipoMoneda = (TipoMoneda) adapter.getSelectedItem();
        _entregaMoneda.setTipoMoneda(tipoMoneda);
        this.setCotizacion(tipoMoneda.getCotizacion());

    }

    protected void setCotizacion(double cotizacion) {
        txtCotizacion.setText(String.valueOf(cotizacion));
        calcularTotal();
    }

    private void calcularTotal() {
        double monto = 0;
        double cotizacion = 0;
        String sCotizacion = this.txtCotizacion.getText().toString();
        String sMonto = txtImporte.getText().toString();
        try {
            cotizacion = Double.parseDouble(sCotizacion);
            monto = Double.parseDouble(sMonto);
        } catch (NumberFormatException ex) {
            monto = 0;
            cotizacion = 1;
        } finally {
            double montoMonedaCorriente = monto * cotizacion;
            txtImporteFinal.setText(Formatter.formatMoney(montoMonedaCorriente));
        }
    }

    private void btnAceptarOnClick() {
        if (validarDatos()) {
            double importe = Double.parseDouble(this.txtImporte.getText().toString());
            double cotizacion = this._entregaMoneda.getTipoMoneda().getCotizacion();
            double total = cotizacion * importe;
            this._entregaMoneda.setImporteMoneda(importe);
            this._entregaMoneda.setImporteMonedaBase(total);
            this._entregaMoneda.setCotizacion(cotizacion);
            Intent intent = new Intent(this, FrmPagos.class);
            intent.putExtra("entrega", this._entregaMoneda);
            setResult(RESULT_OK, intent);
            this.finish();
        }
    }

    private boolean validarDatos() {
        boolean valido = false;
        String sMonto = this.txtImporte.getText().toString();
        if (sMonto == null || sMonto.trim().length() == 0 || Double.parseDouble(sMonto) <= 0) {
            AlertDialog alert = new AlertDialog(this, getString(R.string.tituloError), ErrorManager.ImporteInvalido);
            alert.show();
        } else {
            valido = true;
        }
        return valido;
    }

    private void btnCancelarOnClick() {
        setResult(RESULT_CANCELED);
        this.finish();
    }

    @Override
    public Object getLastNonConfigurationInstance() {
        // TODO Auto-generated method stub
        return this._entregaMoneda;
    }


}
