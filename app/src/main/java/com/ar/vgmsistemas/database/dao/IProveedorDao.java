package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.ProveedorBd;

import java.util.List;

@Dao
public interface IProveedorDao {

    @Query(value = "SELECT proveedores.* FROM proveedores")
    List<ProveedorBd> recoveryAll() throws Exception;

    @Query(value = "SELECT proveedores.* " +
            "FROM proveedores " +
            "WHERE proveedores.id_proveedor = :idProveedor " +
            "LIMIT 1")
    ProveedorBd recoveryByID(int idProveedor) throws Exception;

    @Query(value = "SELECT proveedores.* " +
            "FROM proveedores " +
            "WHERE proveedores.ti_proveedor = 'G' " +
            "ORDER BY proveedores.id_proveedor ASC")
    List<ProveedorBd> recoveryProveedoresTipoGasto() throws Exception;

    @Query(value = "SELECT proveedores.* " +
            "FROM   proveedores " +
            "WHERE  proveedores.ti_proveedor = 'G' AND " +
            "       proveedores.id_sucursal = :idSucursal " +
            "ORDER BY proveedores.id_proveedor ASC")
    List<ProveedorBd> recoveryProveedoresTipoGastoBySucursal(int idSucursal) throws Exception;

}
