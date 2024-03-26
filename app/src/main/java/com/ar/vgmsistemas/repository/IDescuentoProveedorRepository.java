package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.DescuentoProveedorBd;
import com.ar.vgmsistemas.entity.DescuentoProveedor;
import com.ar.vgmsistemas.entity.key.PkCliente;

import java.util.List;


public interface IDescuentoProveedorRepository extends IGenericRepository<DescuentoProveedor, Integer> {

    List<DescuentoProveedor> recoveryByCliente(PkCliente id) throws Exception;

    DescuentoProveedor mappingToDto(DescuentoProveedorBd descuentoProveedorBd);

}
