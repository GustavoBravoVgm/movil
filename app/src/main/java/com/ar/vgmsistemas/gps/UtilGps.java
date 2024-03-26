package com.ar.vgmsistemas.gps;

import android.content.Context;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings.Secure;

public final class UtilGps {

    public static boolean validateGpsStatus(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static String getIMEI(Context context) {
        String IMEI;
        IMEI = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        IMEI += Build.MANUFACTURER + "-" + Build.MODEL;
        IMEI = IMEI.length() > 40 ? IMEI.substring(0, 39) : IMEI;
        return IMEI;
    }

}
