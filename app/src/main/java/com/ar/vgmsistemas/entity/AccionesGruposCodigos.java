package com.ar.vgmsistemas.entity;

import java.util.List;

public class AccionesGruposCodigos {

    /*Atributos/ columnas en bd*/
    private int idAccionesGruposCodigo;

    private int idAccionesCom;

    private int idGrupoClie;

    private int idGrupoClieDetalle;

    private AccionesCom accionesCom;

    private List<GrupoClientesDetalle> grupoClientesDetalle;

    /*Getters-Setters*/

    public int getIdAccionesGruposCodigo() {
        return idAccionesGruposCodigo;
    }

    public void setIdAccionesGruposCodigo(int idAccionesGruposCodigo) {
        this.idAccionesGruposCodigo = idAccionesGruposCodigo;
    }

    public int getIdAccionesCom() {
        return idAccionesCom;
    }

    public void setIdAccionesCom(int idAccionesCom) {
        this.idAccionesCom = idAccionesCom;
    }

    public int getIdGrupoClie() {
        return idGrupoClie;
    }

    public void setIdGrupoClie(int idGrupoClie) {
        this.idGrupoClie = idGrupoClie;
    }

    public int getIdGrupoClieDetalle() {
        return idGrupoClieDetalle;
    }

    public void setIdGrupoClieDetalle(int idGrupoClieDetalle) {
        this.idGrupoClieDetalle = idGrupoClieDetalle;
    }
}
