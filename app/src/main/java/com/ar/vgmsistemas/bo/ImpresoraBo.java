package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.Impresora;
import com.ar.vgmsistemas.repository.IImpresoraRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.List;

public class ImpresoraBo {

    private final IImpresoraRepository _impresoraRepository;

    public ImpresoraBo(RepositoryFactory repoFactory) {
        _impresoraRepository = repoFactory.getImpresoraRepository();
    }

    public List<Impresora> recoveryAll() throws Exception {
        List<Impresora> impresoras = _impresoraRepository.recoveryAll();
        return impresoras;
    }
}
