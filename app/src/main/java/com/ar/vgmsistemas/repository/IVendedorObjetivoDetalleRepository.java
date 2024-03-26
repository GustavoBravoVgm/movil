package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.entity.VendedorObjetivo;
import com.ar.vgmsistemas.entity.VendedorObjetivoDetalle;

import java.util.List;

public interface IVendedorObjetivoDetalleRepository {
    List<VendedorObjetivoDetalle> recoveryDetalles(VendedorObjetivo objetivo) throws Exception;

}
