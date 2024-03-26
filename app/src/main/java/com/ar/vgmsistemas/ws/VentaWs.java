package com.ar.vgmsistemas.ws;

import static com.ar.vgmsistemas.utils.CodeResult.RESULT_ERROR;

import android.content.Context;

import com.android.volley.Request;
import com.ar.vgmsistemas.entity.ListaVentas;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.utils.CodeResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class VentaWs extends GenericWs {

    public VentaWs(Context mContext) {
        webService = "/VentaWs";
        context = mContext;
    }

    public int send(Venta venta) throws Exception {
        String peticion = "/createPedido/";
        metodo = Request.Method.POST;

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSZ").create();
        JsonObject jsonPedido = gson.toJsonTree(venta).getAsJsonObject();
        Map<String, String> parametros = new HashMap<>();
        parametros.put("pedido", jsonPedido.toString());
        String response = callWebService(url, webService + peticion, metodo, parametros,/*jsonPedido, paramName, */VOLUMEN_DE_DATOS_MEDIO);
        CodeResult.checkCode(Integer.valueOf(response));
        try {
            int result = Integer.valueOf(response);
            return result;
        } catch (Exception e) {
            return RESULT_ERROR;
        }
    }

    public String send(ListaVentas ventas) throws Exception {
        String peticion = "/createPedidos/";
        metodo = Request.Method.POST;

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSZ").create();
        JsonObject jsonPedido = gson.toJsonTree(ventas).getAsJsonObject();
        Map<String, String> parametros = new HashMap<>();
        parametros.put("pedido", jsonPedido.toString());
        String response = callWebService(url, webService + peticion, metodo, parametros,/*jsonPedido, paramName, */VOLUMEN_DE_DATOS_MEDIO);
        return response;
    }

    public int anularVenta(String idMovilPs) throws Exception {
        String peticion = "/anularVenta/";
        metodo = Request.Method.POST;

        Map<String, String> parametros = new HashMap<>();
        parametros.put("idMovilPs", idMovilPs);
        int result = Integer.valueOf(this.callWebService(url, webService + peticion, metodo, parametros,/* jsonParameter, paramName,*/ VOLUMEN_DE_DATOS_BAJO));
        return result;
    }

    public void delete(Venta venta) throws Exception {
        String peticion = "/deletePedido/";
        metodo = Request.Method.GET;
        Gson gson = new Gson();
        JsonObject jsonVenta = gson.toJsonTree(venta).getAsJsonObject();
        Map<String, String> parametros = new HashMap<>();
        parametros.put("pedidoJson", jsonVenta.toString());
        callWebService(url, webService + peticion, metodo, parametros,/*jsonVenta, paramName,*/ VOLUMEN_DE_DATOS_MEDIO);
    }
}
