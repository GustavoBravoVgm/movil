package com.ar.vgmsistemas.entity.key;

import java.io.Serializable;

public class PkHojaDetalle implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idHoja;
    private int idSucursal;
    private String idFcnc;
    private String idTipoab;
    private int idPtovta;
    private long idNumdoc;

    public PkHojaDetalle(int idHoja, int idSucursal, String idFcnc, String idTipoab, int idPtovta, long idNumdoc) {
        this.idHoja = idHoja;
        this.idSucursal = idSucursal;
        this.idFcnc = idFcnc;
        this.idTipoab = idTipoab;
        this.idPtovta = idPtovta;
        this.idNumdoc = idNumdoc;
    }

    public PkHojaDetalle() {
    }

    public int getIdHoja() {
        return idHoja;
    }

    public void setIdHoja(int idHoja) {
        this.idHoja = idHoja;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getIdFcnc() {
        return idFcnc;
    }

    public void setIdFcnc(String idFcnc) {
        this.idFcnc = idFcnc;
    }

    public String getIdTipoab() {
        return idTipoab;
    }

    public void setIdTipoab(String idTipoab) {
        this.idTipoab = idTipoab;
    }

    public int getIdPtovta() {
        return idPtovta;
    }

    public void setIdPtovta(int idPtovta) {
        this.idPtovta = idPtovta;
    }

    public Long getIdNumdoc() {
        return idNumdoc;
    }

    public void setIdNumdoc(long idNumdoc) {
        this.idNumdoc = idNumdoc;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return getIdFcnc() + " - " + getIdTipoab() + " - " + getIdPtovta() + " - " + getIdNumdoc() + " ";
    }
}
