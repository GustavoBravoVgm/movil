package com.ar.vgmsistemas.gps;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;

import com.ar.vgmsistemas.bo.DiaLaboralBo;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.utils.ComparatorDateTime;
import com.ar.vgmsistemas.utils.ErrorManager;

import java.util.Timer;
import java.util.TimerTask;

public class GPSService extends Service{
	
	private Timer timer = new Timer();
	private final IBinder mBinder = new MyBinder();
	GPSManagement gps;
	DiaLaboralBo diaLaboralBo = new DiaLaboralBo();
    AuditoriaGpsBroadCastReceiver auditoriaGpsBroadCastReceiver = new AuditoriaGpsBroadCastReceiver();
	
	public void onCreate(){
		super.onCreate();		
		//Inicializo y obtengo la localización por primera vez
		gps = new GPSManagement(getApplicationContext(), null, null);
		gps.getLocation(true);

		IntentFilter filter = new IntentFilter("AuditoriaGpsBroadCastReceiver");
		this.registerReceiver(auditoriaGpsBroadCastReceiver, filter);

		//Independientemente de que pueda o no obtener la ubicación, llamo a pollForUpdates
		envioPeriodicoDeUbicacionesGeograficas();
	}
	
	private void envioPeriodicoDeUbicacionesGeograficas() {
		timer.scheduleAtFixedRate(new TimerTask(){
			@Override
			public void run() {				
				//Valido el rango horario para ver si obtengo la localización y que sea laborable
				boolean isLaborable = false;
				try {
					isLaborable = diaLaboralBo.validateDiaLaboral();
				} catch (Exception e) {
					ErrorManager.manageException("GPSService", "Dia laborable", e);
				}				
				if(ComparatorDateTime.validarRangoHorarioEnvioLocalizacion() && isLaborable){
					gps.getLocation(true);//Captura y guarda la posición geográfica
				}
			}
		}
		, 0, PreferenciaBo.getInstance().getPreferencia(this).getIntervaloTiempoLocalizacion());
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		if(timer != null){
			timer.cancel();
		}
        IntentFilter filter = new IntentFilter("AuditoriaGpsBroadCastReceiver");
        this.unregisterReceiver(auditoriaGpsBroadCastReceiver);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	public class MyBinder extends Binder{
		GPSService getService(){
			return GPSService.this;
		}
	}	
}
