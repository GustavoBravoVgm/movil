package com.ar.vgmsistemas.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Provincia implements Serializable {
    private static final long serialVersionUID = -3181743814260565299L;

    @SerializedName("id")
    private int id;

    @SerializedName("descripcion")
    private String descripcion;

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
        return this.id + " - " + this.descripcion.trim();
    }

    public Provincia(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Provincia() {
    }
}
