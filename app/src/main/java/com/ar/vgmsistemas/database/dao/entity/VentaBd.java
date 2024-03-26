package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;

import com.ar.vgmsistemas.database.dao.entity.key.PkVentaBd;

import java.io.Serializable;
import java.util.Observable;

@Entity(tableName = "ventas",
        primaryKeys = {"id_fcnc", "id_tipoab", "id_ptovta", "id_numdoc"})
public class VentaBd extends Observable implements Serializable {
    @NonNull
    @Embedded
    private PkVentaBd id;

    @ColumnInfo(name = "id_condvta")
    private String idCondicionVenta;

    @ColumnInfo(name = "id_sucursal")
    private int idSucursal;

    @ColumnInfo(name = "id_cliente")
    private int idCliente;

    @ColumnInfo(name = "id_comercio")
    private int idComercio;

    @ColumnInfo(name = "pr_subtotal")
    private double subtotal;

    @ColumnInfo(name = "pr_dgr")
    private double totalRenta;

    @ColumnInfo(name = "pr_ivai")
    private double totalIva21;

    @ColumnInfo(name = "pr_iva2")
    private Double totalIva105;

    @ColumnInfo(name = "pr_total")
    private double total;

    // Descuento general
    @ColumnInfo(name = "ta_dto")
    private double tasaDescuento;

    @ColumnInfo(name = "pr_bonificacion")
    private double precioBonificacion;

    // Descuento por condicion de venta
    @ColumnInfo(name = "ta_dto_cvta")
    private Double tasaDescuentoCondicionVenta;

    @ColumnInfo(name = "pr_bonif_cvta")
    private Double precioBonificacionCondicionVenta;

    @ColumnInfo(name = "ta_dgr")
    private double tasaRenta;

    @ColumnInfo(name = "fe_registro")
    private String fechaRegistro;

    @ColumnInfo(name = "pr_exento")
    private double totalExento;

    @ColumnInfo(name = "id_vendedor")
    private int idVendedor;

    @ColumnInfo(name = "ta_dirsc")
    private Double tasaDirsc;

    @ColumnInfo(name = "pr_dirsc")
    private Double totalDirsc;

    @ColumnInfo(name = "pr_tot_imp_interno")
    private double totalImpuestoInterno;

    @ColumnInfo(name = "fe_sincronizacion")
    private String fechaSincronizacion;

    @ColumnInfo(name = "id_movil_venta")
    private String idMovil;

    @ColumnInfo(name = "fe_venta")
    private String fechaVenta;

    @ColumnInfo(name = "id_repartidor")
    private int idRepartidor;

    @ColumnInfo(name = "de_pie")
    private String pie;

    @ColumnInfo(name = "codigo_autorizacion")
    private String codigoAutorizacion;

    @ColumnInfo(name = "sn_anulo")
    private String anulo;

    @ColumnInfo(name = "cd_autorizacion_acc")
    private String codigoAutorizacionAccionComercial;

    @ColumnInfo(name = "pr_ivani")
    private double totalIvaNoCategorizado;

    @ColumnInfo(name = "fe_entrega")
    private String fechaEntrega;

    @ColumnInfo(name = "pr_subart")
    private double totalPorArticulo;

    @ColumnInfo(name = "id_pedido_ptovta")
    private Integer idPedidoPtoVta;

    @ColumnInfo(name = "ti_turno")
    private String tiTurno = "S";

    @ColumnInfo(name = "id_pedido_num")
    private Long idPedidoNum;

    @ColumnInfo(name = "id_pedido_doc")
    private String idPedidoDoc;

    @ColumnInfo(name = "id_pedido_tipoab")
    private String idPedidoTipoAb;

    @ColumnInfo(name = "id_hoja_integrado")
    private Integer idHojaIntegrado;

    @ColumnInfo(name = "de_cliente")
    private String deCliente;

    @ColumnInfo(name = "id_motivo_autoriza")
    private Integer idMotivoAutoriza;

    @ColumnInfo(name = "id_numdoc_fiscal")
    private Long idNumDocFiscal;

    @ColumnInfo(name = "sn_generado")
    private String snGenerado;

    @ColumnInfo(name = "id_fcfcnc")
    private String idFcFcnc;

    @ColumnInfo(name = "id_fctipoab")
    private String idFcTipoab;

    @ColumnInfo(name = "id_fcptovta")
    private Integer idFcPtovta;

    @ColumnInfo(name = "id_fcnumdoc")
    private Long idFcNumdoc;

