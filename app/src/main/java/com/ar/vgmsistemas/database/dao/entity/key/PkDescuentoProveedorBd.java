package com.ar.vgmsistemas.database.dao.entity.key;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import java.io.Serializable;


public class PkDescuentoProveedorBd implements Serializable {

    @Ignore
    private static final long serialVersionUID = 1L;
    @ColumnInfo(name = "id_vendedor")
    private int idVendedor;
    @ColumnInfo(name = "id_sucursal")
    private int idSucursal;
    @ColumnInfo(name = "id_cliente")
    private int idCliente;
    @ColumnInfo(name = "id_comercio")
    private int idComercio;
    @ColumnInfo(name = "id_dto_proveedor_cliente")
    private int idDescuentoProveedorCliente;
    @ColumnInfo(name = "id_dto_proveedor")
    private int idDescuentoProveedor;

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
