package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.NegocioBd;

import java.util.List;

@Dao
public interface INegocioDao {

    @Query(value = "SELECT negocio.* FROM negocio")
    List<NegocioBd> recoveryAll() throws Exception;

    @Query(value = "SELECT negocio.* " +
            "FROM   negocio " +
            "WHERE  negocio.id_negocio = :idNegocio")
    NegocioBd recoveryById(Integer idNegocio) throws Exception;
}
