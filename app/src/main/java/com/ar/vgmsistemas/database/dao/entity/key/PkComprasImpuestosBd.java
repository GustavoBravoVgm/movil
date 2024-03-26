package com.ar.vgmsistemas.database.dao.entity.key;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import java.io.Serializable;

public class PkComprasImpuestosBd implements Serializable {

    @Ignore
    private static final long serialVersionUID = 1L;

    @ColumnInfo(name = "id_proveedor")
    @NonNull
    private Integer idProveedor;

    @ColumnInfo(name = "id_fcncnd")
    @NonNull
    private String idFcncnd;

    @ColumnInfo(name = "id_ptovta")
    private int idPuntoVenta;

    @ColumnInfo(name = "id_numero")
    private int idNumero;

    @ColumnInfo(name = "id_impuesto")
    private int idImpuesto;

    @ColumnInfo(name = "id_tipoab")
    @NonNull
    private String idTipoab;

    @ColumnInfo(name = "id_secuencia")
    private int idSecuencia;

    public PkComprasImpuestosBd() {

    }

    @NonNull
    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(@NonNull Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    @NonNull
    public String getIdFcncnd() {
        return idFcncnd;
    }

    public void setIdFcncnd(@NonNull String idFcncnd) {
        this.idFcncnd = idFcncnd;
    }

    public int getIdPuntoVenta() {
        return idPuntoVenta;
    }

    public void setIdPuntoVenta(int idPuntoVenta) {
        this.idPuntoVenta = idPuntoVenta;
    }

    public int getIdNumero() {
        return idNumero;
    }

    public void setIdNumero(int idNumero) {
        this.idNumero = idNumero;
    }

    public int getIdImpuesto() {
        return idImpuesto;
    }

    public void setIdImpuesto(int idImpuesto) {
        this.idImpuesto = idImpuesto;
    }

    @NonNull
    public String getIdTipoab() {
        return idTipoab;
    }

    public void setIdTipoab(@NonNull String idTipoab) {
        this.idTipoab = idTipoab;
    }

    public int getIdSecuencia() {
        return idSecuencia;
    }

    public void setIdSecuencia(int idSecuencia) {
        this.idSecuencia = idSecuencia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PkComprasImpuestosBd that = (PkComprasImpuestosBd) o;

        if (idPuntoVenta != that.idPuntoVenta) return false;
        if (idNumero != that.idNumero) return false;
        if (idImpuesto != that.idImpuesto) return false;
        if (idSecuencia != that.idSecuencia) return false;
        if (!idProveedor.equals(that.idProveedor)) return false;
        if (!idFcncnd.equals(that.idFcncnd)) return false;
        return idTipoab.equals(that.idTipoab);

    }

    @Override
    public int hashCode() {
        int result = idProveedor.hashCode();
        result = 31 * result + idFcncnd.hashCode();
        result = 31 * result + idPuntoVenta;
        result = 31 * result + (int) (idNumero ^ (idNumero >>> 32));
        result = 31 * result + idImpuesto;
        result = 31 * result + idTipoab.hashCode();
        result = 31 * result + idSecuencia;
        return result;
    }
}
