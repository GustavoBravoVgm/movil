package com.ar.vgmsistemas.entity;

import android.location.Location;

import java.util.Date;

public class Movimiento {
    /*Constantes*/
    public static final String ALTA = "A";
    public static final String BAJA = "B";
    public static final String MODIFICACION = "M";

    /*Atributos/ columnas en bd*/
    private int id;

    private String tipo;

    private String tabla;

    private String idMovil;

    private Date fechaSincronizacion;

    private Date fechaMovimiento;

    private double nuLatitudMovimiento;

    private double nuLongitudMovimiento;

    private Date fechaAnulacion;

    private boolean isModificado;

    private int idSucursal;

    private int idCliente;

    private int idComercio;

    private Location location;

    /*Getters-Setters*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getIdMovil() {
        return idMovil;
    }

    public void setIdMovil(String idMovil) {
        this.idMovil = idMovil;
    }

    public Date getFechaSincronizacion() {
        return fechaSincronizacion;
    }

    public void setFechaSincronizacion(Date fechaSincronizacion) {
        this.fechaSincronizacion = fechaSincronizacion;
    }

    public Date getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(Date fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public double getNuLatitudMovimiento() {
        return nuLatitudMovimiento;
    }

    public void setNuLatitudMovimiento(double nuLatitudMovimiento) {
        this.nuLatitudMovimiento = nuLatitudMovimiento;
    }

    public double getNuLongitudMovimiento() {
        return nuLongitudMovimiento;
    }

    public void setNuLongitudMovimiento(double nuLongitudMovimiento) {
        this.nuLongitudMovimiento = nuLongitudMovimiento;
    }

    public Date getFechaAnulacion() {
        return fechaAnulacion;
    }

    public void setFechaAnulacion(Date fechaAnulacion) {
        this.fechaAnulacion = fechaAnulacion;
    }

    public boolean getIsModificado() {
        return isModificado;
    }

    public void setIsModificado(boolean modificado) {
        isModificado = modificado;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdComercio() {
        return idComercio;
    }

    public void setIdComercio(int idComercio) {
        this.idComercio = idComercio;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
