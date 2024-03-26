package com.ar.vgmsistemas.ws;

import android.content.Context;

import com.android.volley.Request;
import com.ar.vgmsistemas.entity.Cliente;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class ClienteWs extends GenericWs {

    public ClienteWs(Context mContext) {
        webService = "/ClienteWs";
        context = mContext;
    }

    public int send(Cliente cliente) throws Exception {
        String peticion = "/updateCoordenadas/";
        //String paramName = "clienteJson";
        metodo = Request.Method.POST;
        Gson gson = new Gson();
        JsonObject jsonCliente = gson.toJsonTree(cliente).getAsJsonObject();
        Map<String, String> parametros = new HashMap<>();
        parametros.put("clienteJson", jsonCliente.toString());
        String response = this.callWebService(url, webService + peticion, metodo, parametros,/*jsonCliente, paramName, */VOLUMEN_DE_DATOS_BAJO);
        if (!response.equals("0")) throw new Exception("No se pudo enviar la ubicacion");
        return Integer.valueOf(response);
    }
}
