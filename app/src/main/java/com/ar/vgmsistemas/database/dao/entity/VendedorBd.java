package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vendedor")
public class VendedorBd {

    @ColumnInfo(name = "id_vendedor")
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "ti_control_pedido_rentable")
    private String tiControlPedidoRentable;

    /*Getters-Setters*/

    public int getId() {
        return id;
    }

    public void setId(int idVendedor) {
        this.id = idVendedor;
    }

    public String getTiControlPedidoRentable() {
        return tiControlPedidoRentable;
    }

    public void setTiControlPedidoRentable(String tiControlPedidoRentable) {
        this.tiControlPedidoRentable = tiControlPedidoRentable;
    }

}
