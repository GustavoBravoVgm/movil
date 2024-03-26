package com.ar.vgmsistemas.viewmodel;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.task.sincronizacion.ActualizacionConfiguracionesTask;
import com.ar.vgmsistemas.view.dialogs.ProgressDialogFragment;

public class FrmLoginViewModel extends ViewModel {
    private boolean isRunning;
    private String isFromOtherApp = null;
    private boolean isRotationEnabled;
    private int modo;
    private int ubicacion;
    private ActualizacionConfiguracionesTask task;
    private ProgressDialogFragment progressDialogFragment;


    public FrmLoginViewModel() {
        isRunning = false;
        isFromOtherApp = null;
        isRotationEnabled = false;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public String getIsFromOtherApp() {
        return isFromOtherApp;
    }

    public void setIsFromOtherApp(String isFromOtherApp) {
        this.isFromOtherApp = isFromOtherApp;
    }

    public boolean isRotationEnabled() {
        return isRotationEnabled;
    }

    public void setRotationEnabled(boolean rotationEnabled) {
        isRotationEnabled = rotationEnabled;
    }

    public int getModo() {
        return modo;
    }

    public void setModo(int modo) {
        this.modo = modo;
    }

    public int getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(int ubicacion) {
        this.ubicacion = ubicacion;
    }

    public ActualizacionConfiguracionesTask getTask() {
        return task;
    }

    public void setTask(ActualizacionConfiguracionesTask task) {
        this.task = task;
    }

    public ProgressDialogFragment getProgressDialogFragment() {
        return progressDialogFragment;
    }

    public void setProgressDialogFragment(ProgressDialogFragment progressDialogFragment) {
        this.progressDialogFragment = progressDialogFragment;
    }

    public void getToastActualizacionExitosa(Context context){
        Toast.makeText(context, context.getString(R.string.msjActualizacionExitosa), Toast.LENGTH_SHORT).show();
    }
}
