package com.ar.vgmsistemas.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Articulo implements Serializable {

    private static final long serialVersionUID = -5630607464392810518L;

    @SerializedName("id")
    private int id;

    @SerializedName("subrubro")
    private Subrubro subrubro;

    @SerializedName("marca")
    private Marca marca;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("tasaIva")
    private double tasaIva;

    @SerializedName("codigoEmpresa")
    private String codigoEmpresa;

    @SerializedName("stock")
    private double stock;

    private String snPromocion;

    private boolean promocion;

    @SerializedName("impuestoInterno")
    private double impuestoInterno;

    private boolean isCritico;

    @SerializedName("snPesable")
    private boolean isPesable;

    @SerializedName("snUnidad")
    private String snUnidad;

    @SerializedName("tasaImpuestoInterno")
    private double tasaImpuestoInterno;

    private double cantidadKilos;

    private double precioBase;

    private Proveedor proveedor;

    private double precioCosto;

    private double tasaCosto;

    private String snKit;

    private boolean isAlcohol;

    @SerializedName("unidadPorBulto")
    private Double unidadPorBulto;


    private int cantidadVendida;

    private List<PromocionDetalle> detallePromocion;

    private List<ListaPrecioDetalle> listaPreciosDetalle = new ArrayList<>();

    private Date fechaUltimaVenta;

    private double precioListaPorDefecto;

    /*Getters-Setters*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Subrubro getSubrubro() {
        return subrubro;
    }

    public void setSubrubro(Subrubro subrubro) {
        this.subrubro = subrubro;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null) descripcion = "";
        this.descripcion = descripcion;
    }

    public double getTasaIva() {
        return tasaIva;
    }

    public void setTasaIva(double tasaIva) {
        this.tasaIva = tasaIva;
    }

    public String getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(String codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public String getSnPromocion() {
        return snPromocion;
    }

    public void setSnPromocion(String snPromocion) {
        this.setPromocion(snPromocion != null && snPromocion.equalsIgnoreCase("S"));
        if (snPromocion == null) snPromocion = "N";
        this.snPromocion = snPromocion;
    }

    public boolean isPromocion() {
        return promocion;
    }

    public void setPromocion(boolean promocion) {
        this.promocion = promocion;
    }

    public double getImpuestoInterno() {
        return impuestoInterno;
    }

    public void setImpuestoInterno(double impuestoInterno) {
        this.impuestoInterno = impuestoInterno;
    }

    public boolean isCritico() {
        return isCritico;
    }

    public void setCritico(boolean critico) {
        isCritico = critico;
    }

    public boolean isPesable() {
        return isPesable;
    }

    public void setPesable(boolean pesable) {
        isPesable = pesable;
    }

    public String getSnUnidad() {
        return snUnidad;
    }

    public void setSnUnidad(String snUnidad) {
        this.snUnidad = snUnidad;
    }

    public double getTasaImpuestoInterno() {
        return tasaImpuestoInterno;
    }

    public void setTasaImpuestoInterno(double tasaImpuestoInterno) {
        this.tasaImpuestoInterno = tasaImpuestoInterno;
    }

    public double getCantidadKilos() {
        return cantidadKilos;
    }

    public void setCantidadKilos(double cantidadKilos) {
        this.cantidadKilos = cantidadKilos;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public double getPrecioCosto() {
        return precioCosto;
    }

    public void setPrecioCosto(double precioCosto) {
        this.precioCosto = precioCosto;
    }

    public double getTasaCosto() {
        return tasaCosto;
    }

    public void setTasaCosto(double tasaCosto) {
        this.tasaCosto = tasaCosto;
    }

    public String getSnKit() {
        return snKit;
    }

    public void setSnKit(String snKit) {
        this.snKit = snKit;
    }

    public boolean getIsAlcohol() {
        return isAlcohol;
    }

    public void setAlcohol(boolean alcohol) {
        isAlcohol = alcohol;
    }

    public Double getUnidadPorBulto() {
        return unidadPorBulto;
    }

    public void setUnidadPorBulto(Double unidadPorBulto) {
        this.unidadPorBulto = unidadPorBulto;
    }

    public int getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(int cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    public List<PromocionDetalle> getDetallePromocion() {
        return detallePromocion;
    }

    public void setDetallePromocion(List<PromocionDetalle> detallePromocion) {
        this.detallePromocion = detallePromocion;
    }

    public List<ListaPrecioDetalle> getListaPreciosDetalle() {
        return listaPreciosDetalle;
    }

    public void setListaPreciosDetalle(List<ListaPrecioDetalle> listaPrecios) {
        this.listaPreciosDetalle = listaPrecios;
    }

    public Date getFechaUltimaVenta() {
        return fechaUltimaVenta;
    }

    public void setFechaUltimaVenta(Date fechaUltimaVenta) {
        this.fechaUltimaVenta = fechaUltimaVenta;
    }

    public double getPrecioListaPorDefecto() {
        return precioListaPorDefecto;
    }

    public void setPrecioListaPorDefecto(double precioListaPorDefecto) {
        this.precioListaPorDefecto = precioListaPorDefecto;
    }

}
