package com.ar.vgmsistemas.entity.key;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PkComercioLogin implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("idSucursal")
    private int idSucursal;

    @SerializedName("idCliente")
    private int idCliente;

    @SerializedName("idComercio")
    private int idComercio;

    public PkComercioLogin(int idSucursal, int idCliente, int idComercio) {
        this.idSucursal = idSucursal;
        this.idCliente = idCliente;
        this.idComercio = idComercio;
    }

    public PkComercioLogin() {
    }

    public int getIdSucursal() {
        return this.idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public int getIdCliente() {
        return this.idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdComercio() {
        return this.idComercio;
    }

    public void setIdComercio(int idComercio) {
        this.idComercio = idComercio;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + (int) (this.idCliente ^ (this.idCliente >>> 32));
        result = prime * result
                + (int) (this.idComercio ^ (this.idComercio >>> 32));
        result = prime * result
                + (int) (this.idSucursal ^ (this.idSucursal >>> 32));
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
        PkComercioLogin other = (PkComercioLogin) obj;
        if (this.idCliente != other.idCliente)
            return false;
        if (this.idComercio != other.idComercio)
            return false;
        if (this.idSucursal != other.idSucursal)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return this.idSucursal + "-" + this.idCliente + "-" + this.idComercio;
    }

}
