package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.ProvinciaBd;
import com.ar.vgmsistemas.entity.Provincia;

public interface IProvinciaRepository extends IGenericRepository<Provincia, Integer> {

    Provincia mappingToDto(ProvinciaBd provinciaBd);

}
