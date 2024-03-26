package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;

import com.ar.vgmsistemas.database.dao.entity.key.PkClienteBd;

import java.io.Serializable;

@Entity(tableName = "comercio",
        primaryKeys = {"id_sucursal", "id_cliente", "id_comercio"})
public class ClienteBd implements Serializable {

    @NonNull
    @Embedded()
    private PkClienteBd id;

    @ColumnInfo(name = "id_postal")
    private int idPostal;

    @ColumnInfo(name = "ti_tadgr")
    private Integer idDgr;

    @ColumnInfo(name = "ti_contribuyente")
    private String idCategoriaFiscal;

    @ColumnInfo(name = "id_condvta")
    private String idCondicionVenta;

    @ColumnInfo(name = "id_repartidor")
    private Integer idRepartidor;

    @ColumnInfo(name = "id_lista")
    private int idListaPrecio;

    @ColumnInfo(name = "id_cuit")
    private String cuit;

    @ColumnInfo(name = "de_direccion")
    private String domicilio;

    @ColumnInfo(name = "nu_telefono")
    private String telefono;

    @ColumnInfo(name = "de_organizacion")
    private String razonSocial;

    @ColumnInfo(name = "nu_latitud")
    private Double latitud;

    @ColumnInfo(name = "nu_longitud")
    private Double longitud;

    @ColumnInfo(name = "ti_dirsc")
    private Integer idDirsc;

    @ColumnInfo(name = "id_movil_comercio")
    private String idMovil;

    @ColumnInfo(name = "pr_limitecredito")
    private Double limiteCredito;

    @ColumnInfo(name = "pr_limitedisponibilidad")
    private Double limiteDisponibilidad;

    @ColumnInfo(name = "de_rubro")
    private String descripcionRubro;

    @ColumnInfo(name = "ta_dto_clie")
    private Double tasaDescuentoCliente;

    @ColumnInfo(name = "pr_venta_acum")
    private Double totalVentaAcumulada;

    @ColumnInfo(name = "sn_habilitacion_alcohol")
    private String snHabilitacionAlcohol;

    @ColumnInfo(name = "fe_venc_habilitacion_alcohol")
    private String feVencHabilitacionAlcohol;

    @ColumnInfo(name = "sn_habilitado")
    private String snHabilitado;

    @ColumnInfo(name = "de_cod_autoriza_rep_movil")
    private String codAutCtaCte;


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


    public String getCuit() {
        return this.cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefono() {
        return telefono;
    }

    public PkClienteBd getId() {
        return this.id;
    }

    public void setId(PkClienteBd id) {
        this.id = id;
    }

    public Double getLatitud() {
        return this.latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return this.longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public void setIdMovil(String idMovil) {
        this.idMovil = idMovil;
    }

    public String getIdMovil() {
        return this.idMovil;
    }

    public void setLimiteCredito(Double limiteCredito) {
        if (limiteCredito == null) limiteCredito = 0D;
        this.limiteCredito = limiteCredito;
    }

    public Double getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteDisponibilidad(Double limiteDisponibilidad) {
        if (limiteDisponibilidad == null) limiteDisponibilidad = 0D;
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

    public Double getTasaDescuentoCliente() {
        return tasaDescuentoCliente;
    }

    public void setTasaDescuentoCliente(Double tasaDescuentoCliente) {
        this.tasaDescuentoCliente = tasaDescuentoCliente;
    }

    public Double getTotalVentaAcumulada() {
        return totalVentaAcumulada;
    }

    public void setTotalVentaAcumulada(Double totalVentaAcumulada) {
        this.totalVentaAcumulada = totalVentaAcumulada;
    }

    public String getFeVencHabilitacionAlcohol() {
        return feVencHabilitacionAlcohol;
    }

    public void setFeVencHabilitacionAlcohol(String feVencHabilitacionAlcohol) {
        this.feVencHabilitacionAlcohol = feVencHabilitacionAlcohol;
    }

    public String getCodAutCtaCte() {
        return codAutCtaCte;
    }

    public void setCodAutCtaCte(String codAutCtaCte) {
        if (codAutCtaCte == null) codAutCtaCte = "";
        this.codAutCtaCte = codAutCtaCte;
    }

    public boolean isCodigoCorrecto(String codAValidar) {
        boolean isValido = (getCodAutCtaCte() != null && getCodAutCtaCte() != null && !getCodAutCtaCte().equals("") && getCodAutCtaCte().equals(codAValidar));
        return isValido;
    }

    public int getIdPostal() {
        return idPostal;
    }

    public void setIdPostal(int idPostal) {
        this.idPostal = idPostal;
    }

    public Integer getIdDgr() {
        return idDgr;
    }

    public void setIdDgr(Integer idDgr) {
        this.idDgr = idDgr;
    }

    public String getIdCategoriaFiscal() {
        return idCategoriaFiscal;
    }

    public void setIdCategoriaFiscal(String idCategoriaFiscal) {
        this.idCategoriaFiscal = idCategoriaFiscal;
    }

    public String getIdCondicionVenta() {
        return idCondicionVenta;
    }

    public void setIdCondicionVenta(String idCondicionVenta) {
        this.idCondicionVenta = idCondicionVenta;
    }

    public Integer getIdRepartidor() {
        return idRepartidor;
    }

    public void setIdRepartidor(Integer idRepartidor) {
        this.idRepartidor = idRepartidor;
    }

    public int getIdListaPrecio() {
        return idListaPrecio;
    }

    public void setIdListaPrecio(int idListaPrecio) {
        this.idListaPrecio = idListaPrecio;
    }

    public Integer getIdDirsc() {
        return idDirsc;
    }

    public void setIdDirsc(Integer idDirsc) {
        this.idDirsc = idDirsc;
    }

    public String getSnHabilitacionAlcohol() {
        return snHabilitacionAlcohol;
    }

    public void setSnHabilitacionAlcohol(String snHabilitacionAlcohol) {
        this.snHabilitacionAlcohol = snHabilitacionAlcohol;
    }

    public String getSnHabilitado() {
        return snHabilitado;
    }

    public void setSnHabilitado(String snHabilitado) {
        if (snHabilitado == null) snHabilitado = "S";
        this.snHabilitado = snHabilitado;
    }
}
