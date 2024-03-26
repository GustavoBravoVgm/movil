package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.RetencionBd;
import com.ar.vgmsistemas.entity.Documento;
import com.ar.vgmsistemas.entity.Entrega;
import com.ar.vgmsistemas.entity.Retencion;
import com.ar.vgmsistemas.entity.key.PkVenta;

import java.util.List;

public interface IRetencionRepository extends IGenericRepository<Retencion, PkVenta> {
    List<Retencion> recoveryByEntrega(Entrega entrega) throws Exception;

    boolean existeRetencion(Documento documento, Long numero, int idCliente);

    Retencion recoveryByID(PkVenta id) throws Exception;

    void deleteByEntrega(Retencion retencion) throws Exception;

    Retencion mappingToDto(RetencionBd retencionBd) throws Exception;
}
