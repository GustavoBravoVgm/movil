package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;

import com.ar.vgmsistemas.database.dao.entity.key.PkVentaDetalleBd;
import com.ar.vgmsistemas.helper.TipoOperacion;

import java.io.Serializable;

@Entity(tableName = "ventas_detalle",
        primaryKeys = {"id_fcnc", "id_tipoab", "id_ptovta", "id_numdoc", "id_secuencia"})
public class VentaDetalleBd implements Serializable, Cloneable {
    @NonNull
    @Embedded
    private PkVentaDetalleBd id;

    @ColumnInfo(name = "id_articulos")
    private int idArticulo;

    @ColumnInfo(name = "ca_articulos")
    private double cantidad;

    @ColumnInfo(name = "pr_unitario_siva")
    private double precioUnitarioSinIva;

    @ColumnInfo(name = "pr_iva")
    private double precioIvaUnitario;

    @ColumnInfo(name = "pr_unitario_sdto")
    private double precioUnitarioSinDescuento;

    @ColumnInfo(name = "ta_dto")
    private double tasaDescuento;

    @ColumnInfo(name = "ca_bulto")
    private int bultos;

    @ColumnInfo(name = "ca_unidades")
    private double unidades;

    @ColumnInfo(name = "id_lista")
    private int idLista;

    @ColumnInfo(name = "sn_anulo")
    private String anulo;

    @ColumnInfo(name = "fe_sincronizacion")
    private String fechaSincronizacion;

    @ColumnInfo(name = "pr_imp_interno")
    private double precioImpuestoInterno;

    @ColumnInfo(name = "id_movil_venta")
    private String idMovil;

    @ColumnInfo(name = "id_dto_proveedor")
    private Integer idDescuentoProveedor;

    @ColumnInfo(name = "ta_dto_proveedor")
    private double tasaDescuentoProveedor;

    @ColumnInfo(name = "pr_unitario_sdto_prov")
    private double precioUnitarioSinDescuentoProveedor;

    @ColumnInfo(name = "ta_dto_cliente")
    private double tasaDescuentoCliente;

    @ColumnInfo(name = "pr_unitario_sdto_cliente")
    private double precioUnitarioSinDescuentoCliente;

    @ColumnInfo(name = "nu_tropa")
    private Integer numeroTropa;

    @ColumnInfo(name = "ca_kilos_vd")
    private Double caArticulosKilos;

    @ColumnInfo(name = "pr_kilo_unitario")
    private Double prKiloUnitario;

    @ColumnInfo(name = "id_combo")
    private String idCombo;

    @ColumnInfo(name = "sn_cabecera_combo")
    private String snCabeceraCombo;

    @ColumnInfo(name = "id_acciones_com")
    private Integer idAccionesCom;

    /**
     * reparto *
     */
    @ColumnInfo(name = "ca_unidades_devueltas", defaultValue = "0.00")
    private double unidadesDevueltas = 0d;

    @ColumnInfo(name = "ca_bulto_devuelto", defaultValue = "0.00")
    private int bultosDevueltos = 0;

    @ColumnInfo(name = "ta_imp_interno_vd")
    private Double tasaImpuestoInterno;

    @ColumnInfo(name = "id_promo")
    private Integer idPromo;

    @ColumnInfo(name = "ti_lista")
    private Integer tipoLista;

    @ColumnInfo(name = "id_accion_emp")
    private Integer idAccionEmp;

    @ColumnInfo(name = "sn_max_accom_superado")
    private String isMaxAccomSuperado;

    @ColumnInfo(name = "id_FcSecuencia")
    private Integer idFcSecuencia;

    @ColumnInfo(name = "ca_kilos")
    private Double caKilos;

    @Ignore
    private TipoOperacion tipoOperacion;

    @Ignore
    private int idAccionComDetalleEmp;

    @Ignore
    private int idAccionComDetalleProveedor;

    /*Getters-Setters*/

    public PkVentaDetalleBd getId() {
        return id;
    }

