package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.SucursalBd;

import java.util.List;

@Dao
public interface ISucursalDao {
    @Query(value = "SELECT  sucursal.id_sucursal, " +
            "		        sucursal.de_sucursal, " +
            "		        sucursal.ta_rentabilidad_global, " +
            "		        sucursal.ti_ctrl_accom " +
            "FROM	sucursal " +
            "UNION " +
            "SELECT -1 AS id_sucursal, " +
            "		'TODAS' AS de_sucursal, " +
            "		NULL AS ta_rentabilidad_global, " +
            "		'NR' AS ti_ctrl_accom ")
    List<SucursalBd> recoveryAll() throws Exception;

    @Query(value = "SELECT sucursal.* " +
            "FROM  sucursal " +
            "WHERE sucursal.id_sucursal = :id")
    SucursalBd recoveryByID(Integer id) throws Exception;
}
