package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.RecursoHumanoBd;

import java.util.List;

@Dao
public interface IRecursoHumanoDao {
    @Query(value = "SELECT rrhh.* " +
            "FROM rrhh ")
    List<RecursoHumanoBd> recoveryAll() throws Exception;

    @Query(value = "SELECT rrhh.* " +
            "FROM rrhh " +
            "WHERE rrhh.id_legajo = :id")
    RecursoHumanoBd recoveryByID(int id) throws Exception;
}
