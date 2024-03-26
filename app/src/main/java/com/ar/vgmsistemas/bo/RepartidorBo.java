package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.Repartidor;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.repository.IRepartidorRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.List;

public class RepartidorBo {

    private final IRepartidorRepository _repartidorRepository;

    public RepartidorBo(RepositoryFactory repoFactory) {
        _repartidorRepository = repoFactory.getRepartidorRepository();
    }

    public Repartidor recoveryByCliente(Cliente cliente) throws Exception {
        Repartidor repartidor = _repartidorRepository.recoveryByCliente(cliente);
        return repartidor;
    }

    public List<Repartidor> recoveryAll() throws Exception {
        return _repartidorRepository.recoveryAll();
    }

    public Repartidor recoveryByVenta(Venta venta) throws Exception {
        return _repartidorRepository.recoveryByVenta(venta);
    }

}
