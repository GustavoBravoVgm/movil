package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.AccionesCom;
import com.ar.vgmsistemas.repository.IAccionesComRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

public class AccionesComBo {
    private final IAccionesComRepository _accionesComRepository;

    public AccionesComBo(RepositoryFactory repoFactory) {
        _accionesComRepository = repoFactory.getAccionesComRepository();
    }

    public AccionesCom recoveryById(Integer id) throws Exception {
        return _accionesComRepository.recoveryByID(id);
    }

}
