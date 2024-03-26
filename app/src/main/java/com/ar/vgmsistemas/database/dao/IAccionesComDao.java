package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.ar.vgmsistemas.database.dao.entity.AccionesComBd;

@Dao
public interface IAccionesComDao {

    @Transaction /*<-- Para garantizar que la consulta sea atómica ya que trae los detalles también*/
    @Query("SELECT * FROM acciones_com WHERE acciones_com.id_acciones_com = :id")
    AccionesComBd recoveryByID(Integer id) throws Exception;
}
