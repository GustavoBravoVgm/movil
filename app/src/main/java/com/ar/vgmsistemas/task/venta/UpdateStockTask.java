package com.ar.vgmsistemas.task.venta;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.core.app.NotificationCompat;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.ArticuloBo;
import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.entity.key.PkDocumento;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.view.venta.FrmAgregarLinea;
import com.ar.vgmsistemas.view.venta.FrmAgregarLineaHacienda;
import com.ar.vgmsistemas.ws.ArticuloWs;

public class UpdateStockTask extends AsyncTask<Void, Void, Void> {
    private Activity activity = null;
    private Articulo art;
    private long mIdVendedor;
    private double stock = -1d;
    private Context mContext;
    private PkDocumento pkDocumento;


    public UpdateStockTask(Activity activity, Articulo articulo, long idVendedor, Context context, PkDocumento pkDocumento) {
        attach(activity);
        art = articulo;
        mIdVendedor = idVendedor;
        mContext = context;
        this.pkDocumento = pkDocumento;
    }


    @Override
    protected Void doInBackground(Void... unused) {
        ArticuloWs articuloWs = new ArticuloWs(mContext);
        try {
            stock = articuloWs.recoveryStock(art.getId(), mIdVendedor, pkDocumento);
            RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(activity.getApplicationContext(), RepositoryFactory.ROOM);
            ArticuloBo articuloBo = new ArticuloBo(repoFactory);
            articuloBo.actualizarStock(art, stock);
        } catch (Exception e) {
            stock = art.getStock();
            notificarErrorStock();
        }
        return (null);
    }

    private void notificarErrorStock() {
        String ns = Context.NOTIFICATION_SERVICE;

        //int icon = R.drawable.app_icon;
        CharSequence tickerText = "Error al descargar stock";
        long when = System.currentTimeMillis();

		/*Notification notification = new Notification(icon, tickerText, when);
		notification.flags = Notification.FLAG_AUTO_CANCEL | Notification.FLAG_ONLY_ALERT_ONCE;*/

        CharSequence contentTitle = "Error el descargar el stock";
        CharSequence contentText = "Se utilizara el stock almacenado en el dispositivo.";


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(activity);
        notificationBuilder.setContentTitle(contentTitle);
        notificationBuilder.setContentText(contentText);
        notificationBuilder.setSmallIcon(R.drawable.app_icon_newtransp);
        notificationBuilder.setTicker(tickerText);
        notificationBuilder.setWhen(when);
        notificationBuilder.setContentIntent(
                PendingIntent.getActivity(
                        mContext.getApplicationContext(), 0,
                        new Intent(), PendingIntent.FLAG_IMMUTABLE));

        Notification notification = notificationBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager mNotificationManager = (NotificationManager) activity.getSystemService(ns);
        mNotificationManager.notify(Preferencia.NOTIFICATION_ERROR_DESCARGA_STOCK, notification);
    }

    @Override
    protected void onProgressUpdate(Void... unused) {
    }

    @Override
    protected void onPostExecute(Void unused) {
        if (activity.getClass() == FrmAgregarLinea.class) {
            ((FrmAgregarLinea) activity).setStock(stock);
        } else if (activity.getClass() == FrmAgregarLineaHacienda.class) {
            ((FrmAgregarLineaHacienda) activity).setStock(stock);
        }
        detach();

    }

    void detach() {
        activity = null;
        mContext = null;
    }

    void attach(Activity activity) {
        this.activity = activity;
    }


}	