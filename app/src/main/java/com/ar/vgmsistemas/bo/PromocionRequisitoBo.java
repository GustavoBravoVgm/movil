package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.PromocionRequisito;
import com.ar.vgmsistemas.repository.IPromocionRequisitoRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

public class PromocionRequisitoBo {

    private final IPromocionRequisitoRepository _promocionRequisitoRepository;

    public PromocionRequisitoBo(RepositoryFactory repoFactory) {
        this._promocionRequisitoRepository = repoFactory != null ? repoFactory.getPromocionRequisitoRepository() : null;
    }

    public PromocionRequisito getRequisito(long idArticulo) throws Exception {
        return _promocionRequisitoRepository.recoveryRequisitos((int) idArticulo);
    }

}
