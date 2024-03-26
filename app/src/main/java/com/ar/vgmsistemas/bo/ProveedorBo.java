package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.Proveedor;
import com.ar.vgmsistemas.repository.IProveedorRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.List;

public class ProveedorBo {

    private final IProveedorRepository _ProveedorRepository;

    public ProveedorBo(RepositoryFactory repoFactory) {
        this._ProveedorRepository = repoFactory != null ? repoFactory.getProveedorRepository() : null;
    }

    public Proveedor recoveryProveedorById(Integer id) throws Exception {
        return _ProveedorRepository.recoveryByID(id);
    }

    public List<Proveedor> recoveryProveedoresTipoGasto() throws Exception {
        return _ProveedorRepository.recoveryProveedoresTipoGasto();
    }

    public List<Proveedor> recoveryProveedoresTipoGastoBySucursal(int idSucursal) throws Exception {
        return _ProveedorRepository.recoveryProveedoresTipoGastoBySucursal(idSucursal);
    }
}
