package com.ar.vgmsistemas.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PlanCuenta implements Serializable {
    private static final long serialVersionUID = -299661686797022088L;

    @SerializedName("id")
    private int id;

    private String descripcion;

    private int categoria;

    private String snEgresoMovil;

    public PlanCuenta(int id, String descripcion, int categoria, String snEgresoMovil) {
        this.id = id;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.snEgresoMovil = snEgresoMovil;
    }

    public PlanCuenta() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return descripcion;
    }

    public String getSnEgresoMovil() {
        return snEgresoMovil;
    }

    public void setSnEgresoMovil(String snEgresoMovil) {
        this.snEgresoMovil = snEgresoMovil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlanCuenta that = (PlanCuenta) o;

        if (id != that.id) return false;
        if (categoria != that.categoria) return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null)
            return false;
        return snEgresoMovil != null ? snEgresoMovil.equals(that.snEgresoMovil) : that.snEgresoMovil == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + categoria;
        result = 31 * result + (snEgresoMovil != null ? snEgresoMovil.hashCode() : 0);
        return result;
    }
}
