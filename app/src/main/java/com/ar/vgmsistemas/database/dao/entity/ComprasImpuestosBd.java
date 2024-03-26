package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;

import com.ar.vgmsistemas.database.dao.entity.key.PkComprasImpuestosBd;

import java.io.Serializable;

@Entity(tableName = "compras_impuestos",
        primaryKeys = {"id_proveedor", "id_fcncnd", "id_ptovta",
                "id_numero", "id_impuesto", "id_tipoab", "id_secuencia"})
public class ComprasImpuestosBd implements Serializable {

    @NonNull
    @Embedded
    private PkComprasImpuestosBd id;

    @ColumnInfo(name = "pr_imp_gravado")
    private double prImpGravado;

    @ColumnInfo(name = "ta_impuesto")
    private double taImpuesto = 0d;

    @ColumnInfo(name = "pr_impuesto")
    private double prImpuesto = 0d;

    @ColumnInfo(name = "id_movil")
    private String idMovil;

    @ColumnInfo(name = "sn_anulado")
    private String snAnulado;

    public ComprasImpuestosBd() {

    }

    public PkComprasImpuestosBd getId() {
        return id;
    }

    public void setId(PkComprasImpuestosBd id) {
        this.id = id;
    }

    public double getPrImpGravado() {
        return prImpGravado;
    }

    public void setPrImpGravado(double prImpGravado) {
        this.prImpGravado = prImpGravado;
    }

    public double getTaImpuesto() {
        return taImpuesto;
    }

    public void setTaImpuesto(double taImpuesto) {
        this.taImpuesto = taImpuesto;
    }

    public double getPrImpuesto() {
        return prImpuesto;
    }

    public void setPrImpuesto(double prImpuesto) {
        this.prImpuesto = prImpuesto;
    }

    public String getIdMovil() {
        return idMovil;
    }

    public void setIdMovil(String idMovil) {
        this.idMovil = idMovil;
    }

    public String getSnAnulado() {
        return snAnulado;
    }

    public void setSnAnulado(String snAnulado) {
        this.snAnulado = snAnulado;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        long result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + Double.doubleToLongBits(prImpGravado);
        result = prime * result + Double.doubleToLongBits(prImpuesto);
        result = prime * result + Double.doubleToLongBits(taImpuesto);
        return (int) result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ComprasImpuestosBd other = (ComprasImpuestosBd) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (Double.doubleToLongBits(prImpGravado) != Double.doubleToLongBits(other.prImpGravado))
            return false;
        if (Double.doubleToLongBits(prImpuesto) != Double.doubleToLongBits(other.prImpuesto))
            return false;
        return Double.doubleToLongBits(taImpuesto) == Double.doubleToLongBits(other.taImpuesto);
    }

}
