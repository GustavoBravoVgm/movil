package com.ar.vgmsistemas.entity;


public class RangoRentabilidad {
    /*Atributos/ columnas en bd*/
    private Integer id;

    private int idSucursal;

    private int idProveedor;

    private Double taDesde;

    private Double taHasta;

    private String snRentable;

    private String descripcion;

    private int idGrupoClie;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public Double getTaDesde() {
        return taDesde;
    }

    public void setTaDesde(Double taDesde) {
        this.taDesde = taDesde;
    }

    public Double getTaHasta() {
        return taHasta;
    }

    public void setTaHasta(Double taHasta) {
        this.taHasta = taHasta;
    }

    public String getSnRentable() {
        return snRentable;
    }

    public void setSnRentable(String snRentable) {
        this.snRentable = snRentable;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdGrupoClie() {
        return idGrupoClie;
    }

    public void setIdGrupoClie(Integer idGrupoClie) {
        this.idGrupoClie = idGrupoClie;
    }
}
