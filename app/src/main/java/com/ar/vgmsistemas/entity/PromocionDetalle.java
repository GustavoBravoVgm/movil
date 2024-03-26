package com.ar.vgmsistemas.entity;

import com.ar.vgmsistemas.entity.key.PkPromocionDetalle;

import java.io.Serializable;

public class PromocionDetalle implements Serializable {

    private PkPromocionDetalle id;

    private Articulo articulo;

    private double cantidad;

    private double precioCombo;

    private double unidades;

    private int bultos;

    private double precioNormal;

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public double getCantidad() {
        if (getArticulo() != null) {
            double unidadesXBulto = getArticulo().getUnidadPorBulto();
            int bultos = this.getBultos();
            double unidades = this.getUnidades();
            return bultos * unidadesXBulto + unidades;
        }
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getCantidadComboComun() {
        return cantidad;
    }

    public void setPrecio(double precio) {
        this.precioCombo = precio;
    }

    public double getPrecio() {
        return precioCombo;
    }

    public void setPrecioNormal(double precioNormal) {
        this.precioNormal = precioNormal;
    }

    public double getPrecioNormal() {
        return this.precioNormal;
    }

    public double getUnidades() {
        return unidades;
    }

    public void setUnidades(double unidades) {
        this.unidades = unidades;
    }

    public int getBultos() {
        return bultos;
    }

    public void setBultos(int bultos) {
        this.bultos = bultos;
    }

    public double getPrecioCombo() {
        return precioCombo;
    }

    public void setPrecioCombo(double precioCombo) {
        this.precioCombo = precioCombo;
    }

    public PkPromocionDetalle getId() {
        return id;
    }

    public void setId(PkPromocionDetalle id) {
        this.id = id;
    }
}
