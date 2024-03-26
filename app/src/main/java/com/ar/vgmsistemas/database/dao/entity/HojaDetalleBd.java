package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;

import com.ar.vgmsistemas.database.dao.entity.key.PkHojaDetalleBd;

import java.io.Serializable;

@Entity(tableName = "hoja_detalle",
        primaryKeys = {"id_hoja", "id_sucursal",
                "id_fcnc", "id_tipoab", "id_ptovta", "id_numdoc"})
public class HojaDetalleBd implements Serializable {
    @NonNull
    @Embedded
    private PkHojaDetalleBd id;

    @ColumnInfo(name = "fe_venta")
    private String feVenta;

    @ColumnInfo(name = "id_vendedor")
    private int idVendedor;

    @ColumnInfo(name = "pr_total")
    private double prTotal;

    @ColumnInfo(name = "pr_nc")
    private double prNotaCredito;

    @ColumnInfo(name = "pr_pagado")
    private double prPagado;

    @ColumnInfo(name = "ti_estado")
    private String tiEstado;

    @ColumnInfo(name = "id_succliente")
    private int idSucCliente;

    @ColumnInfo(name = "id_cliente")
    private int idCliente;

    @ColumnInfo(name = "id_comercio")
    private int idComercio;

    @ColumnInfo(name = "id_movil")
    private String idMovil;

    @ColumnInfo(name = "id_entrega_detalle")
    private int idEntregaDetalle;

    @ColumnInfo(name = "id_condvta")
    private String idCondicionVenta;

    @ColumnInfo(name = "id_motivo_autoriza")
    private Integer idMotivoAutorizacion;

    @ColumnInfo(name = "codigo_autorizacion")
    private String codigoAutorizacion;

    private String deCodAutRepMovilCtaCte;

    /*Getters-Setters*/

    @NonNull
    public PkHojaDetalleBd getId() {
        return id;
    }

    public void setId(@NonNull PkHojaDetalleBd id) {
        this.id = id;
    }

    public String getFeVenta() {
        return feVenta;
    }

    public void setFeVenta(String feVenta) {
        this.feVenta = feVenta;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public double getPrTotal() {
        return prTotal;
    }

    public void setPrTotal(double prTotal) {
        this.prTotal = prTotal;
    }

    public double getPrNotaCredito() {
        return prNotaCredito;
    }

    public void setPrNotaCredito(double prNotaCredito) {
        this.prNotaCredito = prNotaCredito;
    }

    public double getPrPagado() {
        return prPagado;
    }

    public void setPrPagado(double prPagado) {
        this.prPagado = prPagado;
    }

    public String getTiEstado() {
        return tiEstado;
    }

    public void setTiEstado(String tiEstado) {
        this.tiEstado = tiEstado;
    }

    public int getIdSucCliente() {
        return idSucCliente;
    }

    public void setIdSucCliente(int idSucCliente) {
        this.idSucCliente = idSucCliente;
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

    public String getIdMovil() {
        return idMovil;
    }

    public void setIdMovil(String idMovil) {
        this.idMovil = idMovil;
    }

    public int getIdEntregaDetalle() {
        return idEntregaDetalle;
    }

    public void setIdEntregaDetalle(int idEntregaDetalle) {
        this.idEntregaDetalle = idEntregaDetalle;
    }

    public String getIdCondicionVenta() {
        return idCondicionVenta;
    }

    public void setIdCondicionVenta(String idCondicionVenta) {
        this.idCondicionVenta = idCondicionVenta;
    }

    public Integer getIdMotivoAutorizacion() {
        return idMotivoAutorizacion;
    }

    public void setIdMotivoAutorizacion(Integer idMotivoAutorizacion) {
        this.idMotivoAutorizacion = idMotivoAutorizacion;
    }

    public String getCodigoAutorizacion() {
        return codigoAutorizacion;
    }

    public void setCodigoAutorizacion(String codigoAutorizacion) {
        this.codigoAutorizacion = codigoAutorizacion;
    }

    public String getDeCodAutRepMovilCtaCte() {
        return deCodAutRepMovilCtaCte;
    }

    public void setDeCodAutRepMovilCtaCte(String deCodAutRepMovilCtaCte) {
        this.deCodAutRepMovilCtaCte = deCodAutRepMovilCtaCte;
    }
}
