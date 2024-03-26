package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.Sucursal;
import com.ar.vgmsistemas.repository.ISucursalRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.List;

public class SucursalBo {
    private final ISucursalRepository _sucursalRepository;

    public SucursalBo(RepositoryFactory repoFactory) {
        this._sucursalRepository = repoFactory.getSucursalRepository();
    }

    public List<Sucursal> recoveryAll() throws Exception {
        return _sucursalRepository.recoveryAll();
    }

    public Sucursal recoveryById(int idSucursal) throws Exception {
        return _sucursalRepository.recoveryByID(idSucursal);
    }
}
