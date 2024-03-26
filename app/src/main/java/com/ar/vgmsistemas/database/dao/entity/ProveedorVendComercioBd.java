package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;

import com.ar.vgmsistemas.database.dao.entity.key.PkProveedorVendComercioBd;

import java.io.Serializable;

@Entity(tableName = "proveedores_vend_comercio",
        primaryKeys = {"id_proveedor", "id_vendedor",
                "id_sucursal", "id_cliente", "id_comercio"})
public class ProveedorVendComercioBd implements Serializable {
    @NonNull
    @Embedded
    private PkProveedorVendComercioBd id;

    public PkProveedorVendComercioBd getId() {
        return id;
    }

    public void setId(PkProveedorVendComercioBd id) {
        this.id = id;
    }


}
