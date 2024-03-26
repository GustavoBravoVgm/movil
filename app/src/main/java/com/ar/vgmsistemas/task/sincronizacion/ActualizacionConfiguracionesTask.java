package com.ar.vgmsistemas.task.sincronizacion;

import android.content.Context;
import android.os.AsyncTask;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.bo.SincronizacionBo;
import com.ar.vgmsistemas.changelog.ChangeLog;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.entity.Version;
import com.ar.vgmsistemas.entity.VersionInfoVendedoresList;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.UpdateManager;

public class ActualizacionConfiguracionesTask extends AsyncTask<Void, Void, Void> {

    // Vistas
    private Context context;

    private boolean done = false;

    private boolean restaurarApk = false;
    private String error;

    // Modos
    private int modoActualizacion;
    public static final int CONFIGURACION_AVANZADA = 0;
    public static final int LOGIN = 1;

    // Version
    private Version versionServidor;
    private UpdateManager updateManager;
    private VersionInfoVendedoresList _listadoVendedoresInfo;

    private ActualizarConfiguracionListener mListener;


    // DAO
    RepositoryFactory _repoFactory;

    public ActualizacionConfiguracionesTask(Context context, int modo, ActualizarConfiguracionListener actualizarConfiguracionListener) {
        this.context = context;
        this.mListener = actualizarConfiguracionListener;
        this.modoActualizacion = modo;
        initVar();
    }


    private void initVar() {
        updateManager = new UpdateManager(this.context);
        _listadoVendedoresInfo = null;//inicializo en null
        this._repoFactory = RepositoryFactory.getRepositoryFactory(context, RepositoryFactory.ROOM);
    }

    @Override
    protected Void doInBackground(Void... unused) {
        done = false;
        try {
            done = update();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorManager.manageException("ActualizacionConfiguracionesTask", "doInBackground", e);
        }
        return (null);
    }

    @Override
    protected void onProgressUpdate(Void... unused) {
    }

    @Override
    protected void onPostExecute(Void unused) {
        if (mListener != null)
            if (done) {
                mListener.onDone(_listadoVendedoresInfo);
            } else {
                mListener.onError(error, restaurarApk);
            }

        context = null;
    }

    public void attach(Context mContext) {
        //this.activityLogin = activityLogin;
        this.context = mContext;
    }

