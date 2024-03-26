package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ar.vgmsistemas.database.dao.entity.AccionesComDetalleBd;

import java.util.List;

@Dao
public interface IAccionesComDetalleDao {

    @Query(value = "SELECT acciones_com_detalle.* " +
            "FROM   acciones_com_detalle " +
            "WHERE  id_acciones_com = :idAccionesCom AND " +
            "       id_acciones_com_detalle = :idAccionesComDetalle")
    AccionesComDetalleBd recoveryByID(int idAccionesCom, int idAccionesComDetalle) throws Exception;

    @Query(value = "SELECT	acciones_com_detalle.* " +
            "FROM	acciones_grupos " +
            "		INNER JOIN acciones_com " +
            "			ON acciones_grupos.id_acciones_com = acciones_com.id_acciones_com " +
            "		INNER JOIN acciones_com_detalle " +
            "			ON acciones_grupos.id_acciones_com = acciones_com_detalle.id_acciones_com " +
            "		INNER JOIN grupo_clientes_detalle " +
            "			ON acciones_grupos.id_grupo_clie = grupo_clientes_detalle.id_grupo_clie " +
            "WHERE	grupo_clientes_detalle.id_sucursal = :idSucursal AND " +
            "		grupo_clientes_detalle.id_cliente = :idCliente AND " +
            "		grupo_clientes_detalle.id_comercio = :idComercio AND  " +
            "		acciones_com.ti_origen = :origen AND " +
            "		acciones_com.id_tipo_acciones = 4 AND " +
            "		acciones_com_detalle.id_articulos = :idArticulo AND " +
            "		(acciones_com_detalle.rg_limite_inf is null OR acciones_com_detalle.rg_limite_inf <= :cantidad) AND	 " +
            "		(acciones_com_detalle.rg_limite_sup is null OR acciones_com_detalle.rg_limite_sup = 0 OR " +
            "		acciones_com_detalle.rg_limite_sup > :cantidad) AND " +
            "		(:origen <> 'P' OR " +
            "		 (:origen = 'P' and acciones_com_detalle.id_proveedor = :idProveedor)) ")
    AccionesComDetalleBd recoveryAccionPorArticulo(int idArticulo, double cantidad, int idProveedor,
                                                   int idSucursal, int idCliente, int idComercio,
                                                   String origen) throws Exception;

    @Query(value = "SELECT	acciones_com_detalle.* " +
            "FROM	acciones_grupos " +
            "		INNER JOIN acciones_com " +
            "			ON acciones_grupos.id_acciones_com = acciones_com.id_acciones_com " +
            "		INNER JOIN acciones_com_detalle " +
            "			ON acciones_grupos.id_acciones_com = acciones_com_detalle.id_acciones_com " +
            "		INNER JOIN grupo_clientes_detalle " +
            "			ON acciones_grupos.id_grupo_clie = grupo_clientes_detalle.id_grupo_clie " +
            "WHERE	grupo_clientes_detalle.id_sucursal = :idSucursal AND " +
            "		grupo_clientes_detalle.id_cliente = :idCliente AND " +
            "		grupo_clientes_detalle.id_comercio = :idComercio AND  " +
            "		acciones_com.ti_origen = :origen AND " +
            "		acciones_com.id_tipo_acciones = 5 AND " +
            "		acciones_com_detalle.id_linea = :idMarca AND " +
            "		(acciones_com_detalle.rg_limite_inf is null OR acciones_com_detalle.rg_limite_inf <= :cantidad) AND	 " +
            "		(acciones_com_detalle.rg_limite_sup is null OR acciones_com_detalle.rg_limite_sup = 0 OR " +
            "		acciones_com_detalle.rg_limite_sup > :cantidad) AND " +
            "		(:origen <> 'P' OR " +
            "		 (:origen = 'P' and acciones_com_detalle.id_proveedor = :idProveedor)) ")
    AccionesComDetalleBd recoveryAccionPorMarca(int idMarca, double cantidad, int idProveedor,
                                                int idSucursal, int idCliente, int idComercio,
                                                String origen) throws Exception;

