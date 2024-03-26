package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.CondicionDirscBd;

import java.util.List;

@Dao
public interface ICondicionDirscDao {

    @Query(value = "SELECT cond_dirsc.* " +
            "FROM   cond_dirsc " +
            "       INNER JOIN comercio " +
            "           ON comercio.ti_dirsc = cond_dirsc.ti_dirsc " +
            "WHERE  comercio.id_sucursal = :idSucursal AND " +
            "       comercio.id_cliente = :idCliente AND " +
            "       comercio.id_comercio = :idComercio")
    CondicionDirscBd recoveryByCliente(int idSucursal, int idCliente, int idComercio) throws Exception;

    @Query(value = "SELECT * FROM cond_dirsc ")
    List<CondicionDirscBd> recoveryAll() throws Exception;


    @Query(value = "SELECT cond_dirsc.* " +
            "FROM cond_dirsc " +
            "WHERE cond_dirsc.ti_dirsc = :id")
    CondicionDirscBd recoveryByID(int id) throws Exception;
}
