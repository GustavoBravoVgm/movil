package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.PagoEfectivo;
import com.ar.vgmsistemas.repository.IPagoEfectivoRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

public class PagoEfectivoBo {
    private final IPagoEfectivoRepository _PagoEfectivoRepository;

    public PagoEfectivoBo(RepositoryFactory repositoryFactory) {
        this._PagoEfectivoRepository = repositoryFactory.getPagoEfectivoRepository();
    }

    public void guardar(PagoEfectivo pago) throws Exception {
        _PagoEfectivoRepository.create(pago);
    }

    public void delete(PagoEfectivo pago) throws Exception {
        _PagoEfectivoRepository.delete(pago);
    }

    public void deleteByEntrega(PagoEfectivo pago) throws Exception {
        _PagoEfectivoRepository.deleteByEntrega(pago);
    }

}
