package com.ar.vgmsistemas.view.informes;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.NoPedidoBo;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.entity.NoPedido;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ComparatorNoPedido;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.ItemMenuNames;
import com.ar.vgmsistemas.view.dialogs.OptionsDialogFragment;
import com.ar.vgmsistemas.view.dialogs.SimpleDialogFragment;
import com.ar.vgmsistemas.view.dialogs.SingleChoiceDialogFragment;
import com.ar.vgmsistemas.view.dialogs.SingleChoiceDialogFragment.SingleChoiceListener;
import com.ar.vgmsistemas.view.menu.ListBaseNavigationFragment;
import com.ar.vgmsistemas.view.nopedido.FrmDetalleNoPedido;
import com.ar.vgmsistemas.view.nopedido.NoPedidoAdapter;

import java.util.Comparator;
import java.util.List;

public class FrmListadoNoPedido extends ListBaseNavigationFragment {
    private static final String TAG = FrmListadoNoPedido.class.getCanonicalName();
    public static final String KEY_NO_PEDIDO = "noPedido";

    private List<NoPedido> _noPedidos;
    private ArrayAdapter<NoPedido> _adapter;
    private NoPedidoBo _noPedidoBo;
    private NoPedido _itemSeleccionado;
    private OptionsDialogFragment optionsDialogFragment;

    private RepositoryFactory _repoFactory;

    @Override
    public void onPause() {
        super.onPause();
        getFragmentManager().saveFragmentInstanceState(this);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onActivityCreated(savedInstanceState);
        initComponents();
        initVar();
        loadData();
    }


    private void initComponents() {
        getListView().setTextFilterEnabled(true);
        getListView().setOnItemClickListener((arg0, arg1, position, arg3) -> {
            _itemSeleccionado = (NoPedido) getListAdapter().getItem(position);
            getDialogOpciones();

        });
    }

    private void initVar() {
        this._repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        _noPedidoBo = new NoPedidoBo(_repoFactory);
    }

    private void loadData() {
        try {
            _noPedidos = _noPedidoBo.recoveryAll();
            _adapter = new NoPedidoAdapter(getActivity(), R.layout.lyt_estadistica_no_pedido, _noPedidos);
            setListAdapter(_adapter);

        } catch (Exception e) {
            Toast toast = Toast.makeText(getActivity(), ErrorManager.ErrorAccederALosDatos, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private void getDialogCancelarPedido() {
        SimpleDialogFragment simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK_CANCEL, getString(R.string.msjCancelarPedido), "", new SimpleDialogFragment.OkCancelListener() {

            @Override
            public void onOkSelected() {
                cancelarPedido();

            }

            @Override
            public void onCancelSelected() {
                // TODO Auto-generated method stub

            }
        });
        simpleDialogFragment.show(getFragmentManager(), TAG);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.mn_lista_no_pedido, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mni_ordenar:
                getDialogOrdenamiento();
                break;
            case R.id.mni_filtros:
                getDialogFiltroEnviados();
                break;
        }
        return true;
    }

    private void getDialogOpciones() {
        //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String[] items;
        String cancelarPedido = getString(R.string.mnCancelarNoPedido);
        String verDetalles = getString(R.string.mnVerDetalles);
        items = new String[]{verDetalles, cancelarPedido};
        OptionsDialogFragment optionsDialogFragment = OptionsDialogFragment.newInstance(items, new OptionsDialogFragment.MultipleChoiceListener() {

            @Override
            public void onItemSelected(int pos) {
                dlgOpcionesOnClick(pos);
            }
        });
        optionsDialogFragment.show(getFragmentManager(), TAG);
    }

    private void dlgOpcionesOnClick(int position) {
        switch (position) {
            case 0: //Ver detalles
                mostrarNoPedido();
                break;
            case 1: //Cancelar pedido
                getDialogCancelarPedido();
                break;
        }
    }

    public void mostrarNoPedido() {

        Fragment fragment = FrmDetalleNoPedido.newInstance(_itemSeleccionado);
        getNavigationMenu().addFragment(fragment, ItemMenuNames.STRING_DETALLE_CLIENTE);
    }

    private void cancelarPedido() {
        NoPedidoBo noPedidoBo = new NoPedidoBo(_repoFactory);
        try {
            noPedidoBo.delete(this._itemSeleccionado);
            loadData();
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "cancelarPedido", e, getActivity());
        }
    }

