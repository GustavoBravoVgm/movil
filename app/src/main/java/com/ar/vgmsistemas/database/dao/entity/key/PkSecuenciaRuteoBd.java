package com.ar.vgmsistemas.database.dao.entity.key;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import java.io.Serializable;

public class PkSecuenciaRuteoBd implements Serializable {

    @Ignore
    private static final long serialVersionUID = -2576850883551277723L;
    @ColumnInfo(name = "id_vendedor")
    private int idVendedor;
    @ColumnInfo(name = "id_sucursal")
    private int idSucursal;
    @ColumnInfo(name = "id_cliente")
    private int idCliente;
    @ColumnInfo(name = "id_comercio")
    private int idComercio;

    public int getIdVendedor() {
        return this.idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public int getIdSucursal() {
        return this.idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public int getIdCliente() {
        return this.idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdComercio() {
        return this.idComercio;
    }

    public void setIdComercio(int idComercio) {
        this.idComercio = idComercio;
    }

}
