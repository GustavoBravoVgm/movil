package com.ar.vgmsistemas.database.dao.entity.key;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import java.io.Serializable;

public class PkChequeBd implements Serializable {
    @Ignore
    private static final long serialVersionUID = -7590950414620242872L;

    @ColumnInfo(name = "id_bancogirado_cheque")
    private int idBanco;

    @ColumnInfo(name = "id_cheque")
    private long numeroCheque;

    @ColumnInfo(name = "id_postal")
    private int idPostal;

    @ColumnInfo(name = "id_sucbanco")
    private int sucursal;

    @ColumnInfo(name = "id_nrocuenta")
    private long nroCuenta;

    public int getIdBanco() {
        return this.idBanco;
    }

    public void setIdBanco(int idBanco) {
        this.idBanco = idBanco;
    }

    public long getNumeroCheque() {
        return this.numeroCheque;
    }

    public void setNumeroCheque(long numeroCheque) {
        this.numeroCheque = numeroCheque;
    }

    public int getIdPostal() {
        return this.idPostal;
    }

    public void setIdPostal(int idPostal) {
        this.idPostal = idPostal;
    }

    public long getNroCuenta() {
        return nroCuenta;
    }

    public void setNroCuenta(long nroCuenta) {
        this.nroCuenta = nroCuenta;
    }

    public int getSucursal() {
        return sucursal;
    }

    public void setSucursal(int sucursal) {
        this.sucursal = sucursal;
    }

    public PkChequeBd() {
    }
}
