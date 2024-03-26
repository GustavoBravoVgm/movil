package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "plancta")
public class PlanCuentaBd implements Serializable {
    @ColumnInfo(name = "id_plancta")
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "de_plancta")
    private String descripcion;

    @ColumnInfo(name = "id_categoria2")
    private Integer categoria;

    @ColumnInfo(name = "sn_egreso_movil")
    private String snEgresoMovil;

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

    public Integer getCategoria() {
        return categoria;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    public String getSnEgresoMovil() {
        return snEgresoMovil;
    }

    public void setSnEgresoMovil(String snEgresoMovil) {
        this.snEgresoMovil = snEgresoMovil;
    }
}
