package com.ar.vgmsistemas.database.dao.entity.key;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import java.io.Serializable;

public class PkCompraBd implements Serializable {

    @Ignore
    private static final long serialVersionUID = 1L;

    @ColumnInfo(name = "id_proveedor")
    @NonNull
    private Integer idProveedor;

    @ColumnInfo(name = "id_fcncnd")
    @NonNull
    private String idFcncnd;

    @ColumnInfo(name = "id_ptovta")
    @NonNull
    private Integer idPuntoVenta;

    @ColumnInfo(name = "id_numero")
    private int idNumero;

    public PkCompraBd() {

    }

    public Integer getIdProveedor() {
        return idProveedor;
    }


    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }


    public String getIdFcncnd() {
        return idFcncnd;
    }


    public void setIdFcncnd(String idFcncnd) {
        this.idFcncnd = idFcncnd;
    }


    public Integer getIdPuntoVenta() {
        return idPuntoVenta;
    }


    public void setIdPuntoVenta(Integer idPuntoVenta) {
        this.idPuntoVenta = idPuntoVenta;
    }


    public int getIdNumero() {
        return idNumero;
    }


    public void setIdNumero(int idNumero) {
        this.idNumero = idNumero;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PkCompraBd pkCompra = (PkCompraBd) o;

        if (idNumero != pkCompra.idNumero) return false;
        if (!idProveedor.equals(pkCompra.idProveedor)) return false;
        if (!idFcncnd.equals(pkCompra.idFcncnd)) return false;
        return idPuntoVenta.equals(pkCompra.idPuntoVenta);

    }

    @Override
    public int hashCode() {
        int result = idProveedor.hashCode();
        result = 31 * result + idFcncnd.hashCode();
        result = 31 * result + idPuntoVenta.hashCode();
        result = 31 * result + idNumero;
        return result;
    }

    @Override
    public String toString() {
        return getIdFcncnd() + "-" + getIdPuntoVenta() + "-" + getIdNumero() + "-" + getIdProveedor();
    }
}