    @Query(value = "SELECT	acciones_com_detalle.* " +
            "FROM	acciones_grupos " +
            "		INNER JOIN acciones_com " +
            "			ON acciones_grupos.id_acciones_com = acciones_com.id_acciones_com " +
            "		INNER JOIN acciones_com_detalle " +
            "			ON acciones_grupos.id_acciones_com = acciones_com_detalle.id_acciones_com " +
            "		INNER JOIN grupo_clientes_detalle " +
            "			ON acciones_grupos.id_grupo_clie = grupo_clientes_detalle.id_grupo_clie " +
            "WHERE	grupo_clientes_detalle.id_sucursal = :idSucursal AND " +
            "		grupo_clientes_detalle.id_cliente = :idCliente AND " +
            "		grupo_clientes_detalle.id_comercio = :idComercio AND  " +
            "		acciones_com.ti_origen = :origen AND " +
            "		acciones_com.id_tipo_acciones = 3 AND " +
            "		acciones_com_detalle.id_negocio = :idNegocio AND " +
            "		acciones_com_detalle.id_segmento = :idRubro AND " +
            "		acciones_com_detalle.id_subrubro = :idSubrubro AND " +
            "		(acciones_com_detalle.rg_limite_inf is null OR acciones_com_detalle.rg_limite_inf <= :cantidad) AND	 " +
            "		(acciones_com_detalle.rg_limite_sup is null OR acciones_com_detalle.rg_limite_sup = 0 OR " +
            "		acciones_com_detalle.rg_limite_sup > :cantidad) AND " +
            "		(:origen <> 'P' OR " +
            "		 (:origen = 'P' and acciones_com_detalle.id_proveedor = :idProveedor)) ")
    AccionesComDetalleBd recoveryAccionPorSubrubro(int idNegocio, int idRubro, int idSubrubro,
                                                   double cantidad, int idProveedor, int idSucursal,
                                                   int idCliente, int idComercio, String origen) throws Exception;


    @Query(value = "SELECT	acciones_com_detalle.* " +
            "FROM	acciones_grupos " +
            "		INNER JOIN acciones_com " +
            "			ON acciones_grupos.id_acciones_com = acciones_com.id_acciones_com " +
            "		INNER JOIN acciones_com_detalle " +
            "			ON acciones_grupos.id_acciones_com = acciones_com_detalle.id_acciones_com " +
            "		INNER JOIN grupo_clientes_detalle " +
            "			ON acciones_grupos.id_grupo_clie = grupo_clientes_detalle.id_grupo_clie " +
            "WHERE	grupo_clientes_detalle.id_sucursal = :idSucursal AND " +
            "		grupo_clientes_detalle.id_cliente = :idCliente AND " +
            "		grupo_clientes_detalle.id_comercio = :idComercio AND  " +
            "		acciones_com.ti_origen = :origen AND " +
            "		acciones_com.id_tipo_acciones = 2 AND " +
            "		acciones_com_detalle.id_negocio = :idNegocio AND " +
            "		acciones_com_detalle.id_segmento = :idRubro AND " +
            "		(acciones_com_detalle.rg_limite_inf is null OR acciones_com_detalle.rg_limite_inf <= :cantidad) AND	 " +
            "		(acciones_com_detalle.rg_limite_sup is null OR acciones_com_detalle.rg_limite_sup = 0 OR " +
            "		acciones_com_detalle.rg_limite_sup > :cantidad) AND " +
            "		(:origen <> 'P' OR " +
            "		 (:origen = 'P' and acciones_com_detalle.id_proveedor = :idProveedor)) ")
    AccionesComDetalleBd recoveryAccionPorRubro(int idNegocio, int idRubro,
                                                double cantidad, int idProveedor, int idSucursal,
                                                int idCliente, int idComercio, String origen) throws Exception;

    @Query(value = "SELECT	acciones_com_detalle.* " +
            "FROM	acciones_grupos " +
            "		INNER JOIN acciones_com " +
            "			ON acciones_grupos.id_acciones_com = acciones_com.id_acciones_com " +
            "		INNER JOIN acciones_com_detalle " +
            "			ON acciones_grupos.id_acciones_com = acciones_com_detalle.id_acciones_com " +
            "		INNER JOIN grupo_clientes_detalle " +
            "			ON acciones_grupos.id_grupo_clie = grupo_clientes_detalle.id_grupo_clie " +
            "WHERE	grupo_clientes_detalle.id_sucursal = :idSucursal AND " +
            "		grupo_clientes_detalle.id_cliente = :idCliente AND " +
            "		grupo_clientes_detalle.id_comercio = :idComercio AND  " +
            "		acciones_com.ti_origen = :origen AND " +
            "		acciones_com.id_tipo_acciones =  1 AND " +
            "		acciones_com_detalle.id_negocio = :idNegocio AND " +
            "		(acciones_com_detalle.rg_limite_inf is null OR acciones_com_detalle.rg_limite_inf <= :cantidad) AND	 " +
            "		(acciones_com_detalle.rg_limite_sup is null OR acciones_com_detalle.rg_limite_sup = 0 OR " +
            "		acciones_com_detalle.rg_limite_sup > :cantidad) AND " +
            "		(:origen <> 'P' OR " +
            "		 (:origen = 'P' and acciones_com_detalle.id_proveedor = :idProveedor)) ")
    AccionesComDetalleBd recoveryAccionPorNegocio(int idNegocio, double cantidad, int idProveedor,
                                                  int idSucursal, int idCliente, int idComercio,
                                                  String origen) throws Exception;

