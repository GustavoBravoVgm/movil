package com.ar.vgmsistemas.view.reparto.hojas;

import static com.ar.vgmsistemas.view.rendicion.egresos.FrmEgreso.CONSULTA;
import static com.ar.vgmsistemas.view.rendicion.egresos.FrmEgreso.MODIFICACION;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.bo.RendicionBo;
import com.ar.vgmsistemas.entity.Compra;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.BaseActivity;
import com.ar.vgmsistemas.view.dialogs.OptionsDialogFragment;
import com.ar.vgmsistemas.view.dialogs.SimpleDialogFragment;
import com.ar.vgmsistemas.view.rendicion.egresos.FrmEgreso;

import java.util.ArrayList;
import java.util.List;

public class FrmListadoEgresos extends BaseActivity {

    private static final String TAG = FrmListadoEgresos.class.getCanonicalName();
    public static final int POSITION_FILTRO_TODOS = 0;
    public static final int POSITION_FILTRO_ENVIADOS = 1;
    public static final int POSITION_FILTRO_NO_ENVIADOS = 2;

    private static final int POSITION_VER_DETALLES = 0;
    private static final int POSITION_CANCELAR_EGRESO = 1;
    private static final int POSITION_MODIFICAR_EGRESO = 2;
    private static final String TAG_FRM_LISTADO_EGRESO = "Frm_listado_egreso";

    private RendicionBo rendicionBo;
    private ListadoEgresoAdapter adapter;
    private Compra itemSeleccionado;
    private List<Compra> egresos;
    private int idHoja;
    private int idSucursal;
    private ListView listadoEgresos;

