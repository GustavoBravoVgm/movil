package com.ar.vgmsistemas.view.informes;

import android.os.Bundle;
import android.util.Log;
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

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.EmpresaBo;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.bo.ReciboBo;
import com.ar.vgmsistemas.entity.Empresa;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.entity.Recibo;
import com.ar.vgmsistemas.printer.ReciboPrinter;
import com.ar.vgmsistemas.printer.ResumenCobranzaPrinter;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.CodeResult;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.dialogs.OptionsDialogFragment;
import com.ar.vgmsistemas.view.dialogs.SimpleDialogFragment;
import com.ar.vgmsistemas.view.menu.BaseNavigationFragment;

import java.util.ArrayList;
import java.util.List;

public class FrmListadoRecibo extends BaseNavigationFragment {

    private static final String TAG = FrmListadoRecibo.class.getCanonicalName();
    //private static final int DIALOG_IMPRIMIR_RECIBO = 0;
    //private static final int DIALOG_OPCIONES_FILTRO = 3;
    public static final int POSITION_FILTRO_TODOS = 0;
    public static final int POSITION_FILTRO_ENVIADOS = 1;
    public static final int POSITION_FILTRO_NO_ENVIADOS = 2;
    public static final int POSITION_IMPRIMIR_PEDIDOS = 0;


    public static final int POSITION_IMPRIMIR_RECIBO = 0;
    public static final int POSITION_INFORMACION_RECIBO = 1;

    private ReciboBo reciboBo;
    private EmpresaBo empresaBo;
    private ListadoReciboAdapter adapter;
    private Recibo itemSeleccionado;
    private List<Recibo> recibos;
    private ListView listadoRecibos;

    private TextView txtTotalReciboValue;
    private TextView txtCantidadReciboValue;

