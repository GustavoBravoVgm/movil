package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.view.CantidadMovimientosViewBd;

import java.util.List;

@Dao
public interface ICantidadesMovimientosDao {

    @Query(value = "SELECT v_cantidad_movimientos.* " +
            "FROM   v_cantidad_movimientos ")
    List<CantidadMovimientosViewBd> recoveryAll() throws Exception;

    @Query(value = "SELECT v_cantidad_movimientos.ca_pedidos " +
            "FROM   v_cantidad_movimientos " +
            "WHERE  v_cantidad_movimientos.id_sucursal = :idSucursal AND " +
            "       v_cantidad_movimientos.id_cliente = :idCliente AND " +
            "       v_cantidad_movimientos.id_comercio = :idComercio")
    int getCantidadPedidos(int idSucursal, int idCliente, int idComercio) throws Exception;

    @Query(value = "SELECT v_cantidad_movimientos.ca_no_atencion " +
            "FROM   v_cantidad_movimientos " +
            "WHERE  v_cantidad_movimientos.id_sucursal = :idSucursal AND " +
            "       v_cantidad_movimientos.id_cliente = :idCliente AND " +
            "       v_cantidad_movimientos.id_comercio = :idComercio")
    int getCantidadNoAtenciones(int idSucursal, int idCliente, int idComercio) throws Exception;

}
