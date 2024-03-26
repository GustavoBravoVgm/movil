package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;

import com.ar.vgmsistemas.database.dao.entity.key.PkCuentaCorrienteBd;

import java.io.Serializable;

@Entity(tableName = "documentos_cuenta",
        primaryKeys = {"id_fcnc", "id_tipoab", "id_ptovta", "id_numdoc", "nu_cuota"})
public class CuentaCorrienteBd implements Serializable {
    @NonNull
    @Embedded
    private PkCuentaCorrienteBd id;

    @ColumnInfo(name = "pr_pagado")
    private double totalPagado;

    @ColumnInfo(name = "pr_nc")
    private double totalNotaCredito;

    @ColumnInfo(name = "id_comercio")
    private int idComercio;

    @ColumnInfo(name = "id_cliente")
    private int idCliente;

    @ColumnInfo(name = "id_succliente")
    private int idSucCliente;

    @ColumnInfo(name = "id_vendedor")
    private int idVendedor;

    @ColumnInfo(name = "pr_cuota")
    private double totalCuota;

    @ColumnInfo(name = "ti_aplica_cc")
    private int signo;

    @ColumnInfo(name = "id_movil")
    private String idMovil;

    @ColumnInfo(name = "id_condvta")
    private String idCondicionVenta;

    @ColumnInfo(name = "fe_venta")
    private String fechaVenta;

    @ColumnInfo(name = "pr_saldo")
    private Double prSaldo;

    @ColumnInfo(name = "id_recibo")
    private Long idRecibo;

    @ColumnInfo(name = "id_recptovta")
    private Integer idRecPtoVta;

    @ColumnInfo(name = "pr_saldoMovil")
    private Double saldoMovil;

    @ColumnInfo(name = "id_numdoc_fiscal")
    private Long idNumDocFiscal;

    @ColumnInfo(name = "de_cod_autoriza_rep_movil")
    private String deCodAutRepMovilCtaCte;


    /*Getters-Setters*/
    @NonNull
    public PkCuentaCorrienteBd getId() {
        return id;
    }

    public void setId(@NonNull PkCuentaCorrienteBd id) {
        this.id = id;
    }

    public double getTotalPagado() {
        return totalPagado;
    }

    public void setTotalPagado(double totalPagado) {
        this.totalPagado = totalPagado;
    }

    public double getTotalNotaCredito() {
        return totalNotaCredito;
    }

    public void setTotalNotaCredito(double totalNotaCredito) {
        this.totalNotaCredito = totalNotaCredito;
    }

    public int getIdComercio() {
        return idComercio;
    }

    public void setIdComercio(int idComercio) {
        this.idComercio = idComercio;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdSucCliente() {
        return idSucCliente;
    }

    public void setIdSucCliente(int idSucCliente) {
        this.idSucCliente = idSucCliente;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public double getTotalCuota() {
        return totalCuota;
    }

    public void setTotalCuota(double totalCuota) {
        this.totalCuota = totalCuota;
    }

    public int getSigno() {
        return signo;
    }

    public void setSigno(int signo) {
        this.signo = signo;
    }

    public String getIdMovil() {
        return idMovil;
    }

    public void setIdMovil(String idMovil) {
        this.idMovil = idMovil;
    }

    public String getIdCondicionVenta() {
        return idCondicionVenta;
    }

    public void setIdCondicionVenta(String idCondicionVenta) {
        this.idCondicionVenta = idCondicionVenta;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Double getPrSaldo() {
        return prSaldo;
    }

    public void setPrSaldo(Double prSaldo) {
        this.prSaldo = prSaldo;
    }

    public Long getIdRecibo() {
        return idRecibo;
    }

    public void setIdRecibo(Long idRecibo) {
        this.idRecibo = idRecibo;
    }

    public Integer getIdRecPtoVta() {
        return idRecPtoVta;
    }

    public void setIdRecPtoVta(Integer idRecPtoVta) {
        this.idRecPtoVta = idRecPtoVta;
    }

    public Double getSaldoMovil() {
        return saldoMovil;
    }

    public void setSaldoMovil(Double saldoMovil) {
        this.saldoMovil = saldoMovil;
    }

    public Long getIdNumDocFiscal() {
        return idNumDocFiscal;
    }

    public void setIdNumDocFiscal(Long idNumDocFiscal) {
        this.idNumDocFiscal = idNumDocFiscal;
    }

    public String getDeCodAutRepMovilCtaCte() {
        return deCodAutRepMovilCtaCte;
    }

    public void setDeCodAutRepMovilCtaCte(String deCodAutRepMovilCtaCte) {
        if (deCodAutRepMovilCtaCte == null) deCodAutRepMovilCtaCte = "";
        this.deCodAutRepMovilCtaCte = deCodAutRepMovilCtaCte;
    }
}
