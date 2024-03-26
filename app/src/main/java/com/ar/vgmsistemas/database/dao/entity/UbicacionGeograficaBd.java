package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Observable;

@Entity(tableName = "ubicacion_geografica")
public class UbicacionGeograficaBd extends Observable implements Serializable {
    @ColumnInfo(name = "id")
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "id_legajo")
    private int idLegajo;

    @ColumnInfo(name = "fe_posicion")
    private String fechaPosicionMovil;

    @ColumnInfo(name = "nu_latitud")
    private Double latitud;

    @ColumnInfo(name = "nu_longitud")
    private Double longitud;

    @ColumnInfo(name = "nu_precision")
    private Double precision;

    @ColumnInfo(name = "nu_velocidad")
    private Double velocidad;

    @ColumnInfo(name = "nu_altitud")
    private Double altitud;

    @ColumnInfo(name = "ti_operacion")
    private String tipoOperacion;

    @ColumnInfo(name = "id_movil")
    private String idMovil;

    @ColumnInfo(name = "fe_sincronizacion")
    private String fechaSincronizacion;

    @ColumnInfo(name = "device_id")
    private String deviceId;

    @ColumnInfo(name = "proveedor_estado")
    private String estadoProveedor;

    /*Getters-Setters*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdLegajo() {
        return idLegajo;
    }

    public void setIdLegajo(int idLegajo) {
        this.idLegajo = idLegajo;
    }

    public String getFechaPosicionMovil() {
        return fechaPosicionMovil;
    }

    public void setFechaPosicionMovil(String fechaPosicionMovil) {
        this.fechaPosicionMovil = fechaPosicionMovil;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getPrecision() {
        return precision;
    }

    public void setPrecision(Double precision) {
        this.precision = precision;
    }

    public Double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(Double velocidad) {
        this.velocidad = velocidad;
    }

    public Double getAltitud() {
        return altitud;
    }

    public void setAltitud(Double altitud) {
        this.altitud = altitud;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public String getIdMovil() {
        return idMovil;
    }

    public void setIdMovil(String idMovil) {
        this.idMovil = idMovil;
    }

    public String getFechaSincronizacion() {
        return fechaSincronizacion;
    }

    public void setFechaSincronizacion(String fechaSincronizacion) {
        this.fechaSincronizacion = fechaSincronizacion;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getEstadoProveedor() {
        return estadoProveedor;
    }

    public void setEstadoProveedor(String estadoProveedor) {
        this.estadoProveedor = estadoProveedor;
    }
}
