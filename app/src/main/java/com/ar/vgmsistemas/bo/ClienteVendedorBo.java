package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.ClienteVendedor;
import com.ar.vgmsistemas.entity.key.PkClienteVendedor;
import com.ar.vgmsistemas.repository.IClienteVendedorRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.List;

public class ClienteVendedorBo {
    private static List<ClienteVendedor> _vendComercio;
    private final IClienteVendedorRepository _clienteVendedorRepository;

    public ClienteVendedorBo(RepositoryFactory repositoryFactory) {
        _clienteVendedorRepository = repositoryFactory.getClienteVendedorRepository();
    }

    public List<ClienteVendedor> recoveryAll() throws Exception {
        _vendComercio = _clienteVendedorRepository.recoveryAll();
        return _vendComercio;
    }

    public ClienteVendedor recoveryByID(PkClienteVendedor pkClienteVendedor) throws Exception {
        return _clienteVendedorRepository.recoveryByID(pkClienteVendedor);
    }

}
