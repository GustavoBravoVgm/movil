package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.Provincia;
import com.ar.vgmsistemas.repository.IProvinciaRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.List;

public class ProvinciaBo {

    private final RepositoryFactory _repoFactory;

    private IProvinciaRepository provinciaRepository;

    public ProvinciaBo(RepositoryFactory repoFactory) {
        _repoFactory = repoFactory;
    }

    public List<Provincia> recoveryAll() throws Exception {
        provinciaRepository = _repoFactory.getProvinciaRepository();
        List<Provincia> provincias = null;
        if (provinciaRepository != null) {
            provincias = provinciaRepository.recoveryAll();
        }
        return provincias;
    }

    public Provincia recoveryByID(Integer id) throws Exception {
        provinciaRepository = _repoFactory.getProvinciaRepository();
        Provincia provincia = null;
        if (provinciaRepository != null) {
            provincia = provinciaRepository.recoveryByID(id);
        }
        return provincia;
    }
}
