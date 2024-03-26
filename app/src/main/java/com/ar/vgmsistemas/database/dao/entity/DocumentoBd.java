package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;

import com.ar.vgmsistemas.database.dao.entity.key.PkDocumentoBd;

import java.io.Serializable;

@Entity(tableName = "documentos",
        primaryKeys = {"id_doc", "id_letra", "id_ptovta"})
public class DocumentoBd implements Serializable {
    @NonNull
    @Embedded
    private PkDocumentoBd id;

    @ColumnInfo(name = "id_numero")
    private long ultimoNumeroDocumento;

    @ColumnInfo(name = "de_documento")
    private String descripcion;

    @ColumnInfo(name = "ti_bn")
    private String tipoBlancoNegro;

    @ColumnInfo(name = "sn_movil")
    private String isMovilVisible;

    @ColumnInfo(name = "ti_aplica_stock")
    private String tiAplicaStock;

    @ColumnInfo(name = "ti_documento")
    private String tipoDocumento;

    @ColumnInfo(name = "id_categoria2")
    private Integer categoriaPlanCuenta;

    @ColumnInfo(name = "id_funcion")
    private int funcionTipoDocumento;

    @ColumnInfo(name = "sn_estadistica")
    private String snEstadistica;

    @ColumnInfo(name = "nu_lineas")
    private Integer numeroLineas;

    @ColumnInfo(name = "sn_generar")
    private String isGenerar;

    @ColumnInfo(name = "sn_factura_electronica")
    private String isFacturaElectronica;

    @ColumnInfo(name = "ti_aplica_factura_electronica")
    private Integer tiAplicaFacturaElectronica;

    @ColumnInfo(name = "ti_aplica_iva")
    private int tiAplicaIva;

    @ColumnInfo(name = "sn_imprimir_imp_movil")
    private String snImprimirImpMovil;

    @ColumnInfo(name = "id_docanula_fcnc")
    private String idDocAnulaFcNc;

    @ColumnInfo(name = "id_docanula_tipoab")
    private String idDocAnulaTipoAB;

    @ColumnInfo(name = "id_docanula_ptovta")
    private Integer idDocAnulaPtoVta;

    @ColumnInfo(name = "sn_pedido_rentable")
    private String snPedidoRentable;

    @ColumnInfo(name = "id_generar_fcnc")
    private String idGenerarFcNc;

    @ColumnInfo(name = "id_generar_tipoab")
    private String idGenerarTipoAB;

    @ColumnInfo(name = "id_generar_ptovta")
    private Integer idGenerarPtoVta;

    @ColumnInfo(name = "sn_aplica_dgr")
    private String snAplicaDgr;

    @ColumnInfo(name = "id_lista_default")
    private Integer idListaDefault;

    @ColumnInfo(name = "ti_modif_visual_movil")
    private String tiModifVisualMovil;

    @ColumnInfo(name = "sn_venta_directa")
    private String snVentaDirecta;

    @ColumnInfo(name = "ti_impresion_movil")
    private Integer tiImpresionMovil;

    /*GETTERS y SETTERS*/

    @NonNull
    public PkDocumentoBd getId() {
        return id;
    }

    public void setId(@NonNull PkDocumentoBd id) {
        this.id = id;
    }

    public long getUltimoNumeroDocumento() {
        return ultimoNumeroDocumento;
    }

