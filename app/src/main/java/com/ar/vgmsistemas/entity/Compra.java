package com.ar.vgmsistemas.entity;

import com.ar.vgmsistemas.entity.key.PkCompra;
import com.ar.vgmsistemas.utils.Formatter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Compra implements Serializable {

    private static final SimpleDateFormat formatDateSqlite = new SimpleDateFormat("yyyy-MM-dd");

    public static final String SI = "S";
    public static final String NO = "N";

    public static final String EXTRA_COMPRA = "extra_compra";

    public static final String EXTRA_MODO = "extra_modo";

    public static final String TABLE = "compra";

    private PkCompra id;

    private double prSubtotal = 0d;

    private double taDto = 0d;

    private double prDto = 0d;

    private double prGravado = 0d;

    private double prExento = 0d;

    private double prPercIngrBruto = 0d;

    private double prPercIva = 0d;

    private double prIpInterno = 0d;

    private double prIva = 0d;

    private double prCompra = 0d;

    private Date feFactura;

    private Date feIngreso;

    private Date feIva;

    private String idMovil;

    private Integer idPlancta;

    private String nuCuit;

    private String idLetra;

    private Integer idSucProv;

    private String deConcepto;

    private String snAnulo;

    private int idHoja;

    private int idSucursal;

    @SerializedName("feFactura")

    private String feFacturaWs;

    @SerializedName("feIngreso")

    private String feIngresoWs;

    @SerializedName("feIva")

    private String feIvaWs;

    private List<ComprasImpuestos> comprasImpuestos = new ArrayList<>();

    public Compra() {
        setId(new PkCompra());
    }

    public PkCompra getId() {
        return id;
    }

    public void setId(PkCompra id) {
        this.id = id;
    }

    public double getPrSubtotal() {
        return prSubtotal;
    }

    public void setPrSubtotal(double prSubtotal) {
        this.prSubtotal = prSubtotal;
    }

    public double getTaDto() {
        return taDto;
    }

    public void setTaDto(double taDto) {
        this.taDto = taDto;
    }

    public double getPrDto() {
        return prDto;
    }

    public void setPrDto(double prDto) {
        this.prDto = prDto;
    }

    public double getPrGravado() {
        return prGravado;
    }

    public void setPrGravado(double prGravado) {
        this.prGravado = prGravado;
    }

    public double getPrExento() {
        return prExento;
    }

    public void setPrExento(double prExento) {
        this.prExento = prExento;
    }

    public double getPrPercIngrBruto() {
        return prPercIngrBruto;
    }

    public void setPrPercIngrBruto(double prPercIngrBruto) {
        this.prPercIngrBruto = prPercIngrBruto;
    }

    public double getPrPercIva() {
        return prPercIva;
    }

    public void setPrPercIva(double prPercIva) {
        this.prPercIva = prPercIva;
    }

    public double getPrIpInterno() {
        return prIpInterno;
    }

    public void setPrIpInterno(double prIpInterno) {
        this.prIpInterno = prIpInterno;
    }

    public double getPrIva() {
        return prIva;
    }

    public void setPrIva(double prIva) {
        this.prIva = prIva;
    }

    public double getPrCompra() {
        return prCompra;
    }

    public void setPrCompra(double prCompra) {
        this.prCompra = prCompra;
    }

    public List<ComprasImpuestos> getComprasImpuestos() {
        if (comprasImpuestos == null)
            return new ArrayList<>();
        return comprasImpuestos;
    }

    public void setComprasImpuestos(List<ComprasImpuestos> comprasImpuestos) {
        this.comprasImpuestos = comprasImpuestos;
    }


    public Date getFeFactura() {
        return feFactura;
    }

    public void setFeFactura(Date feFactura) {

        this.feFactura = feFactura;
        try {
            this.setFeFacturaWs(Formatter.formatDateWs(feFactura));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Date getFeIngreso() {
        return feIngreso;
    }

    public void setFeIngreso(Date feIngreso) {

        this.feIngreso = feIngreso;
        try {
            this.setFeIngresoWs(Formatter.formatDateWs(feIngreso));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Date getFeIva() {
        return feIva;
    }

    public void setFeIva(Date feIva) {

        this.feIva = feIva;
        try {
            this.setFeIvaWs(Formatter.formatDateWs(feIva));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getIdMovil() {
        return idMovil;
    }

    public void setIdMovil(String idMovil) {
        this.idMovil = idMovil;
    }

    public Integer getIdPlancta() {
        return idPlancta;
    }

    public void setIdPlancta(Integer idPlancta) {
        this.idPlancta = idPlancta;
    }

    public String getNuCuit() {
        return nuCuit;
    }

    public void setNuCuit(String nuCuit) {
        this.nuCuit = nuCuit;
    }

    public String getIdLetra() {
        return idLetra;
    }

    public void setIdLetra(String idLetra) {
        this.idLetra = idLetra;
    }

    public Integer getIdSucProv() {
        return idSucProv;
    }

    public void setIdSucProv(Integer idSucProv) {
        this.idSucProv = idSucProv;
    }

    public String getDeConcepto() {
        return deConcepto;
    }

    public void setDeConcepto(String deConcepto) {
        this.deConcepto = deConcepto;
    }

    public String getSnAnulo() {
        return snAnulo;
    }

    public void setSnAnulo(String snAnulo) {
        this.snAnulo = snAnulo;
    }

    public int getIdHoja() {
        return idHoja;
    }

    public void setIdHoja(int idHoja) {
        this.idHoja = idHoja;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getFeFacturaWs() {
        return feFacturaWs;
    }

    public void setFeFacturaWs(String feFacturaWs) {
        this.feFacturaWs = feFacturaWs;
    }

    public String getFeIngresoWs() {
        return feIngresoWs;
    }

    public void setFeIngresoWs(String feIngresoWs) {
        this.feIngresoWs = feIngresoWs;
    }

    public String getFeIvaWs() {
        return feIvaWs;
    }

    public void setFeIvaWs(String feIvaWs) {
        this.feIvaWs = feIvaWs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Compra compra = (Compra) o;

        if (Double.compare(compra.prSubtotal, prSubtotal) != 0) return false;
        if (Double.compare(compra.taDto, taDto) != 0) return false;
        if (Double.compare(compra.prDto, prDto) != 0) return false;
        if (Double.compare(compra.prGravado, prGravado) != 0) return false;
        if (Double.compare(compra.prExento, prExento) != 0) return false;
        if (Double.compare(compra.prPercIngrBruto, prPercIngrBruto) != 0) return false;
        if (Double.compare(compra.prPercIva, prPercIva) != 0) return false;
        if (Double.compare(compra.prIpInterno, prIpInterno) != 0) return false;
        if (Double.compare(compra.prIva, prIva) != 0) return false;
        if (Double.compare(compra.prCompra, prCompra) != 0) return false;
        if (idHoja != compra.idHoja) return false;
        if (idSucursal != compra.idSucursal) return false;
        if (!Objects.equals(id, compra.id)) return false;
        if (!Objects.equals(feFactura, compra.feFactura)) return false;
        if (!Objects.equals(feIngreso, compra.feIngreso)) return false;
        if (!Objects.equals(feIva, compra.feIva)) return false;
        if (!Objects.equals(snAnulo, compra.snAnulo)) return false;
        if (!Objects.equals(feFacturaWs, compra.feFacturaWs)) return false;
        if (!Objects.equals(feIngresoWs, compra.feIngresoWs)) return false;
        if (!Objects.equals(feIvaWs, compra.feIvaWs)) return false;
        if (!Objects.equals(idMovil, compra.idMovil)) return false;
        if (!Objects.equals(idPlancta, compra.idPlancta)) return false;
        if (!Objects.equals(nuCuit, compra.nuCuit)) return false;
        if (!Objects.equals(idLetra, compra.idLetra)) return false;
        if (!Objects.equals(idSucProv, compra.idSucProv)) return false;
        if (!Objects.equals(deConcepto, compra.deConcepto)) return false;
        return Objects.equals(comprasImpuestos, compra.comprasImpuestos);
    }

    @Override
    public int hashCode() {
        long result = id != null ? id.hashCode() : 0;
        result = 31 * result + (prSubtotal != +0.0d ? Double.doubleToLongBits(prSubtotal) : 0);
        result = 31 * result + (taDto != +0.0d ? Double.doubleToLongBits(taDto) : 0);
        result = 31 * result + (prDto != +0.0d ? Double.doubleToLongBits(prDto) : 0);
        result = 31 * result + (prGravado != +0.0d ? Double.doubleToLongBits(prGravado) : 0);
        result = 31 * result + (prExento != +0.0d ? Double.doubleToLongBits(prExento) : 0);
        result = 31 * result + (prPercIngrBruto != +0.0d ? Double.doubleToLongBits(prPercIngrBruto) : 0);
        result = 31 * result + (prPercIva != +0.0d ? Double.doubleToLongBits(prPercIva) : 0);
        result = 31 * result + (prIpInterno != +0.0d ? Double.doubleToLongBits(prIpInterno) : 0);
        result = 31 * result + (prIva != +0.0d ? Double.doubleToLongBits(prIva) : 0);
        result = 31 * result + (prCompra != +0.0d ? Double.doubleToLongBits(prCompra) : 0);
        result = 31 * result + (feFactura != null ? feFactura.hashCode() : 0);
        result = 31 * result + (feIngreso != null ? feIngreso.hashCode() : 0);
        result = 31 * result + (feIva != null ? feIva.hashCode() : 0);
        result = 31 * result + (snAnulo != null ? snAnulo.hashCode() : 0);
        result = 31 * result + idHoja;
        result = 31 * result + idSucursal;
        result = 31 * result + (feFacturaWs != null ? feFacturaWs.hashCode() : 0);
        result = 31 * result + (feIngresoWs != null ? feIngresoWs.hashCode() : 0);
        result = 31 * result + (feIvaWs != null ? feIvaWs.hashCode() : 0);
        result = 31 * result + (idMovil != null ? idMovil.hashCode() : 0);
        result = 31 * result + (idPlancta != null ? idPlancta.hashCode() : 0);
        result = 31 * result + (nuCuit != null ? nuCuit.hashCode() : 0);
        result = 31 * result + (idLetra != null ? idLetra.hashCode() : 0);
        result = 31 * result + (idSucProv != null ? idSucProv.hashCode() : 0);
        result = 31 * result + (deConcepto != null ? deConcepto.hashCode() : 0);
        result = 31 * result + (comprasImpuestos != null ? comprasImpuestos.hashCode() : 0);
        return (int) result;
    }

}
