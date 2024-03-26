package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.CondicionDirscBd;
import com.ar.vgmsistemas.entity.CondicionDirsc;
import com.ar.vgmsistemas.entity.key.PkCliente;

public interface ICondicionDirscRepository extends IGenericRepository<CondicionDirsc, Integer> {

    public CondicionDirsc recoveryByCliente(PkCliente id) throws Exception;

    CondicionDirsc mappingToDto(CondicionDirscBd condicionDirscBd) throws Exception;

}
