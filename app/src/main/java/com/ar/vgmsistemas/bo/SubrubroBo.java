package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.Rubro;
import com.ar.vgmsistemas.entity.Subrubro;
import com.ar.vgmsistemas.entity.key.PkSubrubro;
import com.ar.vgmsistemas.repository.ISubrubroRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.List;

public class SubrubroBo {
    private final ISubrubroRepository _subrubroRepository;

    public SubrubroBo(RepositoryFactory repoFactory) {
        this._subrubroRepository = repoFactory.getSubrubroRepository();
    }

    public List<Subrubro> recoveryAll() throws Exception {
        return _subrubroRepository.recoveryAll();
    }

    public List<Subrubro> recoveryByRubro(Rubro rubro) throws Exception {
        return _subrubroRepository.recoveryByRubro(rubro);
    }

    public Subrubro recoveryById(PkSubrubro pkSubrubro) throws Exception {
        return _subrubroRepository.recoveryByID(pkSubrubro);
    }
}
