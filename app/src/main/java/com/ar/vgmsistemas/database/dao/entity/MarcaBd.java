package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "linea") /*-->se borra el entity Linea porque estaba duplicado*/
public class MarcaBd implements Serializable {
    /*Atributos/ columnas en bd*/
    @ColumnInfo(name = "id_linea")
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "de_linea")
    private String descripcion;

    /*Getters-Setters*/

    public int getId() {
        return id;
    }

    public void setId(int _id) {
        this.id = _id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String _descripcion) {
        this.descripcion = _descripcion;
    }
}
