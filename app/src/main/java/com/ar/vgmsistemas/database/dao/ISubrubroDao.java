package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.SubrubroBd;

import java.util.List;

@Dao
public interface ISubrubroDao {

    @Query(value = "SELECT subrubro.* FROM subrubro")
    List<SubrubroBd> recoveryAll() throws Exception;

    @Query(value = "SELECT subrubro.* " +
            "FROM   subrubro " +
            "WHERE  subrubro.id_negocio = :idNegocio AND " +
            "       subrubro.id_segmento = :idRubro AND " +
            "       subrubro.id_subrubro = :idSubrubro")
    SubrubroBd recoveryByID(int idNegocio, int idRubro, int idSubrubro) throws Exception;

    @Query(value = "SELECT subrubro.* " +
            "FROM   subrubro " +
            "WHERE  subrubro.id_negocio = :idNegocio AND " +
            "       subrubro.id_segmento = :idRubro")
    List<SubrubroBd> recoveryByRubro(int idNegocio, int idRubro) throws Exception;


}
