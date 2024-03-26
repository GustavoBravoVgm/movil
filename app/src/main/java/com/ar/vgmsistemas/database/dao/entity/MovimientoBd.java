package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movimientos")
public class MovimientoBd {
    /*Atributos/ columnas en bd*/
    @ColumnInfo(name = "id_movimiento")
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "ti_movimiento")
    private String tipo;

    @ColumnInfo(name = "de_tabla")
    private String tabla;

    @ColumnInfo(name = "id_movil")
    private String idMovil;

    @ColumnInfo(name = "fe_sincronizacion")
    private String fechaSincronizacion;

    @ColumnInfo(name = "fe_movimiento")
    private String fechaMovimiento;

    @ColumnInfo(name = "nu_latitud_movimiento")
    private Double nuLatitudMovimiento;

    @ColumnInfo(name = "nu_longitud_movimiento")
    private Double nuLongitudMovimiento;

    @ColumnInfo(name = "fe_anulacion")
    private String fechaAnulacion;

    @ColumnInfo(name = "sn_modificado")
    private String snModificado;

    @ColumnInfo(name = "id_sucursal")
    private int idSucursal;

    @ColumnInfo(name = "id_cliente")
    private int idCliente;

    @ColumnInfo(name = "id_comercio")
    private int idComercio;

    /*Getters-Setters*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getIdMovil() {
        return idMovil;
    }

    public void setIdMovil(String idMovil) {
        this.idMovil = idMovil;
    }

    public String getFechaSincronizacion() {
        return fechaSincronizacion;
    }

    public void setFechaSincronizacion(String fechaSincronizacion) {
        this.fechaSincronizacion = fechaSincronizacion;
    }

    public String getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(String fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public Double getNuLatitudMovimiento() {
        return nuLatitudMovimiento;
    }

    public void setNuLatitudMovimiento(Double nuLatitudMovimiento) {
        this.nuLatitudMovimiento = nuLatitudMovimiento;
    }

    public Double getNuLongitudMovimiento() {
        return nuLongitudMovimiento;
    }

    public void setNuLongitudMovimiento(Double nuLongitudMovimiento) {
        this.nuLongitudMovimiento = nuLongitudMovimiento;
    }

    public String getFechaAnulacion() {
        return fechaAnulacion;
    }

    public void setFechaAnulacion(String fechaAnulacion) {
        this.fechaAnulacion = fechaAnulacion;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdComercio() {
        return idComercio;
    }

    public void setIdComercio(int idComercio) {
        this.idComercio = idComercio;
    }

    public String getSnModificado() {
        return snModificado;
    }

    public void setSnModificado(String snModificado) {
        this.snModificado = snModificado;
    }
}
