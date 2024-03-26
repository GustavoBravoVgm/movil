package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.entity.PromocionDetalle;

import java.util.List;

public interface IPromocionRepository extends IGenericRepository<PromocionDetalle, Integer> {

    List<PromocionDetalle> recoveryItems(Articulo articulo) throws Exception;

    List<PromocionDetalle> recoveryItemsByItem(Articulo articuloItem) throws Exception;

}
