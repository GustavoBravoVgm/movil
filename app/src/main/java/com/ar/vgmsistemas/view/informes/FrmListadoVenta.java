package com.ar.vgmsistemas.view.informes;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.DocumentoBo;
import com.ar.vgmsistemas.bo.EmpresaBo;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.bo.VentaBo;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.Documento;
import com.ar.vgmsistemas.entity.Empresa;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.printer.PedidoPrinter;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.CategoriaRecursoHumano;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.dialogs.OptionsDialogFragment;
import com.ar.vgmsistemas.view.dialogs.SimpleDialogFragment;
import com.ar.vgmsistemas.view.dialogs.SingleChoiceDialogFragment;
import com.ar.vgmsistemas.view.menu.BaseNavigationFragment;
import com.ar.vgmsistemas.view.reparto.ListadoPedidoSugeridoAdapter;
import com.ar.vgmsistemas.view.venta.FrmVenta;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FrmListadoVenta extends BaseNavigationFragment {

    private static final String TAG = FrmListadoVenta.class.getCanonicalName();
    private static final int DIALOG_FECHA_DESDE = 0;
    private static final int DIALOG_FECHA_HASTA = 1;

    private static final int POSITION_ENVIADOS = 0;
    private static final int POSITION_NO_ENVIADOS = 1;
    private static final int POSITION_TODOS = 2;
    private static final int POSITION_ANULADOS = 3;

    private static final int POSITION_FILTRO_ENVIADOS = 0;
    private static final int POSITION_FILTRO_ANULADOS = 1;
    private static final int POSITION_FILTRO_TIPO_DOCUMENTO = 2;

    private static final int MODO_CONSULTA = 2;

    private static final int POSITION_VER_DETALLES = 0;
    private static final int POSITION_CANCELAR_PEDIDOS = 1;
    private static final int POSITION_CANCELAR_PEDIDOS_O_IMPRIMIR = 1;
    private static final int POSITION_MODIFICAR_PEDIDOS = 2;
    private static final int POSITION_IMPRIMIR_PEDIDOS = 3;

    private static final int POSITION_GENERAR_REMITO = 0;

    private static final String TAG_FRM_LISTADO_PEDIDO = "Frm_listado_pedido";

    //Dialogs
    public static final String EXTRA_TIPO_DOCUMENTO = "tipoDocumento";
    public static final String EXTRA_CLIENTE = "cliente";
    public static final String EXTRA_MODO_VISTA = "modoVista";

    public static final int MODO_NORMAL = 0;
    public static final int MODO_REPARTO = 1;

    private List<Venta> _ventas;
    private List<List<VentaDetalle>> _ventasDetalle;
    private ListadoVentaAdapter mAdapter;

    private List<String> _documentos;
    private Date fechaDesde;
    private Date fechaHasta;
    private EditText txtFechaDesde;
    private EditText txtFechaHasta;
    private VentaBo _ventaBo;
    protected Venta itemSeleccionado;
    protected Cliente _cliente;
    protected String _tipoDocumento;
    private TextView lblTotalVtaValue;
    private TextView lblTotalVtaGralValue;
    protected ListView listadoVentas;
    private int mModoVista;
    Empresa empresa;
    private View _view;

    private RepositoryFactory _repoFactory;

    public static FrmListadoVenta newInstance(int modo) {
        Bundle b = new Bundle();
        b.putInt(EXTRA_MODO_VISTA, modo);
        FrmListadoVenta frmListadoVenta = new FrmListadoVenta();
        frmListadoVenta.setArguments(b);
        return frmListadoVenta;
    }

    public static FrmListadoVenta newInstance(String tipoDocumento, Cliente cliente) {
        Bundle b = new Bundle();
        b.putString(EXTRA_TIPO_DOCUMENTO, tipoDocumento);
        b.putSerializable(EXTRA_CLIENTE, cliente);
        FrmListadoVenta frmListadoVenta = new FrmListadoVenta();
        frmListadoVenta.setArguments(b);
        return frmListadoVenta;
    }

    public static FrmListadoVenta newInstance(String tipoDocumento) {
        Bundle b = new Bundle();
        b.putString(EXTRA_TIPO_DOCUMENTO, tipoDocumento);
        FrmListadoVenta frmListadoVenta = new FrmListadoVenta();
        frmListadoVenta.setArguments(b);
        return frmListadoVenta;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.lyt_listado_venta, container, false);
        initComponents(view);
        this._view = view;
        return view;
    }

    @Override
    public void onPause() {
        getActivity().getSupportFragmentManager().saveFragmentInstanceState(this);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        initVar();
        actualizarVentas();
    }

    private void getDialogSeleccionarFecha(int tipoFecha) {
        final int tipo = tipoFecha;
        Date fecha;
        if (tipoFecha == DIALOG_FECHA_DESDE)
            fecha = this.fechaDesde;
        else
            fecha = this.fechaHasta;

        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialogFecha = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.set(year, monthOfYear, dayOfMonth);

                if (tipo == DIALOG_FECHA_DESDE) {
                    fechaDesde = cal.getTime();
                    String fechaDesdeString = Formatter.formatDate(fechaDesde);
                    txtFechaDesde.setText(fechaDesdeString);
                } else {
                    fechaHasta = cal.getTime();
                    String fechaHastaString = Formatter.formatDate(fechaHasta);
                    txtFechaHasta.setText(fechaHastaString);
                }
                actualizarVentas();
                filtrarVentas(PreferenciaBo.getInstance().getPreferencia().getFiltroVentasEnviadas());
            }
        }, year, month, day);

        dialogFecha.show();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle b = getArguments();
        if (b != null) {
            mModoVista = b.getInt(EXTRA_MODO_VISTA, MODO_NORMAL);
            if (mModoVista == MODO_NORMAL) {
                _cliente = (Cliente) b.getSerializable("cliente");
                _tipoDocumento = b.getSerializable(EXTRA_TIPO_DOCUMENTO).toString();
            }
        } else {
            mModoVista = MODO_NORMAL;
        }
        itemSeleccionado = (Venta) getLastNonConfigurationInstance();
    }

    protected void initComponents(View view) {
        _repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        EmpresaBo empresaBo = new EmpresaBo(_repoFactory);
        try {
            this.empresa = empresaBo.recoveryEmpresa();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //lstVentas
        this.listadoVentas = view.findViewById(R.id.listadoVentas); //(ListView)
        this.listadoVentas.setOnItemClickListener((parent, view1, position, id) -> lstOnItemClick(parent/*, view*/, position/*, id*/));
        //txtFechaDesde
        this.txtFechaDesde = view.findViewById(R.id.txtFechaDesde);//(EditText)
        this.txtFechaDesde.setKeyListener(null);
        this.txtFechaDesde.setOnClickListener(v -> getDialogSeleccionarFecha(DIALOG_FECHA_DESDE));

        //txtFechaHasta
        this.txtFechaHasta = view.findViewById(R.id.txtFechaHasta);//(EditText)
        this.txtFechaHasta.setKeyListener(null);
        this.txtFechaHasta.setOnClickListener(v -> getDialogSeleccionarFecha(DIALOG_FECHA_HASTA));
    }

    private void lstOnItemClick(AdapterView<?> adapter, int position) {

        itemSeleccionado = (Venta) adapter.getItemAtPosition(position);
        if (mModoVista == MODO_REPARTO) {
            getDialogRemitoHacienda();
        } else {
            getDialogOpciones();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.clear();
        inflater.inflate(R.menu.mn_lista_ventas, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.mni_filtro_ventas:
                getDialogOpcionesFiltro();
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    private void actualizarVentas() {
        try {
            loadData();
            mAdapter.setVentas(_ventas);
            if (mModoVista == MODO_REPARTO) {

                ((ListadoPedidoSugeridoAdapter) mAdapter).setVentasDetalle(_ventasDetalle);

            }
            actualizarTotales(POSITION_TODOS);

        } catch (Exception e) {
            ErrorManager.manageException("FrmListadoVentas", "actualizarVentas", e, getActivity());
        }
    }


    protected void loadData() {
        try {
            if (_cliente == null) {
                if (_tipoDocumento == null) {
                    _ventas = _ventaBo.recoveryByRepartidor(PreferenciaBo.getInstance().getPreferencia(getActivity()).getIdVendedor());
                    try {
                        this._ventasDetalle = _ventaBo.recoveryVentasDetalleByVenta(this._ventas);
                    } catch (Exception e) {
                        e.getMessage();
                    }
                } else {
                    _ventas = _ventaBo.recoveryByPeriodo(this.fechaDesde, this.fechaHasta, PreferenciaBo.getInstance().getPreferencia(getActivity()).getIdVendedor());
                }
            } else {
                _ventas = _ventaBo.recoveryByCliente(_cliente, _tipoDocumento);
            }
        } catch (Exception e) {
            Toast toast = Toast.makeText(getActivity(), ErrorManager.ErrorAccederALosDatos, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private void getDialogOpciones() {
        //ver si hacemos el cancelar pedido
        String[] items;
        String anularPedido = getString(R.string.mnCancelarPedido);
        String verDetalles = getString(R.string.mnVerDetalles);
        String modificarPedido = getString(R.string.mnModificarPedido);
        String imprimirPedido = getString(R.string.mnImprimirPedido);

        try {
            final boolean isEnviado = _ventaBo.isEnviado(itemSeleccionado);
            String doc = itemSeleccionado.getId().getIdDocumento();
            String letra = itemSeleccionado.getId().getIdLetra();
            int ptovta = itemSeleccionado.getId().getPuntoVenta();
            final int tipoImpresion = _ventaBo.tipoImpresionVenta(doc, letra, ptovta);
            //Por Tarea #3335
            if (PreferenciaBo.getInstance().getPreferencia().isEnvioPedidoAutomatico() || isEnviado) {
                if (tipoImpresion != 0) {
                    items = new String[]{verDetalles, imprimirPedido};
                } else {
                    items = new String[]{verDetalles};
                }

            } else {
                if (tipoImpresion != 0) {
                    items = new String[]{verDetalles, anularPedido, modificarPedido, imprimirPedido};
                } else {
                    items = new String[]{verDetalles, anularPedido, modificarPedido};
                }
            }
            OptionsDialogFragment optionsDialogFragment = OptionsDialogFragment.newInstance(items, pos -> dlgOpcionesOnClick(pos, isEnviado));
            optionsDialogFragment.show(getActivity().getSupportFragmentManager(), TAG_FRM_LISTADO_PEDIDO);
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "getDialogOpciones", e, getActivity());
        }
    }

    private void getDialogRemitoHacienda() {
        //ver si hacemos el cancelar pedido
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String[] items;
        String generarRemito = getString(R.string.mnGenerarRemito);
        String anularPedido = getString(R.string.mnAnularPedido);
        try {
            boolean isGenerado = (itemSeleccionado.getSnGenerado().equals(Venta.SI));
            boolean isAnulado = (itemSeleccionado.getAnulo()).equals(Venta.SI);
            if (!isGenerado && !isAnulado) {
                items = new String[]{generarRemito, anularPedido};
                OptionsDialogFragment optionsDialogFragment = OptionsDialogFragment.newInstance(items, new OptionsDialogFragment.MultipleChoiceListener() {

                    @Override
                    public void onItemSelected(int pos) {
                        dlgOpcionesHaciendaOnClick(pos);
                    }
                });
                optionsDialogFragment.show(getActivity().getSupportFragmentManager(), TAG_FRM_LISTADO_PEDIDO);

            } else if (isGenerado) {
                SimpleDialogFragment simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK, ErrorManager.INFORMACION, getString(R.string.msjRemitoGenerado));
                simpleDialogFragment.show(getActivity().getSupportFragmentManager(), TAG_FRM_LISTADO_PEDIDO);
            } else if (isAnulado) {
                SimpleDialogFragment simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK, ErrorManager.INFORMACION, getString(R.string.msjRemitoAnulado));
                simpleDialogFragment.show(getActivity().getSupportFragmentManager(), TAG_FRM_LISTADO_PEDIDO);
            }
            builder.setTitle(R.string.tituloSeleccionarAccion);
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "getDialogOpciones", e, getActivity());
        }
    }

    private void dlgOpcionesHaciendaOnClick(int position) {
        switch (position) {
            case POSITION_GENERAR_REMITO: //Ver detalles
                generarRemito();
                break;
            case POSITION_CANCELAR_PEDIDOS: //Cancelar pedido
                cancelarPedido();
                break;
            case 2: //Modificar pedido
                modificarPedido();
                break;
        }
    }

    private void actualizarTotales(int position) {
        List<Venta> ventasAdapter;
        ventasAdapter = mAdapter.ventas;

        double totalVentas = 0d;
        double totalGralVenta = 0d;

        for (int i = 0; i < ventasAdapter.size(); i++) {

            Venta venta = ventasAdapter.get(i);
            int tipoFuncion = venta.getDocumento().getFuncionTipoDocumento();
            int signo = 1;
            double importe;

            if (tipoFuncion == Documento.FUNCION_CREDITO) {
                signo = -1;
            }

            String anulado = "S";
            if (venta.getAnulo().equals(anulado) && (position != POSITION_ANULADOS)) {
                signo = 0;
            }
            if (venta.getDocumento().isLegal() || (empresa.getSnSumIvaReporteMovil().equals("N") && venta.getDocumento().getTiAplicaIva() == 0)) {
                importe = venta.getSubtotal() * signo;
            } else {
                importe = venta.getTotal() * signo;
            }
            totalVentas += importe;
            totalGralVenta += venta.getTotal() * signo;
        }
        lblTotalVtaValue.setText(Formatter.formatMoney(totalVentas));
        lblTotalVtaGralValue.setText(Formatter.formatMoney(totalGralVenta));
    }

    private void getDialogOpcionesFiltro() {
        OptionsDialogFragment optionsDialogFragment = OptionsDialogFragment.newInstance(getItemsFiltro(), new OptionsDialogFragment.MultipleChoiceListener() {

            @Override
            public void onItemSelected(int pos) {
                dlgOpcionesFiltroOnClick(pos);

            }

        });
        optionsDialogFragment.show(getActivity().getSupportFragmentManager(), TAG_FRM_LISTADO_PEDIDO);
    }

    private String[] getItemsFiltro() {
        String tipoDocumento = getString(R.string.mnTipoDocumento);
        String enviados = getString(R.string.mnEnviados);
        String anulados = getString(R.string.mnAnulo);

        switch (mModoVista) {
            case MODO_REPARTO:
                String[] itemsReparto = {enviados, anulados};
                return itemsReparto;

            default:
                String[] itemsDefault = {enviados, anulados, tipoDocumento};
                return itemsDefault;
        }

    }

    private void getDialogCancelarPedido() {
        SimpleDialogFragment simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK_CANCEL, "Seleccione una opcion", getString(R.string.msjCancelarPedido), new SimpleDialogFragment.OkCancelListener() {

            @Override
            public void onOkSelected() {
                onDeletePedido();
            }

            @Override
            public void onCancelSelected() {
            }

        });
        simpleDialogFragment.show(getActivity().getSupportFragmentManager(), TAG_FRM_LISTADO_PEDIDO);
    }

    private void getDialogFiltroTipoDocumento() {
        //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        DocumentoBo documentoBo = new DocumentoBo(_repoFactory);
        String[] items;
        try {
            _documentos = documentoBo.recoveryTiposDocumentos(Documento.VENTA);
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "getDialogoFiltroTipoDocumento", e, getActivity());
        }
        _documentos.add("Todos");
        items = new String[_documentos.size()];
        _documentos.toArray(items);
        String filtroTipoDocumento = PreferenciaBo.getInstance().getPreferencia(getActivity().getApplicationContext()).getFiltroTipoDocumento();
        int itemSeleccionado = _documentos.indexOf(filtroTipoDocumento);
        if (itemSeleccionado == -1)
            itemSeleccionado = _documentos.size() - 1;

        SingleChoiceDialogFragment singleChoiceDialogFragment = SingleChoiceDialogFragment.newInstance(items, itemSeleccionado, getString(R.string.lblSeleccionarOpcion), new SingleChoiceDialogFragment.SingleChoiceListener() {

            @Override
            public void onAcceptItem(int pos) {
                dlgFiltroTipoDocumentoOnClick(pos);
            }
        });
        singleChoiceDialogFragment.show(getActivity().getSupportFragmentManager(), TAG);
    }

    private void getDialogFiltroEnviados() {
        //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());		
        String enviados = this.getString(R.string.enviados);
        String noEnviados = this.getString(R.string.noEnviados);
        String todos = this.getString(R.string.todos);

        String[] items = {enviados, noEnviados, todos};

        int itemSeleccionado = -1;
        switch (PreferenciaBo.getInstance().getPreferencia(getActivity().getApplicationContext()).getFiltroVentasEnviadas()) {
            case Preferencia.FILTRO_ENVIADOS:
                itemSeleccionado = POSITION_ENVIADOS;
                break;
            case Preferencia.FILTRO_NO_ENVIADAS:
                itemSeleccionado = POSITION_NO_ENVIADOS;
                break;
            case Preferencia.FILTRO_TODOS:
                itemSeleccionado = POSITION_TODOS;
                break;
        }
        SingleChoiceDialogFragment singleChoiceDialogFragment = SingleChoiceDialogFragment.newInstance(items, itemSeleccionado, getString(R.string.lblSeleccionarOpcion), new SingleChoiceDialogFragment.SingleChoiceListener() {

            @Override
            public void onAcceptItem(int pos) {
                dlgFiltroEnviadosOnClick(pos);

            }
        });

        singleChoiceDialogFragment.show(getActivity().getSupportFragmentManager(), TAG_FRM_LISTADO_PEDIDO);
    }

    private void dlgOpcionesOnClick(int position, boolean isEnviado) {
        switch (position) {
            case POSITION_VER_DETALLES: //Ver detalles
                mostrarPedido();
                break;
            case POSITION_CANCELAR_PEDIDOS_O_IMPRIMIR: //Cancelar pedido
                if (PreferenciaBo.getInstance().getPreferencia().isEnvioPedidoAutomatico() || isEnviado) {
                    PedidoPrinter p = new PedidoPrinter();
                    try {
                        p.print(_ventaBo.recoveryById(itemSeleccionado.getId()), getContext());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else
                    cancelarPedido();
                break;
            case POSITION_MODIFICAR_PEDIDOS:
                modificarPedido();
                break;
            case POSITION_IMPRIMIR_PEDIDOS:
                PedidoPrinter p = new PedidoPrinter();
                try {
                    p.print(_ventaBo.recoveryById(itemSeleccionado.getId()), getContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void generarRemito() {
        Venta venta;
        try {
            venta = _ventaBo.recoveryById(itemSeleccionado.getId());
            Intent intent = new Intent(getActivity(), FrmVenta.class);
            intent.putExtra(FrmVenta.EXTRA_VENTA, venta);
            intent.putExtra(FrmVenta.EXTRA_MODO, FrmVenta.GENERACION_REMITO);
            intent.putExtra(FrmVenta.EXTRA_FROM_PEDIDO_SUGERIDO, true);
            intent.putExtra(FrmVenta.EXTRA_CLIENT, venta.getCliente());

            startActivity(intent);

        } catch (Exception e) {
            ErrorManager.manageException(TAG, "onDeletePedido", e, getActivity());
        }
    }

    private void dlgOpcionesFiltroOnClick(int position) {
        switch (position) {
            case POSITION_FILTRO_ENVIADOS: //Filtro enviados
                //getActivity().showDialog(DIALOG_FILTRO_ENVIADOS);asd
                getDialogFiltroEnviados();
                break;
            case POSITION_FILTRO_ANULADOS:
                //showDialog(DIALOG_FILTRO_ENVIADOS);
                PreferenciaBo.getInstance().getPreferencia(getActivity().getApplicationContext()).setFiltroVentasEnviadas(Preferencia.FILTRO_ANULADOS);
                PreferenciaBo.getInstance().getPreferencia(getActivity().getApplicationContext()).setFiltroTipoDocumento("");
                filtrarVentas(POSITION_ANULADOS);
                break;
            case POSITION_FILTRO_TIPO_DOCUMENTO: //Filtro tipoDocumento
                getDialogFiltroTipoDocumento();
                break;
        }
    }

    private void dlgFiltroEnviadosOnClick(int position) {
        switch (position) {
            case POSITION_ENVIADOS: //Enviados
                PreferenciaBo.getInstance().getPreferencia(getActivity().getApplicationContext()).setFiltroVentasEnviadas(Preferencia.FILTRO_ENVIADOS);
                break;
            case POSITION_NO_ENVIADOS: //No enviados
                PreferenciaBo.getInstance().getPreferencia(getActivity().getApplicationContext()).setFiltroVentasEnviadas(Preferencia.FILTRO_NO_ENVIADAS);
                break;
            case POSITION_TODOS: //Todos
                PreferenciaBo.getInstance().getPreferencia(getActivity().getApplicationContext()).setFiltroVentasEnviadas(Preferencia.FILTRO_TODOS);
                break;
            case POSITION_ANULADOS: //Anulados
                PreferenciaBo.getInstance().getPreferencia(getActivity().getApplicationContext()).setFiltroVentasEnviadas(Preferencia.FILTRO_ANULADOS);
        }
        PreferenciaBo.getInstance().getPreferencia(getActivity().getApplicationContext()).setFiltroTipoDocumento("");
        filtrarVentas(position);
        //dialog.dismiss();
    }

    private void filtrarVentas(int position) {
        List<Venta> ventasFiltradas = _ventaBo.filtrarVentas(_ventas, getActivity().getApplicationContext(), position);
        mAdapter.setVentas(ventasFiltradas);
        actualizarTotales(position);
        filtrar();
    }

    private void dlgFiltroTipoDocumentoOnClick(int position) {
        String filtroTipoDocumento;
        if (position == _documentos.size() - 1)
            filtroTipoDocumento = "";
        else
            filtroTipoDocumento = _documentos.get(position);
        PreferenciaBo.getInstance().getPreferencia(getActivity().getApplicationContext()).setFiltroVentasEnviadas(Preferencia.FILTRO_TODOS);
        PreferenciaBo.getInstance().getPreferencia(getActivity().getApplicationContext()).setFiltroTipoDocumento(filtroTipoDocumento);
        filtrarVentas(Preferencia.FILTRO_TODOS);
    }

    private void filtrar() {
        mAdapter.getFilter().filter("");
    }

    private void mostrarPedido() {
        Intent intent = new Intent(getActivity(), FrmVenta.class);
        Venta venta;
        try {
            venta = _ventaBo.recoveryById(itemSeleccionado.getId());
            intent.putExtra("venta", venta);
            intent.putExtra("modo", MODO_CONSULTA);
            intent.putExtra(FrmVenta.EXTRA_CLIENT, venta.getCliente());
            startActivity(intent);
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "mostrarPedido", e, getActivity());

        }
    }

    private void cancelarPedido() {
        getDialogCancelarPedido();
    }

    private void modificarPedido() {
        Intent intent = new Intent(getActivity(), FrmVenta.class);
        Venta venta;
        int modificacion = 1;
        try {
            venta = _ventaBo.recoveryById(itemSeleccionado.getId());
            intent.putExtra("venta", venta);
            intent.putExtra("modo", modificacion);

            intent.putExtra(FrmVenta.EXTRA_CLIENT, venta.getCliente());
            startActivity(intent);
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "mostrarPedido", e, getActivity());
        }
    }

    private void onDeletePedido() {
        Venta venta;
        try {
            venta = _ventaBo.recoveryById(itemSeleccionado.getId());
            if (CategoriaRecursoHumano.isRepartidorDeHacienda()) {
                venta.setFechaEntrega(Calendar.getInstance().getTime());
            }

            if (CategoriaRecursoHumano.isRepartidorDeHacienda()) {
                _ventaBo.anularVenta(venta, VentaBo.ANULAR_VENTA_MODO_ENVIAR, getContext());
            } else {
                _ventaBo.anularVenta(venta, VentaBo.ANULAR_VENTA_MODO_NO_ENVIAR, getContext());
            }

            loadData();

            if (mModoVista == MODO_REPARTO) {
                ((ListadoPedidoSugeridoAdapter) mAdapter).reloadData(_ventas);
            } else {
                mAdapter.setVentas(this._ventas);
            }

        } catch (Exception e) {
            ErrorManager.manageException(TAG, "onDeletePedido", e, getActivity());
        }
    }

    private void initVar() {
        _ventaBo = new VentaBo(_repoFactory);

        //TextView lblTotalVta = getActivity().findViewById(R.id.lblTotalVta);//(TextView)
        TextView lblTotalVta = this._view.findViewById(R.id.lblTotalVta);//(TextView)
        this.lblTotalVtaValue = this._view.findViewById(R.id.lblTotalVtaValue);//(TextView)

        TextView lblTotalVtaGral = this._view.findViewById(R.id.lblTotalVtaGral);//(TextView)
        this.lblTotalVtaGralValue = this._view.findViewById(R.id.lblTotalVtaGralValue);//(TextView)

        this._ventas = new ArrayList<>();//ArrayList<Venta>()

        if (mModoVista == MODO_REPARTO) {
            mAdapter = new ListadoPedidoSugeridoAdapter(getActivity(), this._ventas, this._ventasDetalle);
            this.listadoVentas.setAdapter(mAdapter);
            lblTotalVta.setVisibility(View.GONE);
            lblTotalVtaValue.setVisibility(View.GONE);
            lblTotalVtaGral.setVisibility(View.GONE);
            lblTotalVtaGralValue.setVisibility(View.GONE);
        } else {
            mAdapter = new ListadoVentaAdapter(getActivity(), this._ventas, this.empresa);
            this.listadoVentas.setAdapter(mAdapter);
        }
        Calendar calendarDesde = Calendar.getInstance();
        calendarDesde.setTime(calendarDesde.getTime());
        this.fechaHasta = calendarDesde.getTime();
        calendarDesde.add(Calendar.DAY_OF_YEAR, -1);
        this.fechaDesde = calendarDesde.getTime();

        String sFechaDesde = Formatter.formatDate(this.fechaDesde);
        String sFechaHasta = Formatter.formatDate(this.fechaHasta);

        this.txtFechaDesde.setText(sFechaDesde);
        this.txtFechaHasta.setText(sFechaHasta);
    }

    public Object getLastNonConfigurationInstance() {
        return itemSeleccionado;
    }


}
