package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.EntregaRendicionBd;
import com.ar.vgmsistemas.entity.EntregaRendicion;
import com.ar.vgmsistemas.entity.key.PkEntregaRendicion;

import java.text.ParseException;
import java.util.List;

public interface IEntregaRendicionRepository extends IGenericRepository<EntregaRendicion, PkEntregaRendicion> {

    List<EntregaRendicion> recoveryNoEnviadas() throws Exception;

    EntregaRendicion mappingToDto(EntregaRendicionBd entregaRendicionBd) throws ParseException;

}
