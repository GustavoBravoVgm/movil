package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.CondicionRentaBd;
import com.ar.vgmsistemas.entity.CondicionRenta;
import com.ar.vgmsistemas.entity.key.PkCliente;

public interface ICondicionRentaRepository extends IGenericRepository<CondicionRenta, Integer> {

    public CondicionRenta recoveryByCliente(PkCliente id) throws Exception;

    CondicionRenta mappingToDto(CondicionRentaBd condicionRentaBd) throws Exception;

}
