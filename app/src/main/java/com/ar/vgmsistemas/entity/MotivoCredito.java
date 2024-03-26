package com.ar.vgmsistemas.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class MotivoCredito implements Serializable {
    /*Constantes*/
    private static final long serialVersionUID = 1L;

    /*Atributos/ columnas en bd*/
    private int idMotivoRechazoNC;

    @SerializedName("deMotivoRechazoNC")
    private String descripcion;

    /*Getters-Setters*/
    public int getIdMotivoRechazoNC() {
        return idMotivoRechazoNC;
    }

    public void setIdMotivoRechazoNC(int idMotivoRechazoNC) {
        this.idMotivoRechazoNC = idMotivoRechazoNC;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
