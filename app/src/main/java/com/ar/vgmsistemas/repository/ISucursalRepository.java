package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.SucursalBd;
import com.ar.vgmsistemas.entity.Sucursal;

public interface ISucursalRepository extends IGenericRepository<Sucursal, Integer> {
    Sucursal mappingToDto(SucursalBd sucursalBd);
}