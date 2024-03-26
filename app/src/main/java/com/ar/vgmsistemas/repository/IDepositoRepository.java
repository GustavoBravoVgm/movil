package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.entity.Deposito;
import com.ar.vgmsistemas.entity.Entrega;

import java.util.List;

public interface IDepositoRepository extends IGenericRepository<Deposito, Long> {

    List<Deposito> recoveryByEntrega(Entrega entrega) throws Exception;

    void deleteByEntrega(Deposito entity) throws Exception;

}
