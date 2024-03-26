package com.ar.vgmsistemas.view.cliente;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SearchView.OnQueryTextListener;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.ClienteBo;
import com.ar.vgmsistemas.bo.ComercioLoginBo;
import com.ar.vgmsistemas.bo.EmpresaBo;
import com.ar.vgmsistemas.bo.MenuProfileBo;
import com.ar.vgmsistemas.bo.ObjetivoVentaBo;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.bo.VendedorBo;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.ComercioLogin;
import com.ar.vgmsistemas.entity.Documento;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.entity.SecuenciaRuteo;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.entity.key.PkCliente;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ArmarMapping;
import com.ar.vgmsistemas.utils.ComparatorSecuenciaRuteo;
import com.ar.vgmsistemas.utils.Encoder;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.ItemMenuNames;
import com.ar.vgmsistemas.view.AlertDialog;
import com.ar.vgmsistemas.view.FrmCoordenadasCliente;
import com.ar.vgmsistemas.view.MapPane;
import com.ar.vgmsistemas.view.cobranza.FrmCuentaCorriente;
import com.ar.vgmsistemas.view.dialogs.OptionsDialogFragment;
import com.ar.vgmsistemas.view.dialogs.SimpleDialogFragment;
import com.ar.vgmsistemas.view.dialogs.SingleChoiceDialogFragment;
import com.ar.vgmsistemas.view.informes.FrmListadoVenta;
import com.ar.vgmsistemas.view.informes.FrmVentasXArticulo;
import com.ar.vgmsistemas.view.menu.BaseNavigationFragment;
import com.ar.vgmsistemas.view.nopedido.FrmGestionNoPedido;
import com.ar.vgmsistemas.view.objetivoVenta.FrmGestionObjetivoVenta;
import com.ar.vgmsistemas.view.venta.FrmVenta;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FrmGestionCliente extends BaseNavigationFragment {

    private ArrayAdapter<SecuenciaRuteo> mAdapter;
    private ListView lstClientes;
    private Cliente _clienteSeleccionado;

    private static final String TAG = FrmGestionCliente.class.getCanonicalName();
    private static final String SELECCIONAR_OPCION = "Seleccionar opcion";
    private Map<Integer, Integer> diasVisita;
    private static final int MENU_REGISTRAR_PEDIDO = 0;
    private static final int MENU_CUENTA_CORRIENTE = 1;
    private static final int MENU_REGISTRAR_NO_PEDIDO = 2;
    private static final int MENU_VER_PEDIDOS = 3;
    private static final int MENU_VER_VENTAS = 4;
    private static final int MENU_VENTAS_X_ARTICULO = 5;
    private static final int MENU_VER_DETALLES = 6;
    private static final int MENU_LLAMAR = 7;
    private static final int MENU_MOSTRAR_MAPA = 8;
    //private static final int MENU_OBTENER_COORDENADAS = 9;
    private List<Integer> listValidos;
    private SearchView mSearchView;
    private OptionsDialogFragment optionsDialogFragment;

    //BD
    private RepositoryFactory _repoFactory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.lyt_cliente_gestion, container, false);
        initComponents(view);
        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        int posBusqueda = PreferenciaBo.getInstance().getPreferencia(getActivity()).getBusquedaPreferidaCliente();
        setHintSearchView(posBusqueda);
        mSearchView.setOnQueryTextListener(new OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String arg0) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean onQueryTextChange(String arg0) {
                mAdapter.getFilter().filter(arg0);
                return true;
            }
        });

        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Object data = getLastNonConfigurationInstance();
        if (data != null) {
            _clienteSeleccionado = (Cliente) data;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        lstClientes.destroyDrawingCache();
        loadClientes();
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    private void initComponents(View myView) {
        // lblCampoBusqueda
        _repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);

        this.actualizarLeyendaCampoOrdenamiento();

        // lstClientes(ListView)
        this.lstClientes = myView.findViewById(R.id.lstClientes);
        this.lstClientes.setTextFilterEnabled(true);
        this.lstClientes.setOnItemClickListener((parent, view, position, id) -> {
            // Valido cliente
            PkCliente idCliente = mAdapter.getItem(position).getCliente().getId();

            ClienteBo clienteBo = new ClienteBo(_repoFactory);
            Cliente cliente = null;
            try {
                cliente = clienteBo.recoveryById(idCliente);
            } catch (Exception e) {
                ErrorManager.manageException("FrmGestionCliente", "InitComponents", e);
            }
            if (cliente == null) {
                Toast.makeText(getActivity(), ErrorManager.ErrorClienteConAtributoFaltante, Toast.LENGTH_LONG).show();
            } else {
                lstClientesOnItemClick(parent, view, position, id);
            }
        });
    }


    public Object getLastNonConfigurationInstance() {
        return this._clienteSeleccionado;
    }

    private void loadClientes() {
        VendedorBo vendedorBo = new VendedorBo(_repoFactory);
        int dia = PreferenciaBo.getInstance().getPreferencia(getActivity()).getDiaVisita();
        try {
            List<SecuenciaRuteo> clientes = vendedorBo.recoveryRuteoPorDia(dia);
            this.mAdapter = new SecuenciaRuteoAdapter(getActivity(), clientes);
            this.lstClientes.setAdapter(mAdapter);
            this.ordenarClientes();

        } catch (Exception e) {
            Toast toast = Toast.makeText(getActivity(), ErrorManager.ErrorAccederALosDatos, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            // ErrorManager.manageException(TAG, "loadClientes", e, this);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.clear();
        inflater.inflate(R.menu.mn_gestion_cliente, menu);

        MenuItem item = menu.findItem(R.id.mni_search_cliente);
        mSearchView = (SearchView) MenuItemCompat.getActionView(item);


        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mni_ordenar:
                getDialogOpcionesOrdenamiento();
                return false;
            case R.id.mni_configurar_busqueda:
                getDialogOpcionesBusqueda();
                return false;
            case R.id.mni_diaVisita:
                getDialogDiaVisita();
                return false;
            case R.id.mni_filtros:
                getDialogFiltro();
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getDialogFiltro() {
        DlgMenuFiltros dlgFiltros = DlgMenuFiltros.newInstance(new DlgMenuFiltros.OkListener() {
            @Override
            public void onOkSelected() {
                loadClientes();
            }
        });

        dlgFiltros.show(getParentFragmentManager(), TAG);
    }

    private void getDialogDiaVisita() {
        String lunes = getString(R.string.lunes);
        String martes = getString(R.string.martes);
        String miercoles = getString(R.string.miercoles);
        String jueves = getString(R.string.jueves);
        String viernes = getString(R.string.viernes);
        String sabado = getString(R.string.sabado);
        String domingo = getString(R.string.domingo);
        String todos = getString(R.string.todos);

        // Mapeo entre posicion en la lista y el dia de visita <position,
        // diaVisita>
        diasVisita = new HashMap<>();
        diasVisita.put(0, Preferencia.DIA_VISITA_DOMINGO);
        diasVisita.put(1, Preferencia.DIA_VISITA_LUNES);
        diasVisita.put(2, Preferencia.DIA_VISITA_MARTES);
        diasVisita.put(3, Preferencia.DIA_VISITA_MIERCOLES);
        diasVisita.put(4, Preferencia.DIA_VISITA_JUEVES);
        diasVisita.put(5, Preferencia.DIA_VISITA_VIERNES);
        diasVisita.put(6, Preferencia.DIA_VISITA_SABADO);
        diasVisita.put(7, Preferencia.DIA_VISITA_TODOS);

        String[] items = {domingo, lunes, martes, miercoles, jueves, viernes, sabado, todos};

        int diaVisitaGuardado = PreferenciaBo.getInstance().getPreferencia(getActivity()).getDiaVisita();
        int positionDiaSeleccionado = getItemPosition(diaVisitaGuardado);

        SingleChoiceDialogFragment singleChoiceDialogFragment = SingleChoiceDialogFragment.newInstance(items,
                positionDiaSeleccionado, SELECCIONAR_OPCION, new SingleChoiceDialogFragment.SingleChoiceListener() {

                    @Override
                    public void onAcceptItem(int pos) {
                        dialogDiaVisitaOnClick(pos);
                        loadClientes();
                    }
                });
        singleChoiceDialogFragment.show(getParentFragmentManager(), TAG);
        /*
         * DlgBldrDiaVisita bldrDiaVisita = new DlgBldrDiaVisita(this);
         * AlertDialog dlgDiaVisita = bldrDiaVisita.create();
         * dlgDiaVisita.setOnDismissListener(new OnDismissListener() { public
         * void onDismiss(DialogInterface dialog) { loadClientes(); } }); return
         * dlgDiaVisita;
         */
    }

    private void dialogDiaVisitaOnClick(int item) {
        int diaVisita = diasVisita.get(item);
        PreferenciaBo.getInstance().getPreferencia(getActivity()).setDiaVisita(diaVisita);
    }

    private int getItemPosition(int diaVisita) {
        Set<Integer> posiciones = diasVisita.keySet();
        for (Integer posicion : posiciones) {
            Integer dia = diasVisita.get(posicion);
            if (dia == diaVisita)
                return posicion;
        }
        return -1;
    }

    private void getDialogOpcionesBusqueda() {
        String codigo = getString(R.string.codigo);
        String razonSocial = getString(R.string.razonSocial);
        String domicilio = getString(R.string.domicilio);

        final String[] items = {codigo, razonSocial, domicilio};
        int busquedaPreferidaCliente = PreferenciaBo.getInstance().getPreferencia(getActivity()).getBusquedaPreferidaCliente();
        int itemSeleccionado = -1;

        switch (busquedaPreferidaCliente) {
            case Preferencia.CODIGO:
                itemSeleccionado = 0;
                break;
            case Preferencia.RAZON_SOCIAL:
                itemSeleccionado = 1;
                break;
            case Preferencia.DOMICILIO:
                itemSeleccionado = 2;
                break;
        }

        SingleChoiceDialogFragment singleChoiceDialogFragment = SingleChoiceDialogFragment.newInstance(items, itemSeleccionado,
                getString(R.string.tituloConfigurarBusquedaClientes), pos -> {
                    dialogConfigurarBusquedaOnClick(pos);
                    mAdapter.notifyDataSetChanged();

                    // setHintSearchView(pos);
                });
        singleChoiceDialogFragment.show(getParentFragmentManager(), TAG);
    }

    private void getDialogOpcionesOrdenamiento() {
        String codigo = getString(R.string.codigo);
        String razonSocial = getString(R.string.lblRazonSocial);
        String domicilio = getString(R.string.lblDomicilio);
        String ordenVisita = getString(R.string.ordenVisita);

        final String[] items = {codigo, razonSocial, domicilio, ordenVisita};
        int ordenPreferido = PreferenciaBo.getInstance().getPreferencia(getActivity()).getOrdenPreferidoCliente();
        int itemSeleccionado = -1;
        switch (ordenPreferido) {
            case Preferencia.CODIGO:
                itemSeleccionado = 0;
                break;
            case Preferencia.RAZON_SOCIAL:
                itemSeleccionado = 1;
                break;
            case Preferencia.DOMICILIO:
                itemSeleccionado = 2;
                break;
            case Preferencia.ORDEN_VISITA:
                itemSeleccionado = 3;
                break;
        }

        SingleChoiceDialogFragment singleChoiceDialogFragment = SingleChoiceDialogFragment.newInstance(items, itemSeleccionado,
                SELECCIONAR_OPCION, new SingleChoiceDialogFragment.SingleChoiceListener() {

                    @Override
                    public void onAcceptItem(int pos) {
                        onItemClick(pos);
                        ordenarClientes();
                    }

                });
        singleChoiceDialogFragment.show(getParentFragmentManager(), TAG);
        /*
         * esto es de DlgBldrBusquedaCliente
         * singleChoiceDialogFragment.show(getFragmentManager(), TAG);
         *
         * this.setSingleChoiceItems(items, itemSeleccionado, new
         * OnClickListener() { public void onClick(DialogInterface dialog, int
         * which) { dialogConfigurarBusquedaOnClick(dialog, which); } });
         */
        /*
         * DlgBldrOrdenamientoCliente bldrOrdenamientoCliente = new
         * DlgBldrOrdenamientoCliente(this); AlertDialog dlgOrdenamientoCliente
         * = bldrOrdenamientoCliente.create();
         * dlgOrdenamientoCliente.setOnDismissListener(new OnDismissListener() {
         * public void onDismiss(DialogInterface dialog) { ordenarClientes(); }
         * }); return dlgOrdenamientoCliente;
         */
    }

    private void onItemClick(int item) {
        int ordenPreferido = 0;
        switch (item) {
            case 0:
                ordenPreferido = Preferencia.CODIGO;
                break;
            case 1:
                ordenPreferido = Preferencia.RAZON_SOCIAL;
                break;
            case 2:
                ordenPreferido = Preferencia.DOMICILIO;
                break;
            case 3:
                ordenPreferido = Preferencia.ORDEN_VISITA;
        }

        PreferenciaBo.getInstance().getPreferencia(getActivity()).setOrdenPreferidoCliente(ordenPreferido);
        // dialog.dismiss();
    }

    private void dialogConfigurarBusquedaOnClick(int item) {
        int busquedaPreferida = 0;
        switch (item) {
            case 0: // codigo
                busquedaPreferida = Preferencia.CODIGO;
                break;
            case 1:
                busquedaPreferida = Preferencia.RAZON_SOCIAL;
                break;
            case 2:
                busquedaPreferida = Preferencia.DOMICILIO;
                break;
        }

        setHintSearchView(busquedaPreferida);
        PreferenciaBo.getInstance().getPreferencia(getActivity()).setBusquedaPreferidaCliente(busquedaPreferida);

    }

    private void ordenarClientes() {
        Comparator<SecuenciaRuteo> comparator = new ComparatorSecuenciaRuteo();
        mAdapter.sort(comparator);
        mAdapter.notifyDataSetChanged();
        mAdapter.getFilter().filter("");
        actualizarLeyendaCampoOrdenamiento();
    }

    private void actualizarLeyendaCampoOrdenamiento() {
        int ordenPreferido = 0;
        try {
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    /**
     * Muestra las opciones al seleccionar un cliente
     */

    private void getOpcionesCliente() {
        MenuProfileBo menuProfileBo = new MenuProfileBo();
        listValidos = menuProfileBo.getPosicionesValidasDialogoCliente(getActivity());
        String registrarPedido = getString(R.string.mnRegistrarPedido);
        String registrarNoPedido = getString(R.string.mnRegistrarNoPedido);
        String verDetalles = getString(R.string.mnVerDetalles);
        String verPedidos = getString(R.string.mnVerPedidos);
        String verVentas = getString(R.string.mnVerVentas);
        String ventasXArticulo = getString(R.string.mnVentasXArticulo);
        String llamar = getString(R.string.mnLlamar);
        String mostrarMapa = getString(R.string.mnMostrarMapa);
        String verCuentaCorriente = getString(R.string.mnCuentaCorriente);
        //String obtenerCoordenadas = getString(R.string.mnObtenerCoordenadas);


        int[] claves = {MENU_REGISTRAR_PEDIDO, MENU_CUENTA_CORRIENTE, MENU_REGISTRAR_NO_PEDIDO, MENU_VER_PEDIDOS,
                MENU_VER_VENTAS, MENU_VENTAS_X_ARTICULO, MENU_VER_DETALLES, MENU_LLAMAR, MENU_MOSTRAR_MAPA/*, MENU_OBTENER_COORDENADAS */};

        String[] valores = {registrarPedido, verCuentaCorriente, registrarNoPedido, verPedidos, verVentas, ventasXArticulo, verDetalles, llamar,
                mostrarMapa/*, obtenerCoordenadas */};

        ArmarMapping armarMapa = new ArmarMapping(claves, valores);

        final String[] items = armarMapa.armarStringSecuence(listValidos);

        optionsDialogFragment = OptionsDialogFragment.newInstance(items,
                new OptionsDialogFragment.MultipleChoiceListener() {

                    @Override
                    public void onItemSelected(int pos) throws Exception {
                        dialogoOpcionesClienteOnClick(pos);
                    }

                });
        optionsDialogFragment.show(getParentFragmentManager(), TAG);
        /*
         * DlgBldrOpcionesCliente dlgOpcionesCliente = new
         * DlgBldrOpcionesCliente(this); return dlgOpcionesCliente.create();
         */
    }


    public boolean validarComercioLogin(String codigo) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Cliente cliente = ClienteBo.getClienteSeleccionado();
        ComercioLoginBo comercioLoginBo = new ComercioLoginBo(_repoFactory);
        ComercioLogin comercioLogin = null;
        try {
            comercioLogin = comercioLoginBo.recoveryByCliente(cliente.getId());
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "No se encuentra el cliente", e);
            e.printStackTrace();
        }
        if (comercioLogin != null) {
            String pass = Encoder.SHA1(comercioLogin.getUsuario() + codigo);
            if (comercioLogin.getPassword().equals(pass)) {
                return true;
            }

        }
        return false;
    }

    private boolean validarVendedor() {
        boolean isValid = VendedorBo.isValidVendedor();
        if (!isValid) {
            SimpleDialogFragment simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK, ErrorManager.ErrorVendedorNoValido, "Vendedor desactivado", new SimpleDialogFragment.OkListener() {

                @Override
                public void onOkSelected() {
                    optionsDialogFragment.dismiss();
                }
            });
            simpleDialogFragment.setCancelable(false);
            simpleDialogFragment.show(getParentFragmentManager(), TAG);
        }
        return isValid;
    }

    private void dialogoOpcionesClienteOnClick(int item) throws Exception {
        EmpresaBo empresaBo = new EmpresaBo(_repoFactory);
        ClienteBo clienteBo = new ClienteBo(_repoFactory);
        Cliente cliente = ClienteBo.getClienteSeleccionado();
        mSearchView.setIconified(true);
        mSearchView.setIconified(true);
        mAdapter.getFilter().filter("");
        PkCliente id = cliente.getId();
        try {
            cliente = clienteBo.recoveryById(id);
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "dialogOpcionesClienteOnClick", e, getActivity());
        }
        try {
            switch (listValidos.get(item)) {
                case MENU_REGISTRAR_PEDIDO:
                    if (empresaBo.gpsValido(getActivity().getApplicationContext())) {
                        if (validarVendedor()) {
                            registrarPedido(cliente);
                        }
                    } else {
                        notificarGpsApagado();
                    }
                    break;
                case MENU_REGISTRAR_NO_PEDIDO:
                    if (empresaBo.gpsValido(getActivity().getApplicationContext())) {
                        Intent intentNoPedido = new Intent(getActivity(), FrmGestionNoPedido.class);
                        intentNoPedido.putExtra("cliente", cliente);
                        getActivity().startActivity(intentNoPedido);
                    } else {
                        notificarGpsApagado();
                    }
                    break;
                case MENU_VER_DETALLES:
                    Fragment fragmentDetalles = new FrmDetalleCliente();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(FrmDetalleCliente.KEY_CLIENTE, cliente);
                    fragmentDetalles.setArguments(bundle);
                    getNavigationMenu().addFragment(fragmentDetalles, ItemMenuNames.STRING_DETALLE_CLIENTE);

                    break;
                case MENU_VER_PEDIDOS:
                    Fragment fragment = FrmListadoVenta.newInstance(Documento.TIPO_DOCUMENTO_PEDIDO, cliente);
                    getNavigationMenu().addFragment(fragment, ItemMenuNames.STRING_VENTAS_CLIENTE);

                    break;
                case MENU_VER_VENTAS:
                    Fragment fragment2 = FrmListadoVenta.newInstance(Documento.TIPO_DOCUMENTO_FACTURA, cliente);
                    getNavigationMenu().addFragment(fragment2, ItemMenuNames.STRING_VENTAS_CLIENTE);

                    break;
                case MENU_VENTAS_X_ARTICULO:
                    Intent intent = new Intent(getActivity(), FrmVentasXArticulo.class);
                    intent.putExtra(FrmVentasXArticulo.KEY_CLIENTES, cliente);
                    getActivity().startActivity(intent);
                    break;
                case MENU_CUENTA_CORRIENTE:
                    if (empresaBo.gpsValido(getActivity().getApplicationContext())) {
                        if (validarVendedor()) {
                            Intent intentCuentaCorriente = new Intent(getActivity(), FrmCuentaCorriente.class);
                            intentCuentaCorriente.putExtra("cliente", cliente);
                            getActivity().startActivity(intentCuentaCorriente);
                        }
                    } else {
                        notificarGpsApagado();
                    }
                    break;
                case MENU_LLAMAR:
                    String telefono = cliente.getTelefono();
                    llamar(telefono);
                    break;
                case MENU_MOSTRAR_MAPA:
                    Fragment fragmentDetalleArticulo = MapPane.newInstance(cliente);
                    getNavigationMenu().addFragment(fragmentDetalleArticulo, ItemMenuNames.STRING_VER_CLIENTE_EN_MAPA);
                    break;
            }
            optionsDialogFragment.dismiss();

        } catch (Exception e) {
            ErrorManager.manageException(TAG, "loadClientes", e, getActivity());
        }
    }

    private void abrirActivity(Cliente cliente) {
        Intent intentCoordenadas = new Intent(getActivity(), FrmCoordenadasCliente.class);
        intentCoordenadas.putExtra("cliente", cliente);
        getActivity().startActivity(intentCoordenadas);
    }

    private void registrarPedido(Cliente cliente) throws Exception {
        ObjetivoVentaBo objetivoVentaBo = new ObjetivoVentaBo(_repoFactory);
        int cantObjetivosVenta;
        try {
            Venta venta = new Venta();
            venta.setCliente(cliente);
            cantObjetivosVenta = objetivoVentaBo.getCantidadNoCumplidosCliente(cliente);
            if (cantObjetivosVenta > 0) {
                Intent intentObjetivos = new Intent(getActivity(), FrmGestionObjetivoVenta.class);
                intentObjetivos.putExtra(FrmVenta.EXTRA_VENTA, venta);
                intentObjetivos.putExtra("origen", FrmGestionObjetivoVenta.ORIGEN_OPCIONES_CLIENTE);
                intentObjetivos.putExtra(FrmVenta.EXTRA_CLIENT, cliente);
                getActivity().startActivity(intentObjetivos);
            } else {
                Intent intentVenta = new Intent(getActivity(), FrmVenta.class);
                int alta = 0;
                intentVenta.putExtra(FrmVenta.EXTRA_VENTA, venta);
                intentVenta.putExtra(FrmVenta.EXTRA_MODO, alta);
                intentVenta.putExtra(FrmVenta.EXTRA_CLIENT, cliente);
                getActivity().startActivity(intentVenta);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), ErrorManager.ClienteSinAtributoBDD, Toast.LENGTH_SHORT).show();

        }
    }

    private void llamar(String telefono) {
        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + telefono));
            getActivity().startActivity(callIntent);
        } catch (ActivityNotFoundException e) {
            ErrorManager.manageException(TAG, "llamar", e, getActivity());
        }
    }

    private void notificarGpsApagado() {
        ErrorManager.manageException("DlgBldrOpcionesCliente", "dialogoOpcionesClienteOnClick", new Exception(), getActivity(),
                R.string.tituloErrorGps, R.string.msjErrorGPSApagado);
    }

    private void obtenerCoordenadas(Cliente cliente) {
        final Cliente clienteMostrar = cliente;
        String latitud = String.valueOf(cliente.getLatitud());
        String longitud = String.valueOf(cliente.getLongitud());
        String cero = "0.0";
        if ((!latitud.equals(cero)) && (!longitud.equals(cero))) {
            SimpleDialogFragment simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK_CANCEL, getString(R.string.ActualizarUbicacion),
                    "Informacion", new SimpleDialogFragment.OkCancelListener() {

                        @Override
                        public void onOkSelected() {
                            abrirActivity(clienteMostrar);
                        }

                        @Override
                        public void onCancelSelected() {
                        }
                    });
            simpleDialogFragment.show(getParentFragmentManager(), TAG);

        } else {
            abrirActivity(clienteMostrar);
        }
    }

    private void lstClientesOnItemClick(AdapterView<?> a, View view, int position, long id) {
        this._clienteSeleccionado = mAdapter.getItem(position).getCliente();
        ClienteBo.setClienteSeleccionado(this._clienteSeleccionado);
        Cliente cliente = ClienteBo.getClienteSeleccionado();
        if (!cliente.getIsHabilitado()) {
            notificarError(getString(R.string.msjClienteNoHabilitado));
            return;
        }
        ComercioLoginBo comercioLoginBo = new ComercioLoginBo(_repoFactory);
        ComercioLogin comercioLogin = null;
        try {
            comercioLogin = comercioLoginBo.recoveryByCliente(cliente.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (comercioLogin.getId() != null) {
            ComercioLoginDialogFragment comercioLoginDialogFragment = ComercioLoginDialogFragment.newInstance(new ComercioLoginDialogFragment.ComercioLoginListener() {
                @Override
                public void onAccept(String comercioLog) throws NoSuchAlgorithmException, UnsupportedEncodingException {
                    if (validarComercioLogin(comercioLog)) {
                        getOpcionesCliente();
                    } else {
                        notificarError(getString(R.string.msjComercioLoginDiferente));
                    }

                }
            });
            comercioLoginDialogFragment.show(getParentFragmentManager(), "dialog");
        } else {
            getOpcionesCliente();
        }

    }

    private void notificarError(String errorDelManager) {
        AlertDialog alert = new AlertDialog(this.getContext(), getString(R.string.tituloError), errorDelManager);
        alert.show();
    }


    private void setHintSearchView(int pos) {
        switch (pos) {
            case Preferencia.CODIGO:
                mSearchView.setQueryHint("por código");
                break;
            case Preferencia.RAZON_SOCIAL:
                mSearchView.setQueryHint("por razon social");
                break;
            case Preferencia.DOMICILIO:
                mSearchView.setQueryHint("por domicilio");
                break;
        }
    }

}
