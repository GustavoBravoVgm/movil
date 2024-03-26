package com.ar.vgmsistemas.entity;

import com.ar.vgmsistemas.entity.key.PkComprasImpuestos;

import java.io.Serializable;

public class ComprasImpuestos implements Serializable {

    private PkComprasImpuestos id;

    private double prImpGravado;

    private double taImpuesto;

    private double prImpuesto;

    private String idMovil;

    private String snAnulado;

    private Compra compra;

    private Impuesto impuesto;

    public ComprasImpuestos() {

    }

    public PkComprasImpuestos getId() {
        return id;
    }

    public void setId(PkComprasImpuestos id) {
        this.id = id;
    }

    public double getPrImpGravado() {
        return prImpGravado;
    }

    public void setPrImpGravado(double prImpGravado) {
        this.prImpGravado = prImpGravado;
    }

    public double getTaImpuesto() {
        return taImpuesto;
    }

    public void setTaImpuesto(double taImpuesto) {
        this.taImpuesto = taImpuesto;
    }

    public double getPrImpuesto() {
        return prImpuesto;
    }

    public void setPrImpuesto(double prImpuesto) {
        this.prImpuesto = prImpuesto;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Impuesto getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(Impuesto impuesto) {
        this.impuesto = impuesto;
    }

    public String getIdMovil() {
        return idMovil;
    }

    public void setIdMovil(String idMovil) {
        this.idMovil = idMovil;
    }

    public String getSnAnulado() {
        return snAnulado;
    }

    public void setSnAnulado(String snAnulado) {
        this.snAnulado = snAnulado;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        long result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + Double.doubleToLongBits(prImpGravado);
        result = prime * result + Double.doubleToLongBits(prImpuesto);
        result = prime * result + Double.doubleToLongBits(taImpuesto);
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
        ComprasImpuestos other = (ComprasImpuestos) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (Double.doubleToLongBits(prImpGravado) != Double.doubleToLongBits(other.prImpGravado))
            return false;
        if (Double.doubleToLongBits(prImpuesto) != Double.doubleToLongBits(other.prImpuesto))
            return false;
        return Double.doubleToLongBits(taImpuesto) == Double.doubleToLongBits(other.taImpuesto);
    }

}
