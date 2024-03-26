package com.ar.vgmsistemas.database.dao.entity.key;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import java.io.Serializable;

public class PkCodigoAutCobranzaBd implements Serializable {

    @Ignore
    private static final long serialVersionUID = -6438945780718911018L;

    @ColumnInfo(name = "id_doc")
    @NonNull
    private String idDocumento;

    @ColumnInfo(name = "id_letra")
    @NonNull
    private String idLetra;

    @ColumnInfo(name = "id_ptovta")
    private int puntoVenta;

    @ColumnInfo(name = "id_numero")
    private long idNumeroDocumento;

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

        PkCodigoAutCobranzaBd pkCodigoAutCobranza = (PkCodigoAutCobranzaBd) o;

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
