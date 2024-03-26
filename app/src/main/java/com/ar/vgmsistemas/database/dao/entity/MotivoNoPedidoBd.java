package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "motivos_no_pedidos")
public class MotivoNoPedidoBd implements Serializable {
    /*Atributos/ columnas en bd*/
    @ColumnInfo(name = "id_motivo")
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "de_motivo")
    private String descripcion;

    /*Getters-Setters*/
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

}
