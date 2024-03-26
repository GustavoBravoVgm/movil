package com.ar.vgmsistemas.ws;

import android.content.Context;

import com.android.volley.Request;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.bo.VendedorBo;
import com.ar.vgmsistemas.entity.key.PkDocumento;
import com.ar.vgmsistemas.utils.CodeResult;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class SincronizacionWs extends GenericWs {

    private static final String sincWebServiceName = "/AdministracionWs";

    public SincronizacionWs(Context mContext) {
        webService = sincWebServiceName;
        context = mContext;
    }

    public String descargarDatos(String url) throws Exception {
        String peticion = "/iniciarSincronizacion/";
        //String paramName = "idVendedorYPkDocumentoJson";
        metodo = Request.Method.POST;
        PkDocumento pkDocumento = new PkDocumento();
        pkDocumento.setPuntoVenta(PreferenciaBo.getInstance().getPreferencia().getIdPuntoVentaPorDefecto());
        pkDocumento.setIdDocumento(PreferenciaBo.getInstance().getPreferencia().getIdTipoDocumentoPorDefecto());
        Gson gson = new Gson();
        JsonObject jsonPk = gson.toJsonTree(pkDocumento).getAsJsonObject();
        Map<String, String> parametros = new HashMap<>();
        parametros.put("pkDocumento", jsonPk.toString());
        parametros.put("idVendedor", String.valueOf(VendedorBo.getVendedor().getId()));
        String result = this.callWebService(url, webService + peticion, metodo, parametros,/*jsonParameter, paramName,*/ VOLUMEN_DE_DATOS_ALTO);
        CodeResult.checkCode(result);
        return result;

    }

    public static String getSincWebServiceName() {
        return sincWebServiceName;
    }
}