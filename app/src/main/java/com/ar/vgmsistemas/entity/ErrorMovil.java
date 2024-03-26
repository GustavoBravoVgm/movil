package com.ar.vgmsistemas.entity;

import com.ar.vgmsistemas.utils.Formatter;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.util.Date;

public class ErrorMovil {

    public static final String TABLE = "error_movil";

    private int id;

    private String error;

    private Integer version;

    private Date fechaRegistro;

    private int idVendedor;

    @SerializedName("fechaRegistro")
    private String fechaRegistroWs;

    private String idMovil;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
        try {
            fechaRegistroWs = Formatter.formatDateWs(fechaRegistro);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getFechaRegistroWs() {
        return fechaRegistroWs;
    }

    public void setFechaRegistroWs(String fechaRegistroWs) {
        this.fechaRegistroWs = fechaRegistroWs;
    }

    public String getIdMovil() {
        return idMovil;
    }

    public void setIdMovil(String idMovil) {
        this.idMovil = idMovil;
    }
}
