package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.PlanCuentaBd;
import com.ar.vgmsistemas.entity.Documento;
import com.ar.vgmsistemas.entity.PlanCuenta;

import java.util.List;

public interface IPlanCuentaRepository extends IGenericRepository<PlanCuenta, Integer> {

    List<PlanCuenta> recoveryByDocumento(Documento documento) throws Exception;

    List<PlanCuenta> recoveryForEgreso() throws Exception;

    PlanCuenta mappingToDto(PlanCuentaBd planCuentaBd);

}
