package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "impresora")
public class ImpresoraBd implements Serializable {
    @ColumnInfo(name = "id_impresora")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "de_impresora")
    private String descripcion;

    @ColumnInfo(name = "de_ancho")
    private int tamanioHoja;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String _descripcion) {
        this.descripcion = _descripcion;
    }

    public int getTamanioHoja() {
        return tamanioHoja;
    }

    public void setTamanioHoja(int _tamanioHoja) {
        this.tamanioHoja = _tamanioHoja;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
