package com.ar.vgmsistemas.entity.key;

public class PkPromocionDetalle {
    private static final long serialVersionUID = -6438945780718911018L;
    private int idPromo;
    private int idArticulos;
    private int idSecuencia;

    public PkPromocionDetalle(int idPromo, int idArticulos, int idSecuencia) {
        this.idPromo = idPromo;
        this.idArticulos = idArticulos;
        this.idSecuencia = idSecuencia;
    }

    public PkPromocionDetalle() {
    }

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
