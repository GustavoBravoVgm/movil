package com.ar.vgmsistemas.entity;

import java.io.Serializable;
import java.util.Date;

public class Hoja implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String PRERENDIDO = "P";
    public static final String APROBADA = "A";
    public static final String MOVIL = "M";

    private int idHoja;

    private int idSucursal;

    private Date feHoja;

    private int idDeposito;

    private double prContado;

    private double prFiado;

    private double prPendiente;

    private double prAnulado;

    private double prNc;

    private double prTotal;

    private String tiEstado;

    private String isRendida;

    public int getIdHoja() {
        return idHoja;
    }

    public void setIdHoja(int idHoja) {
        this.idHoja = idHoja;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public Date getFeHoja() {
        return feHoja;
    }

    public void setFeHoja(Date feHoja) {
        if (feHoja != null) {
            this.feHoja = feHoja;
        }
    }

    public int getIdDeposito() {
        return idDeposito;
    }

    public void setIdDeposito(int idDeposito) {
        this.idDeposito = idDeposito;
    }

    public double getPrContado() {
        return prContado;
    }

    public void setPrContado(double prContado) {
        this.prContado = prContado;
    }

    public double getPrFiado() {
        return prFiado;
    }

    public void setPrFiado(double prFiado) {
        this.prFiado = prFiado;
    }

    public double getPrPendiente() {
        return prPendiente;
    }

    public void setPrPendiente(double prPendiente) {
        this.prPendiente = prPendiente;
    }

    public double getPrAnulado() {
        return prAnulado;
    }

    public void setPrAnulado(double prAnulado) {
        this.prAnulado = prAnulado;
    }

    public double getPrNc() {
        return prNc;
    }

    public void setPrNc(double prNc) {
        this.prNc = prNc;
    }

    public double getPrTotal() {
        return prTotal;
    }

    public void setPrTotal(double prTotal) {
        this.prTotal = prTotal;
    }

    public String getTiEstado() {
        return tiEstado;
    }

    public void setTiEstado(String tiEstado) {
        this.tiEstado = tiEstado;
    }

    public String getIsRendida() {
        return isRendida;
    }

    public void setIsRendida(String isRendida) {
        this.isRendida = isRendida;
    }
}
