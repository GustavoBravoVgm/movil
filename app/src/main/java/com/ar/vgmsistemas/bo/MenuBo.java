package com.ar.vgmsistemas.bo;

import static com.ar.vgmsistemas.view.FrmLogin.EXTRA_IS_FROM_OTHER_APP;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.Menu;
import android.view.MenuInflater;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.view.FrmLogin;

import java.util.List;

public class MenuBo {

    public static boolean inflateBaseMenuOptions(Context context, Menu menu, MenuInflater inflater) {
        boolean isMultiEmpresa = PreferenciaBo.getInstance().getPreferencia(context).isMultiEmpresa();
        if (isMultiEmpresa) {
            inflater.inflate(R.menu.mn_base, menu);

        }
        return true;
    }

    public static void changeToOtherApp(Context context) {
        Intent i;
        PackageManager manager = context.getPackageManager();
        try {
            i = manager.getLaunchIntentForPackage(context.getResources().getString(R.string.otraSucursal));
            if (i == null)
                throw new PackageManager.NameNotFoundException();
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            i.putExtra(FrmLogin.EXTRA_IS_RUNNING, checkIfIsRunnig(context));
            i.putExtra(EXTRA_IS_FROM_OTHER_APP, EXTRA_IS_FROM_OTHER_APP);
            context.startActivity(i);
        } catch (Exception e) {

        }
    }

    private static boolean checkIfIsRunnig(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        // get the info from the currently running task
        List<ActivityManager.RunningAppProcessInfo> appsRunning = am.getRunningAppProcesses();
        if (appsRunning.size() > 1)
            for (ActivityManager.RunningAppProcessInfo info : appsRunning) {
                if (info.processName.equals(context.getResources().getString(R.string.otraSucursal))) {
                    return true;
                }
            }
        else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(50);
            for (ActivityManager.RunningTaskInfo tInfo : taskInfo) {
                if (tInfo.topActivity.getPackageName().equals(context.getResources().getString(R.string.otraSucursal))) {
                    return true;
                }
            }
        }
        return false;
    }
}
