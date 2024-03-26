package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.ar.vgmsistemas.database.dao.entity.VentaBd;
import com.ar.vgmsistemas.database.dao.entity.VentaDetalleBd;

import java.util.List;

@Dao
public interface IVentaDao {
    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    void create(VentaBd... venta) throws Exception;

    @Query(value = "SELECT ventas.* " +
            "FROM   ventas " +
            "       INNER JOIN comercio " +
            "           ON  comercio.id_sucursal = ventas.id_sucursal AND " +
            "               comercio.id_cliente = ventas.id_cliente AND " +
            "               comercio.id_comercio = ventas.id_comercio " +
            "       INNER JOIN documentos " +
            "           ON  documentos.id_doc = ventas.id_fcnc AND " +
            "               documentos.id_letra = ventas.id_tipoab AND " +
            "               documentos.id_ptovta = ventas.id_ptovta " +
            "WHERE  documentos.sn_generar = 'S' AND " +
            "       ventas.id_hoja_integrado IS NOT NULL AND " +
            "       ventas.id_repartidor = :idRepartidor")
    List<VentaBd> recoveryByRepartidor(int idRepartidor) throws Exception;

    @Query(value = "SELECT ventas.* " +
            "FROM   ventas " +
            "WHERE  ventas.id_fcfcnc = :idDocumento AND " +
            "       ventas.id_fctipoab = :idLetra AND " +
            "       ventas.id_fcptovta = :puntoVenta AND " +
            "       ventas.id_fcnumdoc = :idNumeroDocumento")
    VentaBd getCredito(String idDocumento, String idLetra, int puntoVenta, long idNumeroDocumento) throws Exception;

    @Query(value = "SELECT ventas.* " +
            "FROM   ventas " +
            "       INNER JOIN comercio " +
            "           ON  comercio.id_sucursal = ventas.id_sucursal AND " +
            "               comercio.id_cliente = ventas.id_cliente AND " +
            "               comercio.id_comercio = ventas.id_comercio " +
            "       INNER JOIN documentos " +
            "           ON  documentos.id_doc = ventas.id_fcnc AND " +
            "               documentos.id_letra = ventas.id_tipoab AND " +
            "               documentos.id_ptovta = ventas.id_ptovta " +
            "       INNER JOIN movimientos " +
            "           ON movimientos.id_movil = ventas.id_movil_venta " +
            "WHERE  (:estado NOT IN(0,3) AND ventas.sn_anulo = 'N') OR " +
            "       ((:tipoDocumento = 'FC' AND documentos.sn_movil IS NOT NULL) OR " +
            "        (:tipoDocumento <> 'FC'AND documentos.sn_movil = 'S') ) OR " +
            "       (:estado = 2 AND movimientos.fe_sincronizacion IS NULL AND " +
            "          movimientos.fe_anulacion IS NULL ) OR " +
            "       (:estado = 4 AND movimientos.fe_sincronizacion IS NULL) OR " +
            "       (:estado = 3 AND movimientos.fe_sincronizacion IS NULL AND " +
            "          movimientos.fe_anulacion IS NULL)")
    List<VentaBd> recoveryByDocumento(String tipoDocumento, int estado) throws Exception;

    @Query(value = "SELECT ventas.* " +
            "FROM   ventas " +
            "       LEFT JOIN comercio " +
            "           ON  comercio.id_sucursal = ventas.id_sucursal AND " +
            "               comercio.id_cliente = ventas.id_cliente AND " +
            "               comercio.id_comercio = ventas.id_comercio " +
            "       INNER JOIN documentos " +
            "           ON  documentos.id_doc = ventas.id_fcnc AND " +
            "               documentos.id_letra = ventas.id_tipoab AND " +
            "               documentos.id_ptovta = ventas.id_ptovta " +
            "WHERE  ventas.id_vendedor = :idVendedor AND " +
            "       documentos.id_funcion <> 3 AND " +
            "       documentos.sn_estadistica = 'S' OR " +
            "       ventas.fe_venta BETWEEN :fechaDesde AND :fechaHasta " +
            "ORDER BY ventas.fe_venta ASC")
    List<VentaBd> recoveryVentas(String fechaDesde, String fechaHasta, int idVendedor) throws Exception;

