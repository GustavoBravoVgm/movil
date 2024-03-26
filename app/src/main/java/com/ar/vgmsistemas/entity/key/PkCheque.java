package com.ar.vgmsistemas.entity.key;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PkCheque implements Serializable {
    private static final long serialVersionUID = -7590950414620242872L;

    @SerializedName("idBanco")
    private int idBanco;

    @SerializedName("numeroCheque")
    private long numeroCheque;

    @SerializedName("idPostal")
    private int idPostal;

    @SerializedName("sucursal") //Sucursal del banco
    private int sucursal;

    @SerializedName("nroCuenta")
    private long nroCuenta;

    public int getIdBanco() {
        return this.idBanco;
    }

    public void setIdBanco(int idBanco) {
        this.idBanco = idBanco;
    }

    public long getNumeroCheque() {
        return this.numeroCheque;
    }

    public void setNumeroCheque(long numeroCheque) {
        this.numeroCheque = numeroCheque;
    }

    public int getIdPostal() {
        return this.idPostal;
    }

    public void setIdPostal(int idPostal) {
        this.idPostal = idPostal;
    }

    public long getNroCuenta() {
        return nroCuenta;
    }

    public void setNroCuenta(long nroCuenta) {
        this.nroCuenta = nroCuenta;
    }

    public int getSucursal() {
        return sucursal;
    }

    public void setSucursal(int sucursal) {
        this.sucursal = sucursal;
    }

    public PkCheque(int idBanco, long numeroCheque, int idPostal, int sucursal, long nroCuenta) {
        this.idBanco = idBanco;
        this.numeroCheque = numeroCheque;
        this.idPostal = idPostal;
        this.sucursal = sucursal;
        this.nroCuenta = nroCuenta;
    }

    public PkCheque() {
    }
}
