package com.ar.vgmsistemas.ws;

import android.content.Context;

import com.android.volley.Request;
import com.ar.vgmsistemas.entity.Compra;
import com.ar.vgmsistemas.entity.EntregaRendicion;
import com.ar.vgmsistemas.utils.CodeResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class RendicionWs extends GenericWs {

    public RendicionWs(Context mContext) {
        webService = "/RendicionWs";
        context = mContext;
    }

    public int enviarEgreso(Compra compra) throws Exception {
        String peticion = "/createEgreso/";
        metodo = Request.Method.POST;

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSZ").create();
        JsonObject jsonCompra = gson.toJsonTree(compra).getAsJsonObject();

        Map<String, String> parametros = new HashMap<>();
        parametros.put("compraJson", jsonCompra.toString());
        String response = callWebService(url, webService + peticion, metodo, parametros,/*jsonParameter, paramName,*/ VOLUMEN_DE_DATOS_MEDIO);
        CodeResult.checkCode(Integer.valueOf(response));
        return Integer.valueOf(response);
    }

    public int enviarEntrega(EntregaRendicion entrega) throws Exception {
        String peticion = "/createEntrega/";
        metodo = Request.Method.POST;

        Gson gson = new Gson();
        JsonObject jsonEntrega = gson.toJsonTree(entrega).getAsJsonObject();

        Map<String, String> parametros = new HashMap<>();
        parametros.put("entregaJson", jsonEntrega.toString());
        String response = callWebService(url, webService + peticion, metodo, parametros,/*jsonParameter, paramName,*/ VOLUMEN_DE_DATOS_MEDIO);
        CodeResult.checkCode(Integer.valueOf(response));
        return Integer.valueOf(response);
    }
}
