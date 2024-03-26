package com.ar.vgmsistemas.entity.key;

import java.io.Serializable;

public class PkObjetivoVenta implements Serializable {

    private static final long serialVersionUID = -6438945780718911018L;
    private int idArticulos;
    private int idSucursal;
    private int idCliente;
    private int idComercio;
    private int idObjetivo;

    public int getIdArticulos() {
        return idArticulos;
    }

    public void setIdArticulos(int idArticulos) {
        this.idArticulos = idArticulos;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdComercio() {
        return idComercio;
    }

    public void setIdComercio(int idComercio) {
        this.idComercio = idComercio;
    }

    public int getIdObjetivo() {
        return idObjetivo;
    }

    public void setIdObjetivo(int idObjetivo) {
        this.idObjetivo = idObjetivo;
    }

    public PkObjetivoVenta(int idArticulos, int idSucursal, int idCliente, int idComercio, int idObjetivo) {
        this.idArticulos = idArticulos;
        this.idSucursal = idSucursal;
        this.idCliente = idCliente;
        this.idComercio = idComercio;
        this.idObjetivo = idObjetivo;
    }

    public PkObjetivoVenta() {
    }
}
