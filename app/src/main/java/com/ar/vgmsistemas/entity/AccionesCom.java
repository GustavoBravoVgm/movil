package com.ar.vgmsistemas.entity;

import java.util.List;

public class AccionesCom {
    /*Constantes*/
    public static Integer TIPO_ACCION_MARCA = 5;
    public static Integer TIPO_ACCION_ARTICULO = 4;
    public static Integer TIPO_ACCION_SUBRUBRO = 3;
    public static Integer TIPO_ACCION_RUBRO = 2;
    public static Integer TIPO_ACCION_NEGOCIO = 1;

    public static String TI_ORIGEN_EMPRESA = "E";
    public static String TI_ORIGEN_PROVEEDOR = "P";
    public static String TI_ORIGEN_CONJUNTA = "C";

    /*Atributos/ columnas en bd*/
    private int idAccionesCom;

    private String snConCodigos;

    private String tiOrigen;

    private Integer idTipoAcciones;

    private List<AccionesComDetalle> accionesComDetalle;

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

    public List<AccionesComDetalle> getAccionesComDetalle() {
        return accionesComDetalle;
    }

    public void setAccionesComDetalle(List<AccionesComDetalle> accionesComDetalle) {
        this.accionesComDetalle = accionesComDetalle;
    }

    public AccionesCom() {
    }

    public AccionesCom(int idAccionesCom, String snConCodigos, String tiOrigen, Integer idTipoAcciones) {
        this.idAccionesCom = idAccionesCom;
        this.snConCodigos = snConCodigos;
        this.tiOrigen = tiOrigen;
        this.idTipoAcciones = idTipoAcciones;
    }
}
