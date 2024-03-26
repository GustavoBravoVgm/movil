package com.ar.vgmsistemas.view.configuracion;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.bo.RepartidorBo;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.entity.Repartidor;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.view.menu.BaseNavigationFragment;

import java.util.List;

public class FrmConfiguracionEstandar extends BaseNavigationFragment {

    private CheckBox chkDescargaAutomaticaStock;
    private CheckBox chkEnvioAutomaticoVenta;
    private CheckBox chkEnvioAutomaticoRecibo;
    private CheckBox chkSugerenciaSincronizacion;
    private CheckBox chkBusquedaPorListaArticulo;
    private CheckBox chkEnvioAutomaticoHojasDetalle;
    private CheckBox chkEnvioAutomaticoEgreso;
    //private CheckBox chkEnvioAutomaticoEntrega;
    private Spinner cmbRepartidor;

    private RepositoryFactory _repoFactory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.lyt_configuracion_estandar, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        initComponents();
        loadData();
        //Desactivo la notificacion de Error de descarga de stock
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(ns);
        mNotificationManager.cancel(Preferencia.NOTIFICATION_ERROR_DESCARGA_STOCK);
    }

    private void loadData() {
        this.chkDescargaAutomaticaStock.setChecked(PreferenciaBo.getInstance().getPreferencia(getActivity()).isDescargaStockAutomatico());
        this.chkEnvioAutomaticoVenta.setChecked(PreferenciaBo.getInstance().getPreferencia(getActivity()).isEnvioPedidoAutomatico());
        this.chkEnvioAutomaticoRecibo.setChecked(PreferenciaBo.getInstance().getPreferencia(getActivity()).isEnvioReciboAutomatico());
        this.chkSugerenciaSincronizacion.setChecked(PreferenciaBo.getInstance().getPreferencia(getActivity()).isSugerenciaSincronizarInicio());
        this.chkBusquedaPorListaArticulo.setChecked(PreferenciaBo.getInstance().getPreferencia(getActivity()).isBusquedaPorListaArticulo());
        this.chkEnvioAutomaticoHojasDetalle.setChecked(PreferenciaBo.getInstance().getPreferencia(getActivity()).isEnvioHojaDetalleAutomatico());
        this.chkEnvioAutomaticoEgreso.setChecked(PreferenciaBo.getInstance().getPreferencia(getActivity()).isEnvioEgresoAutomatico());
        //this.chkEnvioAutomaticoEntrega.setChecked(PreferenciaBo.getInstance().getPreferencia(getActivity()).isEnvioEntregaAutomatico());
        this._repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        loadRepartidores();
    }

    private void loadRepartidores() {
        long idRepartidor = PreferenciaBo.getInstance().getPreferencia(getActivity()).getIdRepartidorPorDefecto();
        Repartidor repartidor = null;
        List<Repartidor> repartidores;
        try {
            RepartidorBo RepartidorBo = new RepartidorBo(_repoFactory);
            repartidores = RepartidorBo.recoveryAll();
            ArrayAdapter<Repartidor> adapterRepartidor = new ArrayAdapter<Repartidor>(getActivity(), R.layout.simple_spinner_item, repartidores);
            this.cmbRepartidor.setAdapter(adapterRepartidor);
            for (int i = 0; i < repartidores.size(); i++) {
                if (repartidores.get(i).getId() == idRepartidor) {
                    repartidor = repartidores.get(i);
                    break;
                }
            }
            ArrayAdapter<Repartidor> arrayAdapter = (ArrayAdapter<Repartidor>) cmbRepartidor.getAdapter();
            int pos = arrayAdapter.getPosition(repartidor);
            this.cmbRepartidor.setSelection(pos);
        } catch (Exception e) {
            Toast toast = Toast.makeText(getActivity(), ErrorManager.ErrorAccederALosDatos, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private void chkDescargaAutomaticaStockOnCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        PreferenciaBo.getInstance().getPreferencia(getActivity()).setDescargaStockAutomatico(isChecked);
    }

    private void chkEnvioAutomaticoVentaOnCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        PreferenciaBo.getInstance().getPreferencia(getActivity()).setEnvioPedidoAutomatico(isChecked);
    }

    private void chkEnvioAutomaticoReciboOnCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        PreferenciaBo.getInstance().getPreferencia(getActivity()).setEnvioReciboAutomatico(isChecked);
    }

    private void chkBusquedaPorListaArticulo(CompoundButton buttonView, boolean isChecked) {
        PreferenciaBo.getInstance().getPreferencia(getActivity()).setBusquedaPorListaArticulo(isChecked);
    }

    private void chkEnvioAutomaticoHojasDetalle(CompoundButton buttonView, boolean isChecked) {
        PreferenciaBo.getInstance().getPreferencia(getActivity()).setEnvioHojaDetalleAutomatico(isChecked);
    }

    private void chkEnvioAutomaticoEgreso(CompoundButton buttonView, boolean isChecked) {
        PreferenciaBo.getInstance().getPreferencia(getActivity()).setEnvioEgresoAutomatico(isChecked);
    }

    private void chkEnvioAutomaticoEntrega(CompoundButton buttonView, boolean isChecked) {
        PreferenciaBo.getInstance().getPreferencia(getActivity()).setEnvioEntregaAutomatico(isChecked);
    }

    private void initComponents() {
        //chkDescargaStockAutomatica
        this.chkDescargaAutomaticaStock = (CheckBox) getActivity().findViewById(R.id.chkDescargaAutomaticaStock);
        this.chkDescargaAutomaticaStock.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                chkDescargaAutomaticaStockOnCheckedChanged(buttonView, isChecked);
            }
        });

        //chkEnvioAutomaticoVenta
        this.chkEnvioAutomaticoVenta = (CheckBox) getActivity().findViewById(R.id.chkEnvioAutomaticoVenta);
        this.chkEnvioAutomaticoVenta.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                chkEnvioAutomaticoVentaOnCheckedChanged(buttonView, isChecked);
            }
        });

        //chkEnvioAutomaticoRecibo
        this.chkEnvioAutomaticoRecibo = (CheckBox) getActivity().findViewById(R.id.chkEnvioAutomaticoRecibo);
        this.chkEnvioAutomaticoRecibo.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                chkEnvioAutomaticoReciboOnCheckedChanged(buttonView, isChecked);
            }


        });

        //chkSugerenciaSincronizacion
        this.chkSugerenciaSincronizacion = (CheckBox) getActivity().findViewById(R.id.chkSugerenciaSincronizar);
        this.chkSugerenciaSincronizacion.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                chkSugerenciaSincronizar(buttonView, isChecked);

            }

        });

        // chkBusquedaPorListaArticulo
        this.chkBusquedaPorListaArticulo = (CheckBox) getActivity().findViewById(R.id.chkBusquedaPorListaArticulo);
        this.chkBusquedaPorListaArticulo.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                chkBusquedaPorListaArticulo(buttonView, isChecked);
            }
        });

        this.chkEnvioAutomaticoHojasDetalle = (CheckBox) getActivity().findViewById(R.id.chkEnvioAutomaticoHojasDetalle);
        this.chkEnvioAutomaticoHojasDetalle.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                chkEnvioAutomaticoHojasDetalle(buttonView, isChecked);
            }
        });

        this.chkEnvioAutomaticoEgreso = (CheckBox) getActivity().findViewById(R.id.chkEnvioAutomaticoEgreso);
        this.chkEnvioAutomaticoEgreso.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                chkEnvioAutomaticoEgreso(buttonView, isChecked);
            }
        });

        //cmbRepartidor
        this.cmbRepartidor = (Spinner) getActivity().findViewById(R.id.cmbRepartidor);
        this.cmbRepartidor.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
                cmbRepartidorOnItemSelected(adapter, view, position, id);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    public void cmbRepartidorOnItemSelected(AdapterView<?> adapter, View view, int position, long id) {
        Repartidor repartidor = (Repartidor) adapter.getSelectedItem();
        PreferenciaBo.getInstance().getPreferencia(getActivity()).setIdRepartidorPorDefecto(repartidor.getId());
    }

    @Override
    public void onStop() {
        super.onStop();
        guardar();
    }

    private void chkSugerenciaSincronizar(CompoundButton buttonView, boolean isChecked) {
        PreferenciaBo.getInstance().getPreferencia(getActivity()).setSugerenciaSincronizarInicio(isChecked);
    }

    private void guardar() {
        PreferenciaBo.getInstance().savePreference(getActivity());
        Toast toast = Toast.makeText(getActivity(), R.string.msjConfiguracionesGuardadas, Toast.LENGTH_LONG);
        toast.show();
    }
}
