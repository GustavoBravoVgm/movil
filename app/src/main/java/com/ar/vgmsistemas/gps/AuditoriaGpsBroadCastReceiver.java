package com.ar.vgmsistemas.gps;

import static com.ar.vgmsistemas.entity.AuditoriaGps.TI_ACCION_APAGA;
import static com.ar.vgmsistemas.entity.AuditoriaGps.TI_ACCION_ENCIENDE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

import com.ar.vgmsistemas.bo.AuditoriaGpsBo;
import com.ar.vgmsistemas.bo.DiaLaboralBo;
import com.ar.vgmsistemas.bo.MovimientoBo;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.entity.AuditoriaGps;
import com.ar.vgmsistemas.entity.Movimiento;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.Calendar;


public class AuditoriaGpsBroadCastReceiver extends BroadcastReceiver {
    public static final String GPS_ENABLED_CHANGE_ACTION = "android.location.PROVIDERS_CHANGED";

    @Override
    public void onReceive(final Context context, Intent intent) {
        //Log.d("---------log--------","GPS Status onReceive");
        DiaLaboralBo diaLaboralBo = new DiaLaboralBo();
        boolean isLaboral = false;
        try {
            isLaboral = diaLaboralBo.validateDiaLaboral();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (intent.getAction().equals(GPS_ENABLED_CHANGE_ACTION) && isLaboral) {
            //Log.d("---------log--------","GPS Status Changed");
            final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            final AuditoriaGps auditoriaGps = new AuditoriaGps();
            if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                auditoriaGps.setTiAccion(TI_ACCION_ENCIENDE);
            } else {
                auditoriaGps.setTiAccion(TI_ACCION_APAGA);
            }
            auditoriaGps.setIdVendedor(PreferenciaBo.getInstance().getPreferencia(context).getIdVendedor());
            Calendar cal = Calendar.getInstance();
            auditoriaGps.setFeRegistroMovil(cal.getTime());
            int hours = cal.get(Calendar.HOUR_OF_DAY);
            int minutes = cal.get(Calendar.MINUTE);
            int seconds = cal.get(Calendar.SECOND);
            String idMovil = auditoriaGps.getIdVendedor() + "-" + hours + ":" + minutes + ":" + seconds;
            auditoriaGps.setIdMovil(idMovil);

            RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(context, RepositoryFactory.ROOM);
            final AuditoriaGpsBo auditoriaGpsBo = new AuditoriaGpsBo(repoFactory);
            try {
                Movimiento movimiento = new Movimiento();
                movimiento.setTabla(AuditoriaGps.TABLE);
                movimiento.setIdMovil(idMovil);
                movimiento.setTipo(Movimiento.ALTA);
                MovimientoBo movimientoBo = new MovimientoBo(repoFactory);

                if (!auditoriaGpsBo.existsAuditoria(auditoriaGps)) {
                    try {
                        auditoriaGpsBo.create(auditoriaGps);
                        movimientoBo.create(movimiento);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Thread t = new Thread() {
                        public void run() {
                            try {
                                auditoriaGpsBo.enviarAuditoria(auditoriaGps, context);
                            } catch (Exception e) {
                                //No se trata la exception porque el servicio va a intentar despues
                            }
                        }
                    };
                    t.start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}