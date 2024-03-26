package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import java.io.Serializable;

@Entity(tableName = "hoja", primaryKeys = {"id_hoja", "id_sucursal"})
public class HojaBd implements Serializable {
    @ColumnInfo(name = "id_hoja")
    private int idHoja;

    @ColumnInfo(name = "id_sucursal")
    private int idSucursal;

    @ColumnInfo(name = "fe_hoja")
    private String feHoja;

    @ColumnInfo(name = "id_deposito")
    private int idDeposito;

    @ColumnInfo(name = "pr_contado")
    private double prContado = 0d;

    @ColumnInfo(name = "pr_fiado")
    private double prFiado = 0d;

    @ColumnInfo(name = "pr_pendiente")
    private double prPendiente = 0d;

    @ColumnInfo(name = "pr_anulado")
    private double prAnulado = 0d;

    @ColumnInfo(name = "pr_nc")
    private double prNc = 0d;

    @ColumnInfo(name = "pr_total")
    private double prTotal = 0d;

    @ColumnInfo(name = "ti_estado")
    private String tiEstado;

    @ColumnInfo(name = "sn_rendida")
    private String isRendida;

    public int getIdHoja() {
        return idHoja;
    }

    public void setIdHoja(int idHoja) {
        this.idHoja = idHoja;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getFeHoja() {
        return feHoja;
    }

    public void setFeHoja(String feHoja) {
        if (feHoja != null) {
            this.feHoja = feHoja;
        }
    }

    public int getIdDeposito() {
        return idDeposito;
    }

    public void setIdDeposito(int idDeposito) {
        this.idDeposito = idDeposito;
    }

    public double getPrContado() {
        return prContado;
    }

    public void setPrContado(double prContado) {
        this.prContado = prContado;
    }

    public double getPrFiado() {
        return prFiado;
    }

    public void setPrFiado(double prFiado) {
        this.prFiado = prFiado;
    }

    public double getPrPendiente() {
        return prPendiente;
    }

    public void setPrPendiente(double prPendiente) {
        this.prPendiente = prPendiente;
    }

    public double getPrAnulado() {
        return prAnulado;
    }

    public void setPrAnulado(double prAnulado) {
        this.prAnulado = prAnulado;
    }

    public double getPrNc() {
        return prNc;
    }

    public void setPrNc(double prNc) {
        this.prNc = prNc;
    }

    public double getPrTotal() {
        return prTotal;
    }

    public void setPrTotal(double prTotal) {
        this.prTotal = prTotal;
    }

    public String getTiEstado() {
        return tiEstado;
    }

    public void setTiEstado(String tiEstado) {
        this.tiEstado = tiEstado;
    }

    public String getIsRendida() {
        return isRendida;
    }

    public void setIsRendida(String isRendida) {
        this.isRendida = isRendida;
    }
}
