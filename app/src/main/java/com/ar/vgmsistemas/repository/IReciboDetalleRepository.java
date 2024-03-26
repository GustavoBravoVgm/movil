package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.entity.Recibo;
import com.ar.vgmsistemas.entity.ReciboDetalle;

import java.util.List;

public interface IReciboDetalleRepository extends IGenericRepository<ReciboDetalle, Integer> {

    List<ReciboDetalle> recoveryByRecibo(Recibo recibo) throws Exception;
}