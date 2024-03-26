package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.VendedorBd;
import com.ar.vgmsistemas.entity.Vendedor;
import com.ar.vgmsistemas.entity.key.PkVenta;

public interface IVendedorRepository extends IGenericRepository<Vendedor, Integer> {

    Vendedor recoveryByLogin(String nombreUsuario, String password) throws Exception;

    Vendedor getVendedorVenta(PkVenta pkVenta) throws Exception;

    Vendedor mappingToDto(VendedorBd vendedorBd) throws Exception;

}
