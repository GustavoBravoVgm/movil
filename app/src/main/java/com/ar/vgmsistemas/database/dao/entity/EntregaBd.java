package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "entrega")
public class EntregaBd implements Serializable {

    @ColumnInfo(name = "id_entrega")
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "pr_efectivo")
    private double prEfectivoEntrega = 0d;

    @ColumnInfo(name = "pr_cheques")
    private double prChequesEntrega = 0d;

    @ColumnInfo(name = "pr_retencion")
    private double prRetencionesEntrega = 0d;

    @ColumnInfo(name = "pr_deposito")
    private double prDepositoEntrega = 0d;

    /*Getters and Setters*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrEfectivoEntrega() {
        return prEfectivoEntrega;
    }

    public void setPrEfectivoEntrega(double prEfectivoEntrega) {
        this.prEfectivoEntrega = prEfectivoEntrega;
    }

    public double getPrChequesEntrega() {
        return prChequesEntrega;
    }

    public void setPrChequesEntrega(double prChequesEntrega) {
        this.prChequesEntrega = prChequesEntrega;
    }

    public double getPrRetencionesEntrega() {
        return prRetencionesEntrega;
    }

    public void setPrRetencionesEntrega(double prRetencionesEntrega) {
        this.prRetencionesEntrega = prRetencionesEntrega;
    }

    public double getPrDepositoEntrega() {
        return prDepositoEntrega;
    }

    public void setPrDepositoEntrega(double prDepositoEntrega) {
        this.prDepositoEntrega = prDepositoEntrega;
    }

    public EntregaBd() {
    }
}
