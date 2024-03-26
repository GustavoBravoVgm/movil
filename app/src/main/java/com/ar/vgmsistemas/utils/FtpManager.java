package com.ar.vgmsistemas.utils;

import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.bo.VendedorBo;
import com.ar.vgmsistemas.entity.Preferencia;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Calendar;

public class FtpManager {

    private String server = PreferenciaBo.getInstance().getPreferencia().getUrlFtp();
    private String userName = PreferenciaBo.getInstance().getPreferencia().getFtpUserName();
    private String password = PreferenciaBo.getInstance().getPreferencia().getFtpPassword();

    public boolean testConnection(String server, String userName, String password) {
        boolean result = false;
        try {
            FTPClient ftp = new FTPClient();
            ftp.connect(server);
            result = ftp.login(userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean testConnection(){
        return this.testConnection(server,userName,password);
    }


    // logs
    public void sendLogs() {
        FTPClient ftp = new FTPClient();
        String pathLocal = Preferencia.getPathSistema() + "/preventa.log";
        //String pathFtp = PreferenciaBo.getInstance().getPreferencia().getFtpFolder();
        String pathFtp = PreferenciaBo.getInstance().getPreferencia().getFtpLogFolder();

        File file = new File(pathLocal);
        if (file.exists()) {
            try {
                ftp.connect(server);
                if (ftp.login(userName, password)) {
                    ftp.enterLocalPassiveMode(); // important!
                    FileInputStream in = new FileInputStream(pathLocal);
                    String targetFileName = "/preventa" + VendedorBo.getVendedor().getId() + "-" + Calendar.getInstance().getTimeInMillis() + ".log";
                    ftp.storeFile(pathFtp + targetFileName, in);
                    in.close();

                    //Borro el log del movil
                    File f = new File(pathLocal);
                    f.delete();

                }
                ftp.logout();
                ftp.disconnect();
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    // "PreventaMovil.apk"
    public InputStream downloadBinaryFile(String fileName) throws Exception {
        FTPClient ftp = new FTPClient();
        ftp.connect(server);
        InputStream is = null;
        if (ftp.login(userName, password)) {
            ftp.enterLocalPassiveMode();
            //ftp.changeWorkingDirectory(PreferenciaBo.getInstance().getPreferencia().getFtpFolder());
            ftp.changeWorkingDirectory(PreferenciaBo.getInstance().getPreferencia().getFtpApkFolder());
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            is = ftp.retrieveFileStream(fileName);
        } else {
            throw new Exception("Error in access to ftp: invalid username or password ");
        }
        return is;
    }

    // "versionInfo.xml"
    public InputStream downloadAsciiFile(String fileName) throws Exception {
        FTPClient ftp = new FTPClient();
        ftp.connect(server);
        InputStream is = null;
        if (ftp.login(userName, password)) {
            ftp.enterLocalPassiveMode();
            ftp.changeWorkingDirectory(PreferenciaBo.getInstance().getPreferencia().getFtpFolder());
            is = ftp.retrieveFileStream(fileName);
        }
        return is;
    }

    public boolean validateRemoteFile(String remoteFileName) throws Exception {
        boolean fileFlag = false;
        FTPClient ftp = new FTPClient();
        ftp.connect(server);
        InputStream is = null;
        if (ftp.login(userName, password)) {
            ftp.enterLocalPassiveMode();
            ftp.changeWorkingDirectory(PreferenciaBo.getInstance().getPreferencia().getFtpFolder());
            is = ftp.retrieveFileStream(remoteFileName);
        }

        if (is != null) {
            fileFlag = true;
        }

        return fileFlag;

    }

}
