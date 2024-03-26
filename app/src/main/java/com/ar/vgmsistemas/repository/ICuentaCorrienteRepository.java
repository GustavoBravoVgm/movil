package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.CuentaCorrienteBd;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.CuentaCorriente;
import com.ar.vgmsistemas.entity.Recibo;
import com.ar.vgmsistemas.entity.key.PkCuentaCorriente;

import java.util.List;


public interface ICuentaCorrienteRepository extends IGenericRepository<CuentaCorriente, PkCuentaCorriente> {
    PkCuentaCorriente create(CuentaCorriente cuentaCorriente, Recibo recibo) throws Exception;

    List<CuentaCorriente> recoveryByRecibo(Recibo recibo) throws Exception;

    List<CuentaCorriente> recoveryByCliente(Cliente cliente) throws Exception;

    double getTotalSaldo(Cliente cliente, String snClienteUnico) throws Exception;

    double getTotalSaldoVencido(Cliente cliente, String snClienteUnico) throws Exception;

    int getNumeroAdelanto() throws Exception;

    CuentaCorriente mappingToDto(CuentaCorrienteBd cuentaCorrienteBd) throws Exception;

}
