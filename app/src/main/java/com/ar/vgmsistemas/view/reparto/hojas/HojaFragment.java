package com.ar.vgmsistemas.view.reparto.hojas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SearchView.OnQueryTextListener;
import androidx.core.view.MenuItemCompat;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.EntregaBo;
import com.ar.vgmsistemas.bo.HojaBo;
import com.ar.vgmsistemas.bo.HojaDetalleBo;
import com.ar.vgmsistemas.bo.MotivoCreditoBo;
import com.ar.vgmsistemas.bo.VentaBo;
import com.ar.vgmsistemas.entity.Compra;
import com.ar.vgmsistemas.entity.Entrega;
import com.ar.vgmsistemas.entity.Hoja;
import com.ar.vgmsistemas.entity.HojaDetalle;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.entity.key.PkCliente;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.task.LoadEntitiesTask;
import com.ar.vgmsistemas.task.LoadEntitiesTask.LoadEntityTaskListener;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.dialogs.OptionsDialogFragment;
import com.ar.vgmsistemas.view.dialogs.OptionsDialogFragment.MultipleChoiceListener;
import com.ar.vgmsistemas.view.dialogs.SimpleDialogFragment;
import com.ar.vgmsistemas.view.dialogs.SingleChoiceDialogFragment;
import com.ar.vgmsistemas.view.dialogs.SingleChoiceDialogFragment.SingleChoiceListener;
import com.ar.vgmsistemas.view.dialogs.SpinnerDialogFragment;
import com.ar.vgmsistemas.view.dialogs.SpinnerDialogFragment.SpinnerDialogListener;
import com.ar.vgmsistemas.view.menu.BaseNavigationFragment;
import com.ar.vgmsistemas.view.rendicion.egresos.FrmEgreso;
import com.ar.vgmsistemas.view.reparto.hojas.detalles.FrmDetalleFacturaReparto;
import com.ar.vgmsistemas.view.reparto.hojas.detalles.Multiples.FrmDetallesFacturasReparto;
import com.ar.vgmsistemas.view.reparto.hojas.resumenCobranza.VerTotalesDialogFragment;
import com.ar.vgmsistemas.view.venta.VentaDetalleAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HojaFragment extends BaseNavigationFragment implements ActionMode.Callback {

    private static final String SELECCIONAR_OPCION = "Seleccionar opcion";
    private static final String[] optionsItem = {"Ver detalles", "No entregado", "Anular"};
    //private static final String[] optionsItemSinAnular = { "Ver detalles", "No entregado" };
    private static final String[] optionsSoloVerDetalles = {"Ver detalles"};
    public static final int CODE_ESTATE = 101;
    public static final String EXTRA_HOJA = "extraHoja";
    public static final String EXTRA_HOJA_DETALLE = "extraHojaDetalle";
    public static final String EXTRA_ID_HOJA = "idHoja";
    public static final String EXTRA_ID_SUCURSAL = "idSucursal";

    private Hoja mHoja;
    private List<HojaDetalle> mHojaDetalles;
    private HojaDetalle mCurrentHojaDetalle;
    private int mPosFiltro, mPosOrden = 0;
    private HojasAdapter mAdapter;
    // private int mPosSelected;
    private SearchView mSearchView;
    private ListView mListView;
    private TextView tvContado;
    private LinearLayout llPendiente;
    private LoadEntitiesTask<HojaDetalle> loadEntityTask;
    private int currentPosition;
    private View mView;
    // private ProgressDialogFragment dialogFragment;
    private TextView tvCtaCte;
    private TextView tvAnulado;
    private TextView tvNotaCredito;
    private TextView tvPendiente;
    private TextView tvEstado;
    private boolean isRotationEnabled = false;
    boolean shouldExecuteOnResume;
    //Variables para poder utilizar la seleccion multiple y el menu ActionMode
    private ActionMode mActionMode;
    private MenuItem itemSelectAll;

    //BO´s
    private VentaBo mVentaBo;
    private HojaDetalleBo mHojaDetalleBo;
    private HojaBo mHojaBo;
    private EntregaBo mEntregaBo;
    RepositoryFactory _repoFactory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHoja = (Hoja) getArguments().getSerializable(EXTRA_HOJA);
        shouldExecuteOnResume = false;
        initVars();
        setHasOptionsMenu(true);
        setViews();
        try {
            isRotationEnabled = Settings.System.getInt(getContext().getContentResolver(), Settings.System.ACCELEROMETER_ROTATION) == 1;
        } catch (Exception e) {
        }
        setAutoOrientationEnabled(getContext(), false);
        loadData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.mn_hoja_reparto, menu);
        MenuItem item = menu.findItem(R.id.miSearch);
        mSearchView = (SearchView) MenuItemCompat.getActionView(item);

    }

    private void setViews() {
        mView = View.inflate(getActivity(), R.layout.lyt_hoja_item, null);

        mListView = (ListView) mView.findViewById(R.id.lvFacturasHoja);
        if (mAdapter != null) {
            mListView.setAdapter(mAdapter);
        }
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                onLisItemCheck(position);
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long l) {
                onLisItemCheckLong(position);
                return true;
            }
        });
        tvContado = (TextView) mView.findViewById(R.id.tvCdo);
        tvCtaCte = (TextView) mView.findViewById(R.id.tvCtaCte);
        tvAnulado = (TextView) mView.findViewById(R.id.tvAnulado);
        tvNotaCredito = (TextView) mView.findViewById(R.id.tvNotaCredito);
        llPendiente = (LinearLayout) mView.findViewById(R.id.llHojaPendiente);
        tvPendiente = (TextView) mView.findViewById(R.id.tvPendiente);
        tvEstado = (TextView) mView.findViewById(R.id.tvEstado);
        switch (mHoja.getIsRendida()) {
            case Hoja.APROBADA:
                tvEstado.setText("APROBADA");
                tvEstado.setVisibility(View.VISIBLE);
                break;
            case Hoja.PRERENDIDO:
                tvEstado.setText("PRERENDIDA");
                tvEstado.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    private void initVars() {
        _repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        mHojaDetalleBo = new HojaDetalleBo(mHoja.getIdSucursal(), mHoja.getIdHoja(), _repoFactory);
        mHojaBo = new HojaBo(_repoFactory);
        mEntregaBo = new EntregaBo(_repoFactory);

    }

    private LoadEntityTaskListener<HojaDetalle> mListener = new LoadEntityTaskListener<HojaDetalle>() {

        @Override
        public void onDone(List<HojaDetalle> mEntities) {
            // dialogFragment.dismiss();
            mHojaDetalles = mEntities;
            loadViews();
            setAutoOrientationEnabled(getContext(), isRotationEnabled);
        }

        @Override
        public void onError(String error) {
            // dialogFragment.dismiss();
            setAutoOrientationEnabled(getContext(), isRotationEnabled);
        }
    };

    private void loadData() {
        try {
            loadEntityTask = new LoadEntitiesTask<HojaDetalle>(getActivity(), mHojaDetalleBo, mListener);
            loadEntityTask.execute((Void) null);
            _repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadViews() {
        mAdapter = new HojasAdapter(getActivity(), R.layout.lyt_hoja_factura, mHojaDetalles);
        mListView.setAdapter(mAdapter);
        initComponents(getView());
        updateViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miFiltros:
                showDialogFiltros();
                break;
            case R.id.miCriterio:
                showDialogConfigBusqueda();
                break;
            case R.id.miOrden:
                showDialogOrdenamiento();
                break;
            case R.id.miCargarEgreso:
                cargarEgreso();
                break;
            case R.id.miVerEgresos:
                verEgresos();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void verEgresos() {
        Intent intent = new Intent(getContext().getApplicationContext(), FrmListadoEgresos.class);
        intent.putExtra(EXTRA_ID_HOJA, mHoja.getIdHoja());
        intent.putExtra(EXTRA_ID_SUCURSAL, mHoja.getIdSucursal());
        startActivity(intent);
    }

    public void verTotales() {
        VerTotalesDialogFragment dialogFragment = new VerTotalesDialogFragment();
        dialogFragment.show(getFragmentManager(), "dialog");
    }

    public void cargarEgreso() {
        try {
            Intent intent = new Intent(getContext().getApplicationContext(), FrmEgreso.class);
            intent.putExtra(EXTRA_ID_HOJA, mHoja.getIdHoja());
            intent.putExtra(EXTRA_ID_SUCURSAL, mHoja.getIdSucursal());
            intent.putExtra(Compra.EXTRA_MODO, FrmEgreso.ALTA);
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDialogFiltros() {
        String[] options = {"Todos", "Atendidos", "Anulados", "No Entregados", "No atendidos"};

        SingleChoiceDialogFragment dialogFragment = SingleChoiceDialogFragment.newInstance(options, mPosFiltro,
                "Filtro", new SingleChoiceListener() {

                    @Override
                    public void onAcceptItem(int pos) {
                        mPosFiltro = pos;
                        mAdapter.setCampoFiltro(mPosFiltro);
                        mAdapter.getFilter().filter("");
                    }
                });
        dialogFragment.show(getChildFragmentManager(), "fd!");

    }

    private void showDialogOrdenamiento() {
        String codigo = getString(R.string.codigo);
        String razonSocial = getString(R.string.lblRazonSocial);
        String saldoAsc = "Total factura (Asc.)";
        String saldoDesc = "Total factura (Des.)";

        final String[] items = {razonSocial, codigo, saldoAsc, saldoDesc};

        SingleChoiceDialogFragment singleChoiceDialogFragment = SingleChoiceDialogFragment.newInstance(items, mPosOrden,
                SELECCIONAR_OPCION, new SingleChoiceListener() {

                    @Override
                    public void onAcceptItem(int pos) {
                        mPosOrden = pos;
                        ordenarClientes();
                    }

                });
        singleChoiceDialogFragment.show(getFragmentManager(), "gd");

    }

    private void ordenarClientes() {
        HojaComparator comparator = new HojaComparator(mPosOrden);
        mAdapter.sort(comparator);
    }

    private void showDialogConfigBusqueda() {

        String[] opciones = {"Nombre", "Dirección", "Código", "Nro de Hoja"};
        SingleChoiceDialogFragment dialogFragment = SingleChoiceDialogFragment.newInstance(opciones,
                mAdapter.getCampoBusqueda(), "Seleccione criterio de búsqueda", new SingleChoiceListener() {

                    @Override
                    public void onAcceptItem(int pos) {

                        setHintSearchView(pos);
                        mAdapter.setCampoBusqueda(pos);

                    }
                });
        dialogFragment.show(getChildFragmentManager(), "dialog");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mView;
    }

    public void initComponents(View view) {
        tvCtaCte.setText(Formatter.formatMoney(HojaBo.getCtaCteDetalles(mHojaDetalles)));
        tvAnulado.setText(Formatter.formatMoney(HojaBo.getAnulado(mHojaDetalles)));
        tvContado.setText(Formatter.formatMoney(HojaBo.getCdoDetalles(mHojaDetalles)));
        tvNotaCredito.setText(Formatter.formatMoney(HojaBo.getCreditoDetalles(mHojaDetalles)));
        tvPendiente.setText(Formatter.formatMoney(HojaBo.getPendiente(mHojaDetalles)));
    }

    private void updateViews() {
        if (mAdapter != null && mAdapter.getCount() > 0) {
            tvContado.setText(Formatter.formatMoney(mAdapter.mTotalContado));
            tvCtaCte.setText(Formatter.formatMoney(mAdapter.mTotalCtaCte));
            tvAnulado.setText(Formatter.formatMoney(mAdapter.mTotalAnulado));
            tvNotaCredito.setText(Formatter.formatMoney(mAdapter.mTotalNCs));
            if (mAdapter.mTotalPendientes > 0) {
                llPendiente.setVisibility(View.VISIBLE);
                tvPendiente.setText(Formatter.formatMoney(mAdapter.mTotalPendientes));
            }
        } else {
            tvContado.setText(Formatter.formatMoney(HojaBo.getCdoDetalles(mHojaDetalles)));
            tvCtaCte.setText(Formatter.formatMoney(HojaBo.getCtaCteDetalles(mHojaDetalles)));
            tvAnulado.setText(Formatter.formatMoney(HojaBo.getAnulado(mHojaDetalles)));
            tvNotaCredito.setText(Formatter.formatMoney(HojaBo.getCreditoDetalles(mHojaDetalles)));
            if (HojaBo.getPendiente(mHojaDetalles) > 0) {
                llPendiente.setVisibility(View.VISIBLE);
                tvPendiente.setText(Formatter.formatMoney(HojaBo.getPendiente(mHojaDetalles)));
            }
        }
    }

    private void setHintSearchView(int pos) {
        switch (pos) {
            case HojasAdapter.CRITERIA_DIRECCION:
                mSearchView.setQueryHint("Por dirección");
                break;
            case HojasAdapter.CRITERIA_ID:
                mSearchView.setQueryHint("Por id");
                break;
            case HojasAdapter.CRITERIA_NOMBRE:
                mSearchView.setQueryHint("Por nombre");
                break;
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (mSearchView != null) {
            mSearchView.setOnQueryTextListener(new OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String arg0) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String arg0) {
                    mAdapter.getFilter().filter(arg0);
                    this.onQueryTextSubmit(arg0);
                    updateViews();
                    return false;
                }
            });
            setHintSearchView(VentaDetalleAdapter.DESCRIPCION_ARTICULO);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v("Fede", "Fede");
        if (shouldExecuteOnResume) {
            updateViews();
        } else {
            shouldExecuteOnResume = true;
        }
    }

    private void onItemClickAction(int pos, HojaDetalle hojaDetalle) {
        switch (pos) {
            case 0:
                showDevolucion(hojaDetalle);
                break;
            case 1:
                showNoEntregado();
                break;
            case 2:
                showAnular();
                break;

        }
    }

    private void showNoEntregado() {
        SimpleDialogFragment dialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK_CANCEL,
                "Esta seguro que desea marcar como pendiente la factura?", "Confirmación",
                new SimpleDialogFragment.OkCancelListener() {

                    @Override
                    public void onOkSelected() {
                        mCurrentHojaDetalle.setTiEstado(HojaDetalle.PENDIENTE);
                        try {
                            mHojaBo.setNoEntregado(mHoja, mCurrentHojaDetalle, getActivity());
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            mAdapter.updateItemView(currentPosition, mCurrentHojaDetalle);

                        }

                    }

                    @Override
                    public void onCancelSelected() {

                    }
                });
        dialogFragment.show(getChildFragmentManager(), "dialogAnular");
    }

    private void showAnular() {
        String msjAnular = "Se va a generar un crédito de " + Formatter.formatMoney(mCurrentHojaDetalle.getPrTotal())
                + " a favor del cliente. Esta seguro que desear anular la factura?";
        final MotivoCreditoBo creditoBo = new MotivoCreditoBo(_repoFactory);
        List<String> elements;

        try {
            elements = MotivoCreditoBo.parseToListArray(creditoBo.recoveryAll());
            SpinnerDialogFragment dialogFragment = SpinnerDialogFragment.newInstance(msjAnular, "Elija un motivo",
                    new SpinnerDialogListener() {

                        @Override
                        public void onAccepted(int posSelected) {
                            try {
                                mVentaBo = new VentaBo(_repoFactory);
                                Venta venta = mVentaBo.recoveryById(mCurrentHojaDetalle.getId().getIdFcnc(),
                                        mCurrentHojaDetalle.getId().getIdTipoab(), mCurrentHojaDetalle.getId().getIdPtovta(),
                                        mCurrentHojaDetalle.getId().getIdNumdoc());
                                //Combo Especial
                                venta = reagruparCombos(venta);
                                venta.setIdMotivoRechazoNC(creditoBo.recoveryAll().get(posSelected).getIdMotivoRechazoNC());
                                mVentaBo.devolverTodo(venta);

                                mCurrentHojaDetalle.setTiEstado(HojaDetalle.ANULADO);
                                mCurrentHojaDetalle.setPrNotaCredito(mCurrentHojaDetalle.getPrTotal());

                                mHojaDetalleBo.guardarHojaDetalle(venta, mCurrentHojaDetalle, getActivity());

                                mAdapter.updateItemView(currentPosition,
                                        mCurrentHojaDetalle);
                                Toast.makeText(getActivity(), "Factura anulada exitósamente", Toast.LENGTH_LONG).show();
                                updateViews();
                            } catch (Exception e) {
                                SimpleDialogFragment sd = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK,
                                        "Error", "No se pudo anular. Motivo: " + e.getMessage());
                                sd.show(getFragmentManager(), getTag());
                            }

                        }
                    }, elements);
            updateViews();
            dialogFragment.show(getChildFragmentManager(), "nose");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Venta reagruparCombos(Venta venta) {
        //Recorrer venta buscando cabeceras de combo, buscar detalles con idPromo = id cabecera, asignar a detalleCombo de la cabecera y quitar de la venta
        for (Iterator<VentaDetalle> iterator = venta.getDetalles().iterator(); iterator.hasNext(); ) {
            VentaDetalle vd = iterator.next();
            if (vd.isCabeceraPromo()) {
                //Tirar magia
                double cantidad = 0d;
                for (VentaDetalle vd2 : venta.getDetalles()) { //Sumo cantidades de detalles con idPromo = idArticulo de la cabecera
                    if (vd2.getTipoLista() == vd.getTipoLista() && vd2.getIdPromo() != null && vd2.getIdPromo() == vd.getArticulo().getId()) {
                        cantidad += vd2.getCantidad();
                    }
                }
                //Calculo la cantidad porque pudo haber cambiado la definición del combo
                double cantidadPorCombo = cantidad / vd.getCantidad();
                double cantidadAsignada = 0d;

                //Vuelvo a recorrer para cargar detalles del combo
                for (Iterator<VentaDetalle> iterator2 = venta.getDetalles().iterator(); iterator2.hasNext(); ) {
                    VentaDetalle vd2 = iterator2.next();
                    if (vd2.getTipoLista() == vd.getTipoLista() && vd2.getIdPromo() != null && vd2.getIdPromo() == vd.getArticulo().getId() && vd2.getCantidad() > 0) { //Es detalle del combo

                        if (cantidadAsignada < cantidadPorCombo * vd.getCantidad()) {

                            VentaDetalle vdAux = null;
                            try {
                                vdAux = vd2.clone();
                                double cantidadAAsignar;
                                if (vdAux.getCantidad() <= cantidadPorCombo * vd.getCantidad() - cantidadAsignada) {
                                    cantidadAAsignar = vdAux.getCantidad();
                                } else {
                                    cantidadAAsignar = cantidadPorCombo * vd.getCantidad() - cantidadAsignada;
                                }

                                vdAux.setUnidades(0);
                                vdAux.setBultos(0);
                                vdAux.setIdFcSecuencia(vdAux.getId().getSecuencia());

                                //Veo si saco de unidades o de bultos
                                if (vd2.getUnidades() >= cantidadAAsignar) {
                                    vd2.setUnidades(vd2.getUnidades() - cantidadAAsignar);
                                    vdAux.setUnidades(cantidadAAsignar);
                                } else if (vd2.getBultos() * vd2.getArticulo().getUnidadPorBulto() >= cantidadAAsignar) {
                                    vd2.setBultos(Math.toIntExact(vd2.getBultos() - Math.round(cantidadAAsignar / vd2.getArticulo().getUnidadPorBulto())));
                                    vdAux.setBultos(Math.toIntExact(Math.round(cantidadAAsignar / vd2.getArticulo().getUnidadPorBulto())));
                                } else { //Tengo que sacar de bultos y de unidades
                                    cantidadAAsignar -= vd2.getBultos() * vd2.getArticulo().getUnidadPorBulto();
                                    cantidadAsignada += vd2.getBultos() * vd2.getArticulo().getUnidadPorBulto();
                                    vdAux.setBultos(vd2.getBultos());
                                    vd2.setBultos(0);
                                    vdAux.setUnidades(cantidadAAsignar);
                                    vd2.setUnidades(vd2.getUnidades() - cantidadAAsignar);
                                }
                                cantidadAsignada += cantidadAAsignar;
                                vd.getDetalleCombo().add(vdAux);
                            } catch (CloneNotSupportedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        //Elimino detalles que hayan quedado con cantidad 0
        for (Iterator<VentaDetalle> iterator = venta.getDetalles().iterator(); iterator.hasNext(); ) {
            VentaDetalle vd = iterator.next();
            if (vd.getCantidad() == 0) {
                iterator.remove();
            }
        }
        return venta;

    }

    private void showDevolucion(HojaDetalle hojaDetalle) {
        Intent intent = new Intent(getActivity(), FrmDetalleFacturaReparto.class);
        Entrega entrega = new Entrega();
        try {
            Integer idEntrega = mHojaDetalleBo.recoveryIdEntrega(hojaDetalle);
            entrega = mEntregaBo.recoveryByID(idEntrega);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        HojaDetalle hojaDetalleActualizada = null;
        try {
            hojaDetalleActualizada = mHojaDetalleBo.recoveryById(hojaDetalle.getId());
            hojaDetalle = hojaDetalleActualizada;
        } catch (Exception e) {
            e.printStackTrace();
        }
        hojaDetalle.setEntrega(entrega);

        intent.putExtra(FrmDetalleFacturaReparto.KEY_HOJA_DETALLE, hojaDetalle);

        startActivityForResult(intent, CODE_ESTATE);

    }

    private void showDevoluciones(List<HojaDetalle> hojasDetalle) {
        Intent intent = new Intent(getActivity(), FrmDetallesFacturasReparto.class);
        List<HojaDetalle> hojasDetallesActualizadas = new ArrayList<>();
        //Esta bandera la uso para verificar que todas las hojaDetalle sean del mismo cliente
        boolean mismoCliente = true;
        boolean mismaEntrega = true;
        PkCliente clienteHojaDetalle = hojasDetalle.get(0).getCliente().getId();
        Entrega entrega = new Entrega();
        try {
            Integer idEntrega = mHojaDetalleBo.recoveryIdEntrega(hojasDetalle.get(0));
            entrega = mEntregaBo.recoveryByID(idEntrega);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        hojasDetalle.get(0).setEntrega(entrega);
        Entrega entregaPrimera = hojasDetalle.get(0).getEntrega();
        for (HojaDetalle hojaDetalle : hojasDetalle) {
            if (hojaDetalle.getCliente().getId().equals(clienteHojaDetalle)) {
                try {
                    Integer idEntrega = mHojaDetalleBo.recoveryIdEntrega(hojaDetalle);
                    entrega = mEntregaBo.recoveryByID(idEntrega);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (entrega.getId() == entregaPrimera.getId()) {
                    hojaDetalle.setEntrega(entrega);
                } else {
                    Toast.makeText(getContext().getApplicationContext(), getString(R.string.msjEntregasDistintas), Toast.LENGTH_SHORT).show();
                    mismaEntrega = false;
                    break;
                }
            } else {
                Toast.makeText(getContext().getApplicationContext(), getString(R.string.msjClientesDistintos), Toast.LENGTH_SHORT).show();
                mismoCliente = false;
                break;
            }
        }
        if (mismoCliente && mismaEntrega) {
            FrmDetallesFacturasReparto detallesFacturasReparto = new FrmDetallesFacturasReparto();
            //detallesFacturasReparto.setArguments(hojasDetallesActualizadas);
            intent.putExtra(FrmDetallesFacturasReparto.KEY_HOJA_DETALLE, (Serializable) hojasDetalle);
            startActivityForResult(intent, CODE_ESTATE);
        } else {
            mActionMode.finish();
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            try {
                HojaDetalle detalle = (HojaDetalle) data.getSerializableExtra("hojadetalle");
                mAdapter.updateItemView(currentPosition, detalle);
            } catch (Exception e) {
                List<HojaDetalle> detalle = (List<HojaDetalle>) data.getSerializableExtra("hojadetalle");
                int i = 0;
                for (HojaDetalle hojaDetalle : mAdapter.getSelected()) {
                    mAdapter.updateItemView(mAdapter.getPosition(hojaDetalle), detalle.get(i));
                    i = +1;
                }
                mActionMode.finish();
            }
            loadData();
            updateViews();
        }
    }

    private void onLisItemCheck(int position) {
        mCurrentHojaDetalle = mAdapter.getItem(position);
        if (mCurrentHojaDetalle.getDocumento().getFuncionTipoDocumento() == 2) {
            Toast.makeText(getActivity(), "No es posible realizar operaciones sobre este comprobante.", Toast.LENGTH_SHORT).show();
            return;
        }
        currentPosition = position;
        String[] optionsDetalle = optionsItem;
        try {
            if (mHojaDetalleBo.isEnviado(mCurrentHojaDetalle)) {
                optionsDetalle = optionsSoloVerDetalles;
            } else {
                if (mHoja.getIsRendida().equals(Hoja.PRERENDIDO) || mHoja.getIsRendida().equals(Hoja.APROBADA)) {
                    optionsDetalle = optionsSoloVerDetalles;
                } else {
                    optionsDetalle = optionsItem;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        mAdapter.toggleSelection(position);
        if (mAdapter.getSelectedCount() > 0 && mActionMode == null) {
            final OptionsDialogFragment choiceDialogFragment = OptionsDialogFragment.newInstance(optionsDetalle,
                    new MultipleChoiceListener() {

                        @Override
                        public void onItemSelected(int pos) {
                            onItemClickAction(pos, mCurrentHojaDetalle);

                        }
                    });
            choiceDialogFragment.show(getChildFragmentManager(), "dialog");
            mAdapter.deselectView(position);
        } else if (!(mAdapter.getSelectedCount() > 0) && mActionMode != null) {
            mActionMode.finish();

        }
        if (mActionMode != null) {
            String msj = (mAdapter.getSelectedCount() > 1) ? " seleccionadas" : " seleccionada";
            mActionMode.setTitle(String.valueOf(mAdapter.getSelectedCount()) + msj);
            Drawable icon = (mAdapter.isSelectAll()) ? getResources().getDrawable(R.drawable.ic_select_all_selected) : getResources()
                    .getDrawable(R.drawable.ic_select_all_unselected);
            itemSelectAll.setIcon(icon);
        }


    }

    private void onLisItemCheckLong(int position) {
        mAdapter.toggleSelection(position);
        if (mAdapter.getSelectedCount() > 0 && mActionMode == null) {
            mActionMode = getActivity().startActionMode(HojaFragment.this);
        } else if (!(mAdapter.getSelectedCount() > 0) && mActionMode != null) {
            mActionMode.finish();

        }
        if (mActionMode != null) {
            String msj = (mAdapter.getSelectedCount() > 1) ? " seleccionadas" : " seleccionada";
            mActionMode.setTitle(String.valueOf(mAdapter.getSelectedCount()) + msj);
            Drawable icon = (mAdapter.isSelectAll()) ? getResources().getDrawable(R.drawable.ic_select_all_selected) : getResources()
                    .getDrawable(R.drawable.ic_select_all_unselected);
            itemSelectAll.setIcon(icon);
        }
    }

    public static void setAutoOrientationEnabled(Context context, boolean enabled) {
        Settings.System.putInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, enabled ? 1 : 0);
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        actionMode.getMenuInflater().inflate(R.menu.menu_select_items_hoja_detalle, menu);
        itemSelectAll = menu.findItem(R.id.mni_select_all);
        itemSelectAll.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (mAdapter.getSelectedCount() < mAdapter.getCount()) {
                    mAdapter.selectAllViews();
                    Drawable icon = (mAdapter.isSelectAll()) ? getResources().getDrawable(R.drawable.ic_select_all_selected)
                            : getResources().getDrawable(R.drawable.ic_select_all_unselected);
                    itemSelectAll.setIcon(icon);
                    mActionMode.setTitle(mAdapter.getSelectedCount() + " seleccionadas");
                } else if (mAdapter.getSelectedCount() == mAdapter.getCount()) {
                    mAdapter.removeSelection();
                    mActionMode.finish();
                }
                return true;
            }
        });
        MenuItem itemPagar = menu.findItem(R.id.mni_pagar_seleccionadas);
        itemPagar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                List<HojaDetalle> hojasDetalles = mAdapter.getSelected();

                showDevoluciones(hojasDetalles);

                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        SparseBooleanArray selected = mAdapter.getSelectedItems();
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < selected.size(); i++) {
            if (selected.valueAt(i)) {
                HojaDetalle selectedItem = mAdapter.getItem(selected.keyAt(i));
                message.append(selectedItem + "\n");
            }
        }
        //Toast.makeText(mContext, message.toString(), Toast.LENGTH_LONG).show();

        // close action mode
        actionMode.finish();
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        mAdapter.removeSelection();
        mActionMode = null;
    }


}
