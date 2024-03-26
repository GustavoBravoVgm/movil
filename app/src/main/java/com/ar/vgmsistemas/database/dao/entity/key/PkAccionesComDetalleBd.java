package com.ar.vgmsistemas.database.dao.entity.key;

import androidx.room.ColumnInfo;

import java.io.Serializable;
import java.util.Objects;


public class PkAccionesComDetalleBd implements Serializable {

    @ColumnInfo(name = "id_acciones_com")
    private int idAccionesCom;

    @ColumnInfo(name = "id_acciones_com_detalle")
    private int idAccionesComDetalle;

    public PkAccionesComDetalleBd(int idAccionesCom, int idAccionesComDetalle) {
        this.idAccionesCom = idAccionesCom;
        this.idAccionesComDetalle = idAccionesComDetalle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PkAccionesComDetalleBd)) return false;
        PkAccionesComDetalleBd that = (PkAccionesComDetalleBd) o;
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
