package com.ar.vgmsistemas.entity;

import com.ar.vgmsistemas.utils.Formatter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;


public class Deposito implements Serializable {
    private static final long serialVersionUID = -2928980544022162845L;

    @SerializedName("id")
    private int id;

    private Date fechaDepositoMovil;

    @SerializedName("numeroComprobante")
    private long numeroComprobante;

    @SerializedName("importe")
    private double importe;

    @SerializedName("cantidadCheques")
    private int cantidadCheques;

    @SerializedName("banco")
    private Banco banco;

    private Entrega entrega;


    @SerializedName("fechaDeposito")
    private String fechaWS;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechaDepositoMovil() {
        return fechaDepositoMovil;
    }

    public void setFechaDepositoMovil(Date fechaDepositoMovil) {
        this.fechaDepositoMovil = fechaDepositoMovil;
        try {
            this.fechaWS = Formatter.formatDateWs(fechaDepositoMovil);
        } catch (ParseException e) {
        }

    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public long getNumeroComprobante() {
        return numeroComprobante;
    }

    public void setNumeroComprobante(long nroComprobante) {
        this.numeroComprobante = nroComprobante;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public String getFechaWS() {
        return fechaWS;
    }

    public void setFechaWS(String fechaWS) {
        this.fechaWS = fechaWS;
    }

    public int getCantidadCheques() {
        return cantidadCheques;
    }

    public void setCantidadCheques(int cantidadCheques) {
        this.cantidadCheques = cantidadCheques;
    }

    public Entrega getEntrega() {
        return entrega;
    }

    public void setEntrega(Entrega entrega) {
        this.entrega = entrega;
    }

}
