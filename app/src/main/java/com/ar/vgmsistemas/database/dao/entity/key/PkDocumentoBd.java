package com.ar.vgmsistemas.database.dao.entity.key;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import java.io.Serializable;

public class PkDocumentoBd implements Serializable {
    @Ignore
    private static final long serialVersionUID = 5966053595981650798L;

    @ColumnInfo(name = "id_doc")
    @NonNull
    private String idDocumento;

    @ColumnInfo(name = "id_letra")
    @NonNull
    private String idLetra;

    @ColumnInfo(name = "id_ptovta")
    private int puntoVenta;

    public PkDocumentoBd() {
    }

    public String getIdDocumento() {
        return this.idDocumento;
    }

    public void setIdDocumento(final String idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getIdLetra() {
        return this.idLetra;
    }

    public void setIdLetra(final String idLetra) {
        this.idLetra = idLetra;
    }

    public int getPuntoVenta() {
        return this.puntoVenta;
    }

    public void setPuntoVenta(final int puntoVenta) {
        this.puntoVenta = puntoVenta;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime
                * result
                + ((this.idDocumento == null) ? 0 : this.idDocumento
                .hashCode());
        result = prime * result
                + ((this.idLetra == null) ? 0 : this.idLetra.hashCode());
        result = prime * result
                + (int) (this.puntoVenta ^ (this.puntoVenta >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof PkDocumentoBd))
            return false;
        PkDocumentoBd other = (PkDocumentoBd) obj;
        if (this.idDocumento == null) {
            if (other.idDocumento != null)
                return false;
        } else if (!this.idDocumento.equals(other.idDocumento))
            return false;
        if (this.idLetra == null) {
            if (other.idLetra != null)
                return false;
        } else if (!this.idLetra.equals(other.idLetra))
            return false;
        if (this.puntoVenta != other.puntoVenta)
            return false;
        return true;
    }

}
