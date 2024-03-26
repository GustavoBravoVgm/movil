package com.ar.vgmsistemas.bo;


import com.ar.vgmsistemas.entity.CondicionRenta;
import com.ar.vgmsistemas.entity.key.PkCliente;
import com.ar.vgmsistemas.repository.ICondicionRentaRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.List;

public class CondicionRentaBo {

    private final ICondicionRentaRepository _condicionRentaRepository;

    public CondicionRentaBo(RepositoryFactory repoFactory) {
        _condicionRentaRepository = repoFactory.getCondicionRentaRepository();
    }

    public List<CondicionRenta> recoveryAll() throws Exception {
        return _condicionRentaRepository.recoveryAll();
    }

    public CondicionRenta recoveryById(int id) throws Exception {
        return _condicionRentaRepository.recoveryByID(id);
    }

    public CondicionRenta recoveryByCliente(PkCliente id) throws Exception {
        return _condicionRentaRepository.recoveryByCliente(id);
    }
}
