package com.ar.vgmsistemas.entity;

public class Stock {

    /*Atributos/ columnas en bd*/
    private int idArticulos;

    private double stock;

    public int getIdArticulos() {
        return idArticulos;
    }

    public void setIdArticulos(int idArticulos) {
        this.idArticulos = idArticulos;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }
}
