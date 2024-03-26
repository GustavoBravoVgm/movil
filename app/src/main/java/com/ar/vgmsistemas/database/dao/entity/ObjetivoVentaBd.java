package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;

import com.ar.vgmsistemas.database.dao.entity.key.PkObjetivoVentaBd;

import java.io.Serializable;
import java.util.Observable;

@Entity(tableName = "objetivos_venta",
        primaryKeys = {"id_articulos", "id_sucursal", "id_cliente",
                "id_comercio", "id_objetivo"})
public class ObjetivoVentaBd extends Observable implements Serializable {
    @NonNull
    @Embedded
    private PkObjetivoVentaBd id;

    @ColumnInfo(name = "cantidad")
    private double cantidad;

    @ColumnInfo(name = "cantidad_vendida")
    private double cantidadVendida;

    /*Getters-Setters*/

    @NonNull
    public PkObjetivoVentaBd getId() {
        return id;
    }

    public void setId(@NonNull PkObjetivoVentaBd id) {
        this.id = id;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(double cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }
}

