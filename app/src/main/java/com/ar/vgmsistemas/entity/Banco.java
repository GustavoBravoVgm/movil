package com.ar.vgmsistemas.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Banco implements Serializable {

    @SerializedName("id")
    private int id;

    private String denominacion;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDenominacion() {
        return this.denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public Banco() {
    }

    @Override
    public String toString() {
        return id + " - " + denominacion;
    }

}
