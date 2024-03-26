package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.entity.Entrega;
import com.ar.vgmsistemas.entity.PagoEfectivo;

import java.util.List;

public interface IPagoEfectivoRepository extends IGenericRepository<PagoEfectivo, Long> {

    public Long create(PagoEfectivo entity) throws Exception;

    public PagoEfectivo recoveryByID(Long id) throws Exception;

    public List<PagoEfectivo> recoveryAll() throws Exception;

    public void update(PagoEfectivo entity) throws Exception;

    public void delete(PagoEfectivo entity) throws Exception;

    public void delete(Long id) throws Exception;

    public List<PagoEfectivo> recoveryByEntrega(Entrega entrega) throws Exception;

    public void deleteByEntrega(PagoEfectivo entity) throws Exception;


}
