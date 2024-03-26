package com.ar.vgmsistemas.database.dao.entity.key;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

public class PkPromocionDetalleBd {
    @Ignore
    private static final long serialVersionUID = -6438945780718911018L;
    @ColumnInfo(name = "id_promo")
    private int idPromo;
    @ColumnInfo(name = "id_articulos")
    private int idArticulos;
    @ColumnInfo(name = "id_secuencia")
    private int idSecuencia;

    public int getIdPromo() {
        return idPromo;
    }

    public void setIdPromo(int idPromo) {
        this.idPromo = idPromo;
    }

    public int getIdArticulos() {
        return idArticulos;
    }

    public void setIdArticulos(int idArticulos) {
        this.idArticulos = idArticulos;
    }

    public int getIdSecuencia() {
        return idSecuencia;
    }

    public void setIdSecuencia(int idSecuencia) {
        this.idSecuencia = idSecuencia;
    }
}
