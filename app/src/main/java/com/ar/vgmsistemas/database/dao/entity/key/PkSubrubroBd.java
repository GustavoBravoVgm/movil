package com.ar.vgmsistemas.database.dao.entity.key;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import java.io.Serializable;

public class PkSubrubroBd implements Serializable {
    @Ignore
    private static final long serialVersionUID = 3497239050731647740L;

    @ColumnInfo(name = "id_subrubro")
    private int idSubrubro;

    @ColumnInfo(name = "id_negocio")
    private int idNegocio;

    @ColumnInfo(name = "id_segmento")
    private int idRubro;

    public int getIdSubrubro() {
        return idSubrubro;
    }

    public void setIdSubrubro(int idSubrubro) {
        this.idSubrubro = idSubrubro;
    }

    public int getIdNegocio() {
        return idNegocio;
    }

    public void setIdNegocio(int idNegocio) {
        this.idNegocio = idNegocio;
    }

    public int getIdRubro() {
        return idRubro;
    }

    public void setIdRubro(int idRubro) {
        this.idRubro = idRubro;
    }
}
