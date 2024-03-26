package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "proveedores")
public class ProveedorBd implements Serializable {
    @ColumnInfo(name = "id_proveedor")
    @PrimaryKey
    @NonNull
    private Integer idProveedor;

    @ColumnInfo(name = "de_proveedor")
    private String deProveedor;

    @ColumnInfo(name = "id_plancta")
    private Integer idPlancta;

    @ColumnInfo(name = "id_sucursal")
    private Integer idSucursal;

    @ColumnInfo(name = "ti_proveedor")
    private String tiProveedor;

    @ColumnInfo(name = "nu_cuit")
    private String nuCuit;

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getDeProveedor() {
        return deProveedor;
    }

    public void setDeProveedor(String deProveedor) {
        this.deProveedor = deProveedor;
    }

    public Integer getIdPlancta() {
        return idPlancta;
    }

    public void setIdPlancta(Integer idPlancta) {
        this.idPlancta = idPlancta;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getTiProveedor() {
        return tiProveedor;
    }

    public void setTiProveedor(String tiProveedor) {
        this.tiProveedor = tiProveedor;
    }

    public String getNuCuit() {
        return nuCuit;
    }

    public void setNuCuit(String nuCuit) {
        this.nuCuit = nuCuit;
    }

}
