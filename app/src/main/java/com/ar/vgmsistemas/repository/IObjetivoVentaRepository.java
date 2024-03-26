package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.ObjetivoVenta;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.entity.key.PkObjetivoVenta;

import java.util.List;

public interface IObjetivoVentaRepository extends IGenericRepository<ObjetivoVenta, PkObjetivoVenta> {
    List<ObjetivoVenta> recoveryByCliente(Cliente cliente) throws Exception;

    List<ObjetivoVenta> recoveryNoCumplidosByCliente(Cliente cliente) throws Exception;

    int getCantNoCumplidosByCliente(Cliente cliente) throws Exception;

    List<ObjetivoVenta> recoveryByClienteArticulo(Cliente cliente, Articulo articulo) throws Exception;

    void updateCantidadVendida(VentaDetalle ventaDetalle, Cliente cliente, int signo) throws Exception;
}
