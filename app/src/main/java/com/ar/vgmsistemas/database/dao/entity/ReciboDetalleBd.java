package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "pagos")
public class ReciboDetalleBd implements Serializable {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "id_fcnc")
    private String idDocumento;

    @ColumnInfo(name = "id_tipoab")
    private String idLetra;

    @ColumnInfo(name = "id_ptovta")
    private int puntoVenta;

    @ColumnInfo(name = "id_numdoc")
    private long idNumeroDocumento;

    @ColumnInfo(name = "nu_cuota")
    private int cuota;

    @ColumnInfo(name = "id_recpvta")
    private int idRecPvta;

    @ColumnInfo(name = "id_recibo")
    private long idRecibo;

    @ColumnInfo(name = "pr_pagado")
    private double importePagado;

    @ColumnInfo(name = "pr_saldomovil")
    private Double saldoMovil;

    @ColumnInfo(name = "ta_dto_recibo")
    private double taDtoRecibo = 0d;

    @ColumnInfo(name = "id_pago")
    private Integer idPago;

    @ColumnInfo(name = "pr_pago")
    private Double prPago;

    @ColumnInfo(name = "pr_nc")
    private Double prNc;

    @ColumnInfo(name = "pr_saldo")
    private Double prSaldo;

    @ColumnInfo(name = "id_legajo")
    private Integer idLegajo;

    /*Getters-Setters*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getIdLetra() {
        return idLetra;
    }

    public void setIdLetra(String idLetra) {
        this.idLetra = idLetra;
    }

    public int getPuntoVenta() {
        return puntoVenta;
    }

    public void setPuntoVenta(int puntoVenta) {
        this.puntoVenta = puntoVenta;
    }

    public long getIdNumeroDocumento() {
        return idNumeroDocumento;
    }

    public void setIdNumeroDocumento(long idNumeroDocumento) {
        this.idNumeroDocumento = idNumeroDocumento;
    }

    public int getCuota() {
        return cuota;
    }

    public void setCuota(int cuota) {
        this.cuota = cuota;
    }

    public int getIdRecPvta() {
        return idRecPvta;
    }

    public void setIdRecPvta(int idRecPvta) {
        this.idRecPvta = idRecPvta;
    }

    public long getIdRecibo() {
        return idRecibo;
    }

    public void setIdRecibo(long idRecibo) {
        this.idRecibo = idRecibo;
    }

    public double getImportePagado() {
        return importePagado;
    }

    public void setImportePagado(double importePagado) {
        this.importePagado = importePagado;
    }

    public Double getSaldoMovil() {
        return saldoMovil;
    }

    public void setSaldoMovil(Double saldoMovil) {
        this.saldoMovil = saldoMovil;
    }

    public double getTaDtoRecibo() {
        return taDtoRecibo;
    }

    public void setTaDtoRecibo(double taDtoRecibo) {
        this.taDtoRecibo = taDtoRecibo;
    }

    public Integer getIdPago() {
        return idPago;
    }

    public void setIdPago(Integer idPago) {
        this.idPago = idPago;
    }

    public Double getPrPago() {
        return prPago;
    }

    public void setPrPago(Double prPago) {
        this.prPago = prPago;
    }

    public Double getPrNc() {
        return prNc;
    }

    public void setPrNc(Double prNc) {
        this.prNc = prNc;
    }

    public Double getPrSaldo() {
        return prSaldo;
    }

    public void setPrSaldo(Double prSaldo) {
        this.prSaldo = prSaldo;
    }

    public Integer getIdLegajo() {
        return idLegajo;
    }

    public void setIdLegajo(Integer idLegajo) {
        this.idLegajo = idLegajo;
    }
}
