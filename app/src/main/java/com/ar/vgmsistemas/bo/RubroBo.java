package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.Rubro;
import com.ar.vgmsistemas.entity.key.PkRubro;
import com.ar.vgmsistemas.repository.IRubroRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.List;

public class RubroBo {
    private final IRubroRepository _rubroRepository;

    public RubroBo(RepositoryFactory repoFactory) {
        this._rubroRepository = repoFactory.getRubroRepository();
    }

    public Rubro recoveryById(PkRubro pkrubro) throws Exception {
        return _rubroRepository.recoveryByID(pkrubro);
    }

    public List<Rubro> recoveryAll() throws Exception {
        return _rubroRepository.recoveryAll();
    }
}
