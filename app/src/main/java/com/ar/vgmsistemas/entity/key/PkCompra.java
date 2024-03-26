package com.ar.vgmsistemas.entity.key;

import java.io.Serializable;

public class PkCompra implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idProveedor;

    private String idFcncnd;

    private Integer idPuntoVenta;

    private int idNumero;

    public PkCompra() {

    }

    public PkCompra(Integer idProveedor, String idFcncnd, Integer idPuntoVenta, int idNumero) {
        this.idProveedor = idProveedor;
        this.idFcncnd = idFcncnd;
        this.idPuntoVenta = idPuntoVenta;
        this.idNumero = idNumero;
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

        PkCompra pkCompra = (PkCompra) o;

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
