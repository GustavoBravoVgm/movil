package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.CategoriaFiscal;
import com.ar.vgmsistemas.repository.ICategoriaFiscalRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.List;

public class CategoriaFiscalBo {
    private final RepositoryFactory _repoFactory;

    public ICategoriaFiscalRepository _categoriaFiscalRepository;

    public CategoriaFiscalBo(RepositoryFactory repoFactory) {
        _repoFactory = repoFactory;
    }

    public List<CategoriaFiscal> recoveryAll() throws Exception {
        _categoriaFiscalRepository = _repoFactory.getCategoriaFiscalRepository();
        return _categoriaFiscalRepository.recoveryAll();
    }

    public CategoriaFiscal recoveryById(String id) throws Exception {
        return _categoriaFiscalRepository.recoveryByID(id);
    }


}
