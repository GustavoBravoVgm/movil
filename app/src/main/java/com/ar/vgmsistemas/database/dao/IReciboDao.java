package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ar.vgmsistemas.database.dao.entity.ReciboBd;

import java.util.List;

@Dao
public interface IReciboDao {
    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    void create(ReciboBd entity) throws Exception;

    @Query(value = "SELECT talon_recibo.* " +
            "FROM   talon_recibo " +
            "       INNER JOIN comercio " +
            "           ON  comercio.id_sucursal = talon_recibo.id_sucursal AND " +
            "               comercio.id_cliente = talon_recibo.id_cliente AND " +
            "               comercio.id_comercio = talon_recibo.id_comercio " +
            "       INNER JOIN vendedor " +
            "           ON  vendedor.id_vendedor = talon_recibo.id_vendedor " +
            "       INNER JOIN rrhh " +
            "           ON  rrhh.id_legajo = vendedor.id_vendedor " +
            "       INNER JOIN entrega " +
            "           ON entrega.id_entrega = talon_recibo.id_entrega")
    List<ReciboBd> recoveryAll() throws Exception;



    @Query(value = "SELECT talon_recibo.* " +
            "FROM   talon_recibo " +
            "       INNER JOIN comercio " +
            "           ON  comercio.id_sucursal = talon_recibo.id_sucursal AND " +
            "               comercio.id_cliente = talon_recibo.id_cliente AND " +
            "               comercio.id_comercio = talon_recibo.id_comercio " +
            "       INNER JOIN vendedor " +
            "           ON  vendedor.id_vendedor = talon_recibo.id_vendedor " +
            "       INNER JOIN rrhh " +
            "           ON  rrhh.id_legajo = vendedor.id_vendedor " +
            "       INNER JOIN entrega " +
            "           ON entrega.id_entrega = talon_recibo.id_entrega " +
            "WHERE  talon_recibo.id_recpvta = :idPtoVta AND" +
            "       talon_recibo.id_recibo = :idRecibo ")
    ReciboBd recoveryById(int idPtoVta,long idRecibo) throws Exception;

    @Query(value = "SELECT talon_recibo.* " +
            "FROM   talon_recibo " +
            "       INNER JOIN comercio " +
            "           ON  comercio.id_sucursal = talon_recibo.id_sucursal AND " +
            "               comercio.id_cliente = talon_recibo.id_cliente AND " +
            "               comercio.id_comercio = talon_recibo.id_comercio " +
            "WHERE  talon_recibo.id_estado IS NOT NULL AND " +
            "       talon_recibo.id_estado <> 'I'")
    List<ReciboBd> recoveryDescargados() throws Exception;

    @Update(onConflict = OnConflictStrategy.ROLLBACK)
    void update(ReciboBd entity) throws Exception;

    @Query(value = "UPDATE talon_recibo " +
            "SET    id_estado = 'I'" +
            "WHERE  talon_recibo.id_recpvta = :idPuntoVenta AND " +
            "       talon_recibo.id_recibo <= :ultimoIdNumeroRecibo")
    void updateEstadoImputado(int idPuntoVenta, long ultimoIdNumeroRecibo) throws Exception;

    @Query(value = "SELECT DISTINCT talon_recibo.id_recpvta " +
            "FROM   talon_recibo " +
            "WHERE  talon_recibo.id_estado IS NULL AND " +
            "       talon_recibo.id_vendedor = :idVendedor")
    List<Integer> getPuntosVentaRecibo(int idVendedor) throws Exception;

    @Query(value = "SELECT DISTINCT COALESCE(talon_recibo.id_recibo,-1) " +
            "FROM   talon_recibo " +
            "WHERE  talon_recibo.id_estado IS NULL AND " +
            "       talon_recibo.id_recpvta = :idPuntoVenta")
    int getSiguienteNumeroRecibo(int idPuntoVenta) throws Exception;

    @Query(value = "SELECT COUNT(*) " +
            "FROM   talon_recibo " +
            "WHERE  talon_recibo.id_estado IS NULL AND " +
            "       talon_recibo.id_recibo = :numeroRecibo")
    int validateNumeroRecibo(long numeroRecibo) throws Exception;

    @Query(value = "SELECT talon_recibo.* " +
            "FROM   talon_recibo " +
            "       INNER JOIN comercio " +
            "           ON  comercio.id_sucursal = talon_recibo.id_sucursal AND " +
            "               comercio.id_cliente = talon_recibo.id_cliente AND " +
            "               comercio.id_comercio = talon_recibo.id_comercio " +
            "       INNER JOIN vendedor " +
            "           ON  vendedor.id_vendedor = talon_recibo.id_vendedor " +
            "       INNER JOIN rrhh " +
            "           ON  rrhh.id_legajo = vendedor.id_vendedor " +
            "       INNER JOIN entrega " +
            "           ON entrega.id_entrega = talon_recibo.id_entrega " +
            "       INNER JOIN movimientos " +
            "           ON  movimientos.id_movil = talon_recibo.id_movil " +
            "WHERE movimientos.fe_sincronizacion IS NULL")
    List<ReciboBd> recoveryNoEnviados() throws Exception;

    @Query(value = "SELECT talon_recibo.* " +
            "FROM   talon_recibo " +
            "       INNER JOIN comercio " +
            "           ON  comercio.id_sucursal = talon_recibo.id_sucursal AND " +
            "               comercio.id_cliente = talon_recibo.id_cliente AND " +
            "               comercio.id_comercio = talon_recibo.id_comercio " +
            "       INNER JOIN vendedor " +
            "           ON  vendedor.id_vendedor = talon_recibo.id_vendedor " +
            "       INNER JOIN rrhh " +
            "           ON  rrhh.id_legajo = vendedor.id_vendedor " +
            "       INNER JOIN entrega " +
            "           ON entrega.id_entrega = talon_recibo.id_entrega " +
            "WHERE  talon_recibo.id_estado IN ('P','I')")
    List<ReciboBd> recoveryNoRendidos() throws Exception;


    @Query(value = "UPDATE talon_recibo " +
            "SET    resultado_envio = :resultado " +
            "WHERE  talon_recibo.id_recpvta = :idPuntoVenta " +
            "       AND talon_recibo.id_recibo = :idRecibo ")
    void updateResultEnvio(int idPuntoVenta, long idRecibo, int resultado) throws Exception;

    @Query(value = "SELECT documentos.id_ptovta " +
            "FROM   documentos " +
            "WHERE  documentos.ti_documento = 'R' AND " +
            "       documentos.id_ptovta = :sucCliente")
    List<Integer> getPuntosVentaReciboPorSucCliente(long sucCliente);
}
