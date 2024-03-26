package com.ar.vgmsistemas.entity.key;

import java.io.Serializable;

public class PkVendedorObjetivoDetalle implements Serializable {
    private static final long serialVersionUID = 6509685569523286858L;

    private int idObjetivo;

    private Integer idSecuencia;

    public PkVendedorObjetivoDetalle() {
    }

    public PkVendedorObjetivoDetalle(int idObjetivo, Integer idSecuencia) {
        this.idObjetivo = idObjetivo;
        this.idSecuencia = idSecuencia;
    }

    public int getIdObjetivo() {
        return idObjetivo;
    }

    public void setIdObjetivo(int idObjetivo) {
        this.idObjetivo = idObjetivo;
    }

    public Integer getIdSecuencia() {
        return idSecuencia;
    }

    public void setIdSecuencia(Integer idSecuencia) {
        this.idSecuencia = idSecuencia;
    }

    @Override
    public String toString() {
        return "PkVendedorObjetivoDetalle{" +
                "idObjetivo=" + idObjetivo +
                ", idSecuencia=" + idSecuencia +
                '}';
    }
}
