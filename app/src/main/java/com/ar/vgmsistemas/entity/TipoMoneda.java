package com.ar.vgmsistemas.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TipoMoneda implements Serializable {
    private static final long serialVersionUID = 7332484080974853883L;

    @SerializedName("id")
    private int id;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("cotizacion")
    private double cotizacion;

    private boolean monedaCurso;

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

    public double getCotizacion() {
        return this.cotizacion;
    }

    public void setCotizacion(double cotizacion) {
        this.cotizacion = cotizacion;
    }

    public boolean isMonedaCurso() {
        return this.monedaCurso;
    }

    public void setMonedaCurso(boolean monedaCurso) {
        this.monedaCurso = monedaCurso;
    }

    @Override
    public String toString() {
        return descripcion;
    }

}
