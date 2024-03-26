package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.ProveedorVendComercioBd;

import java.util.List;

@Dao
public interface IProveedorVendComercioDao {
    @Query(value = "SELECT proveedores_vend_comercio.* " +
            "FROM   proveedores_vend_comercio " +
            "WHERE  proveedores_vend_comercio.id_proveedor = :idProveedor AND " +
            "       proveedores_vend_comercio.id_vendedor = :idVendedor AND " +
            "       proveedores_vend_comercio.id_sucursal = :idSucursal AND " +
            "       proveedores_vend_comercio.id_cliente = :idCliente AND " +
            "       proveedores_vend_comercio.id_comercio = :idComercio")
    ProveedorVendComercioBd recoveryByID(int idProveedor, int idVendedor, int idSucursal, int idCliente, int idComercio) throws Exception;

    @Query(value = "SELECT proveedores_vend_comercio.* " +
            "FROM   proveedores_vend_comercio")
    List<ProveedorVendComercioBd> recoveryAll() throws Exception;

}
