package com.ar.vgmsistemas.entity;

import com.ar.vgmsistemas.utils.Formatter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.Observable;

public class UbicacionGeografica extends Observable implements Serializable {


    private static final long serialVersionUID = 7318201249442050897L;

    public static final String TABLE = "ubicacion_geografica";
    //Tipos de Operación para Localización Geográfica

    public static final String OPERACION_VENTA = "Venta";

    public static final String OPERACION_COBRANZA = "Recibo";

    public static final String OPERACION_REPARTO = "Reparto";

    public static final String OPERACION_NO_ATENCION = "No Atención";

    public static final String OPERACION_EGRESO = "Egreso";

    public static final String OPERACION_ENVIO_PERIODICO = "Envio periódico";


    @SerializedName("id")
    private int id;

    @SerializedName("idLegajo")
    private int idLegajo;

    private Date fechaPosicionMovil;

    @SerializedName("latitud")
    private Double latitud;

    @SerializedName("longitud")
    private Double longitud;

    @SerializedName("precision")
    private Double precision;

    @SerializedName("velocidad")
    private Double velocidad;

    @SerializedName("altitud")
    private Double altitud;

    @SerializedName("tipoOperacion")
    private String tipoOperacion;

    @SerializedName("idMovil")
    private String idMovil;

    private Date fechaSincronizacion;

    @SerializedName("deviceId")
    private String deviceId;

    @SerializedName("estadoProveedor")
    private String estadoProveedor;


    @SerializedName("fechaPosicion")

    private String fechaPosicionWs;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getIdLegajo() {
        return idLegajo;
    }

    public void setIdLegajo(int idLegajo) {
        this.idLegajo = idLegajo;
    }

    public Date getFechaPosicionMovil() {

        if (fechaPosicionMovil == null && fechaPosicionWs != null) {
            try {
                fechaPosicionMovil = Formatter.convertToDateWs(fechaPosicionWs);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        return fechaPosicionMovil;
    }

    public void setFechaPosicionMovil(Date fechaPosicionMovil) {
        this.fechaPosicionMovil = fechaPosicionMovil;

        try {
            fechaPosicionWs = Formatter.formatDateWs(fechaPosicionMovil);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.setChanged();
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

    public Date getFechaSincronizacion() {
        return fechaSincronizacion;
    }

    public void setFechaSincronizacion(Date fechaSincronizacion) {
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
