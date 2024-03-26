package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.CategoriaFiscalBd;

import java.util.List;

@Dao
public interface ICategoriaFiscalDao {

    @Query(value = "SELECT  cond_iva.* FROM   cond_Iva")
    List<CategoriaFiscalBd> recoveryAll() throws Exception;

    @Query(value = "SELECT  cond_Iva.* " +
            "FROM   cond_Iva " +
            "WHERE  cond_Iva.ti_contribuyente = :id")
    CategoriaFiscalBd recoveryByID(String id) throws Exception;

}
