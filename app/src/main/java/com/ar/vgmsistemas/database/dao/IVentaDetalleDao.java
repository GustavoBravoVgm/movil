package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.LineaIntegradoMercaderiaBd;
import com.ar.vgmsistemas.database.dao.entity.VentaDetalleBd;

import java.util.List;

@Dao
public interface IVentaDetalleDao {
    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    void create(VentaDetalleBd ventaDetalle) throws Exception;

    @Query(value = "SELECT ventas_detalle.* " +
            "FROM   ventas_detalle " +
            "       INNER JOIN articulos " +
            "           ON  articulos.id_articulos = ventas_detalle.id_articulos " +
            "       INNER JOIN listasPrecios " +
            "           ON  listasPrecios.id_lista = ventas_detalle.id_lista " +
            "       INNER JOIN proveedores " +
            "           ON  proveedores.id_proveedor = articulos.id_proveedor " +
            "       INNER JOIN listasPreciosDetalle " +
            "           ON  listasPreciosDetalle.id_lista = listasPrecios.id_lista AND " +
            "               listasPreciosDetalle.id_articulos = ventas_detalle.id_articulos " +
            "WHERE  ((listasPreciosDetalle.ca_articulo_desde = 0 AND " +
            "        listasPreciosDetalle.ca_articulo_hasta = 0 AND" +
            "        listasPrecios.ti_lista <> 9 ) OR " +
            "       (listasPreciosDetalle.ca_articulo_desde <= ventas_detalle.ca_articulos AND " +
            "        listasPreciosDetalle.ca_articulo_hasta >= ventas_detalle.ca_articulos AND " +
            "        listasPrecios.ti_lista = 9 )) AND " +
            "       ventas_detalle.id_fcnc = :idDocumento AND " +
            "       ventas_detalle.id_tipoab = :idLetra AND " +
            "       ventas_detalle.id_ptovta = :puntoVenta AND " +
            "       ventas_detalle.id_numdoc = :idNumeroDocumento")
    List<VentaDetalleBd> recoveryByVenta(String idDocumento, String idLetra, int puntoVenta,
                                         long idNumeroDocumento) throws Exception;

    @Query(value = "UPDATE ventas_detalle " +
            "SET    sn_anulo = 'S' " +
            "WHERE  ventas_detalle.id_fcnc = :idDocumento AND " +
            "       ventas_detalle.id_tipoab = :idLetra AND " +
            "       ventas_detalle.id_ptovta = :puntoVenta AND " +
            "       ventas_detalle.id_numdoc = :idNumeroDocumento AND " +
            "       ventas_detalle.id_secuencia = :idSecuencia")
    void delete(String idDocumento, String idLetra, int puntoVenta, long idNumeroDocumento, int idSecuencia) throws Exception;

    @Query(value = "UPDATE ventas_detalle " +
            "SET    sn_anulo = 'S' " +
            "WHERE  ventas_detalle.id_fcnc = :idDocumento AND " +
            "       ventas_detalle.id_tipoab = :idLetra AND " +
            "       ventas_detalle.id_ptovta = :puntoVenta AND " +
            "       ventas_detalle.id_numdoc = :idNumeroDocumento")
    void anularVentasDetalleByVenta(String idDocumento, String idLetra, int puntoVenta, long idNumeroDocumento) throws Exception;

    @Query(value = "DELETE FROM ventas_detalle " +
            "WHERE  ventas_detalle.id_fcnc = :idDocumento AND " +
            "       ventas_detalle.id_tipoab = :idLetra AND " +
            "       ventas_detalle.id_ptovta = :puntoVenta AND " +
            "       ventas_detalle.id_numdoc = :idNumeroDocumento")
    void deleteVentasDetalleByVenta(String idDocumento, String idLetra, int puntoVenta, long idNumeroDocumento) throws Exception;

    @Query(value = "UPDATE ventas_detalle " +
            "SET    ca_unidades_devueltas = :caUnidadesDevuelta, ca_bulto_devuelto = :caBultoDevuelto " +
            "WHERE  ventas_detalle.id_fcnc = :idDocumento AND " +
            "       ventas_detalle.id_tipoab = :idLetra AND " +
            "       ventas_detalle.id_ptovta = :puntoVenta AND " +
            "       ventas_detalle.id_numdoc = :idNumeroDocumento AND " +
            "       ventas_detalle.id_secuencia = :idSecuencia")
    void updateXDevolucion(String idDocumento, String idLetra, int puntoVenta, long idNumeroDocumento,
                           int idSecuencia, double caUnidadesDevuelta, double caBultoDevuelto) throws Exception;

