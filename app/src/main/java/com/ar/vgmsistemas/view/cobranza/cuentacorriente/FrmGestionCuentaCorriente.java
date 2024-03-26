package com.ar.vgmsistemas.view.cobranza.cuentacorriente;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SearchView.OnQueryTextListener;
import androidx.core.view.MenuItemCompat;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.ClienteBo;
import com.ar.vgmsistemas.bo.EmpresaBo;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ComparatorCliente;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.cliente.DlgMenuFiltros;
import com.ar.vgmsistemas.view.cobranza.FrmCuentaCorriente;
import com.ar.vgmsistemas.view.cobranza.cuentacorriente.ObtenerSaldoTask.ObtenerSaldoListener;
import com.ar.vgmsistemas.view.dialogs.ProgressDialogFragment;
import com.ar.vgmsistemas.view.dialogs.SingleChoiceDialogFragment;
import com.ar.vgmsistemas.view.menu.BaseNavigationFragment;

import java.util.Comparator;
import java.util.List;

public class FrmGestionCuentaCorriente extends BaseNavigationFragment {
    public static final int DIALOG_ORDENAR = 1;
    public static final int DIALOG_BUSCAR = 2;

    private ListView lvCuentasCorrientes;
    private TextView tvTotal;
    private CuentaCorrienteClientesAdapter mAdapter;
    private ClienteBo mClienteBo;
    private List<Cliente> mClientes;
    private SearchView mSearchView;
    private ObtenerSaldoTask obtenerSaldoTask;
    ProgressDialogFragment progressDialogFragment;

