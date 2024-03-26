package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.view.CantidadMovimientosViewBd;
import com.ar.vgmsistemas.entity.CantidadesMovimiento;


public interface ICantidadesMovimientoRepository extends IGenericRepository<CantidadesMovimiento, Integer> {

    int getCantidadNoAtenciones(int idSucursal, int idCliente, int idComercio) throws Exception;

    int getCantidadPedidos(int idSucursal, int idCliente, int idComercio) throws Exception;

    CantidadesMovimiento mappingToDto(CantidadMovimientosViewBd cantidadesMovimientoViewBd);

}
