package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mtvanulacionvta")
public class MotivoAnulacionVentaBd {
    @PrimaryKey
    @ColumnInfo(name = "id_motivo")
    public int motivoId;

    @ColumnInfo(name = "de_motivo")
    public String descripcion;

    public int getMotivoId() {
        return motivoId;
    }

    public void setMotivoId(int motivoId) {
        this.motivoId = motivoId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
