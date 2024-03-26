package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.Negocio;
import com.ar.vgmsistemas.repository.INegocioRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.List;

public class NegocioBo {

    private final INegocioRepository _negocioRepository;

    public NegocioBo(RepositoryFactory repoFactory) {
        _negocioRepository = repoFactory.getNegocioRepository();
    }

    public List<Negocio> recoveryAll() throws Exception {
        if (_negocioRepository != null) return _negocioRepository.recoveryAll();
        return null;
    }

    public Negocio recoveryById(Integer id) throws Exception {
        if (_negocioRepository != null) return _negocioRepository.recoveryByID(id);
        return null;
    }
}
