package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.ClienteVendedorBd;
import com.ar.vgmsistemas.entity.ClienteVendedor;
import com.ar.vgmsistemas.entity.key.PkClienteVendedor;

public interface IClienteVendedorRepository extends IGenericRepository<ClienteVendedor, PkClienteVendedor> {
    ClienteVendedor mappingToDto(ClienteVendedorBd clienteVendedorBd);
}
