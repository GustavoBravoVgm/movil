package com.ar.vgmsistemas.entity.key;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PkRubro implements Serializable {
    private static final long serialVersionUID = 6509685569523286853L;

    @SerializedName("idRubro")
    private int idRubro;

    @SerializedName("idNegocio")
    private int idNegocio;


    /*Getters-Setters*/
    public int getIdRubro() {
        return this.idRubro;
    }

    public void setIdRubro(int idSegmento) {
        this.idRubro = idSegmento;
    }

    public int getIdNegocio() {
        return this.idNegocio;
    }

    public void setIdNegocio(int idNegocio) {
        this.idNegocio = idNegocio;
    }

    public PkRubro() {
    }

    public PkRubro(int idRubro, int idNegocio) {
        this.idRubro = idRubro;
        this.idNegocio = idNegocio;
    }
}
