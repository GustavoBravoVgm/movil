package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;

import com.ar.vgmsistemas.database.dao.entity.key.PkPromocionDetalleBd;

import java.io.Serializable;

@Entity(tableName = "promoxart",
        primaryKeys = {"id_promo", "id_articulos", "id_secuencia"},
        indices = {@Index(name = "pxa_idx_id_articulos", value = {"id_articulos"}),
                @Index(name = "pxa_idx_id_promo", value = "id_promo")})
public class PromocionDetalleBd implements Serializable {
    @NonNull
    @Embedded
    private PkPromocionDetalleBd id;

    @ColumnInfo(name = "ca_unidades")
    private double cantidad;

    @ColumnInfo(name = "pr_unidad")
    private double precioCombo;

    /*Getters-Setters*/

    @NonNull
    public PkPromocionDetalleBd getId() {
        return id;
    }

    public void setId(@NonNull PkPromocionDetalleBd id) {
        this.id = id;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioCombo() {
        return precioCombo;
    }

    public void setPrecioCombo(double precioCombo) {
        this.precioCombo = precioCombo;
    }
}
