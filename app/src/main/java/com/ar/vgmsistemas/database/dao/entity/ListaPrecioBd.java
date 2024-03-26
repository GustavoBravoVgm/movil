package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "listasPrecios")
public class ListaPrecioBd implements Serializable {

    @ColumnInfo(name = "id_lista")
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "de_lista")
    private String descripcion;

    @ColumnInfo(name = "ti_lista")
    private int tipoLista;

    @ColumnInfo(name = "id_lista_base")
    private Integer listaBase;

    @ColumnInfo(name = "id_origenprecio")
    private String idOrigenPrecio;

    @ColumnInfo(name = "sn_seleccionable_movil")
    private String snSeleccionable;

    @ColumnInfo(name = "sn_palm")
    private String snMovil;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null) descripcion = "";
        this.descripcion = descripcion;
    }

    public Integer getListaBase() {
        return this.listaBase;
    }

    public void setListaBase(Integer listaBase) {
        this.listaBase = listaBase;
    }

    public int getTipoLista() {
        return tipoLista;
    }

    public void setTipoLista(int tipoLista) {
        this.tipoLista = tipoLista;
    }

    public String getIdOrigenPrecio() {
        return idOrigenPrecio;
    }

    public void setIdOrigenPrecio(String idOrigenPrecio) {
        this.idOrigenPrecio = idOrigenPrecio;
    }

    public String getSnSeleccionable() {
        return snSeleccionable;
    }

    public void setSnSeleccionable(String snSeleccionable) {
        if (snSeleccionable == null) snSeleccionable = "N";
        this.snSeleccionable = snSeleccionable;
    }

    public String getSnMovil() {
        return snMovil;
    }

    public void setSnMovil(String snMovil) {
        if (snMovil == null) snMovil = "N";
        this.snMovil = snMovil;
    }
}
