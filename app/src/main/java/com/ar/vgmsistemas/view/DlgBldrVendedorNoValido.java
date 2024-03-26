package com.ar.vgmsistemas.view;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.utils.ErrorManager;

public class DlgBldrVendedorNoValido extends Builder{

	Context context;
	public DlgBldrVendedorNoValido (Context context){
		super(context);
		setTitle(R.string.error);
		setMessage(ErrorManager.ErrorVendedorNoValido);
		setPositiveButton("Aceptar", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
			}
		});
		
	}
	public DlgBldrVendedorNoValido (Context context, OnClickListener listener){
		super(context);
		setTitle(R.string.error);
		setMessage(ErrorManager.ErrorVendedorNoValido);
		setPositiveButton("Aceptar", listener);
		
	}
}