    @Query(value = "SELECT articulos.*, " +
            "       ventas_detalle.id_articulos AS idArticulo, " +
            "       SUM(ventas_detalle.ca_unidades) AS cantidad, " +
            "       NULL AS idHoja, " +
            "       NULL AS tiEstado, " +
            "       NULL AS prPagado, " +
            "       NULL AS idMovil " +
            "FROM   ventas_detalle " +
            "       INNER JOIN ventas " +
            "           ON  ventas.id_fcnc = ventas_detalle.id_fcnc AND " +
            "               ventas.id_tipoab = ventas_detalle.id_tipoab AND " +
            "               ventas.id_ptovta = ventas_detalle.id_ptovta AND " +
            "               ventas.id_numdoc = ventas_detalle.id_numdoc " +
            "       INNER JOIN articulos " +
            "           ON articulos.id_articulos = ventas_detalle.id_articulos " +
            "WHERE  ventas_detalle.id_fcnc <> :idTipoDocumentoPorDefecto AND " +
            "       ventas.sn_anulo <> 'S' AND " +
            "       ventas.sn_generado <> 'S' AND " +
            "       :tipoEmpresa = 2 " +
            "GROUP BY ventas_detalle.id_articulos " +
            "UNION " +
            "SELECT articulos.*, " +
            "       ventas_detalle.id_articulos AS idArticulo, " +
            "       SUM(ventas_detalle.ca_articulos) AS cantidad, " +
            "       hoja_detalle.id_hoja AS idHoja, " +
            "       hoja_detalle.ti_estado AS tiEstado, " +
            "       hoja_detalle.pr_pagado AS prPagado, " +
            "       hoja_detalle.id_movil AS idMovil " +
            "FROM   ventas_detalle " +
            "       INNER JOIN articulos " +
            "           ON  articulos.id_articulos = ventas_detalle.id_articulos " +
            "       INNER JOIN ventas " +
            "           ON  ventas.id_fcnc = ventas_detalle.id_fcnc AND " +
            "               ventas.id_tipoab = ventas_detalle.id_tipoab AND " +
            "               ventas.id_ptovta = ventas_detalle.id_ptovta AND " +
            "               ventas.id_numdoc = ventas_detalle.id_numdoc " +
            "       INNER JOIN hoja_detalle " +
            "           ON  hoja_detalle.id_fcnc = ventas.id_fcnc AND " +
            "               hoja_detalle.id_tipoab = ventas.id_tipoab AND " +
            "               hoja_detalle.id_ptovta = ventas.id_ptovta AND " +
            "               hoja_detalle.id_numdoc = ventas.id_numdoc " +
            "       INNER JOIN hoja " +
            "           ON  hoja.id_sucursal = hoja_detalle.id_sucursal AND " +
            "               hoja.id_hoja = hoja_detalle.id_hoja " +
            "WHERE  ventas_detalle.ca_unidades_devueltas IS NULL AND " +
            "       ventas_detalle.id_articulos IS NOT NULL AND " +
            "       :tipoEmpresa <> 2 " +
            "GROUP BY   ventas_detalle.id_articulos," +
            "           hoja_detalle.id_hoja," +
            "           hoja_detalle.ti_estado," +
            "           hoja_detalle.pr_pagado," +
            "           hoja_detalle.id_movil " +
            "UNION " +
            "SELECT articulos.*, " +
            "       ventas_detalle.id_articulos AS idArticulo, " +
            "       SUM(ventas_detalle.ca_articulos - ventas_detalle.ca_unidades_devueltas) AS cantidad, " +
            "       hoja_detalle.id_hoja AS idHoja, " +
            "       hoja_detalle.ti_estado AS tiEstado, " +
            "       hoja_detalle.pr_pagado AS prPagado, " +
            "       hoja_detalle.id_movil AS idMovil " +
            "FROM   ventas_detalle " +
            "       INNER JOIN articulos " +
            "           ON  articulos.id_articulos = ventas_detalle.id_articulos " +
            "       INNER JOIN ventas " +
            "           ON  ventas.id_fcnc = ventas_detalle.id_fcnc AND " +
            "               ventas.id_tipoab = ventas_detalle.id_tipoab AND " +
            "               ventas.id_ptovta = ventas_detalle.id_ptovta AND " +
            "               ventas.id_numdoc = ventas_detalle.id_numdoc " +
            "       INNER JOIN hoja_detalle " +
            "           ON  hoja_detalle.id_fcnc = ventas.id_fcnc AND " +
            "               hoja_detalle.id_tipoab = ventas.id_tipoab AND " +
            "               hoja_detalle.id_ptovta = ventas.id_ptovta AND " +
            "               hoja_detalle.id_numdoc = ventas.id_numdoc " +
            "       INNER JOIN hoja " +
            "           ON  hoja.id_sucursal = hoja_detalle.id_sucursal AND " +
            "               hoja.id_hoja = hoja_detalle.id_hoja " +
            "WHERE  ventas_detalle.ca_unidades_devueltas IS NOT NULL AND " +
            "       ventas_detalle.id_articulos IS NOT NULL AND " +
            "       :tipoEmpresa <> 2 " +
            "GROUP BY   ventas_detalle.id_articulos," +
            "           hoja_detalle.id_hoja," +
            "           hoja_detalle.ti_estado," +
            "           hoja_detalle.pr_pagado," +
            "           hoja_detalle.id_movil " +
            "UNION " +
            "SELECT articulos.*, " +
            "       ventas_detalle.id_articulos AS idArticulo, " +
            "       SUM(ventas_detalle.ca_unidades_devueltas) AS cantidad, " +
            "       hoja_detalle.id_hoja AS idHoja, " +
            "       hoja_detalle.ti_estado AS tiEstado, " +
            "       hoja_detalle.pr_pagado AS prPagado, " +
            "       hoja_detalle.id_movil AS idMovil " +
            "FROM   ventas_detalle " +
            "       INNER JOIN articulos " +
            "           ON  articulos.id_articulos = ventas_detalle.id_articulos " +
            "       INNER JOIN ventas " +
            "           ON  ventas.id_fcnc = ventas_detalle.id_fcnc AND " +
            "               ventas.id_tipoab = ventas_detalle.id_tipoab AND " +
            "               ventas.id_ptovta = ventas_detalle.id_ptovta AND " +
            "               ventas.id_numdoc = ventas_detalle.id_numdoc " +
            "       INNER JOIN hoja_detalle " +
            "           ON  hoja_detalle.id_fcnc = ventas.id_fcnc AND " +
            "               hoja_detalle.id_tipoab = ventas.id_tipoab AND " +
            "               hoja_detalle.id_ptovta = ventas.id_ptovta AND " +
            "               hoja_detalle.id_numdoc = ventas.id_numdoc " +
            "       INNER JOIN hoja " +
            "           ON  hoja.id_sucursal = hoja_detalle.id_sucursal AND " +
            "               hoja.id_hoja = hoja_detalle.id_hoja " +
            "WHERE  ventas_detalle.ca_unidades_devueltas > 0 AND " +
            "       ventas_detalle.id_articulos IS NOT NULL AND " +
            "       :tipoEmpresa <> 2 " +
            "GROUP BY   ventas_detalle.id_articulos," +
            "           hoja_detalle.id_hoja," +
            "           hoja_detalle.ti_estado," +
            "           hoja_detalle.pr_pagado," +
            "           hoja_detalle.id_movil ")
    List<LineaIntegradoMercaderiaBd> getLineasIntegrado(int tipoEmpresa, String idTipoDocumentoPorDefecto) throws Exception;

