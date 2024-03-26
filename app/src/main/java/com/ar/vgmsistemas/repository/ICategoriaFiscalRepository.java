package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.CategoriaFiscalBd;
import com.ar.vgmsistemas.entity.CategoriaFiscal;

import java.text.ParseException;

public interface ICategoriaFiscalRepository extends IGenericRepository<CategoriaFiscal, String> {
    CategoriaFiscal mappingToDto(CategoriaFiscalBd categoriaFiscalBd) throws ParseException;

}