    public void setId(PkVentaDetalleBd id) {
        this.id = id;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitarioSinIva() {
        return precioUnitarioSinIva;
    }

    public void setPrecioUnitarioSinIva(double precioUnitarioSinIva) {
        this.precioUnitarioSinIva = precioUnitarioSinIva;
    }

    public double getPrecioIvaUnitario() {
        return precioIvaUnitario;
    }

    public void setPrecioIvaUnitario(double precioIvaUnitario) {
        this.precioIvaUnitario = precioIvaUnitario;
    }

    public double getPrecioUnitarioSinDescuento() {
        return precioUnitarioSinDescuento;
    }

    public void setPrecioUnitarioSinDescuento(double precioUnitarioSinDescuento) {
        this.precioUnitarioSinDescuento = precioUnitarioSinDescuento;
    }

    public double getTasaDescuento() {
        return tasaDescuento;
    }

    public void setTasaDescuento(double tasaDescuento) {
        this.tasaDescuento = tasaDescuento;
    }

    public int getBultos() {
        return bultos;
    }

    public void setBultos(int bultos) {
        this.bultos = bultos;
    }

    public double getUnidades() {
        return unidades;
    }

    public void setUnidades(double unidades) {
        this.unidades = unidades;
    }

    public int getIdLista() {
        return idLista;
    }

    public void setIdLista(int idLista) {
        this.idLista = idLista;
    }

    public String getAnulo() {
        return anulo;
    }

    public void setAnulo(String anulo) {
        this.anulo = anulo;
    }

    public String getFechaSincronizacion() {
        return fechaSincronizacion;
    }

    public void setFechaSincronizacion(String fechaSincronizacion) {
        this.fechaSincronizacion = fechaSincronizacion;
    }

    public double getPrecioImpuestoInterno() {
        return precioImpuestoInterno;
    }

    public void setPrecioImpuestoInterno(double precioImpuestoInterno) {
        this.precioImpuestoInterno = precioImpuestoInterno;
    }

    public String getIdMovil() {
        return idMovil;
    }

    public void setIdMovil(String idMovil) {
        this.idMovil = idMovil;
    }

    public Integer getIdDescuentoProveedor() {
        return idDescuentoProveedor;
    }

    public void setIdDescuentoProveedor(Integer idDescuentoProveedor) {
        this.idDescuentoProveedor = idDescuentoProveedor;
    }

    public double getTasaDescuentoProveedor() {
        return tasaDescuentoProveedor;
    }

    public void setTasaDescuentoProveedor(double tasaDescuentoProveedor) {
        this.tasaDescuentoProveedor = tasaDescuentoProveedor;
    }

    public double getPrecioUnitarioSinDescuentoProveedor() {
        return precioUnitarioSinDescuentoProveedor;
    }

    public void setPrecioUnitarioSinDescuentoProveedor(double precioUnitarioSinDescuentoProveedor) {
        this.precioUnitarioSinDescuentoProveedor = precioUnitarioSinDescuentoProveedor;
    }

    public double getTasaDescuentoCliente() {
        return tasaDescuentoCliente;
    }

    public void setTasaDescuentoCliente(double tasaDescuentoCliente) {
        this.tasaDescuentoCliente = tasaDescuentoCliente;
    }

    public double getPrecioUnitarioSinDescuentoCliente() {
        return precioUnitarioSinDescuentoCliente;
    }

    public void setPrecioUnitarioSinDescuentoCliente(double precioUnitarioSinDescuentoCliente) {
        this.precioUnitarioSinDescuentoCliente = precioUnitarioSinDescuentoCliente;
    }

    public Integer getNumeroTropa() {
        return numeroTropa;
    }

    public void setNumeroTropa(Integer numeroTropa) {
        this.numeroTropa = numeroTropa;
    }

    public Double getCaArticulosKilos() {
        return caArticulosKilos;
    }

    public void setCaArticulosKilos(Double caArticulosKilos) {
        this.caArticulosKilos = caArticulosKilos;
    }

    public Double getPrKiloUnitario() {
        return prKiloUnitario;
    }

    public void setPrKiloUnitario(Double prKiloUnitario) {
        this.prKiloUnitario = prKiloUnitario;
    }

    public String getIdCombo() {
        return idCombo;
    }

    public void setIdCombo(String idCombo) {
        this.idCombo = idCombo;
    }

    public String getSnCabeceraCombo() {
        return snCabeceraCombo;
    }

    public void setSnCabeceraCombo(String snCabeceraCombo) {
        this.snCabeceraCombo = snCabeceraCombo;
    }

    public Integer getIdAccionesCom() {
        return idAccionesCom;
    }

    public void setIdAccionesCom(Integer idAccionesCom) {
        this.idAccionesCom = idAccionesCom;
    }

    public double getUnidadesDevueltas() {
        return unidadesDevueltas;
    }

    public void setUnidadesDevueltas(double unidadesDevueltas) {
        this.unidadesDevueltas = unidadesDevueltas;
    }

    public int getBultosDevueltos() {
        return bultosDevueltos;
    }

    public void setBultosDevueltos(int bultosDevueltos) {
        this.bultosDevueltos = bultosDevueltos;
    }

    public Double getTasaImpuestoInterno() {
        return tasaImpuestoInterno;
    }

    public void setTasaImpuestoInterno(Double tasaImpuestoInterno) {
        this.tasaImpuestoInterno = tasaImpuestoInterno;
    }

    public Integer getIdPromo() {
        return idPromo;
    }

    public void setIdPromo(Integer idPromo) {
        this.idPromo = idPromo;
    }

    public Integer getTipoLista() {
        return tipoLista;
    }

    public void setTipoLista(Integer tipoLista) {
        this.tipoLista = tipoLista;
    }

    public Integer getIdAccionEmp() {
        return idAccionEmp;
    }

    public void setIdAccionEmp(Integer idAccionEmp) {
        this.idAccionEmp = idAccionEmp;
    }

    public String getIsMaxAccomSuperado() {
        return isMaxAccomSuperado;
    }

    public void setIsMaxAccomSuperado(String isMaxAccomSuperado) {
        this.isMaxAccomSuperado = isMaxAccomSuperado;
    }

    public Integer getIdFcSecuencia() {
        return idFcSecuencia;
    }

    public void setIdFcSecuencia(Integer idFcSecuencia) {
        this.idFcSecuencia = idFcSecuencia;
    }

    public Double getCaKilos() {
        return caKilos;
    }

    public void setCaKilos(Double caKilos) {
        this.caKilos = caKilos;
    }

    /***ignore***/
    public TipoOperacion getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(TipoOperacion tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public int getIdAccionComDetalleEmp() {
        return idAccionComDetalleEmp;
    }

    public void setIdAccionComDetalleEmp(int idAccionComDetalleEmp) {
        this.idAccionComDetalleEmp = idAccionComDetalleEmp;
    }

    public int getIdAccionComDetalleProveedor() {
        return idAccionComDetalleProveedor;
    }

    public void setIdAccionComDetalleProveedor(int idAccionComDetalleProveedor) {
        this.idAccionComDetalleProveedor = idAccionComDetalleProveedor;
    }
}
