package com.ar.vgmsistemas.entity.key;

import java.io.Serializable;


public class PkDescuentoProveedor implements Serializable {

    private static final long serialVersionUID = 1L;
    private int idVendedor;
    private int idSucursal;
    private int idCliente;
    private int idComercio;
    private int idDescuentoProveedorCliente;
    private int idDescuentoProveedor;

    public PkDescuentoProveedor(int idVendedor, int idSucursal, int idCliente, int idComercio,
                                int idDescuentoProveedorCliente, int idDescuentoProveedor) {
        this.idVendedor = idVendedor;
        this.idSucursal = idSucursal;
        this.idCliente = idCliente;
        this.idComercio = idComercio;
        this.idDescuentoProveedorCliente = idDescuentoProveedorCliente;
        this.idDescuentoProveedor = idDescuentoProveedor;
    }

    public PkDescuentoProveedor() {
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

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public int getIdDescuentoProveedorCliente() {
        return idDescuentoProveedorCliente;
    }

    public void setIdDescuentoProveedorCliente(int idDescuentoProveedorCliente) {
        this.idDescuentoProveedorCliente = idDescuentoProveedorCliente;
    }

    public int getIdDescuentoProveedor() {
        return idDescuentoProveedor;
    }

    public void setIdDescuentoProveedor(int idDescuentoProveedor) {
        this.idDescuentoProveedor = idDescuentoProveedor;
    }

}
