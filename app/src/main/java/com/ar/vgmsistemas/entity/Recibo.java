package com.ar.vgmsistemas.entity;

import static com.ar.vgmsistemas.utils.Formatter.convertToDateTime;

import com.ar.vgmsistemas.entity.key.PkRecibo;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.utils.Matematica;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;

public class Recibo extends Observable implements Serializable {
    private static final long serialVersionUID = -5248227110744855626L;

    public static final String TABLE = "talon_recibo";

    public static final String ESTADO_IMPUTADO = "I";

    public static final String ESTADO_PAGADO = "P";

    public static final String ESTADO_ANULADO = "A";

    @SerializedName("id")
    private PkRecibo id;

    @SerializedName("vendedor")
    private Vendedor vendedor;

    private String idEstado;

    private Date fechaMovil;
    @SerializedName("fecha")

    private String fechaReciboWs;

    @SerializedName("total")
    private double total;

    @SerializedName("idMovil")
    private String idMovil;

    private Date fechaRegistroMovil;
    @SerializedName("fechaRegistro")

    private String fechaRegistroMovilWs;

    @SerializedName("entrega")
    private Entrega entrega;

    private String observacion;

    @SerializedName("cliente")
    private Cliente cliente;

    @SerializedName("deviceId")
    private String deviceId;

    private int resultadoEnvio;


    @SerializedName("detalles")
    private List<ReciboDetalle> detalles = new ArrayList<>();


    private Date fechaRendicion;


    private List<CuentaCorriente> comprobantesGenerados = new ArrayList<>();

    private int tipoImpresionRecibo;

    public Recibo() {
        this.entrega = new Entrega();
    }

    public PkRecibo getId() {
        return this.id;
    }

    public void setId(PkRecibo id) {
        this.id = id;
    }

    public Vendedor getVendedor() {
        return this.vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
        this.setChanged();
    }

    public String getFechaReciboWs() {
        return this.fechaReciboWs;
    }

    public String getFechaRegistroMovilWs() {
        return this.fechaRegistroMovilWs;
    }

    public Date getFechaMovil() {
        return this.fechaMovil;
    }

