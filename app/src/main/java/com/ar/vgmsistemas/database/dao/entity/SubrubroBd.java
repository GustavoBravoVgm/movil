package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;

import com.ar.vgmsistemas.database.dao.entity.key.PkSubrubroBd;

import java.io.Serializable;

@Entity(tableName = "subrubro",
        primaryKeys = {"id_subrubro", "id_negocio", "id_segmento"})
public class SubrubroBd implements Serializable {
    @NonNull
    @Embedded
    private PkSubrubroBd id;

    @ColumnInfo(name = "de_subrubro")
    private String descripcion;


    /*Getters-Setters*/

    @NonNull
    public PkSubrubroBd getId() {
        return id;
    }

    public void setId(@NonNull PkSubrubroBd id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
