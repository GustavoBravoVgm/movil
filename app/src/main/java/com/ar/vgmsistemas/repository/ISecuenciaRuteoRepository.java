package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.entity.SecuenciaRuteo;
import com.ar.vgmsistemas.entity.key.PkSecuenciaRuteo;

import java.util.List;

public interface ISecuenciaRuteoRepository extends IGenericRepository<SecuenciaRuteo, PkSecuenciaRuteo> {

    List<SecuenciaRuteo> recoveryRutasByDia(int dia, int idLocalidad, int idSucursal) throws Exception;

}
