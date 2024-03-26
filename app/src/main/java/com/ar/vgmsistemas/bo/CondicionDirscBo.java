package com.ar.vgmsistemas.bo;


import com.ar.vgmsistemas.entity.CondicionDirsc;
import com.ar.vgmsistemas.entity.key.PkCliente;
import com.ar.vgmsistemas.repository.ICondicionDirscRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

public class CondicionDirscBo {

    private final ICondicionDirscRepository _condicionDirscRepository;

    public CondicionDirscBo(RepositoryFactory repoFactory) {
        this._condicionDirscRepository = repoFactory.getCondicionDirscRepository();
    }

    public CondicionDirsc recoveryByCliente(PkCliente id) throws Exception {
        return _condicionDirscRepository.recoveryByCliente(id);
    }

}
