package com.ar.vgmsistemas.entity;

import com.ar.vgmsistemas.entity.key.PkVendedorObjetivoDetalle;
import com.ar.vgmsistemas.utils.Matematica;

import java.io.Serializable;

public class VendedorObjetivoDetalle implements Serializable {

    private static final long serialVersionUID = 1L;

    private PkVendedorObjetivoDetalle id;

    private Negocio negocio;

    private Rubro rubro;

    private Subrubro subrubro;

    private Marca linea;

    private Proveedor proveedor;

    private Articulo articulo;

    private Integer caArticulos;

    private Double caKilos;

    private Double taCobertura;

    private Integer caRestante;

    private Double logrado;


    public Negocio getNegocio() {
        return negocio;
    }

    public void setNegocio(Negocio negocio) {
        this.negocio = negocio;
    }

    public Rubro getRubro() {
        return rubro;
    }

    public void setRubro(Rubro rubro) {
        this.rubro = rubro;
    }

    public Subrubro getSubrubro() {
        return subrubro;
    }

    public void setSubrubro(Subrubro subrubro) {
        this.subrubro = subrubro;
    }

    public Marca getLinea() {
        return linea;
    }

    public void setLinea(Marca linea) {
        this.linea = linea;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Integer getCaArticulos() {
        return caArticulos;
    }

    public void setCaArticulos(Integer caArticulos) {
        this.caArticulos = caArticulos;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Double getCaKilos() {
        return caKilos;
    }

    public void setCaKilos(Double caKilos) {
        this.caKilos = caKilos;
    }

    public Double getTaCobertura() {
        return taCobertura;
    }

    public void setTaCobertura(Double taCobertura) {
        this.taCobertura = taCobertura;
    }

    public Double getCaLograda() {
        return logrado;
    }

    public void setCaLograda(Double logrado) {
        this.logrado = logrado;
    }

    public Double getCaLograda(int categoria) {
        if (categoria == VendedorObjetivo.CATEGORIA_COBERTURA) {
            return Matematica.Round(getCaLograda(), 2);
        } else {
            return getCaLograda();

        }
    }

    public Double getObjetivo(int categoria) {
        if (categoria == VendedorObjetivo.CATEGORIA_COBERTURA) {
            return Matematica.Round(Double.valueOf(getTaCobertura() * 100), 2);
        } else {
            return Double.valueOf(getCaArticulos());
        }
    }

    public Integer getCaRestante() {
        return caRestante;
    }

    public void setCaRestante(Integer caRestante) {
        this.caRestante = caRestante;
    }


    public PkVendedorObjetivoDetalle getId() {
        return id;
    }

    public void setId(PkVendedorObjetivoDetalle id) {
        this.id = id;
    }

    public Double getLogrado() {
        return logrado;
    }

    public void setLogrado(Double logrado) {
        this.logrado = logrado;
    }
}
