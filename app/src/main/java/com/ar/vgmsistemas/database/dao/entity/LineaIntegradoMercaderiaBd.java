package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;

public class LineaIntegradoMercaderiaBd {
    @Embedded
    private ArticuloBd articulo;
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

    public LineaIntegradoMercaderiaBd() {
    }

    public ArticuloBd getArticulo() {
        return articulo;
    }

    public void setArticulo(ArticuloBd articulo) {
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