    private RepositoryFactory _repoFactory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lyt_gestion_cuenta_corriente, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        initVars();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        initViews();
    }

    private void initVars() {
        try {
            this._repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
            mClienteBo = new ClienteBo(_repoFactory);
            progressDialogFragment = ProgressDialogFragment.newInstance(getString(R.string.msjCargandoClientes));
            progressDialogFragment.show(getParentFragmentManager(), "");
            this.obtenerSaldoTask = new ObtenerSaldoTask(obtenerSaldoListener(), getContext());
            this.obtenerSaldoTask.execute((Void) null);
        } catch (IllegalStateException i) {
            i.printStackTrace();
        }

    }

    private void getDialogOpcionesOrdenamiento() {
        String codigo = getString(R.string.codigo);
        String razonSocial = getString(R.string.lblRazonSocial);
        String domicilio = getString(R.string.lblDomicilio);
        String saldo_asc = getString(R.string.msjOrdenadoCCAsc);
        String saldo_des = getString(R.string.msjOrdenadoCCDesc);


        final String[] items = {codigo, razonSocial, domicilio, saldo_asc, saldo_des};
        int ordenPreferido = PreferenciaBo.getInstance().getPreferencia(getActivity()).getOrdenPreferidoCCCliente();
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
            case Preferencia.CC_SALDO_ASC:
                itemSeleccionado = 3;
                break;
            case Preferencia.CC_SALDO_DESC:
                itemSeleccionado = 4;
                break;
        }

        SingleChoiceDialogFragment singleChoiceDialogFragment = SingleChoiceDialogFragment.newInstance(items, itemSeleccionado,
                "Seleccione una opcion", new SingleChoiceDialogFragment.SingleChoiceListener() {

                    @Override
                    public void onAcceptItem(int pos) {
                        onItemClick(pos);
                        ordenarClientes();
                    }

                });
        singleChoiceDialogFragment.show(getParentFragmentManager(), "nose");

    }

    private void onItemClick(int item) {
        int ordenPreferido;
        switch (item) {
            case 1:
                ordenPreferido = Preferencia.RAZON_SOCIAL;
                break;
            case 2:
                ordenPreferido = Preferencia.DOMICILIO;
                break;

            case 3:
                ordenPreferido = Preferencia.CC_SALDO_ASC;
                break;
            case 4:
                ordenPreferido = Preferencia.CC_SALDO_DESC;
                break;
            case 0:
            default:
                ordenPreferido = Preferencia.CODIGO;
                break;
        }

        PreferenciaBo.getInstance().getPreferencia(getActivity()).setOrdenPreferidoCCCliente(ordenPreferido);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.miOrdenarPor) {
            getDialogOpcionesOrdenamiento();
            return true;
        } else if (itemId == R.id.miBuscarPor) {
            getDialogOpcionesBusqueda();
            return true;
        } else if (itemId == R.id.mni_filtros) {
            getDialogFiltro();
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getDialogFiltro() {
        DlgMenuFiltros dlgFiltros = DlgMenuFiltros.newInstance(new DlgMenuFiltros.OkListener() {
            @Override
            public void onOkSelected() {
                progressDialogFragment = ProgressDialogFragment.newInstance(getString(R.string.msjCargandoClientes));
                progressDialogFragment.show(getParentFragmentManager(), "");
                obtenerSaldoTask = new ObtenerSaldoTask(obtenerSaldoListener(), getContext());
                obtenerSaldoTask.execute((Void) null);
            }
        }, false);

        dlgFiltros.show(getParentFragmentManager(), "");
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
                getString(R.string.tituloConfigurarBusquedaClientes), new SingleChoiceDialogFragment.SingleChoiceListener() {

                    @Override
                    public void onAcceptItem(int pos) {
                        dialogConfigurarBusquedaOnClick(pos);
                        mAdapter.notifyDataSetChanged();

                        // setHintSearchView(pos);
                    }
                });
        singleChoiceDialogFragment.show(getParentFragmentManager(), "nose");

    }

    private void dialogConfigurarBusquedaOnClick(int item) {
        int busquedaPreferida;
        switch (item) {
            case 1:
                busquedaPreferida = Preferencia.RAZON_SOCIAL;
                break;
            case 2:
                busquedaPreferida = Preferencia.DOMICILIO;
                break;
            case 0: // codigo

            default:
                busquedaPreferida = Preferencia.CODIGO;
                break;
        }

        setHintSearchView(busquedaPreferida);
        PreferenciaBo.getInstance().getPreferencia(getActivity()).setBusquedaPreferidaCliente(busquedaPreferida);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        int posBusqueda = PreferenciaBo.getInstance().getPreferencia(getActivity()).getBusquedaPreferidaCliente();
        setHintSearchView(posBusqueda);
        mSearchView.setOnQueryTextListener(new OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String arg0) {
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.clear();
        inflater.inflate(R.menu.mn_gestion_cuenta_corriente, menu);

        MenuItem item = menu.findItem(R.id.mni_search_cc);
        mSearchView = (SearchView) MenuItemCompat.getActionView(item);


        super.onCreateOptionsMenu(menu, inflater);
    }

    //private void initViews(View view){
    private void initViews() {
        lvCuentasCorrientes = (ListView) getActivity().findViewById(R.id.lvCuentasCorrientes);
        //lvCuentasCorrientes = (ListView) view.findViewById(R.id.lvCuentasCorrientes);
        lvCuentasCorrientes.setAdapter(mAdapter);
        lvCuentasCorrientes.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cliente cliente = mAdapter.getItem(position);
                EmpresaBo empresaBo = new EmpresaBo(_repoFactory);

                try {
                    if (empresaBo.gpsValido(getActivity().getApplicationContext())) {
                        Intent intentCuentaCorriente = new Intent(getActivity(), FrmCuentaCorriente.class);
                        intentCuentaCorriente.putExtra("cliente", cliente);
                        FrmGestionCuentaCorriente.this.startActivity(intentCuentaCorriente);
                    } else {
                        notificarGpsApagado();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        tvTotal = (TextView) getActivity().findViewById(R.id.tvTotal);

    }

    private void ordenarClientes() {
        Comparator<Cliente> comparator = new ComparatorCliente();
        mAdapter.sort(comparator);
        mAdapter.notifyDataSetChanged();
        mAdapter.getFilter().filter("");
    }

    private void notificarGpsApagado() {
        ErrorManager.manageException("DlgBldrOpcionesCliente", "dialogoOpcionesClienteOnClick",
                new Exception(), getActivity(), R.string.tituloErrorGps, R.string.msjErrorGPSApagado);
    }

    private ObtenerSaldoListener obtenerSaldoListener() {
        ObtenerSaldoListener listener = new ObtenerSaldoListener() {

            @Override
            public void onError(String error) {
                progressDialogFragment.dismiss();
                Toast toast = Toast.makeText(getActivity(), ErrorManager.ErrorAccederALosDatos, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }

            @Override
            public void onDone(List<Cliente> clientes) {
                progressDialogFragment.dismiss();
                mClientes = clientes;
                tvTotal.setText(Formatter.formatMoney(mClienteBo.getTotalSaldo(mClientes)));
                adapter();
                ordenarClientes();
            }
        };
        return listener;
    }

    private void adapter() {
        mAdapter = new CuentaCorrienteClientesAdapter(getActivity(), R.layout.lyt_gestion_cuenta_corriente, mClientes);
        lvCuentasCorrientes.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void setHintSearchView(int pos) {
        switch (pos) {

            case Preferencia.RAZON_SOCIAL:
                mSearchView.setQueryHint("por razon social");
                break;
            case Preferencia.DOMICILIO:
                mSearchView.setQueryHint("por domicilio");
                break;
            default:
                mSearchView.setQueryHint("por codigo");
                break;
        }
    }

}
