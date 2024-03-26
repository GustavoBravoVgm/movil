package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;

import com.ar.vgmsistemas.database.dao.entity.key.PkCodigoAutCobranzaBd;

import java.io.Serializable;

@Entity(tableName = "codigos_autorizacion",
        primaryKeys = {"id_doc", "id_letra", "id_ptovta", "id_numero"})
public class CodigoAutorizacionCobranzaBd implements Serializable {

    @NonNull
    @Embedded
    private PkCodigoAutCobranzaBd id;

    @ColumnInfo(name = "codigo")
    private String codigo;

    public PkCodigoAutCobranzaBd getId() {
        return id;
    }

    public void setId(PkCodigoAutCobranzaBd id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

}
