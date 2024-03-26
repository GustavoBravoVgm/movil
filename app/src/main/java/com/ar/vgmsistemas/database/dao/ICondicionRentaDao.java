package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.CondicionRentaBd;

import java.util.List;

@Dao
public interface ICondicionRentaDao {

    @Query(value = "SELECT cond_DGR.* " +
            "FROM   cond_DGR " +
            "       INNER JOIN comercio " +
            "           ON comercio.ti_tadgr = cond_DGR.ti_tadgr " +
            "WHERE  comercio.id_sucursal = :idSucursal AND " +
            "       comercio.id_cliente = :idCliente AND " +
            "       comercio.id_comercio = :idComercio")
    CondicionRentaBd recoveryByCliente(int idSucursal, int idCliente, int idComercio) throws Exception;

    @Query(value = "SELECT * FROM cond_DGR ")
    List<CondicionRentaBd> recoveryAll() throws Exception;


    @Query(value = "SELECT cond_DGR.* " +
            "FROM cond_DGR " +
            "WHERE cond_DGR.ti_tadgr = :id")
    CondicionRentaBd recoveryByID(Integer id) throws Exception;
}
