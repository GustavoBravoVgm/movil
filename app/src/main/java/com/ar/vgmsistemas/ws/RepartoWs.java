package com.ar.vgmsistemas.ws;

import android.content.Context;

import com.android.volley.Request;
import com.ar.vgmsistemas.entity.Hoja;
import com.ar.vgmsistemas.entity.HojaDetalle;
import com.ar.vgmsistemas.entity.ListaHojaDetalle;
import com.ar.vgmsistemas.entity.ListaPkHojaDetalle;
import com.ar.vgmsistemas.entity.ListaVentas;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.utils.CodeResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class RepartoWs extends GenericWs {

    public RepartoWs(Context mContext) {
        webService = "/RepartoWs";
        context = mContext;
    }

    public int updateHojaDetalleCredito(Venta credito, HojaDetalle hojaDetalle, boolean prerendida) throws Exception {
        String peticion = "/updateHojaDetalleCredito/";
        metodo = Request.Method.POST;

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSZ").create();
        JsonObject jsonCredito = gson.toJsonTree(credito).getAsJsonObject();
        JsonObject jsonHojaDetalle = gson.toJsonTree(hojaDetalle).getAsJsonObject();
        Map<String, String> parametros = new HashMap<>();
        parametros.put("credito", jsonCredito.toString());
        parametros.put("hojaDetalleString", jsonHojaDetalle.toString());
        parametros.put("prerendida", String.valueOf(prerendida));
        String response = callWebService(url, webService + peticion, metodo, parametros, VOLUMEN_DE_DATOS_MEDIO);
        CodeResult.checkCode(Integer.parseInt(response));
        return Integer.parseInt(response);
    }

    //METODO QUE ENVIA LAS HOJAS DETALLES AGRUPADAS POR ENTREGA
    public int updateHojaDetalleCreditoPorEntrega(ListaVentas creditos, ListaHojaDetalle hojasDetalles, boolean prerendida) throws Exception {
        String peticion = "/updateHojaDetalleCreditoUnicaEntrega/";
        metodo = Request.Method.POST;

        //Gson gson = new Gson();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSZ").create();
        JsonObject jsonCredito = gson.toJsonTree(creditos).getAsJsonObject();
        JsonObject jsonHojaDetalle = gson.toJsonTree(hojasDetalles).getAsJsonObject();
        Map<String, String> parametros = new HashMap<>();
        parametros.put("listaCredito", jsonCredito.toString());
        parametros.put("listaHojaDetalleString", jsonHojaDetalle.toString());
        parametros.put("prerendida", String.valueOf(prerendida));
        String response = callWebService(url, webService + peticion, metodo, parametros, VOLUMEN_DE_DATOS_MEDIO);
        CodeResult.checkCode(Integer.parseInt(response));
        return Integer.parseInt(response);
    }

    public int updateHojaDetallePorEntrega(ListaHojaDetalle hojasDetalles, boolean prerendida) throws Exception {
        String peticion = "/updateHojaDetalleUnicaEntrega/";
        metodo = Request.Method.POST;

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSZ").create();
        JsonObject jsonHojaDetalle = gson.toJsonTree(hojasDetalles).getAsJsonObject();
        Map<String, String> parametros = new HashMap<>();
        parametros.put("hojaDetalleString", jsonHojaDetalle.toString());
        parametros.put("prerendida", String.valueOf(prerendida));
        String response = callWebService(url, webService + peticion, metodo, parametros, VOLUMEN_DE_DATOS_MEDIO);
        CodeResult.checkCode(Integer.parseInt(response));
        return Integer.parseInt(response);
    }

    public int updateHojaDetalle(HojaDetalle hojaDetalle, boolean prerendida) throws Exception {
        String peticion = "/updateHojaDetalle/";
        metodo = Request.Method.POST;

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSZ").create();
        JsonObject jsonHojaDetalle = gson.toJsonTree(hojaDetalle).getAsJsonObject();
        Map<String, String> parametros = new HashMap<>();
        parametros.put("hojaDetalleString", jsonHojaDetalle.toString());
        parametros.put("prerendida", String.valueOf(prerendida));
        String response = callWebService(url, webService + peticion, metodo, parametros, VOLUMEN_DE_DATOS_MEDIO);
        CodeResult.checkCode(Integer.parseInt(response));
        return Integer.parseInt(response);
    }

    public int updateHoja(Hoja hoja) throws Exception {
        String peticion = "/updateHoja/";
        metodo = Request.Method.POST;

        Gson gson = new Gson();
        JsonObject jsonHoja = gson.toJsonTree(hoja).getAsJsonObject();
        Map<String, String> parametros = new HashMap<>();
        parametros.put("hoja", jsonHoja.toString());

        String response = callWebService(url, webService + peticion, metodo, parametros, VOLUMEN_DE_DATOS_MEDIO);
        CodeResult.checkCode(Integer.parseInt(response));
        return Integer.parseInt(response);
    }

    public ListaPkHojaDetalle estanEnHoja(ListaPkHojaDetalle detalles) throws Exception {
        int ultimo;
        String peticion = "/estanEnHoja/";
        metodo = Request.Method.POST;
        ListaPkHojaDetalle listaHojaDetalle = new ListaPkHojaDetalle();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSZ").create();
        JsonObject jsonPkHojasDetalles = gson.toJsonTree(detalles).getAsJsonObject();
        Map<String, String> parametros = new HashMap<>();
        parametros.put("listHojasDetalles", jsonPkHojasDetalles.toString());
        String response = callWebService(url, webService + peticion, metodo, parametros, VOLUMEN_DE_DATOS_MEDIO);

        if (!response.isEmpty()) {
            ultimo = response.length() - 1;
            if (response.charAt(0) == '"' && response.charAt(ultimo) == '"') {
                //quito el primer y ultimo caracter si son " sino da error
                response = response.substring(1, ultimo);
                response = response.replace("\\\"", "\"");
            }
        }
        listaHojaDetalle = gson.fromJson(response, ListaPkHojaDetalle.class);
        return listaHojaDetalle;
    }
}
