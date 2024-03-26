package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.ListaPrecioDetalleBd;

import java.util.List;

@Dao
public interface IListaPrecioDetalleDao {
    @Query(value = "SELECT listasPreciosDetalle.* FROM listasPreciosDetalle")
    List<ListaPrecioDetalleBd> recoveryAll() throws Exception;

    @Query(value = "SELECT listasPreciosDetalle.* " +
            "FROM   listasPreciosDetalle " +
            "WHERE  listasPreciosDetalle.id_lista = :idLista AND " +
            "       listasPreciosDetalle.id_articulos = :idArticulo AND " +
            "       listasPreciosDetalle.ca_articulo_desde = :caArticuloDesde AND " +
            "       listasPreciosDetalle.ca_articulo_hasta = :caArticuloHasta")
    ListaPrecioDetalleBd recoveryByID(int idLista, int idArticulo, int caArticuloDesde, int caArticuloHasta) throws Exception;

    @Query(value = "SELECT listasPreciosDetalle.* " +
            "FROM   listasPreciosDetalle " +
            "WHERE  listasPreciosDetalle.id_lista = :idLista AND " +
            "       listasPreciosDetalle.id_articulos = :idArticulo ")
    List<ListaPrecioDetalleBd> recoveryByArticuloAndLista(int idLista, int idArticulo) throws Exception;

    @Query(value = "SELECT listasPreciosDetalle.* " +
            "FROM   listasPreciosDetalle " +
            "       INNER JOIN articulos " +
            "           ON articulos.id_articulos = listasPreciosDetalle.id_articulos " +
            "       INNER JOIN proveedores " +
            "           ON proveedores.id_proveedor = articulos.id_proveedor " +
            "       INNER JOIN stock    " +
            "           ON stock.id_articulos = articulos.id_articulos " +
            "WHERE  listasPreciosDetalle.id_lista = :idLista AND " +
            "       (:idProveedor is NULL OR " +
            "        (:idProveedor IS NOT NULL AND articulos.id_proveedor = :idProveedor)) AND " +
            "       ((:tipoLista = 8 AND listasPreciosDetalle.pr_final < 0.02) OR " +
            "        (:tipoLista <> 8)) ")
    List<ListaPrecioDetalleBd> recoveryByListaPrecio(int idLista, Integer idProveedor, int tipoLista) throws Exception;

    @Query(value = "SELECT listasPreciosDetalle.* " +
            "FROM   listasPreciosDetalle " +
            "       INNER JOIN articulos " +
            "           ON articulos.id_articulos = listasPreciosDetalle.id_articulos " +
            "       INNER JOIN proveedores " +
            "           ON proveedores.id_proveedor = articulos.id_proveedor " +
            "       INNER JOIN subrubro " +
            "           ON  subrubro.id_negocio = articulos.id_negocio AND " +
            "               subrubro.id_segmento = articulos.id_segmento AND " +
            "               subrubro.id_subrubro = articulos.id_subrubro " +
            "WHERE  listasPreciosDetalle.id_lista = :idLista AND " +
            "       subrubro.id_subrubro = :idSubrubro  AND " +
            "       (:idProveedor is NULL OR " +
            "        (:idProveedor IS NOT NULL AND articulos.id_proveedor = :idProveedor)) AND " +
            "       ((:tipoLista = 8 AND listasPreciosDetalle.pr_final < 0.02) OR " +
            "        (:tipoLista <> 8)) ")
    List<ListaPrecioDetalleBd> recoveryByListaPrecioSubrubro(int idLista, Integer idProveedor, int idSubrubro, int tipoLista) throws Exception;


    @Query(value = "SELECT listasPreciosDetalle.* " +
            "FROM   listasPreciosDetalle " +
            "       INNER JOIN listasPrecios " +
            "           ON listasPrecios.id_lista = listasPreciosDetalle.id_lista " +
            "WHERE  listasPreciosDetalle.id_articulos = :idArticulo")
    List<ListaPrecioDetalleBd> recoveryByArticulo(int idArticulo) throws Exception;

    @Query(value = "SELECT listasPreciosDetalle.* " +
            "FROM   listasPreciosDetalle " +
            "       INNER JOIN listasPrecios " +
            "           ON listasPrecios.id_lista = listasPreciosDetalle.id_lista " +
            "WHERE  (listasPrecios.sn_palm = 'S' OR listasPrecios.sn_palm IS NULL) AND " +
            "       (listasPrecios.ti_lista NOT IN (8) OR listasPreciosDetalle.pr_final < 0.02 ) AND" +
            "       listasPreciosDetalle.id_articulos = :idArticulo")
    List<ListaPrecioDetalleBd> recoveryByArticuloMovil(int idArticulo) throws Exception;

    @Query(value = "SELECT listasPreciosDetalle.* " +
            "FROM   listasPreciosDetalle " +
            "WHERE  listasPreciosDetalle.id_articulos = :idArticulo AND " +
            "       listasPreciosDetalle.id_lista = :idLista")
    List<ListaPrecioDetalleBd> recoveryById(int idArticulo, int idLista) throws Exception;


    @Query(value = "SELECT listasPreciosDetalle.* " +
            "FROM   listasPreciosDetalle " +
            "       INNER JOIN articulos " +
            "           ON articulos.id_articulos = listasPreciosDetalle.id_articulos " +
            "       INNER JOIN listasPrecios " +
            "           ON listasPrecios.id_lista = listasPreciosDetalle.id_lista " +
            "WHERE  (listasPrecios.sn_palm = 'S' OR listasPrecios.sn_palm IS NULL) AND " +
            "       (listasPrecios.ti_lista = 3 OR listasPrecios.ti_lista = 8) AND " +
            "       (listasPrecios.ti_lista NOT IN (8) OR listasPreciosDetalle.pr_final < 0.02 )")
    List<ListaPrecioDetalleBd> recoveryPromocionesYCombos() throws Exception;


}
