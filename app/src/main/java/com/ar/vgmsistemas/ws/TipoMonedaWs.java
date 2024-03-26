package com.ar.vgmsistemas.ws;

import android.content.Context;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.Map;

public class TipoMonedaWs extends GenericWs{

	public TipoMonedaWs(Context mContext){
		webService = "/TipoMonedaWs";
		context = mContext;
	}
	
	public float recoveryCotizacion(int idTipoMoneda)throws Exception{
        String peticion = "/getCotizacion/";
        //String paramName = "idTipoMonedaJson";
		metodo = Request.Method.POST;
        /*JsonObject jsonParameter = new JsonObject();
        jsonParameter.addProperty("idTipoMoneda", String.valueOf(idTipoMoneda));*/
        Map<String, String> parametros = new HashMap<>();
        parametros.put("idTipoMoneda", String.valueOf(idTipoMoneda));
        float cotizacion = Float.valueOf(
                this.callWebService(url, webService+peticion, metodo, parametros,/*jsonParameter, paramName,*/ VOLUMEN_DE_DATOS_BAJO));
		return cotizacion;
	}
}
