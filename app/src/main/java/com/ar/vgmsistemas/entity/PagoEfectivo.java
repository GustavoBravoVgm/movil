package com.ar.vgmsistemas.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;


public class PagoEfectivo implements Serializable {
    private static final long serialVersionUID = -7278553284013192623L;

    @SerializedName("id")
    private int id;

    @SerializedName("tipoMoneda")
    private TipoMoneda tipoMoneda;

    @SerializedName("cotizacion")
    private double cotizacion; //cotizacion para esa entrega en particular

    @SerializedName("importe")
    private double importeMoneda;

    private double importeMonedaBase;

    @SerializedName("fecha")
    private Date fecha;

    private Entrega entrega;

    public TipoMoneda getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(TipoMoneda tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public Entrega getEntrega() {
        return entrega;
    }

    public void setEntrega(Entrega entrega) {
        this.entrega = entrega;
    }

    public double getImporteMonedaBase() {
        return importeMonedaBase;
    }

    public void setImporteMonedaBase(double importe) {
        this.importeMonedaBase = importe;
    }

    public double getImporteMonedaCorriente() {
        return this.getImporteMonedaBase();
    }

    public double getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(double cotizacion) {
        this.cotizacion = cotizacion;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getImporteMoneda() {
        return importeMoneda;
    }

    public void setImporteMoneda(double importeMoneda) {
        this.importeMoneda = importeMoneda;
    }
}
