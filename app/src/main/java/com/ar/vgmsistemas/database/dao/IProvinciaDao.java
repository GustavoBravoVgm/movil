package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.ProvinciaBd;

import java.util.List;

@Dao
public interface IProvinciaDao {

    @Query(value = "SELECT provincias.* FROM provincias")
    List<ProvinciaBd> recoveryAll() throws Exception;

    @Query(value = "SELECT provincias.* " +
            "FROM   provincias " +
            "WHERE  provincias.id_provincia = :idProvincia")
    ProvinciaBd recoveryByID(int idProvincia) throws Exception;

}
