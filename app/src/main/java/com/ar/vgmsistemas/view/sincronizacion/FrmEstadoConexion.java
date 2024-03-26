package com.ar.vgmsistemas.view.sincronizacion;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.entity.VersionInfoVendedoresList;
import com.ar.vgmsistemas.task.TestConnection;
import com.ar.vgmsistemas.task.sincronizacion.ActualizacionConfiguracionesTask;
import com.ar.vgmsistemas.task.sincronizacion.ActualizacionConfiguracionesTask.ActualizarConfiguracionListener;
import com.ar.vgmsistemas.view.dialogs.ProgressDialogFragment;
import com.ar.vgmsistemas.view.dialogs.SimpleDialogFragment;
import com.ar.vgmsistemas.view.menu.BaseNavigationFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class FrmEstadoConexion extends BaseNavigationFragment {

    TextView txtWIFI;
    TextView txt3G;
    TextInputEditText txtDireccionRemota;
    TextInputEditText txtDireccionRemotaResguardo;
    TextInputEditText txtDireccionLocal;
    TextInputEditText txtFTP;
    private TextView tvConexionInternet;
    private ProgressBar pbConexionInternet;
    private TestConnection mTestConnection;
    private TestConnection testConnection;

    MaterialButton btnProbarConexionRemota;
    MaterialButton btnProbarConexionRemotaResguardo;
    MaterialButton btnProbarConexionLocal;
    MaterialButton btnProbarConexionFTP;

    private static final String ESTADO_APAGADO = "APAGADO";
    private static final String ESTADO_ENCENDIDO = "ENCENDIDO";
    private static final String ESTADO_CONECTADO = "CONECTADO";
    private static final String ESTADO_NO_CONECTADO = "NO CONECTADO";
    private ProgressDialogFragment progressDialogFragment;
    public static final String TAG = FrmSincronizacion.class.getCanonicalName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.lyt_estado_conexion, container, false);
    }

    private void initVars() {
        //mTestConnection = new TestConnection();
        testConnection = new TestConnection(TestConnection.INTERNET_CONNECTION, getActivity(), null, null, new TestConnection.TestConnectionListener() {
            @Override
            public void onDone(boolean isConnected) {
                pbConexionInternet.setVisibility(View.INVISIBLE);
                if (isConnected) {
                    tvConexionInternet.setText(ESTADO_CONECTADO);
                    tvConexionInternet.setTextColor(Color.GREEN);
                } else {
                    tvConexionInternet.setText(ESTADO_NO_CONECTADO);
                    tvConexionInternet.setTextColor(getResources().getColor(R.color.grisApagado));
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        initVars();
        initComponents();
        loadData();
    }

    public static FrmEstadoConexion newInstance() {
        FrmEstadoConexion frmEstadoConexion = new FrmEstadoConexion();
        return frmEstadoConexion;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.mn_estado_conexion, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initComponents() {

        // txtWIFI
        this.txtWIFI = (TextView) getView().findViewById(R.id.lblWifiValue);
        this.txtWIFI.setKeyListener(null);

        // txt3G
        this.txt3G = (TextView) getView().findViewById(R.id.lbl3GValue);
        this.txt3G.setKeyListener(null);

        // txtDireccionRemota
        this.txtDireccionRemota = getView().findViewById(R.id.txtUrlRemotaServidor);
        this.txtDireccionRemota.setKeyListener(null);

        // txtDireccionRemotaServidor
        this.txtDireccionRemotaResguardo = getView().findViewById(R.id.txtUrlRemotaServidor2);
        this.txtDireccionRemotaResguardo.setKeyListener(null);

        // txtDireccionLocal
        this.txtDireccionLocal = getView().findViewById(R.id.txtUrlLocalServidor);
        this.txtDireccionLocal.setKeyListener(null);

        // txtFTP
        this.txtFTP = getView().findViewById(R.id.txtFtpUserName);
        this.txtFTP.setKeyListener(null);

        // btnProbarConexionRemota
        this.btnProbarConexionRemota = getView().findViewById(R.id.btnTestConexionRemotaServidor);
        this.btnProbarConexionRemota.setOnClickListener(v -> {
            reloadConnectionState();
            testConexion(PreferenciaBo.getInstance().getPreferencia(getActivity()).getUrlRemotaServidor(), TestConnection.SERVER_CONNECTION);
            //btnProbarConexionRemotaOnClick();
        });
        // btnProbarConexionRemotaResguardo
        this.btnProbarConexionRemotaResguardo = getView().findViewById(R.id.btnTestConexionRemotaServidor2);
        this.btnProbarConexionRemotaResguardo.setOnClickListener(v -> {
            reloadConnectionState();
            testConexion(PreferenciaBo.getInstance().getPreferencia(getActivity()).getUrlRemotaServidor2(), TestConnection.SERVER_CONNECTION);
            //btnProbarConexionRemotaResguardoOnClick();
        });
        // btnProbarConexionLocal
        this.btnProbarConexionLocal = getView().findViewById(R.id.btnTestConexionLocalServidor);
        this.btnProbarConexionLocal.setOnClickListener(v -> {
            reloadConnectionState();
            testConexion(PreferenciaBo.getInstance().getPreferencia(getActivity()).getUrlLocalServidor(), TestConnection.SERVER_CONNECTION);
            //btnProbarConexionLocalOnClick();
        });
        // btnProbarFTP
        this.btnProbarConexionFTP = getView().findViewById(R.id.btnTestConexionFtp);
        this.btnProbarConexionFTP.setOnClickListener(v -> {
            //reloadConnectionState();
            testConexion(PreferenciaBo.getInstance().getPreferencia(getActivity()).getFtpUserName(), TestConnection.FTP_CONNECTION);
            //btnProbarConexionFTPOnClick(v);
        });
        tvConexionInternet = (TextView) getView().findViewById(R.id.lblConnectionInternet);
        pbConexionInternet = (ProgressBar) getView().findViewById(R.id.pbCheckInternetConnection);

    }

    private void loadData() {

        reloadConnectionState();
        // Seteo url de conexiones de red y ftp
        this.txtDireccionRemota.setText(PreferenciaBo.getInstance().getPreferencia(getActivity()).getUrlRemotaServidor());
        this.txtDireccionRemotaResguardo.setText(PreferenciaBo.getInstance().getPreferencia(getActivity())
                .getUrlRemotaServidor2());
        this.txtDireccionLocal.setText(PreferenciaBo.getInstance().getPreferencia(getActivity()).getUrlLocalServidor());
        this.txtFTP.setText(PreferenciaBo.getInstance().getPreferencia(getActivity()).getFtpUserName());

    }

    @Override
    public void onResume() {
        super.onResume();
        reloadConnectionState();
    }

    private void reloadConnectionState() {
        // Seteo estado de WIFI
        pbConexionInternet.setVisibility(View.VISIBLE);
        tvConexionInternet.setText("");
        if (TestConnection.isWifiEnabled(getActivity())) {
            this.txtWIFI.setText(ESTADO_ENCENDIDO);
            this.txtWIFI.setTextColor(Color.GREEN);
        } else {
            txtWIFI.setText(ESTADO_APAGADO);
            txtWIFI.setTextColor(getResources().getColor(R.color.grisApagado));
        }
        // Seteo estado de 3G
        if (TestConnection.isNetworkEnabled(getActivity())) {
            this.txt3G.setText(ESTADO_ENCENDIDO);
            this.txt3G.setTextColor(Color.GREEN);
        } else {
            txt3G.setText(ESTADO_APAGADO);
            txt3G.setTextColor(getResources().getColor(R.color.grisApagado));
        }
        if (testConnection.getStatus() == AsyncTask.Status.FINISHED || testConnection.getStatus() == AsyncTask.Status.PENDING || testConnection.getStatus() == AsyncTask.Status.RUNNING) {
            initVars();
            testConnection.execute();
        } else {
            testConnection.execute();
        }

    }

    String mensajeResult = "";


    private void testConexion(String txtUrl, int typeConnection) {
        TestConnection testConnection = new TestConnection(typeConnection, getActivity(), txtUrl, txtUrl, new TestConnection.TestConnectionListener() {
            @Override
            public void onDone(boolean isConnected) {
                if (isConnected) {
                    mensajeResult = getString(R.string.msjConexionExitosa);
                } else {
                    mensajeResult = getString(R.string.msjErrorConectarServidor);
                }
                SimpleDialogFragment simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK, getString(R.string.tituloProbarConexion), mensajeResult);
                getFragmentManager().beginTransaction().add(simpleDialogFragment, "tag").commitAllowingStateLoss();
            }

            @Override
            public void onError(String error) {

            }
        });
        testConnection.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // no hago nada con case R.id.itemActualizar:
            case R.id.itemActualizarConfConex: {
                SimpleDialogFragment simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK, getString(R.string.msjConfirmacionActualizacionConfig), getString(R.string.tituloConfirmar), new SimpleDialogFragment.OkListener() {

                    @Override
                    public void onOkSelected() {
                        progressDialogFragment = ProgressDialogFragment.newInstance(getActivity().getString(R.string.msjDescargarConfiguraciones));
                        progressDialogFragment.show(getFragmentManager(), "myDialogFragment");
                        ActualizacionConfiguracionesTask task = new ActualizacionConfiguracionesTask(getActivity().getApplicationContext(), ActualizacionConfiguracionesTask.CONFIGURACION_AVANZADA, getActualizarConfiguracionListener());
                        task.execute((Void) null);
                    }
                });
                simpleDialogFragment.show(getFragmentManager(), TAG);

                break;
            }
            default:
                break;
        }
        if (item.getItemId() == R.id.itemActualizar) {
            reloadConnectionState();
        }
        return super.onOptionsItemSelected(item);
    }

    private ActualizarConfiguracionListener getActualizarConfiguracionListener() {
        ActualizarConfiguracionListener actualizarConfiguracionListener = new ActualizarConfiguracionListener() {

            @Override
            public void onError(String error, boolean restaurarApk) {
                progressDialogFragment.dismiss();
                SimpleDialogFragment simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK, error, getString(R.string.sincronizando), new SimpleDialogFragment.OkListener() {

                    @Override
                    public void onOkSelected() {

                    }
                });
                getFragmentManager().beginTransaction().add(simpleDialogFragment, "tag").commitAllowingStateLoss();
            }

            @Override
            public void onDone(VersionInfoVendedoresList listadoVendedoresInfo) {
                loadData();
                progressDialogFragment.dismiss();
            }
        };

        return actualizarConfiguracionListener;
    }

}
