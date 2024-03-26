package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.EntregaBd;
import com.ar.vgmsistemas.entity.Entrega;

public interface IEntregaRepository extends IGenericRepository<Entrega, Integer> {
    Entrega mappingToDto(EntregaBd entregaBd);

    EntregaBd mappingToDb(Entrega entrega);
}