    public void setFechaMovil(Date fechaMovil) {
        this.fechaMovil = fechaMovil;
        try {
            this.fechaReciboWs = Formatter.formatDateWs(fechaMovil);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.setChanged();
    }

    public double obtenerTotalPagos() {
        return this.getEntrega().obtenerTotalPagos();
    }

    public double getTotal() {
        return this.total;
    }

    public void setTotal(double total) {
        this.total = total;
        this.setCambiado();
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        this.setChanged();
    }

    public List<ReciboDetalle> getDetalles() {
        return this.detalles;
    }


    public void setDetalles(List<ReciboDetalle> detalles) {
        this.detalles = detalles;
        this.setChanged();
    }

    public void addDetalle(ReciboDetalle reciboDetalle) {
        this.detalles.add(reciboDetalle);
        total = total + reciboDetalle.getCuentaCorriente().getTotalPagado();
    }

    public void setCambiado() {
        setChanged();
        notifyObservers();
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Recibo other = (Recibo) obj;
        if (this.fechaMovil == null) {
            if (other.fechaMovil != null)
                return false;
        } else if (!this.fechaMovil.equals(other.fechaMovil))
            return false;
        if (this.id == null) {
            if (other.id != null)
                return false;
        } else if (!this.id.equals(other.id))
            return false;
        if (Double.doubleToLongBits(this.total) != Double
                .doubleToLongBits(other.total))
            return false;
        if (this.vendedor == null) {
            if (other.vendedor != null)
                return false;
        } else if (!this.vendedor.equals(other.vendedor))
            return false;
        return true;
    }

    public double obtenerTotalDocumentosImputados() {
        double total = 0;
        for (ReciboDetalle r : this.detalles) {
            total += Matematica.restarPorcentaje(r.obtenerSaldoCuentaCorriente(), r.getTaDtoRecibo());
        }
        return total;
    }

    public double obtenerTotalReciboDetalle() {
        double total = 0;
        for (ReciboDetalle r : this.detalles) {
            total += r.getImportePagado() * r.getCuentaCorriente().getSigno();
        }
        return total;
    }

    public double getTotalDocumentosGenerados() {
        double total = 0;
        for (CuentaCorriente ctacte : this.comprobantesGenerados) {
            total += ctacte.getTotalCuota();
        }
        return total;
    }

    public double getSaldo() {
        return this.obtenerTotalDocumentosImputados() - this.obtenerTotalPagos();
    }

    public String getIdMovil() {
        return this.idMovil;
    }

    public void setIdMovil(String idMovil) {
        this.idMovil = idMovil;
    }

    public Date getFechaRegistroMovil() {
        if (fechaRegistroMovil == null && fechaRegistroMovilWs != null) {
            try {
                fechaRegistroMovil = Formatter.convertToDateTimeWs(fechaRegistroMovilWs);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return this.fechaRegistroMovil;
    }

    public void setFechaRegistroMovil(Date fechaRegistroMovil) {
        if (fechaRegistroMovil == null && this.idMovil != null) {
            try {
                String feRegistroAuxString = Formatter.formatDateWs(fechaRegistroMovil);
                int desde = idMovil.indexOf(' ') + 1;
                int hasta = idMovil.indexOf('-', desde);
                feRegistroAuxString = feRegistroAuxString + ' ' + idMovil.substring(desde, hasta);
                fechaRegistroMovil = convertToDateTime(feRegistroAuxString);
            } catch (Exception ex) {
            }
        }
        this.fechaRegistroMovil = fechaRegistroMovil;

        try {
            this.fechaRegistroMovilWs = Formatter.formatDateWs(fechaRegistroMovil);
        } catch (Exception ex) {
        }
    }

    public Date getFechaRendicion() {
        return fechaRendicion;
    }

    public void setFechaRendicion(Date fechaRendicion) {
        this.fechaRendicion = fechaRendicion;
    }

    public Entrega getEntrega() {
        return entrega;
    }

    public void setEntrega(Entrega entrega) {
        this.entrega = entrega;
    }

    public double calcularTotalCredito() {
        double total = 0;
        for (ReciboDetalle reciboDetalle : this.detalles) {
            CuentaCorriente cuentaCorriente = reciboDetalle.getCuentaCorriente();
            if (cuentaCorriente.getSigno() < 0) {
                total += cuentaCorriente.calcularSaldo() * cuentaCorriente.getSigno();
            }
        }
        return total;
    }

    public double getTotalFactura() {
        double total = 0;
        for (ReciboDetalle reciboDetalle : this.detalles) {
            CuentaCorriente cuentaCorriente = reciboDetalle.getCuentaCorriente();
            if (cuentaCorriente.getSigno() > 0) {
                total += cuentaCorriente.calcularSaldo();
            }
        }
        return total;
    }

    /**
     * @return the comprobantesGenerados
     */
    public List<CuentaCorriente> getComprobantesGenerados() {
        return comprobantesGenerados;
    }

    /**
     * @param comprobantesGenerados the comprobantesGenerados to set
     */
    public void setComprobantesGenerados(List<CuentaCorriente> comprobantesGenerados) {
        this.comprobantesGenerados = comprobantesGenerados;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getResultadoEnvio() {
        return resultadoEnvio;
    }

    public void setResultadoEnvio(int resultadoEnvio) {
        this.resultadoEnvio = resultadoEnvio;
    }

    public String getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(String idEstado) {
        this.idEstado = idEstado;
    }

    public int getTipoImpresionRecibo() {
        return tipoImpresionRecibo;
    }

    public void setTipoImpresionRecibo(int tipoImpresionRecibo) {
        this.tipoImpresionRecibo = tipoImpresionRecibo;
    }


    public void setFechaRegistroMovilWs(String fechaRegistroMovilWs) {
        this.fechaRegistroMovilWs = fechaRegistroMovilWs;
    }

    public void setFechaReciboWs(String fechaReciboWs) {
        this.fechaReciboWs = fechaReciboWs;
    }
}
