package com.ar.vgmsistemas.ws;

import android.content.Context;

import com.android.volley.Request;
import com.ar.vgmsistemas.entity.NoPedido;
import com.ar.vgmsistemas.utils.CodeResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class NoPedidoWs extends GenericWs {

    public NoPedidoWs(Context mContext) {
        webService = "/NoPedidoWs";
        context = mContext;
    }

    public int send(NoPedido noPedido) throws Exception {
        String peticion = "/enviarNoPedido/";
        //String paramName = "noPedidoJson";
        metodo = Request.Method.POST;
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSZ").create();
        JsonObject jsonNoPedido = gson.toJsonTree(noPedido).getAsJsonObject();
        Map<String, String> parametros = new HashMap<>();
        parametros.put("noPedidoJson", jsonNoPedido.toString());
        String response = this.callWebService(url, webService + peticion, metodo, parametros,/*jsonNoPedido, paramName, */VOLUMEN_DE_DATOS_BAJO);
        CodeResult.checkCode(Integer.valueOf(response));
        return Integer.valueOf(response);
    }
}
