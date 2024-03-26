package com.ar.vgmsistemas.entity;

import java.io.Serializable;

public class Proveedor implements Serializable {
    public static final String TI_PROVEEDOR_GASTO = "G";

    private Integer idProveedor;

    private String deProveedor;

    private Integer idPlancta;

    private Integer idSucursal;

    private String tiProveedor;

    private String nuCuit;

    private boolean isRentableEnPedido = true;

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getDeProveedor() {
        return deProveedor;
    }

    public void setDeProveedor(String deProveedor) {
        this.deProveedor = deProveedor;
    }

    public Integer getIdPlancta() {
        return idPlancta;
    }

    public void setIdPlancta(Integer idPlancta) {
        this.idPlancta = idPlancta;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getTiProveedor() {
        return tiProveedor;
    }

    public void setTiProveedor(String tiProveedor) {
        this.tiProveedor = tiProveedor;
    }

    public String getNuCuit() {
        return nuCuit;
    }

    public void setNuCuit(String nuCuit) {
        this.nuCuit = nuCuit;
    }

    @Override
    public String toString() {
        return idProveedor + " - " + deProveedor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Proveedor proveedor = (Proveedor) o;

        if (idProveedor != null ? !idProveedor.equals(proveedor.idProveedor) : proveedor.idProveedor != null)
            return false;
        if (deProveedor != null ? !deProveedor.equals(proveedor.deProveedor) : proveedor.deProveedor != null)
            return false;
        if (idPlancta != null ? !idPlancta.equals(proveedor.idPlancta) : proveedor.idPlancta != null)
            return false;
        if (idSucursal != null ? !idSucursal.equals(proveedor.idSucursal) : proveedor.idSucursal != null)
            return false;
        if (tiProveedor != null ? !tiProveedor.equals(proveedor.tiProveedor) : proveedor.tiProveedor != null)
            return false;
        return nuCuit != null ? nuCuit.equals(proveedor.nuCuit) : proveedor.nuCuit == null;

    }

    @Override
    public int hashCode() {
        int result = idProveedor != null ? idProveedor.hashCode() : 0;
        result = 31 * result + (deProveedor != null ? deProveedor.hashCode() : 0);
        result = 31 * result + (idPlancta != null ? idPlancta.hashCode() : 0);
        result = 31 * result + (idSucursal != null ? idSucursal.hashCode() : 0);
        result = 31 * result + (tiProveedor != null ? tiProveedor.hashCode() : 0);
        result = 31 * result + (nuCuit != null ? nuCuit.hashCode() : 0);
        return result;
    }

    public boolean isRentableEnPedido() {
        return isRentableEnPedido;
    }

    public void setRentableEnPedido(boolean rentableEnPedido) {
        isRentableEnPedido = rentableEnPedido;
    }

    public Proveedor() {
    }

    public Proveedor(Integer idProveedor, String deProveedor, Integer idPlancta, Integer idSucursal, String tiProveedor, String nuCuit) {
        this.idProveedor = idProveedor;
        this.deProveedor = deProveedor;
        this.idPlancta = idPlancta;
        this.idSucursal = idSucursal;
        this.tiProveedor = tiProveedor;
        this.nuCuit = nuCuit;
    }
}
