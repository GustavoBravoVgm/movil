package com.ar.vgmsistemas.database.dao.entity.key;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PkRubroBd implements Serializable {
    @Ignore
    private static final long serialVersionUID = 6509685569523286853L;

    @ColumnInfo(name = "id_segmento")
    private int idRubro;

    @ColumnInfo(name = "id_negocio")
    private int idNegocio;


    /*Getters-Setters*/
    public int getIdRubro() {
        return this.idRubro;
    }

    public void setIdRubro(int idSegmento) {
        this.idRubro = idSegmento;
    }

    public int getIdNegocio() {
        return this.idNegocio;
    }

    public void setIdNegocio(int idNegocio) {
        this.idNegocio = idNegocio;
    }

}
