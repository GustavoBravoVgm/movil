package com.ar.vgmsistemas.entity;

import com.ar.vgmsistemas.utils.Formatter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

public class NoPedido implements Serializable {
    private static final long serialVersionUID = 6876736428211005431L;
    public static final String TABLE = "No_pedidos";

    /*Atributos/ columnas en bd*/
    @SerializedName("id")
    private long id;

    private transient Date fechaNoPedido;

    @SerializedName("observacion")
    private String observacion;

    @SerializedName("motivoNoPedido")
    private MotivoNoPedido motivoNoPedido;

    @SerializedName("cliente")
    private Cliente cliente;

    @SerializedName("vendedor")
    private Vendedor vendedor;

    @SerializedName("fechaSincronizacion")
    private Date fechaSincronizacion;

    @SerializedName("idMovil")
    private String idMovil;

    private String snAnulo;

    private transient Date fechaRegistroMovil;

    @SerializedName("fechaNoPedido")
    private String fechaNoPedidoWs;

    @SerializedName("fechaRegistroMovil")
    private String fechaRegistroMovilWs;

    /*Getters-Setters*/
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Vendedor getVendedor() {
        return this.vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public MotivoNoPedido getMotivoNoPedido() {
        return this.motivoNoPedido;
    }

    public void setMotivoNoPedido(MotivoNoPedido motivoNoPedido) {
        this.motivoNoPedido = motivoNoPedido;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getObservacion() {
        return this.observacion;
    }

    public void setObservacion(String observacion) {
        if (observacion == null) observacion = "";
        this.observacion = observacion;
    }

    public Date getFechaNoPedido() {
        return this.fechaNoPedido;
    }

    public void setFechaNoPedido(Date fechaNoPedido) {
        this.fechaNoPedido = fechaNoPedido;
        try {
            this.setFechaNoPedidoWs(Formatter.formatDateWs(fechaNoPedido));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Date getFechaSincronizacion() {
        return this.fechaSincronizacion;
    }

    public void setFechaSincronizacion(Date fechaSincronizacion) {
        this.fechaSincronizacion = fechaSincronizacion;
    }

    public void setFechaNoPedidoWs(String fechaNoPedidoWs) {
        this.fechaNoPedidoWs = fechaNoPedidoWs;
    }

    public String getFechaNoPedidoWs() {
        return fechaNoPedidoWs;
    }

    public String getIdMovil() {
        return this.idMovil;
    }

    public void setIdMovil(String idMovil) {
        this.idMovil = idMovil;
    }

    public Date getFechaRegistroMovil() {
        //return _fechaRegistroMovil;
        if (fechaRegistroMovil == null && fechaRegistroMovilWs != null) {
            try {
                this.fechaRegistroMovil = Formatter.convertToDateTimeWs(fechaRegistroMovilWs);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return this.fechaRegistroMovil;
    }

    public void setFechaRegistroMovil(Date fechaRegistroMovil) {
        this.fechaRegistroMovil = fechaRegistroMovil;
        try {
            this.fechaRegistroMovilWs = Formatter.formatDateWs(fechaRegistroMovil);
        } catch (Exception ex) {
        }
    }

    public String getFechaRegistroMovilWs() {
        return fechaRegistroMovilWs;
    }

    public void setFechaRegistroMovilWs(String fechaRegistroMovilWs) {
        this.fechaRegistroMovilWs = fechaRegistroMovilWs;
    }


    public String getSnAnulo() {
        return snAnulo;
    }

    public void setSnAnulo(String _snAnulo) {
        this.snAnulo = _snAnulo;
    }

}
