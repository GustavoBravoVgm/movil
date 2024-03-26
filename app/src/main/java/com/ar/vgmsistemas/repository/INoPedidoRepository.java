package com.ar.vgmsistemas.repository;

import android.location.Location;

import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.NoPedido;

import java.util.List;

public interface INoPedidoRepository extends IGenericRepository<NoPedido, Long> {

    int getCantidadPedidosSinEnviar() throws Exception;

    void updateFechaSincronizacion(NoPedido noPedido) throws Exception;

    List<NoPedido> recoveryPedidosSinEnviar() throws Exception;

    int getCantidadNoPedidos(Cliente cliente) throws Exception;

    void createNoPedidoTransaction(NoPedido noPedido, Location location) throws Exception;

}
