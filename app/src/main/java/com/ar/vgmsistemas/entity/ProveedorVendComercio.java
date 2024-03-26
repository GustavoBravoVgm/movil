package com.ar.vgmsistemas.entity;

import com.ar.vgmsistemas.entity.key.PkProveedorVendComercio;

import java.io.Serializable;

public class ProveedorVendComercio implements Serializable {
    private static final long serialVersionUID = 3593807007462447749L;

    private PkProveedorVendComercio id;

    public PkProveedorVendComercio getId() {
        return id;
    }

    public void setId(PkProveedorVendComercio id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProveedorVendComercio that = (ProveedorVendComercio) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

}
