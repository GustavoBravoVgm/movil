package com.ar.vgmsistemas.entity;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;

public class LineaIntegradoMercaderia {
    @Embedded
    private Articulo articulo;
    @ColumnInfo(name = "idArticulo")
    private int idArticulo;
    @ColumnInfo(name = "cantidad")
    private int cantidad;
    @ColumnInfo(name = "idHoja")
    private Integer idHoja;
    @ColumnInfo(name = "tiEstado")
    private String tiEstado;
    @ColumnInfo(name = "prPagado")
    private double prPagado;
    @ColumnInfo(name = "idMovil")
    private String idMovil;

    public LineaIntegradoMercaderia() {
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getIdHoja() {
        return idHoja;
    }

    public void setIdHoja(Integer idHoja) {
        this.idHoja = idHoja;
    }

    public String getTiEstado() {
        return tiEstado;
    }

    public void setTiEstado(String tiEstado) {
        this.tiEstado = tiEstado;
    }

    public double getPrPagado() {
        return prPagado;
    }

    public void setPrPagado(double prPagado) {
        this.prPagado = prPagado;
    }

    public String getIdMovil() {
        return idMovil;
    }

    public void setIdMovil(String idMovil) {
        this.idMovil = idMovil;
    }
}
