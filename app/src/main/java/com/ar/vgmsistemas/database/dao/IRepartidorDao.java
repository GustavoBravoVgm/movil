package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.RepartidorBd;

import java.util.List;

@Dao
public interface IRepartidorDao {
    @Query(value = "SELECT repartidor.* " +
            "FROM repartidor " +
            "WHERE repartidor.id_repartidor = :id")
    RepartidorBd recoveryByID(int id) throws Exception;

    @Query(value = "SELECT repartidor.* " +
            "FROM   repartidor " +
            "       INNER JOIN rrhh " +
            "           ON repartidor.id_repartidor = rrhh.id_legajo " +
            "WHERE repartidor.sn_movil = 'S'")
    List<RepartidorBd> recoveryAll() throws Exception;

    @Query(value = "SELECT repartidor.* " +
            "FROM   repartidor " +
            "       INNER JOIN rrhh " +
            "           ON repartidor.id_repartidor = rrhh.id_legajo " +
            "       INNER JOIN comercio " +
            "           ON comercio.id_repartidor = repartidor.id_repartidor " +
            "WHERE  comercio.id_sucursal = :idSucursal AND " +
            "       comercio.id_cliente = :idCliente AND " +
            "       comercio.id_comercio = :idComercio ")
    RepartidorBd recoveryByCliente(int idSucursal, int idCliente, int idComercio) throws Exception;

    @Query(value = "SELECT repartidor.* " +
            "FROM   repartidor " +
            "       INNER JOIN rrhh " +
            "           ON repartidor.id_repartidor = rrhh.id_legajo " +
            "       INNER JOIN ventas " +
            "           ON ventas.id_repartidor = repartidor.id_repartidor " +
            "WHERE  ventas.id_fcnc = :idDocumento AND " +
            "       ventas.id_tipoab = :idLetra AND " +
            "       ventas.id_ptovta = :puntoVenta AND " +
            "       ventas.id_numdoc = :idNumeroDocumento")
    RepartidorBd recoveryByVenta(String idDocumento, String idLetra, int puntoVenta, long idNumeroDocumento) throws Exception;
}
