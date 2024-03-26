package com.ar.vgmsistemas.database.dao.entity.result;

import androidx.room.ColumnInfo;

public class SecuenciaRuteoResultBd {
    @ColumnInfo(name = "dia")
    private Integer dia;
    @ColumnInfo(name = "orden")
    private Integer numeroOrden;
    @ColumnInfo(name = "id_vendedor")
    private Integer idVendedor;
    @ColumnInfo(name = "id_sucursal")
    private Integer idSucursal;
    @ColumnInfo(name = "id_cliente")
    private Integer idCliente;
    @ColumnInfo(name = "id_comercio")
    private Integer idComercio;
    @ColumnInfo(name = "id_postal")
    private Integer idPostal;

    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public Integer getNumeroOrden() {
        return numeroOrden;
    }

    public void setNumeroOrden(Integer numeroOrden) {
        this.numeroOrden = numeroOrden;
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

    public Integer getIdPostal() {
        return idPostal;
    }

    public void setIdPostal(Integer idPostal) {
        this.idPostal = idPostal;
    }
}
