package com.ar.vgmsistemas.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Negocio implements Serializable {
    private static final long serialVersionUID = -6096220149840555434L;

    /*Atributos/ columnas en bd*/
    @SerializedName("idNegocio")
    private int id;

    @SerializedName("descripcionNegocio")
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
        this.descripcion = descripcion.trim();
    }

    public Negocio() {
    }

    public Negocio(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }
}
