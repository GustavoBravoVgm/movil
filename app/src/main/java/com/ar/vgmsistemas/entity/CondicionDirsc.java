package com.ar.vgmsistemas.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CondicionDirsc implements Serializable {

    private static final long serialVersionUID = 5496073849234220845L;

    private int id;

    private String descripcion;

    private double tasaImpuesto;

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

    public double getTasaImpuesto() {
        return this.tasaImpuesto;
    }

    public void setTasaImpuesto(double tasaImpuesto) {
        this.tasaImpuesto = tasaImpuesto;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public CondicionDirsc(int id, String descripcion, double tasaImpuesto) {
        this.id = id;
        this.descripcion = descripcion == null ? "" : descripcion;
        this.tasaImpuesto = tasaImpuesto;
    }

    public CondicionDirsc() {
    }
}