    public void setUltimoNumeroDocumento(long ultimoNumeroDocumento) {
        this.ultimoNumeroDocumento = ultimoNumeroDocumento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoBlancoNegro() {
        return tipoBlancoNegro;
    }

    public void setTipoBlancoNegro(String tipoBlancoNegro) {
        this.tipoBlancoNegro = tipoBlancoNegro;
    }

    public String getIsMovilVisible() {
        return isMovilVisible;
    }

    public void setIsMovilVisible(String isMovilVisible) {
        this.isMovilVisible = isMovilVisible;
    }

    public String getTiAplicaStock() {
        return tiAplicaStock;
    }

    public void setTiAplicaStock(String tiAplicaStock) {
        this.tiAplicaStock = tiAplicaStock;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Integer getCategoriaPlanCuenta() {
        return categoriaPlanCuenta;
    }

    public void setCategoriaPlanCuenta(Integer categoriaPlanCuenta) {
        this.categoriaPlanCuenta = categoriaPlanCuenta;
    }

    public int getFuncionTipoDocumento() {
        return funcionTipoDocumento;
    }

    public void setFuncionTipoDocumento(int funcionTipoDocumento) {
        this.funcionTipoDocumento = funcionTipoDocumento;
    }

    public String getSnEstadistica() {
        return snEstadistica;
    }

    public void setSnEstadistica(String snEstadistica) {
        this.snEstadistica = snEstadistica;
    }

    public Integer getNumeroLineas() {
        return numeroLineas;
    }

    public void setNumeroLineas(Integer numeroLineas) {
        this.numeroLineas = numeroLineas;
    }

    public String getIsGenerar() {
        return isGenerar;
    }

    public void setIsGenerar(String isGenerar) {
        this.isGenerar = isGenerar;
    }

    public String getIsFacturaElectronica() {
        return isFacturaElectronica;
    }

    public void setIsFacturaElectronica(String isFacturaElectronica) {
        this.isFacturaElectronica = isFacturaElectronica;
    }

    public Integer getTiAplicaFacturaElectronica() {
        return tiAplicaFacturaElectronica;
    }

    public void setTiAplicaFacturaElectronica(Integer tiAplicaFacturaElectronica) {
        this.tiAplicaFacturaElectronica = tiAplicaFacturaElectronica;
    }

    public int getTiAplicaIva() {
        return tiAplicaIva;
    }

    public void setTiAplicaIva(int tiAplicaIva) {
        this.tiAplicaIva = tiAplicaIva;
    }

    public String getSnImprimirImpMovil() {
        return snImprimirImpMovil;
    }

    public void setSnImprimirImpMovil(String snImprimirImpMovil) {
        if (snImprimirImpMovil == null) snImprimirImpMovil = "S";
        this.snImprimirImpMovil = snImprimirImpMovil;
    }

    public String getIdDocAnulaFcNc() {
        return idDocAnulaFcNc;
    }

    public void setIdDocAnulaFcNc(String idDocAnulaFcNc) {
        this.idDocAnulaFcNc = idDocAnulaFcNc;
    }

    public String getIdDocAnulaTipoAB() {
        return idDocAnulaTipoAB;
    }

    public void setIdDocAnulaTipoAB(String idDocAnulaTipoAB) {
        this.idDocAnulaTipoAB = idDocAnulaTipoAB;
    }

    public Integer getIdDocAnulaPtoVta() {
        return idDocAnulaPtoVta;
    }

    public void setIdDocAnulaPtoVta(Integer idDocAnulaPtoVta) {
        this.idDocAnulaPtoVta = idDocAnulaPtoVta;
    }

    public String getSnPedidoRentable() {
        return snPedidoRentable;
    }

    public void setSnPedidoRentable(String snPedidoRentable) {
        this.snPedidoRentable = snPedidoRentable;
    }

    public String getIdGenerarFcNc() {
        return idGenerarFcNc;
    }

    public void setIdGenerarFcNc(String idGenerarFcNc) {
        this.idGenerarFcNc = idGenerarFcNc;
    }

    public String getIdGenerarTipoAB() {
        return idGenerarTipoAB;
    }

    public void setIdGenerarTipoAB(String idGenerarTipoAB) {
        this.idGenerarTipoAB = idGenerarTipoAB;
    }

    public Integer getIdGenerarPtoVta() {
        return idGenerarPtoVta;
    }

    public void setIdGenerarPtoVta(Integer idGenerarPtoVta) {
        this.idGenerarPtoVta = idGenerarPtoVta;
    }

    public String getSnAplicaDgr() {
        return snAplicaDgr;
    }

    public void setSnAplicaDgr(String snAplicaDgr) {
        this.snAplicaDgr = snAplicaDgr;
    }

    public Integer getIdListaDefault() {
        return idListaDefault;
    }

    public void setIdListaDefault(Integer idListaDefault) {
        this.idListaDefault = idListaDefault;
    }

    public String getTiModifVisualMovil() {
        return tiModifVisualMovil;
    }

    public void setTiModifVisualMovil(String tiModifVisualMovil) {
        this.tiModifVisualMovil = tiModifVisualMovil;
    }

    public String getSnVentaDirecta() {
        return snVentaDirecta;
    }

    public void setSnVentaDirecta(String snVentaDirecta) {
        this.snVentaDirecta = snVentaDirecta;
    }

    public Integer getTiImpresionMovil() {
        return tiImpresionMovil;
    }

    public void setTiImpresionMovil(Integer tiImpresionMovil) {
        this.tiImpresionMovil = tiImpresionMovil;
    }
}
