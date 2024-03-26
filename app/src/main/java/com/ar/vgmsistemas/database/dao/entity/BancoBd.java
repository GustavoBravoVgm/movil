package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "banco",
        indices = {@Index(name = "bco_idx_de_denominacion",value = {"de_denominacion"})})
public class BancoBd implements Serializable {

    @ColumnInfo(name = "id_bancogirado")
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "de_denominacion")
    private String denominacion;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDenominacion() {
        return this.denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

}
