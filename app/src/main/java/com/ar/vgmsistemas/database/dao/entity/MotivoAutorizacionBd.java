package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "motivos_autorizacion")
public class MotivoAutorizacionBd implements Serializable {
    /*Atributos/ columnas en bd*/
    @ColumnInfo(name = "id_motivo_autoriza")
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "de_motivo_autoriza")
    private String motivo;

    @ColumnInfo(name = "ti_autorizacion")
    private String tiAutorizacion;

    /*Getters-Setters*/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getTiAutorizacion() {
        return tiAutorizacion;
    }

    public void setTiAutorizacion(String tiAutorizacion) {
        this.tiAutorizacion = tiAutorizacion;
    }

}
