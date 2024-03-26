package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "localidades")
public class LocalidadBd implements Serializable {
    @ColumnInfo(name = "id_postal")
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "id_provincia")
    private int idProvincia;

    @ColumnInfo(name = "de_localidad")
    private String descripcion;

    @ColumnInfo(name = "cd_postal")
    private Integer codigoPostal;

    /*Getters-Setters*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(int idProvincia) {
        this.idProvincia = idProvincia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(Integer codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
}
