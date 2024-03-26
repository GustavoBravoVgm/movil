package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.LocalidadBd;

import java.util.List;

@Dao
public interface ILocalidadDao{
    @Query("SELECT  DISTINCT localidades.* " +
            "FROM   localidades " +
            "       INNER JOIN comercio" +
            "           ON comercio.id_postal = localidades.id_postal " +
            "ORDER BY localidades.de_localidad")
    List<LocalidadBd> recoveryAll() throws Exception;

    @Query("SELECT localidades.* " +
            "FROM   localidades " +
            "       INNER JOIN comercio" +
            "           ON comercio.id_postal = localidades.id_postal " +
            "WHERE localidades.id_postal = :idPostal " +
            "ORDER BY localidades.de_localidad ASC")
    LocalidadBd recoveryByID(int idPostal) throws Exception;

    @Query("SELECT  DISTINCT localidades.* " +
            "FROM   localidades " +
            "WHERE  localidades.id_provincia = :idProvincia " +
            "ORDER BY localidades.de_localidad ASC")
    List<LocalidadBd> recoveryByProvincia(int idProvincia) throws Exception;


}
