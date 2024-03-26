package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.ar.vgmsistemas.database.dao.entity.RubroBd;

import java.util.List;

@Dao
public interface IRubroDao {

    @Transaction
    @Query(value = "SELECT segmento.* FROM segmento")
    List<RubroBd> recoveryAll() throws Exception;

    @Transaction
    @Query(value = "SELECT segmento.* " +
            "FROM   segmento " +
            "WHERE  segmento.id_negocio = :idNegocio AND " +
            "       segmento.id_segmento = :idSegmento")
    RubroBd recoveryByID(int idNegocio, int idSegmento) throws Exception;
}
