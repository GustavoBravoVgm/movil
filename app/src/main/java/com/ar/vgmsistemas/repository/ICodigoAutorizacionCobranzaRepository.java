package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.CodigoAutorizacionCobranzaBd;
import com.ar.vgmsistemas.entity.CodigoAutorizacionCobranza;
import com.ar.vgmsistemas.entity.key.PkCodigoAutCobranza;

public interface ICodigoAutorizacionCobranzaRepository extends
        IGenericRepository<CodigoAutorizacionCobranza, PkCodigoAutCobranza> {

    CodigoAutorizacionCobranza recoveryByCodigo(String codigo) throws Exception;

    CodigoAutorizacionCobranza mappingToDto(CodigoAutorizacionCobranzaBd codigoAutorizacionCobranzaBd);
}
