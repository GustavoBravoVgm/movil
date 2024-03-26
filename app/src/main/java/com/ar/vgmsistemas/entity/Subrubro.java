package com.ar.vgmsistemas.entity;

import com.ar.vgmsistemas.entity.key.PkSubrubro;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Subrubro implements Serializable {
    private static final long serialVersionUID = 2788302087750143479L;

    @SerializedName("idSubrubro")
    private PkSubrubro id;

    @SerializedName("descripcionSubrubro")
    private String descripcion;

    @SerializedName("rubro")
    private Rubro rubro;

    public PkSubrubro getId() {
        return this.id;
    }

    public void setId(PkSubrubro id) {
        this.id = id;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Rubro getRubro() {
        return this.rubro;
    }

    public void setRubro(Rubro rubro) {
        this.rubro = rubro;
    }


    @Override
    public String toString() {
        return this.descripcion;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((descripcion == null) ? 0 : descripcion.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((rubro == null) ? 0 : rubro.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Subrubro other = (Subrubro) obj;
        if (descripcion == null) {
            if (other.descripcion != null)
                return false;
        } else if (!descripcion.equals(other.descripcion))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (rubro == null) {
            if (other.rubro != null)
                return false;
        } else if (!rubro.equals(other.rubro))
            return false;
        return true;
    }

    public Subrubro(PkSubrubro id, String descripcion, Rubro rubro) {
        this.id = id;
        this.descripcion = descripcion;
        this.rubro = rubro;
    }

    public Subrubro() {
    }
}
