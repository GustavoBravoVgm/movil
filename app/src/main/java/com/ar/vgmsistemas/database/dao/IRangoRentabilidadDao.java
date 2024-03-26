package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.RangoRentabilidadBd;

import java.util.List;

@Dao
public interface IRangoRentabilidadDao {
    @Query(value = "SELECT * " +
            "FROM rangos_rentabilidad")
    List<RangoRentabilidadBd> recoveryAll() throws Exception;

    @Query(value = "SELECT DISTINCT rangos_rentabilidad.id_proveedor " +
            "FROM   rangos_rentabilidad " +
            "WHERE  rangos_rentabilidad.id_proveedor IS NOT NULL")
    List<Integer> recoveryProveedoresPR() throws Exception;

    @Query(value = "SELECT DISTINCT rangos_rentabilidad.* " +
            "FROM   rangos_rentabilidad " +
            "       INNER JOIN grupo_clientes_detalle " +
            "           ON  grupo_clientes_detalle.id_grupo_clie = rangos_rentabilidad.id_grupo_clie " +
            "WHERE  rangos_rentabilidad.id_proveedor = :idProveedor AND " +
            "       grupo_clientes_detalle.id_sucursal = :idSucursal AND " +
            "       grupo_clientes_detalle.id_cliente = :idCliente AND " +
            "       grupo_clientes_detalle.id_comercio = :idComercio")
    List<RangoRentabilidadBd> recoveryRangosByPIdProveedor(int idProveedor, int idSucursal, int idCliente, int idComercio) throws Exception;

    @Query(value = "SELECT DISTINCT rangos_rentabilidad.* " +
            "FROM   rangos_rentabilidad " +
            "       INNER JOIN grupo_clientes_detalle " +
            "           ON  grupo_clientes_detalle.id_grupo_clie = rangos_rentabilidad.id_grupo_clie " +
            "WHERE  rangos_rentabilidad.id_sucursal = :idSucursalRangoRentabilidad AND " +
            "       grupo_clientes_detalle.id_sucursal = :idSucursal AND " +
            "       grupo_clientes_detalle.id_cliente = :idCliente AND " +
            "       grupo_clientes_detalle.id_comercio = :idComercio")
    List<RangoRentabilidadBd> recoveryRangosBySucursal(int idSucursalRangoRentabilidad, int idSucursal, int idCliente, int idComercio) throws Exception;

}
