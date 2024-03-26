package com.ar.vgmsistemas.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.MovimientoBo;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.bo.SincronizacionBo;
import com.ar.vgmsistemas.bo.UbicacionGeograficaBo;
import com.ar.vgmsistemas.bo.VentaBo;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.exclusionmutua.Monitor;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.view.menu.NavigationMenu;
import com.ar.vgmsistemas.ws.ConnectionMannager;

import java.util.Timer;
import java.util.TimerTask;

public class TaskManager extends Service {
    private Timer timer = new Timer();
    private BroadcastReceiver myWifiReceiver;

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startService();
    }

    @Override
    public void onDestroy() {
        // Desregistrar el receptor cuando la actividad se destruye
        this.unregisterReceiver(myWifiReceiver);
        super.onDestroy();
        shutdownService();
    }

    private void startService() {
        try {
            long delayIntervalSincronizacion = PreferenciaBo.getInstance().getPreferencia(getApplicationContext()).getIntervaloSincronizacionAutomatica() * 1000;
            long delayIntervalEnvioLogs = PreferenciaBo.getInstance().getPreferencia(getApplicationContext()).getIntervaloEnvioLogs() * 1000;

            //Registro un broadcastReceiver para recibir notificaciones de cuando cambia el estado del WiFi
            //si el wifi esta conectado y el ping al servidor en la red local funciona, se trabaja con el servidor local
            this.myWifiReceiver = new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {
                    NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
                    if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI && networkInfo.isConnected()) {
                        ConnectionMannager cm = new ConnectionMannager(context);
                        cm.isConexionActiva();
                    }
                }
            };

            this.registerReceiver(this.myWifiReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    enviarDatos();
                }
            }, 2000, delayIntervalSincronizacion);//2000 = 2 segundos de retardo

            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    FtpManager ftpManager = new FtpManager();
                    ftpManager.sendLogs();
                }
            }, 20000, delayIntervalEnvioLogs); //20000 = 20 segundos de retardo

            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    UpdateManager updateManager = new UpdateManager(getApplicationContext());
                    boolean actualizar = updateManager.checkForUpdates();
                    if (actualizar)
                        try {
                            notificarActualizacion();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                }
            }, 10000, 9999999); //10000 = 10 segundos de retardo
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void notificarActualizacion() {
        String ns = Context.NOTIFICATION_SERVICE;
        CharSequence tickerText = "Nueva version disponible";
        long when = System.currentTimeMillis();

//		Notification notification = new Notification(icon, tickerText, when);

        CharSequence contentTitle = "Actualizacion";
        CharSequence contentText = "Se encuentra disponible una nueva version.";

        Intent notificationIntent = new Intent(this, NavigationMenu.class);
        notificationIntent.putExtra(NavigationMenu.EXTRA_HOME, NavigationMenu.HOME_ACTUALIZACION);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getBaseContext());
        notificationBuilder.setSmallIcon(R.drawable.app_icon_newtransp);
        notificationBuilder.setTicker(tickerText);
        notificationBuilder.setContentTitle(contentTitle);
        notificationBuilder.setContentText(contentText);
        notificationBuilder.setWhen(when);

        notificationBuilder.setContentIntent(contentIntent);
        notificationBuilder.setAutoCancel(true);

        Notification notification = notificationBuilder.build();
        notification.defaults |= Notification.DEFAULT_SOUND;

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
        mNotificationManager.notify(Preferencia.NOTIFICATION_ACTUALIZACION, notification);
    }

    private void enviarDatos() {

        if (Monitor.isRelease() && FileManager.isSdPresent()) {
            RepositoryFactory repo = RepositoryFactory.getRepositoryFactory(getApplicationContext(), RepositoryFactory.ROOM);
            MovimientoBo movimientoBo = new MovimientoBo(repo);
            UbicacionGeograficaBo ubicacionGeograficaBo = new UbicacionGeograficaBo(repo);
            VentaBo ventaBo = new VentaBo(repo);

            int cantidadVentasSinEnviar = 0;
            int cantidadPedidosSinEnviar = 0;
            int cantidadRecibosSinEnviar = 0;
            int cantidadEgresosSinEnviar = 0;
            int cantidadEntregasSinEnviar = 0;
            //int cantidadErroresSinEnviar = 0;
            int cantidadUbicacionesSinEnviar = 0;

            try {
                cantidadVentasSinEnviar = /*ventaBo.getCantidadVentasNoEnviadas();*/ movimientoBo.getCantidadPedidosSinEnviar();
                cantidadPedidosSinEnviar = movimientoBo.getCantidadNoPedidosSinEnviar();
                cantidadRecibosSinEnviar = movimientoBo.getCantidadRecibosSinEnviar();
                cantidadEgresosSinEnviar = movimientoBo.getCantidadEgresosSinEnviar();
                cantidadEntregasSinEnviar = movimientoBo.getCantidadEntregasSinEnviar();
                //cantidadErroresSinEnviar = movimientoBo.getCantidadErroresSinEnviar();

                cantidadUbicacionesSinEnviar = movimientoBo.getCantidadUbicacionesGeograficasSinEnviar();

            } catch (Exception e) {
                e.printStackTrace();
            }

            boolean datosSinEnviar = (cantidadPedidosSinEnviar + cantidadRecibosSinEnviar +
                    cantidadVentasSinEnviar + cantidadEgresosSinEnviar + cantidadEntregasSinEnviar +
                    /*cantidadErroresSinEnviar + */cantidadUbicacionesSinEnviar) > 0;

            if (datosSinEnviar) {

                SincronizacionBo sincronizacionBo = new SincronizacionBo(getApplicationContext(), repo);

                try {

                    if (sincronizacionBo.isConexionDatosDisponible()) {

                        try {
                            if (PreferenciaBo.getInstance().getPreferencia(getApplicationContext()).isEnvioPedidoAutomatico()) {
                                sincronizacionBo.enviarPedidosPendientes();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            if (PreferenciaBo.getInstance().getPreferencia(getApplicationContext()).isEnvioReciboAutomatico()) {
                                sincronizacionBo.enviarRecibosPendientes();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            if (PreferenciaBo.getInstance().getPreferencia(getApplicationContext()).isEnvioHojaDetalleAutomatico()) {
                                sincronizacionBo.enviarHojasDetallePendientes();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            if (PreferenciaBo.getInstance().getPreferencia(getApplicationContext()).isEnvioEgresoAutomatico()) {
                                sincronizacionBo.enviarEgresosPendientes();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            sincronizacionBo.enviarNoPedidosPendientes();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            sincronizacionBo.enviarClientes();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            sincronizacionBo.enviarUbicacionesGeograficasPendientes();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }

    }

    /*
     * shutting down the service
     */
    private void shutdownService() {
        if (timer != null) {
            timer.cancel();
        }
    }
}