    private void getDialogOrdenamiento() {

        String razonSocial = this.getString(R.string.lblRazonSocial);
        String fecha = this.getString(R.string.ordenarFecha);
        String motivo = this.getString(R.string.ordenarMotivo);
        String[] items = {razonSocial, fecha, motivo};

        int itemSeleccionado = -1;
        switch (PreferenciaBo.getInstance().getPreferencia(getActivity()).getOrdenPreferidoNoPedido()) {
            case Preferencia.RAZON_SOCIAL:
                itemSeleccionado = 0;
                break;
            case Preferencia.FECHA:
                itemSeleccionado = 1;
                break;
            case Preferencia.MOTIVO:
                itemSeleccionado = 2;
                break;
        }


        SingleChoiceDialogFragment dialogFragment = SingleChoiceDialogFragment.newInstance(items, itemSeleccionado,
                "Ordenar por: ", new SingleChoiceListener() {

                    @Override
                    public void onAcceptItem(int pos) {
                        bldrOrdenOnClickItem(pos);
                    }
                });
        dialogFragment.show(getChildFragmentManager(), "nose");
    }

    private void getDialogFiltroEnviados() {
        String enviados = this.getString(R.string.enviados);
        String noEnviados = this.getString(R.string.noEnviados);
        String todos = this.getString(R.string.todos);

        String[] items = {enviados, noEnviados, todos};

        int itemSeleccionado = -1;
        switch (PreferenciaBo.getInstance().getPreferencia(getActivity()).getFiltroNoAtencionEnviadas()) {
            case Preferencia.FILTRO_ENVIADOS:
                itemSeleccionado = 0;
                break;
            case Preferencia.FILTRO_NO_ENVIADAS:
                itemSeleccionado = 1;
                break;
            case Preferencia.FILTRO_TODOS:
                itemSeleccionado = 2;
                break;
        }

        SingleChoiceDialogFragment dialogFragment = SingleChoiceDialogFragment.newInstance(items, itemSeleccionado,
                "Filtrar:", new SingleChoiceListener() {

                    @Override
                    public void onAcceptItem(int pos) {
                        dlgFiltroEnviadosOnClick(pos);
                    }
                });
        dialogFragment.show(getChildFragmentManager(), "nose");
    }

    private void dlgFiltroEnviadosOnClick(int position) {
        switch (position) {
            case 0: //Enviados
                PreferenciaBo.getInstance().getPreferencia(getActivity()).setFiltroNoAtencionEnviadas(Preferencia.FILTRO_ENVIADOS);
                break;
            case 1: //No enviados
                PreferenciaBo.getInstance().getPreferencia(getActivity()).setFiltroNoAtencionEnviadas(Preferencia.FILTRO_NO_ENVIADAS);
                break;
            case 2: //Todos
                PreferenciaBo.getInstance().getPreferencia(getActivity()).setFiltroNoAtencionEnviadas(Preferencia.FILTRO_TODOS);
                break;
        }
        filtrar();
        //dialog.dismiss();
    }

    private void filtrar() {
        this._adapter.getFilter().filter("");
    }

    private void bldrOrdenOnClickItem(int which) {
        int orden = 0;
        switch (which) {
            case 0:
                orden = Preferencia.RAZON_SOCIAL;
                break;
            case 1:
                orden = Preferencia.FECHA;
                break;
            case 2:
                orden = Preferencia.MOTIVO;
                break;
        }
        PreferenciaBo.getInstance().getPreferencia(getActivity()).setOrdenPreferidoNoPedido(orden);
        ordenar();
    }

    private void ordenar() {
        Comparator<NoPedido> comparator = new ComparatorNoPedido();
        _adapter.sort(comparator);
        _adapter.notifyDataSetChanged();
    }
}
