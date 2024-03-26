package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.AccionesGruposCodigosBd;

import java.util.List;

@Dao
public interface IAccionesGruposCodigosDao {

    @Query(value = "SELECT acciones_grupos_codigos.* " +
            "FROM acciones_grupos_codigos " +
            "WHERE acciones_grupos_codigos.id_acciones_grupos_codigo = :idAccionesGruposCodigos")
    AccionesGruposCodigosBd recoveryByID(int idAccionesGruposCodigos) throws Exception;


    @Query(value = "SELECT acciones_grupos_codigos.* FROM acciones_grupos_codigos ")
    List<AccionesGruposCodigosBd> recoveryAll() throws Exception;
}
