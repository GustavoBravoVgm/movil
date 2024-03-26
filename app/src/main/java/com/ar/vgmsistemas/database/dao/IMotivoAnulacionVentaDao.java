package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.ar.vgmsistemas.database.dao.entity.MotivoAnulacionVentaBd;

import java.util.List;

@Dao
public interface IMotivoAnulacionVentaDao {
    @Query(value = "SELECT  mtvanulacionvta.* FROM mtvanulacionvta ")
    List<MotivoAnulacionVentaBd> recoveryAll() throws Exception;

    @Transaction
    @Query(value = "SELECT  mtvanulacionvta.* " +
            "FROM   mtvanulacionvta " +
            "WHERE  mtvanulacionvta.id_motivo = :idMotivo ")
    MotivoAnulacionVentaBd recoveryByID(int idMotivo) throws Exception;

}
