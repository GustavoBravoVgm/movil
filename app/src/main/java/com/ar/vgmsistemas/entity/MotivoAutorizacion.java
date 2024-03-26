package com.ar.vgmsistemas.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MotivoAutorizacion implements Serializable {
    /*Constantes*/
    public static final String TI_AUTORIZACION_CUENTA_CORRIENTE = "CC";

    public static final String TI_AUTORIZACION_CUENTA_CORRIENTE_REPARTO = "RP";

    public static final String TI_AUTORIZACION_PEDIDO_RENTABLE = "PR";

    public static final String TI_AUTORIZACION_PEDIDO_RENTABLE_SIN_AUTORIZACION = "SA";


    /*Atributos/ columnas en bd*/
    @SerializedName("id")
    private Integer id;

    private String motivo;

    private String tiAutorizacion;

    /*Getters-Setters*/
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getTiAutorizacion() {
        return tiAutorizacion;
    }

    public void setTiAutorizacion(String tiAutorizacion) {
        this.tiAutorizacion = tiAutorizacion;
    }


    @Override
    public String toString() {
        return motivo;
    }
}