    private boolean update() throws Exception {
        try {
            boolean updateOk;//siempre se inicializa false
            updateOk = actualizarConfiguracion();
            return updateOk;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean actualizarConfiguracion() {
        boolean actualizacionOk;//siempre se inicializa false
        try {
            versionServidor = updateManager.getLastVersion();
            //de paso traigo los datos referidos a cada vendedor en caso que existiese el archivo
            _listadoVendedoresInfo = updateManager.getInfoDeVendedores();
            //controlo que si es null la version insert la version que tiene el apk
            if (versionServidor == null) {
                versionServidor = new Version();
                versionServidor.setNumeroVersion(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode);
            }
            if (enviarDatosPendientes()) {

                //Valido que la version del movil sea igual a la del servidor
                Integer versionInstalada = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;

                if (versionInstalada.equals(versionServidor.getNumeroVersion())) {
                    actualizacionOk = actualizarPreferencias();
                } else {
                    // Versiones diferentes
                    if (modoActualizacion == LOGIN) {
                        actualizacionOk = true;
                        // TODO Ir a menu principal

                    } else {
                        actualizacionOk = false;
                        // TODO Notificar versiones diferentes e ir a frmConfiguraciones
                        error = context.getString(R.string.msjErrorConfiguracionVersionesDiferentes);
                    }
                }

            } else {
                //No pude enviar los datos al servidor
                error = context.getString(R.string.msjErrorEnviarDatos);
                actualizacionOk = false;
            }


        } catch (Exception e) {
            if (modoActualizacion == LOGIN) {
                actualizacionOk = false;
                restaurarApk = true;
            } else {
                actualizacionOk = false;
                error = context.getString(R.string.msjErrorConexionFTP);
            }
        }
        return actualizacionOk;
    }

    private boolean actualizarPreferencias() {
        boolean preferenciasOk = false;
        ChangeLog changeLog = new ChangeLog(this.context);

        if (versionServidor.isActualizarConfiguracion()) {

            if (versionServidor.getUrlRemotaServidor() == null ||
                    versionServidor.getUrlRemotaServidor2() == null ||
                    versionServidor.getUrlLocalServidor() == null) {
                //Controlo que el archivo versionInfo.xml tenga configurados los tags de las Url
                error = context.getString(R.string.msjErrorDireccionesNoConfiguradas);

            } else {

                try {
                    // Actualizo preferencias
                    Preferencia preferencia = new Preferencia();

                    preferencia.setUrlRemotaServidor(versionServidor.getUrlRemotaServidor());

                    preferencia.setUrlRemotaServidor2(versionServidor.getUrlRemotaServidor2());

                    preferencia.setUrlLocalServidor(versionServidor.getUrlLocalServidor());

                    preferencia.setFtpUserName(versionServidor.getFtpUserName());

                    //Verifico que la preferencia no este configurada y que el FTP tenga los datos
                    if ((PreferenciaBo.getInstance().getPreferencia().getIdTipoDocumentoPorDefecto() == null ||
                            PreferenciaBo.getInstance().getPreferencia().getIdTipoDocumentoPorDefecto().equals("")) &&
                            (versionServidor.getDocumentoPorDefecto()) != null &&
                            !versionServidor.getDocumentoPorDefecto().equals("")) {
                        preferencia.setIdTipoDocumentoPorDefecto(versionServidor.getDocumentoPorDefecto());
                        preferencia.setIdPuntoVentaPorDefecto(versionServidor.getPuntoDeVentaPorDefecto());
                        preferencia.setIdRepartidorPorDefecto(versionServidor.getRepartidor());
                        preferencia.setTipoDatoIdArticulo(versionServidor.getTipoDatoIdArticulos());
                    }

                    PreferenciaBo.getInstance().saveConfiguracionesConexionPreferencia(context, preferencia);

                    // Sincronizo
                    //sincronizacionBo.descargarDatos();
                    // Actualizo preferences de changeLog
                    changeLog.updateVersionInPreferences();
                    preferenciasOk = true;

                } catch (Exception e) {
                    e.printStackTrace();
                    restaurarVersion();
                    preferenciasOk = false;
                    error = context.getString(R.string.msjErrorDescargarDatosPorActConfig);
                }


            }

        } else {
            if (modoActualizacion == LOGIN) {
                //Ir a menu principal
                preferenciasOk = true;
            } else {
                //Notificar no esta habilitada la actualizacion de configuracion
                preferenciasOk = false;
                error = context.getString(R.string.msjErrorConfiguracionActualizar);
            }
        }
        return preferenciasOk;

    }

    private void restaurarVersion() {
        try {
            updateManager.restaurarPreferencia();
            updateManager.restaurarBaseDatos();
            if (modoActualizacion == LOGIN) {
                restaurarApk = true;
            }

        } catch (Exception e) {
            error = context.getString(R.string.msjErrorAlRestaurarVersion);
            //Error grave, no se va a permitir el ingreso del vendedor para trabajar
            PreferenciaBo.getInstance().getPreferencia(context).setIdVendedor(-1);

        }
    }

    private boolean enviarDatosPendientes() {
        boolean datosEnviados;//siempre inicializa en false
        if (versionServidor.isForzarSincronizacion()) {
            datosEnviados = enviarDatos();
        } else {
            datosEnviados = true;
        }
        return datosEnviados;
    }

    private boolean enviarDatos() {
        RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(context, RepositoryFactory.ROOM);
        SincronizacionBo sincronizacionBo = new SincronizacionBo(this.context, repoFactory);
        boolean envioOk = false;
        try {
            if (this._repoFactory.dataBaseExists()) {
                if (sincronizacionBo.isDatosPendientesEnvio()) {
                    envioOk = sincronizacionBo.enviarDatos();
                } else {
                    envioOk = true;
                }
            } else {
                envioOk = true;
            }
        } catch (Exception e) {
            envioOk = false;
        }
        return envioOk;
    }

    public interface ActualizarConfiguracionListener {
        void onDone(VersionInfoVendedoresList listadoVendedoresInfo);

        void onError(String error, boolean restaurarApk);
    }

}
