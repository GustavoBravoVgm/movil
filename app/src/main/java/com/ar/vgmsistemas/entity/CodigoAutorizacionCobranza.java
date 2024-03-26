package com.ar.vgmsistemas.entity;

import com.ar.vgmsistemas.entity.key.PkCodigoAutCobranza;

import java.io.Serializable;

public class CodigoAutorizacionCobranza implements Serializable {

    private static final long serialVersionUID = 814140212491067324L;
    private PkCodigoAutCobranza id;

    private String codigo;

    public PkCodigoAutCobranza getId() {
        return id;
    }

    public void setId(PkCodigoAutCobranza id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public CodigoAutorizacionCobranza(PkCodigoAutCobranza id, String codigo) {
        this.id = id;
        this.codigo = codigo;
    }

    public CodigoAutorizacionCobranza() {
    }
}
