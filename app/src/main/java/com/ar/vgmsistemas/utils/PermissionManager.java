package com.ar.vgmsistemas.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionManager {
    private static final int REQUEST_BLUETOOTH_ADMIN_PERMISSION = 1;
    private static final int REQUEST_INTERNET_PERMISSION = 2;
    private static final int REQUEST_ACCESS_FINE_LOCATION_PERMISSION = 3;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 4;
    private static final int REQUEST_ACCESS_WIFI_STATE = 5;
    private static final int REQUEST_ACCESS_NETWORK_STATE = 6;
    private static final int REQUEST_WRITE_SETTINGS = 7;
    private static final int REQUEST_ACCESS_COARSE_LOCATION = 8;
    private static final int REQUEST_BLUETOOTH = 9;
    private static final int REQUEST_READ_PHONE_STATE = 10;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 11;
    private static final int REQUEST_CALL_PHONE = 12;

    public static boolean checkPermission(Activity activity, String[] permission) {
        boolean permisosHabilitados = true;
        for (String permiso : permission){
            permisosHabilitados = permisosHabilitados && ContextCompat.checkSelfPermission(activity,
                    permiso) == PackageManager.PERMISSION_GRANTED;
        }
        return permisosHabilitados;
    }

    public static void requestPermission(Activity activity, String permiso) {
        String[] permission = {permiso};
        if (!checkPermission(activity, permission)) {
            int requestCode = getPermissionRequestCode(permiso);
            ActivityCompat.requestPermissions(activity, new String[]{permiso}, requestCode);
        }
    }

    public static boolean handlePermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            return true; // Permiso concedido
        }
        return false; // Permiso denegado
    }

    private static int getPermissionRequestCode(String permission) {
        // Asocia cada tipo de permiso con un código de solicitud único
        switch (permission) {
            case Manifest.permission.BLUETOOTH_ADMIN:
                return REQUEST_BLUETOOTH_ADMIN_PERMISSION;
            case Manifest.permission.INTERNET:
                return REQUEST_INTERNET_PERMISSION;
            case Manifest.permission.ACCESS_FINE_LOCATION:
                return REQUEST_ACCESS_FINE_LOCATION_PERMISSION;
            // Agrega otros permisos según sea necesario
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                return REQUEST_WRITE_EXTERNAL_STORAGE;
            // Agrega otros permisos según sea necesario
            case Manifest.permission.ACCESS_WIFI_STATE:
                return REQUEST_ACCESS_WIFI_STATE;
            case Manifest.permission.ACCESS_NETWORK_STATE:
                return REQUEST_ACCESS_NETWORK_STATE;
            case Manifest.permission.WRITE_SETTINGS:
                return REQUEST_WRITE_SETTINGS;
            case Manifest.permission.ACCESS_COARSE_LOCATION:
                return REQUEST_ACCESS_COARSE_LOCATION;
            case Manifest.permission.BLUETOOTH:
                return REQUEST_BLUETOOTH;
            case Manifest.permission.READ_PHONE_STATE:
                return REQUEST_READ_PHONE_STATE;
            case Manifest.permission.READ_EXTERNAL_STORAGE:
                return REQUEST_READ_EXTERNAL_STORAGE;
            case Manifest.permission.CALL_PHONE:
                return REQUEST_CALL_PHONE;
            default:
                return -1; // Código no válido
        }
    }
}
