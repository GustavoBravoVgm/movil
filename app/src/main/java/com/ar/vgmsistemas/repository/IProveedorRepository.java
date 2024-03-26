package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.ProveedorBd;
import com.ar.vgmsistemas.entity.Proveedor;

import java.util.List;

public interface IProveedorRepository extends IGenericRepository<Proveedor, Integer> {

    public List<Proveedor> recoveryProveedoresTipoGasto() throws Exception;

    List<Proveedor> recoveryProveedoresTipoGastoBySucursal(int idSucursal) throws Exception;

    Proveedor mappingToDto(ProveedorBd proveedorBd) throws Exception;
}