    @Query(value = "SELECT DISTINCT acciones_com_detalle.* " +
            "FROM	acciones_grupos " +
            "		INNER JOIN acciones_com " +
            "			ON acciones_grupos.id_acciones_com = acciones_com.id_acciones_com " +
            "		INNER JOIN acciones_com_detalle " +
            "			ON acciones_grupos.id_acciones_com = acciones_com_detalle.id_acciones_com " +
            "		INNER JOIN grupo_clientes_detalle " +
            "			ON acciones_grupos.id_grupo_clie = grupo_clientes_detalle.id_grupo_clie " +
            "WHERE	grupo_clientes_detalle.id_sucursal = :idSucursal AND " +
            "		grupo_clientes_detalle.id_cliente = :idCliente AND " +
            "		grupo_clientes_detalle.id_comercio = :idComercio AND  " +
            "		acciones_com.ti_origen = :origen AND " +
            "		acciones_com.id_tipo_acciones = 4 AND " +
            "		acciones_com_detalle.id_articulos = :idArticulo AND " +
            "		((:origen <> 'P' and :origen <> 'C') OR " +
            "		 ((:origen = 'P' OR :origen = 'C') and acciones_com_detalle.id_proveedor = :idProveedor)) ")
    List<AccionesComDetalleBd> recoveryAllPorArticulo(int idArticulo, int idProveedor,
                                                      int idSucursal, int idCliente, int idComercio,
                                                      String origen) throws Exception;

    @Query(value = "SELECT	DISTINCT acciones_com_detalle.* " +
            "FROM	acciones_grupos " +
            "		INNER JOIN acciones_com " +
            "			ON acciones_grupos.id_acciones_com = acciones_com.id_acciones_com " +
            "		INNER JOIN acciones_com_detalle " +
            "			ON acciones_grupos.id_acciones_com = acciones_com_detalle.id_acciones_com " +
            "		INNER JOIN grupo_clientes_detalle " +
            "			ON acciones_grupos.id_grupo_clie = grupo_clientes_detalle.id_grupo_clie " +
            "WHERE	grupo_clientes_detalle.id_sucursal = :idSucursal AND " +
            "		grupo_clientes_detalle.id_cliente = :idCliente AND " +
            "		grupo_clientes_detalle.id_comercio = :idComercio AND  " +
            "		acciones_com.ti_origen = :origen AND " +
            "		acciones_com.id_tipo_acciones = 5 AND " +
            "		acciones_com_detalle.id_linea = :idMarca AND " +
            "		((:origen <> 'P' AND :origen <> 'C') OR " +
            "		 ((:origen = 'P' OR :origen = 'C') and acciones_com_detalle.id_proveedor = :idProveedor)) ")
    List<AccionesComDetalleBd> recoveryAllPorMarca(int idMarca, int idProveedor, int idSucursal,
                                                   int idCliente, int idComercio, String origen)
            throws Exception;

    @Query(value = "SELECT	DISTINCT acciones_com_detalle.* " +
            "FROM	acciones_grupos " +
            "		INNER JOIN acciones_com " +
            "			ON acciones_grupos.id_acciones_com = acciones_com.id_acciones_com " +
            "		INNER JOIN acciones_com_detalle " +
            "			ON acciones_grupos.id_acciones_com = acciones_com_detalle.id_acciones_com " +
            "		INNER JOIN grupo_clientes_detalle " +
            "			ON acciones_grupos.id_grupo_clie = grupo_clientes_detalle.id_grupo_clie " +
            "WHERE	grupo_clientes_detalle.id_sucursal = :idSucursal AND " +
            "		grupo_clientes_detalle.id_cliente = :idCliente AND " +
            "		grupo_clientes_detalle.id_comercio = :idComercio AND  " +
            "		acciones_com.ti_origen = :origen AND " +
            "		acciones_com.id_tipo_acciones = 3 AND " +
            "		acciones_com_detalle.id_negocio = :idNegocio AND " +
            "		acciones_com_detalle.id_segmento = :idRubro AND " +
            "		acciones_com_detalle.id_subrubro = :idSubrubro AND " +
            "		((:origen <> 'P' AND :origen <> 'C') OR " +
            "		 ((:origen = 'P' OR :origen = 'C') and acciones_com_detalle.id_proveedor = :idProveedor)) ")
    List<AccionesComDetalleBd> recoveryAllPorSubrubro(int idNegocio, int idRubro, int idSubrubro,
                                                      int idProveedor, int idSucursal, int idCliente,
                                                      int idComercio, String origen) throws Exception;

