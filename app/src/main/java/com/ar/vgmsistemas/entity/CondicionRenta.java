package com.ar.vgmsistemas.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CondicionRenta implements Serializable {
    private static final long serialVersionUID = -6987230094140976511L;

    @SerializedName("id")
    private int id;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("tasaDgr")
    private double tasaDgr;

    @SerializedName("provincia")
    private Provincia provincia;

    private Integer tipoCalculo;

    private Double montoMinimoImpuestoDgr;

    private String snAplicaANc;

    private Double montoMinimoARetener;


    public static final long TIPO_CALCULO_CORRIENTES = 1;

    public static final long TIPO_CALCULO_CHACO = 2;

    public static final long TIPO_CALCULO_MISIONES = 3;

    public static final long TIPO_CALCULO_FORMOSA = 4;

    public static final long TIPO_CALCULO_POR_ARTICULO = 5;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null) {
            descripcion = "";
        }
        this.descripcion = descripcion;
    }

    public double getTasaDgr() {
        return this.tasaDgr;
    }

    public void setTasaDgr(double tasaDgr) {
        this.tasaDgr = tasaDgr;
    }

    //public Provincia getProvincia() {		return provincia;	}
    public Integer getTipoCalculo() {
        return tipoCalculo;
    }

    public void setTipoCalculo(Integer tipoCalculo) {
        this.tipoCalculo = tipoCalculo;
    }

    public Double getMontoMinimoImpuestoDgr() {
        return montoMinimoImpuestoDgr;
    }

    public void setMontoMinimoImpuestoDgr(Double montoMinimoImpuestoDgr) {
        this.montoMinimoImpuestoDgr = montoMinimoImpuestoDgr;
    }

    public String getSnAplicaANc() {
        return snAplicaANc;
    }

    public void setSnAplicaANc(String snAplicaANc) {
        if (snAplicaANc == null || snAplicaANc.equals("")) {
            snAplicaANc = "N";
        }
        this.snAplicaANc = snAplicaANc;
    }

    public Double getMontoMinimoARetener() {
        return montoMinimoARetener;
    }

    public void setMontoMinimoARetener(Double montoMinimoARetener) {
        this.montoMinimoARetener = montoMinimoARetener;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public CondicionRenta(int id, String descripcion, double tasaDgr, Integer tipoCalculo,
                          Double montoMinimoImpuestoDgr, String snAplicaANc, Double montoMinimoARetener) {
        this.id = id;
        this.descripcion = descripcion == null ? "" : descripcion;
        this.tasaDgr = tasaDgr;
        this.tipoCalculo = tipoCalculo;
        this.montoMinimoImpuestoDgr = montoMinimoImpuestoDgr;
        this.snAplicaANc = (snAplicaANc == null || snAplicaANc.equals("")) ? "N" : snAplicaANc;
        this.montoMinimoARetener = montoMinimoARetener;
    }

    public CondicionRenta() {
    }
}
