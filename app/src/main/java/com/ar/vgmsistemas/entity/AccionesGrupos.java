package com.ar.vgmsistemas.entity;

import java.util.List;

public class AccionesGrupos {
    /*Atributos/ columnas en bd*/

    private int idAccionesGrupos;

    private int idAccionesCom;

    private int idGrupoClie;


    private AccionesCom accionesCom;

    private List<GrupoClientesDetalle> grupoClientesDetalle;

    /*Getters-Setters*/

    public int getIdAccionesGrupos() {
        return idAccionesGrupos;
    }

    public void setIdAccionesGrupos(int idAccionesGrupos) {
        this.idAccionesGrupos = idAccionesGrupos;
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

    public AccionesCom getAccionesCom() {
        return accionesCom;
    }

    public void setAccionesCom(AccionesCom accionesCom) {
        this.accionesCom = accionesCom;
    }

    public List<GrupoClientesDetalle> getGrupoClientesDetalle() {
        return grupoClientesDetalle;
    }

    public void setGrupoClientesDetalle(List<GrupoClientesDetalle> grupoClientesDetalle) {
        this.grupoClientesDetalle = grupoClientesDetalle;
    }
}
