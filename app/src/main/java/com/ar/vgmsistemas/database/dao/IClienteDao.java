package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.ar.vgmsistemas.database.dao.entity.ClienteBd;

import java.util.List;

@Dao
public interface IClienteDao {

    @Query(value = "SELECT comercio.* " +
            "FROM comercio " +
            "       INNER JOIN condvta " +
            "           ON condvta.id_condvta = comercio.id_condvta " +
            "       INNER JOIN cond_Iva " +
            "           ON cond_Iva.ti_contribuyente = comercio.ti_contribuyente " +
            "       INNER JOIN localidades " +
            "           ON localidades.id_postal = comercio.id_postal " +
            "       INNER JOIN listasPrecios " +
            "           ON listasPrecios.id_lista = comercio.id_lista " +
            "ORDER BY comercio.de_organizacion ASC")
    List<ClienteBd> recoveryAll() throws Exception;

    @Transaction
    @Query(value = "SELECT comercio.* " +
            "FROM   comercio " +
            "       INNER JOIN condvta " +
            "           ON condvta.id_condvta = comercio.id_condvta " +
            "       INNER JOIN cond_Iva " +
            "           ON cond_Iva.ti_contribuyente = comercio.ti_contribuyente " +
            "       INNER JOIN localidades " +
            "           ON localidades.id_postal = comercio.id_postal " +
            "       INNER JOIN listasPrecios " +
            "           ON listasPrecios.id_lista = comercio.id_lista " +
            "WHERE 	(:idLocalidad = -1 OR ( :idLocalidad <> -1 AND comercio.id_postal = :idLocalidad)) AND " +
            "       (:idSucursalCte = -1 OR ( :idSucursalCte <> -1 AND comercio.id_sucursal = :idSucursalCte))" +
            "GROUP BY comercio.id_cliente, comercio.id_sucursal " +
            "ORDER BY comercio.de_organizacion ASC")
    List<ClienteBd> recoveryAllGroupByCteSuc(int idLocalidad, int idSucursalCte) throws Exception;

    @Query(value = "SELECT comercio.* " +
            "FROM   comercio " +
            "       INNER JOIN condvta " +
            "           ON condvta.id_condvta = comercio.id_condvta " +
            "       INNER JOIN cond_Iva " +
            "           ON cond_Iva.ti_contribuyente = comercio.ti_contribuyente " +
            "       INNER JOIN localidades " +
            "           ON localidades.id_postal = comercio.id_postal " +
            "       INNER JOIN listasPrecios " +
            "           ON listasPrecios.id_lista = comercio.id_lista " +
            "       INNER JOIN provincias " +
            "           ON provincias.id_provincia = localidades.id_provincia " +
            "WHERE 	comercio.id_sucursal = :idSucursal  AND " +
            "       comercio.id_cliente = :idCliente AND " +
            "       comercio.id_comercio = :idComercio " +
            "ORDER BY comercio.de_organizacion")
    ClienteBd recoveryByID(int idSucursal, int idCliente, int idComercio) throws Exception;

    @Update(onConflict = OnConflictStrategy.ROLLBACK)
    void update(ClienteBd cliente) throws Exception;


    @Query(value = "SELECT  comercio.* " +
            "FROM   comercio " +
            "       INNER JOIN movimientos " +
            "           ON movimientos.id_movil = comercio.id_movil_comercio " +
            "WHERE  movimientos.fe_sincronizacion IS NULL ")
    List<ClienteBd> recoveryNoEnviados() throws Exception;

    @Query(value = "SELECT vend_comercio.id_vendedor " +
            "FROM   comercio " +
            "       INNER JOIN vend_comercio " +
            "           ON  vend_comercio.id_sucursal = comercio.id_sucursal AND " +
            "               vend_comercio.id_cliente = comercio.id_cliente AND " +
            "               vend_comercio.id_comercio = comercio.id_comercio " +
            "       INNER JOIN rrhh " +
            "           ON rrhh.id_legajo = vend_comercio.id_vendedor " +
            "WHERE 	comercio.id_sucursal = :idSucursal  AND " +
            "       comercio.id_cliente = :idCliente AND " +
            "       comercio.id_comercio = :idComercio " +
            "ORDER BY comercio.de_organizacion")
    int recoveryVendedorCliente(int idSucursal, int idCliente, int idComercio) throws Exception;

    @Query(value = "UPDATE  comercio " +
            "       SET     pr_limitedisponibilidad = pr_limitedisponibilidad - :saldo " +
            "       WHERE 	comercio.id_sucursal = :idSucursal  AND " +
            "               comercio.id_cliente = :idCliente AND " +
            "               comercio.id_comercio = :idComercio ")
    void updateLimiteDisponibilidad(int idSucursal, int idCliente, int idComercio, double saldo) throws Exception;
}
