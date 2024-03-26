package com.ar.vgmsistemas.entity.key;

import java.io.Serializable;
import java.util.Objects;


public class PkAccionesComDetalle implements Serializable {

    private int idAccionesCom;

    private int idAccionesComDetalle;

    public PkAccionesComDetalle(int idAccionesCom, int idAccionesComDetalle) {
        this.idAccionesCom = idAccionesCom;
        this.idAccionesComDetalle = idAccionesComDetalle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PkAccionesComDetalle)) return false;
        PkAccionesComDetalle that = (PkAccionesComDetalle) o;
        return getIdAccionesCom() == that.getIdAccionesCom() && getIdAccionesComDetalle() == that.getIdAccionesComDetalle();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdAccionesCom(), getIdAccionesComDetalle());
    }

    /*Getters/Setters*/

    public int getIdAccionesCom() {
        return idAccionesCom;
    }

    public void setIdAccionesCom(int idAccionesCom) {
        this.idAccionesCom = idAccionesCom;
    }

    public int getIdAccionesComDetalle() {
        return idAccionesComDetalle;
    }

    public void setIdAccionesComDetalle(int idAccionesComDetalle) {
        this.idAccionesComDetalle = idAccionesComDetalle;
    }


}
