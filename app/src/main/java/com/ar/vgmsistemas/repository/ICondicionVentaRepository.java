package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.CondicionVentaBd;
import com.ar.vgmsistemas.entity.CondicionVenta;

public interface ICondicionVentaRepository extends IGenericRepository<CondicionVenta, String> {

    CondicionVenta mappingToDto(CondicionVentaBd condicionVentaBd);
}
