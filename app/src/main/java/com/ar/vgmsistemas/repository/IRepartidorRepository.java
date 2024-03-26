package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.RepartidorBd;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.Repartidor;
import com.ar.vgmsistemas.entity.Venta;

public interface IRepartidorRepository extends IGenericRepository<Repartidor, Integer> {

    Repartidor recoveryByCliente(Cliente cliente) throws Exception;

    Repartidor recoveryByVenta(Venta venta) throws Exception;

    Repartidor mappingToDto(RepartidorBd repartidorBd) throws Exception;

}
