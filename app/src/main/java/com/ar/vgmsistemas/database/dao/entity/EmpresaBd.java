package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "empresas")
public class EmpresaBd implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "id_empresa")
    private int id;

    @ColumnInfo(name = "de_empresa")
    private String nombreEmpresa;

    @ColumnInfo(name = "id_fcnc_anticipo")
    private String documentoAnticipo;

    @ColumnInfo(name = "id_letra_anticipo")
    private String letraAnticipo;

    @ColumnInfo(name = "nu_cuota_anticipo")
    private long numeroCuotaAnticipo;

    @ColumnInfo(name = "sn_cobranza_estricta")
    private String tipoCobranza;

    @ColumnInfo(name = "id_fcnc_egreso")
    private String documentoEgreso;

    @ColumnInfo(name = "sn_descuento")
    private String isDescuentoActivo;

    @ColumnInfo(name = "id_lista_defecto")
    private int idListaDefecto;

    @ColumnInfo(name = "id_postal_defecto")
    private int idPostalDefecto;

    @ColumnInfo(name = "sn_registrar_localizacion")
    private String snRegistrarLocalizacion;

    @ColumnInfo(name = "pr_monto_min_factura")
    private double montoMinimoFactura = 0d;

    @ColumnInfo(name = "pr_monto_min_dto_factura")
    private double montoACubrirParaDescuento = 0d;

    @ColumnInfo(name = "ta_maxarticulocritico")
    private double tasaMaximaArticulosCriticos = 1d;

    @ColumnInfo(name = "nu_tolerancia")
    private int segundosTolerancia = 0;

    @ColumnInfo(name = "ta_ivanoinscripto")
    private double tasaIvaNoCategorizado = 0.105d;

    @ColumnInfo(name = "sn_multiempresa")
    private String snMultiEmpresa;

    @ColumnInfo(name = "ti_empresa")
    private Integer tipoEmpresa;

    @ColumnInfo(name = "sn_turno")
    private String snManejoTurno;

    @ColumnInfo(name = "sn_control_lim_disp")
    private String snControlLimiteDisp;

    @ColumnInfo(name = "sn_recibo_provisorio")
    private String snReciboProvisorios;

    @ColumnInfo(name = "sn_movil_recibo_dto")
    private String snMovilReciboDto;

    @ColumnInfo(name = "sn_clienteunico")
    private String snClienteUnico;

    @ColumnInfo(name = "anticipo_habilitado")
    private boolean anticipoHabilitado;

    @ColumnInfo(name = "sn_sum_iva_rep_movil")
    private String snSumIvaReporteMovil;

    @ColumnInfo(name = "cat_emp_repartidor")
    private int categoriasEmpRepartidor;

    @ColumnInfo(name = "sn_imprimir_id_empresa")
    private String snImprimirIdEmpresa;

    @ColumnInfo(name = "sn_imp_resumen_cobranza")
    private String snImpResumenCobranza;

    @ColumnInfo(name = "sn_catalogo")
    private String snCatalogo;

    @ColumnInfo(name = "dropboxToken")
    private String dropboxToken;

    @ColumnInfo(name = "dropboxAppName")
    private String dropboxAppName;

    @ColumnInfo(name = "sn_modif_cd_movil")
    private String snModifCdMovil;

    @ColumnInfo(name = "ti_metodo_impint")
    private Integer tiMetodoImpInt;

    @ColumnInfo(name = "sn_comboespecialxvend")
    private String snComboEspecialPorVendedor;

    @ColumnInfo(name = "pr_margen_tolerancia_movil")
    private Double precioMargenToteranciaMovil;

    @ColumnInfo(name = "sn_controla_recibo_bn")
    private String snControlaReciboBn;

    /*Getters-Setters*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getDocumentoAnticipo() {
        return documentoAnticipo;
    }

    public void setDocumentoAnticipo(String documentoAnticipo) {
        this.documentoAnticipo = documentoAnticipo;
    }

    public String getLetraAnticipo() {
        return letraAnticipo;
    }

    public void setLetraAnticipo(String letraAnticipo) {
        this.letraAnticipo = letraAnticipo;
    }

    public long getNumeroCuotaAnticipo() {
        return numeroCuotaAnticipo;
    }

    public void setNumeroCuotaAnticipo(long numeroCuotaAnticipo) {
        this.numeroCuotaAnticipo = numeroCuotaAnticipo;
    }

    public String getTipoCobranza() {
        return tipoCobranza;
    }

    public void setTipoCobranza(String tipoCobranza) {
        this.tipoCobranza = tipoCobranza;
    }

    public String getDocumentoEgreso() {
        return documentoEgreso;
    }

    public void setDocumentoEgreso(String documentoEgreso) {
        this.documentoEgreso = documentoEgreso;
    }

    public String getIsDescuentoActivo() {
        return isDescuentoActivo;
    }

    public void setIsDescuentoActivo(String isDescuentoActivo) {
        this.isDescuentoActivo = isDescuentoActivo;
    }

    public int getIdListaDefecto() {
        return idListaDefecto;
    }

    public void setIdListaDefecto(int idListaDefecto) {
        this.idListaDefecto = idListaDefecto;
    }

    public int getIdPostalDefecto() {
        return idPostalDefecto;
    }

    public void setIdPostalDefecto(int idPostalDefecto) {
        this.idPostalDefecto = idPostalDefecto;
    }

    public String getSnRegistrarLocalizacion() {
        return snRegistrarLocalizacion;
    }

    public void setSnRegistrarLocalizacion(String snRegistrarLocalizacion) {
        this.snRegistrarLocalizacion = snRegistrarLocalizacion;
    }

    public double getMontoMinimoFactura() {
        return montoMinimoFactura;
    }

    public void setMontoMinimoFactura(double montoMinimoFactura) {
        this.montoMinimoFactura = montoMinimoFactura;
    }

    public double getMontoACubrirParaDescuento() {
        return montoACubrirParaDescuento;
    }

    public void setMontoACubrirParaDescuento(double montoACubrirParaDescuento) {
        this.montoACubrirParaDescuento = montoACubrirParaDescuento;
    }

    public double getTasaMaximaArticulosCriticos() {
        return tasaMaximaArticulosCriticos;
    }

    public void setTasaMaximaArticulosCriticos(double tasaMaximaArticulosCriticos) {
        this.tasaMaximaArticulosCriticos = tasaMaximaArticulosCriticos;
    }

    public int getSegundosTolerancia() {
        return segundosTolerancia;
    }

    public void setSegundosTolerancia(int segundosTolerancia) {
        this.segundosTolerancia = segundosTolerancia;
    }

    public double getTasaIvaNoCategorizado() {
        return tasaIvaNoCategorizado;
    }

    public void setTasaIvaNoCategorizado(double tasaIvaNoCategorizado) {
        this.tasaIvaNoCategorizado = tasaIvaNoCategorizado;
    }

    public String getSnMultiEmpresa() {
        return snMultiEmpresa;
    }

    public void setSnMultiEmpresa(String snMultiEmpresa) {
        this.snMultiEmpresa = snMultiEmpresa;
    }

    public Integer getTipoEmpresa() {
        return tipoEmpresa;
    }

    public void setTipoEmpresa(Integer tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

    public String getSnManejoTurno() {
        return snManejoTurno;
    }

    public void setSnManejoTurno(String snManejoTurno) {
        this.snManejoTurno = snManejoTurno;
    }

    public String getSnControlLimiteDisp() {
        return snControlLimiteDisp;
    }

    public void setSnControlLimiteDisp(String snControlLimiteDisp) {
        this.snControlLimiteDisp = snControlLimiteDisp;
    }

    public String getSnReciboProvisorios() {
        return snReciboProvisorios;
    }

    public void setSnReciboProvisorios(String snReciboProvisorios) {
        this.snReciboProvisorios = snReciboProvisorios;
    }

    public String getSnMovilReciboDto() {
        return snMovilReciboDto;
    }

    public void setSnMovilReciboDto(String snMovilReciboDto) {
        this.snMovilReciboDto = snMovilReciboDto;
    }

    public String getSnClienteUnico() {
        return snClienteUnico;
    }

    public void setSnClienteUnico(String snClienteUnico) {
        this.snClienteUnico = snClienteUnico;
    }

    public boolean isAnticipoHabilitado() {
        return anticipoHabilitado;
    }

    public void setAnticipoHabilitado(boolean anticipoHabilitado) {
        this.anticipoHabilitado = anticipoHabilitado;
    }

    public String getSnSumIvaReporteMovil() {
        return snSumIvaReporteMovil;
    }

    public void setSnSumIvaReporteMovil(String snSumIvaReporteMovil) {
        this.snSumIvaReporteMovil = snSumIvaReporteMovil;
    }

    public int getCategoriasEmpRepartidor() {
        return categoriasEmpRepartidor;
    }

    public void setCategoriasEmpRepartidor(int categoriasEmpRepartidor) {
        this.categoriasEmpRepartidor = categoriasEmpRepartidor;
    }

    public String getSnImprimirIdEmpresa() {
        return snImprimirIdEmpresa;
    }

    public void setSnImprimirIdEmpresa(String snImprimirIdEmpresa) {
        this.snImprimirIdEmpresa = snImprimirIdEmpresa;
    }

    public String getSnImpResumenCobranza() {
        return snImpResumenCobranza;
    }

    public void setSnImpResumenCobranza(String snImpResumenCobranza) {
        this.snImpResumenCobranza = snImpResumenCobranza;
    }

    public String getSnCatalogo() {
        return snCatalogo;
    }

    public void setSnCatalogo(String snCatalogo) {
        this.snCatalogo = snCatalogo;
    }

    public String getDropboxToken() {
        return dropboxToken;
    }

    public void setDropboxToken(String dropboxToken) {
        this.dropboxToken = dropboxToken;
    }

    public String getDropboxAppName() {
        return dropboxAppName;
    }

    public void setDropboxAppName(String dropboxAppName) {
        this.dropboxAppName = dropboxAppName;
    }

    public String getSnModifCdMovil() {
        return snModifCdMovil;
    }

    public void setSnModifCdMovil(String snModifCdMovil) {
        this.snModifCdMovil = snModifCdMovil;
    }

    public Integer getTiMetodoImpInt() {
        return tiMetodoImpInt;
    }

    public void setTiMetodoImpInt(Integer tiMetodoImpInt) {
        this.tiMetodoImpInt = tiMetodoImpInt;
    }

    public String getSnComboEspecialPorVendedor() {
        return snComboEspecialPorVendedor;
    }

    public void setSnComboEspecialPorVendedor(String snComboEspecialPorVendedor) {
        this.snComboEspecialPorVendedor = snComboEspecialPorVendedor;
    }

    public Double getPrecioMargenToteranciaMovil() {
        return precioMargenToteranciaMovil;
    }

    public void setPrecioMargenToteranciaMovil(Double precioMargenToteranciaMovil) {
        this.precioMargenToteranciaMovil = precioMargenToteranciaMovil;
    }

    public String getSnControlaReciboBn() {
        return snControlaReciboBn;
    }

    public void setSnControlaReciboBn(String snControlaReciboBn) {
        this.snControlaReciboBn = snControlaReciboBn;
    }
}