    @Query(value = "SELECT 	ventas.id_fcnc,ventas.id_tipoab,ventas.id_ptovta,ventas.id_numdoc, " +
            "	ventas.id_condvta,ventas.id_sucursal,ventas.id_cliente,ventas.id_comercio, " +
            "	ventas.pr_subtotal,ventas.pr_dgr,ventas.pr_ivai,ventas.pr_iva2,ventas.pr_total, " +
            "	ventas.ta_dto,ventas.ta_dgr,ventas.fe_registro,ventas.pr_exento,ventas.id_vendedor, " +
            "	ventas.pr_bonificacion,ventas.ta_dirsc,ventas.pr_dirsc,ventas.sn_anulo, " +
            "	(CASE 	WHEN movimientos.id_movimiento IS NULL  " +
            "			THEN ventas.fe_venta " +
            "			ELSE movimientos.fe_sincronizacion " +
            "	 END) AS fe_sincronizacion, " +
            "	ventas.id_movil_venta,ventas.fe_venta,ventas.id_repartidor,ventas.de_pie,ventas.codigo_autorizacion, " +
            "	ventas.pr_tot_imp_interno,ventas.fe_entrega,ventas.pr_ivani,ventas.ta_dto_cvta,ventas.pr_subart, " +
            "	ventas.pr_bonif_cvta,ventas.sn_generado,ventas.id_pedido_ptovta,ventas.id_pedido_doc,ventas.id_pedido_tipoab, " +
            "	ventas.id_pedido_num,ventas.id_hoja_integrado,ventas.de_cliente,ventas.ti_turno,ventas.id_numdoc_fiscal, " +
            "	ventas.cd_autorizacion_acc,ventas.id_motivo_autoriza,ventas.id_fcfcnc,ventas.id_fctipoab, " +
            "	ventas.id_fcptovta,ventas.id_fcnumdoc,ventas.id_motivo_autoriza_pedr,ventas.sn_max_accom_superado, " +
            "	ventas.id_motivo_rechazo_nc,ventas.ti_nc " +
            "FROM   ventas " +
            "       LEFT JOIN comercio " +
            "           ON  comercio.id_sucursal = ventas.id_sucursal AND " +
            "               comercio.id_cliente = ventas.id_cliente AND " +
            "               comercio.id_comercio = ventas.id_comercio " +
            "       INNER JOIN documentos " +
            "           ON  documentos.id_doc = ventas.id_fcnc AND " +
            "               documentos.id_letra = ventas.id_tipoab AND " +
            "               documentos.id_ptovta = ventas.id_ptovta " +
            "       LEFT JOIN movimientos " +
            "           ON movimientos.id_movil = ventas.id_movil_venta " +
            "WHERE  ventas.id_vendedor = :idVendedor AND " +
            "       documentos.id_funcion <> 3 AND " +
            "       documentos.sn_estadistica <> 'S' OR " +
            "       movimientos.de_tabla ='ventas' AND " +
            "       ventas.fe_venta BETWEEN :fechaDesde AND :fechaHasta " +
            "ORDER BY ventas.fe_venta ASC")
    List<VentaBd> recoveryByPeriodo(String fechaDesde, String fechaHasta, int idVendedor) throws Exception;

    @Query(value = "SELECT ventas.* " +
            "FROM   ventas " +
            "       LEFT JOIN comercio " +
            "           ON  comercio.id_sucursal = ventas.id_sucursal AND " +
            "               comercio.id_cliente = ventas.id_cliente AND " +
            "               comercio.id_comercio = ventas.id_comercio " +
            "       INNER JOIN documentos " +
            "           ON  documentos.id_doc = ventas.id_fcnc AND " +
            "               documentos.id_letra = ventas.id_tipoab AND " +
            "               documentos.id_ptovta = ventas.id_ptovta " +
            "WHERE  ventas.id_sucursal = :idSucursal AND " +
            "       ventas.id_cliente = :idCliente AND " +
            "       ventas.id_comercio = :idComercio AND " +
            "       (:tipoDocumento = 'FC' AND documentos.sn_estadistica = 'S' AND " +
            "         documentos.sn_generar = 'N')  " +
            "UNION " +
            "SELECT ventas.* " +
            "FROM   ventas " +
            "       LEFT JOIN comercio " +
            "           ON  comercio.id_sucursal = ventas.id_sucursal AND " +
            "               comercio.id_cliente = ventas.id_cliente AND " +
            "               comercio.id_comercio = ventas.id_comercio " +
            "       INNER JOIN documentos " +
            "           ON  documentos.id_doc = ventas.id_fcnc AND " +
            "               documentos.id_letra = ventas.id_tipoab AND " +
            "               documentos.id_ptovta = ventas.id_ptovta " +
            "       INNER JOIN movimientos " +
            "           ON movimientos.id_movil = ventas.id_movil_venta " +
            "WHERE  ventas.id_sucursal = :idSucursal AND " +
            "       ventas.id_cliente = :idCliente AND " +
            "       ventas.id_comercio = :idComercio AND " +
            "       (:tipoDocumento <> 'FC' AND documentos.sn_movil = 'S' )" +
            "ORDER BY ventas.fe_venta ASC")
    List<VentaBd> recoveryByCliente(int idSucursal, int idCliente, int idComercio, String tipoDocumento) throws Exception;

