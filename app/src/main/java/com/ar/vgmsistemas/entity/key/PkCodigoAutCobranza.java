package com.ar.vgmsistemas.entity.key;

import java.io.Serializable;

public class PkCodigoAutCobranza implements Serializable {
    private static final long serialVersionUID = -6438945780718911018L;

    private String idDocumento;

    private String idLetra;

    private int puntoVenta;

    private long idNumeroDocumento;

    public PkCodigoAutCobranza(String idDocumento, String idLetra, int puntoVenta, long idNumeroDocumento) {
        this.idDocumento = idDocumento;
        this.idLetra = idLetra;
        this.puntoVenta = puntoVenta;
        this.idNumeroDocumento = idNumeroDocumento;
    }

    public PkCodigoAutCobranza() {
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

    public String toString() {
        return idDocumento + "-" + idLetra + "-" + puntoVenta + "-" + idNumeroDocumento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PkCodigoAutCobranza pkCodigoAutCobranza = (PkCodigoAutCobranza) o;

        if (puntoVenta != pkCodigoAutCobranza.puntoVenta) return false;
        if (idNumeroDocumento != pkCodigoAutCobranza.idNumeroDocumento) return false;
        if (!idDocumento.equals(pkCodigoAutCobranza.idDocumento)) return false;
        return idLetra.equals(pkCodigoAutCobranza.idLetra);

    }

    @Override
    public int hashCode() {
        int result = idDocumento.hashCode();
        result = 31 * result + idLetra.hashCode();
        result = 31 * result + puntoVenta;
        result = 31 * result + (int) (idNumeroDocumento ^ (idNumeroDocumento >>> 32));
        return result;
    }
}
