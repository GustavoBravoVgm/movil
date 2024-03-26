package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.VendedorObjetivoBd;
import com.ar.vgmsistemas.entity.VendedorObjetivo;

import java.util.List;

public interface IVendedorObjetivoRepository {
    List<VendedorObjetivo> recoveryAll() throws Exception;

    VendedorObjetivo mappingToDto(VendedorObjetivoBd vendedorObjetivoBd) throws Exception;

}
