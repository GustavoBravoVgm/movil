package com.ar.vgmsistemas.view.articulo;

import static com.ar.vgmsistemas.view.venta.FrmVenta.EXTRA_VENTA;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.ListaPrecioBo;
import com.ar.vgmsistemas.bo.ListaPrecioDetalleBo;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.bo.SubrubroBo;
import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.entity.ListaPrecio;
import com.ar.vgmsistemas.entity.ListaPrecioDetalle;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.entity.Proveedor;
import com.ar.vgmsistemas.entity.Subrubro;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.entity.key.PkSubrubro;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.view.BaseActivity;
import com.ar.vgmsistemas.view.venta.DetalleVentaFragment;
import com.ar.vgmsistemas.view.venta.FrmAgregarLinea;
import com.ar.vgmsistemas.view.venta.FrmVenta;

import java.util.ArrayList;
import java.util.List;

public class FrmPreciosXLista extends BaseActivity {
    private static final String DESCRIPCION_SUBRUBRO_TODOS = "Todos";
    public static final int RESULT_SELECCIONAR_LINEA = 1;
    public static final String EXTRA_RESTORE_INSTANCE = "EXTRA_RESTORE_INSTANCE";

    private RadioButton rbCodigo;
    private RadioButton rbDescripcion;
    private Spinner cmbListaPrecio;
    private Spinner cmbSubrubros;
    private ListView lstListaPrecioDetalle;
    private EditText txtBuscar;
    private LinearLayout linearFiltroProveedor;
    private CheckBox chkFiltroProveedor;
    private ListaPrecio _lista;
    private ListaPrecio _listaSeleccionada;
    private Subrubro _subrubroSeleccionado;
    private Articulo _articulo;
    private List<ListaPrecio> _listasPrecio;
    private PrecioXListaAdapter _adapter;
    private List<Subrubro> _subrubros;
    private List<ListaPrecioDetalle> _detalles = new ArrayList<>();
    private Venta _venta;
    Proveedor proveedor;

    /*
    Esta variable se envia a FrmDetalleVenta.java para indicar que se vino desde este activity
    Entonces, al Presionar Aceptar en FrmAgregarLinea.java se vuelve a abrir FrmPreciosXLista.java
    */
    public static final boolean ORIGEN_BUSCAR = true;
    public static final String ORIGEN = "origen";
    private static final long NO_FILTRO_POR_SUBRUBRO = -1;
    private boolean isFromRestoreInstance;

