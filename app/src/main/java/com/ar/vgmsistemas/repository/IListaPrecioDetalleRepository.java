package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.ListaPrecioDetalleBd;
import com.ar.vgmsistemas.entity.ListaPrecio;
import com.ar.vgmsistemas.entity.ListaPrecioDetalle;
import com.ar.vgmsistemas.entity.Subrubro;
import com.ar.vgmsistemas.entity.key.PkListaPrecioDetalle;

import java.util.List;

public interface IListaPrecioDetalleRepository extends
        IGenericRepository<ListaPrecioDetalle, PkListaPrecioDetalle> {

    List<ListaPrecioDetalle> recoveryByArticulo(int idArticulo) throws Exception;

    List<ListaPrecioDetalle> recoveryByListaPrecio(ListaPrecio listaPrecio, Integer idProveedor)
            throws Exception;

    List<ListaPrecioDetalle> recoveryByListaPrecioSubrubro(ListaPrecio listaPrecio,
                                                           Subrubro subrubro, Integer idProveedor)
            throws Exception;

    List<ListaPrecioDetalle> recoveryByArticuloMovil(int idArticulo) throws Exception;

    List<ListaPrecioDetalle> recoveryByArticuloAndLista(int idLista, int idArticulo)
            throws Exception;

    List<ListaPrecioDetalle> recoveryById(int idArticulo, int idLista) throws Exception;

    ListaPrecioDetalle recoveryById(int idArticulo, int idLista, int caArticuloDesde,
                                    int caArticuloHasta) throws Exception;

    List<ListaPrecioDetalle> recoveryPromocionesYCombos() throws Exception;

    ListaPrecioDetalle mappingToDto(ListaPrecioDetalleBd listaPrecioDetalleBd) throws Exception;
}
