package com.ar.vgmsistemas.entity;

import java.io.Serializable;

public class Sucursal implements Serializable {

    private static final long serialVersionUID = 4939769535224766428L;
    public static String TI_CONTROL_CON_AUTORIZACION = "CA";
    public static String TI_CONTROL_NO_RETIENE = "NR";
    public static String TI_CONTROL_SACA_DESCUENTO = "SD";

    private int idSucursal;

    private String deSucursal;

    private Double taRentabilidadGlobal;

    private String tiControlAccom;

    public Sucursal(int idSucursal, String deSucursal, Double taRentabilidadGlobal, String tiControlAccom) {
        this.idSucursal = idSucursal;
        this.deSucursal = deSucursal;
        this.taRentabilidadGlobal = taRentabilidadGlobal;
        this.tiControlAccom = tiControlAccom;
    }

    public Sucursal() {
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getDeSucursal() {
        return deSucursal;
    }

    public void setDeSucursal(String deSucursal) {
        this.deSucursal = deSucursal;
    }

    public Double getTaRentabilidadGlobal() {
        return taRentabilidadGlobal;
    }

    public void setTaRentabilidadGlobal(Double taRentabilidadGlobal) {
        this.taRentabilidadGlobal = taRentabilidadGlobal;
    }

    public String getTiControlAccom() {
        return tiControlAccom;
    }

    public void setTiControlAccom(String tiControlAccom) {
        this.tiControlAccom = tiControlAccom;
    }

    @Override
    public String toString() {
        return this.idSucursal + " - " + this.deSucursal.trim();
    }
}
