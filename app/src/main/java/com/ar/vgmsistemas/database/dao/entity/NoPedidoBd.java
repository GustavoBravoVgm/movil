package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "no_pedidos")
public class NoPedidoBd implements Serializable {
    /*Atributos/ columnas en bd*/
    @ColumnInfo(name = "id_no_pedido")
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "fe_no_pedido")
    private String fechaNoPedido;

    @ColumnInfo(name = "de_observacion")
    private String observacion;

    @ColumnInfo(name = "id_motivo")
    private int idMotivo;

    @ColumnInfo(name = "id_sucursal")
    private int idSucursal;

    @ColumnInfo(name = "id_cliente")
    private int idCliente;

    @ColumnInfo(name = "id_comercio")
    private int idComercio;

    @ColumnInfo(name = "id_vendedor")
    private int idVendedor;

    @ColumnInfo(name = "fe_sincronizacion")
    private String fechaSincronizacion;

    @ColumnInfo(name = "id_movil_no_pedidos")
    private String idMovil;

    @ColumnInfo(name = "sn_anulo")
    private String snAnulo;

    @ColumnInfo(name = "fe_registro_movil")
    private String fechaRegistroMovil;

    /*Getters-Setters*/

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFechaNoPedido() {
        return fechaNoPedido;
    }

    public void setFechaNoPedido(String fechaNoPedido) {
        this.fechaNoPedido = fechaNoPedido;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        if (observacion == null) observacion = "";
        this.observacion = observacion;
    }

    public int getIdMotivo() {
        return idMotivo;
    }

    public void setIdMotivo(int idMotivo) {
        this.idMotivo = idMotivo;
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

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getFechaSincronizacion() {
        return fechaSincronizacion;
    }

    public void setFechaSincronizacion(String fechaSincronizacion) {
        this.fechaSincronizacion = fechaSincronizacion;
    }

    public String getIdMovil() {
        return idMovil;
    }

    public void setIdMovil(String idMovil) {
        this.idMovil = idMovil;
    }

    public String getSnAnulo() {
        return snAnulo;
    }

    public void setSnAnulo(String snAnulo) {
        this.snAnulo = snAnulo;
    }

    public String getFechaRegistroMovil() {
        return fechaRegistroMovil;
    }

    public void setFechaRegistroMovil(String fechaRegistroMovil) {
        this.fechaRegistroMovil = fechaRegistroMovil;
    }
}
