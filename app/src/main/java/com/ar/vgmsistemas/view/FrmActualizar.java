package com.ar.vgmsistemas.view;

import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.MovimientoBo;
import com.ar.vgmsistemas.bo.SincronizacionBo;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.entity.Version;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.task.actualizacion.ActualizarVersionTask;
import com.ar.vgmsistemas.task.actualizacion.ActualizarVersionTask.ActualizarVersionListener;
import com.ar.vgmsistemas.utils.UpdateManager;
import com.ar.vgmsistemas.view.dialogs.ProgressDialogFragment;
import com.ar.vgmsistemas.view.dialogs.SimpleDialogFragment;
import com.ar.vgmsistemas.view.sincronizacion.FrmSincronizacion;

import java.io.File;

public class FrmActualizar extends BaseFragment {
    // Button
    private Button btnActualizar;

    private TextView txtDescripcion;
    private TextView txtVersionActual;
    private TextView txtVersionServidor;
    private TextView txtRequiereSincronizacion;
    // Task
    private ActualizarVersionTask taskTraerApk;
    private ActualizarVersionTask taskDescargarApk;
    private String _error;
    private Version _version;
    private UpdateManager updateManager;
    private ProgressDialogFragment progressDialogFragment;

    //dao
    RepositoryFactory repo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.lyt_actualizacion, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (taskTraerApk != null) {
            taskTraerApk.attach(this);
        } else {
            this.taskTraerApk = new ActualizarVersionTask(this, ActualizarVersionTask.MODO_TRAER_APK, getActualizarConexionListener());
        }
        getProgressDialog();
        this.taskTraerApk = new ActualizarVersionTask(this, ActualizarVersionTask.MODO_TRAER_APK, getActualizarConexionListener());
        this.taskTraerApk.execute((Void) null);
    }

    @Override
    public void onStart() {
        super.onStart();
        initVar();
        initComponents();
        repo = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);

    }


    private void initVar() {
        updateManager = new UpdateManager(getActivity());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    }

    private void getProgressDialog() {
        String mensaje = this.getString(R.string.msjDescargarActualizacion);
        progressDialogFragment = ProgressDialogFragment.newInstance(mensaje);
        progressDialogFragment.show(requireActivity().getSupportFragmentManager(), "Sincronizando");
    }

    private void getErrorDialog() {
        SimpleDialogFragment simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK, getString(R.string.tituloError), _error);
        requireActivity().getSupportFragmentManager().beginTransaction().add(simpleDialogFragment, "tag").commitAllowingStateLoss();
    }

    private void getConfirmDialog() {
        SimpleDialogFragment simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK,
                getString(R.string.msjConfirmarActualizacion),
                getString(R.string.tituloConfirmar), (SimpleDialogFragment.OkListener) () -> btnActualizarOnClick());
        simpleDialogFragment.show(requireActivity().getSupportFragmentManager(), "");
    }

    private void btnActualizarOnClick() {
        boolean isNonPlayAppAllowed = false;
        try {
            isNonPlayAppAllowed = Settings.Secure.getInt(getActivity().getContentResolver(), Settings.Secure.INSTALL_NON_MARKET_APPS) == 1;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        if (!isNonPlayAppAllowed) {
            SimpleDialogFragment simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK,
                    getString(R.string.msjHabilitarOrigenesDesconocidos), getString(R.string.msjHabilitarOrigenesDesconocidosTitulo), (SimpleDialogFragment.OkListener) () -> {
                        int sdkVersion = Build.VERSION.SDK_INT;
                        if (sdkVersion < 14) { // Android 3.2 o anterior
                            startActivity(new Intent(Settings.ACTION_APPLICATION_SETTINGS));
                        } else {
                            startActivity(new Intent(Settings.ACTION_SECURITY_SETTINGS));
                        }
                    });
            simpleDialogFragment.show(requireActivity().getSupportFragmentManager(), "");
        } else {
            taskDescargarApk = new ActualizarVersionTask(this, ActualizarVersionTask.MODO_DESCARGAR_APK, null);
            taskDescargarApk.execute();
            getProgressDialog();
        }
    }

    private void actualizarVersion() {
        boolean datosEnviados = true;
        // Verifico si se tiene que sincronizar antes de actualizar el apk
        if (_version.isForzarSincronizacion()) {
            datosEnviados = enviarDatos();
        }

        if (datosEnviados) {
            getProgressDialog();
            this.taskDescargarApk = new ActualizarVersionTask(this, ActualizarVersionTask.MODO_DESCARGAR_APK, getActualizarConexionListener());
            this.taskDescargarApk.execute((Void) null);
        } else {
            markAsErrorAlEnviar();
        }
    }


    public void instalarActualizacion() {
        progressDialogFragment.dismiss();
        // Descarga e instala el apk
        int result = Settings.Secure.getInt(getActivity().getContentResolver(), Settings.Secure.INSTALL_NON_MARKET_APPS, 0);
        if (result == 0) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_SETTINGS);
            startActivity(intent);
        } else {
            try {
                String file = Preferencia.getPathSistema() + "/PreventaMovil.apk";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(file)), "application/vnd.android.package-archive");
                startActivity(intent);
            } catch (Exception e) {
                markAsError(getString(R.string.msjErrorInstalarVersion));
            }
        }
    }


    private boolean enviarDatos() {
        boolean datosEnviados = false;
        boolean datosPendientes = false;
        MovimientoBo _movimientoBo = new MovimientoBo(repo);

        SincronizacionBo sincronizacionBo = new SincronizacionBo(getContext(), repo);
        try {
            datosPendientes = _movimientoBo.isDatosPendientesEnvio();
        } catch (Exception e) {
            markAsError(getString(R.string.msjErrorRecuperarPendienteEnvio));
        }
        if (datosPendientes) {
            boolean envioOk = false;
            try {
                envioOk = sincronizacionBo.enviarDatos();
            } catch (Exception e) {
                markAsErrorAlEnviar();
            }
            if (envioOk) {
                datosEnviados = true;
            } else {
                datosEnviados = false;
            }
        } else {
            datosEnviados = true;
        }
        return datosEnviados;
    }


    private void initComponents() {
        this.btnActualizar = getView().findViewById(R.id.btnActualizar);
        this.btnActualizar.setOnClickListener(v -> getConfirmDialog());
        this.txtDescripcion = getView().findViewById(R.id.txtDescripcion);
        this.txtVersionActual = getView().findViewById(R.id.txtVersionActual);
        this.txtVersionServidor = getView().findViewById(R.id.txtVersionServidor);
        this.txtRequiereSincronizacion = getView().findViewById(R.id.txtValorRequiereSincronizacion);
    }

    //@Override
    public Object getLastCustomNonConfigurationInstance() {
        taskTraerApk.detach();
        return taskTraerApk;
    }

    public void markAsError(String error) {
        this._error = error;
        progressDialogFragment.dismiss();
        getErrorDialog();
    }

    public void markAsErrorAlEnviar() {
        progressDialogFragment.dismiss();
        Toast.makeText(getActivity(), getString(R.string.msjErrorEnviarDatos), Toast.LENGTH_LONG).show();
        Intent frmSincronizacion = new Intent(getActivity(), FrmSincronizacion.class);
        startActivity(frmSincronizacion);
    }

    public void recibirVersion(Version version) {
        progressDialogFragment.dismiss();
        this._version = version;

        if (!(_version == null)) {

            this.txtDescripcion.setText(_version.getDescripcion());
            try {
                this.txtVersionActual.setText(getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName);
            } catch (NameNotFoundException e) {
                this.txtVersionActual.setText("-");
            }
            this.txtVersionServidor.setText(_version.getNumeroVersion() + "");

            if (_version.isForzarSincronizacion()) {
                this.txtRequiereSincronizacion.setText("Si");
            } else {
                this.txtRequiereSincronizacion.setText("No");
            }

        }

    }

    private ActualizarVersionListener getActualizarConexionListener() {
        ActualizarVersionListener listener = new ActualizarVersionListener() {

            @Override
            public void onError(String error) {
                markAsError(error);
            }

            @Override
            public void onDone() {
                progressDialogFragment.dismiss();
            }
        };
        return listener;
    }


}
