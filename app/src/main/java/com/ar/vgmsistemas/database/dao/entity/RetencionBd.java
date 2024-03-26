package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;

import com.ar.vgmsistemas.database.dao.entity.key.PkVentaBd;

import java.io.Serializable;

@Entity(tableName = "retenciones",
        primaryKeys = {"id_fcnc", "id_tipoab", "id_ptovta", "id_numdoc"})
public class RetencionBd implements Serializable {
    @NonNull
    @Embedded
    private PkVentaBd id;

    @ColumnInfo(name = "id_plancta_retencion")
    private int idPlanCtaRetencion;

    @ColumnInfo(name = "de_observacion")
    private String observacion;

    @ColumnInfo(name = "pr_monto")
    private double importe;

    @ColumnInfo(name = "fe_retencion")
    private String fechaMovil;

    @ColumnInfo(name = "id_entrega")
    private int idEntrega;

    public String getFechaMovil() {
        return fechaMovil;
    }


    /*Getters-Setters*/
    @NonNull
    public PkVentaBd getId() {
        return id;
    }

    public void setId(@NonNull PkVentaBd id) {
        this.id = id;
    }

    public int getIdPlanCtaRetencion() {
        return idPlanCtaRetencion;
    }

    public void setIdPlanCtaRetencion(int idPlanCtaRetencion) {
        this.idPlanCtaRetencion = idPlanCtaRetencion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public void setFechaMovil(String fechaMovil) {
        this.fechaMovil = fechaMovil;
    }

    public int getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(int idEntrega) {
        this.idEntrega = idEntrega;
    }
}
