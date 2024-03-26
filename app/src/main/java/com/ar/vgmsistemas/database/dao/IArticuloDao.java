package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.ar.vgmsistemas.database.dao.entity.ArticuloBd;
import com.ar.vgmsistemas.database.dao.entity.result.ArticuloResultBd;

import java.util.List;

@Dao
public interface IArticuloDao {
    @Transaction
    @Query(value = "SELECT  articulos.*, de_negocio, de_segmento, de_subrubro, de_linea, " +
            "               de_proveedor, id_plancta, id_sucursal, ti_proveedor, nu_cuit,  " +
            "               stock.stock, 0 as cantidad_vendida, null as fecha_ultima_venta " +
            "FROM   articulos " +
            "       INNER JOIN subrubro " +
            "           ON  articulos.id_negocio = subrubro.id_negocio AND " +
            "               articulos.id_segmento = subrubro.id_segmento AND " +
            "               articulos.id_subrubro = subrubro.id_subrubro " +
            "       INNER JOIN segmento " +
            "           ON  subrubro.id_negocio = segmento.id_negocio AND " +
            "               subrubro.id_segmento = segmento.id_segmento " +
            "       INNER JOIN linea " +
            "           ON  linea.id_linea = articulos.id_linea " +
            "       INNER JOIN negocio " +
            "           ON subrubro.id_negocio = negocio.id_negocio " +
            "       INNER JOIN stock " +
            "           ON stock.id_articulos = articulos.id_articulos " +
            "       INNER JOIN proveedores " +
            "           ON proveedores.id_proveedor = articulos.id_proveedor ")
    List<ArticuloResultBd> recoveryAll() throws Exception;


    @Transaction
    @Query(value = "SELECT  articulos.*, de_negocio, de_segmento, de_subrubro, de_linea, " +
            "               de_proveedor, id_plancta, id_sucursal, ti_proveedor, nu_cuit,  " +
            "               stock.stock, 0 as cantidad_vendida, null as fecha_ultima_venta " +
            "FROM   articulos " +
            "       INNER JOIN subrubro " +
            "           ON  articulos.id_negocio = subrubro.id_negocio AND " +
            "               articulos.id_segmento = subrubro.id_segmento AND " +
            "               articulos.id_subrubro = subrubro.id_subrubro " +
            "       INNER JOIN segmento " +
            "           ON  subrubro.id_negocio = segmento.id_negocio AND " +
            "               subrubro.id_segmento = segmento.id_segmento " +
            "       INNER JOIN linea " +
            "           ON  linea.id_linea = articulos.id_linea " +
            "       INNER JOIN negocio " +
            "           ON subrubro.id_negocio = negocio.id_negocio " +
            "       INNER JOIN stock " +
            "           ON stock.id_articulos = articulos.id_articulos " +
            "       INNER JOIN proveedores " +
            "           ON proveedores.id_proveedor = articulos.id_proveedor " +
            "       INNER JOIN listasPreciosDetalle " +
            "            ON articulos.id_articulos = listasPreciosDetalle.id_articulos " +
            "WHERE  listasPreciosDetalle.id_lista = :idListaPrecio ")
    List<ArticuloResultBd> recoveryAll(int idListaPrecio) throws Exception;

