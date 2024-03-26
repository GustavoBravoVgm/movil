package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "cond_DGR",
        foreignKeys = @ForeignKey(entity = ProvinciaBd.class,
                parentColumns = "id_provincia",
                childColumns = "id_provincia"),
        indices = @Index(name = "cDgr_idx_id_provincia",value = {"id_provincia"}))
public class CondicionRentaBd implements Serializable {
    @ColumnInfo(name = "ti_tadgr")
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "id_provincia")
    private int idProvincia;

    @ColumnInfo(name = "de_tadgr")
    private String descripcion;

    @ColumnInfo(name = "ta_porcentaje")
    private double tasaDgr = 0d;

    @ColumnInfo(name = "ti_calculo")
    private Integer tipoCalculo;

    @ColumnInfo(name = "pr_min_imp_dgr")
    private double montoMinimoImpuestoDgr = 0d;

    @ColumnInfo(name = "sn_aplica_a_nc")
    private String snAplicaANc;

    @ColumnInfo(name = "pr_min_a_retener")
    private Double montoMinimoARetener;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null) {
            descripcion = "";
        }
        this.descripcion = descripcion;
    }

    public double getTasaDgr() {
        return this.tasaDgr;
    }

    public void setTasaDgr(double tasaDgr) {
        this.tasaDgr = tasaDgr;
    }

    public Integer getTipoCalculo() {
        return tipoCalculo;
    }

    public void setTipoCalculo(Integer tipoCalculo) {
        this.tipoCalculo = tipoCalculo;
    }

    public double getMontoMinimoImpuestoDgr() {
        return montoMinimoImpuestoDgr;
    }

    public void setMontoMinimoImpuestoDgr(double montoMinimoImpuestoDgr) {
        this.montoMinimoImpuestoDgr = montoMinimoImpuestoDgr;
    }

    public String getSnAplicaANc() {
        return snAplicaANc;
    }

    public void setSnAplicaANc(String snAplicaANc) {
        if (snAplicaANc == null || snAplicaANc.equals("")) {
            snAplicaANc = "N";
        }
        this.snAplicaANc = snAplicaANc;
    }

    public Double getMontoMinimoARetener() {
        return montoMinimoARetener;
    }

    public void setMontoMinimoARetener(Double montoMinimoARetener) {
        this.montoMinimoARetener = montoMinimoARetener;
    }

    public int getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(int idProvincia) {
        this.idProvincia = idProvincia;
    }
}
