package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.entity.Cheque;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.Entrega;
import com.ar.vgmsistemas.entity.key.PkCheque;

import java.util.List;

public interface IChequeRepository extends IGenericRepository<Cheque, PkCheque> {

    List<Cheque> recoveryByEntrega(Entrega entrega) throws Exception;

    List<Cheque> recoveryByCliente(Cliente cliente) throws Exception;

    void deleteByEntrega(Cheque cheque) throws Exception;


}
