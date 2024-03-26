package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "pagosEfectivo")
public class PagoEfectivoBd implements Serializable {
    @ColumnInfo(name = "id_pago_efectivo")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "id_valor")
    private int idValor;

    @ColumnInfo(name = "ta_cotizacion")
    private double cotizacion; //cotizacion para esa entrega en particular

    @ColumnInfo(name = "pr_moneda_extranjera")
    private double importeMoneda;

    @ColumnInfo(name = "pr_importe")
    private double importeMonedaBase;

    @ColumnInfo(name = "id_entrega")
    private Integer idEntrega;

    /*Getters-Setters*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdValor() {
        return idValor;
    }

    public void setIdValor(int idValor) {
        this.idValor = idValor;
    }

    public double getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(double cotizacion) {
        this.cotizacion = cotizacion;
    }

    public double getImporteMoneda() {
        return importeMoneda;
    }

    public void setImporteMoneda(double importeMoneda) {
        this.importeMoneda = importeMoneda;
    }

    public double getImporteMonedaBase() {
        return importeMonedaBase;
    }

    public void setImporteMonedaBase(double importeMonedaBase) {
        this.importeMonedaBase = importeMonedaBase;
    }

    public Integer getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(Integer idEntrega) {
        this.idEntrega = idEntrega;
    }
}
