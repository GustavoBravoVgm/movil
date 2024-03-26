package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.Impuesto;
import com.ar.vgmsistemas.repository.IImpuestoRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.List;

public class ImpuestoBo {
    private final IImpuestoRepository _impuestoRepository;

    public ImpuestoBo(RepositoryFactory repoFactory) {
        _impuestoRepository = repoFactory.getImpuestoRepository();
    }

    public List<Impuesto> recoveryAll() throws Exception {
        return _impuestoRepository.recoveryAll();
    }

    public Impuesto recoveryById(Integer id) throws Exception {
        return _impuestoRepository.recoveryByID(id);
    }
}
