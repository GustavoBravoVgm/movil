package com.ar.vgmsistemas.entity;

import java.io.Serializable;

public class Impuesto implements Serializable {

    private static final long serialVersionUID = 3593807007462447749L;
    /*Constantes*/
    public static final int TIPO_IMPUESTO_DGR = 1;
    public static final int TIPO_IMPUESTO_IVA = 2;
    public static final int TIPO_IMPUESTO_DGI = 3;
    public static final int TIPO_IMPUESTO_INTERNO = 4;

    /*Atributos/ columnas en bd*/
    private int id;

    private String descripcion;

    private int tiImpuesto;

    private double taImpuesto;


    /*Getters-Setters*/
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

    public int getTiImpuesto() {
        return tiImpuesto;
    }

    public void setTiImpuesto(int tiImpuesto) {
        this.tiImpuesto = tiImpuesto;
    }

    public double getTaImpuesto() {
        return taImpuesto;
    }

    public void setTaImpuesto(double taImpuesto) {
        this.taImpuesto = taImpuesto;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        long result = 1;
        result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + Double.doubleToLongBits(taImpuesto);
        result = prime * result + tiImpuesto;
        return (int) result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Impuesto other = (Impuesto) obj;
        if (descripcion == null) {
            if (other.descripcion != null)
                return false;
        } else if (!descripcion.equals(other.descripcion))
            return false;
        if (id != other.id)
            return false;
        if (Double.doubleToLongBits(taImpuesto) != Double.doubleToLongBits(other.taImpuesto))
            return false;
        if (tiImpuesto != other.tiImpuesto)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
