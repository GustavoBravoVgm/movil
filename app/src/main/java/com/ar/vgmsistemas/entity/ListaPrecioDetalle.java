package com.ar.vgmsistemas.entity;

import com.ar.vgmsistemas.entity.key.PkListaPrecioDetalle;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class ListaPrecioDetalle implements Serializable {

    private static final long serialVersionUID = -3096719252632833846L;

    @SerializedName("id")
    private PkListaPrecioDetalle id;

    @SerializedName("precio")
    private double precioFinal;

    private double precioSinIva;

    private double caLista;

    private double caVendido;

    private String snMovil;

    private Date fechaVigenciaDesde;

    private Date fechaVigenciaHasta;

    private boolean isTodosLosVendedores;

    private double tasaDtoProveedor;

    private double tasaDtoCliente;

    private boolean isRentableXEmpresa;

    private String nivelRentabilidadEmpresa;

    private boolean isRentableXProveedor;

    private String nivelRentabilidadProveedor;

    private String stockPorLista;

    private double cantidadPedida;

    //
    private Articulo articulo;

    //
    private ListaPrecio listaPrecio;

    public Articulo getArticulo() {
        return this.articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public double getPrecioFinal() {
        return this.precioFinal;
    }

    public void setPrecioFinal(double precioFinal) {
        this.precioFinal = precioFinal;
    }


    public double getPrecioSinIva() {
        return precioSinIva;
    }

    public void setPrecioSinIva(double precioSinIva) {
        this.precioSinIva = precioSinIva;
    }

    public PkListaPrecioDetalle getId() {
        return this.id;
    }

    public void setId(PkListaPrecioDetalle id) {
        this.id = id;
    }

    public ListaPrecio getListaPrecio() {
        return this.listaPrecio;
    }

    public void setListaPrecio(ListaPrecio listaPrecio) {
        this.listaPrecio = listaPrecio;
    }

    public String getSnMovil() {
        return snMovil;
    }

    public void setSnMovil(String snMovil) {
        this.snMovil = snMovil;
    }

    @Override
    public String toString() {
        if (this.listaPrecio != null) {
            return this.listaPrecio.getDescripcion();
        } else {
            return "ListaPrecioDetalle{" +
                    "_id=" + id +
                    ", _articulo=" + articulo +
                    ", _precioFinal=" + precioFinal +
                    ", _precioSinIva=" + precioSinIva +
                    //", _listaPrecio=" + _listaPrecio +
                    ", snMovil='" + snMovil + '\'' +
                    ", fechaVigenciaDesde=" + fechaVigenciaDesde +
                    ", fechaVigenciaHasta=" + fechaVigenciaHasta +
                    ", isRentableXEmpresa=" + isRentableXEmpresa +
                    ", nivelRentabilidadEmpresa='" + nivelRentabilidadEmpresa + '\'' +
                    ", isRentableXProveedor=" + isRentableXProveedor +
                    ", nivelRentabilidadProveedor='" + nivelRentabilidadProveedor + '\'' +
                    '}';
        }
    }

    public Date getFechaVigenciaDesde() {
        return fechaVigenciaDesde;
    }

    public void setFechaVigenciaDesde(Date fechaVigenciaDesde) {
        this.fechaVigenciaDesde = fechaVigenciaDesde;
    }

    public Date getFechaVigenciaHasta() {
        return fechaVigenciaHasta;
    }

    public void setFechaVigenciaHasta(Date fechaVigenciaHasta) {
        this.fechaVigenciaHasta = fechaVigenciaHasta;
    }

    public boolean isRentableXEmpresa() {
        return isRentableXEmpresa;
    }

    public void setRentableXEmpresa(boolean rentableXEmpresa) {
        isRentableXEmpresa = rentableXEmpresa;
    }

    public boolean isRentableXProveedor() {
        return isRentableXProveedor;
    }

    public void setRentableXProveedor(boolean rentableXProveedor) {
        isRentableXProveedor = rentableXProveedor;
    }

    public String getNivelRentabilidadEmpresa() {
        return nivelRentabilidadEmpresa;
    }

    public void setNivelRentabilidadEmpresa(String nivelRentabilidadEmpresa) {
        this.nivelRentabilidadEmpresa = nivelRentabilidadEmpresa;
    }

    public String getNivelRentabilidadProveedor() {
        return nivelRentabilidadProveedor;
    }

    public void setNivelRentabilidadProveedor(String nivelRentabilidadProveedor) {
        this.nivelRentabilidadProveedor = nivelRentabilidadProveedor;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListaPrecioDetalle that = (ListaPrecioDetalle) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }


    public String getStockPorLista() {
        return stockPorLista;
    }

    public void setStockPorLista(String stockPorLista) {
        this.stockPorLista = stockPorLista;
    }


    public double getCantidadPedida() {
        return cantidadPedida;
    }

    public void setCantidadPedida(double cantidadPedida) {
        this.cantidadPedida = cantidadPedida;
    }

    public boolean getIsTodosLosVendedores() {
        return isTodosLosVendedores;
    }

    public void setIsTodosLosVendedores(boolean isTodosLosVendedores) {
        this.isTodosLosVendedores = isTodosLosVendedores;
    }

    public double getTasaDtoProveedor() {
        return tasaDtoProveedor;
    }

    public void setTasaDtoProveedor(double tasaDtoProveedor) {
        this.tasaDtoProveedor = tasaDtoProveedor;
    }

    public double getTasaDtoCliente() {
        return tasaDtoCliente;
    }

    public void setTasaDtoCliente(double tasaDtoCliente) {
        this.tasaDtoCliente = tasaDtoCliente;
    }

    public double getCaLista() {
        return caLista;
    }

    public void setCaLista(double caLista) {
        this.caLista = caLista;
    }

    public double getCaVendido() {
        return caVendido;
    }

    public void setCaVendido(double caVendido) {
        this.caVendido = caVendido;
    }

    public boolean isTodosLosVendedores() {
        return isTodosLosVendedores;
    }

    public void setTodosLosVendedores(boolean todosLosVendedores) {
        isTodosLosVendedores = todosLosVendedores;
    }
}
