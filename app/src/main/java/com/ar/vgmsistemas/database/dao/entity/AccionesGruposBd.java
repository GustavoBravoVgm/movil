package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "acciones_grupos")
public class AccionesGruposBd {
    /*Atributos/ columnas en bd*/

    @PrimaryKey
    @ColumnInfo(name = "id_acciones_grupos")
    private int idAccionesGrupos;

    @ColumnInfo(name = "id_acciones_com")
    private Integer idAccionesCom;

    @ColumnInfo(name = "id_grupo_clie")
    private Integer idGrupoClie;

    /*Getters-Setters*/

    public int getIdAccionesGrupos() {
        return idAccionesGrupos;
    }

    public void setIdAccionesGrupos(int idAccionesGrupos) {
        this.idAccionesGrupos = idAccionesGrupos;
    }

    public Integer getIdAccionesCom() {
        return idAccionesCom;
    }

    public void setIdAccionesCom(Integer idAccionesCom) {
        this.idAccionesCom = idAccionesCom;
    }

    public Integer getIdGrupoClie() {
        return idGrupoClie;
    }

    public void setIdGrupoClie(Integer idGrupoClie) {
        this.idGrupoClie = idGrupoClie;
    }
}
