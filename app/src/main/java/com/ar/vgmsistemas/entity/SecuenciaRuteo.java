package com.ar.vgmsistemas.entity;

import com.ar.vgmsistemas.entity.key.PkSecuenciaRuteo;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SecuenciaRuteo implements Serializable {

    private static final long serialVersionUID = -2211604778129302127L;
    @SerializedName("PKSecuenciaRuteo")
    private PkSecuenciaRuteo id;

    @SerializedName("cliente")
    private Cliente cliente;

    @SerializedName("dia")
    private int dia;

    @SerializedName("numeroOrden")
    private int numeroOrden;

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getNumeroOrden() {
        return this.numeroOrden;
    }

    public void setNumeroOrden(int numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    public int getDia() {
        return this.dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public PkSecuenciaRuteo getId() {
        return this.id;
    }

    public String getIdString() {
        return (String.valueOf(getId().getIdCliente()) + getId().getIdComercio() + getId().getIdSucursal() + getId().getIdVendedor());
    }

    public void setId(PkSecuenciaRuteo id) {
        this.id = id;
    }

}
