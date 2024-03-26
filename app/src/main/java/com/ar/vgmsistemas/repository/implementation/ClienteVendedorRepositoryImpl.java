package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IClienteVendedorDao;
import com.ar.vgmsistemas.database.dao.entity.ClienteVendedorBd;
import com.ar.vgmsistemas.entity.ClienteVendedor;
import com.ar.vgmsistemas.entity.key.PkClienteVendedor;
import com.ar.vgmsistemas.repository.IClienteVendedorRepository;

import java.util.List;

public class ClienteVendedorRepositoryImpl implements IClienteVendedorRepository {
    private IClienteVendedorDao _clienteVendedorDao;

    public ClienteVendedorRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._clienteVendedorDao = db.clienteVendedorDao();
        }
    }

    @Override
    public PkClienteVendedor create(ClienteVendedor entity) throws Exception {
        return null;
    }

    @Override
    public ClienteVendedor recoveryByID(PkClienteVendedor pkClienteVendedor) throws Exception {
        return mappingToDto(_clienteVendedorDao.recoveryByID(pkClienteVendedor.getIdVendedor(),
                pkClienteVendedor.getIdSucursal(), pkClienteVendedor.getIdCliente(),
                pkClienteVendedor.getIdComercio()));
    }

    @Override
    public List<ClienteVendedor> recoveryAll() throws Exception {
        return null;
    }

    @Override
    public void update(ClienteVendedor entity) throws Exception {

    }

    @Override
    public void delete(ClienteVendedor entity) throws Exception {

    }

    @Override
    public void delete(PkClienteVendedor pkClienteVendedor) throws Exception {

    }

    @Override
    public ClienteVendedor mappingToDto(ClienteVendedorBd clienteVendedorBd) {
        ClienteVendedor clienteVendedor = new ClienteVendedor();
        if (clienteVendedorBd != null) {
            PkClienteVendedor id = new PkClienteVendedor(clienteVendedorBd.getId().getIdVendedor(),
                    clienteVendedorBd.getId().getIdSucursal(), clienteVendedorBd.getId().getIdCliente(),
                    clienteVendedorBd.getId().getIdComercio());
            clienteVendedor.setId(id);
            clienteVendedor.setSnFiltroArticulo(clienteVendedorBd.getSnFiltroArticulo());
            clienteVendedor.setTiFiltroArticuloSinc(clienteVendedor.getTiFiltroArticuloSinc());
        }
        return clienteVendedor;
    }
}
