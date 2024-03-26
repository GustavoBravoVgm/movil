package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "cond_Iva")
public class CategoriaFiscalBd implements Serializable {

    @ColumnInfo(name = "ti_contribuyente")
    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name = "de_contribuyente")
    private String descripcion;

    @ColumnInfo(name = "sn_bn", defaultValue = "N")
    private String snBn;

    @ColumnInfo(name = "ti_comprobante_letra")
    private String tipoComprobanteLetra;

    @ColumnInfo(name = "fe_vigencia_letra")
    private String fechaVigenciaLetra;


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

    public String getFechaVigenciaLetra() {
        return fechaVigenciaLetra;
    }

    public void setFechaVigenciaLetra(String fechaVigenciaLetra) {
        this.fechaVigenciaLetra = fechaVigenciaLetra;
    }

}
