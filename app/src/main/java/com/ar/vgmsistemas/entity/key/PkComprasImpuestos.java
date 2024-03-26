package com.ar.vgmsistemas.entity.key;

import java.io.Serializable;

public class PkComprasImpuestos implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idProveedor;

    private String idFcncnd;

    private int idPuntoVenta;

    private int idNumero;

    private int idImpuesto;

    private String idTipoab;

    private int idSecuencia;

    public PkComprasImpuestos() {

    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getIdFcncnd() {
        return idFcncnd;
    }

    public void setIdFcncnd(String idFcncnd) {
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

    public String getIdTipoab() {
        return idTipoab;
    }

    public void setIdTipoab(String idTipoab) {
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

        PkComprasImpuestos that = (PkComprasImpuestos) o;

        if (idPuntoVenta != that.idPuntoVenta) return false;
        if (idNumero != that.idNumero) return false;
        if (idImpuesto != that.idImpuesto) return false;
        if (idSecuencia != that.idSecuencia) return false;
        if (idProveedor != that.idProveedor) return false;
        if (!idFcncnd.equals(that.idFcncnd)) return false;
        return idTipoab.equals(that.idTipoab);

    }

    @Override
    public int hashCode() {
        int result = idProveedor;
        result = 31 * result + idFcncnd.hashCode();
        result = 31 * result + idPuntoVenta;
        result = 31 * result + (int) (idNumero ^ (idNumero >>> 32));
        result = 31 * result + idImpuesto;
        result = 31 * result + idTipoab.hashCode();
        result = 31 * result + idSecuencia;
        return result;
    }
}
