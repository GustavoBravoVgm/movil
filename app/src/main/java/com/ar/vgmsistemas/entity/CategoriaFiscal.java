package com.ar.vgmsistemas.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class CategoriaFiscal implements Serializable {
    public static final String NO_CATEGORIZADO = "NC";

    private static final long serialVersionUID = 3172579163578710285L;

    @SerializedName("id")
    private String id;

    @SerializedName("descripcion")
    private String descripcion;

    private String snBn;

    private String tipoComprobanteLetra;

    private Date fechaVigenciaLetra;


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null) descripcion = "";
        this.descripcion = descripcion;
    }

    public String getSnBn() {
        return snBn;
    }

    public void setSnBn(String snBn) {
        if (snBn == null || snBn.equals("")) snBn = "N";
        this.snBn = snBn;
    }

    public String getTipoComprobanteLetra() {
        return tipoComprobanteLetra;
    }

    public void setTipoComprobanteLetra(String tipoComprobanteLetra) {
        this.tipoComprobanteLetra = tipoComprobanteLetra;
    }

    public Date getFechaVigenciaLetra() {
        return fechaVigenciaLetra;
    }

    public void setFechaVigenciaLetra(Date fechaVigenciaLetra) {
        this.fechaVigenciaLetra = fechaVigenciaLetra;
    }
}
