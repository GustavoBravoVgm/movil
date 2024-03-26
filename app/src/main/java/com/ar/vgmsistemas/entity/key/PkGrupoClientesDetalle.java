package com.ar.vgmsistemas.entity.key;

import java.io.Serializable;

public class PkGrupoClientesDetalle implements Serializable {
    private static final long serialVersionUID = 5966053595981650798L;

    private int idGrupoClie;

    private int idGrupoClieDetalle;

    public int getIdGrupoClie() {
        return idGrupoClie;
    }

    public void setIdGrupoClie(int idGrupoClie) {
        this.idGrupoClie = idGrupoClie;
    }

    public int getIdGrupoClieDetalle() {
        return idGrupoClieDetalle;
    }

    public void setIdGrupoClieDetalle(int idGrupoClieDetalle) {
        this.idGrupoClieDetalle = idGrupoClieDetalle;
    }
}
