package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.DescuentoProveedorGeneralBd;

import java.util.List;

@Dao
public interface IDescuentoProveedorGeneralDao {
    @Query(value = "SELECT dto_proveedor.* " +
            "FROM dto_proveedor " +
            "ORDER BY dto_proveedor.nu_prioridad ASC")
    List<DescuentoProveedorGeneralBd> recoveryAll() throws Exception;

    @Query(value = "SELECT dto_proveedor.* " +
            "FROM dto_proveedor " +
            "WHERE dto_proveedor.id_dto_proveedor = :idDescuentoProveedor")
    DescuentoProveedorGeneralBd recoveryByID(int idDescuentoProveedor) throws Exception;

    @Query(value = "SELECT dto_proveedor.* " +
            "FROM dto_proveedor " +
            "ORDER BY dto_proveedor.nu_prioridad")
    List<DescuentoProveedorGeneralBd> recoveryGenerales() throws Exception;
}
