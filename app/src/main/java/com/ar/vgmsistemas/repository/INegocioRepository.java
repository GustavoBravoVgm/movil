package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.NegocioBd;
import com.ar.vgmsistemas.entity.Negocio;

public interface INegocioRepository extends IGenericRepository<Negocio, Integer> {
    Negocio mappingToDto(NegocioBd negocioBd);

    Negocio mappingToDto(Integer id) throws Exception;
}
