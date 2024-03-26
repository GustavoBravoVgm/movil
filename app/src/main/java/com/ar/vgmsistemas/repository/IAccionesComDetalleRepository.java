package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.entity.AccionesComDetalle;
import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.entity.key.PkAccionesComDetalle;
import com.ar.vgmsistemas.entity.key.PkCliente;

import java.util.List;


public interface IAccionesComDetalleRepository extends IGenericRepository<AccionesComDetalle, PkAccionesComDetalle> {
    //List<AccionesComDetalle> recoveryByClienteYOrigen(PkCliente pkCliente, String tiOrigen) throws Exception;
    void updateCaVendidaAccom(VentaDetalle ventaDetalle) throws Exception;

    //void updateCaVendidaYCaMaxima(AccionesComDetalle acd) throws Exception;
    AccionesComDetalle recoveryAccionPorArticulo(VentaDetalle ventaDetalle, PkCliente pkCliente, String origen) throws Exception;

    AccionesComDetalle recoveryAccionPorMarca(VentaDetalle ventaDetalle, PkCliente pkCliente, String origen) throws Exception;

    AccionesComDetalle recoveryAccionPorSubrubro(VentaDetalle ventaDetalle, PkCliente pkCliente, String origen) throws Exception;

    AccionesComDetalle recoveryAccionPorRubro(VentaDetalle ventaDetalle, PkCliente pkCliente, String origen) throws Exception;

    AccionesComDetalle recoveryAccionPorNegocio(VentaDetalle ventaDetalle, PkCliente pkCliente, String origen) throws Exception;

    List<AccionesComDetalle> recoveryAllPorArticulo(Articulo articulo, PkCliente pkCliente, String origen) throws Exception;

    List<AccionesComDetalle> recoveryAllPorMarca(Articulo articulo, PkCliente pkCliente, String origen) throws Exception;

    List<AccionesComDetalle> recoveryAllPorSubrubro(Articulo articulo, PkCliente pkCliente, String origen) throws Exception;

    List<AccionesComDetalle> recoveryAllPorRubro(Articulo articulo, PkCliente pkCliente, String origen) throws Exception;

    List<AccionesComDetalle> recoveryAllPorNegocio(Articulo articulo, PkCliente pkCliente, String origen) throws Exception;

}
