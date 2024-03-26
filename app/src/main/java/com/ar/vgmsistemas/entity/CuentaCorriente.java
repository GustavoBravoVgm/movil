package com.ar.vgmsistemas.entity;

import com.ar.vgmsistemas.entity.key.PkCuentaCorriente;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class CuentaCorriente implements Serializable {
    private static final long serialVersionUID = -8080848970335011070L;
    public static String DOCUMENTO_ANTICIPO = "AN";

    @SerializedName("id")
    private PkCuentaCorriente id;

    private Documento documento;

    private double totalPagado;

    private double totalNotaCredito;

    private Cliente cliente;

    private Vendedor vendedor;

    private double totalCuota;

    private int signo;

    @SerializedName("idMovil")
    private String idMovil;

    private CondicionVenta condicionVenta;

    private Date fechaVenta;

    private Double prSaldo;

    private long idRecibo;

    private int idRecPtoVta;

    @SerializedName("saldo")
    private Double saldoMovil;

    private Long idNumDocFiscal;

    @SerializedName("deCodAutRepMovilCtaCte")
    private String deCodAutRepMovilCtaCte;

    private double montoCuota;

    /**
     * @return el saldo de la cuenta corriente, ya considera el signo.
     * Saldo positivo para las facturas.
     * Saldo negativo para los creditos, adelantos, etc.
     */
    public double calcularSaldo() {
        return (this.totalCuota - this.totalPagado - this.totalNotaCredito) * this.signo;
    }

    public PkCuentaCorriente getId() {
        return this.id;
    }

    public String getIdAsString() {
        return getId().getIdDocumento() + getId().getIdLetra() + getId().getPuntoVenta() + "-" + getNumdoc();
    }

    public void setId(PkCuentaCorriente id) {
        this.id = id;
    }

    public double getTotalPagado() {
        return this.totalPagado;
    }

    public void setTotalPagado(double totalPagado) {
        this.totalPagado = totalPagado;
    }

    public double getMontoCuota() {
        return this.montoCuota;
    }

    public void setMontoCuota(double montoCuota) {
        this.montoCuota = montoCuota;
    }

    public void setTotalCuota(double totalCuota) {
        this.totalCuota = totalCuota;
    }

    public double getTotalCuota() {
        return totalCuota;
    }

    public void setTotalNotaCredito(double totalNotaCredito) {
        this.totalNotaCredito = totalNotaCredito;
    }

    public double getTotalNotaCredito() {
        return totalNotaCredito;
    }

    public void setSigno(int signo) {
        this.signo = signo;
    }

    public int getSigno() {
        return signo;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Date getFechaVenta() {
        return this.fechaVenta;
    }

    public void setCondicionVenta(CondicionVenta condicionVenta) {
        this.condicionVenta = condicionVenta;
    }

    public CondicionVenta getCondicionVenta() {
        return this.condicionVenta;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Vendedor getVendedor() {
        return this.vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public String getIdMovil() {
        return idMovil;
    }

    public void setIdMovil(String idMovil) {
        this.idMovil = idMovil;
    }

    /**
     * @return Retorno el saldo con dos decimales para luego poder comparar en el servidor
     */
    public Double getSaldoMovil() {
        return this.saldoMovil;
    }

    public Long getIdNumDocFiscal() {
        return idNumDocFiscal;
    }

    public void setIdNumDocFiscal(Long idNumDocFiscal) {
        this.idNumDocFiscal = idNumDocFiscal;
    }

    public void setSaldoMovil(Double saldoMovil) {
        if (saldoMovil == null) saldoMovil = 0d;
        if (saldoMovil < 0d) {
            saldoMovil = saldoMovil * (-1);
        }
        this.saldoMovil = saldoMovil;
    }

    /**
     * @return <b>id_numdoc</b> -> sn_factura_electronica = 'N'; <b>id_numdoc_fiscal</b> -> sn_factura_electronica = 'S';
     */
    public String getNumdoc() {
        if (documento != null && documento.isSnFacturaElectronica() && documento.getTiAplicaFacturaElectronica() != Documento.TI_FC_ELECTRONICA_CAE_ANTICIPADO) {
            return String.valueOf(getIdNumDocFiscal());
        }
        return String.valueOf(getId().getIdNumeroDocumento());
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public String getCodAutCtaCte() {
        return deCodAutRepMovilCtaCte;
    }

    public void setCodAutCtaCte(String codAutCtaCte) {
        if (codAutCtaCte == null) codAutCtaCte = "";
        this.deCodAutRepMovilCtaCte = codAutCtaCte;
    }

    public Double getPrSaldo() {
        return prSaldo;
    }

    public void setPrSaldo(Double prSaldo) {
        this.prSaldo = prSaldo;
    }

    public long getIdRecibo() {
        return idRecibo;
    }

    public void setIdRecibo(long idRecibo) {
        this.idRecibo = idRecibo;
    }

    public int getIdRecPtoVta() {
        return idRecPtoVta;
    }

    public void setIdRecPtoVta(int idRecPtoVta) {
        this.idRecPtoVta = idRecPtoVta;
    }

    public String getDeCodAutRepMovilCtaCte() {
        return deCodAutRepMovilCtaCte;
    }

    public void setDeCodAutRepMovilCtaCte(String deCodAutRepMovilCtaCte) {
        if (deCodAutRepMovilCtaCte == null) deCodAutRepMovilCtaCte = "";

        this.deCodAutRepMovilCtaCte = deCodAutRepMovilCtaCte;
    }
}
