package com.ar.vgmsistemas.entity;

import java.io.Serializable;
import java.util.Date;

public class EntregaMoneda implements Serializable {
    private static final long serialVersionUID = -7278553284013192623L;

    private int id;
    private TipoMoneda tipoMoneda;
    private double cotizacion; //cotizacion para esa entrega en particular
    private double importe;
    private Date fecha;

    public void setTipoMoneda(TipoMoneda tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public TipoMoneda getTipoMoneda() {
        return tipoMoneda;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public double getImporte() {
        return importe;
    }

    public double getImporteMonedaCorriente() {
        return this.getCotizacion() * this.getImporte();
    }

    public void setCotizacion(double cotizacion) {
        this.cotizacion = cotizacion;
    }

    public double getCotizacion() {
        return cotizacion;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }


}
