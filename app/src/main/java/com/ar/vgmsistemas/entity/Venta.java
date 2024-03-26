package com.ar.vgmsistemas.entity;

import com.ar.vgmsistemas.entity.key.PkVenta;
import com.ar.vgmsistemas.utils.Formatter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;

public class Venta extends Observable implements Serializable {

    public static final String SI = "S";

    public static final String NO = "N";

    public static final String ANULADO = "A";

    private static final long serialVersionUID = -8930011811769915730L;

    public static final String TABLE = "ventas";

    @SerializedName("id")
    private PkVenta id;

    @SerializedName("condicionVenta")
    private CondicionVenta condicionVenta;

    @SerializedName("cliente")
    private Cliente cliente;

    @SerializedName("subtotal")
    private double subtotal;

    @SerializedName("totalRenta")
    private double totalRenta;

    @SerializedName("totalIva21")
    private double totalIva21;

    @SerializedName("totalIva105")
    private double totalIva105;

    @SerializedName("total")
    private double total;

    // Descuento general
    @SerializedName("tasaDescuento")
    private double tasaDescuento;

    @SerializedName("precioBonificacion")
    private double precioBonificacion;

    // Descuento por condicion de venta
    @SerializedName("tasaDescuentoCondicionVenta")
    private double tasaDescuentoCondicionVenta;

    @SerializedName("precioBonificacionCondicionVenta")
    private double precioBonificacionCondicionVenta;

    @SerializedName("tasaRenta")
    private double tasaRenta;

    private transient Date fechaRegistro;

    @SerializedName("fechaRegistro")
    private String fechaRegistroWs;

    @SerializedName("totalExento")
    private double totalExento;

    @SerializedName("vendedor")
    private Vendedor vendedor;

    @SerializedName("VentaDetalle")
    private List<VentaDetalle> detalles;

    @SerializedName("tasaDirsc")
    private double tasaDirsc;

    @SerializedName("totalDirsc")
    private double totalDirsc;

    @SerializedName("totalImpuestoInterno")
    private double totalImpuestoInterno;

    private Date fechaSincronizacion;

    @SerializedName("idMovil")
    private String idMovil;

    private transient Date fechaVenta;
    @SerializedName("fechaVenta")
    private String fechaVentaWs;

    @SerializedName("repartidor")
    private Repartidor repartidor;

    @SerializedName("pie")
    private String pie;

    @SerializedName("codigoAutorizacion")
    private String codigoAutorizacion;

    private String anulo;

    @SerializedName("codigoAutorizacionAccionComercial")
    private String codigoAutorizacionAccionComercial;

    @SerializedName("totalIvaNoCategorizado")
    private double totalIvaNoCategorizado;

    private Date fechaEntrega;

    @SerializedName("totalPorArticulo")
    private double totalPorArticulo;

    @SerializedName("idPedidoPtoVta")
    private Integer idPedidoPtoVta;

    @SerializedName("tiTurno")
    private String tiTurno = "S";

    @SerializedName("idPedidoNum")
    private Long idPedidoNum;

    @SerializedName("idPedidoDoc")
    private String idPedidoDoc;

    @SerializedName("idPedidoTipoAb")
    private String idPedidoTipoAb;

    private Integer idHojaIntegrado;

    private String deCliente;

    @SerializedName("motivoAutorizacion")
    private MotivoAutorizacion motivoAutorizacion;

    private Long idNumDocFiscal;

    private String snGenerado;

    @SerializedName("idFcFcnc")
    private String idFcFcnc;

    @SerializedName("idFcTipoab")
    private String idFcTipoab;

    @SerializedName("idFcPtovta")
    private Integer idFcPtovta;

    @SerializedName("idFcNumdoc")
    private Long idFcNumdoc;

    private Integer idMotivoAutorizaPedr = null;

    private String isMaxAccomSuperado;

    private Integer idMotivoRechazoNC;

    private String tiNC;


    private double totalIva21Mas105Sdto;

    private boolean isRentableXEmpresa;

    private String nivelRentabilidadEmpresa;

