package com.ar.vgmsistemas.entity.key;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PkSubrubro implements Serializable {
    private static final long serialVersionUID = 3497239050731647740L;

    @SerializedName("idSubrubro")
    private int idSubrubro;

    @SerializedName("idNegocio")
    private int idNegocio;

    @SerializedName("idRubro")
    private int idRubro;

    public int getIdNegocio() {
        return this.idNegocio;
    }

    public void setIdNegocio(int idNegocio) {
        this.idNegocio = idNegocio;
    }

    public int getIdRubro() {
        return this.idRubro;
    }

    public void setIdRubro(int idRubro) {
        this.idRubro = idRubro;
    }

    public int getIdSubrubro() {
        return this.idSubrubro;
    }

    public void setIdSubrubro(int idSubrubro) {
        this.idSubrubro = idSubrubro;
    }

    public PkSubrubro() {
    }

    public PkSubrubro(int idSubrubro, int idNegocio, int idRubro) {
        this.idSubrubro = idSubrubro;
        this.idNegocio = idNegocio;
        this.idRubro = idRubro;
    }
}
