package com.ar.vgmsistemas.utils;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

public class PermissionUtils {
    public static boolean checkPermission(Context context, String permission) {
        boolean status = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
        return status;
    }
}
