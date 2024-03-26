package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "cond_dirsc",
        foreignKeys = @ForeignKey(entity = ProvinciaBd.class,
                parentColumns = "id_provincia",
                childColumns = "id_provincia"),
        indices = @Index(name = "dirsc_idx_id_provincia", value = {"id_provincia"}))
public class CondicionDirscBd implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "ti_dirsc")
    private int id;

    @ColumnInfo(name = "de_dirsc")
    private String descripcion;

    @ColumnInfo(name = "ta_impuesto")
    private double tasaImpuesto;

    @ColumnInfo(name = "id_provincia")
    private int idProvincia;

    @ColumnInfo(name = "id_postal")
    private int idPostal;

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
        if (descripcion == null) descripcion = "";
        this.descripcion = descripcion;
    }

    public double getTasaImpuesto() {
        return this.tasaImpuesto;
    }

    public void setTasaImpuesto(double tasaImpuesto) {
        this.tasaImpuesto = tasaImpuesto;
    }

    public int getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(int idProvincia) {
        this.idProvincia = idProvincia;
    }

    public int getIdPostal() {
        return idPostal;
    }

    public void setIdPostal(int idPostal) {
        this.idPostal = idPostal;
    }
}
