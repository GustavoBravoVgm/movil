package com.ar.vgmsistemas.database.dao.entity.key;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import java.io.Serializable;

public class PkCuentaCorrienteBd implements Serializable {
    @Ignore
    private static final long serialVersionUID = 1L;

    @ColumnInfo(name = "id_fcnc")
    @NonNull
    private String idDocumento;

    @ColumnInfo(name = "id_tipoab")
    @NonNull
    private String idLetra;

    @ColumnInfo(name = "id_ptovta")
    private int puntoVenta;

    @ColumnInfo(name = "id_numdoc")
    private long idNumeroDocumento;

    @ColumnInfo(name = "nu_cuota")
    private int cuota;

    public PkCuentaCorrienteBd() {
    }

    public void setCuota(int cuota) {
        this.cuota = cuota;
    }

    public int getCuota() {
        return cuota;
    }

    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getIdDocumento() {
        return idDocumento;
    }

    public void setIdLetra(String idLetra) {
        this.idLetra = idLetra;
    }

    public String getIdLetra() {
        return idLetra;
    }

    public void setPuntoVenta(int puntoVenta) {
        this.puntoVenta = puntoVenta;
    }

    public int getPuntoVenta() {
        return puntoVenta;
    }

    public void setIdNumeroDocumento(long idNumeroDocumento) {
        this.idNumeroDocumento = idNumeroDocumento;
    }

    public long getIdNumeroDocumento() {
        return idNumeroDocumento;
    }

    @Override
    public String toString() {
        return idDocumento + idLetra + puntoVenta + "-" + idNumeroDocumento;
    }
}
