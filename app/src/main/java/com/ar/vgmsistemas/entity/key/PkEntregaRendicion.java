package com.ar.vgmsistemas.entity.key;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class PkEntregaRendicion implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idVeces;
    private int idLegajo;
    private Date feEntrega;

    public PkEntregaRendicion(int idVeces, int idLegajo, Date feEntrega) {
        this.idVeces = idVeces;
        this.idLegajo = idLegajo;
        this.feEntrega = feEntrega;
    }

    public PkEntregaRendicion() {
    }

    public int getIdVeces() {
        return idVeces;
    }

    public void setIdVeces(int idVeces) {
        this.idVeces = idVeces;
    }

    public int getIdLegajo() {
        return idLegajo;
    }

    public void setIdLegajo(int idLegajo) {
        this.idLegajo = idLegajo;
    }

    public Date getFeEntrega() {
        return feEntrega;
    }

    public void setFeEntrega(Date feEntrega) {
        this.feEntrega = feEntrega;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((feEntrega == null) ? 0 : feEntrega.hashCode());
        result = prime * result + Integer.parseInt(String.valueOf(idLegajo));
        result = prime * result + idVeces;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PkEntregaRendicion that = (PkEntregaRendicion) o;

        if (idVeces != that.idVeces) return false;
        if (idLegajo != that.idLegajo) return false;
        return Objects.equals(feEntrega, that.feEntrega);
    }
}
