package com.ar.vgmsistemas.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Entrega implements Serializable {

    private static final long serialVersionUID = 375806855265851138L;

    @SerializedName("id")
    private int id;

    private double prEfectivoEntrega;

    private double prChequesEntrega;

    private double prRetencionesEntrega;

    private double prDepositoEntrega;

    private List<Cheque> cheques = new ArrayList<>();

    private List<Deposito> depositos = new ArrayList<>();

    private List<Retencion> retenciones = new ArrayList<>();

    private List<PagoEfectivo> pagosEfectivo = new ArrayList<>();

    public List<Cheque> getCheques() {
        return this.cheques;
    }

    public void setCheques(List<Cheque> cheques) {
        this.cheques = cheques;
    }

    public List<Retencion> getRetenciones() {
        return this.retenciones;
    }

    public void setRetenciones(List<Retencion> retenciones) {
        this.retenciones = retenciones;
    }

    public List<PagoEfectivo> getEntregasEfectivo() {
        return this.pagosEfectivo;
    }

    public void setPagosEfectivo(
            List<PagoEfectivo> entregasMonedasExtranjeras) {
        this.pagosEfectivo = entregasMonedasExtranjeras;
    }

    public double calcularTotalPagosEfectivo() {
        double total = 0d;
        for (PagoEfectivo em : this.pagosEfectivo) {
            total += em.getImporteMonedaCorriente();
        }
        return total;
    }

    public double calcularTotalEntregasCheque() {
        double total = 0d;
        for (Cheque ch : this.cheques) {
            total += ch.getImporte();
        }
        return total;
    }

    public double calcularTotalRetenciones() {
        double total = 0d;
        for (Retencion r : this.retenciones) {
            total += r.getImporte();
        }
        return total;
    }

    public double calcularTotalEntregasDeposito() {
        double total = 0d;
        for (Deposito dp : this.depositos) {
            total += dp.getImporte();
        }
        return total;
    }

    public double obtenerTotalPagos() {
        return this.calcularTotalEntregasCheque() + this.calcularTotalPagosEfectivo() + this.calcularTotalRetenciones() + this.calcularTotalEntregasDeposito();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Deposito> getDepositos() {
        return depositos;
    }

    public void setDepositos(List<Deposito> depositos) {
        this.depositos = depositos;
    }

    public double getPrEfectivoEntrega() {
        return prEfectivoEntrega;
    }

    public void setPrEfectivoEntrega(double prEfectivoEntrega) {
        this.prEfectivoEntrega = prEfectivoEntrega;
    }

    public void setPrChequesEntrega(double prChequesEntrega) {
        this.prChequesEntrega = prChequesEntrega;
    }

    public void setPrRetencionesEntrega(double prRetencionesEntrega) {
        this.prRetencionesEntrega = prRetencionesEntrega;
    }

    public void setPrDepositoEntrega(double prDepositoEntrega) {
        this.prDepositoEntrega = prDepositoEntrega;
    }

    public double getPrChequesEntrega() {
        return prChequesEntrega;
    }

    public double getPrRetencionesEntrega() {
        return prRetencionesEntrega;
    }

    public double getPrDepositoEntrega() {
        return prDepositoEntrega;
    }

    public List<PagoEfectivo> getPagosEfectivo() {
        return pagosEfectivo;
    }

    public Entrega(int id, double prEfectivoEntrega, double prChequesEntrega,
                   double prRetencionesEntrega, double prDepositoEntrega) {
        this.id = id;
        this.prEfectivoEntrega = prEfectivoEntrega;
        this.prChequesEntrega = prChequesEntrega;
        this.prRetencionesEntrega = prRetencionesEntrega;
        this.prDepositoEntrega = prDepositoEntrega;
    }

    public Entrega() {
    }
}
