package com.ar.vgmsistemas.ws.vendedor;

import android.content.Context;

import com.android.volley.Request;
import com.ar.vgmsistemas.utils.CodeResult;
import com.ar.vgmsistemas.ws.GenericWs;

import java.util.HashMap;
import java.util.Map;

public class VendedorWs extends GenericWs {
    private static final String TRUE = "true";
    private static final String METODO_IS_VALID_VENDEDOR = "/validarVendedor/";
    private static final String PARAM_ID_VENDEDOR = "idVendedor";

    public VendedorWs(Context mContext) {
        webService = "/VendedorWs";
        context = mContext;
    }

    public boolean isValidVendedor(long idVendedor) throws Exception {
        String peticion = METODO_IS_VALID_VENDEDOR;
        metodo = Request.Method.POST;
        Map<String, String> parametros = new HashMap<>();
        parametros.put(PARAM_ID_VENDEDOR, String.valueOf(idVendedor));
        String response = callWebService(url, webService + peticion, metodo, parametros, VOLUMEN_DE_DATOS_BAJO);
        CodeResult.checkCode(response);
        return (response.equals(TRUE) || response.equals(""));
    }
}
