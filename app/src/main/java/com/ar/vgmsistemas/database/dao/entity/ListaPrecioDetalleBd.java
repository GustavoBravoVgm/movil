package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;

import com.ar.vgmsistemas.database.dao.entity.key.PkListaPrecioDetalleBd;

import java.io.Serializable;

@Entity(tableName = "listasPreciosDetalle",
        primaryKeys = {"id_lista", "id_articulos", "ca_articulo_desde", "ca_articulo_hasta"})
public class ListaPrecioDetalleBd implements Serializable {
    @NonNull
    @Embedded
    private PkListaPrecioDetalleBd id;

    @ColumnInfo(name = "pr_final")
    private double precioFinal;

    @ColumnInfo(name = "pr_siva")
    private double precioSinIva;

    @ColumnInfo(name = "ca_lista")
    private double caLista = 0d;

    @ColumnInfo(name = "ca_vendido")
    private double caVendido = 0d;

    @ColumnInfo(name = "sn_palm")
    private String snMovil;

    @ColumnInfo(name = "fe_vigencia_desde")
    private String fechaVigenciaDesde;

    @ColumnInfo(name = "fe_vigencia_hasta")
    private String fechaVigenciaHasta;

    @ColumnInfo(name = "sn_vend_todos")
    private String snTodosLosVendedores;

    @ColumnInfo(name = "ta_dto_proveedor_lp")
    private Double tasaDtoProveedor;

    @ColumnInfo(name = "ta_dto_cliente_lp")
    private Double tasaDtoCliente;

    public double getPrecioFinal() {
        return this.precioFinal;
    }

    public void setPrecioFinal(double precioFinal) {
        this.precioFinal = precioFinal;
    }


    public double getPrecioSinIva() {
        return precioSinIva;
    }

    public void setPrecioSinIva(double precioSinIva) {
        this.precioSinIva = precioSinIva;
    }

    public PkListaPrecioDetalleBd getId() {
        return this.id;
    }

    public void setId(PkListaPrecioDetalleBd id) {
        this.id = id;
    }

    public String getSnMovil() {
        return snMovil;
    }

    public void setSnMovil(String snMovil) {
        this.snMovil = snMovil;
    }

    public String getFechaVigenciaDesde() {
        return fechaVigenciaDesde;
    }

    public void setFechaVigenciaDesde(String fechaVigenciaDesde) {
        this.fechaVigenciaDesde = fechaVigenciaDesde;
    }

    public String getFechaVigenciaHasta() {
        return fechaVigenciaHasta;
    }

    public void setFechaVigenciaHasta(String fechaVigenciaHasta) {
        this.fechaVigenciaHasta = fechaVigenciaHasta;
    }

    public Double getTasaDtoProveedor() {
        return tasaDtoProveedor;
    }

    public void setTasaDtoProveedor(Double tasaDtoProveedor) {
        this.tasaDtoProveedor = tasaDtoProveedor;
    }

    public Double getTasaDtoCliente() {
        return tasaDtoCliente;
    }

    public void setTasaDtoCliente(Double tasaDtoCliente) {
        this.tasaDtoCliente = tasaDtoCliente;
    }

    public double getCaLista() {
        return caLista;
    }

    public void setCaLista(double caLista) {
        this.caLista = caLista;
    }

    public double getCaVendido() {
        return caVendido;
    }

    public void setCaVendido(double caVendido) {
        this.caVendido = caVendido;
    }

    public String getSnTodosLosVendedores() {
        return snTodosLosVendedores;
    }

    public void setSnTodosLosVendedores(String snTodosLosVendedores) {
        if (snTodosLosVendedores == null) snTodosLosVendedores = "S";
        this.snTodosLosVendedores = snTodosLosVendedores;
    }
}
