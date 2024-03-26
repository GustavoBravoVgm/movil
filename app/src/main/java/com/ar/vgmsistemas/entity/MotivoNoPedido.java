package com.ar.vgmsistemas.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MotivoNoPedido implements Serializable {
    private static final long serialVersionUID = 2872791251079989054L;

    /*Atributos/ columnas en bd*/
    @SerializedName("id")
    private int id;

    @SerializedName("descripcion")
    private String descripcion;

    /*Getters-Setters*/
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return this.descripcion;
    }

}
