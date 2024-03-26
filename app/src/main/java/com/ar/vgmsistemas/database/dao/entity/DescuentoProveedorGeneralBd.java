package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "dto_proveedor")
public class DescuentoProveedorGeneralBd {

    @ColumnInfo(name = "id_dto_proveedor")
    @PrimaryKey
    private int idDescuentoProveedor;

    @ColumnInfo(name = "id_proveedor")
    private int idProveedor;

    @ColumnInfo(name = "id_negocio")
    private int idNegocio;

    @ColumnInfo(name = "id_segmento")
    private int idRubro;

    @ColumnInfo(name = "id_subrubro")
    private int idSubrubro;

    @ColumnInfo(name = "id_articulos")
    private int idArticulo;

    @ColumnInfo(name = "ta_dto_proveedor")
    private double tasaDescuento;

    @ColumnInfo(name = "nu_prioridad")
    private int prioridad;

    @ColumnInfo(name = "id_linea")
    private int idMarca;

    /*@ColumnInfo(name = "fe_inicio_vigencia")
    private String feInicioVigencia;

    @ColumnInfo(name = "fe_fin_vigencia")
    private String feFinVigencia;

    @ColumnInfo(name = "sn_aplica_cli")
    private String snAplicaCliente;*/


    /*Getters-Setters*/

    public int getIdDescuentoProveedor() {
        return idDescuentoProveedor;
    }

    public void setIdDescuentoProveedor(int idDescuentoProveedor) {
        this.idDescuentoProveedor = idDescuentoProveedor;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public int getIdNegocio() {
        return idNegocio;
    }

    public void setIdNegocio(int idNegocio) {
        this.idNegocio = idNegocio;
    }

    public int getIdRubro() {
        return idRubro;
    }

    public void setIdRubro(int idRubro) {
        this.idRubro = idRubro;
    }

    public int getIdSubrubro() {
        return idSubrubro;
    }

    public void setIdSubrubro(int idSubrubro) {
        this.idSubrubro = idSubrubro;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
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

   /* public String getFeInicioVigencia() {
        return feInicioVigencia;
    }

    public void setFeInicioVigencia(String feInicioVigencia) {
        this.feInicioVigencia = feInicioVigencia;
    }

    public String getFeFinVigencia() {
        return feFinVigencia;
    }

    public void setFeFinVigencia(String feFinVigencia) {
        this.feFinVigencia = feFinVigencia;
    }*/

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    /*public String getSnAplicaCliente() {
        return snAplicaCliente;
    }

    public void setSnAplicaCliente(String snAplicaCliente) {
        this.snAplicaCliente = snAplicaCliente;
    }*/
}