    @Query(value = "SELECT ventas_detalle.* " +
            "FROM   ventas_detalle " +
            "       INNER JOIN articulos " +
            "           ON  articulos.id_articulos = ventas_detalle.id_articulos " +
            "WHERE  ventas_detalle.id_promo = :idArticulos AND " +
            "       ventas_detalle.id_fcnc = :idDocumento AND " +
            "       ventas_detalle.id_tipoab = :idLetra AND " +
            "       ventas_detalle.id_ptovta = :puntoVenta AND " +
            "       ventas_detalle.id_numdoc = :idNumeroDocumento")
    List<VentaDetalleBd> recoveryArtComponentesCombo(int idArticulos, String idDocumento,
                                                     String idLetra, int puntoVenta,
                                                     long idNumeroDocumento) throws Exception;

    @Query(value = "SELECT (IFNULL(ventas_detalle.id_secuencia,0) + 1) AS 'id_secuencia' " +
            "FROM   ventas_detalle " +
            "WHERE  ventas_detalle.id_fcnc = :idDocumento AND " +
            "       ventas_detalle.id_tipoab = :idLetra AND " +
            "       ventas_detalle.id_ptovta = :puntoVenta AND " +
            "       ventas_detalle.id_numdoc = :idNumeroDocumento")
    long recoveryIdSecuencia(String idDocumento, String idLetra, int puntoVenta,
                             long idNumeroDocumento) throws Exception;
}
