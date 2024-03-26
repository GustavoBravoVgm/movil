package com.ar.vgmsistemas.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReciboDetalle implements Serializable {
    private static final long serialVersionUID = -717012540735698600L;

    @SerializedName("id")
    private int id;

    @SerializedName("importePagado")
    private double importePagado;

    @SerializedName("saldoMovil")
    private Double saldoMovil;

    @SerializedName("taDtoRecibo")
    private double taDtoRecibo;

    private Integer idPago;

    private Double prPago;

    private Double prNc;

    private Double prSaldo;

    private Integer idLegajo;

    @SerializedName("cuentaCorriente")
    private CuentaCorriente cuentaCorriente;

    private Recibo recibo;

    private boolean seActualizaEnBd;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CuentaCorriente getCuentaCorriente() {
        return this.cuentaCorriente;
    }

    public void setCuentaCorriente(CuentaCorriente cuentaCorriente) {
        this.cuentaCorriente = cuentaCorriente;
    }

    public double obtenerSaldoCuentaCorriente() {
        return this.cuentaCorriente.calcularSaldo();
    }

    public void setRecibo(Recibo recibo) {
        this.recibo = recibo;
    }

    public Recibo getRecibo() {
        return recibo;
    }

    public double getImportePagado() {
        return this.importePagado;
    }

    public void setImportePagado(double totalPagado) {
        this.importePagado = totalPagado;
    }

    public Double getSaldoMovil() {
        return saldoMovil;
    }

    public void setSaldoMovil(Double saldoMovil) {
        if (saldoMovil < 0) {
            saldoMovil = saldoMovil * (-1);
        }
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

    public boolean isSeActualizaEnBd() {
        return seActualizaEnBd;
    }

    public void setSeActualizaEnBd(boolean seActualizaEnBd) {
        this.seActualizaEnBd = seActualizaEnBd;
    }
}
