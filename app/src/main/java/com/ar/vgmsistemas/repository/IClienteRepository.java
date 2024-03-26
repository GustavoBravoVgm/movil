package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.ClienteBd;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.key.PkCliente;

import java.util.List;

public interface IClienteRepository extends IGenericRepository<Cliente, PkCliente> {

    List<Cliente> recoveryNoEnviados() throws Exception;

    void updateLimiteDisonibilidad(Cliente cliente) throws Exception;

    int recoveryVendedorCliente(Cliente cliente) throws Exception;

    List<Cliente> recoveryAllGroupByCteSuc(int idLocalidad, int idSucursalCte) throws Exception;

    Cliente mappingToDto(ClienteBd clienteBd) throws Exception;
}
