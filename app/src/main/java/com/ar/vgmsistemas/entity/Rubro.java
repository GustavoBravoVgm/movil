package com.ar.vgmsistemas.entity;

import com.ar.vgmsistemas.entity.key.PkRubro;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rubro implements Serializable {

    private static final long serialVersionUID = -7046464468790325830L;
    @SerializedName("idRubro")
    private PkRubro id;

    @SerializedName("descripcionRubro")
    private String descripcion;

    @SerializedName("negocio")
    private Negocio negocio;


    /*Getters-Setters*/
    public PkRubro getId() {
        return this.id;
    }

    public void setId(PkRubro id) {
        this.id = id;
    }

    public String getDescripcion() {
        return this.descripcion.trim();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Negocio getNegocio() {
        return this.negocio;
    }

    public void setNegocio(Negocio negocio) {
        this.negocio = negocio;
    }

    public Rubro() {
    }

    public Rubro(PkRubro id, String descripcion, Negocio negocio) {
        this.id = id;
        this.descripcion = descripcion;
        this.negocio = negocio;
    }
}
