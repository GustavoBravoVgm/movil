package com.ar.vgmsistemas.view;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.ar.vgmsistemas.R;

public class AlertDialog extends Builder {


    public AlertDialog(Context context, String title, String message) {
        super(context);
        this.setTitle(title);
        this.setMessage(message);
        this.setIcon(R.drawable.alert);
        this.setPositiveButton(context.getString(R.string.btnAceptar), new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    public AlertDialog(Context context, int title, int message) {
        super(context);
        this.setTitle(title);
        this.setMessage(message);
        this.setIcon(R.drawable.alert);
        this.setPositiveButton(R.string.btnAceptar, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }


}
