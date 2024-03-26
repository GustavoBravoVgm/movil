package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;

import com.ar.vgmsistemas.database.dao.entity.key.PkVendedorObjetivoDetalleBd;

import java.io.Serializable;

@Entity(tableName = "vend_objetivos_detalle",
        primaryKeys = {"id_objetivo", "id_secuencia"})
public class VendedorObjetivoDetalleBd implements Serializable {
    @NonNull
    @Embedded
    PkVendedorObjetivoDetalleBd id;

    @ColumnInfo(name = "id_segmento")
    private Integer idSegmento;

    @ColumnInfo(name = "id_subrubro")
    private Integer idSubrubro;

    @ColumnInfo(name = "id_linea")
    private Integer idLinea;

    @ColumnInfo(name = "id_proveedor")
    private Integer idProveedor;

    @ColumnInfo(name = "ca_articulos")
    private Integer caArticulos;

    @ColumnInfo(name = "id_articulos")
    private Integer idArticulo;

    @ColumnInfo(name = "ca_kilos")
    private Double caKilos;

    @ColumnInfo(name = "id_negocio")
    private Integer idNegocio;

    @ColumnInfo(name = "ta_cobertura")
    private Double taCobertura;

    @ColumnInfo(name = "ca_restantes")
    private Integer caRestante;

    @ColumnInfo(name = "ca_lograda")
    private Double logrado;

    /*Getters-Setters*/

    public PkVendedorObjetivoDetalleBd getId() {
        return id;
    }

    public void setId(PkVendedorObjetivoDetalleBd id) {
        this.id = id;
    }

    public Integer getIdSegmento() {
        return idSegmento;
    }

    public void setIdSegmento(Integer idSegmento) {
        this.idSegmento = idSegmento;
    }

    public Integer getIdSubrubro() {
        return idSubrubro;
    }

    public void setIdSubrubro(Integer idSubrubro) {
        this.idSubrubro = idSubrubro;
    }

    public Integer getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(Integer idLinea) {
        this.idLinea = idLinea;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Integer getCaArticulos() {
        return caArticulos;
    }

    public void setCaArticulos(Integer caArticulos) {
        this.caArticulos = caArticulos;
    }

    public Integer getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Integer idArticulo) {
        this.idArticulo = idArticulo;
    }

    public Double getCaKilos() {
        return caKilos;
    }

    public void setCaKilos(Double caKilos) {
        this.caKilos = caKilos;
    }

    public Integer getIdNegocio() {
        return idNegocio;
    }

    public void setIdNegocio(Integer idNegocio) {
        this.idNegocio = idNegocio;
    }

    public Double getTaCobertura() {
        return taCobertura;
    }

    public void setTaCobertura(Double taCobertura) {
        this.taCobertura = taCobertura;
    }

    public Integer getCaRestante() {
        return caRestante;
    }

    public void setCaRestante(Integer caRestante) {
        this.caRestante = caRestante;
    }

    public Double getLogrado() {
        return logrado;
    }

    public void setLogrado(Double logrado) {
        this.logrado = logrado;
    }
}
