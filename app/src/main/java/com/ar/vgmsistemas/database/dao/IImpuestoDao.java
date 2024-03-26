package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.ImpuestoBd;

import java.util.List;

@Dao
public interface IImpuestoDao {

    @Query(value = "SELECT impuestos.* FROM impuestos ")
    List<ImpuestoBd> recoveryAll() throws Exception;

    @Query(value = "SELECT impuestos.* " +
            "FROM impuestos " +
            "WHERE impuestos.id_impuesto = :id")
    ImpuestoBd recoveryByID(Integer id) throws Exception;
}
