package com.ar.vgmsistemas.entity;

import com.ar.vgmsistemas.entity.key.PkHojaDetalle;
import com.ar.vgmsistemas.utils.Matematica;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class HojaDetalle implements Serializable {
    public static final String CUENTA_CORRIENTE = "F";
    public static final String ANULADO = "A";
    public static final String PENDIENTE = "N";
    public static final String CONTADO = "C";
    public static final String SIN_ESTADO = "";

    public static final String NC = " 'NC' ";
    public static final String TABLE = "hoja_detalle";

    private static final long serialVersionUID = 1L;
    @SerializedName("pkHojaDetalle")
    private PkHojaDetalle id;

    @SerializedName("feVenta")
    private Date feVenta;

    @SerializedName("vendedor")
    private Vendedor vendedor;

    @SerializedName("prTotal")
    private double prTotal;

    @SerializedName("prNotaCredito")
    private double prNotaCredito;

    @SerializedName("prPagado")
    private double prPagado;

    @SerializedName("tiEstado")
    private String tiEstado;

    @SerializedName("cliente")
    private Cliente cliente;

    @SerializedName("idMovil")
    private String idMovil;

    @SerializedName("entregaCobranza")
    private Entrega entrega;

    private CondicionVenta condicionVenta;

    private MotivoAutorizacion motivoAutorizacion;

    private String codigoAutorizacion;

    @SerializedName("de_cod_autoriza_rep_movil")
    private String deCodAutRepMovilCtaCte;

    private Documento documento;

    public HojaDetalle() {
        entrega = new Entrega();
    }

    public PkHojaDetalle getId() {
        return id;
    }

    public void setId(PkHojaDetalle id) {
        this.id = id;
    }

    public Date getFeVenta() {
        return feVenta;
    }

    public void setFeVenta(Date feVenta) {
        this.feVenta = feVenta;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public double getPrTotal() {
        return prTotal;
    }

    public void setPrTotal(double prTotal) {
        this.prTotal = prTotal;
    }

    public double getPrPagado() {
        return prPagado;
    }

    public void setPrPagado(double prPagado) {
        this.prPagado = prPagado;
    }

    public double getPrNotaCredito() {
        return Matematica.Round(prNotaCredito, 2);
    }

    public void setPrNotaCredito(double prNotaCredito) {
        this.prNotaCredito = prNotaCredito;
    }

    public String getTiEstado() {
        if (tiEstado == null)
            return "";
        return tiEstado;
    }

    public void setTiEstado(String tiEstado) {
        this.tiEstado = tiEstado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Entrega getEntrega() {
        return entrega;
    }

    public void setEntrega(Entrega entrega) {
        this.entrega = entrega;
    }

    public String getIdMovil() {
        return idMovil;
    }

    public void setIdMovil(String idMovil) {
        this.idMovil = idMovil;
    }

    public CondicionVenta getCondicionVenta() {
        return condicionVenta;
    }

    public void setCondicionVenta(CondicionVenta condicionVenta) {
        this.condicionVenta = condicionVenta;
    }

    public String getCodigoAutorizacion() {
        return codigoAutorizacion;
    }

    public void setCodigoAutorizacion(String codigoAutorizacion) {
        this.codigoAutorizacion = codigoAutorizacion;
    }

    public MotivoAutorizacion getMotivoAutorizacion() {
        return motivoAutorizacion;
    }

    public void setMotivoAutorizacion(MotivoAutorizacion motivoAutorizacion) {
        this.motivoAutorizacion = motivoAutorizacion;
    }

    public String getCodAutCtaCte() {
        return deCodAutRepMovilCtaCte;
    }

    public void setCodAutCtaCte(String codAutCtaCte) {
        this.deCodAutRepMovilCtaCte = codAutCtaCte;
    }


    public Documento getDocumento() {

        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }


    public String getDeCodAutRepMovilCtaCte() {
        return deCodAutRepMovilCtaCte;
    }

    public void setDeCodAutRepMovilCtaCte(String deCodAutRepMovilCtaCte) {
        this.deCodAutRepMovilCtaCte = deCodAutRepMovilCtaCte;
    }
}
