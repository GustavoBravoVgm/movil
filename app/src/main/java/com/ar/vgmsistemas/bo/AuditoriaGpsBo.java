package com.ar.vgmsistemas.bo;

import android.content.Context;

import com.ar.vgmsistemas.entity.AuditoriaGps;
import com.ar.vgmsistemas.repository.IAuditoriaGpsRepository;
import com.ar.vgmsistemas.repository.IMovimientoRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.CodeResult;
import com.ar.vgmsistemas.ws.AuditoriaGpsWs;

import java.util.List;


public class AuditoriaGpsBo {
    private final IAuditoriaGpsRepository _auditoriaGpsRepository;
    private final IMovimientoRepository _movimientoRepository;

    public AuditoriaGpsBo(RepositoryFactory _repoFactory) {
        _auditoriaGpsRepository = _repoFactory != null ? _repoFactory.getAuditoriaGpsRepository() : null;
        _movimientoRepository = _repoFactory != null ? _repoFactory.getMovimientoRepository() : null;
    }

    public List<AuditoriaGps> recoveryAEnviar() throws Exception {
        return _auditoriaGpsRepository.recoveryAEnviar();
    }

    public synchronized void enviarAuditoria(AuditoriaGps auditoriaGps, Context context) throws Exception {
        AuditoriaGpsWs auditoriaGpsWs = new AuditoriaGpsWs(context);
        int result = auditoriaGpsWs.send(auditoriaGps);
        if (result == CodeResult.RESULT_OK) {
            _movimientoRepository.updateFechaSincronizacion(auditoriaGps);
        }
    }

    public Long create(AuditoriaGps auditoriaGps) throws Exception {
        return _auditoriaGpsRepository.create(auditoriaGps);
    }

    public boolean existsAuditoria(String idMovil) throws Exception {
        return _auditoriaGpsRepository.existsAuditoria(idMovil);
    }

    public boolean existsAuditoria(AuditoriaGps auditoriaGps) throws Exception {
        return _auditoriaGpsRepository.existsAuditoria(auditoriaGps);
    }

}
