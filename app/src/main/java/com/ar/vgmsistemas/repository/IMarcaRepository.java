package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.MarcaBd;
import com.ar.vgmsistemas.entity.Marca;

public interface IMarcaRepository extends IGenericRepository<Marca, Integer> {

    Marca mappingToDto(MarcaBd marcaBd) throws Exception;
}