    private RepositoryFactory _repoFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        this._lista = (ListaPrecio) b.getSerializable(DetalleVentaFragment.EXTRA_LISTA_PRECIO);
        this.proveedor = (Proveedor) b.getSerializable(DetalleVentaFragment.EXTRA_PROVEEDOR);
        this._venta = (Venta) b.getSerializable(EXTRA_VENTA);
        setContentView(R.layout.lyt_precios_x_lista);
        setActionBarTitle(R.string.tituloSeleccionarArticulo);
        initComponents();
        loadData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //setResult(RESULT_SELECCIONAR_LINEA, intent);
    }

    public void seleccionarListaPorDefecto() {
        if (this._lista != null) {
            int size = _listasPrecio.size();
            for (int i = 0; i < size; i++) {
                ListaPrecio lista = _listasPrecio.get(i);
                if (lista.getId() == this._lista.getId()) {
                    this.cmbListaPrecio.setSelection(i);
                    //Seteo la Lista de Precio seleccionada, en este caso la por defecto
                    _listaSeleccionada = lista;
                    break;
                }
            }
        }
    }

    private void initComponents() {
        //rbCodigo
        this.rbCodigo = (RadioButton) findViewById(R.id.rbCodigo);
        this.rbCodigo.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setCampoFiltro(Preferencia.ARTICULO_CODIGO);
                    PreferenciaBo.getInstance().getPreferencia(getApplicationContext()).setBusquedaPreferidaArticulo(Preferencia.ARTICULO_CODIGO);
                }
            }
        });

        //rbDescripcion
        this.rbDescripcion = (RadioButton) findViewById(R.id.rbDescripcion);
        this.rbDescripcion.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setCampoFiltro(Preferencia.ARTICULO_DESCRIPCION);
                    PreferenciaBo.getInstance().getPreferencia(getApplicationContext()).setBusquedaPreferidaArticulo(Preferencia.ARTICULO_DESCRIPCION);
                }
            }
        });

        //cmbSubrubros
        this.cmbSubrubros = (Spinner) findViewById(R.id.cmbSubrubro);
        this.cmbSubrubros.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> adapter, View parent, int position, long id) {
                cmbSubrubrosOnItemSelected(adapter, parent, position, id);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //cmbListas
        this.cmbListaPrecio = (Spinner) findViewById(R.id.cmbListaPrecio);
        this.cmbListaPrecio.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> adapter, View parent, int position, long id) {
                cmbListaPrecioOnItemSelected(adapter, parent, position, id);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //lstListaPrecioDetalle
        this.lstListaPrecioDetalle = (ListView) findViewById(R.id.lstListaPrecioDetalle);
        this.lstListaPrecioDetalle.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View parent, int position, long id) {
                lstListaPrecioDetalleOnItemClick(adapter, parent, position, id);
            }
        });

        //txtBuscar
        this.txtBuscar = (EditText) findViewById(R.id.txtBuscar);
        this.txtBuscar.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                filtrar();
            }
        });

        this.linearFiltroProveedor = (LinearLayout) findViewById(R.id.linearFiltroProveedor);
        if (proveedor == null)
            this.linearFiltroProveedor.setVisibility(View.GONE);

        this.chkFiltroProveedor = (CheckBox) findViewById(R.id.chkFiltroProveedor);
        this.chkFiltroProveedor.setChecked(true);
        this.chkFiltroProveedor.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    loadArticulosSubrubros(/*_subrubroSeleccionado, _listaSeleccionada*/);
                } catch (Exception e) {
                    ErrorManager.manageException("FrmPreciosXLista", "loadArticulosSubrubros", e, getApplicationContext());
                }
            }
        });
    }

    private void setCampoFiltro(int campoFiltro) {
        if (_adapter != null) {
            _adapter.setCampoFiltro(campoFiltro);
            _adapter.notifyDataSetChanged();
            filtrar();
        }
    }

    private void filtrar() {
        String s = this.txtBuscar.getText().toString();
        if (_adapter != null && s != null) {
            this._adapter.getFilter().filter(s);
            this._adapter.notifyDataSetChanged();
        }

    }

    private void lstListaPrecioDetalleOnItemClick(AdapterView<?> adapter, View parent, int position, long id) {

        //Obtengo la listaPrecioDetalleSeleccionada
        ListaPrecioDetalle listaPrecioDetalleSeleccionada = (ListaPrecioDetalle) adapter.getItemAtPosition(position);

        //Seteo la lista de precio a la listaPrecioDetalle
        listaPrecioDetalleSeleccionada.setListaPrecio(_listaSeleccionada);

        //Obtengo el articulo de la listaPrecioDetalle
        _articulo = listaPrecioDetalleSeleccionada.getArticulo();

        //Guardo el texto ingresado en el filtro en Preferencia
        String textoFiltro = this.txtBuscar.getText().toString();
        Preferencia.setTextoFiltroArticulo(textoFiltro);

        //Guardo la Posicion del subrubro elegido
        Preferencia.setBusquedaRecordadaSubrubro(this.cmbSubrubros.getSelectedItemPosition());

        //Guardo la Posicion de la Lista de Precio seleccionada
        Preferencia.setListaPrecioRecordada(this.cmbListaPrecio.getSelectedItemPosition());

        //Guardo la posicion del ultimo articulo seleccionado
        Preferencia.setUltimaPosicionArticuloLista(position);

        Intent intent = new Intent(this, FrmAgregarLinea.class);
        intent.putExtra("listaPrecioDetalle", listaPrecioDetalleSeleccionada);
        intent.putExtra(FrmVenta.EXTRA_VENTA, _venta);
        intent.putExtra(ORIGEN, ORIGEN_BUSCAR);
        intent.putExtra(EXTRA_RESTORE_INSTANCE, isFromRestoreInstance);
        setResult(RESULT_SELECCIONAR_LINEA, intent);


        //Cierro el activity actual
        this.finish();

    }

    public Articulo getArticulo() {
        return this._articulo;
    }

    private void loadData() {
        this._repoFactory = RepositoryFactory.getRepositoryFactory(getApplicationContext(), RepositoryFactory.ROOM);
        //Cargo el Combo de Listas de Precio
        loadListasPrecio();

        //Inicializo la variable _listaSeleccionada
        _listaSeleccionada = new ListaPrecio();

        //Cargo el combo de Subrubros
        loadSubrubros();

        //Recupero el filtro de busqueda preferida
        int busquedaPreferida = PreferenciaBo.getInstance().getPreferencia(getApplicationContext()).getBusquedaPreferidaArticulo();

        //Configuro el filtro segun las preferencias
        switch (busquedaPreferida) {
            case Preferencia.ARTICULO_CODIGO:
                this.rbCodigo.setChecked(true);
                break;
            case Preferencia.ARTICULO_DESCRIPCION:
            default:
                this.rbDescripcion.setChecked(true);
                break;
        }
        setCampoFiltro(busquedaPreferida);
        //Cargo el Texto del filtro con lo cargado antes
        String filtro = Preferencia.getTextoFiltroArticulo();
        if (filtro != null && filtro.trim().length() > 0) {
            this.txtBuscar.setText(filtro);
        }

        //Cargo Filtro de subrubro
        this.cmbSubrubros.setSelection(Preferencia.getBusquedaRecordadaSubrubro());

        //Cargo Filtro de lista de precio
        this.cmbListaPrecio.setSelection(Preferencia.getListaPrecioRecordada());

        //Cargo la ultima posicion en la lista de articulos
        this.lstListaPrecioDetalle.setSelection(Preferencia.getUltimaPosicionArticuloLista());
    }

    /**
     * Carga el combo con las listas de precio
     */

    private void loadListasPrecio() {
        ListaPrecioBo listaPrecioBo = new ListaPrecioBo(_repoFactory);
        try {
            _listasPrecio = listaPrecioBo.recoveryAll();
            ArrayAdapter<ListaPrecio> adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, _listasPrecio);
            this.cmbListaPrecio.setAdapter(adapter);

            seleccionarListaPorDefecto();

        } catch (Exception e) {
            ErrorManager.manageException("FrmGestionArticulo", "loadData", e, this);
        }
    }

    /**
     * Cargo el combo con subrubros
     */

    private void loadSubrubros() {
        SubrubroBo subrubroBo = new SubrubroBo(_repoFactory);
        try {
            _subrubros = subrubroBo.recoveryAll();
            ArrayAdapter<Subrubro> adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, _subrubros);
            setearSubrubroPorDefecto();
            this.cmbSubrubros.setAdapter(adapter);

            seleccionarSubrubroPorDefecto();

        } catch (Exception e) {
            ErrorManager.manageException("FrmPreciosXLista", "loadData subrubros", e, this);
        }

    }

    /*
     * El subrubro por defecto es mostrar todos los subrubros
     */
    private void setearSubrubroPorDefecto() {
        Subrubro subrubroTodos = new Subrubro();
        PkSubrubro id = new PkSubrubro();
        id.setIdSubrubro(-1);
        subrubroTodos.setId(id);
        subrubroTodos.setDescripcion(DESCRIPCION_SUBRUBRO_TODOS);
        _subrubros.add(0, subrubroTodos);

    }
	
	/*
	 Selecciono en la lista el subrubro TODOS
	 */

    private void seleccionarSubrubroPorDefecto() {
        this.cmbSubrubros.setSelection(0);
        //Inicializo el subrubro seleccionado
        _subrubroSeleccionado = this._subrubros.get(0);
    }

    public void cmbListaPrecioOnItemSelected(AdapterView<?> adapter, View parent, int position, long id) {
        ListaPrecio listaSeleccionada = (ListaPrecio) adapter.getItemAtPosition(position);
        this._listaSeleccionada = listaSeleccionada;
        try {
            loadArticulosSubrubros(/*_subrubroSeleccionado, _listaSeleccionada*/);
        } catch (Exception e) {
            ErrorManager.manageException("FrmGestionArticulo", "loadData", e, this);
        }
        hideSoftKeyboard();
    }


    public void cmbSubrubrosOnItemSelected(AdapterView<?> adapter, View parent, int position, long id) {
        //Obtengo el subrubro seleccionado en la lista de subrubros
        _subrubroSeleccionado = (Subrubro) adapter.getItemAtPosition(position);
        try {
            loadArticulosSubrubros(/*_subrubroSeleccionado, _listaSeleccionada*/);
        } catch (Exception e) {
            ErrorManager.manageException("FrmGestionArticulo", "loadData", e, this);
        }
        hideSoftKeyboard();
    }

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private void loadArticulosSubrubros(/*Subrubro subrubroSeleccionado, ListaPrecio listaPrecio*/) throws Exception {
        ListaPrecioDetalleBo listaPrecioDetalleBo = new ListaPrecioDetalleBo(_repoFactory);
        //Si muestra todos, entonces hago un recovery all
        if (_subrubroSeleccionado.getId().getIdSubrubro() == NO_FILTRO_POR_SUBRUBRO) {
            if (proveedor != null && chkFiltroProveedor.isChecked()) {
                _detalles = listaPrecioDetalleBo.recoveryByListaPrecio(_listaSeleccionada, proveedor.getIdProveedor());
            } else {
                _detalles = listaPrecioDetalleBo.recoveryByListaPrecio(_listaSeleccionada, null);
            }
        } else {
            if (proveedor != null && chkFiltroProveedor.isChecked()) {
                _detalles = listaPrecioDetalleBo.recoveryByArticuloSubrubro(_listaSeleccionada, _subrubroSeleccionado, proveedor.getIdProveedor());
            } else {
                _detalles = listaPrecioDetalleBo.recoveryByArticuloSubrubro(_listaSeleccionada, _subrubroSeleccionado, null);
            }
        }
        List<ListaPrecioDetalle> listaPrecioDetallesSinRepetidos = new ArrayList<>();
        if (_listaSeleccionada.getTipoLista() == 9) {
            long size = 0;
            size = this._detalles.size();
            for (int i = 0; i < size; i++) {
                if (_detalles.get(i).getId().getCaArticuloDesde() == 0) {
                    listaPrecioDetallesSinRepetidos.add(_detalles.get(i));
                }
            }
        } else {
            listaPrecioDetallesSinRepetidos = _detalles;
        }

        this._adapter = new PrecioXListaAdapter(this, R.layout.lyt_precios_x_lista_item, listaPrecioDetallesSinRepetidos, _subrubroSeleccionado.getId().getIdSubrubro(), _venta);
        this._adapter.sort();
        this.filtrar();
        this.lstListaPrecioDetalle.setAdapter(_adapter);
        this.lstListaPrecioDetalle.setSelection(Preferencia.getUltimaPosicionArticuloLista());
    }

    protected void ordenar() {
        this._adapter.sort();
        this._adapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //outState.putBoolean(EXTRA_RESTORE_INSTANCE, true);
        super.onSaveInstanceState(outState);
    }
}