    private TextView txtTotalEgresoValue;
    private TextView txtCantidadEgresoValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_listado_egreso);
        if (savedInstanceState != null) {
            try {
                idHoja = (int) savedInstanceState.getSerializable(HojaFragment.EXTRA_ID_HOJA);
                idSucursal = (int) savedInstanceState.getSerializable(HojaFragment.EXTRA_ID_SUCURSAL);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Bundle b = this.getIntent().getExtras();
            try {
                idHoja = (int) b.getSerializable(HojaFragment.EXTRA_ID_HOJA);
                idSucursal = (int) b.getSerializable(HojaFragment.EXTRA_ID_SUCURSAL);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        initComponents();
        initVar();
        loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        initComponents();
        initVar();
        actualizarEgresos();
    }

    private void initComponents() {
        this.listadoEgresos = (ListView) findViewById(R.id.listadoEgresos);
        this.listadoEgresos.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lstOnItemClick(parent, view, position, id);
            }
        });
        this.txtTotalEgresoValue = (TextView) findViewById(R.id.lblTotalEgresoValue);
        this.txtCantidadEgresoValue = (TextView) findViewById(R.id.lblCantidadEgresoValue);
    }


    private void actualizarEgresos() {
        loadData();
        adapter.setEgresos(egresos);
        adapter.notifyDataSetChanged();
        actualizarTotales(POSITION_FILTRO_TODOS);
    }

    private void initVar() {
        RepositoryFactory _repoFactory = RepositoryFactory.getRepositoryFactory(getApplicationContext(), RepositoryFactory.ROOM);
        rendicionBo = new RendicionBo(_repoFactory);
        this.egresos = new ArrayList<Compra>();
        this.adapter = new ListadoEgresoAdapter(this, this.egresos);
        this.listadoEgresos.setAdapter(this.adapter);
    }

    private void loadData() {
        try {
            //egresos = rendicionBo.recoveryAllEgresos();
            egresos = rendicionBo.recoveryEgresosByHoja(idHoja, idSucursal);
        } catch (Exception e) {
            Toast toast = Toast.makeText(getApplicationContext(), ErrorManager.ErrorAccederALosDatos, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private void lstOnItemClick(AdapterView<?> adapter, View view, int position, long id) {
        itemSeleccionado = (Compra) adapter.getItemAtPosition(position);
        getDialogOpciones();
    }

    private void getDialogOpciones() {
        String[] items;
        String anularEgreso = getString(R.string.mnCancelarEgreso);
        String verDetalles = getString(R.string.mnVerDetalles);
        String modificarEgreso = getString(R.string.mnModificarEgreso);

        try {
            final boolean isEnviado = rendicionBo.isEnviado(itemSeleccionado);

            //Por Tarea #3335
            if (isEnviado) {
                items = new String[]{verDetalles};
            } else {
                items = new String[]{verDetalles, anularEgreso, modificarEgreso};
            }
            OptionsDialogFragment optionsDialogFragment = OptionsDialogFragment.newInstance(items, new OptionsDialogFragment.MultipleChoiceListener() {

                @Override
                public void onItemSelected(int pos) {
                    dlgOpcionesOnClick(pos);
                }

            });
            optionsDialogFragment.show(getSupportFragmentManager(), TAG_FRM_LISTADO_EGRESO);
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "getDialogOpciones", e, getApplicationContext());
        }

    }

    private void dlgOpcionesOnClick(int position) {
        switch (position) {
            case POSITION_VER_DETALLES: //Ver detalles
                mostrarEgreso();
                break;
            case POSITION_CANCELAR_EGRESO: //Cancelar pedido
                cancelarEgreso();
                break;
            case POSITION_MODIFICAR_EGRESO:
                modificarEgreso();
                break;
        }
    }

    private void mostrarEgreso() {
        Compra egreso;
        try {
            egreso = rendicionBo.recoveryEgresoByID(itemSeleccionado.getId());
            Intent intent = new Intent(getApplicationContext(), FrmEgreso.class);
            intent.putExtra(Compra.EXTRA_COMPRA, egreso);
            intent.putExtra(Compra.EXTRA_MODO, CONSULTA);
            startActivity(intent);

        } catch (Exception e) {
            ErrorManager.manageException(TAG, "mostrarEgreso", e, getApplicationContext());

        }
    }

    private void modificarEgreso() {
        Compra egreso;
        try {
            egreso = rendicionBo.recoveryEgresoByID(itemSeleccionado.getId());

            Intent intent = new Intent(getApplicationContext(), FrmEgreso.class);
            intent.putExtra(Compra.EXTRA_COMPRA, egreso);
            intent.putExtra(Compra.EXTRA_MODO, MODIFICACION);
            startActivity(intent);

        } catch (Exception e) {
            ErrorManager.manageException(TAG, "modificarEgreso", e, getApplicationContext());

        }
    }

    private void cancelarEgreso() {
        getDialogCancelarEgreso();
    }

    private void getDialogCancelarEgreso() {
        SimpleDialogFragment simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK_CANCEL, "Seleccione una opcion", getString(R.string.msjAnularEgreso), new SimpleDialogFragment.OkCancelListener() {

            @Override
            public void onOkSelected() {
                onDeleteEgreso();
            }

            @Override
            public void onCancelSelected() {

            }

        });
        simpleDialogFragment.show(getSupportFragmentManager(), TAG_FRM_LISTADO_EGRESO);
    }

    private void onDeleteEgreso() {
        Compra egreso;
        try {
            egreso = rendicionBo.recoveryEgresoByID(itemSeleccionado.getId());
            rendicionBo.anularEgreso(egreso);
            actualizarEgresos();
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "onDeletePedido", e, getApplicationContext());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
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
        optionsDialogFragment.show(getSupportFragmentManager(), TAG);

    }

    private void dlgOpcionesFiltroOnClick(int position) {
        switch (position) {
            case POSITION_FILTRO_ENVIADOS:
                PreferenciaBo.getInstance().getPreferencia(getApplicationContext()).setFiltroEgresosEnviados(Preferencia.FILTRO_ENVIADOS);
                filtrar();
                break;
            case POSITION_FILTRO_NO_ENVIADOS:
                PreferenciaBo.getInstance().getPreferencia(getApplicationContext()).setFiltroEgresosEnviados(Preferencia.FILTRO_NO_ENVIADAS);
                filtrar();
                break;
            case POSITION_FILTRO_TODOS:
                PreferenciaBo.getInstance().getPreferencia(getApplicationContext()).setFiltroEgresosEnviados(Preferencia.FILTRO_TODOS);
                filtrar();
                break;
        }
        actualizarTotales(position);
        filtrar();
    }

    private void filtrar() {
        this.adapter.getFilter().filter("");
    }

    private void actualizarTotales(int position) {
        List<Compra> egresosFiltrados = rendicionBo.filtrarEgresos(egresos, position);
        this.adapter.setEgresos(egresosFiltrados);
        this.adapter.notifyDataSetChanged();
        double totalEgresos = 0d;
        int cant = 0;
        for (int i = 0; i < egresosFiltrados.size(); i++) {
            Compra egreso = egresosFiltrados.get(i);
            if (egreso.getSnAnulo() == null || egreso.getSnAnulo().equals(Compra.NO)) {
                totalEgresos += egreso.getPrCompra();
                cant += 1;
            }
        }
        this.txtTotalEgresoValue.setText(Formatter.formatMoney(totalEgresos));
        String cantidad = String.valueOf(cant);
        this.txtCantidadEgresoValue.setText(cantidad);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle guardarEstado) {
        super.onSaveInstanceState(guardarEstado);
        guardarEstado.putSerializable(HojaFragment.EXTRA_ID_HOJA, idHoja);
        guardarEstado.putSerializable(HojaFragment.EXTRA_ID_SUCURSAL, idSucursal);
    }

    @Override
    protected void onRestoreInstanceState(Bundle recEstado) {
        super.onRestoreInstanceState(recEstado);
        idHoja = (int) recEstado.getSerializable(HojaFragment.EXTRA_ID_HOJA);
        idSucursal = (int) recEstado.getSerializable(HojaFragment.EXTRA_ID_SUCURSAL);
    }
}
