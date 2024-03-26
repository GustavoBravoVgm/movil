package com.ar.vgmsistemas.ws;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.entity.Preferencia;

import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionMannager extends GenericWs {
    private Context context;

    public ConnectionMannager(Context context) {
        this.context = context;
    }

    public boolean isWifiConnected() {
        return this.isNetworkConnectedOrConnecting(ConnectivityManager.TYPE_WIFI);
    }

    public boolean isDataMobileConnected() {
        return this.isNetworkConnectedOrConnecting(ConnectivityManager.TYPE_MOBILE);
    }

    public boolean isConnected() {
        return (isDataMobileConnected() || isWifiConnected());
    }

    public boolean isConexionActiva() {
        boolean existsConexion = false;
        String url = PreferenciaBo.getInstance().getPreferencia(context).getUrlRemotaServidor();
        if (checkConnection(PreferenciaBo.getInstance().getPreferencia(context).getUrlLocalServidor())) {
            url = PreferenciaBo.getInstance().getPreferencia(context).getUrlLocalServidor();
            PreferenciaBo.getInstance().getPreferencia(context).setSincronizacionLocal(true);
            existsConexion = true;
        } else if (checkConnection(PreferenciaBo.getInstance().getPreferencia(context).getUrlRemotaServidor())) {
            PreferenciaBo.getInstance().getPreferencia(context).setSincronizacionLocal(false);
            existsConexion = true;
        } else if (checkConnection(PreferenciaBo.getInstance().getPreferencia(context).getUrlRemotaServidor2())) {
            url = PreferenciaBo.getInstance().getPreferencia(context).getUrlRemotaServidor2();
            PreferenciaBo.getInstance().getPreferencia(context).setUrlServidorActiva(url);
            PreferenciaBo.getInstance().getPreferencia(context).setSincronizacionLocal(false);
            existsConexion = true;
        }
        PreferenciaBo.getInstance().getPreferencia(context).setUrlServidorActiva(url);
        return existsConexion;
    }

    public boolean checkConnection(String url) {
        boolean result = false;
        try {
            if (isConnected()) {
                result = ping(url);
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return result;
    }


    private boolean isNetworkConnectedOrConnecting(int networkType) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            return activeNetwork.isConnectedOrConnecting();
        } else {
            return false;
        }
    }

    private boolean ping(String urlDestino) {
        boolean conectado = false;
        if (!urlDestino.equalsIgnoreCase("") &&
                !urlDestino.equalsIgnoreCase(Preferencia.STRING_HTTP)) {
            try {
                URL url = new URL(urlDestino);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(5000);// Tiempo de espera de conexión en milisegundos
                httpURLConnection.setReadTimeout(5000); // Tiempo de espera de lectura en milisegundos ***
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode() == 200)
                    conectado = true;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return conectado;
    }

}
