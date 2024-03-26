package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.TipoMonedaBd;

import java.util.List;

@Dao
public interface ITipoMonedaDao {
    @Query(value = "SELECT valores.* " +
            "FROM   valores " +
            "WHERE  valores.id_valor = :id")
    TipoMonedaBd recoveryByID(Integer id) throws Exception;

    @Query(value = "SELECT valores.* FROM   valores")
    List<TipoMonedaBd> recoveryAll() throws Exception;
}
