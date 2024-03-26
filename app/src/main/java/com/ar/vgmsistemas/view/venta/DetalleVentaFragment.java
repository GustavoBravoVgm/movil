package com.ar.vgmsistemas.view.venta;

import static com.ar.vgmsistemas.view.venta.FrmVenta.EXTRA_VENTA;
import static com.ar.vgmsistemas.view.venta.FrmVenta.IS_FROM_RESTORE_INSTANCE;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.view.ActionMode;
import androidx.appcompat.view.ActionMode.Callback;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SearchView.OnQueryTextListener;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.DocumentoBo;
import com.ar.vgmsistemas.bo.DocumentosListaBo;
import com.ar.vgmsistemas.bo.ListaPrecioBo;
import com.ar.vgmsistemas.bo.MenuBo;
import com.ar.vgmsistemas.bo.VentaBo;
import com.ar.vgmsistemas.bo.VentaDetalleBo;
import com.ar.vgmsistemas.entity.Documento;
import com.ar.vgmsistemas.entity.ListaPrecio;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.entity.key.PkDocumento;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.TipoEmpresaCode;
import com.ar.vgmsistemas.view.dialogs.SimpleDialogFragment;
import com.ar.vgmsistemas.view.dialogs.SimpleDialogFragment.OkCancelListener;
import com.ar.vgmsistemas.view.dialogs.SingleChoiceDialogFragment;
import com.ar.vgmsistemas.view.dialogs.SingleChoiceDialogFragment.SingleChoiceListener;
import com.ar.vgmsistemas.view.objetivoVenta.FrmGestionObjetivoVenta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DetalleVentaFragment extends Fragment {
    private static final String TAG = DetalleVentaFragment.class.getCanonicalName();

    public static final int MODO_ALTA = 0;
    public static final int MODO_EDITAR = 1;
    public static final int MODO_CONSULTA = 2;

    public static final String EXTRA_LISTA_PRECIO = "listaPrecio";
    public static final String EXTRA_PROVEEDOR = "proveedor";

    public static final int RESULT_OK_COMBO = 200;

    // Activities
    public final static int ACTIVITY_AGREGAR_LINEA = 0;
    private final static int ACTIVITY_OBJETIVOS_VENTA = 1;
    private final static int ACTIVITY_PRECIOS_X_LISTA_PROVEEDOR = 1233;

    // Modos
    private int modo;
    private ActionMode mActionMode;
    private boolean ORIGEN_BUSCAR;
    private boolean OrigenCatalogo;

    private ListView lstLineasVenta;
    private VentaDetalleAdapter adapter;
    private Venta _venta;
    private VentaDetalle lineaSeleccionada;
    // BO
    private DocumentoBo documentoBo;
    private ListaPrecioBo listaPrecioBo;
    private DocumentosListaBo mDocumentosListaBo;
    private FrmVenta mFrmVenta;

    private boolean mCurrentLineValid = true;
    // Dialogs
    private final int DIALOG_OPCIONES_LINEA = 0;
    private final int DIALOG_ELIMINAR = 1;
    private SearchView mSearchView;
    private ImageButton mButtonAgregar;
    private MenuItem itemSelectAll;

    //DAO
    RepositoryFactory _repoFactory;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mFrmVenta = (FrmVenta) getActivity();
        mFrmVenta.setDetalleVentaFragment(this);
        Bundle b = getArguments();

        if (savedInstanceState == null) {
            this._venta = (Venta) b.getSerializable(EXTRA_VENTA);
            this.modo = (Integer) b.getSerializable(FrmVenta.EXTRA_MODO);

        } else {
            this._venta = (Venta) savedInstanceState.getSerializable(EXTRA_VENTA);
            this.modo = (Integer) savedInstanceState.getSerializable(FrmVenta.EXTRA_MODO);

        }

        this.initVar();
        boolean activityRestored = b.getBoolean(IS_FROM_RESTORE_INSTANCE, false);
        if (_venta.getDetalles().size() == 0 && savedInstanceState == null && !activityRestored) {
            this.agregarLinea(ACTIVITY_AGREGAR_LINEA);
        } else {
            _venta.setDetalles(VentaDetalleBo.checkVentasDetallesCombos(_venta.getDetalles()));
            actualizarTotales();
        }

    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mFrmVenta.updateLineasPedido(_venta.getDetalles(), _venta.getId());
        if (mFrmVenta.isNormalEmpresa()) {
            adapter.setListValids(mFrmVenta.get_listValids());
        }

    }

    private void initVar() {
        this._repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        this.mDocumentosListaBo = new DocumentosListaBo(_repoFactory);
        this.documentoBo = new DocumentoBo(_repoFactory);
        this.listaPrecioBo = new ListaPrecioBo(_repoFactory);
        mDocumentosListaBo = new DocumentosListaBo(_repoFactory);
        mFrmVenta.updateLineasPedido(_venta.getDetalles(), _venta.getId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.lyt_venta_detalle, null);
        lstLineasVenta = (ListView) view.findViewById(R.id.lstLineasPedido);
        lstLineasVenta.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lstLineasPedidoOnItemClick(parent, view, position, id);
            }
        });
        //mButtonAgregar (ImageButton)
        mButtonAgregar = view.findViewById(R.id.btnAgregar);
        int visibility;
        try {
            visibility = (modo == MODO_CONSULTA || !isHacerPedidos()) ? View.GONE : View.VISIBLE;
            mButtonAgregar.setVisibility(visibility);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        mButtonAgregar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                agregarLinea(ACTIVITY_AGREGAR_LINEA);
                cleanSearchView();
            }

        });
        VentaDetalleBo.checkNumerosTropas(_venta.getDetalles());
        List<VentaDetalle> detalles = _venta.getDetalles();//si es modo edicion tengo que armar el combo, si es un alta ya esta armado
        adapter = new VentaDetalleAdapter(mFrmVenta, R.layout.lyt_detalle_impuestos_item, detalles, _venta);
        adapter.setListValids(mFrmVenta.get_listValids());
        lstLineasVenta.setAdapter(adapter);

        registerForContextMenu(lstLineasVenta);
        lstLineasVenta.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                if (modo != MODO_CONSULTA) {
                    onLisItemCheck(position);
                    return true;
                }
                return false;
            }
        });
        Bundle b = getArguments();
        boolean activityRestored = b.getBoolean(IS_FROM_RESTORE_INSTANCE, false);
        b = null;
        if (activityRestored)
            mFrmVenta.onActivityResultRestore();
        return view;
    }

    private void onLisItemCheck(int position) {
        adapter.toggleSelection(position);
        if (adapter.getSelectedCount() > 0 && mActionMode == null) {
            mButtonAgregar.setVisibility(View.INVISIBLE);
            mActionMode = mFrmVenta.startSupportActionMode(new ActionModeCallback(mFrmVenta));
            mFrmVenta.setEnableTabWidget(false, position);
        } else if (!(adapter.getSelectedCount() > 0) && mActionMode != null) {
            mActionMode.finish();
            mButtonAgregar.setVisibility(View.VISIBLE);
            ;
            mFrmVenta.setEnableTabWidget(true, position);
        }
        if (mActionMode != null)
            mActionMode.setTitle(String.valueOf(adapter.getSelectedCount()) + " seleccionada");
        Drawable icon = (adapter.isSelectAll()) ? getResources().getDrawable(R.drawable.ic_select_all_selected) : getResources().getDrawable(R.drawable.ic_select_all_unselected);
        itemSelectAll.setIcon(icon);
    }

    public void showDialog(int typeDialog) {
        Dialog dialog = null;
        switch (typeDialog) {
            case DIALOG_ELIMINAR:
                dialog = eliminarLinea();
                dialog.show();
                break;

        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {

        boolean retorno = false;
        try {
            retorno = isHacerPedidos();
        } catch (Exception e) {
        }
        if (retorno) {
            menu.clear();
            menuInflater.inflate(R.menu.mn_venta_detalle, menu);
            MenuBo.inflateBaseMenuOptions(getActivity(), menu, menuInflater);
            MenuItem item = menu.findItem(R.id.mni_search);
            mSearchView = (SearchView) MenuItemCompat.getActionView(item);
            super.onCreateOptionsMenu(menu, menuInflater);
        }


    }

    private void cleanSearchView() {
        adapter.getFilter().filter("");
        ;
        if (!mSearchView.isIconified()) {
            mSearchView.setIconified(true);
            mSearchView.setIconified(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mni_agregar:
                this.agregarLinea(ACTIVITY_AGREGAR_LINEA);
                cleanSearchView();

                break;
            case R.id.mni_objetivos_venta:
                this.verObjetivosVenta();
                break;
            case R.id.mni_config_busqueda:
                showDialogConfigBusqueda();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialogConfigBusqueda() {
        String[] opciones = {"Descripcion", "Codigo"};
        SingleChoiceDialogFragment dialogFragment = SingleChoiceDialogFragment.newInstance(opciones, adapter.getCampoFiltro(), "Seleccione criterio de busqueda", new SingleChoiceListener() {

            @Override
            public void onAcceptItem(int pos) {
                setHintSearchView(pos);
                adapter.setCampoFiltro(pos);

            }
        });
        dialogFragment.show(getChildFragmentManager(), "dialogo");
    }

    private void setHintSearchView(int pos) {
        if (pos == VentaDetalleAdapter.DESCRIPCION_ARTICULO) {
            mSearchView.setQueryHint("Por descripcion");
        } else {
            mSearchView.setQueryHint("Por codigo");
        }
    }

    private void verObjetivosVenta() {
        Intent intent = new Intent(mFrmVenta, FrmGestionObjetivoVenta.class);
        Venta ventaObjetivo = new Venta();
        ventaObjetivo.setCliente(this._venta.getCliente());
        intent.putExtra("venta", ventaObjetivo);
        intent.putExtra("origen", FrmGestionObjetivoVenta.ORIGEN_AGREGAR_LINEA);
        startActivityForResult(intent, ACTIVITY_OBJETIVOS_VENTA);
    }

    private boolean isHacerPedidos() throws Exception {
        boolean puedeHacerPedidos = false;
        // Controlo que no se haya alcanzado el limite de lineas por pedido
        // segun el documento
        // Obtengo la cantidad de lineas del pedido
        int cantidadLineas = _venta.getDetalles().size();
        // Obtengo la cantidad de lineas admisible para el documento
        PkDocumento idDocumento = new PkDocumento();
        idDocumento.setIdDocumento(_venta.getId().getIdDocumento());
        idDocumento.setIdLetra(_venta.getId().getIdLetra());
        idDocumento.setPuntoVenta(_venta.getId().getPuntoVenta());
        int cantidadLineasAdmisible = 0;
        Documento documento;
        try {
            documento = documentoBo.recoveryById(idDocumento);
        } catch (Exception e) {
            ErrorManager.manageException(TAG, ErrorManager.RecuperarLineas, e, mFrmVenta);
            throw new Exception(ErrorManager.RecuperarLineas);
        }

        if (documento != null) {
            cantidadLineasAdmisible = documento.getNumeroLineas();
        } else {
            Dialog alertDialog = getDialogConfiguracionDocumento();
            alertDialog.show();
        }

        if (cantidadLineas < (cantidadLineasAdmisible - 1)) {
            puedeHacerPedidos = true;
        }
        return puedeHacerPedidos;
    }

    private void actualizarLineas() {
        adapter.update(_venta.getDetalles());

        actualizarTotales();
    }

    public void actualizarTotales() {
        try {
            VentaBo.actualizarTotales(_venta);
        } catch (Exception ex) {
            ErrorManager.manageException(TAG, "actualizarTotales", ex, mFrmVenta);
        }
    }

    public void lstLineasPedidoOnItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mActionMode == null) {
            lineaSeleccionada = adapter.getItem(position);
            mCurrentLineValid = adapter.getItem(position).isValid();
            if (modo != MODO_CONSULTA) {
                Dialog dialog = getDialogOpcionesLinea(lineaSeleccionada);
                dialog.show();
            }
        } else {
            onLisItemCheck(position);
        }

    }

    private Dialog getDialogConfiguracionDocumento() {
        Builder builder = new Builder(mFrmVenta);
        builder.setTitle(this.getString(R.string.error));
        builder.setMessage(ErrorManager.ErrorConfiguracionDocumento);
        builder.setIcon(R.drawable.alert);

        builder.setPositiveButton(R.string.btnAceptar, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mFrmVenta.finish();
            }
        });

        AlertDialog alert = builder.create();
        return alert;
    }

    private Dialog getDialogOpcionesLinea(VentaDetalle ventaDetalle) {
        String eliminar = this.getString(R.string.eliminar);
        String editar = this.getString(R.string.editar);
        String productosProveedor = this.getString(R.string.buscarProductoProv);
        CharSequence[] items;
        if (ventaDetalle.getArticulo().getProveedor().isRentableEnPedido())
            items = new CharSequence[]{eliminar, editar};
        else
            items = new CharSequence[]{eliminar, editar, productosProveedor};

        Builder builder = new Builder(mFrmVenta);
        builder.setTitle(this.getString(R.string.tituloSeleccionarAccion));
        builder.setNegativeButton(R.string.btnCancelar, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setItems(items, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                menuOpcionesLineaOnClick(dialog, which);
            }
        });
        AlertDialog alert = builder.create();
        return alert;
    }

    private Dialog eliminarLinea() {
        Builder builder = new Builder(mFrmVenta);
        builder.setMessage("Desea eliminar la linea seleccionada?").setCancelable(false)
                .setPositiveButton("Si", new OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        adapter.remove(lineaSeleccionada);
                        actualizarTotales();
                        mFrmVenta.updateLineasPedido(_venta.getDetalles(), _venta.getId());

                    }
                }).setNegativeButton("No", new OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        return alert;
    }

    private void agregarLinea(int requestCode) {
        modo = MODO_ALTA;
        try {
            if (isHacerPedidos()) {
                Intent intent;

                if (requestCode == ACTIVITY_PRECIOS_X_LISTA_PROVEEDOR) {
                    filtrarProveedores();
                } else {
                    if (mFrmVenta.getTipoEmpresa() == TipoEmpresaCode.TYPE_HACIENDA) {
                        intent = new Intent(mFrmVenta, FrmAgregarLineaHacienda.class);
                    } else {
                        intent = new Intent(mFrmVenta, FrmAgregarLinea.class);
                    }
                    intent.putExtra(EXTRA_VENTA, _venta);
                    intent.putExtra("Catalogo", isOrigenCatalogo());
                    mFrmVenta.startForActivityResult(intent, ACTIVITY_AGREGAR_LINEA);
                }
            } else {
                Toast.makeText(mFrmVenta, R.string.msjLimiteAlcanzado, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            ErrorManager.manageException(TAG, ErrorManager.EvaluarLimiteLineasPedido, e, mFrmVenta);
        }
    }

    private ListaPrecio obtenerListaPrecioPorDefecto() {
        ListaPrecio lista;
        // ListaPrecioBo listaPrecioBo = new ListaPrecioBo();
        List<ListaPrecio> listasPrecio = new ArrayList<ListaPrecio>();
        try {
            listasPrecio = listaPrecioBo.recoveryAll();
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "Obtener lista precio por defecto", e, mFrmVenta);
        }

        lista = listasPrecio.get(Preferencia.getListaPrecioRecordada());
        return lista;

    }

    private void editarLinea() {
        Intent intent;

        modo = MODO_EDITAR;

        if (mFrmVenta.getTipoEmpresa() == TipoEmpresaCode.TYPE_HACIENDA) {
            intent = new Intent(mFrmVenta, FrmAgregarLineaHacienda.class);
        } else {
            intent = new Intent(mFrmVenta, FrmAgregarLinea.class);
        }

        intent.putExtra("cliente", this._venta.getCliente());
        intent.putExtra("linea", this.lineaSeleccionada);

        intent.putExtra(FrmVenta.EXTRA_MODO, modo);
        intent.putExtra(EXTRA_VENTA, _venta);
        intent.putExtra(FrmAgregarLinea.EXTRA_LIST_VALID, mCurrentLineValid);
        intent.putExtra(FrmAgregarLinea.EXTRA_IS_COMBO, lineaSeleccionada.isCabeceraPromo());
        startActivityForResult(intent, ACTIVITY_AGREGAR_LINEA);
        cleanSearchView();
    }

    private void menuOpcionesLineaOnClick(DialogInterface dialog, int index) {
        switch (index) {
            case 0:
                showDialog(DIALOG_ELIMINAR);
                break;
            case 1:
                editarLinea();
                break;
            case 2:
                filtrarProveedores();
                break;
        }
    }

    private void filtrarProveedores() {
        Intent intent = new Intent(getContext(), FrmAgregarLinea.class);
        intent.putExtra(EXTRA_LISTA_PRECIO, this.lineaSeleccionada.getListaPrecio());
        intent.putExtra(EXTRA_PROVEEDOR, this.lineaSeleccionada.getArticulo().getProveedor());
        intent.putExtra(EXTRA_VENTA, _venta);
        startActivityForResult(intent, ACTIVITY_PRECIOS_X_LISTA_PROVEEDOR);
    }

    public void onActivityResults(final Integer requestCode, Integer resultCode, Intent data) {
        Bundle b = null;
        if (data != null)
            b = data.getExtras();
        if (b != null && resultCode != null && resultCode == Activity.RESULT_OK && requestCode == ACTIVITY_OBJETIVOS_VENTA) {
            Venta ventaObjetivos = (Venta) b.getSerializable("ventaObjetivos");
            this.agregarLineasObjetivosVenta(ventaObjetivos);
        }
        if (data != null && b != null) {
            setOrigenCatalogo(data.getBooleanExtra("catalogo", false));
        }
        //para no cargar de vuelta el catalogo, viene del backPre
        if (data == null && b == null) {
            setOrigenCatalogo(false);
        }
        // Vengo de agregar linea
        if (b != null && resultCode != null && resultCode == Activity.RESULT_OK && (requestCode == ACTIVITY_AGREGAR_LINEA || requestCode == ACTIVITY_PRECIOS_X_LISTA_PROVEEDOR || b.getInt("modo") == FrmAgregarLinea.ALTA)) {

            final VentaDetalle linea = (VentaDetalle) b.getSerializable("linea");
            if (linea.getTasaDescuento() > 0) {
                _venta.setTieneDescuento(true);
            }
            ORIGEN_BUSCAR = (boolean) b.getBoolean("origen");
            // Controlo que no exista ese producto en los detalles
            boolean existe = false;
            Iterator<VentaDetalle> iterator = _venta.getDetalles().iterator();
            while (iterator.hasNext() && !existe) {
                final VentaDetalle detalle = (VentaDetalle) iterator.next();
                if (detalle.getArticulo().getId() == linea.getArticulo().getId()) {
                    existe = true;
                    if (modo == MODO_ALTA) {
                        Builder builder = new Builder(mFrmVenta);
                        builder.setTitle("Agregar linea");
                        builder.setMessage("El articulo " + linea.getArticulo().getDescripcion()
                                + " ya fue cargado. Desea incorporarlo nuevamente con " + linea.getBultos() + " bultos y "
                                + linea.getUnidades() + " unidades?");
                        builder.setPositiveButton("Incorporar", new OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                _venta.getDetalles().add(linea);
                                actualizarLineas();
                                agregarLinea(requestCode);
                                dialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("Cancelar", new OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                agregarLinea(requestCode);
                            }
                        });
                        builder.show();
                        break;
                    } else { // TODO
                        _venta.getDetalles().remove(detalle);
                        _venta.getDetalles().add(linea);
                        actualizarLineas();
                        agregarLinea(requestCode);
                    }
                }
            }

            if (!existe) {
                this._venta.getDetalles().add(linea);
                actualizarLineas();
                agregarLinea(requestCode);
                this._venta.notifyObservers();
            }
        }
    }

    private void agregarLineasObjetivosVenta(Venta ventaObjetivos) {
        try {
            if (isHacerPedidos()) {
                Iterator<VentaDetalle> iterator = ventaObjetivos.getDetalles().iterator();
                while (iterator.hasNext()) {
                    VentaDetalle detalleObjetivo = (VentaDetalle) iterator.next();
                    this._venta.getDetalles().add(detalleObjetivo);
                    actualizarLineas();
                    this._venta.notifyObservers();
                }
            } else {
                Toast.makeText(mFrmVenta, R.string.msjLimiteAlcanzado, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ActionModeCallback implements Callback {
        private Context mContext;

        public ActionModeCallback(Context context) {
            mContext = context;
        }

        @Override
        public boolean onActionItemClicked(ActionMode action, MenuItem arg1) {

            SparseBooleanArray selected = adapter.getSelectedItems();
            StringBuilder message = new StringBuilder();
            for (int i = 0; i < selected.size(); i++) {
                if (selected.valueAt(i)) {
                    VentaDetalle selectedItem = adapter.getItem(selected.keyAt(i));
                    message.append(selectedItem + "\n");
                }
            }
            Toast.makeText(mContext, message.toString(), Toast.LENGTH_LONG).show();

            // close action mode
            action.finish();
            return false;
        }

        @Override
        public boolean onCreateActionMode(ActionMode action, Menu menu) {
            action.getMenuInflater().inflate(R.menu.menu_select_items_detalle, menu);
            final MenuItem itemDelete = menu.findItem(R.id.mni_delete);
            itemDelete.setOnMenuItemClickListener(new OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    showDialogBorrarLineas();
                    return true;
                }
            });
            itemSelectAll = menu.findItem(R.id.mni_select_all);
            itemSelectAll.setOnMenuItemClickListener(new OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    adapter.selectAllViews();
                    Drawable icon = (adapter.isSelectAll()) ? getResources().getDrawable(R.drawable.ic_select_all_selected) : getResources().getDrawable(R.drawable.ic_select_all_unselected);
                    itemSelectAll.setIcon(icon);
                    mActionMode.setTitle(String.valueOf(adapter.getSelectedCount()) + " seleccionadas");
                    return true;
                }
            });
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode action) {
            adapter.removeSelection();
            mFrmVenta.setEnableTabWidget(true, 1);//obligo posición 1 de detalle
            mButtonAgregar.setVisibility(View.VISIBLE);
            mActionMode = null;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
            // TODO Auto-generated method stub
            return false;
        }

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        try {
            if (modo == MODO_CONSULTA || !isHacerPedidos()) {
                menu.findItem(R.id.mni_agregar).setEnabled(false);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (mSearchView != null) {
            mSearchView.setOnQueryTextListener(new OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String arg0) {
                    // TODO Auto-generated method stub
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String arg0) {
                    adapter.getFilter().filter(arg0);
                    return false;
                }
            });
            setHintSearchView(VentaDetalleAdapter.DESCRIPCION_ARTICULO);
        }
        super.onPrepareOptionsMenu(menu);
    }

    private void showDialogBorrarLineas() {

        String msj = "Esta seguro de eliminar las lineas seleccionadas?";
        SimpleDialogFragment dialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK_CANCEL, msj,
                "Importante", new OkCancelListener() {

                    @Override
                    public void onOkSelected() {
                        adapter.removeSelected();
                        actualizarTotales();
                        mFrmVenta.updateLineasPedido(_venta.getDetalles(), _venta.getId());
                        mActionMode.finish();
                    }

                    @Override
                    public void onCancelSelected() {

                    }
                });

        dialogFragment.show(getChildFragmentManager(), "dialogo");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(EXTRA_VENTA, _venta);
        outState.putSerializable(FrmVenta.EXTRA_MODO, modo);
        super.onSaveInstanceState(outState);
    }

    public boolean isOrigenCatalogo() {
        return OrigenCatalogo;
    }

    public void setOrigenCatalogo(boolean OrigenCatalogo) {
        this.OrigenCatalogo = OrigenCatalogo;
    }
}