    private boolean isRentableXProveedor = true;

    private String nivelRentabilidadProveedor;

    @SerializedName("prBonifCiva")

    private double prBonifCiva;
    @SerializedName("prBonifCvCiva")

    private double prBonifCvCiva;

    private boolean tieneDescuento = false;

    private double montoTotalArticulosCriticos;

    private Documento documento;


    private double totalDescuentosDetalle;

    public String getSnGenerado() {
        if (snGenerado != null) {
            return snGenerado;
        }
        return "";
    }

    public void setSnGenerado(String snGenerado) {
        this.snGenerado = snGenerado;
    }

    public Documento getDocumento() {
        return this.documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public PkVenta getId() {
        return this.id;
    }

    public String getNumDoc() {
        DecimalFormat df = new DecimalFormat("00000000");
        return (documento.isSnFacturaElectronica() && documento.getTiAplicaFacturaElectronica() != Documento.TI_FC_ELECTRONICA_CAE_ANTICIPADO) ? String.valueOf(getIdNumDocFiscal()) : df.format(getId().getIdNumeroDocumento());
    }

    public void setId(PkVenta id) {
        this.id = id;
    }

    public CondicionVenta getCondicionVenta() {
        return this.condicionVenta;
    }

    public void setCondicionVenta(CondicionVenta condicionVenta) {
        this.condicionVenta = condicionVenta;
        this.setChanged();
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        this.setChanged();
    }

    public double getSubtotal() {
        return this.subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
        this.setChanged();
    }

    public double getTotalRenta() {
        return this.totalRenta;
    }

    public void setTotalRenta(double totalRenta) {
        this.totalRenta = totalRenta;
        this.setChanged();
    }

    public double getTotalIva21() {
        return this.totalIva21;
    }

    public void setTotalIva21(double totalIva21) {
        this.totalIva21 = totalIva21;
        this.setChanged();
    }

    public double getTotalIva105() {
        return this.totalIva105;
    }

    public void setTotalIva105(double totalIva105) {
        this.totalIva105 = totalIva105;
        this.setChanged();
    }

    public double getTotal() {
        return this.total;
    }

    public void setTotal(double total) {
        this.total = total;
        this.setChanged();
    }

    public double getTasaDescuento() {
        return this.tasaDescuento;
    }

    public void setTasaDescuento(double tasaDescuento) {
        this.tasaDescuento = tasaDescuento;
        this.setChanged();
    }

    public double getTasaRenta() {
        return this.tasaRenta;
    }

    public void setTasaRenta(double tasaRenta) {
        this.tasaRenta = tasaRenta;
        this.setChanged();
    }

    public Date getFechaRegistro() {
        if (fechaRegistro == null && fechaRegistroWs != null) {
            try {
                fechaRegistro = Formatter.convertToDateTimeWs(fechaRegistroWs);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return this.fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
        try {
            fechaRegistroWs = Formatter.formatDateWs(fechaRegistro);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public double getTotalExento() {
        return this.totalExento;
    }

    public void setTotalExento(double totalExento) {
        this.totalExento = totalExento;
        this.setChanged();
    }

    public Vendedor getVendedor() {
        return this.vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
        this.setChanged();
    }

    public List<VentaDetalle> getDetalles() {
        if (detalles == null) {
            detalles = new ArrayList<>();
        }

        return detalles;
    }

    public void setDetalles(List<VentaDetalle> detalles) {
        this.detalles = detalles;
        this.setChanged();
    }

    public double getPrecioBonificacion() {
        return this.precioBonificacion;
    }

    public void setPrecioBonificacion(double precioBonificacion) {
        this.precioBonificacion = precioBonificacion;
        this.setChanged();
    }

    public double getTotalDescuentosDetalle() {
        return this.totalDescuentosDetalle;
    }

    public void setTotalDescuentosDetalle(double totalDescuentos) {
        this.totalDescuentosDetalle = totalDescuentos;
        this.setChanged();
    }

    public double getTasaDirsc() {
        return this.tasaDirsc;
    }

    public void setTasaDirsc(double tasaDirsc) {
        this.tasaDirsc = tasaDirsc;
        this.setChanged();
    }

    public double getTotalDirsc() {
        return this.totalDirsc;
    }

    public void setTotalDirsc(double totalDirsc) {
        this.totalDirsc = totalDirsc;
        this.setChanged();
    }

    public double getTotalImpuestoInterno() {
        return totalImpuestoInterno;
    }

    public void setTotalImpuestoInterno(double _totalImpuestoInterno) {
        this.totalImpuestoInterno = _totalImpuestoInterno;
        this.setChanged();
    }

    public String getAnulo() {
        return this.anulo;
    }

    public void setAnulo(String anulo) {
        this.anulo = anulo;
        this.setChanged();
    }

    public Date getFechaSincronizacion() {
        return this.fechaSincronizacion;
    }

    public void setFechaSincronizacion(Date fechaSincronizacion) {
        this.fechaSincronizacion = fechaSincronizacion;
    }

    public String getIdMovil() {
        return this.idMovil;
    }

    public void setIdMovil(String idMovil) {
        this.idMovil = idMovil;
    }

    public Date getFechaVenta() {
        if (fechaVenta == null && fechaVentaWs != null) {
            try {
                fechaVenta = Formatter.convertToDateWs(fechaVentaWs);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return this.fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
        try {
            fechaVentaWs = Formatter.formatDateWs(fechaVenta);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.setChanged();
    }

    public Repartidor getRepartidor() {
        return this.repartidor;
    }

    public void setRepartidor(Repartidor repartidor) {
        this.repartidor = repartidor;
    }

    public String getPie() {
        return this.pie;
    }

    public void setPie(String pie) {
        this.pie = pie;
    }

    public void setCodigoAutorizacion(String codigoAutorizacion) {
        this.codigoAutorizacion = codigoAutorizacion;

    }

    public String getCodigoAutorizacion() {
        return this.codigoAutorizacion;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    // Método para obtener la ubicación geográfica del móvil
    public UbicacionGeografica getUbicacionGeografica() throws Exception {
        /*UbicacionGeograficaBo ubicacionGeograficaBo = new UbicacionGeograficaBo();
        return ubicacionGeograficaBo.recoveryByIdMovil(idMovil);*/
        return null;
    }


    public boolean isTieneDescuento() {
        return tieneDescuento;
    }

    public void setTieneDescuento(boolean tieneDescuento) {
        this.tieneDescuento = tieneDescuento;
    }

    public double getMontoTotalArticulosCriticos() {
        return montoTotalArticulosCriticos;
    }

    public void setMontoTotalArticulosCriticos(double montoTotalArticulosCriticos) {
        this.montoTotalArticulosCriticos = montoTotalArticulosCriticos;
    }

    public double getTotalIvaNoCategorizado() {
        return totalIvaNoCategorizado;
    }

    public void setTotalIvaNoCategorizado(double totalIvaNoCategorizado) {
        this.totalIvaNoCategorizado = totalIvaNoCategorizado;
        this.setChanged();

    }

    public double getTasaDescuentoCondicionVenta() {
        return tasaDescuentoCondicionVenta;
    }

    public void setTasaDescuentoCondicionVenta(double tasaDescuentoCondicionVenta) {
        this.tasaDescuentoCondicionVenta = tasaDescuentoCondicionVenta;
    }

    public double getPrecioBonificacionCondicionVenta() {
        return precioBonificacionCondicionVenta;
    }

    public void setPrecioBonificacionCondicionVenta(double precioBonificacionCondicionVenta) {
        this.precioBonificacionCondicionVenta = precioBonificacionCondicionVenta;
    }

    public double getTotalPorArticulo() {
        return totalPorArticulo;
    }

    public void setTotalPorArticulo(double _totalPorArticulo) {
        this.totalPorArticulo = _totalPorArticulo;
    }

    public Integer getIdPedidoPtoVta() {
        return idPedidoPtoVta;
    }

    public void setIdPedidoPtoVta(Integer idPedidoPtoVta) {
        this.idPedidoPtoVta = idPedidoPtoVta;
    }

    public String getTiTurno() {
        if (tiTurno == null) {
            return "S";
        }
        return tiTurno;
    }

    public void setTiTurno(String tiTurno) {
        if (tiTurno == null) {
            tiTurno = "S";
        }
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

    public String getDeCliente() {
        return deCliente;
    }

    public void setDeCliente(String deCliente) {
        this.deCliente = deCliente;
    }

    public Long getIdNumDocFiscal() {
        return idNumDocFiscal;
    }

    public void setIdNumDocFiscal(Long idNumDocFiscal) {
        this.idNumDocFiscal = idNumDocFiscal;
    }

    public String getCodigoAutorizacionAccionComercial() {
        if (codigoAutorizacionAccionComercial == null) return "";
        return codigoAutorizacionAccionComercial;
    }

    public void setCodigoAutorizacionAccionComercial(String codigoAutorizacionAccionComercial) {
        if (codigoAutorizacionAccionComercial == null) codigoAutorizacionAccionComercial = "";
        this.codigoAutorizacionAccionComercial = codigoAutorizacionAccionComercial;
    }

    public MotivoAutorizacion getMotivoAutorizacion() {
        return motivoAutorizacion;
    }

    public void setMotivoAutorizacion(MotivoAutorizacion motivoAutorizacion) {
        this.motivoAutorizacion = motivoAutorizacion;
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

    public double getPrBonifCiva() {
        return prBonifCiva;
    }

    public void setPrBonifCiva(double prBonifCiva) {
        this.prBonifCiva = prBonifCiva;
    }

    public double getPrBonifCvCiva() {
        return prBonifCvCiva;
    }

    public void setPrBonifCvCiva(double prBonifCvCiva) {
        this.prBonifCvCiva = prBonifCvCiva;
    }

    public double getTotalIva21Mas105Sdto() {
        return totalIva21Mas105Sdto;
    }

    public void setTotalIva21Mas105Sdto(double _totalIva21Mas105Sdto) {
        this.totalIva21Mas105Sdto = _totalIva21Mas105Sdto;
    }

    public Integer getIdMotivoAutorizaPedr() {
        return idMotivoAutorizaPedr;
    }

    public void setIdMotivoAutorizaPedr(Integer idMotivoAutorizaPedr) {
        this.idMotivoAutorizaPedr = idMotivoAutorizaPedr;
    }

    public boolean isRentableXEmpresa() {
        return isRentableXEmpresa;
    }

    public void setRentableXEmpresa(boolean rentableXEmpresa) {
        isRentableXEmpresa = rentableXEmpresa;
    }

    public String getNivelRentabilidadEmpresa() {
        return nivelRentabilidadEmpresa;
    }

    public void setNivelRentabilidadEmpresa(String nivelRentabilidadEmpresa) {
        this.nivelRentabilidadEmpresa = nivelRentabilidadEmpresa;
    }

    public boolean isRentableXProveedor() {
        return isRentableXProveedor;
    }

    public void setRentableXProveedor(boolean rentableXProveedor) {
        isRentableXProveedor = rentableXProveedor;
    }

    public String getNivelRentabilidadProveedor() {
        return nivelRentabilidadProveedor;
    }

    public void setNivelRentabilidadProveedor(String nivelRentabilidadProveedor) {
        this.nivelRentabilidadProveedor = nivelRentabilidadProveedor;
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

    public String getFechaRegistroWs() {
        return fechaRegistroWs;
    }

    public void setFechaRegistroWs(String fechaRegistroWs) {
        this.fechaRegistroWs = fechaRegistroWs;
    }

    public String getFechaVentaWs() {
        return fechaVentaWs;
    }

    public void setFechaVentaWs(String fechaVentaWs) {
        this.fechaVentaWs = fechaVentaWs;
    }

    public Integer getIdHojaIntegrado() {
        return idHojaIntegrado;
    }

    public void setIdHojaIntegrado(Integer idHojaIntegrado) {
        this.idHojaIntegrado = idHojaIntegrado;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Venta venta = (Venta) o;

        return id.equals(venta.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
