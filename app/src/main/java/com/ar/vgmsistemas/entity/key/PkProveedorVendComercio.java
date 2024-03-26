package com.ar.vgmsistemas.entity.key;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PkProveedorVendComercio implements Serializable {
    private static final long serialVersionUID = -2112303972229898819L;

    @SerializedName("id_proveedor")
    private Integer idProveedor;

    @SerializedName("id_vendedor")
    private Integer idVendedor;

    @SerializedName("id_sucursal")
    private Integer idSucursal;

    @SerializedName("id_cliente")
    private Integer idCliente;

    @SerializedName("id_comercio")
    private Integer idComercio;

    public PkProveedorVendComercio(Integer idProveedor, Integer idVendedor,
                                   Integer idSucursal, Integer idCliente, Integer idComercio) {
        this.idProveedor = idProveedor;
        this.idVendedor = idVendedor;
        this.idSucursal = idSucursal;
        this.idCliente = idCliente;
        this.idComercio = idComercio;
    }

    public PkProveedorVendComercio() {
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Integer getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Integer idVendedor) {
        this.idVendedor = idVendedor;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdComercio() {
        return idComercio;
    }

    public void setIdComercio(Integer idComercio) {
        this.idComercio = idComercio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PkProveedorVendComercio that = (PkProveedorVendComercio) o;
        return idProveedor.equals(that.idProveedor) &&
                idVendedor.equals(that.idVendedor) &&
                idSucursal.equals(that.idSucursal) &&
                idCliente.equals(that.idCliente) &&
                idComercio.equals(that.idComercio);
    }

    @Override
    public int hashCode() {
        int result = idProveedor.hashCode();
        result = 31 * result + idVendedor.hashCode();
        result = 31 * result + idSucursal.hashCode();
        result = 31 * result + idCliente.hashCode();
        result = 31 * result + idComercio.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PkProveedorVendComercio{" +
                "idProveedor=" + idProveedor +
                ", idVendedor=" + idVendedor +
                ", idSucursal=" + idSucursal +
                ", idCliente=" + idCliente +
                ", idComercio=" + idComercio +
                '}';
    }
}
