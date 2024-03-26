package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "articulos",
        indices = {@Index(name = "art_idx_de_articulos",value = {"de_articulos"}),
                @Index(name = "art_idx_id_empresa", value = {"id_empresa"}, unique = true, orders = {Index.Order.ASC})})
public class ArticuloBd implements Serializable {
    @ColumnInfo(name = "id_articulos")
    @PrimaryKey
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

    @ColumnInfo(name = "ca_stock")
    private Double stock;

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

    @ColumnInfo(name = "ca_kilos",defaultValue = "0")
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

    @Ignore
    private double cantidad_vendida;

    @Ignore
    private String fecha_ultima_venta;

    /*Getters-Setters*/

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
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

    public void setStock(Double stock) {
        this.stock = stock;
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
        if (snKit == null) return "N";
        return snKit;
    }

    public void setSnKit(String snKit) {
        if (snKit == null) snKit = "N";
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


    /*public ArticuloBd(int id, int idNegocio, int idRubro, int idSubrubro, int idLinea,
                      String descripcion, double tasaIva, String codigoEmpresa, Double stock,
                      String snPromocion, double impuestoInterno, String snCritico, String snPesable,
                      String snUnidad, double tasaImpuestoInterno, double cantidadKilos, double precioBase,
                      int idProveedor, double precioCosto, Double tasaCosto, String snKit,
                      String snAlcohol, double unidadPorBulto) {
        this.id = id;
        this.idNegocio = idNegocio;
        this.idRubro = idRubro;
        this.idSubrubro = idSubrubro;
        this.idLinea = idLinea;
        this.descripcion = descripcion;
        this.tasaIva = tasaIva;
        this.codigoEmpresa = codigoEmpresa;
        this.stock = stock;
        this.snPromocion = snPromocion;
        this.impuestoInterno = impuestoInterno;
        this.snCritico = snCritico;
        this.snPesable = snPesable;
        this.snUnidad = snUnidad;
        this.tasaImpuestoInterno = tasaImpuestoInterno;
        this.cantidadKilos = cantidadKilos;
        this.precioBase = precioBase;
        this.idProveedor = idProveedor;
        this.precioCosto = precioCosto;
        this.tasaCosto = tasaCosto;
        this.snKit = snKit;
        this.snAlcohol = snAlcohol;
        this.unidadPorBulto = unidadPorBulto;
    }*/

    public double getCantidad_vendida() {
        return cantidad_vendida;
    }

    public void setCantidad_vendida(double cantidad_vendida) {
        this.cantidad_vendida = cantidad_vendida;
    }

    public String getFecha_ultima_venta() {
        return fecha_ultima_venta;
    }

    public void setFecha_ultima_venta(String fecha_ultima_venta) {
        this.fecha_ultima_venta = fecha_ultima_venta;
    }

    public ArticuloBd(int id, int idNegocio, int idRubro, int idSubrubro, int idLinea,
                      @NonNull String descripcion, double tasaIva, String codigoEmpresa, Double stock,
                      String snPromocion, double impuestoInterno, String snCritico, String snPesable,
                      String snUnidad, Double tasaImpuestoInterno, double cantidadKilos,
                      Double precioBase, int idProveedor, Double precioCosto, Double tasaCosto,
                      String snKit, String snAlcohol, double unidadPorBulto) {
        this.id = id;
        this.idNegocio = idNegocio;
        this.idRubro = idRubro;
        this.idSubrubro = idSubrubro;
        this.idLinea = idLinea;
        this.descripcion = descripcion;
        this.tasaIva = tasaIva;
        this.codigoEmpresa = codigoEmpresa;
        this.stock = stock;
        this.snPromocion = snPromocion;
        this.impuestoInterno = impuestoInterno;
        this.snCritico = snCritico;
        this.snPesable = snPesable;
        this.snUnidad = snUnidad;
        this.tasaImpuestoInterno = tasaImpuestoInterno;
        this.cantidadKilos = cantidadKilos;
        this.precioBase = precioBase;
        this.idProveedor = idProveedor;
        this.precioCosto = precioCosto;
        this.tasaCosto = tasaCosto;
        this.snKit = snKit;
        this.snAlcohol = snAlcohol;
        this.unidadPorBulto = unidadPorBulto;
    }
}