    @ColumnInfo(name = "id_motivo_autoriza_pedr")
    private Integer idMotivoAutorizaPedr = null;

    @ColumnInfo(name = "sn_max_accom_superado")
    private String isMaxAccomSuperado;

    @ColumnInfo(name = "id_motivo_rechazo_nc")
    private Integer idMotivoRechazoNC;

    @ColumnInfo(name = "ti_nc")
    private String tiNC;

    /*Getters-Setters*/

    @NonNull
    public PkVentaBd getId() {
        return id;
    }

    public void setId(@NonNull PkVentaBd id) {
        this.id = id;
    }

    public String getIdCondicionVenta() {
        return idCondicionVenta;
    }

    public void setIdCondicionVenta(String idCondicionVenta) {
        this.idCondicionVenta = idCondicionVenta;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdComercio() {
        return idComercio;
    }

    public void setIdComercio(int idComercio) {
        this.idComercio = idComercio;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTotalRenta() {
        return totalRenta;
    }

    public void setTotalRenta(double totalRenta) {
        this.totalRenta = totalRenta;
    }

    public double getTotalIva21() {
        return totalIva21;
    }

    public void setTotalIva21(double totalIva21) {
        this.totalIva21 = totalIva21;
    }

    public Double getTotalIva105() {
        return totalIva105;
    }

    public void setTotalIva105(Double totalIva105) {
        this.totalIva105 = totalIva105;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTasaDescuento() {
        return tasaDescuento;
    }

    public void setTasaDescuento(double tasaDescuento) {
        this.tasaDescuento = tasaDescuento;
    }

    public double getPrecioBonificacion() {
        return precioBonificacion;
    }

    public void setPrecioBonificacion(double precioBonificacion) {
        this.precioBonificacion = precioBonificacion;
    }

    public Double getTasaDescuentoCondicionVenta() {
        return tasaDescuentoCondicionVenta;
    }

    public void setTasaDescuentoCondicionVenta(Double tasaDescuentoCondicionVenta) {
        this.tasaDescuentoCondicionVenta = tasaDescuentoCondicionVenta;
    }

    public Double getPrecioBonificacionCondicionVenta() {
        return precioBonificacionCondicionVenta;
    }

    public void setPrecioBonificacionCondicionVenta(Double precioBonificacionCondicionVenta) {
        this.precioBonificacionCondicionVenta = precioBonificacionCondicionVenta;
    }

    public double getTasaRenta() {
        return tasaRenta;
    }

    public void setTasaRenta(double tasaRenta) {
        this.tasaRenta = tasaRenta;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public double getTotalExento() {
        return totalExento;
    }

    public void setTotalExento(double totalExento) {
        this.totalExento = totalExento;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public Double getTasaDirsc() {
        return tasaDirsc;
    }

    public void setTasaDirsc(Double tasaDirsc) {
        this.tasaDirsc = tasaDirsc;
    }

    public Double getTotalDirsc() {
        return totalDirsc;
    }

    public void setTotalDirsc(Double totalDirsc) {
        this.totalDirsc = totalDirsc;
    }

    public double getTotalImpuestoInterno() {
        return totalImpuestoInterno;
    }

    public void setTotalImpuestoInterno(double totalImpuestoInterno) {
        this.totalImpuestoInterno = totalImpuestoInterno;
    }

    public String getFechaSincronizacion() {
        return fechaSincronizacion;
    }

    public void setFechaSincronizacion(String fechaSincronizacion) {
        this.fechaSincronizacion = fechaSincronizacion;
    }

    public String getIdMovil() {
        return idMovil;
    }

    public void setIdMovil(String idMovil) {
        this.idMovil = idMovil;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public int getIdRepartidor() {
        return idRepartidor;
    }

    public void setIdRepartidor(int idRepartidor) {
        this.idRepartidor = idRepartidor;
    }

    public String getPie() {
        return pie;
    }

    public void setPie(String pie) {
        this.pie = pie;
    }

    public String getCodigoAutorizacion() {
        return codigoAutorizacion;
    }

    public void setCodigoAutorizacion(String codigoAutorizacion) {
        this.codigoAutorizacion = codigoAutorizacion;
    }

    public String getAnulo() {
        return anulo;
    }

    public void setAnulo(String anulo) {
        this.anulo = anulo;
    }

    public String getCodigoAutorizacionAccionComercial() {
        return codigoAutorizacionAccionComercial;
    }

    public void setCodigoAutorizacionAccionComercial(String codigoAutorizacionAccionComercial) {
        this.codigoAutorizacionAccionComercial = codigoAutorizacionAccionComercial;
    }

    public double getTotalIvaNoCategorizado() {
        return totalIvaNoCategorizado;
    }

    public void setTotalIvaNoCategorizado(double totalIvaNoCategorizado) {
        this.totalIvaNoCategorizado = totalIvaNoCategorizado;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public double getTotalPorArticulo() {
        return totalPorArticulo;
    }

    public void setTotalPorArticulo(double totalPorArticulo) {
        this.totalPorArticulo = totalPorArticulo;
    }

    public Integer getIdPedidoPtoVta() {
        return idPedidoPtoVta;
    }

    public void setIdPedidoPtoVta(Integer idPedidoPtoVta) {
        this.idPedidoPtoVta = idPedidoPtoVta;
    }

    public String getTiTurno() {
        return tiTurno;
    }

    public void setTiTurno(String tiTurno) {
        this.tiTurno = tiTurno;
    }

    public Long getIdPedidoNum() {
        return idPedidoNum;
    }

    public void setIdPedidoNum(Long idPedidoNum) {
        this.idPedidoNum = idPedidoNum;
    }

    public String getIdPedidoDoc() {
        return idPedidoDoc;
    }

    public void setIdPedidoDoc(String idPedidoDoc) {
        this.idPedidoDoc = idPedidoDoc;
    }

    public String getIdPedidoTipoAb() {
        return idPedidoTipoAb;
    }

    public void setIdPedidoTipoAb(String idPedidoTipoAb) {
        this.idPedidoTipoAb = idPedidoTipoAb;
    }

    public Integer getIdHojaIntegrado() {
        return idHojaIntegrado;
    }

    public void setIdHojaIntegrado(Integer idHojaIntegrado) {
        this.idHojaIntegrado = idHojaIntegrado;
    }

    public String getDeCliente() {
        return deCliente;
    }

    public void setDeCliente(String deCliente) {
        this.deCliente = deCliente;
    }

    public Integer getIdMotivoAutoriza() {
        return idMotivoAutoriza;
    }

    public void setIdMotivoAutoriza(Integer idMotivoAutoriza) {
        this.idMotivoAutoriza = idMotivoAutoriza;
    }

    public Long getIdNumDocFiscal() {
        return idNumDocFiscal;
    }

    public void setIdNumDocFiscal(Long idNumDocFiscal) {
        this.idNumDocFiscal = idNumDocFiscal;
    }

    public String getSnGenerado() {
        return snGenerado;
    }

    public void setSnGenerado(String snGenerado) {
        this.snGenerado = snGenerado;
    }

    public String getIdFcFcnc() {
        return idFcFcnc;
    }

    public void setIdFcFcnc(String idFcFcnc) {
        this.idFcFcnc = idFcFcnc;
    }

    public String getIdFcTipoab() {
        return idFcTipoab;
    }

    public void setIdFcTipoab(String idFcTipoab) {
        this.idFcTipoab = idFcTipoab;
    }

    public Integer getIdFcPtovta() {
        return idFcPtovta;
    }

    public void setIdFcPtovta(Integer idFcPtovta) {
        this.idFcPtovta = idFcPtovta;
    }

    public Long getIdFcNumdoc() {
        return idFcNumdoc;
    }

    public void setIdFcNumdoc(Long idFcNumdoc) {
        this.idFcNumdoc = idFcNumdoc;
    }

    public Integer getIdMotivoAutorizaPedr() {
        return idMotivoAutorizaPedr;
    }

    public void setIdMotivoAutorizaPedr(Integer idMotivoAutorizaPedr) {
        this.idMotivoAutorizaPedr = idMotivoAutorizaPedr;
    }

    public String getIsMaxAccomSuperado() {
        return isMaxAccomSuperado;
    }

    public void setIsMaxAccomSuperado(String isMaxAccomSuperado) {
        this.isMaxAccomSuperado = isMaxAccomSuperado;
    }

    public Integer getIdMotivoRechazoNC() {
        return idMotivoRechazoNC;
    }

    public void setIdMotivoRechazoNC(Integer idMotivoRechazoNC) {
        this.idMotivoRechazoNC = idMotivoRechazoNC;
    }

    public String getTiNC() {
        return tiNC;
    }

    public void setTiNC(String tiNC) {
        this.tiNC = tiNC;
    }
}
