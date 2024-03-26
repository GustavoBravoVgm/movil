package com.ar.vgmsistemas.entity;

public class VersionInfoVendedor {
    private int vendedorID;
    private String docDefectoVenta;
    private int ptoVtaDefectoVenta;
    private String docDefectoRecibo;
    private int ptoVtaDefectoRecibo;
    private int repartidorDefecto;


    public int getVendedorID() {
        return vendedorID;
    }

    public void setVendedorID(int vendedorID) {
        this.vendedorID = vendedorID;
    }

    public String getDocDefectoVenta() {
        return docDefectoVenta;
    }

    public void setDocDefectoVenta(String docDefectoVenta) {
        this.docDefectoVenta = docDefectoVenta;
    }

    public int getPtoVtaDefectoVenta() {
        return ptoVtaDefectoVenta;
    }

    public void setPtoVtaDefectoVenta(int ptoVtaDefectoVenta) {
        this.ptoVtaDefectoVenta = ptoVtaDefectoVenta;
    }

    public String getDocDefectoRecibo() {
        return docDefectoRecibo;
    }

    public void setDocDefectoRecibo(String docDefectoRecibo) {
        this.docDefectoRecibo = docDefectoRecibo;
    }

    public int getPtoVtaDefectoRecibo() {
        return ptoVtaDefectoRecibo;
    }

    public void setPtoVtaDefectoRecibo(int ptoVtaDefectoRecibo) {
        this.ptoVtaDefectoRecibo = ptoVtaDefectoRecibo;
    }

    public int getRepartidorDefecto() {
        return repartidorDefecto;
    }

    public void setRepartidorDefecto(int repartidorDefecto) {
        this.repartidorDefecto = repartidorDefecto;
    }
}
