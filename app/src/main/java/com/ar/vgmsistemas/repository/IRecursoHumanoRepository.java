package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.RecursoHumanoBd;
import com.ar.vgmsistemas.entity.RecursoHumano;

import java.text.ParseException;

public interface IRecursoHumanoRepository extends IGenericRepository<RecursoHumano, Integer> {

    RecursoHumano mappingToDto(RecursoHumanoBd recursoHumanoBd) throws ParseException;

}
