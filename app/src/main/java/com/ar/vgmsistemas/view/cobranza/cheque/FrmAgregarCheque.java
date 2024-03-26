package com.ar.vgmsistemas.view.cobranza.cheque;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.BancoBo;
import com.ar.vgmsistemas.bo.LocalidadBo;
import com.ar.vgmsistemas.bo.ProvinciaBo;
import com.ar.vgmsistemas.entity.Banco;
import com.ar.vgmsistemas.entity.Cheque;
import com.ar.vgmsistemas.entity.Localidad;
import com.ar.vgmsistemas.entity.Provincia;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.AlertDialog;
import com.ar.vgmsistemas.view.cobranza.BaseFrmAgregar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FrmAgregarCheque extends BaseFrmAgregar {

    private AutoCompleteTextView acTxtLocalidad;
    private AutoCompleteTextView acTxtProvincia;
    private AutoCompleteTextView acTxtBanco;
    private TextView txtSucursal;
    private TextView txtNumeroCheque;
    private TextView txtFechaEmision;
    private TextView txtNroCuenta;
    private EditText etCuit;
    private Spinner cmbTipoCheque;
    private EditText txtTotal;
    private Button btnAceptar;
    private Button btnCancelar;
    private BancoBo bancoBo;
    private BancoAdapter _adapter;
    private Cheque _cheque;
    private Provincia provinciaSeleccionada;
    private List<Localidad> localidades;
    private List<Provincia> provincias;
    private static final String TAG = FrmAgregarCheque.class.getCanonicalName();
    private static final int DATE_DIALOG = 0;
    private String tipoChequeSeleccionado;

    //BD
    private RepositoryFactory _repoFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.agregar_cheque);
        setContentView(R.layout.lyt_cheques_agregar);
        getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        initComponents();

        Object data = getLastNonConfigurationInstance();
        if (data != null) {
            _cheque = (Cheque) data;
        } else {
            this._cheque = new Cheque();
        }
        _repoFactory = RepositoryFactory.getRepositoryFactory(getApplicationContext(), RepositoryFactory.ROOM);
        loadProvincias();
        loadBancos();
        loadData();
        loadTipoCheques();
    }

    private void loadTipoCheques() {
        List<String> tiposCheques = new ArrayList<>();
        tiposCheques.add("Fisico");
        tiposCheques.add("Electronico");
        ArrayAdapter<String> adapterTiposCheques = new ArrayAdapter<>(this, R.layout.simple_spinner_item, tiposCheques);
        cmbTipoCheque.setAdapter(adapterTiposCheques);
    }

    private void loadProvincias() {
        ProvinciaBo provinciaBo = new ProvinciaBo(_repoFactory);
        try {
            provincias = provinciaBo.recoveryAll();
            ArrayAdapter<Provincia> adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, provincias);
            acTxtProvincia.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadLocalidades(int idProvincia) {
        try {
            LocalidadBo localidadBo = new LocalidadBo(_repoFactory);
            localidades = localidadBo.recoveryByProvincia(idProvincia);
            ArrayAdapter<Localidad> adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, localidades);
            this.acTxtLocalidad.setAdapter(adapter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadBancos() {
        bancoBo = new BancoBo(_repoFactory);
        try {
            List<Banco> bancos = bancoBo.recoveryAll();
            this._adapter = new BancoAdapter(this, bancos);
            acTxtBanco.setAdapter(this._adapter);
        } catch (Exception ex) {
            ErrorManager.manageException(TAG, "initVar", ex, this);
        }
    }

    public void acTxtLocalidadOnItemClick(AdapterView<?> adapter, View view,
                                          int position, long id) {
        Localidad localidadSeleccionada = (Localidad) adapter.getItemAtPosition(position);
        this._cheque.setLocalidad(localidadSeleccionada);
        this.acTxtBanco.requestFocus();
    }

    public void acTxtBancoOnItemClick(AdapterView<?> adapter, View view,
                                      int position, long id) {
        Banco bancoSeleccionado = (Banco) adapter.getItemAtPosition(position);
        this._cheque.setBanco(bancoSeleccionado);
        this.txtSucursal.requestFocus();
    }

    @Override
    public void initComponents() {
        super.initComponents();
        acTxtProvincia = (AutoCompleteTextView) findViewById(R.id.actxtProvincia);
        acTxtProvincia.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loadLocalidades(((Provincia) parent.getItemAtPosition(position)).getId());
                provinciaSeleccionada = (Provincia) parent.getItemAtPosition(position);
            }
        });
        this.acTxtLocalidad = (AutoCompleteTextView) findViewById(R.id.actxtLocalidad);
        this.acTxtLocalidad.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                acTxtLocalidadOnItemClick(parent, view, position, id);
            }
        });
        acTxtLocalidad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    if (provinciaSeleccionada == null)
                        Toast.makeText(getApplicationContext(), getString(R.string.seleccionarProvincia), Toast.LENGTH_SHORT).show();
            }
        });
        // cmbBanco
        this.acTxtBanco = (AutoCompleteTextView) findViewById(R.id.acTxtBanco);
        this.acTxtBanco.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                acTxtBancoOnItemClick(parent, view, position, id);
            }
        });

        // txtSucursal
        this.txtSucursal = (TextView) findViewById(R.id.txtSucursal);

        //txtNumeroCuenta
        this.txtNroCuenta = (TextView) findViewById(R.id.txtNumeroCuenta);


        // txtNumeroCheque
        this.txtNumeroCheque = (TextView) findViewById(R.id.txtNumeroCheque);
        etCuit = (EditText) findViewById(R.id.txtCuit);
        etCuit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if ((etCuit.getText().toString().trim().length() == 11)) {
                    if (Formatter.isValidCuit(etCuit.getText().toString().trim())) {
                        etCuit.setError(null);
                    } else {
                        etCuit.setError("Ingrese un Cuit valido");

                    }

                } else {
                    etCuit.setError(null);
                }

            }
        });
        // txtFechaVencimiento
        this.txtFechaEmision = (EditText) findViewById(R.id.txtFechaVencimiento);
        this.txtFechaEmision.setKeyListener(null);
        this.txtFechaEmision.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG);
            }
        });

        //tipoCheque
        this.cmbTipoCheque = (Spinner) findViewById(R.id.cmbTipoCheque);
        this.cmbTipoCheque.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
                tipoChequeSeleccionado = (String) adapter.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> arg0) {

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

        // btnAceptar
        this.btnAceptar = (Button) findViewById(R.id.btnAgregarCheque);
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
        Date fecha = this._cheque.getFechaChequeMovil();
        if (fecha == null) {
            fecha = Calendar.getInstance().getTime();
            this._cheque.setFechaChequeMovil(fecha);
        }
        this.txtFechaEmision.setText(Formatter.formatDate(fecha));
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        if (id == DATE_DIALOG) {
            dialog = getDialogFecha();
        }
        return dialog;
    }

    private Dialog getDialogFecha() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this._cheque.getFechaChequeMovil());

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
        this._cheque.setFechaChequeMovil(fechaVenta);
        actualizarFecha();
    }

    private void btnAceptarOnClick(View v) {
        String sTotal = this.txtTotal.getText().toString();
        String sNumero = this.txtNumeroCheque.getText().toString();
        String sSucursal = this.txtSucursal.getText().toString();
        String sNroCuenta = this.txtNroCuenta.getText().toString();
        String cuit = etCuit.getText().toString();
        if (validarDatos()) {
            double total = Double.parseDouble(sTotal);
            long numero = Long.parseLong(sNumero);
            long nroCuenta = Long.parseLong(sNroCuenta);
            int sucursal = Integer.parseInt(sSucursal);
            this._cheque.setImporte(total);
            this._cheque.getId().setNumeroCheque(numero);
            this._cheque.getId().setSucursal(sucursal);
            this._cheque.getId().setNroCuenta(nroCuenta);
            _cheque.setCuit(cuit);
            if (tipoChequeSeleccionado == null || tipoChequeSeleccionado.equals("")) {
                this._cheque.setTipoCheque("F");
            } else {
                if (tipoChequeSeleccionado.equals("Fisico")) {
                    this._cheque.setTipoCheque("F");
                } else {
                    this._cheque.setTipoCheque("E");
                }
            }

            Intent intent = new Intent(this, FrmGestionCheques.class);
            intent.putExtra("cheque", this._cheque);
            setResult(RESULT_OK, intent);
            this.finish();
        }
    }

    private boolean validarDatos() {
        boolean valido = false;
        String sImporte = this.txtTotal.getText().toString();
        String sNumero = this.txtNumeroCheque.getText().toString();
        String sSucursal = this.txtSucursal.getText().toString();
        String sNroCuenta = this.txtNroCuenta.getText().toString();
        Localidad localidad = this._cheque.getLocalidad();
        if (localidad == null) {
            AlertDialog alert = new AlertDialog(this, getString(R.string.tituloError), ErrorManager.SeleccionarLocalidad);
            alert.show();
        } else if (this._cheque.getBanco() == null) {
            AlertDialog alert = new AlertDialog(this, getString(R.string.tituloError), ErrorManager.SeleccionarBanco);
            alert.show();
        } else if (sSucursal == null || sSucursal.trim().length() == 0) {
            AlertDialog alert = new AlertDialog(this, getString(R.string.tituloError), ErrorManager.IngresarSucursal);
            alert.show();
        } else if (sNumero == null || sNumero.trim().length() == 0) {
            AlertDialog alert = new AlertDialog(this, getString(R.string.tituloError), ErrorManager.IngresarNumero);
            alert.show();
        } else if (sNroCuenta == null || sNroCuenta.trim().length() == 0) {
            AlertDialog alert = new AlertDialog(this, getString(R.string.tituloError), ErrorManager.IngresarNumeroCuenta);
            alert.show();
        } else if (sNroCuenta.trim().length() > 18) {
            AlertDialog alert = new AlertDialog(this, getString(R.string.tituloError), ErrorManager.IngresarNumeroCuentaGrande);
            alert.show();
        } else if (sImporte == null || sImporte.trim().length() == 0 || Double.parseDouble(sImporte) <= 0) {
            AlertDialog alert = new AlertDialog(this, getString(R.string.tituloError), ErrorManager.ImporteInvalido);
            alert.show();
        } else if ((!Formatter.isValidCuit(etCuit.getText().toString().trim())) ||
                etCuit.getText().toString().trim().length() == 0 ||
                etCuit.getText().toString().trim() == null) {
            etCuit.setError("Ingrese un Cuit valido");
            etCuit.requestFocus();
            return false;

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
        return this._cheque;
    }


}
