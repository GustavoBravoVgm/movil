package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "motivos_rechazo_nc")
public class MotivoCreditoBd implements Serializable {
    /*Atributos/ columnas en bd*/
    @ColumnInfo(name = "id_motivo_rechazo_nc")
    @PrimaryKey
    private int idMotivoRechazoNC;

    @ColumnInfo(name = "de_motivo_rechazo_nc")
    private String descripcion;

    /*Getters-Setters*/
    public int getIdMotivoRechazoNC() {
        return idMotivoRechazoNC;
    }

    public void setIdMotivoRechazoNC(int idMotivoRechazoNC) {
        this.idMotivoRechazoNC = idMotivoRechazoNC;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
