package com.ar.vgmsistemas.view.venta;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter.FilterListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.ArticuloBo;
import com.ar.vgmsistemas.bo.DocumentosListaBo;
import com.ar.vgmsistemas.bo.ListaPrecioBo;
import com.ar.vgmsistemas.bo.ListaPrecioDetalleBo;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.bo.VentaBo;
import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.ListaPrecio;
import com.ar.vgmsistemas.entity.ListaPrecioDetalle;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.entity.key.PkDocumentosLista;
import com.ar.vgmsistemas.entity.key.PkVenta;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.task.venta.UpdateStockTask;
import com.ar.vgmsistemas.utils.CategoriaRecursoHumano;
import com.ar.vgmsistemas.utils.ComparatorDateTime;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.utils.TipoEmpresaCode;
import com.ar.vgmsistemas.view.AlertDialog;
import com.ar.vgmsistemas.view.BaseActivity;
import com.ar.vgmsistemas.view.articulo.ArticuloAdapter;
import com.ar.vgmsistemas.view.articulo.FrmPreciosXLista;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class FrmAgregarLineaHacienda extends BaseActivity {
    private static final String TAG = FrmAgregarLinea.class.getCanonicalName();
    private static final int RESULT_SELECCIONAR_LINEA = 1;

    public static final String EXTRA_PK_VENTA = "pkVenta";
    public static final String EXTRA_LIST_VALID = "list_no_valid";
    public static final String EXTRA_MODO = "modo";

    private final static int ACTIVITY_PRECIOS_X_LISTA = 1233;

    public final static int ALTA = 0;
    public final static int MODIFICACION = 1;

    private AutoCompleteTextView aCTxtDescripcion;
    private EditText txtCodigo;
    private EditText txtPrecioKilo;
    private EditText txtUnidades;
    private EditText txtSubtotal;
    private EditText txtNroTropa;
    private EditText txtKilogramos;
    private EditText txtStock;
    private Spinner cmbListaPrecio;

    private int mTipoLista;

    private int modo = 0; //para saber si es alta o modificacion
    /**
     * determina si se esta creando o modificando un pedido
     */
    private boolean ORIGEN_BUSCAR = false;
    private ListaPrecioDetalle mListaPrecioDetalle;
    private ArticuloAdapter _adapter;
    private VentaDetalle mLinea;
    private PkVenta _pkVenta;
    private ListaPrecio _listaPrecioXDefecto;
    private Cliente _cliente;
    private ListaPrecioDetalle listaPrecioDetalleDefecto;
    private Venta mVenta;

    //BO´s
    private DocumentosListaBo mDocumentosListaBo;
    private ArticuloBo _articuloBo;
    private ListaPrecioBo listaPrecioBo;


    //BD
    private RepositoryFactory _repoFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_venta_agregar_linea_hacienda);
        setActionBarTitle(this.getString(R.string.tituloAgregarLinea));
        Bundle b = this.getIntent().getExtras();

        initComponents();
        initVar();

        mLinea = (VentaDetalle) b.getSerializable("linea");
        mVenta = (Venta) b.getSerializable(FrmVenta.EXTRA_VENTA);
        if (mVenta != null) {
            _pkVenta = mVenta.getId();
            _cliente = mVenta.getCliente();
        }

        //busco la lista de precio configurada en el comprobante si es que esta configurado sino tomo el del cliente
        if (mVenta.getDocumento() != null && mVenta.getDocumento().getIdListaDefault() != null && mVenta.getDocumento().getIdListaDefault() > 0) {
            try {
                ListaPrecioBo listaPrecioBo = new ListaPrecioBo(_repoFactory);
                this._listaPrecioXDefecto = listaPrecioBo.recoveryById(mVenta.getDocumento().getIdListaDefault());
            } catch (Exception e) {
                errorListaDocumentoNoDisponibleCliente();
                e.printStackTrace();
            }
        } else {
            if (_cliente != null) {
                this._listaPrecioXDefecto = _cliente.getListaPrecio();
            }
        }

        //Es una nueva linea caso contrario una modificacion
        if (this.mLinea == null) {
            mLinea = (VentaDetalle) getLastNonConfigurationInstance();
            if (mLinea == null) {
                this.mLinea = new VentaDetalle();
            }
            this.setModo(ALTA);
        } else {
            this.setModo(MODIFICACION);
        }
        if (mLinea.getArticulo() != null) {
            this.setLinea(this.mLinea);
            mTipoLista = mLinea.getListaPrecio().getTipoLista();
            actualizarDatosArticulo();
        }

        if (PreferenciaBo.getInstance().getPreferencia().isBusquedaPorListaArticulo() &&
                this.getModo() != MODIFICACION) {
            this.buscarArticulo();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.mn_agregar_linea_venta, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void errorListaDocumentoNoDisponibleCliente() {
        AlertDialog dialog = new AlertDialog(this, getString(R.string.tituloError),
                ErrorManager.ListaPrecioDocumentoNoDisponible);
        dialog.show();
    }

    private void initBaseComponents() {
        setResult(RESULT_CANCELED);

        //txtCodigo
        this.txtCodigo = (EditText) findViewById(R.id.txtCodigo);
        String tipoDatoArticulo = PreferenciaBo.getInstance().getPreferencia(getApplicationContext()).getTipoDatoIdArticulo();

        if (tipoDatoArticulo.equals("Numerico")) {
            this.txtCodigo.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            this.txtCodigo.setInputType(InputType.TYPE_CLASS_TEXT);
        }

        this.txtCodigo.setOnFocusChangeListener(new OnFocusChangeListener() {
                                                    public void onFocusChange(View v, boolean hasFocus) {
                                                        txtCodigoOnFocusChange(v, hasFocus);
                                                    }
                                                }
        );

        this.txtCodigo.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                txtCodigoOnTextChanged(s.toString());
            }
        });

        //txtDescripcion
        aCTxtDescripcion = (AutoCompleteTextView) findViewById(R.id.txtDescripcion);
        aCTxtDescripcion.setTextColor(getResources().getColor(R.color.base_text_color));
        aCTxtDescripcion.setOnFocusChangeListener(new OnFocusChangeListener() {
                                                      public void onFocusChange(View v, boolean hasFocus) {
                                                          txtDescripcionOnFocusChange(v, hasFocus);
                                                      }
                                                  }
        );
        aCTxtDescripcion.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txtDescripcionOnItemClick(parent, view, position, id);

            }
        });
        //txtUnidades
        this.txtUnidades = (EditText) findViewById(R.id.txtUnidades);
        this.txtUnidades.setSelectAllOnFocus(true);

        this.txtUnidades.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtUnidadesOnTextChanged(s, start, before, count);

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });

        //txtPrecioKilo
        this.txtPrecioKilo = (EditText) findViewById(R.id.txtPrecioKilo);
        txtPrecioKilo.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                onChangePrecio(arg0, arg1, arg2, arg3);

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {


            }

            @Override
            public void afterTextChanged(Editable arg0) {


            }
        });

        //txtSubtotal
        this.txtSubtotal = (EditText) findViewById(R.id.txtSubtotal);
        this.txtSubtotal.setKeyListener(null);
        this.cmbListaPrecio = (Spinner) findViewById(R.id.cmbListaPrecio);
        cmbListaPrecio.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                       long arg3) {
                cmbListaPrecioOnItemSelected(arg0, arg1, arg2, arg3);
                setEditPrecio();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }
        });


        //btnAceptar
        Button btnAceptar = (Button) findViewById(R.id.btnAgregarLinea);
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isValidData()) {
                    btnAceptarOnClick();
                }

            }
        });

        //btnCancelar
        Button btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btnCancelarOnClick();
            }
        });

        //txtStock
        txtStock = (EditText) findViewById(R.id.txtStock);

    }

    private boolean isValidData() {
        if (this.mLinea.getArticulo() == null) {
            AlertDialog alert = new AlertDialog(this, ErrorManager.ERROR, ErrorManager.NoArticuloSeleccionado);
            alert.show();
            aCTxtDescripcion.requestFocus();
            return false;
        }
        if (this.mLinea.getCantidad() == 0) {
            AlertDialog alert = new AlertDialog(this, ErrorManager.ERROR, ErrorManager.NoUnidadIngresada);
            alert.show();
            txtUnidades.requestFocus();
            return false;
        }
        if (isRepartidor()) {
            double nroTropa = (txtNroTropa.getText().toString().equals("") ? 0 : Double.valueOf(txtNroTropa.getText().toString()));
            if (nroTropa == 0) {
                AlertDialog alertDialog = new AlertDialog(this, ErrorManager.ERROR, ErrorManager.ErrorNroTropa);
                alertDialog.show();
                txtNroTropa.requestFocus();
                return false;
            }
            double kilogramos = (txtKilogramos.getText().toString().equals("") ? 0 : Double.valueOf(txtKilogramos.getText().toString()));
            if (kilogramos == 0) {
                AlertDialog alertDialog = new AlertDialog(this, ErrorManager.ERROR, ErrorManager.ERROR_NO_KILOGRAMOS);
                alertDialog.show();

                return false;
            }
        }
        if (mTipoLista == ListaPrecio.TIPO_LISTA_LIBRE) {
            double precioKg = txtPrecioKilo.getText().toString().equals("") ? 0 : Double.valueOf(txtPrecioKilo.getText().toString());
            if (precioKg == 0) {
                AlertDialog alertDialog = new AlertDialog(this, ErrorManager.ERROR, ErrorManager.ERROR_NO_PRECIO_KILOGRAMOS);
                alertDialog.show();
                return false;
            }
        }

        return true;


    }

    private void onKilogramosChange(CharSequence chars, int start, int before, int count) {
        double totalKg = 0d;
        if (chars.toString().trim().length() > 0) {
            String totalKgStr = String.valueOf(chars);
            totalKg = Double.valueOf(totalKgStr);
        }
        mLinea.setCaArticulosKilos(totalKg);
        if (mListaPrecioDetalle != null) {
            setearPrecios(mListaPrecioDetalle);
        }
        actualizarPrecio();

    }

    private void initTipoHaciendaComponents() {
        txtNroTropa = (EditText) findViewById(R.id.txtNroTropa);
        txtKilogramos = (EditText) findViewById(R.id.txtKg);
        txtNroTropa.setEnabled(CategoriaRecursoHumano.isRepartidorDeHacienda());
        txtKilogramos.setEnabled(CategoriaRecursoHumano.isRepartidorDeHacienda());

        if (isRepartidor()) {
            txtKilogramos.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    onKilogramosChange(s, start, before, count);

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {


                }

                @Override
                public void afterTextChanged(Editable s) {


                }
            });
        }
        findViewById(R.id.lblPrecioKilo).setEnabled(true);
        txtPrecioKilo.setClickable(true);
        txtPrecioKilo.setCursorVisible(true);
        txtPrecioKilo.setFocusable(true);
        txtPrecioKilo.setFocusableInTouchMode(true);
        txtPrecioKilo.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onPrecioPorKgChange(s, start, before, count);

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void onPrecioPorKgChange(CharSequence caracteres, int start, int end, int count) {

        double precioXKg = 0;

        if (caracteres.toString().trim().length() > 0) {
            precioXKg = Double.valueOf(caracteres.toString());

        }

        mLinea.setPrKiloUnitario(precioXKg);
        if (mListaPrecioDetalle != null) {
            setearPrecios(mListaPrecioDetalle);
        }

        actualizarPrecio();
    }

    private void initComponents() {
        initBaseComponents();
        initTipoHaciendaComponents();
    }


    private void setEditPrecio() {
        boolean listaLibre = mTipoLista == ListaPrecio.TIPO_LISTA_LIBRE;
        txtPrecioKilo.setClickable(listaLibre);
        txtPrecioKilo.setCursorVisible(listaLibre);
        txtPrecioKilo.setFocusable(listaLibre);
        txtPrecioKilo.setFocusableInTouchMode(listaLibre);
        findViewById(R.id.lblPrecioKilo).setEnabled(listaLibre);
        txtPrecioKilo.setEnabled(listaLibre);
        txtKilogramos.setEnabled(CategoriaRecursoHumano.isRepartidorDeHacienda());

        if (listaLibre) {
            txtPrecioKilo.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        } else {
            double kg = mLinea.getArticulo().getCantidadKilos() * mLinea.getUnidades();
            txtKilogramos.setText(String.valueOf(kg));
            txtPrecioKilo.setText(String.valueOf(mLinea.getArticulo().getPrecioBase()));
            txtPrecioKilo.setInputType(InputType.TYPE_NULL);
        }
    }

    private void onChangePrecio(CharSequence sec, int start, int end, int count) {
        double precio = 0d;
        if (sec.toString().trim().length() > 0) {
            mLinea.setPrKiloUnitario(precio);
        }
        actualizarPrecio();
    }

    private void initVar() {
        _repoFactory = RepositoryFactory.getRepositoryFactory(getApplicationContext(), RepositoryFactory.ROOM);
        this._articuloBo = new ArticuloBo(_repoFactory);
        //carga los articulos para el autocomplete txtDescripcion
        ArticuloBo articuloBo = new ArticuloBo(_repoFactory);
        List<Articulo> articulos = null;
        try {
            articulos = articuloBo.recoveryAll();
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "initVar", e, this);
        }
        _adapter = new ArticuloAdapter(this, R.layout.lyt_articulo_lista_item, articulos);
        aCTxtDescripcion.setAdapter(_adapter);
        this._adapter.sort();
        this._adapter.notifyDataSetChanged();
    }

    public void setModo(int modo) {
        this.modo = modo;
    }

    private void txtCodigoOnFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            this._adapter.setCampoFiltro(Preferencia.ARTICULO_CODIGO);
        }
    }

    private void txtDescripcionOnFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            this._adapter.setCampoFiltro(Preferencia.ARTICULO_DESCRIPCION);
        }
    }

    private void txtCodigoOnTextChanged(String s) {
        if (_adapter.getCampoFiltro() == Preferencia.ARTICULO_CODIGO) {
            String codigo = s;
            this._adapter.getFilter().filter(codigo, new FilterListener() {
                public void onFilterComplete(int count) {
                    txtCodigoOnFilterComplete(count);
                }
            });
        }
    }

    private void txtCodigoOnFilterComplete(int count) {
        if (count == 1) {
            Articulo articulo = (Articulo) this._adapter.getItem(0);
            this.mLinea.setArticulo(articulo);
            this.aCTxtDescripcion.setText(articulo.getDescripcion());
            this.actualizarDatosArticulo();
        } else {
            this.mLinea.setArticulo(null);
            this.limpiarDatos();
        }
    }

    public void txtDescripcionOnItemClick(AdapterView<?> adapter, View view, int position, long id) {
        Articulo articuloSeleccionado = (Articulo) adapter.getItemAtPosition(position);
        this.mLinea.setArticulo(articuloSeleccionado);
        this.txtCodigo.setText(articuloSeleccionado.getCodigoEmpresa());
        this.actualizarDatosArticulo();
        txtUnidades.requestFocus();
    }

    private void actualizarListasPrecio() {
        Articulo articulo = this.mLinea.getArticulo();
        ArticuloBo articuloBo = new ArticuloBo(_repoFactory);
        DocumentosListaBo documentosListaBo = new DocumentosListaBo(_repoFactory);
        try {
            List<ListaPrecioDetalle> listasPrecioDetalle = articuloBo.recoveryListaPrecioToMobile(articulo);
            articulo.setListaPreciosDetalle(listasPrecioDetalle);

            List<Boolean> listValid = documentosListaBo.getValidList(listasPrecioDetalle, _pkVenta);

            ListaPrecioDetalleAdapter adapter = new ListaPrecioDetalleAdapter(this, R.layout.simple_spinner_item, listasPrecioDetalle, listValid);
            this.cmbListaPrecio.setAdapter(adapter);
            setListaPrecioDefecto(articulo);
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "actualizarListasPrecio", e, this);
        }
    }

    private void setListaPrecioDefecto(Articulo articulo) {
        //Listas de precio disponible para ese articulo
        List<ListaPrecioDetalle> listasPrecioDetalle = articulo.getListaPreciosDetalle();
        //Selecciona la lista por defecto
        ListaPrecio listaPorDefecto = null;

        if (this.getModo() == ALTA) {
            listaPorDefecto = this._listaPrecioXDefecto;
        } else {
            listaPorDefecto = this.mLinea.getListaPrecio();
        }

        if (listaPorDefecto != null) {
            int size = listasPrecioDetalle.size();
            DocumentosListaBo documentosListaBo = new DocumentosListaBo(_repoFactory);
            for (int i = 0; i < size; i++) {
                ListaPrecioDetalle listaPrecioDetalle = listasPrecioDetalle.get(i);
                int idListaPrecio = listaPorDefecto.getId();
                if (articulo.isPromocion()) {
                    idListaPrecio = listaPorDefecto.getListaBase();
                }
                PkDocumentosLista pk = new PkDocumentosLista();
                pk.setIdDocumento(_pkVenta.getIdDocumento());
                pk.setIdLetra(_pkVenta.getIdLetra());
                pk.setIdLista(idListaPrecio);
                pk.setIdPtovta(_pkVenta.getPuntoVenta());
                boolean listIsValid = true;
                try {
                    listIsValid = documentosListaBo.isValid(pk) || (modo == MODIFICACION);
                } catch (Exception e) {

                    e.printStackTrace();
                }
                if (listaPrecioDetalle.getListaPrecio().getId() == idListaPrecio && listIsValid) {
                    this.listaPrecioDetalleDefecto = listaPrecioDetalle;
                    if (getTipoEmpresa() == TipoEmpresaCode.TYPE_HACIENDA) {
                        mListaPrecioDetalle = listaPrecioDetalle;
                        this.mLinea.setListaPrecio(mListaPrecioDetalle.getListaPrecio());
                    }
                    this.cmbListaPrecio.setSelection(i);
                    break;

                }
            }
        }
    }

    /**
     * Setea la linea para modificar
     *
     * @param linea linea a modificar
     */
    public void setLinea(VentaDetalle linea) {
        this.mLinea = linea;
        String codigo = this.mLinea.getArticulo().getCodigoEmpresa();
        String descripcion = this.mLinea.getArticulo().getDescripcion();
        this.txtCodigo.setText(codigo);
        this.aCTxtDescripcion.setText(descripcion);
        this.txtUnidades.requestFocus();
    }

    private void actualizarDatosArticulo() {
        double precioUnitario = mLinea.getPrecioUnitarioSinIvaNoBo();
        Articulo articulo = this.mLinea.getArticulo();
        if (articulo != null) {
            String sBultos = String.valueOf(this.mLinea.getBultos());
            String sUnidades = "0";

            if (articulo.getSnUnidad() != null) {
                if (articulo.getSnUnidad().equals("S")) {
                    sUnidades = String.valueOf((int) this.mLinea.getUnidades());
                } else {
                    sUnidades = String.valueOf(this.mLinea.getUnidades());
                }
            } else {
                sUnidades = String.valueOf((int) this.mLinea.getUnidades());
            }

            double descuento = this.mLinea.getTasaDescuento();
            if (descuento == 0) {
                descuento = descuento * 100;
            } else {
                descuento = descuento * -1 * 100;
            }

            if (mTipoLista == ListaPrecio.TIPO_LISTA_LIBRE) {
                txtKilogramos.setText(String.valueOf(mLinea.getCaArticulosKilos()));
                String nroTropa = (mLinea.getNumeroTropa() == null || mLinea.getNumeroTropa() == 0) ? "" : mLinea.getNumeroTropa().toString();
                txtNroTropa.setText(nroTropa);
            } else {
                this.txtKilogramos.setText(String.valueOf(articulo.getCantidadKilos()));
            }

            if (mTipoLista == ListaPrecio.TIPO_LISTA_LIBRE) {
                if (isVendedor() && modo == ALTA) {
                    txtPrecioKilo.setText(String.valueOf(mLinea.getArticulo().getPrecioBase()));
                } else {
                    double cantidad = mLinea.getCantidad();
                    double cantidadKilos = mLinea.getCaArticulosKilos();

                    double tasaIva = mLinea.getArticulo().getTasaIva();
                    txtPrecioKilo.setText(String.valueOf(VentaBo.getPrecioKgListaLibre(cantidad, cantidadKilos, precioUnitario, tasaIva)));

                }
            } else {

                txtPrecioKilo.setText(String.valueOf(mLinea.getArticulo().getPrecioBase()));
            }
            if (isRepartidor() && modo == MODIFICACION) {
                txtPrecioKilo.setText(String.valueOf(mLinea.getPrKiloUnitario()));

            }
            txtUnidades.setText(String.valueOf(mLinea.getCantidad()));
            this.actualizarListasPrecio();
            this.actualizarPrecio();
            this.actualizarStock();
        }
    }

    public void btnCancelarOnClick() {
        this.finish();
    }

    public void cmbListaPrecioOnItemSelected(AdapterView<?> adapter, View view, int position, long id) {
        mListaPrecioDetalle = (ListaPrecioDetalle) adapter.getItemAtPosition(position);
        if (listaPrecioDetalleDefecto != null) {
            this.aCTxtDescripcion.setText(this.mLinea.getArticulo().getDescripcion());
            if (validateVigenciaCombos(mListaPrecioDetalle)) {
                boolean isValid = true;
                try {
                    isValid = mDocumentosListaBo.isValid(_pkVenta.getIdDocumento(), _pkVenta.getIdLetra(), _pkVenta.getPuntoVenta(), mListaPrecioDetalle.getListaPrecio().getId());
                } catch (Exception e) {

                    e.printStackTrace();
                }
                if (!isValid && modo == MODIFICACION) {
                    ((TextView) view).setError("");
                } else {
                    ((TextView) view).setError(null);

                }
                if (mListaPrecioDetalle.getListaPrecio().getTipoLista() != ListaPrecio.TIPO_LISTA_COMBOS_ESPECIALES) {
                    //Si no es combo especial, hago el control
                    if (mListaPrecioDetalle.getPrecioFinal() >= listaPrecioDetalleDefecto.getPrecioFinal()) {
                        this.mLinea.setListaPrecio(mListaPrecioDetalle.getListaPrecio());

                        setearPrecios(mListaPrecioDetalle);
                        this.actualizarPrecio();
                    } else {
                        errorListaNoDisponibleCliente();
                        //busco la lista de precio configurada en el comprobante si es que esta configurado sino tomo el del cliente
                        if (mVenta.getDocumento() != null && mVenta.getDocumento().getIdListaDefault() != null && mVenta.getDocumento().getIdListaDefault() > 0) {
                            try {
                                ListaPrecioBo listaPrecioBo = new ListaPrecioBo(_repoFactory);
                                this._listaPrecioXDefecto = listaPrecioBo.recoveryById(mVenta.getDocumento().getIdListaDefault());
                            } catch (Exception e) {
                                errorListaDocumentoNoDisponibleCliente();
                                e.printStackTrace();
                            }
                        } else {
                            this._listaPrecioXDefecto = this._cliente.getListaPrecio();
                        }
                        setListaPrecioDefecto(mLinea.getArticulo());
                    }
                } else {
                    this.mLinea.setListaPrecio(mListaPrecioDetalle.getListaPrecio());
                    setearPrecios(mListaPrecioDetalle);
                    this.actualizarPrecio();
                }
            } else {
                errorComboNoVigente();
            }
        } else {
            errorRecuperarListaCliente();
        }

    }

    private void setearPrecios(ListaPrecioDetalle listaPrecioDetalle) {
        try {
            VentaBo.setearPreciosDetalle(_cliente, mLinea, listaPrecioDetalle, getApplicationContext());
            mTipoLista = mLinea.getListaPrecio().getTipoLista();

        } catch (Exception e) {
            errorSetearPreciosDetalle();
        }
    }

    private void errorSetearPreciosDetalle() {
        AlertDialog dialog = new AlertDialog(this, getString(R.string.tituloError), ErrorManager.ErrorSetearPreciosDetalle);
        dialog.setPositiveButton(R.string.btnAceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                FrmAgregarLineaHacienda.this.finish();

            }
        });
        dialog.show();
    }

    private void errorListaNoDisponibleCliente() {
        AlertDialog dialog = new AlertDialog(this, getString(R.string.tituloError), ErrorManager.ListaPrecioNoDisponible);
        dialog.show();
    }

    private void errorComboNoVigente() {
        AlertDialog dialog = new AlertDialog(this, getString(R.string.tituloNoVigente), ErrorManager.ErrorComboNoVigente);
        dialog.setPositiveButton(R.string.btnAceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                FrmAgregarLineaHacienda.this.finish();

            }
        });

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    FrmAgregarLineaHacienda.this.finish();
                    return true;
                }
                return false;
            }
        });
        dialog.show();
    }

    private void errorRecuperarListaCliente() {
        AlertDialog dialog = new AlertDialog(this, getString(R.string.tituloError), ErrorManager.ErrorAlRecuperarLaListaPrecio);
        dialog.setPositiveButton(R.string.btnAceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                FrmAgregarLineaHacienda.this.finish();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    private boolean validateVigenciaCombos(ListaPrecioDetalle lista) {
        boolean isComboVigente = false;
        if ((lista.getListaPrecio().getTipoLista() == ListaPrecio.TIPO_LISTA_COMBOS_COMUNES) ||
                (lista.getListaPrecio().getTipoLista() == ListaPrecio.TIPO_LISTA_COMBOS_ESPECIALES)) {
            if ((lista.getFechaVigenciaDesde() == null) ||
                    (lista.getFechaVigenciaHasta() == null)) {

                isComboVigente = true;

            } else {
                if (ComparatorDateTime.isWithinRange(new Date(), lista.getFechaVigenciaDesde(), lista.getFechaVigenciaHasta())) {
                    isComboVigente = true;
                }
            }
        } else {
            isComboVigente = true;
        }
        return isComboVigente;
    }

    private void txtUnidadesOnTextChanged(CharSequence s, int start, int before, int count) {
        Double unidades = 0D;
        if (this.mLinea.getArticulo() != null) {
            if (this.mLinea.getArticulo().getSnUnidad() != null) {
                if (this.mLinea.getArticulo().getSnUnidad().equals("S")) {
                    this.txtUnidades.setInputType(InputType.TYPE_CLASS_NUMBER);
                    unidades = txtIngresoCantidadEntera(s);
                } else {
                    this.txtUnidades.setInputType(InputType.TYPE_CLASS_PHONE);
                    unidades = txtIngresoCantidadDecimal();
                }
            } else {
                this.txtUnidades.setInputType(InputType.TYPE_CLASS_NUMBER);
                unidades = txtIngresoCantidadEntera(s);
            }
        } else {
            unidades = txtIngresoCantidadEntera(s);
        }

        this.mLinea.setUnidades(unidades);
        if (CategoriaRecursoHumano.isVendedorDeHacienda()) {
            if (mLinea.getArticulo() != null) {
                double kg = mLinea.getArticulo().getCantidadKilos() * unidades;
                txtKilogramos.setText(String.valueOf(kg));
            }

        }

        actualizarPrecio();
    }

    private Double txtIngresoCantidadDecimal() {
        Double cantidad = 0D;
        try {
            String sUnidades = txtUnidades.getText().toString();
            if (sUnidades.length() > 0) {
                cantidad = Double.valueOf(sUnidades);
            }
        } catch (NumberFormatException e) {
            AlertDialog alert = new AlertDialog(this, getString(R.string.tituloError), ErrorManager.CantidadInvalida);
            alert.show();
        }
        return cantidad;
    }

    private Double txtIngresoCantidadEntera(CharSequence s) {
        Double cantidad = 0D;
        if (s.length() > 0) {
            String sUnidades = txtUnidades.getText().toString();
            if (sUnidades.length() > 0) {
                cantidad = Double.valueOf(sUnidades);
            }
        }
        if (cantidad % 1 != 0) {
            AlertDialog alert = new AlertDialog(this, "Error", ErrorManager.IngresarNumerosEnteros);
            alert.show();
        }
        return cantidad;
    }

    private void limpiarDatos() {
        this.aCTxtDescripcion.setText("");
        this.txtStock.setText("");
    }


    private void actualizarStock() {
        if (PreferenciaBo.getInstance().getPreferencia(getApplicationContext()).isDescargaStockAutomatico()) {
            long idVendedor = PreferenciaBo.getInstance().getPreferencia(this).getIdVendedor();
            UpdateStockTask updateStockTask = new UpdateStockTask(this, this.mLinea.getArticulo(), idVendedor, getApplicationContext(), mVenta.getDocumento().getId());
            updateStockTask.execute();
        } else {
            double stock = this.mLinea.getArticulo().getStock();
            this.txtStock.setText(String.valueOf(stock));
        }
    }

    private void actualizarPrecio() {

        Articulo articulo = this.mLinea.getArticulo();
        if (articulo != null) {

            double cantidad = this.mLinea.getCantidad();
            double precioUnitarioSinIva;
            precioUnitarioSinIva = VentaBo.calcularPrecioUnitarioSinIva(mLinea, CategoriaRecursoHumano.getIdCategoriaRecursoHumano(), modo, mTipoLista);

            double tasaImpuestoInterno = this.mLinea.getArticulo().getTasaImpuestoInterno();
            double impuestoInterno = precioUnitarioSinIva * tasaImpuestoInterno;
            double tasaIva = articulo.getTasaIva();
            double precioIvaUnitario = precioUnitarioSinIva * tasaIva;
            double precioUnitarioConIva = precioUnitarioSinIva;

            precioUnitarioConIva = precioUnitarioSinIva + precioIvaUnitario;

            double precioFinal = precioUnitarioConIva + impuestoInterno;

            double subtotal = Double.isNaN(cantidad * (precioFinal)) ? 0 : cantidad * precioFinal;

            mLinea.setPrecioIvaUnitario(precioIvaUnitario);
            mLinea.setPrecioImpuestoInterno(impuestoInterno);
            mLinea.setPrecioUnitarioSinDescuento(precioUnitarioSinIva);
            mLinea.setPrecioUnitarioSinDescuentoCliente(precioUnitarioSinIva);
            mLinea.setPrecioUnitarioSinDescuentoProveedor(precioUnitarioSinIva);
            mLinea.setPrecioUnitarioSinIva(precioUnitarioSinIva);

            txtSubtotal.setText(Formatter.formatMoney(subtotal));

        }

    }

    public VentaDetalle getVentaDetalle() {
        return mLinea;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mni_buscar_articulo:
                buscarArticulo();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void buscarArticulo() {
        Intent intent = new Intent(this, FrmPreciosXLista.class);
        intent.putExtra("listaPrecio", this._listaPrecioXDefecto);
        intent.putExtra(FrmVenta.EXTRA_VENTA, mVenta);
        startActivityForResult(intent, ACTIVITY_PRECIOS_X_LISTA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_SELECCIONAR_LINEA) {
            //Pongo el foco sobre el txtCodigo para evitar problemas
            this.txtCodigo.setFocusable(true);
            //Recibo la Lista Precio Detalle
            ListaPrecioDetalle listaPrecioDetalle = (ListaPrecioDetalle) data.getSerializableExtra("listaPrecioDetalle");
            Articulo articulo = listaPrecioDetalle.getArticulo();
            if (articulo != null) {
                //Seteo el articulo a la linea
                this.mLinea.setArticulo(articulo);
                // Actualizo el stock
                this.actualizarStock();
                //Cargo las listas de precio
                actualizarListasPrecio();
                //Error #12523
                //this._listaPrecioXDefecto = listaPrecioDetalle.getListaPrecio();
                //Cargo el codigo de articulo en el txtCodigo
                this.txtCodigo.setText(articulo.getCodigoEmpresa());
                //Seteo la lista que recibi de FrmPreciosXLista
                setearComboListaPrecio(listaPrecioDetalle, articulo);
                //Seteo la variable para saber desde donde vine
                //Esta variable se usa en el boton aceptar
                ORIGEN_BUSCAR = true;
            }
        }
    }

    /**
     * Seteo la lista de precio que viene desde FrmPreciosXLista
     *
     * @param listaPrecioDetalle
     * @param articulo
     */
    private void setearComboListaPrecio(ListaPrecioDetalle listaPrecioDetalle, Articulo articulo) {
        //Listas de precio disponible para ese articulo
        ListaPrecioDetalleBo listaPrecioDetalleBo = new ListaPrecioDetalleBo(_repoFactory);
        List<ListaPrecioDetalle> listasPrecioDetalle = new ArrayList<>();
        try {
            listasPrecioDetalle = _articuloBo.recoveryListaPrecioToMobile(articulo);
            articulo.setListaPreciosDetalle(listasPrecioDetalle);
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "recoveryListasPrecioDetalle", e, this);
        }
        int size = listasPrecioDetalle.size();
        for (int i = 0; i < size; i++) {
            ListaPrecioDetalle listaPrecioDetalleAux = listasPrecioDetalle.get(i);
            long idListaPrecio = listaPrecioDetalleAux.getListaPrecio().getId();

            if (listaPrecioDetalle.getListaPrecio().getId() == idListaPrecio) {
                this.cmbListaPrecio.setSelection(i);
                break;
            }
        }

    }

    public int getModo() {
        return modo;
    }

    @Override
    public Object getLastNonConfigurationInstance() {

        return this.mLinea;
    }

    private void loadTextViews() {
        String kilogramosStr = txtKilogramos.getText().toString();
        Double kilogramos = (kilogramosStr.equals("")) ? 0 : Double.valueOf(kilogramosStr);
        mLinea.setCaArticulosKilos(kilogramos);
        String precioKg = txtPrecioKilo.getText().toString();
        mLinea.setPrKiloUnitario(Double.valueOf(precioKg));
        if (isRepartidor()) {
            String nroTropa = txtNroTropa.getText().toString().trim();
            if (!nroTropa.equals("")) {
                int numeroTropa = Integer.valueOf(nroTropa);
                mLinea.setNumeroTropa(numeroTropa);
                mLinea.setValid(true);
            } else if (nroTropa.equals("")) {
                mLinea.setNumeroTropa(null);
                mLinea.setValid(false);
            }
        }

    }

    public void btnAceptarOnClick() {
        loadTextViews();

        //Guardo el articulo seleccionado por si quiere vender de nuevo uno parecido
        Preferencia.setUltimoArticuloSeleccionado(this.mLinea.getArticulo());
        Intent intent = new Intent(this, DetalleVentaFragment.class);
        intent.putExtra("linea", this.mLinea);
        intent.putExtra("modo", ALTA);
        intent.putExtra("origen", ORIGEN_BUSCAR);
        setResult(RESULT_OK, intent);
        this.finish();

    }

    public void setStock(double stock) {
        String st = String.valueOf(stock);
        this.txtStock.setText(st);
    }

}
