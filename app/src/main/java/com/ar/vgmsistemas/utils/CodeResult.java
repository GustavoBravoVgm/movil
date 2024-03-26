package com.ar.vgmsistemas.utils;

import com.ar.vgmsistemas.bo.VendedorBo;

public class CodeResult {
    public static final int RESULT_OK = 0;
    public static final int RESULT_ERROR = 1;
    public static final int RESULT_MONTO_DISPONIBLE_CERO_RECIBO_BO = 2;
    public static final int RESULT_NO_SE_ENCONTRO_UN_COMPROBANTE_EN_CTACTE_RECIBO_BO = 3;
    public static final int RESULT_ALGUN_COMPROBANTE_TIENE_SALDO_CERO = 4;
    public static final int RESULT_VENDEDOR_INVALID = 101;
    public static final int RESULT_PEDIDOS_NO_AUTORIZADO = 102;
    public static final int RESULT_RECIBO_YA_IMPUTADO = 103;
    public static final int RESULT_PEDIDO_EXISTENTE = 104; // lo dejo para posterior uso.

    public static final String ERROR_SINCRONIZACION = "errorSincronizacion";
    public static final String RESULT_VENDEDOR_INVALID_S = "vendedorNoValido";
    public static final int ERROR_HOJA_CAMBIADA = 5;


    public static final void checkCode(String code) {
        if (code.equals(RESULT_VENDEDOR_INVALID_S)) {
            VendedorBo.setValidVendedor(false);
        }

    }

    public static final void checkCode(int code) {
        if (code == RESULT_VENDEDOR_INVALID) {
            VendedorBo.setValidVendedor(false);
        }
    }


}
