package com.ar.vgmsistemas.entity;

import com.ar.vgmsistemas.entity.key.PkGrupoClientesDetalle;


public class GrupoClientesDetalle {
    private PkGrupoClientesDetalle id;

    private Cliente cliente;

    public PkGrupoClientesDetalle getId() {
        return id;
    }

    public void setId(PkGrupoClientesDetalle id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}
