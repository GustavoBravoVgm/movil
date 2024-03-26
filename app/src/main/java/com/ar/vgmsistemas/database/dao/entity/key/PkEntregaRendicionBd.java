package com.ar.vgmsistemas.database.dao.entity.key;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

import java.io.Serializable;
import java.util.Objects;

public class PkEntregaRendicionBd implements Serializable {
    @ColumnInfo(name = "id_veces")
    private int idVeces;
    @ColumnInfo(name = "id_legajo")
    private int idLegajo;
    @ColumnInfo(name = "fe_entrega")
    @NonNull
    private String feEntrega;

    public PkEntregaRendicionBd() {
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

    public String getFeEntrega() {
        return feEntrega;
    }

    public void setFeEntrega(String feEntrega) {
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

        PkEntregaRendicionBd that = (PkEntregaRendicionBd) o;

        if (idVeces != that.idVeces) return false;
        if (idLegajo != that.idLegajo) return false;
        return Objects.equals(feEntrega, that.feEntrega);

    }
}
