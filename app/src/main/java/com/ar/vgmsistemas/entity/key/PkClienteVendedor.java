package com.ar.vgmsistemas.entity.key;

import java.io.Serializable;

public class PkClienteVendedor implements Serializable {

    private static final long serialVersionUID = -2576850883551277723L;
    private int idVendedor;
    private int idSucursal;
    private int idCliente;
    private int idComercio;

    public PkClienteVendedor(int idVendedor, int idSucursal, int idCliente, int idComercio) {
        this.idVendedor = idVendedor;
        this.idSucursal = idSucursal;
        this.idCliente = idCliente;
        this.idComercio = idComercio;
    }

    public PkClienteVendedor() {
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
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

}
