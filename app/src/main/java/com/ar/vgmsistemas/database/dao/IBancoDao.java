package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.BancoBd;

import java.util.List;

@Dao
public interface IBancoDao {

    @Query("SELECT banco.* FROM banco")
    List<BancoBd> recoveryAll() throws Exception;

    @Query("SELECT banco.* FROM banco WHERE banco.id_bancogirado = :idBanco")
    BancoBd recoveryByID(int idBanco) throws Exception;
}
