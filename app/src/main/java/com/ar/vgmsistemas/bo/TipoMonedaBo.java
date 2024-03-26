package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.TipoMoneda;
import com.ar.vgmsistemas.repository.ITipoMonedaRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.List;

public class TipoMonedaBo {

    private final ITipoMonedaRepository _tipoMonedaRepository;

    public TipoMonedaBo(RepositoryFactory repoFactory) {
        this._tipoMonedaRepository = repoFactory.getTipoMonedaRepository();
    }

    public List<TipoMoneda> recoveryAll() throws Exception {
        List<TipoMoneda> tiposMonedas = _tipoMonedaRepository.recoveryAll();
        return tiposMonedas;
    }

}
