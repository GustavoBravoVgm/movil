package com.ar.vgmsistemas.entity;

import com.ar.vgmsistemas.entity.key.PkDocumento;
import com.ar.vgmsistemas.utils.Formatter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class Documento implements Serializable {

    public static final int TI_FC_ELECTRONICA_CAE_ANTICIPADO = 5;

    public static final String PEDIDO_SUGERIDO = "PS";

    public static final String REMITO = "RR";

    public static final String TIPO_DOCUMENTO_PEDIDO_SUGERIDO = "PS";

    public static final String TIPO_DOCUMENTO_PEDIDO = "PD";

    public static final String TIPO_DOCUMENTO_FACTURA = "FC";

    public static final String TIPO_DOCUMENTO_EGRESO = "A";

    public static final String VENTA = "V";

    public static final String RETENCION = "C";

    public static final String TIPO_DOCUMENTOS_RECIBO = "R";

    public final static boolean SOLO_AVION = true;

    public final static int SOLO_AVION_INT = 1;

    public final static boolean TODOS = false;

    public final static int TODOS_INT = 0;


    public static final Integer FUNCION_FACTURA = 1;

    public static final Integer FUNCION_CREDITO = 2;

    public static final Integer FUNCION_DEBITO = 3;

    public static final String BLANCO = "B";

    public static final String NEGRO = "N";


    private static final long serialVersionUID = 1411747566534220062L;

    @SerializedName("id")
    private PkDocumento id;

    @SerializedName("ultimoNumeroDocumento")
    private long ultimoNumeroDocumento;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("tipoBlancoNegro")
    private String tipoBlancoNegro;

    private String isMovilVisible;

    private String tiAplicaStock;

    private String tipoDocumento;

    private int categoriaPlanCuenta;

    private int funcionTipoDocumento;

    private String snEstadistica;

    private int numeroLineas;

    private String isGenerar;

    private String isFacturaElectronica;

    private int tiAplicaFacturaElectronica;

    private int tiAplicaIva;

    private String snImprimirImpMovil;

    private String idDocAnulaFcNc;

    private String idDocAnulaTipoAB;

    private int idDocAnulaPtoVta;

    private String snPedidoRentable;

    private String idGenerarFcNc;

    private String idGenerarTipoAB;

    private int idGenerarPtoVta;

    private String snAplicaDgr;

    private Integer idListaDefault;

    private String tiModifVisualMovil;

    private String snVentaDirecta;

    private int tiImpresionMovil;

    private boolean snFacturaElectronica;

    /*GETTERS y SETTERS*/

    public PkDocumento getId() {
        return id;
    }

    public void setId(PkDocumento id) {
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

    public int getCategoriaPlanCuenta() {
        return categoriaPlanCuenta;
    }

    public void setCategoriaPlanCuenta(int categoriaPlanCuenta) {
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

    public int getNumeroLineas() {
        return numeroLineas;
    }

    public void setNumeroLineas(int numeroLineas) {
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
        this.snFacturaElectronica = Formatter.parseBooleanString(isFacturaElectronica);
    }

    public int getTiAplicaFacturaElectronica() {
        return tiAplicaFacturaElectronica;
    }

    public void setTiAplicaFacturaElectronica(int tiAplicaFacturaElectronica) {
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
        if (snImprimirImpMovil != null)
            this.snImprimirImpMovil = snImprimirImpMovil;
        else
            this.snImprimirImpMovil = "S";
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

    public int getIdDocAnulaPtoVta() {
        return idDocAnulaPtoVta;
    }

    public void setIdDocAnulaPtoVta(int idDocAnulaPtoVta) {
        this.idDocAnulaPtoVta = idDocAnulaPtoVta;
    }

    public String getSnPedidoRentable() {
        return snPedidoRentable;
    }

    public void setSnPedidoRentable(String snPedidoRentable) {
        if (snPedidoRentable != null)
            this.snPedidoRentable = snPedidoRentable;
        else
            this.snPedidoRentable = "N";
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

    public int getIdGenerarPtoVta() {
        return idGenerarPtoVta;
    }

    public void setIdGenerarPtoVta(int idGenerarPtoVta) {
        this.idGenerarPtoVta = idGenerarPtoVta;
    }

    public String getSnAplicaDgr() {
        return snAplicaDgr;
    }

    public void setSnAplicaDgr(String snAplicaDgr) {
        if (snAplicaDgr != null) {
            this.snAplicaDgr = snAplicaDgr;
        } else {
            this.snAplicaDgr = "S";
        }
    }

    public Integer getIdListaDefault() {
        return idListaDefault;
    }

    public void setIdListaDefault(Integer idListaDefault) {
        if (idListaDefault != null) {
            this.idListaDefault = idListaDefault;
        } else {
            this.idListaDefault = 0;
        }
    }

    public String getTiModifVisualMovil() {
        return tiModifVisualMovil;
    }

    public void setTiModifVisualMovil(String tiModifVisualMovil) {
        if (tiModifVisualMovil != null)
            this.tiModifVisualMovil = tiModifVisualMovil;
        else
            this.tiModifVisualMovil = "NO";
    }

    public String getSnVentaDirecta() {
        return snVentaDirecta;
    }

    public void setSnVentaDirecta(String snVentaDirecta) {
        if (snVentaDirecta != null)
            this.snVentaDirecta = snVentaDirecta;
        else
            this.snVentaDirecta = "N";
    }

    public int getTiImpresionMovil() {
        return tiImpresionMovil;
    }

    public void setTiImpresionMovil(int tiImpresionMovil) {
        this.tiImpresionMovil = tiImpresionMovil;
    }

    public boolean isLegal() {
        boolean isLegal = false;
        try {
            if (this.tipoBlancoNegro == null || this.tipoBlancoNegro.equals(Documento.BLANCO)) {
                isLegal = true;
            }
        } catch (Exception e) {
            isLegal = false;
        }
        return isLegal;
    }


    public boolean isSnFacturaElectronica() {
        return snFacturaElectronica;
    }

    public void setSnFacturaElectronica(boolean snFacturaElectronica) {
        this.snFacturaElectronica = snFacturaElectronica;
    }


    @Override
    public String toString() {
        return this.descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Documento documento = (Documento) o;

        if (ultimoNumeroDocumento != documento.ultimoNumeroDocumento) return false;
        if (categoriaPlanCuenta != documento.categoriaPlanCuenta) return false;
        if (funcionTipoDocumento != documento.funcionTipoDocumento) return false;
        if (numeroLineas != documento.numeroLineas) return false;
        if (tiAplicaIva != documento.tiAplicaIva) return false;
        if (snFacturaElectronica != documento.snFacturaElectronica) return false;
        if (tiAplicaFacturaElectronica != documento.tiAplicaFacturaElectronica) return false;
        if (idDocAnulaPtoVta != documento.idDocAnulaPtoVta) return false;
        if (!Objects.equals(id, documento.id)) return false;
        if (!Objects.equals(descripcion, documento.descripcion)) return false;
        if (!Objects.equals(tipoBlancoNegro, documento.tipoBlancoNegro)) return false;
        if (!Objects.equals(isMovilVisible, documento.isMovilVisible)) return false;
        if (!Objects.equals(tipoDocumento, documento.tipoDocumento)) return false;
        if (!Objects.equals(isGenerar, documento.isGenerar)) return false;
        if (!Objects.equals(idDocAnulaFcNc, documento.idDocAnulaFcNc)) return false;
        if (!Objects.equals(idDocAnulaTipoAB, documento.idDocAnulaTipoAB)) return false;
        if (!Objects.equals(snImprimirImpMovil, documento.snImprimirImpMovil)) return false;
        if (!Objects.equals(snAplicaDgr, documento.snAplicaDgr)) return false;
        if (!Objects.equals(idListaDefault, documento.idListaDefault)) return false;
        if (!Objects.equals(tiModifVisualMovil, documento.tiModifVisualMovil)) return false;
        if (!Objects.equals(snVentaDirecta, documento.snVentaDirecta)) return false;
        return Objects.equals(snPedidoRentable, documento.snPedidoRentable);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (int) (ultimoNumeroDocumento ^ (ultimoNumeroDocumento >>> 32));
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (tipoBlancoNegro != null ? tipoBlancoNegro.hashCode() : 0);
        result = 31 * result + (isMovilVisible != null ? isMovilVisible.hashCode() : 0);
        result = 31 * result + (tipoDocumento != null ? tipoDocumento.hashCode() : 0);
        result = 31 * result + categoriaPlanCuenta;
        result = 31 * result + funcionTipoDocumento;
        result = 31 * result + numeroLineas;
        result = 31 * result + tiAplicaIva;
        result = 31 * result + (isGenerar != null ? isGenerar.hashCode() : 0);
        result = 31 * result + (snFacturaElectronica ? 1 : 0);
        result = 31 * result + tiAplicaFacturaElectronica;
        result = 31 * result + (idDocAnulaFcNc != null ? idDocAnulaFcNc.hashCode() : 0);
        result = 31 * result + (idDocAnulaTipoAB != null ? idDocAnulaTipoAB.hashCode() : 0);
        result = 31 * result + idDocAnulaPtoVta;
        result = 31 * result + (snImprimirImpMovil != null ? snImprimirImpMovil.hashCode() : 0);
        result = 31 * result + (snPedidoRentable != null ? snPedidoRentable.hashCode() : 0);
        result = 31 * result + (snAplicaDgr != null ? snAplicaDgr.hashCode() : 0);
        result = 31 * result + (idListaDefault != null ? idListaDefault.hashCode() : 0);
        result = 31 * result + (tiModifVisualMovil != null ? tiModifVisualMovil.hashCode() : 0);

        return result;
    }

}
