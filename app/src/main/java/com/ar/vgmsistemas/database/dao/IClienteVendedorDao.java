package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.ClienteVendedorBd;

@Dao
public interface IClienteVendedorDao {

    @Query(value = "SELECT vend_comercio.* " +
            "FROM   vend_comercio " +
            "WHERE  vend_comercio.id_vendedor = :idVendedor AND " +
            "       vend_comercio.id_sucursal = :idSucursal AND " +
            "       vend_comercio.id_cliente = :idCliente AND " +
            "       vend_comercio.id_comercio = :idComercio")
    ClienteVendedorBd recoveryByID(int idVendedor, int idSucursal, int idCliente, int idComercio) throws Exception;
}
