package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "vend_objetivos")
public class VendedorObjetivoBd implements Serializable {
    @ColumnInfo(name = "id_objetivo")
    @PrimaryKey
    private int idObjetivo;

    @ColumnInfo(name = "id_vendedor")
    private int idVendedor;

    @ColumnInfo(name = "ti_objetivo")
    private int tiObjetivo;

    @ColumnInfo(name = "fe_desde")
    private String feDesde;

    @ColumnInfo(name = "fe_hasta")
    private String feHasta;

    @ColumnInfo(name = "id_proveedor")
    private int idProveedor;

    @ColumnInfo(name = "sn_aplica")
    private String snAplica;

    @ColumnInfo(name = "ti_categoria")
    private int tiCategoria;

    /*Getters-Setters*/

    public int getIdObjetivo() {
        return idObjetivo;
    }

    public void setIdObjetivo(int idObjetivo) {
        this.idObjetivo = idObjetivo;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public int getTiObjetivo() {
        return tiObjetivo;
    }

    public void setTiObjetivo(int tiObjetivo) {
        this.tiObjetivo = tiObjetivo;
    }

    public String getFeDesde() {
        return feDesde;
    }

    public void setFeDesde(String feDesde) {
        this.feDesde = feDesde;
    }

    public String getFeHasta() {
        return feHasta;
    }

    public void setFeHasta(String feHasta) {
        this.feHasta = feHasta;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getSnAplica() {
        return snAplica;
    }

    public void setSnAplica(String snAplica) {
        this.snAplica = snAplica;
    }

    public int getTiCategoria() {
        return tiCategoria;
    }

    public void setTiCategoria(int tiCategoria) {
        this.tiCategoria = tiCategoria;
    }
}
