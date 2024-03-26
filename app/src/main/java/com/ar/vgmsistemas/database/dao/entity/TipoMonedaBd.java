package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "valores")
public class TipoMonedaBd implements Serializable {
    @ColumnInfo(name = "id_valor")
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "de_valor")
    private String descripcion;

    @ColumnInfo(name = "ta_cotizacion")
    private double cotizacion;

    @ColumnInfo(name = "sn_moneda_corriente")
    private String snMonedaCorriente;

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

    public double getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(double cotizacion) {
        this.cotizacion = cotizacion;
    }

    public String getSnMonedaCorriente() {
        return snMonedaCorriente;
    }

    public void setSnMonedaCorriente(String snMonedaCorriente) {
        this.snMonedaCorriente = snMonedaCorriente;
    }
}
