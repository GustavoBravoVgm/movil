package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.ComercioLogin;
import com.ar.vgmsistemas.entity.key.PkCliente;
import com.ar.vgmsistemas.repository.IComercioLoginRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

public class ComercioLoginBo {

    private final IComercioLoginRepository _comercioLoginRepository;

    public ComercioLoginBo(RepositoryFactory repoFactory) {
        _comercioLoginRepository = repoFactory.getComercioLoginRepository();
    }

    public ComercioLogin recoveryByCliente(PkCliente id) throws Exception {
        return _comercioLoginRepository.recoveryByCliente(id);
    }


}
