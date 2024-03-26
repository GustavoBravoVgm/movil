package com.ar.vgmsistemas.ws;

import android.content.Context;

import com.android.volley.Request;
import com.ar.vgmsistemas.entity.ErrorMovil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class ErrorWs extends GenericWs {

    public ErrorWs(Context mContext) {
        webService = "/ErrorWs";
        context = mContext;
    }

    public int send(ErrorMovil error) throws Exception {
        String peticion = "/registrarError/";
        metodo = Request.Method.POST;
        Gson gson = new Gson();
        JsonObject jsonError = gson.toJsonTree(error).getAsJsonObject();
        Map<String, String> parametros = new HashMap<>();
        parametros.put("errorString", jsonError.toString());
        String response = this.callWebService(url, webService + peticion, metodo, parametros, VOLUMEN_DE_DATOS_BAJO);
        return Integer.valueOf(response);
    }
}
