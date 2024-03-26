package com.ar.vgmsistemas.ws;

import android.content.Context;

import com.android.volley.Request;
import com.ar.vgmsistemas.entity.UbicacionGeografica;
import com.ar.vgmsistemas.utils.CodeResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class UbicacionGeograficaWs extends GenericWs {

    public UbicacionGeograficaWs(Context mContext) {
        webService = "/UbicacionWs";
        context = mContext;
    }

    public int send(UbicacionGeografica ubicacionGeografica) throws Exception {
        String peticion = "/enviarCoordenadas/";
        metodo = Request.Method.POST;

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSZ").create();
        JsonObject jsonUbicacionGeografica = gson.toJsonTree(ubicacionGeografica).getAsJsonObject();
        Map<String, String> parametros = new HashMap<>();
        parametros.put("ubicacionGeograficaJson", jsonUbicacionGeografica.toString());
        String response = callWebService(url, webService + peticion, metodo, parametros,/*jsonUbicacionGeografica, paramName,*/ VOLUMEN_DE_DATOS_MEDIO);
        String verdadero = "true";
        if (response.equals(verdadero)) {
            return CodeResult.RESULT_OK;
        } else {
            return CodeResult.RESULT_ERROR;
        }
    }
}
