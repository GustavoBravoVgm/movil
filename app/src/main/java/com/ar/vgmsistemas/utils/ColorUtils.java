package com.ar.vgmsistemas.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import androidx.appcompat.app.ActionBar;

import com.ar.vgmsistemas.R;

public class ColorUtils {

	public enum TipoCambio{
		CAMBIO_COLOR("CC"), CAMBIO_FUENTE("CF");
		private String tiCambio; //el tipo de cambio que aplica

		//Añadir un constructor
		TipoCambio(String s){tiCambio = s; }
		//Añadir un método
		String getTiCambio(){return tiCambio;}
	}

	public static int getTextBaseColor(Context context){
		int color;
		color = context.getResources().getColor(R.color.base_text_color);
		return color;
	}

	public static void cambioColor(Window myWindow, ActionBar myActionBar, TipoCambio tipoCambio, int color){

		//enum para controlar sentencia switch
		switch(tipoCambio){
			case CAMBIO_COLOR:
				myActionBar.setBackgroundDrawable( new ColorDrawable( color));
				break;
			case CAMBIO_FUENTE:
				break;
		}
	}
}
