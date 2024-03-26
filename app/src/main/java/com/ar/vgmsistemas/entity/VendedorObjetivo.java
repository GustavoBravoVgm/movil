package com.ar.vgmsistemas.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class VendedorObjetivo implements Serializable {

    public static final String TIPO = "Tipo";

    public static final String CATEGORIA = "Categoría";

    public static final String ESTADO = "Estado";


    public static final String[] TIPOS = {"Todos", "Negocio", "Segmento", "Subrubro", "Linea", "Proveedor", "Artículo"};

    public static final String[] ESTADOS = {"Todos", "Logrado", "No Logrado"};

    public static final String[] CATEGORIAS = {"Todos", "Cobertura", "General"};


    private static final long serialVersionUID = 1L;

    public static final int TODOS = 0;

    public static final int CATEGORIA_COBERTURA = 1;

    public static final int CATEGORIA_GENERAL = 2;


    public static final int LOGRADO = 1;

    public static final int NO_LOGRADO = 2;


    public static final int TIPO_NEGOCIO = 1;

    public static final int TIPO_SEGMENTO = TIPO_NEGOCIO + 1;

    public static final int TIPO_SUBRUBRO = TIPO_SEGMENTO + 1;

    public static final int TIPO_LINEA = TIPO_SUBRUBRO + 1;

    public static final int TIPO_PROVEEDOR = TIPO_LINEA + 1;

    public static final int TIPO_ARTICULO = TIPO_PROVEEDOR + 1;

    private int idObjetivo;

    private Vendedor vendedor;

    private int tiObjetivo;

    private Date feDesde;

    private Date feHasta;

    private Proveedor proveedor;

    private String isAplica;

    private int tiCategoria;


    private Double caLograda;

    private boolean snAplica;

    private int idSecuencia;

    private List<VendedorObjetivoDetalle> objetivosDetalle;

    public int getIdObjetivo() {
        return idObjetivo;
    }

    public void setIdObjetivo(int idObjetivo) {
        this.idObjetivo = idObjetivo;
        this.idSecuencia = this.idObjetivo;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public int getTiObjetivo() {
        return tiObjetivo;
    }

    public void setTiObjetivo(int tiObjetivo) {
        this.tiObjetivo = tiObjetivo;
    }

    public Date getFeDesde() {
        return feDesde;
    }

    public void setFeDesde(Date feDesde) {
        this.feDesde = feDesde;
    }

    public Date getFeHasta() {
        return feHasta;
    }

    public void setFeHasta(Date feHasta) {
        this.feHasta = feHasta;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public int getIdSecuencia() {
        return idSecuencia;
    }

    public void setIdSecuencia(int idSecuencia) {
        this.idSecuencia = idSecuencia;
    }

    public List<VendedorObjetivoDetalle> getObjetivosDetalle() {
        return objetivosDetalle;
    }

    public void setObjetivosDetalle(List<VendedorObjetivoDetalle> objetivosDetalle) {
        this.objetivosDetalle = objetivosDetalle;
    }

    public int getTiCategoria() {
        return tiCategoria;
    }

    public void setTiCategoria(int tiCategoria) {
        this.tiCategoria = tiCategoria;
    }

    public boolean isSnAplica() {
        return snAplica;
    }

    public void setSnAplica(boolean snAplica) {
        this.snAplica = snAplica;
    }

    public Double getCaLograda() {
        return caLograda;
    }

    public void setCaLograda(Double caLograda) {
        this.caLograda = caLograda;
    }

    public static String getTipo(int tipo) {
        switch (tipo) {
            case TIPO_NEGOCIO:
                return "Negocio";
            case TIPO_SUBRUBRO:
                return "Subrubro";
            case TIPO_LINEA:
                return "Línea";
            case TIPO_PROVEEDOR:
                return "Proveedor";
            case TIPO_SEGMENTO:
                return "Segmento";
        }
        return "Artículo";

    }

    public static String getCategoria(int categoria) {
        if (categoria == CATEGORIA_COBERTURA) return "Cobertura";
        return "General";
    }


    public String getIsAplica() {
        return isAplica;
    }

    public void setIsAplica(String isAplica) {
        this.isAplica = isAplica;
        snAplica = isAplica.equalsIgnoreCase("S");
    }
}
