package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.Marca;
import com.ar.vgmsistemas.repository.IMarcaRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.List;

public class MarcaBo {

    private final IMarcaRepository _marcaRepository;

    public MarcaBo(RepositoryFactory repoFactory) {
        _marcaRepository = repoFactory.getMarcaRepository();
    }

    public List<Marca> recoveryAll() throws Exception {
        return _marcaRepository.recoveryAll();
    }

    public Marca recoveryById(int id) throws Exception {
        return _marcaRepository.recoveryByID(id);
    }
}