    private RepositoryFactory _repoFactory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.lyt_listado_recibo, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        initComponents();
        initVar();
        actualizarRecibos();
    }

    private void initComponents() {
        this.listadoRecibos = getActivity().findViewById(R.id.listadoRecibos);//(ListView)
        this.listadoRecibos.setOnItemClickListener((parent, view, position, id) -> {
            lstOnItemClick(parent, position);//lstOnItemClick(parent, view, position, id);
        });
        this.txtTotalReciboValue = getActivity().findViewById(R.id.lblTotalReciboValue);//(TextView)
        this.txtCantidadReciboValue = getActivity().findViewById(R.id.lblCantidadReciboValue);//(TextView)
    }

    private void actualizarRecibos() {
        loadData();
        adapter.setRecibos(recibos);
        adapter.notifyDataSetChanged();
        actualizarTotales(POSITION_FILTRO_TODOS);
    }

    private void initVar() {
        this._repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        reciboBo = new ReciboBo(_repoFactory);
        this.recibos = new ArrayList<>();//ArrayList<Recibo>()
        this.adapter = new ListadoReciboAdapter(getActivity(), this.recibos);
        this.listadoRecibos.setAdapter(this.adapter);
        empresaBo = new EmpresaBo(_repoFactory);
    }

    private void loadData() {
        try {
            recibos = reciboBo.recoveryAll();
        } catch (Exception e) {
            Toast toast = Toast.makeText(getActivity(), ErrorManager.ErrorAccederALosDatos, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private void lstOnItemClick(AdapterView<?> adapter, /*View view,*/ int position/*, long id*/) {
        itemSeleccionado = (Recibo) adapter.getItemAtPosition(position);
        determinarTipoImpresionRecibo();
        getDialogOpciones();
    }

    private void getDialogOpciones() {
        String[] items;
        String imprimirRecibo = getString(R.string.mnImprimirRecibo);
        String verInformacionEnvio = getString(R.string.mnInformacionEnvio);
        int resultado = itemSeleccionado.getResultadoEnvio();
        int tipoImpresion = itemSeleccionado.getTipoImpresionRecibo();
        if (resultado == CodeResult.RESULT_ALGUN_COMPROBANTE_TIENE_SALDO_CERO || resultado == CodeResult.RESULT_MONTO_DISPONIBLE_CERO_RECIBO_BO || resultado == CodeResult.RESULT_NO_SE_ENCONTRO_UN_COMPROBANTE_EN_CTACTE_RECIBO_BO) {
            if (tipoImpresion != 0) {
                items = new String[]{imprimirRecibo, verInformacionEnvio};
            } else {
                items = new String[]{verInformacionEnvio};
            }
        } else {
            if (tipoImpresion != 0) {
                items = new String[]{imprimirRecibo};
            } else {
                return;
            }
        }
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
            case POSITION_IMPRIMIR_RECIBO: //Ver detalles
                imprimirRecibo();
                break;
            case POSITION_INFORMACION_RECIBO: //Cancelar pedido
                getDialogoInformacionRecibo();
                break;
        }
    }

    private void getDialogoInformacionRecibo() {
        String mensaje = "";
        switch (itemSeleccionado.getResultadoEnvio()) {
            case CodeResult.RESULT_ALGUN_COMPROBANTE_TIENE_SALDO_CERO:
                mensaje = getString(R.string.msjAlgunComprobanteSaldoCero);
                break;
            case CodeResult.RESULT_MONTO_DISPONIBLE_CERO_RECIBO_BO:
                mensaje = getString(R.string.msjResultMontoDispCero);
                break;
            case CodeResult.RESULT_NO_SE_ENCONTRO_UN_COMPROBANTE_EN_CTACTE_RECIBO_BO:
                mensaje = getString(R.string.msjResultNoSeEncontroUnComprobante);
                break;
        }
        SimpleDialogFragment simpleDialog = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK, mensaje, getString(R.string.mnInformacionEnvio), new SimpleDialogFragment.OkListener() {
            @Override
            public void onOkSelected() {
            }
        });
        simpleDialog.show(getFragmentManager(), TAG);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.mn_lista_recibos, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mni_filtros:
                getDialogOpcionesFiltro();
                break;
            case R.id.mni_ResumenRecibos:
                getDialogOpcionesResumenRecibos();
                break;
        }
        return true;
    }

    private void getDialogOpcionesFiltro() {
        String todos = getString(R.string.mnTodos);
        String enviados = getString(R.string.mnSoloEnviados);
        String noEnviados = getString(R.string.mnNoEnviados);
        String[] items = {todos, enviados, noEnviados};
        OptionsDialogFragment optionsDialogFragment = OptionsDialogFragment.newInstance(items, new OptionsDialogFragment.MultipleChoiceListener() {

            @Override
            public void onItemSelected(int pos) {
                dlgOpcionesFiltroOnClick(pos);
            }
        });
        optionsDialogFragment.show(getFragmentManager(), TAG);

    }

    private void getDialogOpcionesResumenRecibos() {
        Empresa empresa = new Empresa();
        try {
            empresa = empresaBo.recoveryEmpresa();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String impResumenCobranza;
        if (empresa.getSnImpResumenCobranza() == null) {
            impResumenCobranza = "N";
        } else {
            impResumenCobranza = empresa.getSnImpResumenCobranza();
        }
        if (impResumenCobranza.equals("N")) {
            String mensaje = "Opcion deshabilitada";
            SimpleDialogFragment simpleDialog = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK, mensaje, getString(R.string.error), new SimpleDialogFragment.OkListener() {
                @Override
                public void onOkSelected() {
                }
            });
            simpleDialog.show(getFragmentManager(), TAG);
        } else {
            String imprimirResumen = getString(R.string.mnImprimirResumenRecibos);
            String[] items = {imprimirResumen};
            OptionsDialogFragment optionsDialogFragment = OptionsDialogFragment.newInstance(items, new OptionsDialogFragment.MultipleChoiceListener() {
                @Override
                public void onItemSelected(int pos) {
                    dlgOpcionesResumenRecibosOnClick(pos);
                }
            });
            optionsDialogFragment.show(getFragmentManager(), TAG);
        }
    }

    private void dlgOpcionesFiltroOnClick(int position) {
        switch (position) {
            case POSITION_FILTRO_ENVIADOS:
                PreferenciaBo.getInstance().getPreferencia(getActivity()).setFiltroRecibosEnviados(Preferencia.FILTRO_ENVIADOS);
                filtrar();
                break;
            case POSITION_FILTRO_NO_ENVIADOS:
                PreferenciaBo.getInstance().getPreferencia(getActivity()).setFiltroRecibosEnviados(Preferencia.FILTRO_NO_ENVIADAS);
                filtrar();
                break;
            case POSITION_FILTRO_TODOS:
                PreferenciaBo.getInstance().getPreferencia(getActivity()).setFiltroRecibosEnviados(Preferencia.FILTRO_TODOS);
                filtrar();
                break;
        }
        actualizarTotales(position);
        filtrar();
    }

    private void dlgOpcionesResumenRecibosOnClick(int position) {
        if (position == POSITION_IMPRIMIR_PEDIDOS) {
            ResumenCobranzaPrinter p = new ResumenCobranzaPrinter();
            p._context = getContext();
            try {
                List<Recibo> recibos = reciboBo.recoveryNoRendidos();
                Log.v("Fenibi", "Recibos no rendidos");
                for (Recibo r : recibos) {
                    Log.v("Fenibi", r.getId().getIdRecibo() + '-' + r.getId().getIdPuntoVenta() + '-' + String.valueOf(r.getTotal()));
                }
                if (recibos.size() > 0) {
                    p.print(reciboBo.recoveryNoRendidos());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        actualizarTotales(position);
        filtrar();
    }

    private void filtrar() {
        this.adapter.getFilter().filter("");
    }

    private void imprimirRecibo() {
        Recibo recibo;
        try {
            recibo = reciboBo.recoveryRecibo(itemSeleccionado);
            ReciboPrinter miRecibo = new ReciboPrinter();
            miRecibo._context = getContext();
            miRecibo.print(recibo);
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "imprimirRecibo", e, getActivity());
        }
    }

    private void actualizarTotales(int position) {
        List<Recibo> recibosFiltrados = reciboBo.filtrarRecibos(recibos, getActivity(), position);
        this.adapter.setRecibos(recibosFiltrados);
        this.adapter.notifyDataSetChanged();
        float totalRecibos = 0F;
        for (int i = 0; i < recibosFiltrados.size(); i++) {
            Recibo recibo = recibosFiltrados.get(i);
            totalRecibos += recibo.getTotal();
        }
        this.txtTotalReciboValue.setText(Formatter.formatMoney(totalRecibos));
        String cantidad = String.valueOf(recibosFiltrados.size());
        this.txtCantidadReciboValue.setText(cantidad);
    }

    private void determinarTipoImpresionRecibo() {
        ReciboBo recBo = new ReciboBo(_repoFactory);
        try {
            //busco si se imprime o no el recibo si es = 0 no se imprime
            int tipoImpresionRC = recBo.tipoImpresionRecibo(itemSeleccionado.getId().getIdPuntoVenta());
            itemSeleccionado.setTipoImpresionRecibo(tipoImpresionRC);
        } catch (Exception ex1) {
            ex1.printStackTrace();
            ErrorManager.manageException(TAG, "actualizarTipoImpresionRecibo", ex1);
        }
    }
}
