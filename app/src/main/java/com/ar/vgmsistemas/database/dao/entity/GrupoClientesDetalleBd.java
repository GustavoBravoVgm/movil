package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import com.ar.vgmsistemas.database.dao.entity.key.PkGrupoClientesDetalleBd;


@Entity(tableName = "grupo_clientes_detalle",
        primaryKeys = {"id_grupo_clie", "id_grupo_clie_detalle"},
        foreignKeys = @ForeignKey(entity = ClienteBd.class,
                parentColumns = {"id_sucursal", "id_cliente", "id_comercio"},
                childColumns = {"id_sucursal", "id_cliente", "id_comercio"}),
        indices = @Index(name = "gcd_idx_suc_clie_comer", value = {"id_sucursal", "id_cliente", "id_comercio"}))
public class GrupoClientesDetalleBd {
    @NonNull
    @Embedded
    private PkGrupoClientesDetalleBd id;

    @ColumnInfo(name = "id_sucursal")
    private int idSucursal;

    @ColumnInfo(name = "id_cliente")
    private int idCliente;

    @ColumnInfo(name = "id_comercio")
    private int idComercio;

    public PkGrupoClientesDetalleBd getId() {
        return id;
    }

    public void setId(PkGrupoClientesDetalleBd id) {
        this.id = id;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdComercio() {
        return idComercio;
    }

    public void setIdComercio(int idComercio) {
        this.idComercio = idComercio;
    }

}
