package com.ar.vgmsistemas.entity;

import com.ar.vgmsistemas.entity.key.PkCliente;
import com.ar.vgmsistemas.utils.Formatter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Cliente implements Serializable {
    private static final long serialVersionUID = 6354610967643444849L;
    public static final String TABLE = "comercio";

    @SerializedName("id")
    private PkCliente id;

    private String cuit;

    private String domicilio;

    private String telefono;

    private String razonSocial;

    @SerializedName("latitud")
    private double latitud;

    @SerializedName("longitud")
    private double longitud;

    private String idMovil;

    private Double limiteCredito;

    private Double limiteDisponibilidad;

    private String descripcionRubro;

    private double tasaDescuentoCliente;

    private double totalVentaAcumulada;

    private boolean isHabilitadoParaAlcohol;

    private Date feVencHabilitacionAlcohol;

    private boolean isHabilitado;

    private String codAutCtaCte;

    private int cantidadVentas = 0; // Tenia valor 90
    private int cantidadNoPedidos = 0; // Tenia valor 90
    //private String limiteDisponibilidadString;
    private double totalSaldoCuentaCorriente;


    private Localidad localidad;

    private CondicionRenta condicionRenta;

    private CategoriaFiscal categoriaFiscal;

    private CondicionVenta condicionVenta;

    private Repartidor repartidor;

    private ListaPrecio listaPrecio;

    private CondicionDirsc condicionDirsc;

    public String getRazonSocial() {
        return this.razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDomicilio() {
        return this.domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public CategoriaFiscal getCategoriaFiscal() {
        return this.categoriaFiscal;
    }

    public void setCategoriaFiscal(CategoriaFiscal categoriaFiscal) {
        this.categoriaFiscal = categoriaFiscal;
    }

    public CondicionRenta getCondicionRenta() {
        return this.condicionRenta;
    }

    public void setCondicionRenta(CondicionRenta condicionRenta) {
        this.condicionRenta = condicionRenta;
    }

    public String getCuit() {
        return this.cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public CondicionVenta getCondicionVenta() {
        return this.condicionVenta;
    }

    public void setCondicionVenta(CondicionVenta condicionVenta) {
        this.condicionVenta = condicionVenta;
    }

    public ListaPrecio getListaPrecio() {
        return this.listaPrecio;
    }

    public void setListaPrecio(ListaPrecio listaPrecio) {
        this.listaPrecio = listaPrecio;
    }

    public Repartidor getRepartidor() {
        return this.repartidor;
    }

    public void setRepartidor(Repartidor repartidor) {
        this.repartidor = repartidor;
    }

    public Localidad getLocalidad() {
        return this.localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefono() {
        return telefono;
    }

    public PkCliente getId() {
        return this.id;
    }

    public void setId(PkCliente id) {
        this.id = id;
    }

    public double getLatitud() {
        return this.latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return this.longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public CondicionDirsc getCondicionDirsc() {
        return this.condicionDirsc;
    }

    public void setCondicionDirsc(CondicionDirsc condicionDirsc) {
        this.condicionDirsc = condicionDirsc;
    }

    public void setCantidadVentas(int cantidadVentas) {
        this.cantidadVentas = cantidadVentas;
    }

    public int getCantidadVentas() {
        return cantidadVentas;
    }

    public int getCantidadNoPedidos() {
        return cantidadNoPedidos;
    }

    public void setCantidadNoPedidos(int cantidadNoPedidos) {
        this.cantidadNoPedidos = cantidadNoPedidos;
    }

    public void setIdMovil(String idMovil) {
        this.idMovil = idMovil;
    }

    public String getIdMovil() {
        return this.idMovil;
    }

    public void setLimiteCredito(Double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public Double getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteDisponibilidad(Double limiteDisponibilidad) {
        this.limiteDisponibilidad = limiteDisponibilidad;
    }

    public Double getLimiteDisponibilidad() {
        return limiteDisponibilidad;
    }

    public String getDescripcionRubro() {
        return descripcionRubro;
    }

    public void setDescripcionRubro(String descripcionRubro) {
        this.descripcionRubro = descripcionRubro;
    }

    public double getTasaDescuentoCliente() {
        return tasaDescuentoCliente;
    }

    public void setTasaDescuentoCliente(double tasaDescuentoCliente) {
        this.tasaDescuentoCliente = tasaDescuentoCliente;
    }

    public double getTotalVentaAcumulada() {
        return totalVentaAcumulada;
    }

    public String getTotalSaldoCCString() {

        return Formatter.formatMoney(totalSaldoCuentaCorriente);
    }

    public double getTotalSaldoCuentaCorriente() {
        return totalSaldoCuentaCorriente;
    }

    public void setTotalSaldoCuentaCorriente(double totalSaldoCuentaCorriente) {
        this.totalSaldoCuentaCorriente = totalSaldoCuentaCorriente;
    }

    public void setTotalVentaAcumulada(double totalVentaAcumulada) {
        this.totalVentaAcumulada = totalVentaAcumulada;
    }

    public boolean getIsHabilitadoParaAlcohol() {
        return isHabilitadoParaAlcohol;
    }

    public void setIsHabilitadoParaAlcohol(boolean isHabilitadoParaAlcohol) {
        this.isHabilitadoParaAlcohol = isHabilitadoParaAlcohol;
    }

    public Date getFeVencHabilitacionAlcohol() {
        return feVencHabilitacionAlcohol;
    }

    public void setFeVencHabilitacionAlcohol(Date feVencHabilitacionAlcohol) {
        this.feVencHabilitacionAlcohol = feVencHabilitacionAlcohol;
    }

    public boolean getIsHabilitado() {
        return isHabilitado;
    }

    public void setIsHabilitado(boolean isHabilitado) {
        this.isHabilitado = isHabilitado;
    }

    public String getCodAutCtaCte() {
        return codAutCtaCte;
    }

    public void setCodAutCtaCte(String codAutCtaCte) {
        this.codAutCtaCte = codAutCtaCte;
    }

    public boolean isCodigoCorrecto(String codAValidar) {
        boolean isValido = (getCodAutCtaCte() != null && getCodAutCtaCte() != null && !getCodAutCtaCte().equals("") && getCodAutCtaCte().equals(codAValidar));
        return isValido;
    }

    public boolean isHabilitadoParaAlcohol() {
        return isHabilitadoParaAlcohol;
    }

    public void setHabilitadoParaAlcohol(boolean habilitadoParaAlcohol) {
        isHabilitadoParaAlcohol = habilitadoParaAlcohol;
    }

    public boolean isHabilitado() {
        return isHabilitado;
    }

    public void setHabilitado(boolean habilitado) {
        isHabilitado = habilitado;
    }


}
