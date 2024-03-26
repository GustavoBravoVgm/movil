package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "condvta")
public class CondicionVentaBd implements Serializable {
    @ColumnInfo(name = "id_condvta")
    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name = "de_convta")
    private String descripcion;

    @ColumnInfo(name = "sn_controlfiado")
    private String snControlFiado;

    @ColumnInfo(name = "nu_diasatraso")
    private int diasAtraso;

    @ColumnInfo(name = "ta_var1")
    private double tasaDescuento = 0d;

    @ColumnInfo(name = "nu_cuotas")
    private int nuCuotas = 1;

    /*Getters-Setters*/

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setDiasAtraso(int diasAtraso) {
        this.diasAtraso = diasAtraso;
    }

    public int getDiasAtraso() {
        return diasAtraso;
    }

    public double getTasaDescuento() {
        return tasaDescuento;
    }

    public void setTasaDescuento(double _tasaDescuento) {
        this.tasaDescuento = _tasaDescuento;
    }

    public String getSnControlFiado() {
        return snControlFiado;
    }

    public void setSnControlFiado(String snControlFiado) {
        this.snControlFiado = snControlFiado;
    }

    public int getNuCuotas() {
        return nuCuotas;
    }

    public void setNuCuotas(int nuCuotas) {
        this.nuCuotas = nuCuotas;
    }

}