    @Transaction
    @Query(value = "SELECT  articulos.*, de_negocio, de_segmento, de_subrubro, de_linea, " +
            "               de_proveedor, id_plancta, proveedores.id_sucursal as id_sucursal , ti_proveedor, nu_cuit,  " +
            "               stock.stock, " +
            "               SUM(ventas_detalle.ca_articulos * documentos.ti_aplica_stock * -1) as cantidad_vendida, " +
            "               MAX(fe_venta) as fecha_ultima_venta " +
            "FROM   documentos " +
            "       INNER JOIN ventas " +
            "            ON documentos.id_doc = ventas.id_fcnc AND " +
            "               documentos.id_letra = ventas.id_tipoab AND " +
            "               documentos.id_ptovta = ventas.id_ptovta " +
            "       INNER JOIN ventas_detalle " +
            "            ON ventas.id_fcnc = ventas_detalle.id_fcnc AND " +
            "               ventas.id_tipoab = ventas_detalle.id_tipoab AND " +
            "               ventas.id_ptovta = ventas_detalle.id_ptovta AND " +
            "               ventas.id_numdoc = ventas_detalle.id_numdoc " +
            "       INNER JOIN articulos " +
            "            ON ventas_detalle.id_articulos = articulos.id_articulos " +
            "       INNER JOIN subrubro " +
            "           ON  articulos.id_negocio = subrubro.id_negocio AND " +
            "               articulos.id_segmento = subrubro.id_segmento AND " +
            "               articulos.id_subrubro = subrubro.id_subrubro " +
            "       INNER JOIN segmento " +
            "           ON  subrubro.id_negocio = segmento.id_negocio AND " +
            "               subrubro.id_segmento = segmento.id_segmento " +
            "       INNER JOIN linea " +
            "           ON  linea.id_linea = articulos.id_linea " +
            "       INNER JOIN negocio " +
            "           ON subrubro.id_negocio = negocio.id_negocio " +
            "       INNER JOIN stock " +
            "           ON stock.id_articulos = articulos.id_articulos " +
            "       INNER JOIN proveedores " +
            "           ON proveedores.id_proveedor = articulos.id_proveedor " +
            "WHERE  (:idSucursal = 0 and :idCliente = 0 and :idComercio = 0) OR " + /*cuando es null el valor de la pkcliente*/
            "       (ventas.id_sucursal = :idSucursal AND " +
            "       ventas.id_cliente = :idCliente AND " +
            "       ventas.id_comercio = :idComercio) " +
            "GROUP BY (ventas_detalle.id_articulos) " +
            "ORDER BY ventas.fe_venta ")
    List<ArticuloResultBd> recoveryCantidadesAcumuladasPorCliente(int idSucursal, int idCliente, int idComercio) throws Exception;


