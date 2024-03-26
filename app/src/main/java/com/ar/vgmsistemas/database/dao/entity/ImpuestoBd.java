package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "impuestos")
public class ImpuestoBd implements Serializable {

    /*Atributos/ columnas en bd*/
    @ColumnInfo(name = "id_impuesto")
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "de_impuesto")
    private String descripcion;

    @ColumnInfo(name = "ti_impuesto")
    private int tiImpuesto;

    @ColumnInfo(name = "ta_impuesto")
    private Double taImpuesto;


    /*Getters-Setters*/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getTiImpuesto() {
        return tiImpuesto;
    }

    public void setTiImpuesto(int tiImpuesto) {
        this.tiImpuesto = tiImpuesto;
    }

    public Double getTaImpuesto() {
        return taImpuesto;
    }

    public void setTaImpuesto(Double taImpuesto) {
        this.taImpuesto = taImpuesto;
    }


}
