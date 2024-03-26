package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;

import com.ar.vgmsistemas.database.dao.entity.key.PkRubroBd;

import java.io.Serializable;

@Entity(tableName = "segmento",
        primaryKeys = {"id_segmento", "id_negocio"})
public class RubroBd implements Serializable {
    @NonNull
    @Embedded
    private PkRubroBd id;

    @ColumnInfo(name = "de_segmento")
    private String descripcion;

    /*Getters-Setters*/

    @NonNull
    public PkRubroBd getId() {
        return id;
    }

    public void setId(@NonNull PkRubroBd id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
