package com.ar.vgmsistemas.view.cobranza.deposito;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.BancoBo;
import com.ar.vgmsistemas.entity.Banco;
import com.ar.vgmsistemas.entity.Deposito;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.AlertDialog;
import com.ar.vgmsistemas.view.cobranza.BaseFrmAgregar;
import com.ar.vgmsistemas.view.cobranza.cheque.BancoAdapter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FrmAgregarDeposito extends BaseFrmAgregar {

    private AutoCompleteTextView acTxtBanco;
    private TextView txtNumeroComprobante;
    private TextView txtFechaDeposito;
    private EditText txtTotal;
    private TextView txtCantidadCheques;
    private Button btnAceptar;
    private Button btnCancelar;
    private BancoBo bancoBo;
    private BancoAdapter adapter;
    private Deposito deposito;
    private static final String TAG = FrmAgregarDeposito.class.getCanonicalName();
    private static final int DATE_DIALOG = 0;

    //BD
    private RepositoryFactory _repoFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_deposito_agregar);
        setTitle(R.string.agregar_deposito);

        initComponents();

        Object data = getLastNonConfigurationInstance();
        if (data != null) {
            deposito = (Deposito) data;
        } else {
            this.deposito = new Deposito();
        }
        _repoFactory = RepositoryFactory.getRepositoryFactory(getApplicationContext(), RepositoryFactory.ROOM);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initVar();
        loadData();
    }

    private void initVar() {
        loadBancos();
    }

    private void loadBancos() {
        bancoBo = new BancoBo(_repoFactory);
        try {
            List<Banco> bancos = bancoBo.recoveryAll();
            this.adapter = new BancoAdapter(this, bancos);
            acTxtBanco.setAdapter(this.adapter);
        } catch (Exception ex) {
            ErrorManager.manageException(TAG, "initVar", ex, this);
        }
    }

    public void acTxtBancoOnItemClick(AdapterView<?> adapter, View view,
                                      int position, long id) {
        Banco bancoSeleccionado = (Banco) adapter.getItemAtPosition(position);
        this.deposito.setBanco(bancoSeleccionado);
        this.txtNumeroComprobante.requestFocus();
    }

    @Override
    public void initComponents() {
        super.initComponents();
        // cmbBanco
        this.acTxtBanco = (AutoCompleteTextView) findViewById(R.id.acTxtBanco);
        this.acTxtBanco.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                acTxtBancoOnItemClick(parent, view, position, id);
            }
        });

        // txtNumeroComprobante
        this.txtNumeroComprobante = (TextView) findViewById(R.id.txtNumeroComprobante);

        // txtFechaVencimiento
        this.txtFechaDeposito = (EditText) findViewById(R.id.txtFechaDeposito);
        this.txtFechaDeposito.setKeyListener(null);
        this.txtFechaDeposito.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG);
            }
        });

        // txtTotal
        this.txtTotal = (EditText) findViewById(R.id.txtTotal);
        setControlEditText(txtTotal, new ControlMontoListener() {

            @Override
            public void dataOk() {
                btnAceptar.setEnabled(true);

            }

            @Override
            public void dataInvalid() {
                btnAceptar.setEnabled(false);

            }
        });

        //txtCantidadCheques
        this.txtCantidadCheques = (TextView) findViewById(R.id.txtCantidadCheques);

        // btnAceptar
        this.btnAceptar = (Button) findViewById(R.id.btnAgregarDeposito);
        this.btnAceptar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                btnAceptarOnClick(v);
            }
        });

        // btnCancelar
        this.btnCancelar = (Button) findViewById(R.id.btnCancelar);
        this.btnCancelar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                btnCancelarOnClick(v);
            }
        });

    }

    private void loadData() {
        actualizarFecha();
    }

    private void actualizarFecha() {
        Date fecha = this.deposito.getFechaDepositoMovil();
        if (fecha == null) {
            fecha = Calendar.getInstance().getTime();
            this.deposito.setFechaDepositoMovil(fecha);
        }
        this.txtFechaDeposito.setText(Formatter.formatDate(fecha));
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case DATE_DIALOG:
                dialog = getDialogFecha();
                break;
        }
        return dialog;
    }

    private Dialog getDialogFecha() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.deposito.getFechaDepositoMovil());

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialogFecha = new DatePickerDialog(this, new OnDateSetListener() {
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                txtFechaOnDateSet(view, year, monthOfYear, dayOfMonth);
            }
        }, year, month, day);
        return dialogFecha;
    }

    /*
     * EVENTOS
     */

    public void txtFechaOnDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);

        Date fechaVenta = cal.getTime();
        this.deposito.setFechaDepositoMovil(fechaVenta);
        actualizarFecha();
    }

    private void btnAceptarOnClick(View v) {
        String sTotal = this.txtTotal.getText().toString();
        String sNumero = this.txtNumeroComprobante.getText().toString();
        String sCantidadCheques = this.txtCantidadCheques.getText().toString();

        if (validarDatos()) {
            double total = Double.parseDouble(sTotal);
            long numero = Long.parseLong(sNumero);
            int cantCheques = Integer.parseInt(sCantidadCheques);

            this.deposito.setImporte(total);
            this.deposito.setNumeroComprobante(numero);
            this.deposito.setCantidadCheques(cantCheques);

            Intent intent = new Intent(this, FrmGestionDeposito.class);
            intent.putExtra("entrega", this.deposito);
            setResult(RESULT_OK, intent);
            this.finish();
        }
    }

    private boolean validarDatos() {
        boolean valido = false;
        String sImporte = this.txtTotal.getText().toString();
        String sNumero = this.txtNumeroComprobante.getText().toString();
        String sCantCheques = this.txtCantidadCheques.getText().toString();


        if (this.deposito.getBanco() == null) {
            AlertDialog alert = new AlertDialog(this, getString(R.string.tituloError), ErrorManager.SeleccionarBanco);
            alert.show();
        } else if (sNumero == null || sNumero.trim().length() == 0) {
            AlertDialog alert = new AlertDialog(this, getString(R.string.tituloError), ErrorManager.IngresarNumero);
            alert.show();
        } else if (sImporte == null || sImporte.trim().length() == 0 || Double.parseDouble(sImporte) <= 0) {
            AlertDialog alert = new AlertDialog(this, getString(R.string.tituloError), ErrorManager.ImporteInvalido);
            alert.show();
        } else if (sCantCheques == null || sCantCheques.trim().length() <= 0) {
            AlertDialog alert = new AlertDialog(this, getString(R.string.tituloError), getString(R.string.msjIngresarCantidadCheques));
            alert.show();
        } else {
            valido = true;
        }
        return valido;
    }

    private void btnCancelarOnClick(View v) {
        setResult(RESULT_CANCELED);
        this.finish();
    }

    @Override
    public Object getLastNonConfigurationInstance() {
        // TODO Auto-generated method stub
        return this.deposito;
    }


}
