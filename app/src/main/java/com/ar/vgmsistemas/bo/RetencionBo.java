package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.Retencion;
import com.ar.vgmsistemas.repository.IRetencionRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

public class RetencionBo {
    private final IRetencionRepository _retencionRepository;

    public RetencionBo(RepositoryFactory repoFactory) {
        this._retencionRepository = repoFactory.getRetencionRepository();
    }

    public void guardar(Retencion retencion) throws Exception {
        _retencionRepository.create(retencion);
    }

    public void delete(Retencion retencion) throws Exception {
        _retencionRepository.delete(retencion);
    }

    public void deleteByEntrega(Retencion retencion) throws Exception {
        _retencionRepository.deleteByEntrega(retencion);
    }

}
