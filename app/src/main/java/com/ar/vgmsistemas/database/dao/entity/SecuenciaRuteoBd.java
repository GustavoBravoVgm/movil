package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;

import com.ar.vgmsistemas.database.dao.entity.key.PkSecuenciaRuteoBd;

import java.io.Serializable;

@Entity(tableName = "vend_comercio_ruta",
        primaryKeys = {"id_vendedor", "id_sucursal", "id_cliente",
                "id_comercio", "dia"})
public class SecuenciaRuteoBd implements Serializable {
    @NonNull
    @Embedded
    private PkSecuenciaRuteoBd id;

    @ColumnInfo(name = "dia")
    private int dia;

    @ColumnInfo(name = "orden")
    private int numeroOrden;

    public int getNumeroOrden() {
        return this.numeroOrden;
    }

    public void setNumeroOrden(int numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    public int getDia() {
        return this.dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public PkSecuenciaRuteoBd getId() {
        return this.id;
    }

    public void setId(PkSecuenciaRuteoBd id) {
        this.id = id;
    }

}
