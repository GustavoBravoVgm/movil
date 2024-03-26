package com.ar.vgmsistemas.entity;

import com.ar.vgmsistemas.entity.key.PkCheque;
import com.ar.vgmsistemas.utils.Formatter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

public class Cheque implements Serializable {
    private static final long serialVersionUID = 7698535072264710403L;

    @SerializedName("id")
    private PkCheque id;

    private int idEntrega;

    private Date fechaChequeMovil;

    @SerializedName("importe")
    private double importe;

    @SerializedName("cuit")
    private String cuit;

    private int idCliente;

    private int idSucursalCliente;

    @SerializedName("tipoCheque")
    private String tipoCheque;

    @SerializedName("fechaCheque")
    private String fechaWs;

    @SerializedName("banco")
    private Banco banco;

    private Localidad localidad;

    private Entrega entrega;

    public Cheque() {
        id = new PkCheque();
    }

    public PkCheque getId() {
        return this.id;
    }

    public void setId(PkCheque id) {
        this.id = id;
    }

    public double getImporte() {
        return this.importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public Date getFechaChequeMovil() {
        return this.fechaChequeMovil;
    }

    public void setFechaChequeMovil(Date fechaChequeMovil) {
        this.fechaChequeMovil = fechaChequeMovil;
        try {
            this.fechaWs = Formatter.formatDateWs(fechaChequeMovil);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Banco getBanco() {
        return this.banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
        id.setIdBanco(banco.getId());
    }

    public Localidad getLocalidad() {
        return this.localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
        id.setIdPostal(localidad.getId());
    }

    public void setEntrega(Entrega entrega) {
        this.entrega = entrega;
    }

    public Entrega getEntrega() {
        return entrega;
    }

    public void setFechaWs(String fechaWs) {
        this.fechaWs = fechaWs;
    }

    public String getFechaWs() {
        return fechaWs;
    }

    public String getCuit() {

        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }


    public String getTipoCheque() {
        return tipoCheque;
    }

    public void setTipoCheque(String tipoCheque) {
        this.tipoCheque = tipoCheque;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdSucursalCliente() {
        return idSucursalCliente;
    }

    public void setIdSucursalCliente(int idSucursalCliente) {
        this.idSucursalCliente = idSucursalCliente;
    }

    public int getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(int idEntrega) {
        this.idEntrega = idEntrega;
    }

    public Cheque(PkCheque id, int idEntrega, Date fechaChequeMovil, double importe, String cuit, int idCliente, int idSucursalCliente, String tipoCheque, String fechaWs, Banco banco, Localidad localidad, Entrega entrega) {
        this.id = id;
        this.idEntrega = idEntrega;
        this.fechaChequeMovil = fechaChequeMovil;
        this.importe = importe;
        this.cuit = cuit;
        this.idCliente = idCliente;
        this.idSucursalCliente = idSucursalCliente;
        this.tipoCheque = tipoCheque;
        this.fechaWs = fechaWs;
        this.banco = banco;
        this.localidad = localidad;
        this.entrega = entrega;
    }
}
