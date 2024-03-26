package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "promo_requisitos")
public class PromocionRequisitoBd {

    @ColumnInfo(name = "id_promo")
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "ca_unidades")
    private int cantUnidades;

    @ColumnInfo(name = "ca_bultos")
    private int cantBultos;

    @ColumnInfo(name = "ca_articulos")
    private int cantArticulos;

    @ColumnInfo(name = "id_articulos")
    private int idArticulos;

    @ColumnInfo(name = "id_articulos1")
    private int idArticulos1;

    @ColumnInfo(name = "id_articulos2")
    private int idArticulos2;

    @ColumnInfo(name = "ca_articulos_ingreso")
    private int caArticulosIngreso;

    @ColumnInfo(name = "ti_requisito")
    private int tipoRequisito;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantArticulos() {
        return cantArticulos;
    }

    public void setCantArticulos(int cantArticulos) {
        this.cantArticulos = cantArticulos;
    }

    public int getCantBultos() {
        return cantBultos;
    }

    public void setCantBultos(int cantBultos) {
        this.cantBultos = cantBultos;
    }

    public int getCantUnidades() {
        return cantUnidades;
    }

    public void setCantUnidades(int cantUnidades) {
        this.cantUnidades = cantUnidades;
    }

    public int getIdArticulos() {
        return idArticulos;
    }

    public void setIdArticulos(int idArticulos) {
        this.idArticulos = idArticulos;
    }

    public int getIdArticulos1() {
        return idArticulos1;
    }

    public void setIdArticulos1(int idArticulos1) {
        this.idArticulos1 = idArticulos1;
    }

    public int getIdArticulos2() {
        return idArticulos2;
    }

    public void setIdArticulos2(int idArticulos2) {
        this.idArticulos2 = idArticulos2;
    }

    public int getCaArticulosIngreso() {
        return caArticulosIngreso;
    }

    public void setCaArticulosIngreso(int caArticulosIngreso) {
        this.caArticulosIngreso = caArticulosIngreso;
    }

    public int getTipoRequisito() {
        return tipoRequisito;
    }

    public void setTipoRequisito(int tipoRequisito) {
        this.tipoRequisito = tipoRequisito;
    }
}
