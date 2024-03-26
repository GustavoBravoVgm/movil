package com.ar.vgmsistemas.ws;

import android.content.Context;

import com.android.volley.Request;
import com.ar.vgmsistemas.entity.key.PkDocumento;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class ArticuloWs extends GenericWs {

    public ArticuloWs(Context mContext) {
        webService = "/ArticuloWs";
        context = mContext;
    }

    public float recoveryStock(long idArticulo, long idVendedor, PkDocumento pkDocumento) throws Exception {
        String peticion = "/getStock/";
        metodo = Request.Method.POST;
        Gson gson = new Gson();
        JsonObject pkJson = gson.toJsonTree(pkDocumento).getAsJsonObject();
        Map<String, String> parametros = new HashMap<>();
        parametros.put("idArticulo", String.valueOf(idArticulo));
        parametros.put("idVendedor", String.valueOf(idVendedor));
        parametros.put("pkDocumentoString", pkJson.toString());
        float stock = Float.valueOf(this.callWebService(url, webService + peticion, metodo, parametros,/* jsonParameter, paramName, */VOLUMEN_DE_DATOS_BAJO));
        return stock;
    }
}
