package com.ar.vgmsistemas.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ar.vgmsistemas.bo.PreferenciaBo;

public class UsbReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//		if (intent.getAction().equalsIgnoreCase( "android.intent.action.UMS_CONNECTED")){
        PreferenciaBo.getInstance().loadPreference(context);
//			Intent intentLogin = new Intent(context, FrmLogin.class);
//			intentLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			context.startActivity(intentLogin);
//        }
    }
}
