package com.ar.vgmsistemas.ws;

import android.content.Context;

import com.android.volley.Request;
import com.ar.vgmsistemas.entity.AuditoriaGps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;


public class AuditoriaGpsWs extends GenericWs {
    public AuditoriaGpsWs(Context mContext) {
        webService = "/AuditoriaGpsWs";
        context = mContext;
    }

    public int send(AuditoriaGps auditoriaGps) throws Exception {
        String peticion = "/createAuditoriaGps/";
        //String paramName = "auditoriaGpsJson";

        metodo = Request.Method.POST;
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSZ").create();
        JsonObject jsonCliente = gson.toJsonTree(auditoriaGps).getAsJsonObject();
        Map<String, String> parametros = new HashMap<>();
        parametros.put("auditoriaGpsJson", jsonCliente.toString());
        String response = this.callWebService(url, webService + peticion, metodo, parametros,/*jsonCliente, paramName,*/ VOLUMEN_DE_DATOS_BAJO);
        return Integer.valueOf(response);
    }
}
