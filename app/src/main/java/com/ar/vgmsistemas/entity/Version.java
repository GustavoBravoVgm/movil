package com.ar.vgmsistemas.entity;

import org.simpleframework.xml.Element;

public class Version { //Queda la anotacion Element porque el archivo se descarga del ftp y viene en xml
    @Element(name = "numeroVersion")
    private int numeroVersion;
    @Element(name = "descripcion", required = false)
    private String descripcion;
    @Element(name = "forzarSincronizacion", required = false)
    private boolean forzarSincronizacion;
    // Configuracion avanzada
    @Element(name = "actualizarConfiguracion", required = false)
    private boolean actualizarConfiguracion;
    @Element(name = "urlRemotaServidor", required = false)
    private String urlRemotaServidor;
    @Element(name = "urlRemotaServidor2", required = false)
    private String urlRemotaServidor2;
    @Element(name = "urlLocalServidor", required = false)
    private String urlLocalServidor;
    @Element(name = "ftpUserName", required = false)
    private String ftpUserName;
    @Element(name = "fechaLimiteActualizacion", required = false)
    private String fechaLimiteActualizacion = "";

    @Element(name = "vendedoresHabilitadosParaActualizar", required = false)
    private String vendedoresHabilitadosParaActualizar;

    @Element(name = "documentoPorDefecto", required = false)
    private String documentoPorDefecto;
    @Element(name = "puntoDeVentaPorDefecto", required = false)
    private int puntoDeVentaPorDefecto;
    @Element(name = "repartidor", required = false)
    private int repartidor;
    @Element(name = "tipoDatoIdArticulos", required = false)
    private String tipoDatoIdArticulos;

    public void setNumeroVersion(int version) {
        this.numeroVersion = version;
    }

    public int getNumeroVersion() {
        return numeroVersion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setForzarSincronizacion(boolean forzarSincronizacion) {
        this.forzarSincronizacion = forzarSincronizacion;
    }

    public boolean isForzarSincronizacion() {
        return forzarSincronizacion;
    }

    public boolean isActualizarConfiguracion() {
        return actualizarConfiguracion;
    }

    public void setActualizarConfiguracion(boolean actualizarConfiguracion) {
        this.actualizarConfiguracion = actualizarConfiguracion;
    }

    public String getUrlRemotaServidor() {
        return urlRemotaServidor;
    }

    public void setUrlRemotaServidor(String urlRemotaServidor) {
        this.urlRemotaServidor = urlRemotaServidor;
    }

    public String getUrlRemotaServidor2() {
        return urlRemotaServidor2;
    }

    public void setUrlRemotaServidor2(String urlRemotaServidor2) {
        this.urlRemotaServidor2 = urlRemotaServidor2;
    }

    public String getUrlLocalServidor() {
        return urlLocalServidor;
    }

    public void setUrlLocalServidor(String urlLocalServidor) {
        this.urlLocalServidor = urlLocalServidor;
    }

    public String getFtpUserName() {
        return ftpUserName;
    }

    public void setFtpUserName(String ftpUserName) {
        this.ftpUserName = ftpUserName;
    }

    public String getFechaLimiteActualizacion() {
        return fechaLimiteActualizacion;
    }

    public void setFechaLimiteActualizacion(String fechaLimiteActualizacion) {
        this.fechaLimiteActualizacion = fechaLimiteActualizacion;
    }

    public void setDispositivosHabilitadosParaActualizar(String dispositivosHabilitadosParaActualizar) {
        this.vendedoresHabilitadosParaActualizar = dispositivosHabilitadosParaActualizar;
    }

    public String getDispositivosHabilitadosParaActualizar() {
        return vendedoresHabilitadosParaActualizar;
    }

    public String getDocumentoPorDefecto() {
        return documentoPorDefecto;
    }

    public void setDocumentoPorDefecto(String documentoPorDefecto) {
        this.documentoPorDefecto = documentoPorDefecto;
    }

    public int getPuntoDeVentaPorDefecto() {
        return puntoDeVentaPorDefecto;
    }

    public void setPuntoDeVentaPorDefecto(int puntoDeVentaPorDefecto) {
        this.puntoDeVentaPorDefecto = puntoDeVentaPorDefecto;
    }

    public int getRepartidor() {
        return repartidor;
    }

    public void setRepartidor(int repartidor) {
        this.repartidor = repartidor;
    }

    public String getTipoDatoIdArticulos() {
        return tipoDatoIdArticulos;
    }

    public void setTipoDatoIdArticulos(String tipoDatoIdArticulos) {
        this.tipoDatoIdArticulos = tipoDatoIdArticulos;
    }
}
