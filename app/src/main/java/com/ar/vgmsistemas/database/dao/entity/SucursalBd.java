package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "sucursal")
public class SucursalBd implements Serializable {
    @ColumnInfo(name = "id_sucursal")
    @PrimaryKey
    private int idSucursal;

    @ColumnInfo(name = "de_sucursal")
    private String deSucursal;

    @ColumnInfo(name = "ta_rentabilidad_global")
    private Double taRentabilidadGlobal;

    @ColumnInfo(name = "ti_ctrl_accom")
    private String tiControlAccom;

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getDeSucursal() {
        return deSucursal;
    }

    public void setDeSucursal(String deSucursal) {
        this.deSucursal = deSucursal;
    }

    public Double getTaRentabilidadGlobal() {
        return taRentabilidadGlobal;
    }

    public void setTaRentabilidadGlobal(Double taRentabilidadGlobal) {
        this.taRentabilidadGlobal = taRentabilidadGlobal;
    }

    public String getTiControlAccom() {
        return tiControlAccom;
    }

    public void setTiControlAccom(String tiControlAccom) {
        this.tiControlAccom = tiControlAccom;
    }

    @Override
    public String toString() {
        return this.idSucursal + " - " + this.deSucursal.trim();
    }
}
