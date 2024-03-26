package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.MarcaBd;

import java.util.List;

@Dao
public interface IMarcaDao{

    @Query(value = "SELECT linea.* FROM linea")
    List<MarcaBd> recoveryAll() throws Exception;

    @Query(value = "SELECT linea.* " +
            "FROM linea " +
            "WHERE linea.id_linea = :idLinea")
    MarcaBd recoveryByID(int idLinea) throws Exception;

}
