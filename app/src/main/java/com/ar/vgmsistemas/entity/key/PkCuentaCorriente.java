package com.ar.vgmsistemas.entity.key;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PkCuentaCorriente implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("idDocumento")
    private String idDocumento;

    @SerializedName("idLetra")
    private String idLetra;

    @SerializedName("puntoVenta")
    private int puntoVenta;

    @SerializedName("idNumeroDocumento")
    private long idNumeroDocumento;

    @SerializedName("numeroCuota")
    private int cuota;

    public PkCuentaCorriente(String idDocumento, String idLetra, int puntoVenta, long idNumeroDocumento, int cuota) {
        this.idDocumento = idDocumento;
        this.idLetra = idLetra;
        this.puntoVenta = puntoVenta;
        this.idNumeroDocumento = idNumeroDocumento;
        this.cuota = cuota;
    }

    public PkCuentaCorriente() {
    }

    public void setCuota(int cuota) {
        this.cuota = cuota;
    }

    public int getCuota() {
        return cuota;
    }

    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getIdDocumento() {
        return idDocumento;
    }

    public void setIdLetra(String idLetra) {
        this.idLetra = idLetra;
    }

    public String getIdLetra() {
        return idLetra;
    }

    public void setPuntoVenta(int puntoVenta) {
        this.puntoVenta = puntoVenta;
    }

    public int getPuntoVenta() {
        return puntoVenta;
    }

    public void setIdNumeroDocumento(long idNumeroDocumento) {
        this.idNumeroDocumento = idNumeroDocumento;
    }

    public long getIdNumeroDocumento() {
        return idNumeroDocumento;
    }

    @Override
    public String toString() {
        return idDocumento + idLetra + puntoVenta + "-" + idNumeroDocumento;
    }
}
