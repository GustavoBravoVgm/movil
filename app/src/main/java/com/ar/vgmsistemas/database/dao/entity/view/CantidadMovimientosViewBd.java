package com.ar.vgmsistemas.database.dao.entity.view;

import androidx.room.ColumnInfo;
import androidx.room.DatabaseView;

@DatabaseView(
        value = "select id_sucursal, id_cliente, id_comercio,  " +
                " count (case when de_tabla ='ventas' and fe_anulacion is null and sn_modificado is null then 1 end) as ca_pedidos, " +
                " count (case when de_tabla ='No_pedidos' then id_comercio end) as ca_no_atencion " +
                "from movimientos " +
                "where fe_anulacion is null and sn_modificado is null " +
                "group by id_sucursal, id_cliente, id_comercio " +
                "order by id_sucursal, id_cliente, id_comercio",
        viewName = "v_cantidad_movimientos"
)
//@Entity(tableName = "v_cantidad_movimientos", primaryKeys = {"id_sucursal","id_cliente","id_comercio"})
public class CantidadMovimientosViewBd {
    @ColumnInfo(name = "id_sucursal")
    public int idSucursal;

    @ColumnInfo(name = "id_cliente")
    public int idCliente;

    @ColumnInfo(name = "id_comercio")
    public int idComercio;

    @ColumnInfo(name = "ca_pedidos")
    public int caPedidos;

    @ColumnInfo(name = "ca_no_atencion")
    public int caNoAtencion;

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

    public int getCaPedidos() {
        return caPedidos;
    }

    public void setCaPedidos(int caPedidos) {
        this.caPedidos = caPedidos;
    }

    public int getCaNoAtencion() {
        return caNoAtencion;
    }

    public void setCaNoAtencion(int caNoAtencion) {
        this.caNoAtencion = caNoAtencion;
    }
}
