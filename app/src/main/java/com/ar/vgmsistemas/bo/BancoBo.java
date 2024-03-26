package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.Banco;
import com.ar.vgmsistemas.repository.IBancoRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.List;

public class BancoBo {

    public final IBancoRepository _bancoRepository;

    public BancoBo(RepositoryFactory repoFactory) {
        _bancoRepository = repoFactory.getBancoRepository();
    }

    public List<Banco> recoveryAll() throws Exception {
        return (_bancoRepository.recoveryAll());
    }

}
