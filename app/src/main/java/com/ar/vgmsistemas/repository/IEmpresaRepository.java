package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.entity.Empresa;

public interface IEmpresaRepository extends IGenericRepository<Empresa, Long> {

    public boolean existsTable() throws Exception;
}
