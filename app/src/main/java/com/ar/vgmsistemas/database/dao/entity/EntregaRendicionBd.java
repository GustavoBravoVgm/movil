package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;

import com.ar.vgmsistemas.database.dao.entity.key.PkEntregaRendicionBd;

@Entity(tableName = "entrega_rendicion",
        primaryKeys = {"id_veces", "id_legajo", "fe_entrega",})
public class EntregaRendicionBd {
    @NonNull
    @Embedded
    private PkEntregaRendicionBd id;
    @Ignore
    public static final String TABLE = "entrega_rendicion";

    @ColumnInfo(name = "pr_efectivo")
    private Double prEfectivo;
    @ColumnInfo(name = "pr_billete1")
    private Double prBillete1;
    @ColumnInfo(name = "pr_billete2")
    private Double prBillete2;
    @ColumnInfo(name = "pr_billete3")
    private Double prBillete3;
    @ColumnInfo(name = "pr_billete4")
    private Double prBillete4;
    @ColumnInfo(name = "pr_billete5")
    private Double prBillete5;
    @ColumnInfo(name = "pr_billete6")
    private Double prBillete6;
    @ColumnInfo(name = "pr_billete7")
    private Double prBillete7;
    @ColumnInfo(name = "pr_billete8")
    private Double prBillete8;

    @ColumnInfo(name = "ca_billete1")
    private Integer caBillete1;
    @ColumnInfo(name = "ca_billete2")
    private Integer caBillete2;
    @ColumnInfo(name = "ca_billete3")
    private Integer caBillete3;
    @ColumnInfo(name = "ca_billete4")
    private Integer caBillete4;
    @ColumnInfo(name = "ca_billete5")
    private Integer caBillete5;
    @ColumnInfo(name = "ca_billete6")
    private Integer caBillete6;
    @ColumnInfo(name = "ca_billete7")
    private Integer caBillete7;
    @ColumnInfo(name = "ca_billete8")
    private Integer caBillete8;

    @ColumnInfo(name = "pr_monedas")
    private Double prMonedas;

    @ColumnInfo(name = "pr_tickets")
    private Double prTickets;

    @ColumnInfo(name = "pr_cheques")
    private Double prCheques;

    @ColumnInfo(name = "pr_gastos")
    private Double prGastos;

    @ColumnInfo(name = "id_sucursal")
    private Long idSucursal;

    @ColumnInfo(name = "id_sucentrega")
    private Integer idSucentrega;

    @ColumnInfo(name = "id_movil")
    private String idMovil;

    @Ignore
    private Double prBanco;
    @Ignore
    private Double prOtros;
    @Ignore
    private Integer idBono;

    public PkEntregaRendicionBd getId() {
        return id;
    }

    public void setId(PkEntregaRendicionBd id) {
        this.id = id;
    }

    public Double getPrEfectivo() {
        return prEfectivo;
    }

    public void setPrEfectivo(Double prEfectivo) {
        this.prEfectivo = prEfectivo;
    }

    public Double getPrBillete1() {
        return prBillete1;
    }

    public void setPrBillete1(Double prBillete1) {
        this.prBillete1 = prBillete1;
    }

    public Double getPrBillete2() {
        return prBillete2;
    }

    public void setPrBillete2(Double prBillete2) {
        this.prBillete2 = prBillete2;
    }

    public Double getPrBillete3() {
        return prBillete3;
    }

    public void setPrBillete3(Double prBillete3) {
        this.prBillete3 = prBillete3;
    }

    public Double getPrBillete4() {
        return prBillete4;
    }

    public void setPrBillete4(Double prBillete4) {
        this.prBillete4 = prBillete4;
    }

    public Double getPrBillete5() {
        return prBillete5;
    }

    public void setPrBillete5(Double prBillete5) {
        this.prBillete5 = prBillete5;
    }

    public Double getPrBillete6() {
        return prBillete6;
    }

    public void setPrBillete6(Double prBillete6) {
        this.prBillete6 = prBillete6;
    }

    public Double getPrBillete7() {
        return prBillete7;
    }

    public void setPrBillete7(Double prBillete7) {
        this.prBillete7 = prBillete7;
    }

    public Double getPrBillete8() {
        return prBillete8;
    }

    public void setPrBillete8(Double prBillete8) {
        this.prBillete8 = prBillete8;
    }

    public Integer getCaBillete1() {
        return caBillete1;
    }

    public void setCaBillete1(Integer caBillete1) {
        this.caBillete1 = caBillete1;
    }

    public Integer getCaBillete2() {
        return caBillete2;
    }

    public void setCaBillete2(Integer caBillete2) {
        this.caBillete2 = caBillete2;
    }

    public Integer getCaBillete3() {
        return caBillete3;
    }

    public void setCaBillete3(Integer caBillete3) {
        this.caBillete3 = caBillete3;
    }

    public Integer getCaBillete4() {
        return caBillete4;
    }

    public void setCaBillete4(Integer caBillete4) {
        this.caBillete4 = caBillete4;
    }

    public Integer getCaBillete5() {
        return caBillete5;
    }

    public void setCaBillete5(Integer caBillete5) {
        this.caBillete5 = caBillete5;
    }

    public Integer getCaBillete6() {
        return caBillete6;
    }

    public void setCaBillete6(Integer caBillete6) {
        this.caBillete6 = caBillete6;
    }

    public Integer getCaBillete7() {
        return caBillete7;
    }

    public void setCaBillete7(Integer caBillete7) {
        this.caBillete7 = caBillete7;
    }

    public Integer getCaBillete8() {
        return caBillete8;
    }

    public void setCaBillete8(Integer caBillete8) {
        this.caBillete8 = caBillete8;
    }

    public Double getPrMonedas() {
        return prMonedas;
    }

    public void setPrMonedas(Double prMonedas) {
        this.prMonedas = prMonedas;
    }

    public Double getPrTickets() {
        return prTickets;
    }

    public void setPrTickets(Double prTickets) {
        this.prTickets = prTickets;
    }

    public Double getPrCheques() {
        return prCheques;
    }

    public void setPrCheques(Double prCheques) {
        this.prCheques = prCheques;
    }

    public Double getPrGastos() {
        return prGastos;
    }

    public void setPrGastos(Double prGastos) {
        this.prGastos = prGastos;
    }

    public Long getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Long idSucursal) {
        this.idSucursal = idSucursal;
    }

    public Integer getIdSucentrega() {
        return idSucentrega;
    }

    public void setIdSucentrega(Integer idSucentrega) {
        this.idSucentrega = idSucentrega;
    }

    public String getIdMovil() {
        return idMovil;
    }

    public void setIdMovil(String idMovil) {
        this.idMovil = idMovil;
    }

    public Double getPrBanco() {
        return prBanco;
    }

    public void setPrBanco(Double prBanco) {
        this.prBanco = prBanco;
    }

    public Double getPrOtros() {
        return prOtros;
    }

    public void setPrOtros(Double prOtros) {
        this.prOtros = prOtros;
    }

    public Integer getIdBono() {
        return idBono;
    }

    public void setIdBono(Integer idBono) {
        this.idBono = idBono;
    }
}
