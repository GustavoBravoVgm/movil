package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "acciones_com")
public class AccionesComBd {

    /*Atributos/ columnas en bd*/
    @PrimaryKey
    @ColumnInfo(name = "id_acciones_com")
    private int idAccionesCom;

    @ColumnInfo(name = "sn_con_codigos")
    private String snConCodigos;

    @ColumnInfo(name = "ti_origen")    private String tiOrigen;

    @ColumnInfo(name = "id_tipo_acciones")
    private Integer idTipoAcciones;

    /*Getters-Setters*/
    public int getIdAccionesCom() {
        return idAccionesCom;
    }

    public void setIdAccionesCom(int idAccionesCom) {
        this.idAccionesCom = idAccionesCom;
    }

    public String getSnConCodigos() {
        return snConCodigos;
    }

    public void setSnConCodigos(String snConCodigos) {
        this.snConCodigos = snConCodigos;
    }

    public String getTiOrigen() {
        return tiOrigen;
    }

    public void setTiOrigen(String tiOrigen) {
        this.tiOrigen = tiOrigen;
    }

    public Integer getIdTipoAcciones() {
        return idTipoAcciones;
    }

    public void setIdTipoAcciones(Integer idTipoAcciones) {
        this.idTipoAcciones = idTipoAcciones;
    }

}
