package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.MotivoAutorizacionBd;

import java.util.List;

@Dao
public interface IMotivoAutorizacionDao {
    @Query(value = "SELECT motivos_autorizacion.* " +
            "FROM motivos_autorizacion " +
            "WHERE motivos_autorizacion.ti_autorizacion = 'CC'")
    List<MotivoAutorizacionBd> recoveryForCtaCte() throws Exception;

    @Query(value = "SELECT motivos_autorizacion.* " +
            "FROM motivos_autorizacion " +
            "WHERE motivos_autorizacion.ti_autorizacion = 'PR'")
    List<MotivoAutorizacionBd> recoveryForPedidoRentable() throws Exception;

    @Query(value = "SELECT motivos_autorizacion.* " +
            "FROM motivos_autorizacion " +
            "WHERE motivos_autorizacion.ti_autorizacion = 'SA'" +
            "LIMIT 1")
    MotivoAutorizacionBd recoveryForPedidoRentableSinAutorizacion() throws Exception;

    @Query(value = "SELECT motivos_autorizacion.* " +
            "FROM motivos_autorizacion ")
    List<MotivoAutorizacionBd> recoveryAll() throws Exception;

    @Query(value = "SELECT motivos_autorizacion.* " +
            "FROM motivos_autorizacion " +
            "WHERE motivos_autorizacion.id_motivo_autoriza= :id " )
    MotivoAutorizacionBd recoveryByID(Integer id) throws Exception;
}
