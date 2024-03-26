package com.ar.vgmsistemas.view.venta;

import static com.ar.vgmsistemas.entity.Documento.SOLO_AVION;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.CondicionVentaBo;
import com.ar.vgmsistemas.bo.CuentaCorrienteBo;
import com.ar.vgmsistemas.bo.DocumentoBo;
import com.ar.vgmsistemas.bo.DocumentosListaBo;
import com.ar.vgmsistemas.bo.EmpresaBo;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.bo.RepartidorBo;
import com.ar.vgmsistemas.bo.VendedorBo;
import com.ar.vgmsistemas.bo.VentaBo;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.CondicionVenta;
import com.ar.vgmsistemas.entity.Documento;
import com.ar.vgmsistemas.entity.Empresa;
import com.ar.vgmsistemas.entity.MotivoAutorizacion;
import com.ar.vgmsistemas.entity.Repartidor;
import com.ar.vgmsistemas.entity.Turno;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.entity.key.PkVenta;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.utils.TipoEmpresaCode;
import com.ar.vgmsistemas.view.AlertDialog;
import com.ar.vgmsistemas.view.cobranza.FrmResumenCuentaCorriente;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CabeceraVentaFragment extends Fragment {
    private static final int ALTA = 0;
    private static final int CONSULTA = 2;

    private static final int DATE_VENTA_DIALOG = 0;
    private static final int DATE_ENTREGA_DIALOG = 1;
    private static final int ACTIVITY_CTA_CTE_INHABILITADA = 9999;
    private static final String TAG = CabeceraVentaFragment.class.getCanonicalName();

    private Activity mActivity;
    private Venta _venta;
    private FrmVenta mFrmVenta;
    private Spinner cmbCondicionVenta;
    private Spinner cmbTipoDocumento;
    private Spinner cmbPuntoVenta;
    private Spinner cmbTurno;
    private Spinner cmbRepartidor;
    private TextView txtNumeroDocumento;
    private EditText txtCategoriaFiscal;
    private EditText txtCliente;
    private EditText txtFechaVenta;
    private EditText txtPie;
    private EditText txtCuit;
    private EditText txtFechaEntrega;
    private EditText txtCodigoAutorizacionAcc;
    private boolean permiteCambiarPtoVta;
    private int _modo;
    private RepositoryFactory _repoFactory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mActivity = getActivity();
        _repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(mActivity, R.layout.lyt_venta_cabecera, null);
        mFrmVenta = (FrmVenta) getActivity();
        Object data = getLastNonConfigurationInstance();
        // TODO controlar esto
        Bundle b = getArguments();
        this._modo = ALTA;
        if (data == null) {
            this._venta = (Venta) b.getSerializable(FrmVenta.EXTRA_VENTA);
            //
            this._modo = (Integer) b.getSerializable(FrmVenta.EXTRA_MODO);
            if (this._venta.getId().getIdNumeroDocumento() == 0)
                this._modo = ALTA;
        } else {
            this._venta = (Venta) data;
            //
            this._modo = (Integer) b.getSerializable(FrmVenta.EXTRA_MODO);
        }
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {

        // txtCliente (EditText)
        this.txtCliente = view.findViewById(R.id.txtCliente);
        this.txtCliente.setKeyListener(null);
        this.txtCliente.setText(this._venta.getCliente().getRazonSocial());

        // txtCondicionAnteIVA (EditText)
        this.txtCategoriaFiscal = view.findViewById(R.id.txtCategoriaFiscal);
        this.txtCategoriaFiscal.setKeyListener(null);
        this.txtCategoriaFiscal.setText(this._venta.getCliente().getCategoriaFiscal().getDescripcion());

        // txtCuit (EditText)
        txtCuit = view.findViewById(R.id.txtCuit);
        txtCuit.setKeyListener(null);
        txtCuit.setText(this._venta.getCliente().getCuit());

        // txtFechaVenta (EditText)
        this.txtFechaVenta = view.findViewById(R.id.txtFechaVenta);
        this.txtFechaVenta.setKeyListener(null);
        this.txtFechaVenta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_VENTA_DIALOG);
            }
        });

        // txtCodigoAutorizacionAccionComercial (EditText)
        this.txtCodigoAutorizacionAcc = view.findViewById(R.id.txtCdAutorizacionAcc);
        this.txtCodigoAutorizacionAcc.setInputType(InputType.TYPE_CLASS_NUMBER);
        OnFocusChangeListener onFocusChangeListener = new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                CuentaCorrienteBo cuentaCorrienteBo = new CuentaCorrienteBo(_repoFactory);
                boolean isCodCorrecto = cuentaCorrienteBo.validarCodigoAutorizacion(txtCodigoAutorizacionAcc.getText().toString());
                if (txtCodigoAutorizacionAcc.getText().toString().equals("") || isCodCorrecto)
                    txtCodigoAutorizacionAcc.setError(null);
                else
                    txtCodigoAutorizacionAcc.setError("Codigo incorrecto");
                _venta.setCodigoAutorizacionAccionComercial(txtCodigoAutorizacionAcc.getText().toString());
            }
        };
        this.txtCodigoAutorizacionAcc.setOnFocusChangeListener(onFocusChangeListener);

        TextWatcher watcher = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _venta.setCodigoAutorizacionAccionComercial(txtCodigoAutorizacionAcc.getText().toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        this.txtCodigoAutorizacionAcc.addTextChangedListener(watcher);
        this.txtCodigoAutorizacionAcc.setText(this._venta.getCodigoAutorizacionAccionComercial());


        // txtNumeroDocumento (TextView)
        this.txtNumeroDocumento = view.findViewById(R.id.txtNumero);
        this.txtNumeroDocumento.setKeyListener(null);
        this.txtFechaVenta.setKeyListener(null);

        // cmbCondicionVenta (Spinner)
        this.cmbCondicionVenta = view.findViewById(R.id.cmbCondicionVenta);
        this.cmbCondicionVenta.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
                cmbCondicionVentaOnItemSelected(adapter/*, view, position, id*/);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        // cmbTipoDocumento (Spinner)
        this.cmbTipoDocumento = view.findViewById(R.id.cmbTipoDocumento);
        this.cmbTipoDocumento.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cmbDocumentoOnItemSelected(parent, /*view,*/ position/*, id*/);
            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        this.cmbPuntoVenta = view.findViewById(R.id.cmbPuntVenta);
        permiteCambiarPtoVta = false;
        if (!TipoEmpresaCode.isHacienda()) {//si no es hacienda determinar como esta config el movil en conf avanzada
            permiteCambiarPtoVta = PreferenciaBo.getInstance().getPreferencia(getActivity()).isCambiarPtoVta();
        }
        cmbPuntoVenta.setEnabled(permiteCambiarPtoVta);
        this.cmbPuntoVenta.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cmbPuntoVentaOnItemSelected(parent, position);
            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        //cmbTurno (Spinner)
        cmbTurno = view.findViewById(R.id.cmbTurno);
        // MANEJO DE TURNOS-----------------------------------------------------
        if (PreferenciaBo.getInstance().getPreferencia(getActivity()).isManejoTurno()) {
            view.findViewById(R.id.llTurno).setVisibility(View.VISIBLE);
            ArrayAdapter<String> adapterTurnos = new ArrayAdapter<>(getActivity(), R.layout.simple_spinner_item, Turno.turnosList);
            cmbTurno.setAdapter(adapterTurnos);
            cmbTurno.setOnItemSelectedListener(new OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                    _venta.setTiTurno(Turno.turnosMap.get(pos));

                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub

                }
            });
        }
        // ------------------------------------------------------------------------------

        this.cmbRepartidor = view.findViewById(R.id.cmbRepartidor);
        this.cmbRepartidor.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cmbRepartidorOnItemSelected(parent, view, position, id);
            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        // txtPie (EditText)
        this.txtPie = view.findViewById(R.id.txtPie);
        this.txtPie.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pie = txtPie.getText().toString();
                _venta.setPie(pie);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void afterTextChanged(Editable s) {
            }
        });

        // txtFechaEntrega (EditText)
        this.txtFechaEntrega = view.findViewById(R.id.txtFechaEntrega);
        this.txtFechaEntrega.setKeyListener(null);
        this.txtFechaEntrega.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                showDialog(DATE_ENTREGA_DIALOG);

            }
        });

        if (_modo == CONSULTA) {
            cmbRepartidor.setEnabled(false);
            cmbCondicionVenta.setEnabled(false);
            cmbTipoDocumento.setEnabled(false);
            txtPie.setEnabled(false);
            cmbPuntoVenta.setEnabled(false);
            txtCodigoAutorizacionAcc.setEnabled(false);
        }
    }

    private void showDialog(int typeDialog) {
        switch (typeDialog) {
            case DATE_VENTA_DIALOG: {
                Calendar cal = Calendar.getInstance();
                cal.setTime(this._venta.getFechaVenta());

                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialogFecha = new DatePickerDialog(mActivity, new OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        txtFechaOnDateSet(/*view,*/ year, monthOfYear, dayOfMonth);
                    }
                }, year, month, day);
                dialogFecha.show();
                break;
            }
            case DATE_ENTREGA_DIALOG: {
                Calendar cal = Calendar.getInstance();

                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialogFechaEntrega = new DatePickerDialog(mActivity, new OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        txtFechaEntregaOnDateSet(year, monthOfYear, dayOfMonth);
                    }
                }, year, month, day);
                dialogFechaEntrega.show();
                break;
            }

        }
    }

    public void txtFechaOnDateSet(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);

        Date fechaVenta = cal.getTime();
        this._venta.setFechaVenta(fechaVenta);
        actualizarFecha();
    }

    private void actualizarFecha() {
        String fecha = Formatter.formatDate(this._venta.getFechaVenta());
        this.txtFechaVenta.setText(fecha);

        String fechaEntrega = Formatter.formatDate(this._venta.getFechaEntrega());
        this.txtFechaEntrega.setText(fechaEntrega);

    }

    /**
     * Actualizo la fecha de entrega seleccionada
     */
    private void actualizarFechaEntrega() {
        String fechaEntrega = Formatter.formatDate(this._venta.getFechaEntrega());
        this.txtFechaEntrega.setText(fechaEntrega);
    }

    public void txtFechaEntregaOnDateSet(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);

        Date fechaEntrega = cal.getTime();
        this._venta.setFechaEntrega(fechaEntrega);
        actualizarFechaEntrega();
    }

    @Override
    public void onResume() {
        super.onResume();
        initVar();
    }

    private void loadTurno() {
        if (PreferenciaBo.getInstance().getPreferencia(getActivity()).isManejoTurno()) {
            cmbTurno.setSelection(Turno.turnosMapInverse.get(_venta.getTiTurno()));
        }
    }

    private void initVar() {
        actualizarFecha();

        // Documentos
        loadDocumentos();

        // TURNOS
        loadTurno();
        // Condiciones de venta
        loadCondicionVenta();

        // PKVenta
        if (this._venta.getId() == null) {
            PkVenta pkVenta = new PkVenta();
            this._venta.setId(pkVenta);
        }

        // Vendedor
        if (!mFrmVenta.isFromPedidoSugerido()) {
            this._venta.setVendedor(VendedorBo.getVendedor());
        }

        // Repartidor
        loadRepartidor();

        // Observaciones
        String pie = this._venta.getPie();
        if (pie != null) {
            this.txtPie.setText(pie);

        }
    }

    private void loadDocumentos() {
        List<String> documentos;
        try {
            if (_modo != CONSULTA) {
                DocumentoBo documentoBo = new DocumentoBo(_repoFactory);
                if (this._venta.getCliente().getCategoriaFiscal().getSnBn().equals("N")) {
                    documentos = documentoBo.recoveryTiposDocumentos(Documento.VENTA);
                } else {
                    documentos = documentoBo.recoveryTiposDocumentos(Documento.VENTA, SOLO_AVION);
                }
            } else {
                documentos = new ArrayList<>();
                documentos.add(_venta.getId().getIdDocumento());
            }
            ArrayAdapter<String> adapterTipoDocumento = new ArrayAdapter<>(mActivity, R.layout.simple_spinner_item,
                    documentos);

            this.cmbTipoDocumento.setAdapter(adapterTipoDocumento);

            String documentoSeleccionado = "";
            // si el tipo de documento no es nulo, debe seleccionar el elemento
            if (mFrmVenta.getTipoEmpresa() == TipoEmpresaCode.TYPE_NORMAL) {
                if (this._venta.getId().getIdDocumento() != null) {
                    documentoSeleccionado = this._venta.getId().getIdDocumento();
                } else {
                    documentoSeleccionado = PreferenciaBo.getInstance().getPreferencia(mActivity).getIdTipoDocumentoPorDefecto();
                }
            } else if (TipoEmpresaCode.isHacienda()) {
                cmbTipoDocumento.setEnabled(false);
                documentoSeleccionado = PreferenciaBo.getInstance().getPreferencia(mActivity).getIdTipoDocumentoPorDefecto();
                _venta.getId().setIdDocumento(documentoSeleccionado);
            }

            ArrayAdapter<String> adapter = (ArrayAdapter<String>) cmbTipoDocumento.getAdapter();
            int pos = adapter.getPosition(documentoSeleccionado);
            cmbTipoDocumento.setSelection(pos);


        } catch (Exception e) {
            ErrorManager.manageException(TAG, "loadDocumentos", e, mActivity);
        }
    }

    private void loadRepartidor() {
        List<Repartidor> repartidores;
        try {
            RepartidorBo RepartidorBo = new RepartidorBo(_repoFactory);
            repartidores = RepartidorBo.recoveryAll();
            ArrayAdapter<Repartidor> adapterRepartidor = new ArrayAdapter<>(mActivity, R.layout.simple_spinner_item,
                    repartidores);
            this.cmbRepartidor.setAdapter(adapterRepartidor);

            // Selecciona el repartidor, si la venta no tiene repartidor
            // seteada, carga
            // el repartidor por defecto
            Repartidor repartidor;
            if (this._venta.getRepartidor() != null) {
                repartidor = this._venta.getRepartidor();
            } else {
                repartidor = _venta.getCliente().getRepartidor();
            }

            ArrayAdapter<Repartidor> arrayAdapter = (ArrayAdapter<Repartidor>) cmbRepartidor.getAdapter();
            int pos = arrayAdapter.getPosition(repartidor);
            this.cmbRepartidor.setSelection(pos);
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "loadRepartidor", e, mActivity);
        }
    }

    private void cmbDocumentoOnItemSelected(AdapterView<?> adapter, /*View view,*/ int position/*, long id*/) {
        if (position >= 0) {
            // Seteo el tipo de documento en la venta
            String idDocumento = (String) adapter.getSelectedItem();
            this._venta.getId().setIdDocumento(idDocumento);
            // Obtengo los puntos de venta para el tipo de documento
            // seleccionado y los cargo en el combo
            try {
                List<Integer> puntosVenta = new ArrayList<>();
                if (_modo != CONSULTA) {
                    DocumentoBo documentoBo = new DocumentoBo(_repoFactory);
                    if (this._venta.getCliente().getCategoriaFiscal().getSnBn().equals("N")) {
                        puntosVenta = documentoBo.recoveryPuntosVenta(idDocumento);
                    } else {
                        puntosVenta = documentoBo.recoveryPuntosVenta(idDocumento, SOLO_AVION);
                    }
                    ArrayAdapter<Integer> adapterPuntoVenta = new ArrayAdapter<>(mActivity, R.layout.simple_spinner_item,
                            puntosVenta);
                    cmbPuntoVenta.setAdapter(adapterPuntoVenta);

                    // selecciono el punto de venta de la venta, si no es null
                    if (this._venta.getId() != null) {
                        int size = puntosVenta.size();
                        for (int i = 0; i < size; i++) {
                            int puntoVenta = puntosVenta.get(i);
                            if (puntoVenta == this._venta.getId().getPuntoVenta()) {
                                this.cmbPuntoVenta.setSelection(i);
                                //this.cmbPuntoVenta.setText(puntoVenta);
                                break;
                            }
                        }
                    }
                } else {
                    puntosVenta.add(_venta.getId().getPuntoVenta());
                    ArrayAdapter<Integer> adapterPuntoVenta = new ArrayAdapter<>(mActivity, R.layout.simple_spinner_item,
                            puntosVenta);
                    cmbPuntoVenta.setAdapter(adapterPuntoVenta);
                }
                VentaBo.actualizarTotales(_venta);
            } catch (Exception e) {
                ErrorManager.manageException(TAG, "cmbDocumentoOnItemSelected", e, mActivity);
            }
        }
    }

    /**
     * Cambia el punto de venta seleccionado y se actualiza el numero de
     * documento
     *
     * @param adapter
     * @param
     * @param position
     * @param
     */
    private void cmbPuntoVentaOnItemSelected(AdapterView<?> adapter, /*View view,*/ int position/*, long id*/) {
        if (position >= 0) {

            // Seteo el punto de venta
            int puntoVenta = Integer.valueOf(adapter.getSelectedItem().toString());
            this._venta.getId().setPuntoVenta(puntoVenta);
            // Obtengo el numero de documento, lo seteo en la venta y muestro en
            // pantalla
            DocumentosListaBo documentosListaBo = new DocumentosListaBo(_repoFactory);
            mFrmVenta.updateLineasPedido(_venta.getDetalles(), _venta.getId());
            List<Boolean> listValids = new ArrayList<>();
            try {
                listValids = documentosListaBo.getValidListCabecera(_venta.getDetalles(), _venta.getId());
            } catch (Exception e1) {

                e1.printStackTrace();
            }
            try {
                DocumentoBo documentoBo = new DocumentoBo(_repoFactory);
                VentaBo.actualizarTotales(_venta);
                String documento = this._venta.getId().getIdDocumento();
                String letra = this._venta.getId().getIdLetra();

                if (this._modo == ALTA) {
                    long numeroDocumento = documentoBo.recoveryNumeroDocumento(documento, letra, puntoVenta);
                    this._venta.getId().setIdNumeroDocumento(numeroDocumento);
                }
                if (_modo == FrmVenta.GENERACION_REMITO) {
                    long numeroDocumento = documentoBo.recoveryNumeroDocumento(PreferenciaBo.getInstance().getPreferencia()
                            .getIdTipoDocumentoPorDefecto(), letra, puntoVenta);
                    _venta.getId().setIdNumeroDocumento(numeroDocumento);
                    int ptoVta = PreferenciaBo.getInstance().getPreferencia().getIdPuntoVentaPorDefecto();
                    _venta.getId().setPuntoVenta(ptoVta);
                }
                DecimalFormat df = new DecimalFormat("00000000");
                txtNumeroDocumento.setText(df.format(this._venta.getId().getIdNumeroDocumento()));
            } catch (Exception e) {
                ErrorManager.manageException(TAG, "cmbPuntoVentaOnItemSelected", e, mActivity);
            }

            if (!mFrmVenta.isDataValid()) {
                showDialogListasNoValidas(listValids);
            }
        }
    }

    private void showDialogListasNoValidas(final List<Boolean> listValids) {
        AlertDialog dialog = new AlertDialog(mActivity, ErrorManager.ErrorListaNoValida, getString(R.string.msjListasNoValidas));
        dialog.setPositiveButton(R.string.btnAceptar, new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                dialog.dismiss();

            }
        });
        dialog.setNegativeButton(R.string.btnVerDetalles, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                mFrmVenta.showDetailTab();
                dialog.dismiss();
            }
        });
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });
        dialog.show();
    }

    public void cmbCondicionVentaOnItemSelected(AdapterView<?> adapter/*, View view, int position, long id*/) {
        CondicionVenta condicion = (CondicionVenta) adapter.getSelectedItem();
        Cliente cliente = _venta.getCliente();
        int diasAtrasoDefecto = cliente.getCondicionVenta().getDiasAtraso();
        int diasAtraso = condicion.getDiasAtraso();

        CuentaCorrienteBo cuentaCorrienteBo = new CuentaCorrienteBo(_repoFactory);
        if (diasAtraso <= diasAtrasoDefecto) {
            try {
                if (cuentaCorrienteBo.isVentaAutorizada(_venta)) {
                    this._venta.setCondicionVenta(condicion);
                    this._venta.setTasaDescuentoCondicionVenta(condicion.getTasaDescuento());
                    VentaBo.actualizarTotales(_venta);
                    this._venta.notifyObservers();

                } else {
                    if (this._modo == ALTA) {
                        // Muestro el resumen de cuenta informandole que no puede vender a cta cte
                        Intent intentResumenCuenta = new Intent(mActivity, FrmResumenCuentaCorriente.class);
                        intentResumenCuenta.putExtra("cliente", cliente);
                        startActivityForResult(intentResumenCuenta, ACTIVITY_CTA_CTE_INHABILITADA);
                    }
                }
            } catch (Exception ex) {
                ErrorManager.manageException(TAG, "cmbCondicionVentaOnItemSelected", ex, mActivity);
            }
        } else {
            try {
                boolean ctaCteHabilitada = cuentaCorrienteBo.isVentaCuentaCorrienteHabilitada(cliente);
                if (ctaCteHabilitada) {
                    ArrayAdapter<CondicionVenta> arrayAdapter = (ArrayAdapter<CondicionVenta>) cmbCondicionVenta.getAdapter();
                    int pos = arrayAdapter.getPosition(cliente.getCondicionVenta());
                    this.cmbCondicionVenta.setSelection(pos);
                } else {
                    this._venta.setCondicionVenta(null);
                    loadCondicionVenta();
                }
                AlertDialog alert = new AlertDialog(mActivity, "Error", ErrorManager.VentaNoPermitida);
                alert.show();
            } catch (Exception ex) {
                ErrorManager.manageException(TAG, "cmbCondicionVentaOnItemSelected", ex, mActivity);
            }
        }
        // }
    }

    private void loadCondicionVenta() {
        List<CondicionVenta> condiciones;

        try {
            EmpresaBo empresaBo = new EmpresaBo(_repoFactory);
            Empresa empresa = empresaBo.recoveryEmpresa();
            CondicionVentaBo condicionVentaBo = new CondicionVentaBo(_repoFactory);
            condiciones = condicionVentaBo.recoveryAll();
            ArrayAdapter<CondicionVenta> adapterCondicionVenta = new ArrayAdapter<>(mActivity,
                    R.layout.simple_spinner_item, condiciones);
            this.cmbCondicionVenta.setAdapter(adapterCondicionVenta);
            CuentaCorrienteBo cuentaCorrienteBo = new CuentaCorrienteBo(_repoFactory);
            boolean ventaCuentaCorrienteHabilitada = cuentaCorrienteBo.isVentaCuentaCorrienteHabilitada(_venta.getCliente());

            // Si estamos mostrando una venta guardada, cargamos la condicion
            // con la que se guardo
            CondicionVenta condicionVenta = null;
            if (this._venta.getCondicionVenta() != null) {
                condicionVenta = this._venta.getCondicionVenta();
            } else {
                // controlo el saldo vencido, si es > 0, solo se le vende a
                // contado.
                if (!ventaCuentaCorrienteHabilitada) {
                    condicionVenta = condicionVentaBo.getCondicionVentaContado();
                } else if (_venta.getCliente().getCondicionVenta() != null) {
                    condicionVenta = this._venta.getCliente().getCondicionVenta();
                }
            }

            if (empresa.getSnModifCdMovil() != null && empresa.getSnModifCdMovil().equals("S")) {
                condicionVenta = this._venta.getCliente().getCondicionVenta();
                cmbCondicionVenta.setEnabled(false);
            }

            ArrayAdapter<CondicionVenta> arrayAdapter = (ArrayAdapter<CondicionVenta>) cmbCondicionVenta.getAdapter();
            int pos = arrayAdapter.getPosition(condicionVenta);
            this.cmbCondicionVenta.setSelection(pos);
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "loadCondicionesVenta", e, mActivity);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTIVITY_CTA_CTE_INHABILITADA) {
            // Si se autorizo la venta a cuenta corriente
            if (resultCode == Activity.RESULT_OK) {
                Bundle b = data.getExtras();
                String codigoAutorizacion = (b != null) ? b.getString("codigoAutorizacion") : "";
                _venta.setCodigoAutorizacion(codigoAutorizacion);

            } else if (resultCode == Activity.RESULT_CANCELED) {
                getActivity().finish();
            } else if (resultCode == FrmResumenCuentaCorriente.RESULT_CON_MOTIVO) {
                MotivoAutorizacion autorizacion = (MotivoAutorizacion) data.getSerializableExtra(FrmResumenCuentaCorriente.MOTIVO);
                _venta.setMotivoAutorizacion(autorizacion);
                _venta.setPie(data.getStringExtra(FrmResumenCuentaCorriente.OBSERVACION));
            }
        }
    }

    public void cmbRepartidorOnItemSelected(AdapterView<?> adapter, View view, int position, long id) {
        Repartidor repartidor = (Repartidor) adapter.getSelectedItem();
        this._venta.setRepartidor(repartidor);
    }

    public Object getLastNonConfigurationInstance() {
        return this._venta;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
