package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.BancoBd;
import com.ar.vgmsistemas.entity.Banco;

public interface IBancoRepository extends IGenericRepository<Banco, Integer> {
    Banco mappingToDto(BancoBd bancoBd);
}
