package com.ar.vgmsistemas.database.dao.entity.key;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import java.io.Serializable;

public class PkVentaDetalleBd implements Serializable {

    @Ignore
    private static final long serialVersionUID = 5229006748665162824L;
    @ColumnInfo(name = "id_fcnc")
    @NonNull
    private String idDocumento;

    @ColumnInfo(name = "id_tipoab")
    @NonNull
    private String idLetra;

    @ColumnInfo(name = "id_ptovta")
    private int puntoVenta;

    @ColumnInfo(name = "id_numdoc")
    private long idNumeroDocumento;

    @ColumnInfo(name = "id_secuencia")
    private int secuencia;


    public PkVentaDetalleBd() {
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

        PkVentaDetalleBd that = (PkVentaDetalleBd) o;

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
