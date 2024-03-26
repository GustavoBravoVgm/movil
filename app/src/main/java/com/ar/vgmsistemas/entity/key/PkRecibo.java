package com.ar.vgmsistemas.entity.key;

import java.io.Serializable;

public class PkRecibo implements Serializable {

    private static final long serialVersionUID = 2953004486951129119L;

    private int idPuntoVenta;

    private long idRecibo;

    public PkRecibo(int idPuntoVenta, long idRecibo) {
        this.idPuntoVenta = idPuntoVenta;
        this.idRecibo = idRecibo;
    }

    public PkRecibo() {
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
        PkRecibo other = (PkRecibo) obj;
        if (idPuntoVenta != other.idPuntoVenta)
            return false;
        if (idRecibo != other.idRecibo)
            return false;
        return true;
    }

}
