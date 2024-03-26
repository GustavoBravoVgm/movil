package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.MotivoNoPedidoBd;
import com.ar.vgmsistemas.entity.MotivoNoPedido;

public interface IMotivoNoPedidoRepository extends IGenericRepository<MotivoNoPedido, Long> {

    MotivoNoPedido mappingToDto(MotivoNoPedidoBd motivoNoPedidoBd);
}
