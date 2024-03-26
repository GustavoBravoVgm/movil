package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.ImpresoraBd;

import java.util.List;

@Dao
public interface IImpresoraDao {
    @Query(value = "SELECT impresora.* " +
            "FROM impresora")
    List<ImpresoraBd> recoveryAll() throws Exception;

    @Query(value = "SELECT impresora.* " +
            "FROM impresora " +
            "WHERE impresora.id_impresora = :id")
    ImpresoraBd recoveryByID(Integer id) throws Exception;
}
