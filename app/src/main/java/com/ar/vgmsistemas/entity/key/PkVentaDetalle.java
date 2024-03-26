package com.ar.vgmsistemas.entity.key;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PkVentaDetalle implements Serializable {

    private static final long serialVersionUID = 5229006748665162824L;
    @SerializedName("idDocumento")
    private String idDocumento;

    @SerializedName("idLetra")
    private String idLetra;

    @SerializedName("puntoVenta")
    private int puntoVenta;

    @SerializedName("idNumeroDocumento")
    private long idNumeroDocumento;

    @SerializedName("secuencia")
    private int secuencia;

    public PkVentaDetalle(String idDocumento, String idLetra, int puntoVenta, long idNumeroDocumento, int secuencia) {
        this.idDocumento = idDocumento;
        this.idLetra = idLetra;
        this.puntoVenta = puntoVenta;
        this.idNumeroDocumento = idNumeroDocumento;
        this.secuencia = secuencia;
    }

    public PkVentaDetalle() {
    }

    public String getIdDocumento() {
        return this.idDocumento;
    }

    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getIdLetra() {
        return this.idLetra;
    }

    public void setIdLetra(String idLetra) {
        this.idLetra = idLetra;
    }

    public int getPuntoVenta() {
        return this.puntoVenta;
    }

    public void setPuntoVenta(int puntoVenta) {
        this.puntoVenta = puntoVenta;
    }

    public long getIdNumeroDocumento() {
        return this.idNumeroDocumento;
    }

    public void setIdNumeroDocumento(long idNumeroDocumento) {
        this.idNumeroDocumento = idNumeroDocumento;
    }

    public int getSecuencia() {
        return this.secuencia;
    }

    public void setSecuencia(int secuencia) {
        this.secuencia = secuencia;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return idDocumento + idLetra + puntoVenta + idNumeroDocumento + secuencia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PkVentaDetalle that = (PkVentaDetalle) o;

        if (puntoVenta != that.puntoVenta) return false;
        if (idNumeroDocumento != that.idNumeroDocumento) return false;
        if (secuencia != that.secuencia) return false;
        if (!idDocumento.equals(that.idDocumento)) return false;
        return idLetra.equals(that.idLetra);

    }

    @Override
    public int hashCode() {
        int result = idDocumento.hashCode();
        result = 31 * result + idLetra.hashCode();
        result = 31 * result + (int) (puntoVenta ^ (puntoVenta >>> 32));
        result = 31 * result + (int) (idNumeroDocumento ^ (idNumeroDocumento >>> 32));
        result = 31 * result + (int) (secuencia ^ (secuencia >>> 32));
        return result;
    }
}
