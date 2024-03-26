package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "acciones_grupos_codigos")
public class AccionesGruposCodigosBd {

    /*Atributos/ columnas en bd*/
    @PrimaryKey
    @ColumnInfo(name = "id_acciones_grupos_codigo")
    private int idAccionesGruposCodigo;

    @ColumnInfo(name = "id_acciones_com")
    private Integer idAccionesCom;

    @ColumnInfo(name = "id_grupo_clie")
    private Integer idGrupoClie;

    @ColumnInfo(name = "id_grupo_clie_detalle")
    private Integer idGrupoClieDetalle;

    /*Getters-Setters*/

    public int getIdAccionesGruposCodigo() {
        return idAccionesGruposCodigo;
    }

    public void setIdAccionesGruposCodigo(int idAccionesGruposCodigo) {
        this.idAccionesGruposCodigo = idAccionesGruposCodigo;
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

    public Integer getIdGrupoClieDetalle() {
        return idGrupoClieDetalle;
    }

    public void setIdGrupoClieDetalle(Integer idGrupoClieDetalle) {
        this.idGrupoClieDetalle = idGrupoClieDetalle;
    }
}
