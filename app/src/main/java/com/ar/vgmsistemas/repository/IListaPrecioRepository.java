package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.ListaPrecioBd;
import com.ar.vgmsistemas.entity.ListaPrecio;

import java.util.List;

public interface IListaPrecioRepository extends IGenericRepository<ListaPrecio, Integer> {

    List<ListaPrecio> recoveryAllSeleccionable() throws Exception;

    ListaPrecio mappingToDto(ListaPrecioBd listaPrecioBd);
}
