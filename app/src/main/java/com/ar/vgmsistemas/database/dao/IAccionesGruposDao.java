package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.AccionesGruposBd;

import java.util.List;

@Dao
public interface IAccionesGruposDao {

    @Query(value = "SELECT acciones_grupos.* " +
            "FROM acciones_grupos " +
            "WHERE acciones_grupos.id_acciones_grupos = :idAccionesGrupos")
    AccionesGruposBd recoveryByID(int idAccionesGrupos) throws Exception;


    @Query(value = "SELECT acciones_grupos.* FROM acciones_grupos ")
    List<AccionesGruposBd> recoveryAll() throws Exception;
}
