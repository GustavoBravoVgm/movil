package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.entity.GrupoClientesDetalle;
import com.ar.vgmsistemas.entity.key.PkGrupoClientesDetalle;

import java.util.List;

public interface IGrupoClientesDetalleRepository extends IGenericRepository<GrupoClientesDetalle, PkGrupoClientesDetalle> {
    List<GrupoClientesDetalle> recoveryByRuta(long idVendedor) throws Exception;
}
