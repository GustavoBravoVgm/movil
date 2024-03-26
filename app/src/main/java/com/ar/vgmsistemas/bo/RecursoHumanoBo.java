package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.RecursoHumano;
import com.ar.vgmsistemas.repository.IRecursoHumanoRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

public class RecursoHumanoBo {
    private final IRecursoHumanoRepository _recursoHumanoRepository;

    public RecursoHumanoBo(RepositoryFactory repoFactory) {
        this._recursoHumanoRepository = repoFactory.getRecursoHumanoRepository();
    }

    public RecursoHumano recoveryByID(int id) throws Exception {
        return _recursoHumanoRepository.recoveryByID(id);
    }
}
