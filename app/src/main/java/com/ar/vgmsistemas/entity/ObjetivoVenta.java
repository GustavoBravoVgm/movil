package com.ar.vgmsistemas.entity;

import com.ar.vgmsistemas.entity.key.PkObjetivoVenta;

import java.io.Serializable;
import java.util.Observable;

public class ObjetivoVenta extends Observable implements Serializable {
    private static final long serialVersionUID = -8930011811769915730L;

    private PkObjetivoVenta id;

    private Cliente cliente;

    private Articulo articulo;

    private double cantidad;

    private double cantidadVendida;

    private double cantidadAVender;

    private int bultosAVender;

    private double tasaDescuento;


    public PkObjetivoVenta getId() {
        return id;
    }

    public void setId(PkObjetivoVenta id) {
        this.id = id;
    }

    public double getCantidadAVender() {
        return cantidadAVender;
    }

    public void setCantidadAVender(double cantidadAVender) {
        this.cantidadAVender = cantidadAVender;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(double cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public int getBultosAVender() {
        return bultosAVender;
    }

    public void setBultosAVender(int bultosAVender) {
        this.bultosAVender = bultosAVender;
    }

    public double getTasaDescuento() {
        return tasaDescuento;
    }

    public void setTasaDescuento(double tasaDescuento) {
        this.tasaDescuento = tasaDescuento;
    }
}

