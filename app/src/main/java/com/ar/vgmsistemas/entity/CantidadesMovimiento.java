package com.ar.vgmsistemas.entity;


public class CantidadesMovimiento {
    private static final String TABLE = "v_cantidad_movimientos";

    private int idSucursal;

    private int idCliente;

    private int idComercio;

    private int cantidadPedidos;

    private int cantidadNoAtenciones;

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

    public int getCantidadPedidos() {
        return cantidadPedidos;
    }

    public void setCantidadPedidos(int cantidadPedidos) {
        this.cantidadPedidos = cantidadPedidos;
    }

    public int getCantidadNoAtenciones() {
        return cantidadNoAtenciones;
    }

    public void setCantidadNoAtenciones(int cantidadNoAtenciones) {
        this.cantidadNoAtenciones = cantidadNoAtenciones;
    }

    public CantidadesMovimiento(int idSucursal, int idCliente, int idComercio, int cantidadPedidos, int cantidadNoAtenciones) {
        this.idSucursal = idSucursal;
        this.idCliente = idCliente;
        this.idComercio = idComercio;
        this.cantidadPedidos = cantidadPedidos;
        this.cantidadNoAtenciones = cantidadNoAtenciones;
    }

    public CantidadesMovimiento() {
    }
}
