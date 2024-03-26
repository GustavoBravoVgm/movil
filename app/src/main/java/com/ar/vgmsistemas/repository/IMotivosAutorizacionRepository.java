package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.MotivoAutorizacionBd;
import com.ar.vgmsistemas.entity.MotivoAutorizacion;

import java.util.List;

public interface IMotivosAutorizacionRepository extends IGenericRepository<MotivoAutorizacion, Long> {

    List<MotivoAutorizacion> recoveryForCtaCte() throws Exception;

    List<MotivoAutorizacion> recoveryForPedidoRentable() throws Exception;

    MotivoAutorizacion recoveryForPedidoRentableSinAutorizacion() throws Exception;

    MotivoAutorizacion mappingToDto(MotivoAutorizacionBd motivoAutorizacionBd);
}
