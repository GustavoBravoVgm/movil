package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.MotivoAutorizacion;
import com.ar.vgmsistemas.repository.IMotivosAutorizacionRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.List;

public class MotivoAutorizacionBo {
    private final IMotivosAutorizacionRepository _motivoAutorizacionRepository;

    public MotivoAutorizacionBo(RepositoryFactory repoFactory) {
        _motivoAutorizacionRepository = repoFactory.getMotivoAutorizacionRepository();
    }

    public List<MotivoAutorizacion> recoveryAll() throws Exception {
        return _motivoAutorizacionRepository.recoveryAll();
    }

    public List<MotivoAutorizacion> recoveryForCtaCte() throws Exception {
        return _motivoAutorizacionRepository.recoveryForCtaCte();
    }

    public List<MotivoAutorizacion> recoveryForPedidoRentable() throws Exception {
        return _motivoAutorizacionRepository.recoveryForPedidoRentable();
    }

    public MotivoAutorizacion recoveryForPedidoRentableSinAutorizacion() throws Exception {
        return _motivoAutorizacionRepository.recoveryForPedidoRentableSinAutorizacion();
    }
}
