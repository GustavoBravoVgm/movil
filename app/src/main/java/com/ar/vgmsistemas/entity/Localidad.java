package com.ar.vgmsistemas.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Localidad implements Serializable {

    private static final long serialVersionUID = -7264836591388893411L;

    @SerializedName("id")
    private int id;

    private int idProvincia;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("codigoPostal")
    private int codigoPostal;

    @SerializedName("provincia")
    private Provincia provincia;

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
        if (descripcion == null) descripcion = "";
        this.descripcion = descripcion;
    }

    public int getCodigoPostal() {
        return this.codigoPostal;
    }

    public void setCodigoPostal(int codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Provincia getProvincia() {
        return this.provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    @Override
    public String toString() {
        return this.codigoPostal + " - " + this.descripcion.trim();
    }

    public Localidad(int id, int idProvincia, String descripcion, int codigoPostal, Provincia provincia) {
        this.id = id;
        this.idProvincia = idProvincia;
        this.descripcion = descripcion;
        this.codigoPostal = codigoPostal;
        this.provincia = provincia;
    }

    public Localidad() {
    }
}
