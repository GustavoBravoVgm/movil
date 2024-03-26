package com.ar.vgmsistemas.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/*-->se borra el entity Linea porque estaba duplicado*/
public class Marca implements Serializable {
    /*Atributos/ columnas en bd*/
    @SerializedName("idMarca")
    private int id;

    @SerializedName("descripcionMarca")
    private String descripcion;

    /*Getters-Setters*/

    public int getId() {
        return id;
    }

    public void setId(int _id) {
        this.id = _id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String _descripcion) {
        this.descripcion = _descripcion;
    }

    public Marca(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Marca() {
    }
}
