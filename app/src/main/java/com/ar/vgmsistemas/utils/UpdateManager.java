package com.ar.vgmsistemas.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.entity.Version;
import com.ar.vgmsistemas.entity.VersionInfoVendedoresList;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.repository.filesystem.PreferenciaRepositoryImpl;
import com.ar.vgmsistemas.ws.ConnectionMannager;
import com.google.gson.Gson;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.Arrays;

public class UpdateManager {

    private Context context;
    private PreferenciaRepositoryImpl preferenciaRepository;
    private PreferenciaBo preferenciaBo;


    //DAO
    RepositoryFactory _repoFactory;

    //DaoFactory _daoFactory = DaoFactory.getDaoFactory(DaoFactory.SQLITE);

    public UpdateManager(Context context) {
        this.context = context;
        this.preferenciaRepository = new PreferenciaRepositoryImpl();
        this._repoFactory = RepositoryFactory.getRepositoryFactory(context, RepositoryFactory.ROOM);
    }

    public boolean checkForUpdates() {
        boolean update = false;
        if (FileManager.isSdPresent()) {
            String[] dispHabilit = null;
            try {
                int versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
                Version version = getLastVersion();
                if(version != null){
                    if (version.getDispositivosHabilitadosParaActualizar() != null)
                        dispHabilit = version.getDispositivosHabilitadosParaActualizar().split(",");

                    if ((dispHabilit == null || Arrays.asList(dispHabilit).contains(PreferenciaBo.getInstance().getPreferencia().getIdVendedor() + "")) && versionCode < version.getNumeroVersion()) {
                        PreferenciaBo.getInstance().getPreferencia().setFechaLimiteActualizacion(version.getFechaLimiteActualizacion());
                        PreferenciaBo.getInstance().getPreferencia().setVersion(version.getNumeroVersion());
                        update = true;
                    } else {
                        //no tiene que actualizar, seteo version 0 para que no salte ni un cartel al intentar descargar.
                        PreferenciaBo.getInstance().getPreferencia().setVersion(0);
                    }
                }
            } catch (UnknownHostException ex) {

            } catch (Exception e) {
                ErrorManager.manageException("UpdateManager", "checkForUpdate", e);
            }
        }
        return update;
    }

    /**
     * Devuelve true si el movil tiene instalado la version disponible en el servidor
     *
     * @return
     */
    public boolean isMovilActualizado() {
        boolean movilActualizado = false;
        Integer lastVersion;
        try {
            int versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            lastVersion = getLastVersion().getNumeroVersion();
            movilActualizado = (lastVersion == versionCode);
        } catch (Exception e) {
        }
        return movilActualizado;
    }

    public Version getLastVersion() throws Exception {
        if (!isValidConfigFtp()) return null;
        FtpManager ftpManager = new FtpManager();
        if (ftpManager.testConnection()) {
            InputStream is = ftpManager.downloadAsciiFile(Preferencia._fileVersionInfo);
            Serializer serializer = new Persister();
            Version version = serializer.read(Version.class, is);
            return version;
        } else return null;
    }

    /*Para obtener la info x por vendedor*/
    public VersionInfoVendedoresList getInfoDeVendedores() throws Exception {
        if (!isValidConfigFtp()) return null;
        FtpManager ftpManager = new FtpManager();
        if (ftpManager.testConnection()) {
            //Creating an InputStream object
            InputStream is = ftpManager.downloadAsciiFile(Preferencia._fileVersionInfoVendedores);
            //creating an InputStreamReader object
            InputStreamReader isReader = new InputStreamReader(is);
            //Creating a BufferedReader object
            BufferedReader reader = new BufferedReader(isReader);
            StringBuffer sb = new StringBuffer();
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }

            Gson gson = new Gson();
            VersionInfoVendedoresList vrsInfoVendList = gson.fromJson(sb.toString(), VersionInfoVendedoresList.class);
            return vrsInfoVendList;
        } else return null;
    }

    private boolean isValidConfigFtp() {
        String server = PreferenciaBo.getInstance().getPreferencia().getUrlFtp();
        String userName = PreferenciaBo.getInstance().getPreferencia().getFtpUserName();
        String password = PreferenciaBo.getInstance().getPreferencia().getFtpPassword();
        return userName != null && server != null && password != null &&
                !userName.equalsIgnoreCase("") && !server.equalsIgnoreCase("") &&
                !password.equalsIgnoreCase("");
    }


    public void update() throws Exception {
        if (!FileManager.isSdPresent()) {
            throw new Exception(ErrorManager.ErrorAccesoSdCard);
        } else {
            ConnectionMannager cm = new ConnectionMannager(context);
            if (!cm.isConnected()) {
                throw new Exception(ErrorManager.ErrorConexionDatos);
            } else {
                String file = Preferencia.getPathSistema().concat(Preferencia._preventaApk);
                FtpManager ftpManager = new FtpManager();
                InputStream is = ftpManager.downloadBinaryFile(Preferencia._preventaApk);
                FileManager fileManager = new FileManager();
                fileManager.saveFile(is, file);
            }
        }
    }


    /**
     * Realiza una copia de seguridad de el archivo de base de datos, instalador movil y configuraciones
     *
     * @throws Exception
     */
    public void resguardarVersion() throws Exception {
        // Hago backUp de sqlite, preferencias y apk
        resguardarPreferencia();
        resguardarBaseDatos();
        resguardarAPK();
    }

    public void resguardarAPK() throws Exception {
        final PackageManager pm = context.getPackageManager();
        ApplicationInfo info = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        String sourceDir = info.sourceDir;
        FileManager fileManager = new FileManager();
        fileManager.copyFiles(sourceDir, Preferencia.getPathResguardoVersion().concat(Preferencia._preventaApk));
    }

    public void resguardarBaseDatos() throws Exception {

        String pathDb = Preferencia.getPathDB();
        File file = new File(pathDb);
        if (file.exists()) {
            FileManager fileManager = new FileManager();
            fileManager.copyFiles(Preferencia.getPathDB(), Preferencia.getPathResguardoVersion().concat(Preferencia._preventaSqlite));
        }

    }

    public void resguardarPreferencia() throws Exception {
        FileManager fileManager = new FileManager();
        fileManager.copyFiles(Preferencia.getPathSistema().concat(Preferencia._filePreferencias), Preferencia.getPathResguardoVersion().concat(Preferencia._filePreferencias));
    }

    public void restaurarBaseDatos() throws Exception {
        String pathResguardo = Preferencia.getPathResguardoVersion().concat(Preferencia._preventaSqlite);
        File file = new File(pathResguardo);
        if (file.exists()) {
            //Backup
            this._repoFactory.backupDb();
            //Cierro la conexión a la bd
            this._repoFactory.closeConnection();
            FileManager fileManager = new FileManager();
            fileManager.copyFiles(pathResguardo, Preferencia.getPathDB());
            //Abro de nuevo la conexión
            this._repoFactory.open();
        }

    }

    public void restaurarPreferencia() throws Exception {
        PreferenciaRepositoryImpl preferenciaRepositoryImpl = new PreferenciaRepositoryImpl();
        Preferencia preferencia = preferenciaRepositoryImpl.loadPreferencias(Preferencia.getPathResguardoVersion());
        PreferenciaBo.getInstance().saveConfiguracionesConexionPreferencia(context, preferencia);

    }

}
