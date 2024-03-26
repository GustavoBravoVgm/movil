package com.ar.vgmsistemas.entity;

import com.ar.vgmsistemas.entity.key.PkClienteVendedor;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClienteVendedor implements Serializable {

    @SerializedName("id")
    private PkClienteVendedor id;

    private String snFiltroArticulo;

    private String tiFiltroArticuloSinc;


    public PkClienteVendedor getId() {
        return id;
    }

    public void setId(PkClienteVendedor id) {
        this.id = id;
    }

    public String getSnFiltroArticulo() {
        return snFiltroArticulo;
    }

    public void setSnFiltroArticulo(String snFiltroArticulo) {
        if (snFiltroArticulo == null || snFiltroArticulo.equals("")) snFiltroArticulo = "N";
        this.snFiltroArticulo = snFiltroArticulo;
    }

    public String getTiFiltroArticuloSinc() {
        return tiFiltroArticuloSinc;
    }

    public void setTiFiltroArticuloSinc(String tiFiltroArticuloSinc) {
        if (tiFiltroArticuloSinc == null) tiFiltroArticuloSinc = "";
        this.tiFiltroArticuloSinc = tiFiltroArticuloSinc;
    }

}