    @Query(value = "SELECT ventas.* " +
            "FROM   ventas " +
            "       LEFT JOIN comercio " +
            "           ON  comercio.id_sucursal = ventas.id_sucursal AND " +
            "               comercio.id_cliente = ventas.id_cliente AND " +
            "               comercio.id_comercio = ventas.id_comercio " +
            "       INNER JOIN condvta " +
            "           ON  condvta.id_condvta = ventas.id_condvta " +
            "WHERE  ventas.id_fcnc = :idDocumento AND " +
            "       ventas.id_tipoab = :idLetra AND " +
            "       ventas.id_ptovta = :puntoVenta AND " +
            "       ventas.id_numdoc = :idNumeroDocumento")
    VentaBd recoveryByID(String idDocumento, String idLetra, int puntoVenta, long idNumeroDocumento) throws Exception;

    @Query(value = "UPDATE ventas " +
            "SET    sn_anulo = 'S', sn_generado = 'A' " +
            "WHERE  ventas.id_fcnc = :idDocumento AND " +
            "       ventas.id_tipoab = :idLetra AND " +
            "       ventas.id_ptovta = :puntoVenta AND " +
            "       ventas.id_numdoc = :idNumeroDocumento")
    void delete(String idDocumento, String idLetra, int puntoVenta, long idNumeroDocumento) throws Exception;

    @Query(value = "UPDATE ventas " +
            "SET    id_condvta = :idCondVta, pr_subtotal = :prSubtotal, " +
            "       pr_dgr = :prTotalRenta, pr_ivai = :prIva21, pr_iva2 = :prIva105 ," +
            "       pr_total = :prTotal, ta_dto = :taDto, pr_bonificacion = :prBonificacion," +
            "       ta_dto_cvta = :taDtoCondVta, pr_bonif_cvta = :prBonificacionCondVta," +
            "       ta_dgr = :taDGR, fe_registro = :feRegistro, pr_exento = :prTotalExento," +
            "       ta_dirsc = :taDirsc, pr_dirsc = :prTotalDirsc," +
            "       pr_tot_imp_interno = :prTotalImpuestoInterno, " +
            "       pr_ivani = :prTotalIvaNoCategorizado, pr_subart = :prTotalPorArticulo, " +
            "       fe_venta = :feVenta " +
            "WHERE  ventas.id_movil_venta = :idMovil ")
    void updateByIdMovil(String idMovil, String idCondVta, double prSubtotal, double prTotalRenta,
                         double prIva21, double prIva105, double prTotal, double taDto, double prBonificacion,
                         double taDtoCondVta, double prBonificacionCondVta, double taDGR, String feRegistro,
                         double prTotalExento, double taDirsc, double prTotalDirsc, double prTotalImpuestoInterno,
                         double prTotalIvaNoCategorizado, double prTotalPorArticulo, String feVenta) throws Exception;

    /*Se deberia aplicar para los siguientes metodos
    void updateByIdMovil(Venta venta) throws Exception;
    void update(Venta venta) throws Exception;
    void updateFechaSincronizacion(Venta venta) throws Exception;
    * */
    @Update(onConflict = OnConflictStrategy.ROLLBACK)
    void update(VentaBd venta) throws Exception;

