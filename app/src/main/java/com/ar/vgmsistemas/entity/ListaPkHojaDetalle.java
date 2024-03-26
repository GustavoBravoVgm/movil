package com.ar.vgmsistemas.entity;

import com.ar.vgmsistemas.entity.key.PkHojaDetalle;

import java.util.List;

public class ListaPkHojaDetalle {

    public ListaPkHojaDetalle(List<PkHojaDetalle> mPkHojasDetalles) {
        this.pkHojasDetalles = mPkHojasDetalles;
    }

    public ListaPkHojaDetalle() {
    }

    public List<PkHojaDetalle> pkHojasDetalles;

    public List<PkHojaDetalle> getPkHojasDetalles() {
        return pkHojasDetalles;
    }

    public void setPkHojasDetalles(List<PkHojaDetalle> pkHojasDetalles) {
        this.pkHojasDetalles = pkHojasDetalles;
    }

}
