package com.ar.vgmsistemas.entity;

import com.ar.vgmsistemas.entity.key.PkDescuentoProveedor;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DescuentoProveedor implements Serializable {

    private static final long serialVersionUID = 898474326261287482L;

    @SerializedName("id")
    PkDescuentoProveedor id;

    @SerializedName("descripcionDescuento")
    private String descripcionDescuento;

    @SerializedName("idProveedor")
    private Integer idProveedor;

    @SerializedName("idNegocio")
    private Integer idNegocio;

    @SerializedName("idRubro")
    private Integer idRubro;

    @SerializedName("idSubrubro")
    private Integer idSubrubro;

    @SerializedName("idArticulo")
    private Integer idArticulo;

    @SerializedName("idMarca")
    private Integer idMarca;

    @SerializedName("tasaDescuento")
    private double tasaDescuento;

    @SerializedName("prioridad")
    private int prioridad;

    public PkDescuentoProveedor getId() {
        return id;
    }

    public void setId(PkDescuentoProveedor _id) {
        this.id = _id;
    }

    public String getDescripcionDescuento() {
        return descripcionDescuento;
    }

    public void setDescripcionDescuento(String _descripcionDescuento) {
        this.descripcionDescuento = _descripcionDescuento;
    }

    public double getTasaDescuento() {
        return tasaDescuento;
    }

    public void setTasaDescuento(double _tasaDescuento) {
        this.tasaDescuento = _tasaDescuento;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int _prioridad) {
        this.prioridad = _prioridad;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer _idProveedor) {
        this.idProveedor = _idProveedor;
    }

    public Integer getIdNegocio() {
        return idNegocio;
    }

    public void setIdNegocio(Integer _idNegocio) {
        this.idNegocio = _idNegocio;
    }

    public Integer getIdRubro() {
        return idRubro;
    }

    public void setIdRubro(Integer _idRubro) {
        this.idRubro = _idRubro;
    }

    public Integer getIdSubrubro() {
        return idSubrubro;
    }

    public void setIdSubrubro(Integer _idSubrubro) {
        this.idSubrubro = _idSubrubro;
    }

    public Integer getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Integer _idMarca) {
        this.idMarca = _idMarca;
    }

    public Integer getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Integer _idArticulo) {
        this.idArticulo = _idArticulo;
    }

}