    @Transaction
    @Query(value = "SELECT  articulos.*, de_negocio, de_segmento, de_subrubro, de_linea, " +
            "               de_proveedor, id_plancta, id_sucursal, ti_proveedor, nu_cuit,  " +
            "               stock.stock, 0 as cantidad_vendida, " +
            "               null as fecha_ultima_venta " +
            "FROM   articulos " +
            "       INNER JOIN subrubro " +
            "           ON  articulos.id_negocio = subrubro.id_negocio AND " +
            "               articulos.id_segmento = subrubro.id_segmento AND " +
            "               articulos.id_subrubro = subrubro.id_subrubro " +
            "       INNER JOIN segmento " +
            "           ON  subrubro.id_negocio = segmento.id_negocio AND " +
            "               subrubro.id_segmento = segmento.id_segmento " +
            "       INNER JOIN negocio " +
            "           ON subrubro.id_negocio = negocio.id_negocio " +
            "       INNER JOIN linea " +
            "           ON  linea.id_linea = articulos.id_linea " +
            "       INNER JOIN stock " +
            "           ON stock.id_articulos = articulos.id_articulos " +
            "       INNER JOIN proveedores " +
            "           ON proveedores.id_proveedor = articulos.id_proveedor " +
            "WHERE  articulos.id_articulos = :id ")
    ArticuloResultBd recoveryByID(int id) throws Exception;

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END as exist " +
            "FROM   articulos " +
            "WHERE  articulos.id_articulos = :idArticulo AND " +
            "       articulos.id_subrubro = :idSubrubro ")
    int isArticuloInSubrubro(int idArticulo, int idSubrubro) throws Exception;

    @Transaction
    @Query(value = "SELECT  articulos.*, de_negocio, de_segmento, de_subrubro, de_linea, " +
            "               de_proveedor, id_plancta, proveedores.id_sucursal as id_sucursal, ti_proveedor, nu_cuit,  " +
            "               stock.stock, " +
            "               SUM(ventas_detalle.ca_articulos * documentos.ti_aplica_stock * -1) as cantidad_vendida, " +
            "               MAX(fe_venta) as fecha_ultima_venta " +
            "FROM   documentos " +
            "       INNER JOIN ventas " +
            "            ON documentos.id_doc = ventas.id_fcnc AND " +
            "               documentos.id_letra = ventas.id_tipoab AND " +
            "               documentos.id_ptovta = ventas.id_ptovta " +
            "       INNER JOIN ventas_detalle " +
            "            ON ventas.id_fcnc = ventas_detalle.id_fcnc AND " +
            "               ventas.id_tipoab = ventas_detalle.id_tipoab AND " +
            "               ventas.id_ptovta = ventas_detalle.id_ptovta AND " +
            "               ventas.id_numdoc = ventas_detalle.id_numdoc " +
            "       INNER JOIN articulos " +
            "            ON ventas_detalle.id_articulos = articulos.id_articulos " +
            "       INNER JOIN subrubro " +
            "           ON  articulos.id_negocio = subrubro.id_negocio AND " +
            "               articulos.id_segmento = subrubro.id_segmento AND " +
            "               articulos.id_subrubro = subrubro.id_subrubro " +
            "       INNER JOIN segmento " +
            "           ON  subrubro.id_negocio = segmento.id_negocio AND " +
            "               subrubro.id_segmento = segmento.id_segmento " +
            "       INNER JOIN negocio " +
            "           ON subrubro.id_negocio = negocio.id_negocio " +
            "       INNER JOIN linea " +
            "           ON  linea.id_linea = articulos.id_linea " +
            "       INNER JOIN stock " +
            "           ON stock.id_articulos = articulos.id_articulos " +
            "       INNER JOIN proveedores " +
            "           ON proveedores.id_proveedor = articulos.id_proveedor " +
            "WHERE  documentos.sn_estadistica = 'S' AND " +
            "       ((:idSucursal = 0 and :idCliente = 0 and :idComercio = 0) OR " + /*cuando es null el valor de la pkcliente*/
            "       (ventas.id_sucursal = :idSucursal AND " +
            "       ventas.id_cliente = :idCliente AND " +
            "       ventas.id_comercio = :idComercio)) " +
            "GROUP BY articulos.id_articulos, ventas.fe_venta  " +
            "ORDER BY ventas.fe_venta ")
    List<ArticuloResultBd> recoveryCantidadesPorCliente(int idSucursal, int idCliente, int idComercio) throws Exception;

    @Transaction
    @Query(value = "SELECT IFNULL(stk.stock,0.00) " +
            "FROM   articulos " +
            "       INNER JOIN stock as stk " +
            "           ON articulos.id_articulos = stk.id_articulos " +
            "WHERE articulos.id_articulos = :idArticulo ")
    double recoveryStockById(int idArticulo) throws Exception;

    @Transaction
    @Query(value = "SELECT  articulos.*, de_negocio, de_segmento, de_subrubro, de_linea, " +
            "               de_proveedor, id_plancta, proveedores.id_sucursal as id_sucursal, ti_proveedor, nu_cuit,  " +
            "               stock.stock, 0 as cantidad_vendida, " +
            "               null as fecha_ultima_venta " +
            "FROM   articulos " +
            "       INNER JOIN subrubro " +
            "           ON  articulos.id_negocio = subrubro.id_negocio AND " +
            "               articulos.id_segmento = subrubro.id_segmento AND " +
            "               articulos.id_subrubro = subrubro.id_subrubro " +
            "       INNER JOIN segmento " +
            "           ON  subrubro.id_negocio = segmento.id_negocio AND " +
            "               subrubro.id_segmento = segmento.id_segmento " +
            "       INNER JOIN negocio " +
            "           ON subrubro.id_negocio = negocio.id_negocio " +
            "       INNER JOIN linea " +
            "           ON  linea.id_linea = articulos.id_linea " +
            "       INNER JOIN stock " +
            "           ON stock.id_articulos = articulos.id_articulos " +
            "       INNER JOIN proveedores " +
            "           ON proveedores.id_proveedor = articulos.id_proveedor " +
            "INNER JOIN proveedores_vend_comercio " +
            "   ON articulos.id_proveedor = proveedores_vend_comercio.id_proveedor " +
            "INNER JOIN comercio " +
            "   ON  proveedores_vend_comercio.id_sucursal = comercio.id_sucursal AND " +
            "       proveedores_vend_comercio.id_cliente = comercio.id_comercio AND " +
            "       proveedores_vend_comercio.id_comercio = comercio.id_comercio " +
            "WHERE  (:idSucursal = 0 and :idCliente = 0 and :idComercio = 0) OR " + /*cuando es null el valor de la pkcliente*/
            "       (comercio.id_sucursal = :idSucursal AND " +
            "       comercio.id_cliente = :idCliente AND " +
            "       comercio.id_comercio = :idComercio) ")
    List<ArticuloResultBd> recoveryAllByCliente(int idSucursal, int idCliente, int idComercio) throws Exception;

    @Transaction
    @Query(value = "SELECT articulos.* " +
            "FROM   articulos " +
            "WHERE  articulos.id_articulos = :id ")
    ArticuloBd recoveryByIDSinCruce(int id) throws Exception;

    @Query(value = "UPDATE stock " +
            "SET stock = :stockNuevo " +
            "WHERE stock.id_articulos = :idArticulo")
    void updateStock(int idArticulo, double stockNuevo) throws Exception;
}