    @Query(value = "SELECT	DISTINCT acciones_com_detalle.* " +
            "FROM	acciones_grupos " +
            "		INNER JOIN acciones_com " +
            "			ON acciones_grupos.id_acciones_com = acciones_com.id_acciones_com " +
            "		INNER JOIN acciones_com_detalle " +
            "			ON acciones_grupos.id_acciones_com = acciones_com_detalle.id_acciones_com " +
            "		INNER JOIN grupo_clientes_detalle " +
            "			ON acciones_grupos.id_grupo_clie = grupo_clientes_detalle.id_grupo_clie " +
            "WHERE	grupo_clientes_detalle.id_sucursal = :idSucursal AND " +
            "		grupo_clientes_detalle.id_cliente = :idCliente AND " +
            "		grupo_clientes_detalle.id_comercio = :idComercio AND  " +
            "		acciones_com.ti_origen = :origen AND " +
            "		acciones_com.id_tipo_acciones = 2 AND " +
            "		acciones_com_detalle.id_negocio = :idNegocio AND " +
            "		acciones_com_detalle.id_segmento = :idRubro AND " +
            "		((:origen <> 'P' AND :origen <> 'C') OR " +
            "		 ((:origen = 'P' OR :origen = 'C') and acciones_com_detalle.id_proveedor = :idProveedor)) ")
    List<AccionesComDetalleBd> recoveryAllPorRubro(int idNegocio, int idRubro,
                                                   int idProveedor, int idSucursal, int idCliente,
                                                   int idComercio, String origen) throws Exception;


    @Query(value = "SELECT	acciones_com_detalle.* " +
            "FROM	acciones_grupos " +
            "		INNER JOIN acciones_com " +
            "			ON acciones_grupos.id_acciones_com = acciones_com.id_acciones_com " +
            "		INNER JOIN acciones_com_detalle " +
            "			ON acciones_grupos.id_acciones_com = acciones_com_detalle.id_acciones_com " +
            "		INNER JOIN grupo_clientes_detalle " +
            "			ON acciones_grupos.id_grupo_clie = grupo_clientes_detalle.id_grupo_clie " +
            "WHERE	grupo_clientes_detalle.id_sucursal = :idSucursal AND " +
            "		grupo_clientes_detalle.id_cliente = :idCliente AND " +
            "		grupo_clientes_detalle.id_comercio = :idComercio AND  " +
            "		acciones_com.ti_origen = :origen AND " +
            "		acciones_com.id_tipo_acciones =  1 AND " +
            "		acciones_com_detalle.id_negocio = :idNegocio AND " +
            "		((:origen <> 'P' AND :origen <> 'C') OR " +
            "		 ((:origen = 'P' OR :origen = 'C') and acciones_com_detalle.id_proveedor = :idProveedor)) ")
    List<AccionesComDetalleBd> recoveryAllPorNegocio(int idNegocio, int idProveedor, int idSucursal,
                                                     int idCliente, int idComercio, String origen)
            throws Exception;

    @Query(value = "UPDATE acciones_com_detalle " +
            "SET    ca_vendida = IFNULL(ca_vendida,0.00) + (:cantidadVendida * :tipoOperacion) " +
            "WHERE  id_acciones_com = :idAccionesCom AND " +
            "       id_acciones_com_detalle = :idAccionesComDetalle ")
    void updateCaVendidaAcCom(int idAccionesCom, int idAccionesComDetalle, double cantidadVendida, int tipoOperacion)
            throws Exception;

    @Update(onConflict = OnConflictStrategy.ROLLBACK)
    void updateAccionesComDetalle(AccionesComDetalleBd accionesComDetalle);
}