    @Query(value = "SELECT COUNT(*) " +
            "FROM   ( SELECT ventas.id_fcnc " +
            "         FROM  ventas " +
            "               INNER JOIN documentos " +
            "                   ON  documentos.id_doc = ventas.id_fcnc AND " +
            "                       documentos.id_letra = ventas.id_tipoab AND " +
            "                       documentos.id_ptovta = ventas.id_ptovta " +
            "         WHERE ventas.sn_anulo = 'N' AND " +
            "               documentos.sn_movil = 'S' " +
            "         UNION " +
            "       SELECT ventas.id_fcnc " +
            "         FROM  ventas " +
            "               INNER JOIN documentos " +
            "                   ON  documentos.id_doc = ventas.id_fcnc AND " +
            "                       documentos.id_letra = ventas.id_tipoab AND " +
            "                       documentos.id_ptovta = ventas.id_ptovta " +
            "               INNER JOIN movimientos " +
            "                   ON  movimientos.id_movil = ventas.id_movil_venta" +
            "         WHERE movimientos.fe_sincronizacion IS NULL AND " +
            "               ventas.fe_entrega > CURRENT_DATE AND" +
            "               :snPosterior = 'S')")
    int getCantidadVentasNoEnviadas(String snPosterior) throws Exception;

    @Query(value = "SELECT COUNT(*) " +
            "FROM   ventas " +
            "       LEFT JOIN comercio " +
            "           ON  comercio.id_sucursal = ventas.id_sucursal AND " +
            "               comercio.id_cliente = ventas.id_cliente AND " +
            "               comercio.id_comercio = ventas.id_comercio " +
            "       INNER JOIN documentos " +
            "           ON  documentos.id_doc = ventas.id_fcnc AND " +
            "               documentos.id_letra = ventas.id_tipoab AND " +
            "               documentos.id_ptovta = ventas.id_ptovta " +
            "       INNER JOIN movimientos " +
            "           ON movimientos.id_movil = ventas.id_movil_venta " +
            "WHERE  ventas.sn_anulo = 'N' AND " +
            "       documentos.sn_movil = 'S' AND " +
            "       ventas.id_sucursal = :idSucursal AND " +
            "       ventas.id_cliente = :idCliente AND " +
            "       ventas.id_comercio = :idComercio ")
    int getCantidadVentas(int idSucursal, int idCliente, int idComercio) throws Exception;

    @Query(value = "SELECT COUNT(*)" +
            "FROM   ventas " +
            "       INNER JOIN movimientos " +
            "           ON movimientos.id_movil = ventas.id_movil_venta " +
            "WHERE  ventas.sn_anulo = 'N' AND " +
            "       movimientos.fe_sincronizacion  IS NULL AND" +
            "       movimientos.id_movil = :idMovil ")
    int isEnviado(String idMovil) throws Exception;//0:se envió; 1:no se envió

    @Query(value = "SELECT COUNT(*)" +
            "FROM   ventas " +
            "       INNER JOIN movimientos " +
            "           ON movimientos.id_movil = ventas.id_movil_venta " +
            "WHERE  ventas.sn_generado = 'S' AND " +
            "       movimientos.id_movil = :idMovil ")
    int isGenerado(String idMovil) throws Exception;//0:no se generó, 1:generado

    @Delete
    void delete(VentaBd venta) throws Exception;

    @Query(value = "SELECT COUNT(*)" +
            "FROM   ventas " +
            "       INNER JOIN movimientos " +
            "           ON movimientos.id_movil = ventas.id_movil_venta " +
            "WHERE  ventas.fe_entrega > CURRENT_DATE AND " +
            "       ventas.fe_sincronizacion IS NULL AND " +
            "       movimientos.fe_anulacion IS NULL")
    int getCantidadVentasPosteriores() throws Exception;

    @Transaction
    @Query(value = "UPDATE ventas " +
            "SET    sn_generado = 'S' " +
            "WHERE  ventas.id_fcnc = :idDocumento AND " +
            "       ventas.id_tipoab = :idLetra AND " +
            "       ventas.id_ptovta = :puntoVenta AND " +
            "       ventas.id_numdoc = :idNumeroDocumento")
    void setGenerado(String idDocumento, String idLetra, int puntoVenta, long idNumeroDocumento) throws Exception;
}
