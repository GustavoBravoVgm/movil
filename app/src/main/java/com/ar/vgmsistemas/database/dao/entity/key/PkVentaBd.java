package com.ar.vgmsistemas.database.dao.entity.key;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import java.io.Serializable;

public class PkVentaBd implements Serializable {

    @Ignore
    private static final long serialVersionUID = -6438945780718911018L;

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

    public PkVentaBd() {
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

        PkVentaBd pkVenta = (PkVentaBd) o;

        if (puntoVenta != pkVenta.puntoVenta) return false;
        if (idNumeroDocumento != pkVenta.idNumeroDocumento) return false;
        if (!idDocumento.equals(pkVenta.idDocumento)) return false;
        return idLetra.equals(pkVenta.idLetra);

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
