package com.ar.vgmsistemas.database.dao.entity.key;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import java.io.Serializable;

public class PkReciboBd implements Serializable {

    @Ignore
    private static final long serialVersionUID = 2953004486951129119L;

    @ColumnInfo(name = "id_recpvta")
    private int idPuntoVenta;

    @ColumnInfo(name = "id_recibo")
    private long idRecibo;

    public PkReciboBd() {
    }

    public long getIdRecibo() {
        return this.idRecibo;
    }

    public void setIdRecibo(long idRecibo) {
        this.idRecibo = idRecibo;
    }

    public int getIdPuntoVenta() {
        return this.idPuntoVenta;
    }

    public void setIdPuntoVenta(int idPuntoVenta) {
        this.idPuntoVenta = idPuntoVenta;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + idPuntoVenta;
        result = prime * result + (int) (idRecibo ^ (idRecibo >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PkReciboBd other = (PkReciboBd) obj;
        if (idPuntoVenta != other.idPuntoVenta)
            return false;
        if (idRecibo != other.idRecibo)
            return false;
        return true;
    }

}
