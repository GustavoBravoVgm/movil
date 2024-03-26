package com.ar.vgmsistemas.view.venta;

import static com.ar.vgmsistemas.view.venta.DetalleVentaFragment.EXTRA_PROVEEDOR;

import android.app.Activity;
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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter.FilterListener;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.ArticuloBo;
import com.ar.vgmsistemas.bo.CuentaCorrienteBo;
import com.ar.vgmsistemas.bo.DocumentosListaBo;
import com.ar.vgmsistemas.bo.EmpresaBo;
import com.ar.vgmsistemas.bo.ListaPrecioBo;
import com.ar.vgmsistemas.bo.ListaPrecioDetalleBo;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.bo.PromocionBo;
import com.ar.vgmsistemas.bo.VentaBo;
import com.ar.vgmsistemas.bo.VentaDetalleBo;
import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.ListaPrecio;
import com.ar.vgmsistemas.entity.ListaPrecioDetalle;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.entity.Proveedor;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.entity.key.PkDocumentosLista;
import com.ar.vgmsistemas.entity.key.PkVenta;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.task.venta.UpdateStockTask;
import com.ar.vgmsistemas.utils.ColorUtils;
import com.ar.vgmsistemas.utils.ComparatorDateTime;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.utils.ItemMenuNames;
import com.ar.vgmsistemas.view.AlertDialog;
import com.ar.vgmsistemas.view.BaseActivity;
import com.ar.vgmsistemas.view.articulo.ArticuloAdapter;
import com.ar.vgmsistemas.view.articulo.DlgBldrCombo;
import com.ar.vgmsistemas.view.articulo.FrmPreciosXLista;
import com.ar.vgmsistemas.view.informes.FrmVentasXArticulo;
import com.ar.vgmsistemas.view.informes.objetivos.VendedorObjetivoFragment;
import com.ar.vgmsistemas.view.venta.catalogo.CatalogoActivity;
import com.ar.vgmsistemas.view.venta.combo.CargaComboDialogFragment;
import com.ar.vgmsistemas.view.venta.combo.CargaComboDialogFragment.ComboListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrmAgregarLinea extends BaseActivity {

    private static final int MODIFICACION = 1;
    private static final int ACTIVITY_PRECIOS_X_LISTA = 1233;
    private static final int ACTIVITY_CATALOGO = 1234;
    private static final String TAG = FrmAgregarLinea.class.getCanonicalName();
    private static final int RESULT_SELECCIONAR_LINEA = 1;
    private static final String VENDEDOR_OBJETIVO = "vendedor_objetivo_fragment";

    public static final String EXTRA_LIST_VALID = "list_no_valid";
    public static final String EXTRA_IS_COMBO = "is_combo";
    public static final int ALTA = 0;
    public static final String KEY_VENTA = "Venta";
    public static String EXTRA_LINEA = "linea";


    private List<ListaPrecioDetalle> ListaPrecioDetallesConRepetidos;

    private AutoCompleteTextView aCTxtDescripcion;
    private Button btnVerDetallePromocion;
    private AutoCompleteTextView txtCodigo;
    private EditText txtPrecio;
    private EditText txtPrecioKilo;
    private EditText txtBultos;
    private EditText txtUnidades;
    private EditText txtSubtotal;
    private EditText txtDescuento;
    private EditText txtStock;
    private Spinner cmbListaPrecio;
    private TextView lblUnidadPorBulto;
    private TextView lblPorcentajeDescuentoProveedor;
    private TextView lblPorcentajeDescuentoCliente;
    private double mSubTotal;
    private double mSubTotalOriginal;
    ListaPrecioDetalle mListaPrecioDetalle;

    private int modo = 0; // para saber si es alta o modificacion
    private boolean ORIGEN_BUSCAR = false;
    private ArticuloAdapter _adapter;
    private VentaDetalle _linea;
    private PkVenta _pkVenta;
    private ListaPrecio _listaPrecioXDefecto;
    private Cliente mCliente;
    private Venta mVenta;
    private ListaPrecioDetalle listaPrecioDetalleDefecto;
    private CargaComboDialogFragment mDialogFragment;

    //BO´s
    private DocumentosListaBo _documentosListaBo;
    private EmpresaBo _empresaBo;
    private ArticuloBo _articuloBo;
    //DAO
    private RepositoryFactory _repoFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lyt_venta_agregar_linea);

        Bundle b = this.getIntent().getExtras();
        boolean catalogo = b.getBoolean("Catalogo", false);
        setActionBarTitle(ItemMenuNames.STRING_AGREGAR_LINEA);

        mVenta = (Venta) b.getSerializable(FrmVenta.EXTRA_VENTA);
        boolean isCombo = b.getBoolean(EXTRA_IS_COMBO, false);
        boolean isOrigenBuscar = b.getBoolean(FrmPreciosXLista.ORIGEN, false);
        mCliente = mVenta.getCliente();
        _pkVenta = mVenta.getId();

        initComponents();
        initVar();


        if (savedInstanceState != null) {
            _linea = (VentaDetalle) savedInstanceState.getSerializable(EXTRA_LINEA);
            if (_linea.getArticulo() != null)
                actualizarListasPrecio();
            else
                _linea = null;
        } else
            _linea = (VentaDetalle) b.getSerializable(EXTRA_LINEA);

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
            this._listaPrecioXDefecto = mCliente.getListaPrecio();
        }

        //busco si hay que cambiar el color de fondo
        if (mVenta != null && mVenta.getDocumento() != null &&
                mVenta.getDocumento().getTiModifVisualMovil() != null &&
                (!mVenta.getDocumento().getTiModifVisualMovil().equals("NO"))) {
            try {
                Window window = getWindow();
                ActionBar actBar = getSupportActionBar();
                String confDocumento = mVenta.getDocumento().getTiModifVisualMovil();
                int color; //Siempre inicializa en 0;
                //COLOR_VERDE("CV"),COLOR_AZUL("CA"), COLOR_AMARILLO("CY"), COLOR_NARANJA("CN");
                switch (confDocumento) {
                    case "CV":
                        color = getResources().getColor(R.color.color_verde);
                        break;
                    case "CA":
                        color = getResources().getColor(R.color.color_azul);
                        break;
                    case "CY":
                        color = getResources().getColor(R.color.color_amarillo);
                        break;
                    case "CN":
                        color = getResources().getColor(R.color.color_naranja);
                        break;
                    default:
                        color = getResources().getColor(R.color.action_bar);
                        break;
                }
                ColorUtils.cambioColor(window, actBar, ColorUtils.TipoCambio.CAMBIO_COLOR, color);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Es una nueva linea caso contrario una modificacion
        if (this._linea == null) {
            this._linea = new VentaDetalle();
            this.setModo(ALTA);
        } else {
            this.setModo(MODIFICACION);
            mSubTotalOriginal = _articuloBo.getSubtotal(_linea);

        }
        if (_linea.getArticulo() != null) {
            this.setLinea(this._linea);
            actualizarDatosArticulo();
        }

        Proveedor proveedor = (Proveedor) b.getSerializable(EXTRA_PROVEEDOR);
        if (PreferenciaBo.getInstance().getPreferencia(getApplicationContext()).isBusquedaPorListaArticulo()
                && this.getModo() != MODIFICACION && proveedor == null && savedInstanceState == null) {
            this.buscarArticulo(null);
        }
        if (isCombo) {
            showComboEspecialFragment();
        }

        if (proveedor != null)
            buscarArticulo(b);

        if (catalogo) {
            Intent intentCatalogo = new Intent(this, CatalogoActivity.class);
            intentCatalogo.putExtra(FrmVentasXArticulo.KEY_CLIENTES, mCliente);
            intentCatalogo.putExtra(FrmVentasXArticulo.KEY_VENTA, mVenta);
            startActivityForResult(intentCatalogo, ACTIVITY_CATALOGO);
        }
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            setActionBarTitle("Objetivos");
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            setActionBarTitle("Agregar linea");
            return;

        }

        super.onBackPressed();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(EXTRA_LINEA, _linea);
        if (mDialogFragment != null) {
            mDialogFragment.dismissAllowingStateLoss();
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.mn_agregar_linea_venta, menu);
        String snCatalogo = "";
        try {
            snCatalogo = _empresaBo.recoveryEmpresa().getSnCatalogo();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (snCatalogo == null || snCatalogo.equals("") || snCatalogo.equals("N")) {
            menu.findItem(R.id.mni_catalogo).setVisible(false);
        } else {
            menu.findItem(R.id.mni_catalogo).setVisible(true);
        }

        return super.onCreateOptionsMenu(menu);
    }

    private void initComponents() {
        setResult(RESULT_CANCELED);
        // btnVerDetallePromocion
        this.btnVerDetallePromocion = findViewById(R.id.btnVerDetallesPromocion);
        this.btnVerDetallePromocion.setOnClickListener(arg0 -> btnVerDetallePromocionOnClick());

        // txtCodigo(AutoCompleteTextView)
        this.txtCodigo = findViewById(R.id.txtCodigo);
        String tipoDatoArticulo = PreferenciaBo.getInstance().getPreferencia(getApplicationContext())
                .getTipoDatoIdArticulo();

        if (tipoDatoArticulo.equals("Numerico")) {
            this.txtCodigo.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            this.txtCodigo.setInputType(InputType.TYPE_CLASS_TEXT);
        }

        this.txtCodigo.setOnFocusChangeListener(this::txtCodigoOnFocusChange);
        txtCodigo.setOnItemClickListener(this::txtCodigoOnItemClick);

        // txtDescripcion(AutoCompleteTextView)
        aCTxtDescripcion = findViewById(R.id.txtDescripcion);
        aCTxtDescripcion.setTextColor(getResources().getColor(R.color.base_text_color));
        aCTxtDescripcion.setOnFocusChangeListener(this::txtDescripcionOnFocusChange);
        aCTxtDescripcion.setOnItemClickListener(this::txtDescripcionOnItemClick);

        // txtBultos(EditText)
        this.txtBultos = findViewById(R.id.txtBultos);
        this.txtBultos.setSelectAllOnFocus(true);
        this.txtBultos.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtBultosOnTextChanged(s, start, before, count);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });

        // txtUnidades(EditText)
        this.txtUnidades = findViewById(R.id.txtUnidades);
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

        // txtPrecio(EditText)
        this.txtPrecio = findViewById(R.id.txtPrecioUnitario);
        this.txtPrecio.setKeyListener(null);

        // txtPrecioKilo(EditText)
        this.txtPrecioKilo = findViewById(R.id.txtPrecioKilo);
        this.txtPrecioKilo.setKeyListener(null);

        // txtDescuento(EditText)
        this.txtDescuento = findViewById(R.id.txtDescuentos);
        this.txtDescuento.setSelectAllOnFocus(true);
        this.txtDescuento.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtDescuentoOnTextChanged(s, start, before, count);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {

            }
        });

        // txtSubtotal(EditText)
        this.txtSubtotal = findViewById(R.id.txtSubtotal);
        this.txtSubtotal.setKeyListener(null);
        //cmbListaPrecio(Spinner)
        this.cmbListaPrecio = findViewById(R.id.cmbListaPrecio);

        this.cmbListaPrecio.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cmbListaPrecioOnItemSelected(parent, view, position, id);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        // btnAceptar//(Button)
        Button btnAceptar = findViewById(R.id.btnAgregarLinea);
        btnAceptar.setOnClickListener(v -> {
            try {
                btnAceptarOnClick();
            } catch (Exception e) {

                e.printStackTrace();
            }
        });

        // btnCancelar(Button)
        Button btnCancelar = findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(v -> btnCancelarOnClick());

        // txtStock(EditText)
        txtStock = findViewById(R.id.txtStock);

        // lblUnidadPorBulto(TextView)
        lblUnidadPorBulto = findViewById(R.id.lblValueUnidadPorBulto);

        // lblPorcentajeDescuentoProveedor(TextView)
        lblPorcentajeDescuentoProveedor = findViewById(R.id.lblPorcentajeDescuentoProveedor);

        // lblPorcentajeDescuentoCliente(TextView)
        lblPorcentajeDescuentoCliente = findViewById(R.id.lblPorcentajeDescuentoCliente);

        // Valido Configuracion de Descuentos en los Pedidos
        validarConfiguracionDescuentosEnPedido();
    }

    private void validarConfiguracionDescuentosEnPedido() {
        // Consulto la tabla Empresas por la configuracion
        String descuento = PreferenciaBo.getInstance().getPreferencia().getIsDescuento();
        if (descuento == null) {
            // Se da cuando sn_descuento tiene el valor null en la tabla
            // empresas
            AlertDialog dialog = new AlertDialog(this, R.string.tituloError, R.string.msjErrorRecuperarDto);
            dialog.show();
            // por defecto le deshabilitamos el uso de descuentos
            this.txtDescuento.setEnabled(false);
        } else {
            // Si es N -> Que no le deje hacer descuentos
            if (descuento.equals("N")) {
                this.txtDescuento.setEnabled(false);
                this.txtDescuento.setKeyListener(null);
            }
        }

    }

    private void showNoDisponibilidadDialog() {
        AlertDialog alertDialog = new AlertDialog(this, ErrorManager.ERROR, ErrorManager.ErrorLimiteSaldoSuperado
                + mCliente.getLimiteDisponibilidad() + ErrorManager.LimiteDisponibilidadModificar);
        alertDialog.show();
    }

    private void initVar() {
        _repoFactory = RepositoryFactory.getRepositoryFactory(getApplicationContext(), RepositoryFactory.ROOM);
        // carga los articulos para el autocomplete txtDescripcion
        this._articuloBo = new ArticuloBo(_repoFactory);
        this._documentosListaBo = new DocumentosListaBo(_repoFactory);
        this._empresaBo = new EmpresaBo(_repoFactory);
        int idVendedor = PreferenciaBo.getInstance().getPreferencia().getIdVendedor();
        ArticuloBo articuloBo = new ArticuloBo(_repoFactory);
        List<Articulo> articulos = null;
        try {
            articulos = articuloBo.recoveryAllByCliente(mCliente, idVendedor);
            if (articulos == null || articulos.size() <= 0) {
                articulos = articuloBo.recoveryAll();
            }
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "initVar", e, this);
        }
        _adapter = new ArticuloAdapter(this, R.layout.lyt_articulo_lista_item, articulos);
        aCTxtDescripcion.setAdapter(_adapter);
        txtCodigo.setAdapter(_adapter);
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

    private void txtCodigoOnTextChanged(String codigo) {
        if (_adapter.getCampoFiltro() == Preferencia.ARTICULO_CODIGO) {
            this._adapter.getFilter().filter(codigo, new FilterListener() {
                public void onFilterComplete(int count) {
                    txtCodigoOnFilterComplete(count);
                }
            });
        }
    }

    private void txtCodigoOnFilterComplete(int count) {
        if (count == 1) {
            Articulo articulo = this._adapter.getItem(0);//(Articulo)
            this._linea.setArticulo(articulo);
            this.aCTxtDescripcion.setText(articulo.getDescripcion());
            this.actualizarDatosArticulo();
        } else {
            this._linea.setArticulo(null);
            this.limpiarDatos();
        }
    }

    public void txtDescripcionOnItemClick(AdapterView<?> adapter, View view, int position, long id) {
        Articulo articuloSeleccionado = (Articulo) adapter.getItemAtPosition(position);
        if (!articuloSeleccionado.getIsAlcohol() ||
                (articuloSeleccionado.getIsAlcohol() && estaHabilitadoParaVenderAlcohol())) {
            this._linea.setArticulo(articuloSeleccionado);
            this.txtCodigo.setText(articuloSeleccionado.getCodigoEmpresa());
            this.txtUnidades.setText("0");
            this._linea.setUnidades(0d);
            this.txtBultos.setText("0");
            this._linea.setBultos(0);
            this.actualizarDatosArticulo();
            txtUnidades.requestFocus();
        } else {
            showClienteNoHabilitadoParaAlcohol();
        }
    }

    public void txtCodigoOnItemClick(AdapterView<?> adapter, View view, int position, long id) {
        Articulo articuloSeleccionado = (Articulo) adapter.getItemAtPosition(position);
        if (!articuloSeleccionado.getIsAlcohol() ||
                (articuloSeleccionado.getIsAlcohol() && estaHabilitadoParaVenderAlcohol())) {
            this._linea.setArticulo(articuloSeleccionado);
            this.aCTxtDescripcion.setText(articuloSeleccionado.getDescripcion());
            txtCodigo.setText(articuloSeleccionado.getCodigoEmpresa());
            this.txtUnidades.setText("0");
            this._linea.setUnidades(0d);
            this.txtBultos.setText("0");
            this._linea.setBultos(0);
            this.actualizarDatosArticulo();
            txtUnidades.requestFocus();
        } else {
            showClienteNoHabilitadoParaAlcohol();
        }
    }

    private void actualizarListasPrecio() {
        Articulo articulo = this._linea.getArticulo();
        ArticuloBo articuloBo = new ArticuloBo(_repoFactory);
        DocumentosListaBo documentosListaBo = new DocumentosListaBo(_repoFactory);
        try {

            List<ListaPrecioDetalle> listasPrecioDetalle = articuloBo.recoveryListaPrecioToMobile(articulo);

            int size = listasPrecioDetalle.size();
            List<ListaPrecioDetalle> listaPrecioDetallesSinRepetidos = new ArrayList<>();
            //Por #44853
            Map<Integer, ListaPrecioDetalle> mapListPrecioDet = new HashMap<>(size);
            for (ListaPrecioDetalle lpd : listasPrecioDetalle) {
                mapListPrecioDet.put(lpd.getId().getIdLista(), lpd);
            }
            for (Map.Entry<Integer, ListaPrecioDetalle> itemLpd : mapListPrecioDet.entrySet()) {
                listaPrecioDetallesSinRepetidos.add(itemLpd.getValue());
            }

            articulo.setListaPreciosDetalle(listaPrecioDetallesSinRepetidos);
            this.setListaPrecioDetallesConRepetidos(listasPrecioDetalle);
            List<Boolean> listValid = documentosListaBo.getValidList(listaPrecioDetallesSinRepetidos, _pkVenta);
            ListaPrecioDetalleAdapter adapter = new ListaPrecioDetalleAdapter(this, R.layout.simple_spinner_item,
                    listaPrecioDetallesSinRepetidos, listValid);
            this.cmbListaPrecio.setAdapter(adapter);

            setListaPrecioDefecto(articulo);
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "actualizarListasPrecio", e, this);
        }
    }

    private void setListaPrecioDefecto(Articulo articulo) {
        // Listas de precio disponible para ese articulo
        List<ListaPrecioDetalle> listasPrecioDetalle = articulo.getListaPreciosDetalle();
        // Selecciona la lista por defecto
        ListaPrecio listaPorDefecto;

        if (this.getModo() == ALTA) {
            listaPorDefecto = this._listaPrecioXDefecto;
        } else {
            listaPorDefecto = this._linea.getListaPrecio();
        }

        if (listaPorDefecto != null) {
            int size = listasPrecioDetalle.size();
            DocumentosListaBo documentosListaBo = new DocumentosListaBo(_repoFactory);
            for (int i = 0; i < size; i++) {
                ListaPrecioDetalle listaPrecioDetalle = listasPrecioDetalle.get(i);
                int idListaPrecio = listaPorDefecto.getId();
                if (articulo.isPromocion() && (!articulo.getSnKit().equals("S"))) {
                    idListaPrecio = listaPorDefecto.getListaBase();
                }
                if (articulo.isPromocion() && (articulo.getSnKit().equals("S"))) {
                    /*tratamiento del kit*/
                    idListaPrecio = listaPorDefecto.getId();
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
        this._linea = linea;
        String codigo = this._linea.getArticulo().getCodigoEmpresa();
        String descripcion = this._linea.getArticulo().getDescripcion();
        this.txtCodigo.setText(codigo);
        this.aCTxtDescripcion.setText(descripcion);
        this.txtUnidades.requestFocus();
    }

    /**
     * Actualiza los campos de texto con los datos del articulo
     * <p>
     * <p>
     * es el componente que llama
     */
    private void actualizarDatosArticulo() {
        Articulo articulo = this._linea.getArticulo();
        if (articulo != null) {
            String sBultos = String.valueOf(this._linea.getBultos());
            String sUnidades = "0";

            if (articulo.getSnUnidad() != null) {
                if (articulo.getSnUnidad().equals("S")) {
                    sUnidades = String.valueOf((int) this._linea.getUnidades());
                } else {
                    sUnidades = String.valueOf(this._linea.getUnidades());
                }
            } else {
                sUnidades = String.valueOf((int) this._linea.getUnidades());
            }

            double descuento = this._linea.getTasaDescuento();
            if (descuento == 0) {
                descuento = descuento * 100;
            } else {
                descuento = descuento * -1 * 100;
            }

            String sDescuento = String.valueOf(descuento);

            String sUnidadesXBulto = String.valueOf(this._linea.getArticulo().getUnidadPorBulto());

            this.txtBultos.setText(sBultos);
            this.txtUnidades.setText(sUnidades);
            this.txtDescuento.setText(sDescuento);
            this.lblUnidadPorBulto.setText(sUnidadesXBulto);

            this.actualizarListasPrecio();
            if (articulo.isPromocion()) {
                PromocionBo promocionBo = new PromocionBo(_repoFactory);
                promocionBo.setearImpuestoInternoCabeceraCombo(articulo, (ListaPrecioDetalle) cmbListaPrecio.getSelectedItem());
            }
            this.actualizarPrecio(_linea);
            this.actualizarStock();
        }
    }

    private void btnVerDetallePromocionOnClick() {
        Intent intent = new Intent(this, DlgBldrCombo.class);
        intent.putExtra("promocion", this._linea.getArticulo());
        intent.putExtra("listaPrecioXDefecto", this._listaPrecioXDefecto);
        intent.putExtra("listaPrecioPromocion", this._linea.getListaPrecio());
        startActivity(intent);
    }

    public void btnCancelarOnClick() {
        this.finish();
    }

    public void cmbListaPrecioOnItemSelected(AdapterView<?> adapter, View view, int position, long id) {
        mListaPrecioDetalle = (ListaPrecioDetalle) adapter.getItemAtPosition(position);
        ListaPrecioBo listaPrecioBo = new ListaPrecioBo(_repoFactory);
        try {
            if (listaPrecioBo.recoveryById(mListaPrecioDetalle.getListaPrecio().getId()).getTipoLista() == 9) {
                long size = this.getListaPrecioDetallesConRepetidos().size();

                for (int i = 0; i < size; i++) {
                    ListaPrecioDetalle listaPrecioDetalleAux = this.getListaPrecioDetallesConRepetidos().get(i);

                    if (listaPrecioDetalleAux.getId().getIdLista() == mListaPrecioDetalle.getId().getIdLista() &&
                            this._linea.getCantidad() >= listaPrecioDetalleAux.getId().getCaArticuloDesde() &&
                            this._linea.getCantidad() <= listaPrecioDetalleAux.getId().getCaArticuloHasta()
                    ) {
                        mListaPrecioDetalle = listaPrecioDetalleAux;
                        break;
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (listaPrecioDetalleDefecto != null) {
            this.aCTxtDescripcion.setText(this._linea.getArticulo().getDescripcion());
            if (validateVigenciaCombos(mListaPrecioDetalle)) {
                boolean isValid = true;
                try {
                    isValid = _documentosListaBo.isValid(_pkVenta.getIdDocumento(), _pkVenta.getIdLetra(),
                            _pkVenta.getPuntoVenta(), mListaPrecioDetalle.getListaPrecio().getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (view != null) {
                    if (!isValid && modo == MODIFICACION) {
                        ((TextView) view).setError("");
                    } else {
                        ((TextView) view).setError(null);
                    }
                    if (!ListaPrecioDetalleBo.havePrice(mListaPrecioDetalle)) {
                        ((TextView) view).setError("");

                    } else {

                        ((TextView) view).setError(null);

                    }
                }
                if (mListaPrecioDetalle.getListaPrecio().getSnSeleccionable().equals("S") ||
                        mListaPrecioDetalle.getPrecioFinal() >= listaPrecioDetalleDefecto.getPrecioFinal()
                        || !mListaPrecioDetalle.getListaPrecio().getIdOrigenPrecio()
                        .equals(ListaPrecio.ID_ORIGENPRECIO_A)) {
                    this._linea.setListaPrecio(mListaPrecioDetalle.getListaPrecio());
                    setearPrecios(mListaPrecioDetalle);
                    this.actualizarPrecio(_linea);
                } else {
                    errorListaNoDisponibleCliente();
                    //busco la lista de precio configurada en el comprobante si es que esta configurado sino tomo el del cliente
                    if (mVenta.getDocumento() != null && mVenta.getDocumento().getIdListaDefault() != null && mVenta.getDocumento().getIdListaDefault() > 0) {
                        try {
                            listaPrecioBo = new ListaPrecioBo(_repoFactory);
                            this._listaPrecioXDefecto = listaPrecioBo.recoveryById(mVenta.getDocumento().getIdListaDefault());
                        } catch (Exception e) {
                            errorListaDocumentoNoDisponibleCliente();
                            e.printStackTrace();
                        }
                    } else {
                        this._listaPrecioXDefecto = mCliente.getListaPrecio();
                    }
                    setListaPrecioDefecto(_linea.getArticulo());
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
            VentaBo.setearPreciosDetalle(mCliente, _linea, listaPrecioDetalle, getApplicationContext());

        } catch (Exception e) {
            errorSetearPreciosDetalle();
            e.printStackTrace();
        }
    }

    private void errorSetearPreciosDetalle() {
        AlertDialog dialog = new AlertDialog(this, getString(R.string.tituloError),
                ErrorManager.ErrorSetearPreciosDetalle);
        dialog.setPositiveButton(R.string.btnAceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                FrmAgregarLinea.this.finish();

            }
        });
        dialog.show();
    }

    private void errorListaNoDisponibleCliente() {
        AlertDialog dialog = new AlertDialog(this, getString(R.string.tituloError),
                ErrorManager.ListaPrecioNoDisponible);
        dialog.show();
    }


    private void errorListaDocumentoNoDisponibleCliente() {
        AlertDialog dialog = new AlertDialog(this, getString(R.string.tituloError),
                ErrorManager.ListaPrecioDocumentoNoDisponible);
        dialog.show();
    }

    private void errorComboNoVigente() {
        AlertDialog dialog = new AlertDialog(this, getString(R.string.tituloNoVigente),
                ErrorManager.ErrorComboNoVigente);
        dialog.setPositiveButton(R.string.btnAceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                FrmAgregarLinea.this.finish();

            }
        });

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    FrmAgregarLinea.this.finish();
                    return true;
                }
                return false;
            }
        });
        dialog.show();
    }

    private void errorRecuperarListaCliente() {
        AlertDialog dialog = new AlertDialog(this, getString(R.string.tituloError),
                ErrorManager.ErrorAlRecuperarLaListaPrecio);
        dialog.setPositiveButton(R.string.btnAceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                FrmAgregarLinea.this.finish();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    private boolean validateVigenciaCombos(ListaPrecioDetalle lista) {
        boolean isComboVigente = false;
        if ((lista.getListaPrecio().getTipoLista() == ListaPrecio.TIPO_LISTA_COMBOS_COMUNES)
                || (lista.getListaPrecio().getTipoLista() == ListaPrecio.TIPO_LISTA_COMBOS_ESPECIALES)) {
            if ((lista.getFechaVigenciaDesde() == null) || (lista.getFechaVigenciaHasta() == null)) {

                isComboVigente = true;

            } else {
                if (ComparatorDateTime.isWithinRange(new Date(), lista.getFechaVigenciaDesde(),
                        lista.getFechaVigenciaHasta())) {
                    isComboVigente = true;
                }
            }
        } else {
            isComboVigente = true;
        }
        return isComboVigente;
    }

    private void txtBultosOnTextChanged(CharSequence s, int start, int before, int count) {
        int bultos = 0;
        if (this._linea.getArticulo() != null) {
            if (this._linea.getArticulo().isPesable() == true) {
                this.txtBultos.setEnabled(false);
                // this.txtBultos.setKeyListener(null);
            } else {
                this.txtBultos.setEnabled(true);
                if (s.length() > 0) {
                    String sBultos = txtBultos.getText().toString();
                    if (sBultos.length() > 0)
                        bultos = Integer.valueOf(sBultos);
                }
                this._linea.setBultos(bultos);
                if (mListaPrecioDetalle != null) {
                    ListaPrecioBo listaPrecioBo = new ListaPrecioBo(_repoFactory);
                    try {
                        if (listaPrecioBo.recoveryById(mListaPrecioDetalle.getId().getIdLista()).getTipoLista() == 9) {
                            long size = this.getListaPrecioDetallesConRepetidos().size();

                            for (int i = 0; i < size; i++) {
                                ListaPrecioDetalle listaPrecioDetalleAux = this.getListaPrecioDetallesConRepetidos().get(i);

                                if (listaPrecioDetalleAux.getId().getIdLista() == mListaPrecioDetalle.getId().getIdLista() &&
                                        this._linea.getCantidad() >= listaPrecioDetalleAux.getId().getCaArticuloDesde() &&
                                        this._linea.getCantidad() <= listaPrecioDetalleAux.getId().getCaArticuloHasta()
                                ) {
                                    mListaPrecioDetalle = listaPrecioDetalleAux;
                                    break;
                                }
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    setearPrecios(mListaPrecioDetalle);
                }
                actualizarPrecio(_linea);
            }
        }

    }

    private void txtUnidadesOnTextChanged(CharSequence s, int start, int before, int count) {
        Double unidades = 0D;
        if (this._linea.getArticulo() != null) {
            if (this._linea.getArticulo().getSnUnidad() != null) {
                if (this._linea.getArticulo().getSnUnidad().equals("S")) {
                    this.txtUnidades.setInputType(InputType.TYPE_CLASS_NUMBER);
                    unidades = txtIngresoCantidadEntera(s);
                } else {
                    txtUnidades.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                    unidades = txtIngresoCantidadDecimal();
                }
            } else {
                this.txtUnidades.setInputType(InputType.TYPE_CLASS_NUMBER);
                unidades = txtIngresoCantidadEntera(s);
            }
        } else {
            unidades = txtIngresoCantidadEntera(s);
        }

        this._linea.setUnidades(unidades);
        if (mListaPrecioDetalle != null) {
            ListaPrecioBo listaPrecioBo = new ListaPrecioBo(_repoFactory);
            try {
                if (listaPrecioBo.recoveryById(mListaPrecioDetalle.getId().getIdLista()).getTipoLista() == 9) {
                    long size = this.getListaPrecioDetallesConRepetidos().size();

                    for (int i = 0; i < size; i++) {
                        ListaPrecioDetalle listaPrecioDetalleAux = this.getListaPrecioDetallesConRepetidos().get(i);

                        if (listaPrecioDetalleAux.getId().getIdLista() == mListaPrecioDetalle.getId().getIdLista() &&
                                this._linea.getCantidad() >= listaPrecioDetalleAux.getId().getCaArticuloDesde() &&
                                this._linea.getCantidad() <= listaPrecioDetalleAux.getId().getCaArticuloHasta()
                        ) {
                            mListaPrecioDetalle = listaPrecioDetalleAux;
                            break;
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            setearPrecios(mListaPrecioDetalle);
        }
        actualizarPrecio(_linea);
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

    private void txtDescuentoOnTextChanged(CharSequence s, int start, int before, int count) {
        double tasaDescuento = 0;
        if (s.length() > 0) {
            String sDescuento = txtDescuento.getText().toString();
            if (sDescuento.equals(".")) {
                sDescuento = "0.";
                txtDescuento.setText(sDescuento);
                txtDescuento.setSelection(sDescuento.length());
            }
            if (sDescuento.length() > 0) {
                tasaDescuento = Double.valueOf(sDescuento);
                if (tasaDescuento > 0) {
                    tasaDescuento = tasaDescuento * (-1) / 100;
                }
            }
        }
        this._linea.setTasaDescuento(tasaDescuento);

        this.actualizarPrecio(_linea);
    }

    private void limpiarDatos() {
        this.aCTxtDescripcion.setText("");
        this.txtStock.setText("");
        this.cmbListaPrecio.setAdapter(null);
    }

    /**
     * Obtiene el stock en linea desde el servidor
     *
     * @return stock
     */
    private void actualizarStock() {
        if (PreferenciaBo.getInstance().getPreferencia(getApplicationContext()).isDescargaStockAutomatico()) {
            long idVendedor = PreferenciaBo.getInstance().getPreferencia(this).getIdVendedor();
            UpdateStockTask updateStockTask = new UpdateStockTask(this, this._linea.getArticulo(), idVendedor,
                    getApplicationContext(), mVenta.getDocumento().getId());
            updateStockTask.execute();
        } else {
            double stock = this._linea.getArticulo().getStock();
            this.txtStock.setText(String.valueOf(stock));
        }
    }

    private void actualizarPrecio(VentaDetalle vd) {
        Integer tiMetodoImpInt;//siempre se inicializa en 0
        try {
            tiMetodoImpInt = _empresaBo.recoveryEmpresa().getTiMetodoImpInt();
        } catch (Exception e) {
            tiMetodoImpInt = 1;
        }
        Articulo articulo = vd.getArticulo();
        if (articulo != null) {

            double cantidad = vd.getCantidad();
            double precioUnitarioSinIva = vd.getPrecioUnitarioSinIva();
            double tasaImpuestoInterno = vd.getArticulo().getTasaImpuestoInterno();
            double impuestoInterno;//siempre se inicializa en 0
            if (tiMetodoImpInt == 1) {
                impuestoInterno = precioUnitarioSinIva * tasaImpuestoInterno;
            } else {
                impuestoInterno = vd.getArticulo().getImpuestoInterno();
            }
            double tasaIva = articulo.getTasaIva();
            double precioIvaUnitario = precioUnitarioSinIva * tasaIva;
            double precioUnitarioConIva = precioUnitarioSinIva + precioIvaUnitario;
            double precioFinal = precioUnitarioConIva + impuestoInterno;
            mSubTotal = cantidad * (precioFinal);
            if (articulo.isPromocion()) {
                this.btnVerDetallePromocion.setVisibility(Button.VISIBLE);
            } else {
                this.btnVerDetallePromocion.setVisibility(Button.GONE);
            }

            vd.setPrecioIvaUnitario(precioIvaUnitario);
            vd.setPrecioImpuestoInterno(impuestoInterno);
            this.txtSubtotal.setError(null);
            this.txtSubtotal.setText(Formatter.formatMoney(mSubTotal));
            this.txtPrecio.setText(Formatter.formatMoney(precioFinal));
            this.lblPorcentajeDescuentoProveedor.setText(Formatter.formatPercent(vd.getTasaDescuentoProveedor()));
            this.lblPorcentajeDescuentoCliente.setText(Formatter.formatPercent(vd.getTasaDescuentoCliente()));

            // Precio del kilo - Si el articulo es pesable
            if (articulo.isPesable()) {
                double precioKilo = precioFinal / articulo.getCantidadKilos();
                vd.setCaKilos(articulo.getCantidadKilos());
                this.txtPrecioKilo.setText(Formatter.formatMoney(precioKilo));
                this.txtPrecioKilo.setEnabled(true);
            } else {
                this.txtPrecioKilo.setText(null);
                this.txtPrecioKilo.setEnabled(false);
            }
        }

    }

    public VentaDetalle getVentaDetalle() {
        return _linea;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mni_buscar_articulo:
                buscarArticulo(null);
                return false;
            case R.id.mni_cantidades_acumuladas_x_cliente:
                Intent intent = new Intent(getApplicationContext(), FrmVentasXArticulo.class);
                intent.putExtra(FrmVentasXArticulo.KEY_CLIENTES, mCliente);
                startActivity(intent);

                return true;

            case R.id.mni_objetivos_venta:
                showObjetivosVenta();
                break;

            case R.id.mni_catalogo:
                Intent intentCatalogo = new Intent(this, CatalogoActivity.class);
                intentCatalogo.putExtra(FrmVentasXArticulo.KEY_CLIENTES, mCliente);
                intentCatalogo.putExtra(FrmVentasXArticulo.KEY_VENTA, mVenta);
                startActivityForResult(intentCatalogo, ACTIVITY_CATALOGO);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showObjetivosVenta() {

        VendedorObjetivoFragment objetivoVenta = new VendedorObjetivoFragment();
        setFragment(objetivoVenta);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.flContent, objetivoVenta);
        transaction.addToBackStack(VENDEDOR_OBJETIVO);
        transaction.commit();
    }

    private void showComboEspecialFragment() {
        if (modo == ALTA) {
            mDialogFragment = CargaComboDialogFragment.newInstance(txtUnidades.getText().toString(),
                    _linea.getArticulo(), new ComboListener() {

                        @Override
                        public void onComboAccepted(ArrayList<VentaDetalle> listPromociones, int cant) {
                            guardarComboEspecial(listPromociones, cant);

                        }

                        @Override
                        public void onDismissDialog() {
                            // TODO Auto-generated method stub

                        }
                    });
            mDialogFragment.setCancelable(false);
            mDialogFragment.show(getSupportFragmentManager(), "");
        } else {
            mDialogFragment = CargaComboDialogFragment.newInstance(_linea, new ComboListener() {

                @Override
                public void onComboAccepted(ArrayList<VentaDetalle> listVentasDetalles, int cant) {
                    guardarComboEspecial(listVentasDetalles, cant);

                }

                @Override
                public void onDismissDialog() {
                    FrmAgregarLinea.this.finish();

                }

            });
            mDialogFragment.setCancelable(false);
            mDialogFragment.show(getSupportFragmentManager(), "dialog");
        }
    }

    private void guardarComboEspecial(List<VentaDetalle> detallesCombo, int cant) {
        _linea.setCabeceraPromo(true);
        _linea.setBultos(0);
        _linea.setUnidades(cant);

        try {
            List<VentaDetalle> detallesComboCompleto = VentaDetalleBo.completeVentasDetalles(detallesCombo, mCliente,
                    mListaPrecioDetalle, getApplicationContext());
            _linea.setDetalleCombo(detallesComboCompleto);
            for (VentaDetalle vd : detallesComboCompleto) {
                actualizarPrecio(vd);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Intent intent = new Intent(FrmAgregarLinea.this, DetalleVentaFragment.class);
        intent.putExtra("linea", _linea);
        setResult(Activity.RESULT_OK, intent);
        FrmAgregarLinea.this.finish();
    }

    public void buscarArticulo(Bundle extras) {
        Intent intent = new Intent(this, FrmPreciosXLista.class);
        if (extras == null) {
            intent.putExtra(DetalleVentaFragment.EXTRA_LISTA_PRECIO, this._listaPrecioXDefecto);
            intent.putExtra(FrmVenta.EXTRA_VENTA, mVenta);
        } else {
            intent.putExtras(extras);
        }
        startActivityForResult(intent, ACTIVITY_PRECIOS_X_LISTA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_SELECCIONAR_LINEA) {
            // Pongo el foco sore el txtCodigo para evitar problemas
            this.txtCodigo.setFocusable(true);
            // Recibo la Lista Precio Detalle
            ListaPrecioDetalle listaPrecioDetalle = (ListaPrecioDetalle) data
                    .getSerializableExtra("listaPrecioDetalle");
            Articulo articulo = listaPrecioDetalle.getArticulo();
            if (articulo != null) {
                // Seteo el articulo a la linea
                this._linea.setArticulo(articulo);
                this.txtUnidades.setText("0");
                this._linea.setUnidades(0d);
                this.txtBultos.setText("0");
                this._linea.setBultos(0);
                // Actualizo el stock
                this.actualizarStock();
                // Cargo las listas de precio
                actualizarListasPrecio();
                // Cargo el codigo de articulo en el txtCodigo
                this.txtCodigo.setText(articulo.getCodigoEmpresa());
                // Seteo la lista que recibi de FrmPreciosXLista
                setearComboListaPrecio(listaPrecioDetalle, articulo);
                // Seteo la variable para saber desde donde vine
                // Esta variable se usa en el boton aceptar
                ORIGEN_BUSCAR = true;
                String sUnidadesXBulto = String.valueOf(this._linea.getArticulo().getUnidadPorBulto());
                this.lblUnidadPorBulto.setText(sUnidadesXBulto);
            }
        } else {
            // Entra por catalogo
            if (data != null) {
                VentaDetalle vd = (VentaDetalle) data.getSerializableExtra("linea");
                this.setLinea(vd);
                this.mListaPrecioDetalle = (ListaPrecioDetalle) data.getSerializableExtra("lineaPrecioDetalle");
                this.actualizarDatosArticulo();

                Intent intent = new Intent(this, FrmVenta.class);
                intent.putExtra("linea", data.getSerializableExtra("linea"));//(VentaDetalle)
                intent.putExtra("modo", ALTA);
                intent.putExtra("origen", ORIGEN_BUSCAR);
                intent.putExtra("catalogo", data.getBooleanExtra("catalogo", false));
                setResult(RESULT_OK, intent);
                finish();
            } else {
                onBackPressed();
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

    public void btnAceptarOnClick() throws Exception {
        CuentaCorrienteBo bo = new CuentaCorrienteBo(_repoFactory);

        if (this._linea.getArticulo() == null) {
            AlertDialog alert = new AlertDialog(this, "Error", ErrorManager.NoArticuloSeleccionado);
            alert.show();
        } else if (this._linea.getCantidad() == 0) {
            AlertDialog alert = new AlertDialog(this, "Error", ErrorManager.NoUnidadIngresada);
            alert.show();
        } else if (this._linea.getTasaDescuento() > 1) {
            AlertDialog alert = new AlertDialog(this, "Error", ErrorManager.PorcentajeDescuentoInvalido);
            alert.show();
        } else if (!bo.tieneCredito(mVenta, mVenta.getTotal() + mSubTotal - mSubTotalOriginal)) { // no
            // disponibilidad
            // se usa el cliente de la venta porque es el que despues se guarda
            showNoDisponibilidadDialog();
        } else if (!ListaPrecioDetalleBo.havePrice(mListaPrecioDetalle)) {
            showNoPriceDialog();

        } else if (PromocionBo.esCabeceraComboEspecial(_linea.getArticulo().getId(), _linea.getListaPrecio(), getApplicationContext())) {
            showComboEspecialFragment();
        } else {
            // Guardo el articulo seleccionado por si quiere vender de nuevo uno parecido
            Preferencia.setUltimoArticuloSeleccionado(this._linea.getArticulo());
            Intent intent = new Intent(this, FrmVenta.class);
            intent.putExtra("linea", this._linea);
            intent.putExtra("modo", ALTA);
            intent.putExtra("origen", ORIGEN_BUSCAR);
            setResult(RESULT_OK, intent);
            this.finish();
        }
    }

    public void setStock(double stock) {
        String st = String.valueOf(stock);
        this.txtStock.setText(st);
    }

    public void showNoPriceDialog() {
        AlertDialog dialog = new AlertDialog(this, "Importante",
                "La lista de precio seleccionada no tiene precio asociado, seleccione una valida.");
        dialog.show();

    }

    public List<ListaPrecioDetalle> getListaPrecioDetallesConRepetidos() {
        return ListaPrecioDetallesConRepetidos;
    }

    public void setListaPrecioDetallesConRepetidos(List<ListaPrecioDetalle> ListaPrecioDetallesConRepetidos) {
        this.ListaPrecioDetallesConRepetidos = ListaPrecioDetallesConRepetidos;
    }

    public boolean estaHabilitadoParaVenderAlcohol() {
        boolean isHabilitado = true; //para los casos en que este habilitado y tenga fecha null o no este habilitado y fecha null
        boolean habitadoParaVenderAlcohol = mCliente.getIsHabilitadoParaAlcohol();
        boolean tieneFechaVencimientoAsignada = (mCliente.getFeVencHabilitacionAlcohol() != null);
        if (habitadoParaVenderAlcohol) {
            if (tieneFechaVencimientoAsignada) {
                Calendar cal = new GregorianCalendar();
                Date fechaActual = cal.getTime();
                isHabilitado = mCliente.getFeVencHabilitacionAlcohol().compareTo(fechaActual) >= 0;
            } else {
                isHabilitado = false;
            }
        }
        if (!habitadoParaVenderAlcohol && tieneFechaVencimientoAsignada) {
            isHabilitado = false;
        }
        return isHabilitado;
    }

    private void showClienteNoHabilitadoParaAlcohol() {
        AlertDialog dialog = new AlertDialog(this, "Importante",
                "Cliente no habilitado para bebidas alcoholicas.");
        dialog.show();
    }
}
