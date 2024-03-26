package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.MotivoCreditoBd;

import java.util.List;

@Dao
public interface IMotivoCreditoDao {
    @Query(value = "SELECT motivos_rechazo_nc.* " +
            "FROM motivos_rechazo_nc")
    List<MotivoCreditoBd> recoveryAll() throws Exception;

    @Query(value = "SELECT motivos_rechazo_nc.* " +
            "FROM   motivos_rechazo_nc " +
            "WHERE  motivos_rechazo_nc.id_motivo_rechazo_nc = :idMotivoRechazoNC")
    MotivoCreditoBd recoveryByID(Integer idMotivoRechazoNC) throws Exception;
}
