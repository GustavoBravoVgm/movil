package com.ar.vgmsistemas.database.dao.entity.key;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import java.io.Serializable;

public class PkGrupoClientesDetalleBd implements Serializable {

    @Ignore
    private static final long serialVersionUID = 5966053595981650798L;

    @ColumnInfo(name = "id_grupo_clie")
    private int idGrupoClie;

    @ColumnInfo(name = "id_grupo_clie_detalle")
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
