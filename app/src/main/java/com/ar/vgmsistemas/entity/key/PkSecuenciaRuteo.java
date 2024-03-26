package com.ar.vgmsistemas.entity.key;

import java.io.Serializable;

public class PkSecuenciaRuteo implements Serializable {

    private static final long serialVersionUID = -2576850883551277723L;

    private int idVendedor;
    private int idSucursal;
    private int idCliente;
    private int idComercio;

    public PkSecuenciaRuteo(int idVendedor, int idSucursal, int idCliente, int idComercio) {
        this.idVendedor = idVendedor;
        this.idSucursal = idSucursal;
        this.idCliente = idCliente;
        this.idComercio = idComercio;
    }

    public PkSecuenciaRuteo() {
    }

    public int getIdVendedor() {
        return this.idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public int getIdSucursal() {
        return this.idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public int getIdCliente() {
        return this.idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdComercio() {
        return this.idComercio;
    }

    public void setIdComercio(int idComercio) {
        this.idComercio = idComercio;
    }

}
