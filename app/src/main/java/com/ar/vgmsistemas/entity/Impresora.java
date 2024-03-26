package com.ar.vgmsistemas.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Impresora implements Serializable {

    @SerializedName("id")
    private int id;

    private String descripcion;

    private int tamanioHoja;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String _descripcion) {
        this.descripcion = _descripcion;
    }

    public int getTamanioHoja() {
        return tamanioHoja;
    }

    public void setTamanioHoja(int _tamanioHoja) {
        this.tamanioHoja = _tamanioHoja;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
