package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.ImpuestoBd;
import com.ar.vgmsistemas.entity.Impuesto;

public interface IImpuestoRepository extends IGenericRepository<Impuesto, Integer> {

    Impuesto mappingToDto(ImpuestoBd impuestoBd);
}
