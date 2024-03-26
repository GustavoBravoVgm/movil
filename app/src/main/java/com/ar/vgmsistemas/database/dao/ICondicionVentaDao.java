package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.CondicionVentaBd;

import java.util.List;

@Dao
public interface ICondicionVentaDao {

    @Query(value = "SELECT * FROM condvta")
    List<CondicionVentaBd> recoveryAll() throws Exception;

    @Query(value = "SELECT * FROM condvta WHERE condvta.id_condvta = :id")
    CondicionVentaBd recoveryByID(String id) throws Exception;

}
