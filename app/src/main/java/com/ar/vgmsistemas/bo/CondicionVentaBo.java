package com.ar.vgmsistemas.bo;


import com.ar.vgmsistemas.entity.CondicionVenta;
import com.ar.vgmsistemas.repository.ICondicionVentaRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.List;

public class CondicionVentaBo {

    private final ICondicionVentaRepository _condicionVentaRepository;
    private List<CondicionVenta> condicionesVenta;

    public CondicionVentaBo(RepositoryFactory repoFactory) {
        _condicionVentaRepository = repoFactory.getCondicionVentaRepository();
    }

    public List<CondicionVenta> recoveryAll() throws Exception {
        this.condicionesVenta = _condicionVentaRepository.recoveryAll();
        return this.condicionesVenta;
    }

    public CondicionVenta getCondicionVentaContado() throws Exception {
        CondicionVenta condicionVentaContado = null;
        if (this.condicionesVenta == null) {
            recoveryAll();
        }
        for (CondicionVenta condicionVenta : condicionesVenta) {
            if (!condicionVenta.isCuentaCorriente()) {
                condicionVentaContado = condicionVenta;
                break;
            }
        }
        return condicionVentaContado;
    }

    public CondicionVenta getCondicionById(String id) throws Exception {
        return _condicionVentaRepository.recoveryByID(id);
    }
}
