package com.ar.vgmsistemas.view;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Process;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.DiaLaboralBo;
import com.ar.vgmsistemas.bo.EmpresaBo;
import com.ar.vgmsistemas.bo.LoginEnum;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.bo.SincronizacionBo;
import com.ar.vgmsistemas.bo.VendedorBo;
import com.ar.vgmsistemas.changelog.ChangeLog;
import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.entity.VersionInfoVendedor;
import com.ar.vgmsistemas.entity.VersionInfoVendedoresList;
import com.ar.vgmsistemas.gps.GPSManagement;
import com.ar.vgmsistemas.gps.GPSService;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.task.TestConnection;
import com.ar.vgmsistemas.task.sincronizacion.ActualizacionConfiguracionesTask;
import com.ar.vgmsistemas.task.sincronizacion.ActualizacionConfiguracionesTask.ActualizarConfiguracionListener;
import com.ar.vgmsistemas.task.sincronizacion.BuscarConexionTask;
import com.ar.vgmsistemas.task.sincronizacion.SincronizacionTask;
import com.ar.vgmsistemas.utils.ComparatorDateTime;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.FileManager;
import com.ar.vgmsistemas.utils.PermissionManager;
import com.ar.vgmsistemas.utils.TaskManager;
import com.ar.vgmsistemas.view.configuracion.FrmConfiguracionesAvanzadasTab;
import com.ar.vgmsistemas.view.dialogs.BaseDialogFragment;
import com.ar.vgmsistemas.view.dialogs.InputDialogFragment;
import com.ar.vgmsistemas.view.dialogs.ProgressDialogFragment;
import com.ar.vgmsistemas.view.dialogs.SimpleDialogFragment;
import com.ar.vgmsistemas.view.menu.NavigationMenu;
import com.ar.vgmsistemas.viewmodel.FrmLoginViewModel;
import com.ar.vgmsistemas.ws.vendedor.StateVendedorResultReceiver;
import com.ar.vgmsistemas.ws.vendedor.StateVendedorService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class FrmLogin extends AppCompatActivity {

    public static final String EXTRA_IS_RUNNING = "isRunning";
    public static final String EXTRA_IS_FROM_OTHER_APP = "EXTRA_IS_FROM_OTHER_APP";
    public static final String EXTRA_MODO = "modo";

    public static final int EXTRA_UBICACION_MENU = 2;
    public static final int EXTRA_UBICACION_INICIO = 1;
    public static final String EXTRA_UBICACION = "extraUbicacion";

    private static final String TAG = "FrmLogin";
    /*private EditText txtUsuario;
    private EditText txtPassword;*/
    private TextInputEditText txtUsuario;
    private TextInputEditText txtPassword;

    private boolean isRotationEnabled = false;

    // BO
    private VendedorBo _vendedorBo;
    private EmpresaBo _empresaBo;
    private DiaLaboralBo _diaLaboralBo;

    // Dialogos
    private static final int DIALOG_DATOS_DESACTUALIZADOS = 0;
    private static final int DIALOG_CODIGO_VENDEDOR_NO_INGRESADO = 1;
    private static final int DIALOG_ACTUALIZAR_CONFIGURACION = 2;
    private static final int PROGRESS_DIALOG = 3;
    private static final int ERROR_DIALOG = 4;
    private static final int DIALOG_ERROR_APK = 5;
    private static final int DIALOG_USUARIO_FTP_NO_INGRESADO = 6;
    private static final int DIALOG_USUARIO_FTP_INCORRECTO = 7;
    private static final int DIALOG_FALLO_SINCRONIZACION = 8;
    private static final String TAG_DIALOG_DATOS_DESACTUALIZADOS = "datos_desactualizados";
    private static final String TAG_DIALOG_CODIGO_VENDEDOR_NO_INGRESADO = "codigo_vendedor_no_ingresado";
    private static final String TAG_DIALOG_ACTUALIZAR_CONFIGURACION = "actualizar_configuracion";
    private static final String TAG_PROGRESS_DIALOG = "progress_dialog";
    private static final String TAG_ERROR_DIALOG = "error_dialog";
    private static final String TAG_DIALOG_ERROR_APK = "error_apk_dialog";
    private static final String TAG_DIALOG_USUARIO_FTP_NO_INGRESADO = "usuario_ftp_no_ingresado";
    private static final String TAG_DIALOG_USUARIO_FTP_INCORRECTO = "usuario_ftp_incorrecto";
    private static final String TAG_DIALOG_FALLO_SINCRONIZACION = "fallo_sincronizacion";

    // Modos
    //private int modo;
    //private int ubicacion;
    public static final int MODO_ADMINISTRACION_LOGIN = 0;
    public static final int MODO_CONFIGURACION = 1;
    public static final boolean MODO_RECONFIGURANDO_MOVIL = true;
    public static final boolean MODO_SOLO_DESCARGA_DATOS = false;
    public static final boolean MODO_SOLICITAR_VENDEDOR = true;
    public static final boolean MODO_SOLO_ACTUALIZAR_CONF = false;
    private ProgressDialogFragment progressDialogFragment;

    // DAO
    public RepositoryFactory _db;

    // Task
    private ActualizacionConfiguracionesTask task;
    //view model
    private FrmLoginViewModel miViewModelLogin;
    //permisos
    private static final int REQUEST_CODE_ALL_PERMISSION = 123456;

    private String error;
    ChangeLog changeLog;

    private SincronizacionTask sincronizacionTask;

    //DTO
    private VersionInfoVendedoresList _versionInfoVendsList;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Comprobar si la aplicación tiene permiso WRITE_SETTINGS
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                // Si no tiene permiso, lanzar un intento para solicitar permiso
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }
            if (solicitarPermisos()) {
                iniciar();
            }
        } else {
            // Para versiones anteriores a Android 6.0, el permiso se considera otorgado en tiempo de instalación
            Preferencia.setPathSistemaPreferido(Environment.getExternalStorageDirectory() + "");
            iniciar();
        }
    }

    private void iniciar() {
        // Registrar el receptor de transmisiones
        LocalBroadcastManager.getInstance(this).registerReceiver(
                miReceiver, new IntentFilter(GPSManagement.ACTION_VGM_GPS_MSJ)
        );

        //Fabric.with(getApplication(), new Crashlytics());
        miViewModelLogin = new ViewModelProvider(this).get(FrmLoginViewModel.class);
        try {
            miViewModelLogin.setRotationEnabled(Settings.System.getInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION) == 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        isRotationEnabled = miViewModelLogin.isRotationEnabled();
        setContentView(R.layout.lyt_login);
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        Bundle b = getIntent().getExtras();
        if (b != null) {
            miViewModelLogin.setRunning(b.getBoolean(EXTRA_IS_RUNNING, false));
            miViewModelLogin.setIsFromOtherApp(b.getString(EXTRA_IS_FROM_OTHER_APP));
            miViewModelLogin.setModo(b.getInt(EXTRA_MODO));
            if (miViewModelLogin.getModo() != MODO_ADMINISTRACION_LOGIN) {
                Toast.makeText(getApplicationContext(), R.string.validacionOperacion, Toast.LENGTH_LONG).show();
            }
            miViewModelLogin.setUbicacion(b.getInt(EXTRA_UBICACION, EXTRA_UBICACION_INICIO));
        } else {
            // Al inicio seteo el Modo 0 para el inicio porque sino da una
            // excepción al iniciar la aplicación
            miViewModelLogin.setModo(MODO_ADMINISTRACION_LOGIN);
            miViewModelLogin.setRunning(false);
            miViewModelLogin.setUbicacion(EXTRA_UBICACION_INICIO);
        }
        if (miViewModelLogin.getIsFromOtherApp() == null) {
            // Cargo las preferencias
            PreferenciaBo.getInstance().loadPreference(getApplicationContext());
            Preferencia.crearDirectorioAplicacion(getApplicationContext());

            // Obtengo la tarea del ViewModel
            task = miViewModelLogin.getTask();

            // Verifica si la tarea ya está en progreso
            if (task == null) {
                // Si no hay tarea en curso, crea una nueva tarea y almacénala en el ViewModel
                task = new ActualizacionConfiguracionesTask(this, ActualizacionConfiguracionesTask.LOGIN, getActualizarConfiguracionListener(this, MODO_SOLO_ACTUALIZAR_CONF));
                miViewModelLogin.setTask(task);
               /* // Inicia la tarea en segundo plano
                task.execute();*/
            } else {
                miViewModelLogin.getTask().attach(this);
            }
            AppDataBase.cleanBackups(); // Limpio los archivos de backups
            initVar();
            splashCambiosVersion(); // Muestro los cambios de la version
            initComponents();
        } else {
            finish();
        }
    }


    public void onCreate() {
    }


    private boolean solicitarPermisos() {
        String[] permisos = {Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CALL_PHONE};
                /*Manifest.permission.BLUETOOTH,Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.INTERNET,
                Manifest.permission.WRITE_SETTINGS*/
        //verifico si los permisos estan concedidos
        if (!PermissionManager.checkPermission(this, permisos)) {
            do {
                ActivityCompat.requestPermissions(this, permisos, REQUEST_CODE_ALL_PERMISSION);
            } while (!PermissionManager.checkPermission(this, permisos));
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_ALL_PERMISSION) {
            boolean permisosConcedidos = true;
            for (int resultado : grantResults) {
                permisosConcedidos = permisosConcedidos && (resultado == PackageManager.PERMISSION_GRANTED);
            }
            if (permisosConcedidos) {
                // El usuario concedió el permiso, realiza la operación que requería el permiso
                iniciar();
            } else {
                // El usuario denegó el permiso, muestra un mensaje de que la operación no puede realizarse sin permiso
                mostrarMensajePermisosNecesarios();
            }
        }
    }

    private void mostrarMensajePermisosNecesarios() {
        String tituloMsj = "Permisos Necesarios";
        String mensaje = "No has concedido todos los permisos necesarios para utilizar todas las " +
                "funciones de esta aplicación.";
        AlertDialog builder = new AlertDialog(getApplicationContext(), tituloMsj, mensaje);
        builder.setPositiveButton("Entendido", (dialogInterface, i) -> {
            // Cerrar la aplicación o realizar otras acciones según tus necesidades
            finish();
        });
        builder.show();
    }

    private SincronizacionTask.SincronizacionListener getSincronizacionListener() {
        return new SincronizacionTask.SincronizacionListener() {
            @Override
            public void onDone() {
                //progressDialogFragment.dismiss();
                if (miViewModelLogin.getProgressDialogFragment() != null &&
                        miViewModelLogin.getProgressDialogFragment().isAdded()) {
                    miViewModelLogin.getProgressDialogFragment().dismiss();
                }
                setAutoOrientationEnabled(getApplicationContext(), isRotationEnabled);
                Toast.makeText(getApplicationContext(), getString(R.string.msjActualizacionExitosa), Toast.LENGTH_LONG).show();
                showMenuPrincipal(NavigationMenu.HOME_CLIENTE);
            }

            @Override
            public void onError(String error) {
                //progressDialogFragment.dismiss();
                if (miViewModelLogin.getProgressDialogFragment() != null &&
                        miViewModelLogin.getProgressDialogFragment().isAdded()) {
                    miViewModelLogin.getProgressDialogFragment().dismiss();
                }
                setAutoOrientationEnabled(getApplicationContext(), isRotationEnabled);
                Toast.makeText(getApplicationContext(), getString(R.string.msjErrorDescarga), Toast.LENGTH_LONG).show();
            }
        };
    }

    private ActualizarConfiguracionListener getActualizarConfiguracionListener(final Context context, final boolean modo) {
        return new ActualizarConfiguracionListener() {
            @Override
            public void onError(String error, boolean restaurarApk) {
                //progressDialogFragment.dismiss();
                if (miViewModelLogin.getProgressDialogFragment() != null &&
                        miViewModelLogin.getProgressDialogFragment().isAdded()) {
                    miViewModelLogin.getProgressDialogFragment().dismiss();
                }
                if (modo == MODO_SOLICITAR_VENDEDOR) {
                    SimpleDialogFragment simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK, getString(R.string.msjErrorAlActualizarConfiguraciones), getString(R.string.tituloError), (SimpleDialogFragment.OkListener) () -> promptUsuarioFTP());
                    getSupportFragmentManager().beginTransaction().add(simpleDialogFragment, "tag").commitAllowingStateLoss();
                } else {
                    if (restaurarApk) {
                        SimpleDialogFragment simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK, getString(R.string.msjErrorAlActualizarVersion), getString(R.string.tituloError), (SimpleDialogFragment.OkListener) () -> restaurarApk());
                        getSupportFragmentManager().beginTransaction().add(simpleDialogFragment, "tag").commitAllowingStateLoss();
                    } else {
                        markAsError(error);
                    }
                }
                setAutoOrientationEnabled(getApplicationContext(), isRotationEnabled);
            }

            @Override
            public void onDone(VersionInfoVendedoresList listadoVendedoresInfo) {
                //progressDialogFragment.dismiss();
                if (miViewModelLogin.getProgressDialogFragment() != null &&
                        miViewModelLogin.getProgressDialogFragment().isAdded()) {
                    miViewModelLogin.getProgressDialogFragment().dismiss();
                }

                if (modo == MODO_SOLICITAR_VENDEDOR) {
                    if (listadoVendedoresInfo != null && listadoVendedoresInfo.getListaVendedoresInfo() != null &&
                            listadoVendedoresInfo.getListaVendedoresInfo().size() > 0) {
                        _versionInfoVendsList = listadoVendedoresInfo;
                    }
                    promptVendededor(MODO_RECONFIGURANDO_MOVIL);
                } else {
                    Toast.makeText(context, getString(R.string.msjActualizacionExitosa), Toast.LENGTH_SHORT).show();
                    //miViewModelLogin.getToastActualizacionExitosa( context);
                    changeLog.updateVersionInPreferences();
                    setAutoOrientationEnabled(getApplicationContext(), false);
                    //progressDialogFragment = ProgressDialogFragment.newInstance(getString(R.string.msjSincronizando));
                    //progressDialogFragment.show(getSupportFragmentManager(), "myDialogFragment");
                    miViewModelLogin.setProgressDialogFragment(ProgressDialogFragment.newInstance(getString(R.string.msjSincronizando)));
                    miViewModelLogin.getProgressDialogFragment().show(getSupportFragmentManager(), "myDialogFragment");
                    SincronizacionBo sincronizacionBo = new SincronizacionBo(getApplicationContext(), _db);
                    sincronizacionBo.isUrlActiva(getApplicationContext(), getBuscarConexionListener());
                }
            }
        };
    }

    private BuscarConexionTask.BuscarConexionListener getBuscarConexionListener() {
        return new BuscarConexionTask.BuscarConexionListener() {
            @Override
            public void onError(String error) {
                //progressDialogFragment.dismiss();
                if (miViewModelLogin.getProgressDialogFragment() != null) {
                    miViewModelLogin.getProgressDialogFragment().dismiss();
                }
                setAutoOrientationEnabled(getApplicationContext(), isRotationEnabled);
                Toast.makeText(getApplicationContext(), getString(R.string.msjErrorConectarServidor), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDone() {
                sincronizacionTask = new SincronizacionTask(getApplicationContext(), SincronizacionTask.DESCARGA, getSincronizacionListener());
                sincronizacionTask.execute((Void) null);
            }
        };
    }

    @Override
    protected void onStop() {
        super.onStop();
        PreferenciaBo.getInstance().savePreference(getApplicationContext());
    }

    private void splashCambiosVersion() {
        if (changeLog.firstRun()) {
            changeLog.getLogDialog().show();
        }
    }

    private void initComponents() {

        // button Cancelar
        txtUsuario = findViewById(R.id.txtUsuario);
        txtPassword = findViewById(R.id.txtPassword);

        /*Button btnCancelar = findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(v -> {
            //finish();
            Process.killProcess(Process.myPid());
        });*/

        // button Aceptar
        MaterialButton btnAceptar = findViewById(R.id.btnAceptar);
        btnAceptar.setOnClickListener(v -> checkLogin());

        // EditText usuario
        txtUsuario = findViewById(R.id.txtUsuario);
        // EditText password
        txtPassword = findViewById(R.id.txtPassword);
        // TextView Versión
        TextView txtVersion = findViewById(R.id.txtNroVersion);
        try {
            txtVersion.setText(this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName);
        } catch (NameNotFoundException e) {
            txtVersion.setText("-");
        }
        CheckBox mCheckBox = findViewById(R.id.cbRecordar);
        mCheckBox.setChecked(PreferenciaBo.getInstance().getPreferencia(getApplicationContext()).isRecordarContrasena());
        mCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> PreferenciaBo.getInstance().getPreferencia(getApplicationContext()).setRecordarContrasena(isChecked));
        if (mCheckBox.isChecked() && miViewModelLogin.getModo() != MODO_CONFIGURACION) {
            txtUsuario.setText(String.valueOf(PreferenciaBo.getInstance().getPreferencia(getApplicationContext()).getIdVendedor()));
            txtPassword.setText(PreferenciaBo.getInstance().getPreferencia(getApplicationContext()).getContrasena());

        } else if (miViewModelLogin.getModo() == MODO_CONFIGURACION) {
            mCheckBox.setVisibility(View.GONE);
        }
        ImageButton mSincronizacion = findViewById(R.id.ibtnSincronizacion);
        mSincronizacion.setOnClickListener(v -> promptVendededor(MODO_SOLO_DESCARGA_DATOS));
        ImageButton mConfiguracion = findViewById(R.id.ibtnConfiguracion);
        if (movilNoConfigurado()) {
            mConfiguracion.setOnClickListener(v -> promptUsuarioFTP());
        } else {
            mConfiguracion.setVisibility(View.GONE);
        }
    }

    private boolean movilNoConfigurado() {
        Preferencia preferencia = PreferenciaBo.getInstance().getPreferencia();
        return preferencia.getFtpUserName() == null ||
                preferencia.getUrlRemotaServidor().equals("http://") ||
                preferencia.getUrlRemotaServidor().equals("") ||
                preferencia.getUrlLocalServidor().equals("http://") ||
                preferencia.getUrlLocalServidor().equals("") ||
                preferencia.getIdVendedor() <= 0 ||
                preferencia.getIdTipoDocumentoPorDefecto().equals("");
    }

    private void promptUsuarioFTP() {
        Builder alert = new Builder(this);
        alert.setTitle(R.string.tituloConfigurarAplicacion);
        alert.setMessage(R.string.msjIngreseUsuarioFTP);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        alert.setView(input);
        alert.setPositiveButton(R.string.btnAceptar, (dialog, whichButton) -> promptUsuarioFTPOnClick(input));
        alert.setNegativeButton(R.string.btnCancelar, (dialog, whichButton) -> {
        });
        alert.show();
    }

    private void promptUsuarioFTPOnClick(EditText input) {
        String nombreUsuario = input.getText().toString();
        if (nombreUsuario.equals("")) {
            //showDialog(DIALOG_USUARIO_FTP_NO_INGRESADO);
            createDialog(DIALOG_USUARIO_FTP_NO_INGRESADO);
        } else {
            PreferenciaBo.getInstance().getPreferencia().setFtpUserName(nombreUsuario);
            TestConnection test = new TestConnection(TestConnection.FTP_CONNECTION, getApplicationContext(), nombreUsuario, null, new TestConnection.TestConnectionListener() {
                @Override
                public void onDone(boolean isConnected) {
                    if (isConnected) {
                        traerConfiguracionesConexion(ActualizacionConfiguracionesTask.LOGIN, getActualizarConfiguracionListener(getApplicationContext(), MODO_SOLICITAR_VENDEDOR));
                    } else {
                        //showDialog(DIALOG_USUARIO_FTP_INCORRECTO);
                        createDialog(DIALOG_USUARIO_FTP_INCORRECTO);
                    }
                }

                @Override
                public void onError(String error) {
                    //showDialog(DIALOG_USUARIO_FTP_INCORRECTO);
                    createDialog(DIALOG_USUARIO_FTP_INCORRECTO);
                }
            });
            test.execute();
        }
    }

    /**
     * <li>enable=<b>false: </b> desaparecen los botones aceptar y cancelar,
     * aparece el boton "desbloquear"</li>
     * <li>enable=<b>true: </b> el formulario de logueo tiene su formato
     * original, botones cancelar y aceptar</li>
     */
    private void checkLogin() {

        StateVendedorResultReceiver receiver = new StateVendedorResultReceiver(new Handler());
        Intent intent = new Intent(getApplicationContext(), StateVendedorService.class);
        intent.putExtra(StateVendedorService.EXTRA_ID_VENDEDOR,
                PreferenciaBo.getInstance().getPreferencia().getIdVendedor());
        intent.putExtra(StateVendedorService.EXTRA_RECEIVER, receiver);
        startService(intent);
        try {
            btnAceptarOnClick();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initVar() {
        _db = RepositoryFactory.getRepositoryFactory(getApplicationContext(), RepositoryFactory.ROOM);
        _vendedorBo = new VendedorBo(_db);
        _empresaBo = new EmpresaBo(_db);
        _diaLaboralBo = new DiaLaboralBo();
        //_ubicacionGeograficaBo = new UbicacionGeograficaBo();
        changeLog = new ChangeLog(this);
        changeLog.setWebView(findViewById(R.id.webView));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        // Desregistrar el receptor cuando la actividad se destruye
        LocalBroadcastManager.getInstance(this).unregisterReceiver(miReceiver);
        PreferenciaBo.getInstance().savePreference(getApplicationContext());
        super.onDestroy();
    }

    private void promptVendededor(final boolean isReConfigurandoMovil) {

        if (VendedorBo.isValidVendedor() || isReConfigurandoMovil) {
            Builder alert = new Builder(this);
            alert.setTitle(R.string.tituloSincronizar);
            alert.setMessage(R.string.msjIngreseCodigoVendedor);
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            alert.setView(input);
            alert.setPositiveButton(R.string.btnAceptar, (dialog, whichButton) -> promptVendedorOnClick(input, isReConfigurandoMovil));
            alert.setNegativeButton(R.string.btnCancelar, (dialog, whichButton) -> {
            });
            alert.show();
        } else {
            DlgBldrVendedorNoValido dialogo = new DlgBldrVendedorNoValido(this);
            dialogo.show();
        }

    }

    private void promptVendedorOnClick(EditText input, boolean isReconfigurandoMovil) {
        String nombreUsuario = input.getText().toString();
        if (nombreUsuario.equals("")) {
            //showDialog(DIALOG_CODIGO_VENDEDOR_NO_INGRESADO);
            getDialogCodigoVendedorNoIngresado().show();
        } else {
            if (isReconfigurandoMovil) {
                Preferencia preferencia = PreferenciaBo.getInstance().getPreferencia();
                preferencia.setIdVendedor(Integer.parseInt(input.getText().toString()));
                actualizarInfomacionVendedores(preferencia, preferencia.getIdVendedor());
                PreferenciaBo.getInstance().savePreference(getApplicationContext());
            }
            if (VendedorBo.validateVendedor(nombreUsuario)) {
                if (changeLog.firstRun() && !changeLog.firstRunEver()) {
                    //showDialog(DIALOG_ACTUALIZAR_CONFIGURACION);
                    createDialog(DIALOG_ACTUALIZAR_CONFIGURACION);
                    return;
                } else {
                    changeLog.updateVersionInPreferences();
                    setAutoOrientationEnabled(getApplicationContext(), false);
                    //progressDialogFragment = ProgressDialogFragment.newInstance(getString(R.string.msjSincronizando));
                    //progressDialogFragment.show(getSupportFragmentManager(), "myDialogFragment");
                    miViewModelLogin.setProgressDialogFragment(ProgressDialogFragment.newInstance(getString(R.string.msjSincronizando)));
                    miViewModelLogin.getProgressDialogFragment().show(getSupportFragmentManager(), "myDialogFragment");
                    SincronizacionBo sincronizacionBo = new SincronizacionBo(getApplicationContext(), _db);
                    sincronizacionBo.isUrlActiva(getApplicationContext(), getBuscarConexionListener());
                }
            } else {
                notificarError(getString(R.string.msjLoginDiferente));
            }
        }
    }

    /*@Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case PROGRESS_DIALOG:
                dialog = getProgressDialog();
                break;
            case DIALOG_DATOS_DESACTUALIZADOS:
                dialog = getDialogDatosDesactualizados();
                break;
            case DIALOG_CODIGO_VENDEDOR_NO_INGRESADO:
                dialog = getDialogCodigoVendedorNoIngresado();
                break;
            case DIALOG_ACTUALIZAR_CONFIGURACION:
                dialog = getDialogActualizarConfiguracion();
                break;
            case ERROR_DIALOG:
                dialog = getErrorDialog();
                break;
            case DIALOG_ERROR_APK:
                dialog = getErrorApkDialog();
                break;
            case DIALOG_USUARIO_FTP_NO_INGRESADO:
                dialog = getDialogUsuarioFTPNoIngresado();
                break;
            case DIALOG_USUARIO_FTP_INCORRECTO:
                dialog = getDialogUsuarioFTPIncorrecto();
                break;
            case DIALOG_FALLO_SINCRONIZACION:
                dialog = getDialogFalloSincronizacion();
                break;
        }
        return dialog;
    }*/

    protected void createDialog(int id) {
        ProgressDialogFragment progressDialog;
        SimpleDialogFragment simpleDialogFragment;
        String sTituloDialogo;
        String sMensajeDialogo;
        switch (id) {
            case PROGRESS_DIALOG:
                sTituloDialogo = this.getString(R.string.msjSincronizando);
                sMensajeDialogo = this.getString(R.string.msjAplicandoConfiguracionesDatos);
                progressDialog = ProgressDialogFragment.newInstance(sTituloDialogo, sMensajeDialogo);
                progressDialog.setCancelable(false);
                progressDialog.show(getSupportFragmentManager(), TAG_PROGRESS_DIALOG);
                break;
            case DIALOG_DATOS_DESACTUALIZADOS:
                sTituloDialogo = this.getString(R.string.tituloSincronizar);
                sMensajeDialogo = this.getString(R.string.msjDatosDesactualizados);
                simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK,
                        sMensajeDialogo, sTituloDialogo, (SimpleDialogFragment.OkListener) ()
                                -> showMenuPrincipal(NavigationMenu.HOME_SINCRONIZACION));
                simpleDialogFragment.show(getSupportFragmentManager(), TAG_DIALOG_DATOS_DESACTUALIZADOS);
                break;
            case DIALOG_CODIGO_VENDEDOR_NO_INGRESADO:
                sTituloDialogo = this.getString(R.string.tituloError);
                sMensajeDialogo = ErrorManager.CodigoVendedorNoIngresado;
                simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK,
                        sMensajeDialogo, sTituloDialogo);
                simpleDialogFragment.show(getSupportFragmentManager(), TAG_DIALOG_CODIGO_VENDEDOR_NO_INGRESADO);
                break;
            case DIALOG_ACTUALIZAR_CONFIGURACION:
                sTituloDialogo = this.getString(R.string.tituloSincronizar);
                sMensajeDialogo = this.getString(R.string.msjSincronizarUpdateConfiguraciones);
                simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK,
                        sMensajeDialogo, sTituloDialogo, (SimpleDialogFragment.OkListener) ()
                                -> traerConfiguracionesConexion(ActualizacionConfiguracionesTask.LOGIN,
                                getActualizarConfiguracionListener(getApplicationContext(), MODO_SOLO_ACTUALIZAR_CONF)));
                simpleDialogFragment.show(getSupportFragmentManager(), TAG_DIALOG_ACTUALIZAR_CONFIGURACION);
                break;
            case ERROR_DIALOG:
                sTituloDialogo = this.getString(R.string.tituloError);
                sMensajeDialogo = error;
                simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK,
                        sMensajeDialogo, sTituloDialogo);
                simpleDialogFragment.show(getSupportFragmentManager(), TAG_ERROR_DIALOG);
                break;
            case DIALOG_ERROR_APK:
                sTituloDialogo = this.getString(R.string.tituloError);
                sMensajeDialogo = this.getString(R.string.msjErrorAlActualizarVersion);
                simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK,
                        sMensajeDialogo, sTituloDialogo, (SimpleDialogFragment.OkListener) this::restaurarApk);
                simpleDialogFragment.show(getSupportFragmentManager(), TAG_DIALOG_ERROR_APK);
                break;
            case DIALOG_USUARIO_FTP_NO_INGRESADO:
                sTituloDialogo = this.getString(R.string.tituloError);
                sMensajeDialogo = ErrorManager.UsuarioFTPNoIngresado;
                simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK,
                        sMensajeDialogo, sTituloDialogo);
                simpleDialogFragment.show(getSupportFragmentManager(), TAG_DIALOG_USUARIO_FTP_NO_INGRESADO);
                break;
            case DIALOG_USUARIO_FTP_INCORRECTO:
                sTituloDialogo = this.getString(R.string.tituloError);
                sMensajeDialogo = ErrorManager.UsuarioFTPIncorrecto;
                simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK,
                        sMensajeDialogo, sTituloDialogo);
                simpleDialogFragment.show(getSupportFragmentManager(), TAG_DIALOG_USUARIO_FTP_INCORRECTO);
                break;
            case DIALOG_FALLO_SINCRONIZACION:
                sTituloDialogo = this.getString(R.string.tituloSincronizar);
                sMensajeDialogo = this.getString(R.string.msjErrorDescarga);
                simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK,
                        sMensajeDialogo, sTituloDialogo);
                simpleDialogFragment.show(getSupportFragmentManager(), TAG_DIALOG_FALLO_SINCRONIZACION);
                break;
        }
    }


    /*private Dialog getProgressDialog() {
        String titulo = this.getString(R.string.msjSincronizando);
        String mensaje = this.getString(R.string.msjAplicandoConfiguracionesDatos);
        ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle(titulo);
        progressDialog.setMessage(mensaje);
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    private Dialog getDialogDatosDesactualizados() {
        Builder builder = new Builder(this);
        builder.setTitle(R.string.tituloSincronizar);
        builder.setMessage(R.string.msjDatosDesactualizados);
        builder.setPositiveButton(R.string.btnAceptar, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                showMenuPrincipal(NavigationMenu.HOME_SINCRONIZACION);
            }
        });
        return builder.create();
    }

    private Dialog getDialogFalloSincronizacion() {
        Builder builder = new Builder(this);
        builder.setTitle(R.string.tituloSincronizar);
        builder.setMessage(R.string.msjErrorDescarga);
        builder.setPositiveButton(R.string.btnAceptar, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //no hacer nada
            }
        });
        return builder.create();
    }*/

    private Dialog getDialogCodigoVendedorNoIngresado() {
        Builder builder = new Builder(this);
        builder.setTitle(R.string.tituloError);
        builder.setMessage(ErrorManager.CodigoVendedorNoIngresado);
        builder.setPositiveButton(R.string.btnAceptar, (dialog, which) -> dialog.dismiss());
        return builder.create();
    }

    /*private Dialog getDialogUsuarioFTPNoIngresado() {
        Builder builder = new Builder(this);
        builder.setTitle(R.string.tituloError);
        builder.setMessage(ErrorManager.UsuarioFTPNoIngresado);
        builder.setPositiveButton(R.string.btnAceptar, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }

    private Dialog getDialogUsuarioFTPIncorrecto() {
        Builder builder = new Builder(this);
        builder.setTitle(R.string.tituloError);
        builder.setMessage(ErrorManager.UsuarioFTPIncorrecto);
        builder.setPositiveButton(R.string.btnAceptar, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }

    private Dialog getDialogActualizarConfiguracion() {
        Builder builder = new Builder(this);
        builder.setTitle(R.string.tituloSincronizar);
        builder.setMessage(R.string.msjSincronizarUpdateConfiguraciones);
        builder.setPositiveButton(R.string.btnAceptar, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                traerConfiguracionesConexion(ActualizacionConfiguracionesTask.LOGIN,
                        getActualizarConfiguracionListener(getApplicationContext(), MODO_SOLO_ACTUALIZAR_CONF));
            }
        });
        return builder.create();
    }

    private Dialog getErrorDialog() {
        Builder builder = new Builder(this);
        builder.setTitle(R.string.tituloError);
        builder.setMessage(error);
        builder.setPositiveButton(R.string.btnAceptar, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }

    private Dialog getErrorApkDialog() {
        Builder builder = new Builder(this);
        builder.setTitle(R.string.tituloError);
        builder.setMessage(getString(R.string.msjErrorAlActualizarVersion));
        builder.setPositiveButton(R.string.btnAceptar, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                restaurarApk();
            }
        });
        return builder.create();
    }*/

    private void getErrorFTPSincronizar() {
        String ftpUserName = PreferenciaBo.getInstance().getPreferencia(getApplicationContext()).getFtpUserName();
        if (this.error.equals(getString(R.string.msjErrorConfiguracionVersionesDiferentes))) {
            SimpleDialogFragment simpleDialogFragment = SimpleDialogFragment.newInstance(BaseDialogFragment.TYPE_OK, getString(R.string.tituloSincronizar), this.error);
            simpleDialogFragment.show(getSupportFragmentManager(), TAG);
        } else {
            InputDialogFragment inputDialogFragment = InputDialogFragment.newInstance(this, ftpUserName, SimpleDialogFragment.TYPE_OK_CANCEL, getString(R.string.tituloSincronizar), this.error, InputType.TYPE_CLASS_TEXT, new InputDialogFragment.OkCancelListener() {
                @Override
                public void onOkSelected(String textInput) {
                    updateConfiguracionUserFtp(textInput);
                }

                @Override
                public void onCancelSelected() {
                }
            });
            inputDialogFragment.show(getSupportFragmentManager(), TAG);
        }
    }

    private void updateConfiguracionUserFtp(String value) {
        PreferenciaBo.getInstance().getPreferencia(getApplicationContext()).setFtpUserName(value);
        Toast.makeText(getApplicationContext(), getString(R.string.msjConfiguracionesGuardadas), Toast.LENGTH_LONG).show();
    }

    private void traerConfiguracionesConexion(int modo, ActualizarConfiguracionListener listener) {
        setAutoOrientationEnabled(getApplicationContext(), false);
        //progressDialogFragment = ProgressDialogFragment.newInstance(getString(R.string.msjDescargarConfiguraciones));
        //progressDialogFragment.show(getSupportFragmentManager(), "myDialogFragment");
        miViewModelLogin.setProgressDialogFragment(ProgressDialogFragment.newInstance(getString(R.string.msjDescargarConfiguraciones)));
        miViewModelLogin.getProgressDialogFragment().show(getSupportFragmentManager(), "myDialogFragment");
        this.task = new ActualizacionConfiguracionesTask(getApplicationContext(), modo, listener);
        this.task.execute((Void) null);
    }

    public void markAsError(String errorMostrar) {
        this.error = errorMostrar;
        //progressDialogFragment.dismiss();
        if (miViewModelLogin.getProgressDialogFragment() != null &&
                miViewModelLogin.getProgressDialogFragment().isAdded()) {
            miViewModelLogin.getProgressDialogFragment().dismiss();
        }
        getErrorFTPSincronizar();
    }

    private void btnAceptarOnClick() throws Exception {
        if (!FileManager.isSdPresent()) {
            // No tiene montada la Tarjeta SD
            notificarError(getString(R.string.msjErrorAccesoSdCard));
        } else {
            // Recupero el usuario y contraseña ingresados
            String usuario = txtUsuario.getText().toString();
            String password = txtPassword.getText().toString();

            // Me da el tipo de Login que corresponde
            LoginEnum login = null;
            try {
                // Recupero el tipo de Login según el modo, usuario y contraseña
                login = _vendedorBo.recoveryByLogin(usuario, password, getApplicationContext());
                if (login == LoginEnum.ADMINISTRADOR) {
                    // Si Modo = 0 o Modo = 1 entonces abro Configuración
                    // Avanzada
                    // Si Modo = 2 o Modo = 3 devuelvo un mensaje de error
                    if ((miViewModelLogin.getModo() == MODO_ADMINISTRACION_LOGIN) || (miViewModelLogin.getModo() == MODO_CONFIGURACION)) {
                        abrirFrmConfiguracionesAvanzadas();
                    }
                } else if (login == LoginEnum.INCORRECTO) {
                    notificarError(getString(R.string.msjLoginInvalido));
                    limpiarTxt();
                } else if (login == LoginEnum.CORRECTO) {
                    // Solamente si modo = 1 puede entrar como Vendedor
                    if (miViewModelLogin.getModo() == MODO_ADMINISTRACION_LOGIN) {
                        login();
                    } else {
                        notificarError(getString(R.string.msjErrorVendedorLogin));
                    }
                } else {
                    notificarError(ErrorManager.LoginDiferente);
                    limpiarTxt();
                }
            } catch (SQLiteException e) {
                notificarErrorAccesoBDLogin();
            } catch (Exception e) {
                ErrorManager.manageException(TAG, "btnAceptarOnClick", e, this);
                notificarErrorAccesoBDLogin();
            }
        }
    }

    private void login() throws Exception {
        if (!_empresaBo.gpsValido(getApplicationContext())) {
            notificarGpsApagado();
        } else {
            inicioServicioGeolocalizacion();

            // Verifico si es necesario actualizar configuraciones y sincronizar
            if (changeLog.firstRun() && !changeLog.firstRunEver()) {
                //showDialog(DIALOG_ACTUALIZAR_CONFIGURACION);
                createDialog(DIALOG_ACTUALIZAR_CONFIGURACION);
            } else {
                changeLog.updateVersionInPreferences();
                if (PreferenciaBo.getInstance().getPreferencia(getApplicationContext()).isSugerenciaSincronizarInicio()) {
                    Date hoy = Calendar.getInstance().getTime();
                    Date ultimaSincronizacion = PreferenciaBo.getInstance().getPreferencia(getApplicationContext()).getFechaUltimaDescarga();
                    Long diferenciaEnHoras = ((hoy.getTime() - ultimaSincronizacion.getTime()) / 60000) / 60;
                    if (diferenciaEnHoras >= 24) {

                        SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK, getString(R.string.msjDatosDesactualizados), getString(R.string.tituloSincronizar), new SimpleDialogFragment.OkListener() {
                            @Override
                            public void onOkSelected() {
                                showMenuPrincipal(NavigationMenu.HOME_SINCRONIZACION);
                            }
                        }).show(getSupportFragmentManager(), "");

                    } else {
                        showMenuPrincipal(NavigationMenu.HOME_CLIENTE);
                    }
                } else {
                    showMenuPrincipal(NavigationMenu.HOME_CLIENTE);
                }
            }
        }
    }

    private void abrirFrmConfiguracionesAvanzadas() {
        Intent intent = new Intent(getApplicationContext(), FrmConfiguracionesAvanzadasTab.class);
        intent.putExtra(EXTRA_UBICACION, miViewModelLogin.getUbicacion());
        startActivity(intent);
        txtUsuario.setText("");
        txtPassword.setText("");
        txtUsuario.requestFocus();
        finish();
    }

    private void notificarErrorAccesoBDLogin() {
        try {
            restaurarBaseDatos();
        } catch (Exception e2) {
            setAutoOrientationEnabled(getApplicationContext(), false);
            //progressDialogFragment = ProgressDialogFragment.newInstance(getString(R.string.msjSincronizando));
            //progressDialogFragment.show(getSupportFragmentManager(), "myDialogFragment");
            miViewModelLogin.setProgressDialogFragment(ProgressDialogFragment.newInstance(getString(R.string.msjSincronizando)));
            miViewModelLogin.getProgressDialogFragment().show(getSupportFragmentManager(), "myDialogFragment");
            SincronizacionBo sincronizacionBo = new SincronizacionBo(getApplicationContext(), _db);
            sincronizacionBo.isUrlActiva(getApplicationContext(), getBuscarConexionListener());
        }
    }

    private void notificarError(String errorDelManager) {
        AlertDialog alert = new AlertDialog(this, getString(R.string.tituloError), errorDelManager);
        alert.show();
    }

    private void restaurarBaseDatos() throws Exception {
        _db.restoreDb(); // Restaura
        // Tarea #3198
        _empresaBo.updateConfiguracionesPreferencia(getApplicationContext());
        reiniciarServicioGps();
        btnAceptarOnClick();
        //notificarRestauracionBackup();
    }

    private void reiniciarServicioGps() throws Exception {
        // Reiniciar el servicio
        stopService(new Intent(getApplicationContext(), GPSService.class));
        // Consulto en tabla Empresas por si se aplica la funcionalidad y valido
        // que este dentro del rango horario de trabajo
        String si = "S";
        if (PreferenciaBo.getInstance().getPreferencia().getIsRegistrarLocalizacion().equals(si)
                && ComparatorDateTime.validarRangoHorarioEnvioLocalizacion()) {
            startService(new Intent(getApplicationContext(), GPSService.class));
        }
    }

    private void notificarRestauracionBackup() {
        AlertDialog alert = new AlertDialog(this, getString(R.string.info), getString(R.string.msjBack));
        alert.show();
    }

    private void notificarGpsApagado() {
        AlertDialog alert = new AlertDialog(this, R.string.tituloErrorGps, R.string.msjErrorGPSApagado);
        alert.setPositiveButton(R.string.btnAceptar, (dialog, whichButton) -> finish());
        alert.show();
    }

    private void inicioServicioGeolocalizacion() throws Exception {
        // Evaluo aplicabilidad de Geolocalización de vendedores
        boolean aplicoGeolocalizacion = false;
        // Tarea #3198
        String si = "S";
        if (PreferenciaBo.getInstance().getPreferencia().getIsRegistrarLocalizacion().equals(si)) {
            aplicoGeolocalizacion = true;
        }
        // Llamo a DiaLaboralBo para ver si el dia actual esta dentro de los laborables
        boolean isLaboral = _diaLaboralBo.validateDiaLaboral();
        if (aplicoGeolocalizacion && isLaboral) {
            new TaskGPS().start();
        }
    }

    private void showMenuPrincipal(int extraHome) {
        new Task(/*this*/).start();
        Intent intent = new Intent(getApplicationContext(), NavigationMenu.class);
        intent.putExtra(NavigationMenu.EXTRA_HOME, extraHome);
        PreferenciaBo.getInstance().getPreferencia(getApplicationContext()).setContrasena(txtPassword.getText().toString());
        startActivity(intent);
        finish();
    }

    private void limpiarTxt() {
        txtPassword.setText("");
        txtPassword.requestFocus();
    }

    /**
     * Servicio que se encarga de las actualizaciones automaticas y otras yerbas
     *
     * @author VGM Sistemas
     */
    public class Task extends Thread {
        //public FrmLogin frmLogin;

        public Task(/*FrmLogin frmLogin*/) {
            this.setPriority(Process.THREAD_PRIORITY_BACKGROUND);
            //this.frmLogin = frmLogin;
        }

        @Override
        public void run() {
            // Arranco el servicio que se encarga de las actualizaciones de version
            // automaticas y otras yerbas
            Intent intent = new Intent(getApplicationContext(), TaskManager.class);
            intent.putExtra(FrmLogin.EXTRA_MODO, MODO_ADMINISTRACION_LOGIN);// Paso
            // un modo por defecto
            startService(intent);
        }
    }

    /**
     * Servicio para la Localización Geográfica por GPS
     *
     * @author VGM Sistemas
     */
    public class TaskGPS extends Thread {

        public TaskGPS() {
            this.setPriority(Process.THREAD_PRIORITY_BACKGROUND);
        }

        @Override
        public void run() {
            Intent intent = new Intent(getApplicationContext(), GPSService.class);
            startService(intent);
        }
    }

    private void restaurarApk() {
        int result = Settings.Secure.getInt(getContentResolver(), Settings.Secure.INSTALL_NON_MARKET_APPS, 0);
        if (result == 0) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_SETTINGS);
            startActivity(intent);
        } else {
            try {
                String file = Preferencia.getPathResguardoVersion().concat(Preferencia._preventaApk);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(file)), "application/vnd.android.package-archive");
                startActivity(intent);
            } catch (Exception e) {
                markAsError(getString(R.string.msjErrorInstalarVersion));
            }
        }
    }

    private HostnameVerifier getHostnameVerifier() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return hostname.equals("ec2-18-216-165-15.us-east-2.compute.amazonaws.com");
            }
        };
    }

    private SSLSocketFactory getSSLSocketFactory()
            throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream caInput = getResources().openRawResource(R.raw.aws); // this cert file stored in \app\src\main\res\raw folder path

        Certificate ca = cf.generateCertificate(caInput);
        caInput.close();

        KeyStore keyStore = KeyStore.getInstance("BKS");
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        TrustManager[] wrappedTrustManagers = getWrappedTrustManagers(tmf.getTrustManagers());

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, wrappedTrustManagers, null);

        return sslContext.getSocketFactory();
    }

    private TrustManager[] getWrappedTrustManagers(TrustManager[] trustManagers) {
        final X509TrustManager originalTrustManager = (X509TrustManager) trustManagers[0];
        return new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return originalTrustManager.getAcceptedIssuers();
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        try {
                            if (certs != null && certs.length > 0) {
                                certs[0].checkValidity();
                            } else {
                                originalTrustManager.checkClientTrusted(certs, authType);
                            }
                        } catch (CertificateException e) {
                            Log.w("checkClientTrusted", e.toString());
                        }
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        try {
                            if (certs != null && certs.length > 0) {
                                certs[0].checkValidity();
                            } else {
                                originalTrustManager.checkServerTrusted(certs, authType);
                            }
                        } catch (CertificateException e) {
                            Log.w("checkServerTrusted", e.toString());
                        }
                    }
                }
        };
    }

    public void peticionSsl() {
        HurlStack hurlStack = new HurlStack() {
            @Override
            protected HttpURLConnection createConnection(URL url) throws IOException {
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) super.createConnection(url);
                try {
                    httpsURLConnection.setSSLSocketFactory(getSSLSocketFactory());
                    httpsURLConnection.setHostnameVerifier(getHostnameVerifier());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return httpsURLConnection;
            }
        };

        String url = "https://ec2-18-216-165-15.us-east-2.compute.amazonaws.com/";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
        };
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext(), hurlStack);
        requestQueue.add(jsObjRequest);
    }

    public static void setAutoOrientationEnabled(Context context, boolean enabled) {
        Settings.System.putInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, enabled ? 1 : 0);
    }

    private void actualizarInfomacionVendedores(Preferencia preferencia, long idVendedor) {
        if (_versionInfoVendsList != null) {
            for (VersionInfoVendedor versionInfoVend : _versionInfoVendsList.getListaVendedoresInfo()) {
                if (versionInfoVend.getVendedorID() == idVendedor) {
                    //valores por defecto en la parte de ventas
                    preferencia.setIdTipoDocumentoPorDefecto(versionInfoVend.getDocDefectoVenta());
                    preferencia.setIdPuntoVentaPorDefecto(versionInfoVend.getPtoVtaDefectoVenta());
                    //valores por defecto en la parte de recibos
                    preferencia.setIdTipoDocumentoRecibo(versionInfoVend.getDocDefectoRecibo());
                    preferencia.setIdPuntoVentaPorDefectoRecibo(versionInfoVend.getPtoVtaDefectoRecibo());
                    //valores por defecto en repartidor
                    preferencia.setIdRepartidorPorDefecto(versionInfoVend.getRepartidorDefecto());
                }
            }
        }
        //Se debe aplicar o impactar los cambios  en la llamada
    }

    /**/
    private BroadcastReceiver miReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Procesar el mensaje recibido
            String mensaje = intent.getStringExtra("mensaje");
            Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show();
        }
    };
}