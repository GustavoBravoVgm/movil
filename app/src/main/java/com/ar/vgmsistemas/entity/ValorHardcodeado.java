package com.ar.vgmsistemas.entity;

public class ValorHardcodeado {
    private int cantidad;
    private double importe;
    private String descripcion;
    private double valor;
    private boolean isCalculable;

    public ValorHardcodeado() {

    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public boolean isCalculable() {
        return isCalculable;
    }

    public void setCalculable(boolean calculable) {
        isCalculable = calculable;
    }

    @Override
    public String toString() {
        return getDescripcion();
    }
}
