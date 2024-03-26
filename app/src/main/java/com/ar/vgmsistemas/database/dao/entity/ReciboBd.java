package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;

import com.ar.vgmsistemas.database.dao.entity.key.PkReciboBd;

import java.io.Serializable;
import java.util.Observable;

@Entity(tableName = "talon_recibo",
        primaryKeys = {"id_recpvta", "id_recibo"})
public class ReciboBd extends Observable implements Serializable {
    @NonNull
    @Embedded
    private PkReciboBd id;

    @ColumnInfo(name = "id_vendedor")
    private Integer idVendedor;

    @ColumnInfo(name = "id_sucursal")
    private Integer idSucursal;

    @ColumnInfo(name = "id_estado")
    private String idEstado;

    @ColumnInfo(name = "id_cliente")
    private Integer idCliente;

    @ColumnInfo(name = "fe_recibo")
    private String fechaMovil;

    @ColumnInfo(name = "pr_total")
    private Double total;

    @ColumnInfo(name = "id_movil")
    private String idMovil;

    @ColumnInfo(name = "fe_registro_movil")
    private String fechaRegistroMovil;

    @ColumnInfo(name = "id_entrega")
    private Integer idEntrega;

    @ColumnInfo(name = "de_observacion")
    private String observacion;

    @ColumnInfo(name = "id_comercio")
    private Integer idComercio;

    @ColumnInfo(name = "device_id")
    private String deviceId;

    @ColumnInfo(name = "resultado_envio")
    private Integer resultadoEnvio;

    /*Getters-Setters*/

    @NonNull
    public PkReciboBd getId() {
        return id;
    }

    public void setId(@NonNull PkReciboBd id) {
        this.id = id;
    }

    public Integer getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Integer idVendedor) {
        this.idVendedor = idVendedor;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(String idEstado) {
        this.idEstado = idEstado;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getFechaMovil() {
        return fechaMovil;
    }

    public void setFechaMovil(String fechaMovil) {
        this.fechaMovil = fechaMovil;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getIdMovil() {
        return idMovil;
    }

    public void setIdMovil(String idMovil) {
        this.idMovil = idMovil;
    }

    public String getFechaRegistroMovil() {
        return fechaRegistroMovil;
    }

    public void setFechaRegistroMovil(String fechaRegistroMovil) {
        this.fechaRegistroMovil = fechaRegistroMovil;
    }

    public Integer getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(Integer idEntrega) {
        this.idEntrega = idEntrega;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Integer getIdComercio() {
        return idComercio;
    }

    public void setIdComercio(Integer idComercio) {
        this.idComercio = idComercio;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getResultadoEnvio() {
        return resultadoEnvio;
    }

    public void setResultadoEnvio(Integer resultadoEnvio) {
        this.resultadoEnvio = resultadoEnvio;
    }
}
