package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.entity.AuditoriaGps;

import java.util.List;

public interface IAuditoriaGpsRepository extends IGenericRepository<AuditoriaGps, Long> {
    List<AuditoriaGps> recoveryAEnviar() throws Exception;

    boolean existsAuditoria(String idMovil) throws Exception;

    boolean existsAuditoria(AuditoriaGps auditoriaGps) throws Exception;
}
