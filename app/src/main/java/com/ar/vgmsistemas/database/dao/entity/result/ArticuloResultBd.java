package com.ar.vgmsistemas.database.dao.entity.result;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

import java.io.Serializable;

public class ArticuloResultBd implements Serializable {
    @ColumnInfo(name = "id_articulos")
    private int id;

    @ColumnInfo(name = "id_negocio")
    private int idNegocio;

    @ColumnInfo(name = "id_segmento")
    private int idRubro;

    @ColumnInfo(name = "id_subrubro")
    private int idSubrubro;

    @ColumnInfo(name = "id_linea")
    private int idLinea;

    @ColumnInfo(name = "de_articulos")
    @NonNull
    private String descripcion;

    @ColumnInfo(name = "ta_iva")
    private double tasaIva;

    @ColumnInfo(name = "id_empresa")
    private String codigoEmpresa;

    @ColumnInfo(name = "stock")
    private Double stock;

    @ColumnInfo(name = "ca_stock")
    private Double caStock;

    @ColumnInfo(name = "sn_promocion")
    private String snPromocion;

    @ColumnInfo(name = "pr_imp_interno", defaultValue = "0")
    private double impuestoInterno;

    @ColumnInfo(name = "sn_critico")
    private String snCritico;

    @ColumnInfo(name = "sn_pesable")
    private String snPesable;

    @ColumnInfo(name = "sn_unidad")
    private String snUnidad;

    @ColumnInfo(name = "ta_imp_interno")
    private Double tasaImpuestoInterno;

    @ColumnInfo(name = "ca_kilos", defaultValue = "0")
    private double cantidadKilos;

    @ColumnInfo(name = "pr_arcor_a")
    private Double precioBase;

    @ColumnInfo(name = "id_proveedor")
    private int idProveedor;

    @ColumnInfo(name = "pr_costo")
    private Double precioCosto;

    @ColumnInfo(name = "ta_costo")
    private Double tasaCosto;

    @ColumnInfo(name = "sn_kit")
    private String snKit;

    @ColumnInfo(name = "sn_alcohol")
    private String snAlcohol;

    @ColumnInfo(name = "ca_unidxbulto", defaultValue = "1.00")
    private double unidadPorBulto;

    /*rubro*/
    @ColumnInfo(name = "de_negocio")
    private String descripcionNegocio;

    /*rubro*/
    @ColumnInfo(name = "de_segmento")
    private String descripcionRubro;

    /*Subrubro*/
    @ColumnInfo(name = "de_subrubro")
    private String descripcionSubrubro;

    /*Marca*/
    @ColumnInfo(name = "de_linea")
    private String descripcionMarca;

    /*Proveedor*/
    @ColumnInfo(name = "de_proveedor")
    private String deProveedor;

    @ColumnInfo(name = "id_plancta")
    private Integer idPlancta;

    @ColumnInfo(name = "id_sucursal")
    private Integer idSucursal;

    @ColumnInfo(name = "ti_proveedor")
    private String tiProveedor;

    @ColumnInfo(name = "nu_cuit")
    private String nuCuit;

    /**/
    @ColumnInfo(name = "cantidad_vendida")
    private Integer cantidadVendida;

    @ColumnInfo(name = "fecha_ultima_venta")
    private String fechaUltimaVenta;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdNegocio() {
        return idNegocio;
    }

    public void setIdNegocio(int idNegocio) {
        this.idNegocio = idNegocio;
    }

    public int getIdRubro() {
        return idRubro;
    }

    public void setIdRubro(int idRubro) {
        this.idRubro = idRubro;
    }

    public int getIdSubrubro() {
        return idSubrubro;
    }

    public void setIdSubrubro(int idSubrubro) {
        this.idSubrubro = idSubrubro;
    }

    public int getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(int idLinea) {
        this.idLinea = idLinea;
    }

    @NonNull
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(@NonNull String descripcion) {
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

    public Double getStock() {
        return stock;
    }

    public void setStock(Double caStock) {
        this.stock = caStock;
    }

    public Double getCaStock() {
        return caStock;
    }

    public void setCaStock(Double caStock) {
        this.stock = caStock;
    }

    public String getSnPromocion() {
        return snPromocion;
    }

    public void setSnPromocion(String snPromocion) {
        this.snPromocion = snPromocion;
    }

    public double getImpuestoInterno() {
        return impuestoInterno;
    }

    public void setImpuestoInterno(double impuestoInterno) {
        this.impuestoInterno = impuestoInterno;
    }

    public String getSnCritico() {
        return snCritico;
    }

    public void setSnCritico(String snCritico) {
        this.snCritico = snCritico;
    }

    public String getSnPesable() {
        return snPesable;
    }

    public void setSnPesable(String snPesable) {
        this.snPesable = snPesable;
    }

    public String getSnUnidad() {
        return snUnidad;
    }

    public void setSnUnidad(String snUnidad) {
        this.snUnidad = snUnidad;
    }

    public Double getTasaImpuestoInterno() {
        return tasaImpuestoInterno;
    }

    public void setTasaImpuestoInterno(Double tasaImpuestoInterno) {
        this.tasaImpuestoInterno = tasaImpuestoInterno;
    }

    public double getCantidadKilos() {
        return cantidadKilos;
    }

    public void setCantidadKilos(double cantidadKilos) {
        this.cantidadKilos = cantidadKilos;
    }

    public Double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(Double precioBase) {
        this.precioBase = precioBase;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Double getPrecioCosto() {
        return precioCosto;
    }

    public void setPrecioCosto(Double precioCosto) {
        this.precioCosto = precioCosto;
    }

    public Double getTasaCosto() {
        return tasaCosto;
    }

    public void setTasaCosto(Double tasaCosto) {
        this.tasaCosto = tasaCosto;
    }

    public String getSnKit() {
        return snKit;
    }

    public void setSnKit(String snKit) {
        this.snKit = snKit;
    }

    public String getSnAlcohol() {
        return snAlcohol;
    }

    public void setSnAlcohol(String snAlcohol) {
        this.snAlcohol = snAlcohol;
    }

    public double getUnidadPorBulto() {
        return unidadPorBulto;
    }

    public void setUnidadPorBulto(double unidadPorBulto) {
        this.unidadPorBulto = unidadPorBulto;
    }

    public String getDescripcionNegocio() {
        return descripcionNegocio;
    }

    public void setDescripcionNegocio(String descripcionNegocio) {
        this.descripcionNegocio = descripcionNegocio;
    }

    public String getDescripcionRubro() {
        return descripcionRubro;
    }

    public void setDescripcionRubro(String descripcionRubro) {
        this.descripcionRubro = descripcionRubro;
    }

    public String getDescripcionSubrubro() {
        return descripcionSubrubro;
    }

    public void setDescripcionSubrubro(String descripcionSubrubro) {
        this.descripcionSubrubro = descripcionSubrubro;
    }

    public String getDescripcionMarca() {
        return descripcionMarca;
    }

    public void setDescripcionMarca(String descripcionMarca) {
        this.descripcionMarca = descripcionMarca;
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

    public Integer getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(Integer cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    public String getFechaUltimaVenta() {
        return fechaUltimaVenta;
    }

    public void setFechaUltimaVenta(String fechaUltimaVenta) {
        this.fechaUltimaVenta = fechaUltimaVenta;
    }
}
