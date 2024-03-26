package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;

import com.ar.vgmsistemas.database.dao.entity.key.PkClienteVendedorBd;

import java.io.Serializable;

@Entity(tableName = "vend_comercio",
        primaryKeys = {"id_vendedor", "id_sucursal", "id_cliente", "id_comercio"})
public class ClienteVendedorBd implements Serializable {

    @NonNull
    @Embedded
    private PkClienteVendedorBd id;

    @ColumnInfo(name = "sn_filtro_art_sinc")
    private String snFiltroArticulo;

    @ColumnInfo(name = "ti_filtro_art_sinc")
    private String tiFiltroArticuloSinc;


    public PkClienteVendedorBd getId() {
        return id;
    }

    public void setId(PkClienteVendedorBd id) {
        this.id = id;
    }

    public String getSnFiltroArticulo() {
        return snFiltroArticulo;
    }

    public void setSnFiltroArticulo(String snFiltroArticulo) {
        if (snFiltroArticulo == null || snFiltroArticulo.equals("")) snFiltroArticulo = "N";
        this.snFiltroArticulo = snFiltroArticulo;
    }

    public String getTiFiltroArticuloSinc() {
        return tiFiltroArticuloSinc;
    }

    public void setTiFiltroArticuloSinc(String tiFiltroArticuloSinc) {
        if (tiFiltroArticuloSinc == null) tiFiltroArticuloSinc = "";
        this.tiFiltroArticuloSinc = tiFiltroArticuloSinc;
    }

}
