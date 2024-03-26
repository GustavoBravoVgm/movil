package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.DescuentoProveedorBd;

import java.util.List;

@Dao
public interface IDescuentoProveedorDao {
    @Query(value = "SELECT v_dto_proveedor_movil.* " +
            "FROM   v_dto_proveedor_movil " +
            "WHERE  v_dto_proveedor_movil.id_vendedor = :idVendedor AND " +
            "v_dto_proveedor_movil.id_sucursal = :idSucursal AND " +
            "v_dto_proveedor_movil.id_cliente = :idCliente AND " +
            "v_dto_proveedor_movil.id_comercio = :idComercio AND " +
            "v_dto_proveedor_movil.id_dto_proveedor_cliente = :idDescuentoProveedorCliente AND " +
            "v_dto_proveedor_movil.id_dto_proveedor = :idDescuentoProveedor")
    DescuentoProveedorBd recoveryByID(int idVendedor, int idSucursal, int idCliente, int idComercio,
                                      int idDescuentoProveedorCliente, int idDescuentoProveedor) throws Exception;

    @Query(value = "SELECT v_dto_proveedor_movil.* " +
            "FROM   v_dto_proveedor_movil " +
            "WHERE  v_dto_proveedor_movil.id_dto_proveedor_cliente = :idDtoProveedor")
    DescuentoProveedorBd recoveryByID(int idDtoProveedor) throws Exception;

    @Query(value = "SELECT v_dto_proveedor_movil.* " +
            "FROM   v_dto_proveedor_movil " +
            "WHERE v_dto_proveedor_movil.id_sucursal = :idSucursal AND " +
            "v_dto_proveedor_movil.id_cliente = :idCliente " +
            "ORDER BY v_dto_proveedor_movil.nu_prioridad ASC")
    List<DescuentoProveedorBd> recoveryByCliente(int idSucursal, int idCliente) throws Exception;

}
