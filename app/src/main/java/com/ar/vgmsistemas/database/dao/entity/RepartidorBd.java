package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "repartidor")
public class RepartidorBd {
    @ColumnInfo(name = "id_repartidor")
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "sn_movil")
    public String snMovil = "sn_movil";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSnMovil() {
        return snMovil;
    }

    public void setSnMovil(String snMovil) {
        this.snMovil = snMovil;
    }
}
