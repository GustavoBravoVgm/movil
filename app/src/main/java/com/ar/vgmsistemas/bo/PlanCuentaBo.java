package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.Documento;
import com.ar.vgmsistemas.entity.PlanCuenta;
import com.ar.vgmsistemas.repository.IPlanCuentaRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.List;

public class PlanCuentaBo {
    private final RepositoryFactory _repoFactory;
    private final IPlanCuentaRepository _planCuentaRepository;

    public PlanCuentaBo(RepositoryFactory repoFactory) {
        _repoFactory = repoFactory;
        _planCuentaRepository = _repoFactory != null ? _repoFactory.getPlanCuentaRepository() : null;
    }

    public List<PlanCuenta> recoveryByDocumento(Documento documento) throws Exception {
        DocumentoBo documentoBo = new DocumentoBo(_repoFactory);
        documento = documentoBo.recoveryById(documento.getId());
        return _planCuentaRepository.recoveryByDocumento(documento);
    }

    public List<PlanCuenta> recoveryForEgreso() throws Exception {
        return _planCuentaRepository.recoveryForEgreso();
    }

    public PlanCuenta recoveryById(int idPlancta) throws Exception {
        return _planCuentaRepository.recoveryByID(idPlancta);
    }

}
