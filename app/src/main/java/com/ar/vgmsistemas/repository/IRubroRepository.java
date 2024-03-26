package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.RubroBd;
import com.ar.vgmsistemas.entity.Rubro;
import com.ar.vgmsistemas.entity.key.PkRubro;


public interface IRubroRepository extends IGenericRepository<Rubro, PkRubro> {
    Rubro mappingToDto(RubroBd rubroBd) throws Exception;

    Rubro mappingToDtoSinNegocio(RubroBd rubroBd);
}
