package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.ar.vgmsistemas.database.dao.entity.NoPedidoBd;

import java.util.List;

@Dao
public interface INoPedidoDao {
    @Query(value = "SELECT no_pedidos.*" +
            "FROM   no_pedidos " +
            "       INNER JOIN motivos_no_pedidos " +
            "           ON  motivos_no_pedidos.id_motivo = no_pedidos.id_no_pedido " +
            "       INNER JOIN comercio " +
            "           ON  comercio.id_sucursal = no_pedidos.id_sucursal AND " +
            "               comercio.id_cliente = no_pedidos.id_cliente AND " +
            "               comercio.id_comercio = no_pedidos.id_comercio " +
            "WHERE  (no_pedidos.sn_anulo = 'N' OR " +
            "        no_pedidos.sn_anulo IS NULL) " +
            "ORDER BY comercio.de_organizacion ASC")
    List<NoPedidoBd> recovery() throws Exception;

    @Query(value = "SELECT no_pedidos.*" +
            "FROM   no_pedidos " +
            "       INNER JOIN motivos_no_pedidos " +
            "           ON  motivos_no_pedidos.id_motivo = no_pedidos.id_no_pedido " +
            "       INNER JOIN comercio " +
            "           ON  comercio.id_sucursal = no_pedidos.id_sucursal AND " +
            "               comercio.id_cliente = no_pedidos.id_cliente AND " +
            "               comercio.id_comercio = no_pedidos.id_comercio " +
            "       INNER JOIN movimientos " +
            "           ON  movimientos.id_movil = no_pedidos.id_movil_no_pedidos " +
            "WHERE  no_pedidos.sn_anulo = 'N' or  no_pedidos.sn_anulo IS NULL " +
            "ORDER BY comercio.de_organizacion ASC")
    List<NoPedidoBd> recoveryPedidosSinEnviar() throws Exception;

    @Query(value = "SELECT COUNT(*) " +
            "FROM no_pedidos " +
            "WHERE no_pedidos.fe_sincronizacion IS NULL ")
    int getCantidadPedidosSinEnviar() throws Exception;

    @Transaction
    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    Long create(NoPedidoBd entity) throws Exception;

    @Query(value = "UPDATE no_pedidos SET sn_anulo = 'S' " +
            "WHERE no_pedidos.id_no_pedido = :id ")
    void delete(long id) throws Exception;//baja lógica

    //se usará para void updateFechaSincronizacion(NoPedido noPedido) throws Exception
    @Update(onConflict = OnConflictStrategy.ROLLBACK)
    void update(NoPedidoBd noPedido) throws Exception;

    @Query(value = "SELECT COUNT(*) " +
            "FROM   no_pedidos " +
            "       INNER JOIN movimientos " +
            "           ON movimientos.id_movil = no_pedidos.id_movil_no_pedidos " +
            "WHERE  no_pedidos.sn_anulo = 'N' AND " +
            "       no_pedidos.id_sucursal = :idSucursal AND " +
            "       no_pedidos.id_cliente = :idCliente AND " +
            "       no_pedidos.id_comercio = :idComercio ")
    int getCantidadNoPedidos(int idSucursal, int idCliente, int idComercio) throws Exception;
}
