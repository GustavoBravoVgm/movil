package com.ar.vgmsistemas.database.dao.entity.key;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import java.io.Serializable;

public class PkVendedorObjetivoDetalleBd implements Serializable {
    @Ignore
    private static final long serialVersionUID = 6509685569523286858L;

    @ColumnInfo(name = "id_objetivo")
    private int idObjetivo;

    @ColumnInfo(name = "id_secuencia")
    private int idSecuencia;

    public int getIdObjetivo() {
        return idObjetivo;
    }

    public void setIdObjetivo(int idObjetivo) {
        this.idObjetivo = idObjetivo;
    }

    public int getIdSecuencia() {
        return idSecuencia;
    }

    public void setIdSecuencia(int idSecuencia) {
        this.idSecuencia = idSecuencia;
    }

    @Override
    public String toString() {
        return "PkVendedorObjetivoDetalleBd{" +
                "idObjetivo=" + idObjetivo +
                ", idSecuencia=" + idSecuencia +
                '}';
    }
}
