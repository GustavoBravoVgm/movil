package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.MotivoNoPedidoBd;

import java.util.List;

@Dao
public interface IMotivoNoPedidoDao {
    @Query(value = "SELECT motivos_no_pedidos.* " +
            "FROM   motivos_no_pedidos " +
            "WHERE  motivos_no_pedidos.id_motivo = :id")
    MotivoNoPedidoBd recoveryByID(int id) throws Exception;

    @Query(value = "SELECT motivos_no_pedidos.* " +
            "FROM   motivos_no_pedidos ")
    List<MotivoNoPedidoBd> recoveryAll() throws Exception;
}
