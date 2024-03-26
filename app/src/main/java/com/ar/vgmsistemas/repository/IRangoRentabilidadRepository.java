package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.entity.RangoRentabilidad;
import com.ar.vgmsistemas.entity.key.PkCliente;

import java.util.List;

public interface IRangoRentabilidadRepository extends IGenericRepository<RangoRentabilidad, Integer> {

    List<Integer> recoveryProveedoresPR() throws Exception;

    List<RangoRentabilidad> recoveryRangosByPIdProveedor(int idProveedor, PkCliente pkCliente) throws Exception;

    List<RangoRentabilidad> recoveryRangosBySucursal(int IdSucursal, PkCliente pkCliente) throws Exception;

}
