package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.ComercioLoginBd;

import java.util.List;

@Dao
public interface IComercioLoginDao {

    @Query(value = "SELECT comercio_login.* " +
            "FROM   comercio_login " +
            "       JOIN   comercio " +
            "           ON  comercio.id_sucursal = comercio_login.id_sucursal AND " +
            "           comercio.id_cliente = comercio_login.id_cliente AND " +
            "           comercio.id_comercio = comercio_login.id_comercio " +
            "WHERE  comercio_login.id_sucursal = :idSucursal AND " +
            "       comercio_login.id_cliente = :idCliente AND " +
            "       comercio_login.id_comercio = :idComercio ")
    ComercioLoginBd recoveryByCliente(int idSucursal, int idCliente, int idComercio) throws Exception;


    @Query(value = "SELECT * " +
            "FROM   comercio_login ")
    List<ComercioLoginBd> recoveryAll() throws Exception;

    @Query(value = "SELECT * " +
            "FROM   comercio_login " +
            "WHERE comercio_login.id = :miId")
    ComercioLoginBd recoveryByID(long miId) throws Exception;


}
