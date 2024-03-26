package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.ar.vgmsistemas.database.dao.entity.CuentaCorrienteBd;

import java.util.List;

@Dao
public interface ICuentaCorrienteDao {

    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    void create(CuentaCorrienteBd cuentaCorriente) throws Exception;

    @Transaction
    @Query(value = "SELECT documentos_cuenta.* " +
            "FROM   documentos_cuenta " +
            "       INNER JOIN condvta " +
            "           ON condvta.id_condvta = documentos_cuenta.id_condvta " +
            "WHERE  documentos_cuenta.id_succliente = :idSucursal AND " +
            "       documentos_cuenta.id_cliente = :idCliente AND " +
            "       (documentos_cuenta.pr_nc + documentos_cuenta.pr_pagado) < " +
            "       documentos_cuenta.pr_cuota")
    List<CuentaCorrienteBd> recoveryByCliente(int idSucursal, int idCliente) throws Exception;

    @Transaction
    @Query(value = "SELECT documentos_cuenta.* " +
            "FROM   documentos_cuenta " +
            "       INNER JOIN condvta " +
            "           ON condvta.id_condvta = documentos_cuenta.id_condvta " +
            "       INNER JOIN talon_recibo " +
            "           ON  talon_recibo.id_recpvta = documentos_cuenta.id_recptovta AND " +
            "               talon_recibo.id_recibo = documentos_cuenta.id_recibo " +
            "WHERE  documentos_cuenta.id_recptovta = :idPuntoVenta AND " +
            "       documentos_cuenta.id_recibo = :idRecibo AND " +
            "       (documentos_cuenta.pr_nc + documentos_cuenta.pr_pagado) < " +
            "           documentos_cuenta.pr_cuota " +
            "ORDER BY documentos_cuenta.fe_venta")
    List<CuentaCorrienteBd> recoveryByRecibo(int idPuntoVenta, long idRecibo) throws Exception;

    @Transaction
    @Query(value = "SELECT  documentos_cuenta.* " +
            "FROM   documentos_cuenta " +
            "WHERE  (documentos_cuenta.pr_nc + documentos_cuenta.pr_pagado) < " +
            "           documentos_cuenta.pr_cuota AND " +
            "       (documentos_cuenta.pr_nc + documentos_cuenta.pr_pagado) < " +
            "       documentos_cuenta.pr_cuota " +
            "ORDER BY documentos_cuenta.fe_venta")
    List<CuentaCorrienteBd> recoveryAll() throws Exception;

    @Update(onConflict = OnConflictStrategy.ROLLBACK)
    void update(CuentaCorrienteBd cuentaCorriente) throws Exception;

    @Transaction
    @Query(value = "SELECT  documentos_cuenta.* " +
            "FROM   documentos_cuenta " +
            "WHERE  documentos_cuenta.id_fcnc = :idDocumento AND " +
            "       documentos_cuenta.id_tipoab = :idLetra AND " +
            "       documentos_cuenta.id_ptovta = :puntoVenta AND " +
            "       documentos_cuenta.id_numdoc = :idNumeroDocumento AND " +
            "       documentos_cuenta.nu_cuota = :cuota " +
            "ORDER BY documentos_cuenta.fe_venta")
    CuentaCorrienteBd recoveryByID(String idDocumento, String idLetra, int puntoVenta, long idNumeroDocumento, int cuota) throws Exception;

    @Transaction
    @Query(value = "SELECT  (SUM(documentos_cuenta.pr_cuota - documentos_cuenta.pr_pagado - documentos_cuenta.pr_nc) " +
            "               * documentos_cuenta.ti_aplica_cc) AS total " +
            "FROM   documentos_cuenta " +
            "WHERE  (documentos_cuenta.id_succliente = :idSucursal AND " +
            "        documentos_cuenta.id_cliente = :idCliente AND " +
            "        :snClienteUnico <> 'S') OR " +
            "       (documentos_cuenta.id_cliente = :idCliente AND " +
            "        :snClienteUnico = 'S')")
    double getTotalSaldo(int idSucursal, int idCliente, String snClienteUnico) throws Exception;

    @Transaction
    @Query(value = "SELECT documentos_cuenta.* " +
            "FROM   documentos_cuenta " +
            "       INNER JOIN condvta " +
            "           ON condvta.id_condvta = documentos_cuenta.id_condvta " +
            "WHERE  ((documentos_cuenta.id_succliente = :idSucursal AND " +
            "        documentos_cuenta.id_cliente = :idCliente AND " +
            "        :snClienteUnico <> 'S') OR " +
            "        (documentos_cuenta.id_cliente = :idCliente AND " +
            "        :snClienteUnico = 'S')) AND " +
            "       ((documentos_cuenta.pr_nc + documentos_cuenta.pr_pagado) < " +
            "           documentos_cuenta.pr_cuota) AND " +
            "       (documentos_cuenta.fe_venta < DATE( julianday(DATE('now')) - condvta.nu_diasatraso)) AND " +
            "       documentos_cuenta.ti_aplica_cc > 0 AND " +
            "       condvta.sn_controlfiado = 'S'" +
            "ORDER BY documentos_cuenta.fe_venta")
    double getTotalSaldoVencido(int idSucursal, int idCliente, String snClienteUnico) throws Exception;

    @Query(value = "SELECT ((IFNULL(MAX(documentos_cuenta.id_numdoc),0)) + 1) AS 'numero' " +
            "FROM   documentos_cuenta " +
            "WHERE  documentos_cuenta.id_fcnc = :docAnticipo")
        // documentos_cuenta.id_fcnc = 'AN'
    int getNumeroAdelanto(String docAnticipo) throws Exception;

}
