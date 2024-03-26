package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ar.vgmsistemas.database.dao.entity.ObjetivoVentaBd;

import java.util.List;

@Dao
public interface IObjetivoVentaDao {
    @Query(value = "SELECT objetivos_venta.* " +
            "FROM   objetivos_venta " +
            "       INNER JOIN comercio " +
            "           ON  comercio.id_sucursal = objetivos_venta.id_sucursal AND " +
            "               comercio.id_cliente = objetivos_venta.id_cliente AND " +
            "               comercio.id_comercio = objetivos_venta.id_comercio " +
            "       INNER JOIN articulos " +
            "           ON  articulos.id_articulos = objetivos_venta.id_articulos " +
            "WHERE  objetivos_venta.id_sucursal = :idSucursal AND " +
            "       objetivos_venta.id_cliente = :idCliente AND " +
            "       objetivos_venta.id_comercio = :idComercio")
    List<ObjetivoVentaBd> recoveryByCliente(int idSucursal, int idCliente, int idComercio) throws Exception;

    @Query(value = "SELECT objetivos_venta.* " +
            "FROM   objetivos_venta " +
            "WHERE  objetivos_venta.id_articulos = :idArticulos AND " +
            "       objetivos_venta.id_sucursal = :idSucursal AND " +
            "       objetivos_venta.id_cliente = :idCliente AND " +
            "       objetivos_venta.id_comercio = :idComercio AND " +
            "       objetivos_venta.id_objetivo = :idObjetivo")
    ObjetivoVentaBd recoveryByID(int idArticulos, int idSucursal, int idCliente, int idComercio, int idObjetivo) throws Exception;

    //reemplaza a void updateCantidadVendida(VentaDetalle ventaDetalle, Cliente cliente, int signo) throws Exception
    @Update(onConflict = OnConflictStrategy.ROLLBACK)
    void update(ObjetivoVentaBd entity) throws Exception;

    @Query(value = "SELECT COALESCE(objetivos_venta.cantidad_vendida,0.00) " +
            "FROM   objetivos_venta " +
            "WHERE  objetivos_venta.id_articulos = :idArticulos AND " +
            "       objetivos_venta.id_sucursal = :idSucursal AND " +
            "       objetivos_venta.id_cliente = :idCliente AND " +
            "       objetivos_venta.id_comercio = :idComercio AND " +
            "       objetivos_venta.id_objetivo = :idObjetivo")
    double recoveryCantidadVendida(int idArticulos, int idSucursal, int idCliente, int idComercio, int idObjetivo) throws Exception;

    @Query(value = "SELECT objetivos_venta.* " +
            "FROM   objetivos_venta " +
            "       INNER JOIN comercio " +
            "           ON  comercio.id_sucursal = objetivos_venta.id_sucursal AND " +
            "               comercio.id_cliente = objetivos_venta.id_cliente AND " +
            "               comercio.id_comercio = objetivos_venta.id_comercio " +
            "       INNER JOIN articulos " +
            "           ON  articulos.id_articulos = objetivos_venta.id_articulos " +
            "       INNER JOIN stock " +
            "           ON  stock.id_articulos = articulos.id_articulos " +
            "WHERE  (objetivos_venta.cantidad - objetivos_venta.cantidad_vendida) > 0 AND " +
            "       objetivos_venta.id_sucursal = :idSucursal AND " +
            "       objetivos_venta.id_cliente = :idCliente AND " +
            "       objetivos_venta.id_comercio = :idComercio")
    List<ObjetivoVentaBd> recoveryNoCumplidosByCliente(int idSucursal, int idCliente, int idComercio) throws Exception;

    @Query(value = "SELECT COUNT(*) " +
            "FROM   objetivos_venta " +
            "WHERE  (objetivos_venta.cantidad - objetivos_venta.cantidad_vendida) > 0 AND " +
            "       objetivos_venta.id_sucursal = :idSucursal AND " +
            "       objetivos_venta.id_cliente = :idCliente AND " +
            "       objetivos_venta.id_comercio = :idComercio")
    int getCantNoCumplidosByCliente(int idSucursal, int idCliente, int idComercio) throws Exception;
}
