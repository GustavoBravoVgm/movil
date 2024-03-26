package com.ar.vgmsistemas.entity;

import com.ar.vgmsistemas.entity.key.PkVenta;
import com.ar.vgmsistemas.utils.Formatter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

public class Retencion implements Serializable {
    private static final long serialVersionUID = 4955640278090523195L;

    @SerializedName("id")
    private PkVenta id;

    @SerializedName("planCuenta")
    private PlanCuenta planCuenta;

    @SerializedName("observacion")
    private String observacion;

    @SerializedName("importe")
    private double importe;

    private Date fechaMovil;
    @SerializedName("fecha")
    private String fechaWs;

    private Entrega entrega;

    public Date getFechaMovil() {
        return fechaMovil;
    }

    public void setFechaMovil(Date fechaMovil) {
        this.fechaMovil = fechaMovil;
        try {
            this.setFechaWs(Formatter.formatDateWs(fechaMovil));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Entrega getEntrega() {
        return this.entrega;
    }

    public void setEntrega(Entrega entrega) {
        this.entrega = entrega;
    }

    public Entrega getRecibo() {
        return this.entrega;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double total) {
        this.importe = total;
    }

    public PkVenta getId() {
        return id;
    }

    public void setId(PkVenta id) {
        this.id = id;
    }

    public PlanCuenta getPlanCuenta() {
        return this.planCuenta;
    }

    public void setPlanCuenta(PlanCuenta planCuenta) {
        this.planCuenta = planCuenta;
    }

    public String getObservacion() {
        return this.observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getFechaWs() {
        return fechaWs;
    }

    public void setFechaWs(String fechaWs) {
        this.fechaWs = fechaWs;
    }

}
