package com.ar.vgmsistemas.entity;

import com.ar.vgmsistemas.entity.key.PkVentaDetalle;
import com.ar.vgmsistemas.helper.TipoOperacion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VentaDetalle implements Serializable, Cloneable {
    private static final long serialVersionUID = 4360824841165769430L;

    @SerializedName("id")
    private PkVentaDetalle id;

    @SerializedName("articulo")
    private Articulo articulo;

    @SerializedName("cantidad")
    private double cantidad;

    @SerializedName("precioUnitarioSinIva")
    private double precioUnitarioSinIva;

    @SerializedName("precioIvaUnitario")
    private double precioIvaUnitario;

    @SerializedName("precioUnitarioSinDescuento")
    private double precioUnitarioSinDescuento;

    @SerializedName("tasaDescuento")
    private double tasaDescuento;

    @SerializedName("bultos")
    private int bultos;

    @SerializedName("unidades")
    private double unidades;

    @SerializedName("listaPrecio")
    private ListaPrecio listaPrecio;

    @SerializedName("nulo")
    private String anulo;

    @SerializedName("fechaSincronizacion")
    private Date fechaSincronizacion;

    @SerializedName("precioImpuestoInterno")
    private double precioImpuestoInterno;

    @SerializedName("idMovil")
    private String idMovil;

    @SerializedName("idDescuentoProveedor")
    private Integer idDescuentoProveedor;

    @SerializedName("tasaDescuentoProveedor")
    private double tasaDescuentoProveedor;

    @SerializedName("precioUnitarioSinDescuentoProveedor")
    private double precioUnitarioSinDescuentoProveedor;

    @SerializedName("tasaDescuentoCliente")
    private double tasaDescuentoCliente;

    @SerializedName("precioUnitarioSinDescuentoCliente")
    private double precioUnitarioSinDescuentoCliente;

    @SerializedName("numeroTropa")
    private Integer numeroTropa;

    @SerializedName("caArticulosKilos")
    private double caArticulosKilos;

    @SerializedName("prKiloUnitario")
    private double prKiloUnitario;

    @SerializedName("idCombo")
    private String idCombo;
    private List<VentaDetalle> detalleCombo;

    private boolean isCabeceraPromo;

    @JsonIgnore
    private Integer idAccionesCom;

    /*** reparto **/
    private double unidadesDevueltas = 0d;

    private int bultosDevueltos = 0;

    private Double tasaImpuestoInterno;

    private Integer idPromo;

    private Integer tipoLista;

    private Integer idAccionEmp;

    private String isMaxAccomSuperado;

    private Integer idFcSecuencia;

    @SerializedName("kilos")
    private double caKilos;


    private Venta venta;
    @SerializedName("tipoOperacion")

    private TipoOperacion tipoOperacion;

    private boolean isValid = true; // ______NO SE MAPEA______ sirve para determinar si el detalle es valido con los datos ingresados #13992

    private int idAccionComDetalleEmp;

    private int idAccionComDetalleProveedor;

    private int idPromoTemporal; // ______NO SE MAPEA______ sirve para determinar si el es de una promo especial y tomar el dto en caso que sea por dto #50673

    private double importeTotal;

    public PkVentaDetalle getId() {
        return this.id;
    }

    public void setId(PkVentaDetalle id) {
        this.id = id;
    }

    public Articulo getArticulo() {
        return this.articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public double getCantidad() {
        cantidad = 0;
        if (getArticulo() != null) {
            double unidadesXBulto = getArticulo().getUnidadPorBulto();
            int bultos = this.getBultos();
            double unidades = this.getUnidades();
            cantidad = bultos * unidadesXBulto + unidades;
        }
        return this.cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getCaKilos() {
        return this.caKilos;
    }

    public void setCaKilos(double caKilos) {
        this.caKilos = caKilos;
    }

    public double getPrecioUnitarioSinIva() {

        //_precioUnitarioSinIva = _precioUnitarioSinDescuento * (1 - _tasaDescuento/100);
        precioUnitarioSinIva = precioUnitarioSinDescuento * (1 + tasaDescuento);
        return this.precioUnitarioSinIva;
    }

    public double getPrecioUnitarioSinIvaNoBo() {
        return precioUnitarioSinIva;
    }

    public void setPrecioUnitarioSinIva(double precioUnitarioSinIva) {
        this.precioUnitarioSinIva = precioUnitarioSinIva;
    }

    public double getPrecioIvaUnitario() {
        return this.precioIvaUnitario;
    }

    public void setPrecioIvaUnitario(double precioIvaUnitario) {
        this.precioIvaUnitario = precioIvaUnitario;
    }

    public double getPrecioUnitarioSinDescuento() {
        //return this._precioUnitarioSinDescuento;
        precioUnitarioSinDescuento = precioUnitarioSinDescuentoCliente * (1 + tasaDescuentoCliente);
        return precioUnitarioSinDescuento;
    }

    public void setPrecioUnitarioSinDescuento(double precioUnitarioSinDescuento) {
        this.precioUnitarioSinDescuento = precioUnitarioSinDescuento;
    }

    public double getTasaDescuento() {
        return this.tasaDescuento;
    }

    public void setTasaDescuento(double tasaDescuento) {
        this.tasaDescuento = tasaDescuento;
    }

    public double getPrecioImpuestoInterno() {
        return precioImpuestoInterno;
    }

    public void setPrecioImpuestoInterno(double _precioImpuestoInterno) {
        this.precioImpuestoInterno = _precioImpuestoInterno;
    }

    public double getUnidades() {
        return this.unidades;
    }

    public void setUnidades(double unidades) {
        this.unidades = unidades;
    }

    public int getBultos() {
        return this.bultos;
    }

    public void setBultos(int bultos) {
        this.bultos = bultos;
    }

    public ListaPrecio getListaPrecio() {
        return this.listaPrecio;
    }

    public void setListaPrecio(ListaPrecio listaPrecio) {
        this.listaPrecio = listaPrecio;
    }

    public Venta getVenta() {
        return this.venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public TipoOperacion getTipoOperacion() {
        return this.tipoOperacion;
    }

    public void setTipoOperacion(TipoOperacion tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public String getAnulo() {
        return this.anulo;
    }

    public void setAnulo(String nulo) {
        this.anulo = nulo;
    }

    public Date getFechaSincronizacion() {
        return this.fechaSincronizacion;
    }

    public void setFechaSincronizacion(Date fechaSincronizacion) {
        this.fechaSincronizacion = fechaSincronizacion;
    }

    public double getImporteTotal() {
        double subt = Math.round(getPrecioUnitarioSinIva() * 1000) / 1000.0d;
        importeTotal = subt * getCantidad();
        return importeTotal;
    }

    public String getIdMovil() {
        return this.idMovil;
    }

    public void setIdMovil(String idMovil) {
        this.idMovil = idMovil;
    }

    public Integer getIdDescuentoProveedor() {
        return idDescuentoProveedor;
    }

    public void setIdDescuentoProveedor(Integer _idDescuentoProveedor) {
        this.idDescuentoProveedor = _idDescuentoProveedor;
    }

    public double getTasaDescuentoProveedor() {
        return tasaDescuentoProveedor;
    }

    public void setTasaDescuentoProveedor(double _tasaDescuentoProveedor) {
        this.tasaDescuentoProveedor = _tasaDescuentoProveedor;
    }

    public double getPrecioUnitarioSinDescuentoProveedor() {
        return precioUnitarioSinDescuentoProveedor;
    }

    public void setPrecioUnitarioSinDescuentoProveedor(double _precioUnitarioSinDescuentoProveedor) {
        this.precioUnitarioSinDescuentoProveedor = _precioUnitarioSinDescuentoProveedor;
    }

    public double getTasaDescuentoCliente() {
        return tasaDescuentoCliente;
    }

    public void setTasaDescuentoCliente(double _tasaDescuentoCliente) {
        this.tasaDescuentoCliente = _tasaDescuentoCliente;
    }

    public double getPrecioUnitarioSinDescuentoCliente() {
        precioUnitarioSinDescuentoCliente = precioUnitarioSinDescuentoProveedor * (1 + tasaDescuentoProveedor);
        return precioUnitarioSinDescuentoCliente;
    }

    public void setPrecioUnitarioSinDescuentoCliente(
            double _precioUnitarioSinDescuentoCliente) {
        this.precioUnitarioSinDescuentoCliente = _precioUnitarioSinDescuentoCliente;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public Integer getNumeroTropa() {
        return numeroTropa;
    }

    public String getNumeroTropaAsString() {
        if (numeroTropa == null || numeroTropa == 0) {
            return "";
        }
        return getNumeroTropa().toString();
    }

    public void setNumeroTropa(Integer numeroTropa) {
        this.numeroTropa = numeroTropa;
    }

    public double getCaArticulosKilos() {
        return caArticulosKilos;
    }

    public void setCaArticulosKilos(double caKilos) {
        this.caArticulosKilos = caKilos;
    }

    public double getPrKiloUnitario() {
        return prKiloUnitario;
    }

    public void setPrKiloUnitario(double prKiloUnitario) {
        this.prKiloUnitario = prKiloUnitario;
    }

    public boolean isCabeceraPromo() {
        if ((getTipoLista() != null && (getTipoLista() == 3 || getTipoLista() == 8)) && getIdPromo() == null) {
            return true;
        } else return isCabeceraPromo;
    }

    public void setCabeceraPromo(boolean isPromo) {
        this.isCabeceraPromo = isPromo;
    }

    public List<VentaDetalle> getDetalleCombo() {
        if (detalleCombo == null) detalleCombo = new ArrayList<VentaDetalle>();
        return detalleCombo;
    }

    public void setDetalleCombo(List<VentaDetalle> detalleCombo) {
        this.detalleCombo = detalleCombo;
    }

    public String getIdCombo() {
        if (idCombo == null) return "";
        return idCombo;
    }

    public void setIdCombo(String id) {
        this.idCombo = id;
    }

    public Integer getIdAccionesCom() {
        return idAccionesCom;
    }

    public void setIdAccionesCom(Integer idAccionesCom) {
        this.idAccionesCom = idAccionesCom;
    }

    public int getBultosDevueltos() {
        return bultosDevueltos;
    }

    public void setBultosDevueltos(int bultosDevueltos) {
        this.bultosDevueltos = bultosDevueltos;
    }

    public double getUnidadesDevueltas() {
        return unidadesDevueltas;
    }

    public void setUnidadesDevueltas(double unidadesDevueltas) {
        this.unidadesDevueltas = unidadesDevueltas;
    }

    public double getCantidadDevuelta() {
        double cantidad = 0;
        if (getArticulo() != null) {
            double unidadesXBulto = getArticulo().getUnidadPorBulto();
            int bultos = this.getBultosDevueltos();
            double unidades = this.getUnidadesDevueltas();
            cantidad = bultos * unidadesXBulto + unidades;
        }
        return cantidad;
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

    public VentaDetalle clone() throws CloneNotSupportedException {
        return (VentaDetalle) super.clone();
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

    public Integer getIdFcSecuencia() {
        return idFcSecuencia;
    }

    public void setIdFcSecuencia(Integer idFcSecuencia) {
        this.idFcSecuencia = idFcSecuencia;
    }

    public int getIdPromoTemporal() {
        return idPromoTemporal;
    }

    public void setIdPromoTemporal(int idPromoCalculado) {
        this.idPromoTemporal = idPromoCalculado;
    }


    public void setImporteTotal(double importeTotal) {
        this.importeTotal = importeTotal;
    }

}
