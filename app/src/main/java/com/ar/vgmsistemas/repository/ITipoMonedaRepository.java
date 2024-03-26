package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.TipoMonedaBd;
import com.ar.vgmsistemas.entity.TipoMoneda;

public interface ITipoMonedaRepository extends IGenericRepository<TipoMoneda, Integer> {
    default TipoMoneda mappingToDto(TipoMonedaBd tipoMonedaBd) {
        return null;
    }
}
