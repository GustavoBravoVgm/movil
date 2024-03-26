package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "provincias")
public class ProvinciaBd implements Serializable {
    @ColumnInfo(name = "id_provincia")
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "de_provincia")
    private String descripcion;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return this.id + " - " + this.descripcion.trim();
    }

}
