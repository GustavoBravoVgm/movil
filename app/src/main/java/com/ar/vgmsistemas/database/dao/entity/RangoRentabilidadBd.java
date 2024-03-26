package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "rangos_rentabilidad")
public class RangoRentabilidadBd {

    /*Atributos/ columnas en bd*/
    @ColumnInfo(name = "id_rango")
    @PrimaryKey
    @NonNull
    private Integer id;

    @ColumnInfo(name = "id_sucursal")
    private Integer idSucursal;

    @ColumnInfo(name = "id_proveedor")
    private Integer idProveedor;

    @ColumnInfo(name = "ta_desde")
    private Double taDesde;

    @ColumnInfo(name = "ta_hasta")
    private Double taHasta;

    @ColumnInfo(name = "sn_rentable")
    private String snRentable;

    @ColumnInfo(name = "de_descripcion")
    private String descripcion;

    @ColumnInfo(name = "id_grupo_clie")
    private Integer idGrupoClie;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
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
