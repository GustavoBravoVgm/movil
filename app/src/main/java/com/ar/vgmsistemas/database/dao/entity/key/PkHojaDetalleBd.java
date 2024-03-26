package com.ar.vgmsistemas.database.dao.entity.key;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import java.io.Serializable;

public class PkHojaDetalleBd implements Serializable {

    @Ignore
    private static final long serialVersionUID = 1L;

    @ColumnInfo(name = "id_hoja")
    private int idHoja;
    @ColumnInfo(name = "id_sucursal")
    private int idSucursal;
    @ColumnInfo(name = "id_fcnc")
    @NonNull
    private String idFcnc;
    @ColumnInfo(name = "id_tipoab")
    @NonNull
    private String idTipoab;
    @ColumnInfo(name = "id_ptovta")
    private int idPtovta;
    @ColumnInfo(name = "id_numdoc")
    private long idNumdoc;

    public PkHojaDetalleBd() {
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

    public long getIdNumdoc() {
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
