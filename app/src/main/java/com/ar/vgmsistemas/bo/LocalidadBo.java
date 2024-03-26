package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.Localidad;
import com.ar.vgmsistemas.repository.ILocalidadRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.List;

public class LocalidadBo {
    private final ILocalidadRepository _localidadRepository;

    public LocalidadBo(RepositoryFactory repoFactory) {
        this._localidadRepository = repoFactory.getLocalidadRepository();
    }

    public List<Localidad> recoveryAll() throws Exception {
        return _localidadRepository.recoveryAll();
    }

    public List<Localidad> recoveryByProvincia(int idProvincia) throws Exception {
        return _localidadRepository.recoveryByProvincia(idProvincia);
    }

}
