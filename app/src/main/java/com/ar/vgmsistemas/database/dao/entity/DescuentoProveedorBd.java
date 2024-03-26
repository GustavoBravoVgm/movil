package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;

import com.ar.vgmsistemas.database.dao.entity.key.PkDescuentoProveedorBd;

import java.io.Serializable;

@Entity(tableName = "v_dto_proveedor_movil",
        primaryKeys = {"id_vendedor", "id_sucursal",
                "id_cliente", "id_comercio", "id_dto_proveedor_cliente", "id_dto_proveedor"},
        indices = {@Index(name = "vdpm_idx_suc_clie", value = {"id_sucursal", "id_cliente"})})
public class DescuentoProveedorBd implements Serializable {
    @NonNull
    @Embedded
    PkDescuentoProveedorBd id;

    @ColumnInfo(name = "de_dto_proveedor")
    private String descripcionDescuento;

    @ColumnInfo(name = "id_proveedor")
    private Integer idProveedor;

    @ColumnInfo(name = "id_negocio")
    private Integer idNegocio;

    @ColumnInfo(name = "id_segmento")
    private Integer idRubro;

    @ColumnInfo(name = "id_subrubro")
    private Integer idSubrubro;

    @ColumnInfo(name = "id_articulos")
    private Integer idArticulo;

    @ColumnInfo(name = "id_linea")
    private Integer idMarca;

    @ColumnInfo(name = "ta_dto_proveedor")
    private double tasaDescuento;

    @ColumnInfo(name = "nu_prioridad")
    private int prioridad;


    /*Getters-Setters*/

    @NonNull
    public PkDescuentoProveedorBd getId() {
        return id;
    }

    public void setId(@NonNull PkDescuentoProveedorBd id) {
        this.id = id;
    }

    public String getDescripcionDescuento() {
        return descripcionDescuento;
    }

    public void setDescripcionDescuento(String descripcionDescuento) {
        this.descripcionDescuento = descripcionDescuento;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Integer getIdNegocio() {
        return idNegocio;
    }

    public void setIdNegocio(Integer idNegocio) {
        this.idNegocio = idNegocio;
    }

    public Integer getIdRubro() {
        return idRubro;
    }

    public void setIdRubro(Integer idRubro) {
        this.idRubro = idRubro;
    }

    public Integer getIdSubrubro() {
        return idSubrubro;
    }

    public void setIdSubrubro(Integer idSubrubro) {
        this.idSubrubro = idSubrubro;
    }

    public Integer getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Integer idArticulo) {
        this.idArticulo = idArticulo;
    }

    public Integer getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }

    public double getTasaDescuento() {
        return tasaDescuento;
    }

    public void setTasaDescuento(double tasaDescuento) {
        this.tasaDescuento = tasaDescuento;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }
}
