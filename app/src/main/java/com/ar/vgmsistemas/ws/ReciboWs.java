package com.ar.vgmsistemas.ws;

import static com.ar.vgmsistemas.utils.CodeResult.RESULT_ERROR;

import android.content.Context;

import com.android.volley.Request;
import com.ar.vgmsistemas.entity.Recibo;
import com.ar.vgmsistemas.utils.CodeResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class ReciboWs extends GenericWs {

    public ReciboWs(Context mContext) {
        webService = "/ReciboWs";
        context = mContext;
    }

    public int send(Recibo recibo) throws Exception {
        String peticion = "/enviarRecibo/";
        //String paramName = "reciboJson";
        metodo = Request.Method.POST;

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSZ").create();
        JsonObject jsonRecibo = gson.toJsonTree(recibo).getAsJsonObject();
        Map<String, String> parametros = new HashMap<>();
        parametros.put("reciboJson", jsonRecibo.toString());
        String response = callWebService(url, webService + peticion, metodo, parametros,/*jsonRecibo, paramName,*/ VOLUMEN_DE_DATOS_MEDIO);
        CodeResult.checkCode(Integer.valueOf(response));
        //return Integer.valueOf(response);
        try {
            int result = Integer.valueOf(response);
            return result;
        } catch (Exception e) {
            return RESULT_ERROR;
        }
    }
}
