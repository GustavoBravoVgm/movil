package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;

import com.ar.vgmsistemas.database.dao.entity.key.PkCompraBd;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "compra",
        primaryKeys = {"id_proveedor", "id_fcncnd", "id_ptovta", "id_numero"})
public class CompraBd implements Serializable {

    @NonNull
    @Embedded
    private PkCompraBd id;

    @ColumnInfo(name = "pr_subtotal")
    private double prSubtotal = 0d;

    @ColumnInfo(name = "ta_dto")
    private double taDto = 0d;

    @ColumnInfo(name = "pr_dto")
    private double prDto = 0d;

    @ColumnInfo(name = "pr_gravado")
    private double prGravado = 0d;

    @ColumnInfo(name = "pr_exento")
    private double prExento = 0d;

    @ColumnInfo(name = "pr_perc_ingr_bruto")
    private double prPercIngrBruto = 0d;

    @ColumnInfo(name = "pr_perc_iva")
    private double prPercIva = 0d;

    @ColumnInfo(name = "pr_ipinterno")
    private double prIpInterno = 0d;

    @ColumnInfo(name = "pr_iva")
    private double prIva = 0d;

    @ColumnInfo(name = "pr_compra")
    private double prCompra = 0d;

    @ColumnInfo(name = "fe_factura")
    private String feFactura;

    @ColumnInfo(name = "fe_ingreso")
    private String feIngreso;

    @ColumnInfo(name = "fe_iva")
    private String feIva;

    @ColumnInfo(name = "id_movil")
    private String idMovil;

    @ColumnInfo(name = "id_plancta")
    private Integer idPlancta;

    @ColumnInfo(name = "nu_cuit")
    private String nuCuit;

    @ColumnInfo(name = "id_tipoab")
    private String idLetra;

    @ColumnInfo(name = "id_sucprov")
    private Integer idSucProv;

    @ColumnInfo(name = "de_concepto")
    private String deConcepto;

    @ColumnInfo(name = "sn_anulado")
    private String snAnulo;

    @ColumnInfo(name = "id_hoja")
    private Integer idHoja;

    @ColumnInfo(name = "id_sucursal_hoja")
    private Integer idSucursal;

    public CompraBd() {
        setId(new PkCompraBd());
    }

    public PkCompraBd getId() {
        return id;
    }

    public void setId(PkCompraBd id) {
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

    public String getFeFactura() {
        return feFactura;
    }

    public void setFeFactura(String feFactura) {
        this.feFactura = feFactura;
    }

    public String getFeIngreso() {
        return feIngreso;
    }

    public void setFeIngreso(String feIngreso) {

        this.feIngreso = feIngreso;
    }

    public String getFeIva() {
        return feIva;
    }

    public void setFeIva(String feIva) {

        this.feIva = feIva;
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

    public Integer getIdHoja() {
        return idHoja;
    }

    public void setIdHoja(Integer idHoja) {
        this.idHoja = idHoja;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompraBd compra = (CompraBd) o;

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
        if (!Objects.equals(idMovil, compra.idMovil)) return false;
        if (!Objects.equals(idPlancta, compra.idPlancta)) return false;
        if (!Objects.equals(nuCuit, compra.nuCuit)) return false;
        if (!Objects.equals(idLetra, compra.idLetra)) return false;
        if (!Objects.equals(idSucProv, compra.idSucProv)) return false;
        if (!Objects.equals(deConcepto, compra.deConcepto)) return false;
        return false;
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
        result = 31 * result + (idMovil != null ? idMovil.hashCode() : 0);
        result = 31 * result + (idPlancta != null ? idPlancta.hashCode() : 0);
        result = 31 * result + (nuCuit != null ? nuCuit.hashCode() : 0);
        result = 31 * result + (idLetra != null ? idLetra.hashCode() : 0);
        result = 31 * result + (idSucProv != null ? idSucProv.hashCode() : 0);
        result = 31 * result + (deConcepto != null ? deConcepto.hashCode() : 0);
        return (int) result;
    }

}
