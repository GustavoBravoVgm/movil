package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.DescuentoProveedorGeneralBd;
import com.ar.vgmsistemas.entity.DescuentoProveedorGeneral;

import java.util.List;

public interface IDescuentoProveedorGeneralRepository extends IGenericRepository<DescuentoProveedorGeneral, Long> {

    List<DescuentoProveedorGeneral> recoveryGenerales() throws Exception;

    DescuentoProveedorGeneral mappingToDto(DescuentoProveedorGeneralBd descuentoProveedorBd);

}
