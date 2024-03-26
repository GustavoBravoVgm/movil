package com.ar.vgmsistemas.task;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.utils.FtpManager;
import com.ar.vgmsistemas.ws.ConnectionMannager;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestConnection extends AsyncTask<Void, Void, Void> {
    private TestConnectionListener listener;
    private int typeConnection;
    private static final int MESSAGE_OK =10;
    private static final int MESSAGE_NO_OK =11;

    public static final int INTERNET_CONNECTION = 1;
    public static final int FTP_CONNECTION = 2;
    public static final int SERVER_CONNECTION = 3;
    private String mUser;
    private Context context;
    private String mUrl;
    private int resultado;

    public TestConnection(int typeConnection, Context context, String mUser, String mUrl, TestConnectionListener listener){
        this.listener = listener;
        this.typeConnection = typeConnection;
        this.context = context;
        this.mUser = mUser;
        this.mUrl = mUrl;
    }

    @Override
    protected Void doInBackground(Void... objects) {
        if (isNetworkEnabled(context)||isWifiEnabled(context)) {
            switch (typeConnection) {
                case INTERNET_CONNECTION:
                    resultado = testInternetConnection();
                    break;

                case FTP_CONNECTION:
                    resultado = testConexionFTP(context, mUser);
                    break;
                case SERVER_CONNECTION:
                    resultado = testServerConnection(context, mUrl);
                    break;
            }
        }else{
            resultado = MESSAGE_NO_OK ;
        }
        return null;
    }

    public static boolean isNetworkEnabled(Context context) {
        try{
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            return activeNetInfo.isConnected();
        }catch (NullPointerException n){
            return false;
        }
    }

    public static boolean isWifiEnabled(Context context){
        try {
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return mWifi.isConnected();
        }catch (NullPointerException n){
            return false;
        }
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if(listener != null)
            if(resultado == MESSAGE_OK){
                listener.onDone(true);
            } else if(resultado == MESSAGE_NO_OK){
                listener.onDone(false);
            }else{
                listener.onError("Error");
            }
        context = null;
    }


    public static int testInternetConnection(){
        try {
            HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
            urlc.setRequestProperty("User-Agent", "Test");
            urlc.setRequestProperty("Connection", "close");
            urlc.setConnectTimeout(5000);
            urlc.connect();
            if (urlc.getResponseCode()==200){
                return MESSAGE_OK;
            }else{
                return MESSAGE_NO_OK;
            }


        } catch (IOException e) {
            Log.i("warning", "Error checking internet connection", e);
            return MESSAGE_NO_OK;
        }
    }

    public interface TestConnectionListener{
        void onDone(boolean isConnected);
        void onError(String error);
    }

    private int testConexionFTP(Context context, String userName) {
        FtpManager ftpManager = new FtpManager();
        String url = PreferenciaBo.getInstance().getPreferencia(context.getApplicationContext()).getUrlFtp();
        String password = PreferenciaBo.getInstance().getPreferencia(context.getApplicationContext()).getFtpPassword();
        return (ftpManager.testConnection(url, userName, password) )? MESSAGE_OK : MESSAGE_NO_OK;
        //ftpManager.sendLogs();
    }

    private static int testServerConnection(Context context, String url) {
        ConnectionMannager cm = new ConnectionMannager(context);
        boolean isConnected = cm.checkConnection(url);

        return isConnected ? MESSAGE_OK:MESSAGE_NO_OK;
    }
}
