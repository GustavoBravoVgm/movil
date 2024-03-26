package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.SecuenciaRuteoBd;
import com.ar.vgmsistemas.database.dao.entity.result.SecuenciaRuteoResultBd;

import java.util.List;

@Dao
public interface ISecuenciaRuteoDao {
    @Query(value = "SELECT vend_comercio_ruta.* " +
            "FROM   vend_comercio_ruta " +
            "WHERE  vend_comercio_ruta.id_vendedor = :idVendedor AND " +
            "       vend_comercio_ruta.id_sucursal = :idSucursal AND" +
            "       vend_comercio_ruta.id_cliente = :idCliente AND " +
            "       vend_comercio_ruta.id_comercio = :idComercio")
    SecuenciaRuteoBd recoveryByID(int idVendedor, int idSucursal, int idCliente, int idComercio) throws Exception;

    @Query(value = "SELECT vend_comercio_ruta.* " +
            "FROM vend_comercio_ruta")
    List<SecuenciaRuteoBd> recoveryAll() throws Exception;

    @Query(value = "SELECT vend_comercio_ruta.* , comercio.id_postal " +
            "FROM   vend_comercio_ruta " +
            "       INNER JOIN comercio " +
            "           ON  comercio.id_sucursal = vend_comercio_ruta.id_sucursal AND " +
            "               comercio.id_cliente = vend_comercio_ruta.id_cliente AND " +
            "               comercio.id_comercio = vend_comercio_ruta.id_comercio " +
            "WHERE  ((:idLocalidad = -1) OR (:idLocalidad <> -1 AND comercio.id_postal = :idLocalidad)) AND " +
            "       ((:idSucursal = -1) OR (:idSucursal <> -1 AND vend_comercio_ruta.id_sucursal = :idSucursal)) AND " +
            "       ((:dia > 7) OR (:dia <= 7 AND vend_comercio_ruta.dia = :dia))")
    List<SecuenciaRuteoResultBd> recoveryRutasByDia(int dia, int idLocalidad, int idSucursal) throws Exception;
}
