package com.ar.vgmsistemas.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Empresa implements Serializable {

    public static final String SI = "S";
    public static final String NO = "N";
    public static final String CON_MOTIVO = "M";

    private static final long serialVersionUID = 343374132094197670L;

    @SerializedName("id")
    private int id;

    private String nombreEmpresa;

    private String documentoAnticipo;

    private String letraAnticipo;

    private long numeroCuotaAnticipo;

    private String tipoCobranza;

    private String documentoEgreso;

    private String isDescuentoActivo;

    private int idListaDefecto;

    private int idPostalDefecto;

    private boolean isRegistrarLocalizacion;

    private double montoMinimoFactura;

    private double montoACubrirParaDescuento;

    private double tasaMaximaArticulosCriticos;

    private int segundosTolerancia;

    private double tasaIvaNoCategorizado;

    private boolean isMultiEmpresa;

    private Integer tipoEmpresa;

    private boolean isManejoTurno;

    private boolean isControlLimiteDisp;

    private boolean isReciboProvisorio;

    private String snMovilReciboDto;

    private String snClienteUnico;

    private boolean anticipoHabilitado;

    private String snSumIvaReporteMovil;

    private int categoriasEmpRepartidor;

    private String snImprimirIdEmpresa;

    private String snImpResumenCobranza;

    private String snCatalogo;

    private String dropboxToken;

    private String dropboxAppName;

    private String snModifCdMovil;

    private Integer tiMetodoImpInt;

    private String snComboEspecialPorVendedor;

    private Double precioMargenToteranciaMovil;

    private String snControlaReciboBn;

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

    public Long getNumeroCuotaAnticipo() {
        return numeroCuotaAnticipo;
    }

    public boolean isControlLimiteDisp() {
        return isControlLimiteDisp;
    }

    public void setControlLimiteDisp(boolean isControlLimiteDisp) {
        this.isControlLimiteDisp = isControlLimiteDisp;
    }

    public void setNumeroCuotaAnticipo(Long numeroCuotaAnticipo) {
        this.numeroCuotaAnticipo = numeroCuotaAnticipo;
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

    public boolean isRegistrarLocalizacion() {
        return isRegistrarLocalizacion;
    }

    public void setRegistrarLocalizacion(boolean isRegistrarLocalizacion) {
        this.isRegistrarLocalizacion = isRegistrarLocalizacion;
    }

    public Double getMontoMinimoFactura() {
        return montoMinimoFactura;
    }

    public void setMontoMinimoFactura(Double montoMinimoFactura) {
        this.montoMinimoFactura = montoMinimoFactura;
    }

    public Double getMontoACubrirParaDescuento() {
        return montoACubrirParaDescuento;
    }

    public void setMontoACubrirParaDescuento(Double montoACubrirParaDescuento) {
        this.montoACubrirParaDescuento = montoACubrirParaDescuento;
    }

    public double getTasaMaximaArticulosCriticos() {
        return tasaMaximaArticulosCriticos;
    }

    public void setTasaMaximaArticulosCriticos(double tasaMaximaArticulosCriticos) {
        this.tasaMaximaArticulosCriticos = tasaMaximaArticulosCriticos;
    }

    public Integer getSegundosTolerancia() {
        return segundosTolerancia;
    }

    public void setSegundosTolerancia(Integer segundosTolerancia) {
        this.segundosTolerancia = segundosTolerancia;
    }

    public double getTasaIvaNoCategorizado() {
        return tasaIvaNoCategorizado;
    }

    public void setTasaIvaNoCategorizado(double tasaIvaNoCategorizado) {
        this.tasaIvaNoCategorizado = tasaIvaNoCategorizado;
    }

    public boolean isMultiEmpresa() {
        return isMultiEmpresa;
    }

    public void setMultiEmpresa(boolean isMultiEmpresa) {
        this.isMultiEmpresa = isMultiEmpresa;
    }

    public Integer getTipoEmpresa() {
        return tipoEmpresa;
    }

    public void setTipoEmpresa(Integer tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

    public boolean getIsManejoTurno() {
        return isManejoTurno;
    }

    public void setIsManejoTurno(boolean isManejoTurno) {
        this.isManejoTurno = isManejoTurno;
    }

    public boolean isReciboProvisorio() {
        return isReciboProvisorio;
    }

    public void setReciboProvisorio(boolean reciboProvisorio) {
        this.isReciboProvisorio = reciboProvisorio;
    }

    public String getTipoCobranza() {
        return tipoCobranza;
    }

    public void setTipoCobranza(String tipoCobranza) {
        this.tipoCobranza = tipoCobranza;
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
        if (snClienteUnico == null || snClienteUnico.equals("")) {
            this.snClienteUnico = NO;
        } else {
            this.snClienteUnico = snClienteUnico;
        }
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
        if (snSumIvaReporteMovil == null || snSumIvaReporteMovil.equals("")) {
            this.snSumIvaReporteMovil = "S";
        } else {
            this.snSumIvaReporteMovil = snSumIvaReporteMovil;
        }
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

    public Integer getTiMetodoImpInt() {
        return tiMetodoImpInt;
    }

    public void setTiMetodoImpInt(Integer tiMetodoImpInt) {
        this.tiMetodoImpInt = tiMetodoImpInt;
    }

    public Double getPrecioMargenToteranciaMovil() {
        return precioMargenToteranciaMovil;
    }

    public void setPrecioMargenToteranciaMovil(Double precioMargenToteranciaMovil) {
        this.precioMargenToteranciaMovil = precioMargenToteranciaMovil;
    }

    public int getIdPostalDefecto() {
        return idPostalDefecto;
    }

    public void setIdPostalDefecto(int idPostalDefecto) {
        this.idPostalDefecto = idPostalDefecto;
    }

    public String getSnComboEspecialPorVendedor() {
        return snComboEspecialPorVendedor;
    }

    public void setSnComboEspecialPorVendedor(String snComboEspecialPorVendedor) {
        this.snComboEspecialPorVendedor = snComboEspecialPorVendedor;
    }

    public void setNumeroCuotaAnticipo(long numeroCuotaAnticipo) {
        this.numeroCuotaAnticipo = numeroCuotaAnticipo;
    }

    public int getIdListaDefecto() {
        return idListaDefecto;
    }

    public void setIdListaDefecto(int idListaDefecto) {
        this.idListaDefecto = idListaDefecto;
    }

    public void setMontoMinimoFactura(double montoMinimoFactura) {
        this.montoMinimoFactura = montoMinimoFactura;
    }

    public void setMontoACubrirParaDescuento(double montoACubrirParaDescuento) {
        this.montoACubrirParaDescuento = montoACubrirParaDescuento;
    }

    public void setSegundosTolerancia(int segundosTolerancia) {
        this.segundosTolerancia = segundosTolerancia;
    }

    public boolean isManejoTurno() {
        return isManejoTurno;
    }

    public void setManejoTurno(boolean manejoTurno) {
        isManejoTurno = manejoTurno;
    }

    public String getSnModifCdMovil() {
        return snModifCdMovil;
    }

    public void setSnModifCdMovil(String snModifCdMovil) {
        this.snModifCdMovil = snModifCdMovil;
    }

    public String getSnControlaReciboBn() {
        return snControlaReciboBn;
    }

    public void setSnControlaReciboBn(String snControlaReciboBn) {
        this.snControlaReciboBn = snControlaReciboBn;
    }
}
