package com.ar.vgmsistemas.entity.key;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PkListaPrecioDetalle implements Serializable {
    private static final long serialVersionUID = 3385978229048993236L;

    @SerializedName("idLista")
    private int idLista;

    @SerializedName("idArticulo")
    private int idArticulo;

    @SerializedName("ca_articulo_desde")
    private int caArticuloDesde;

    @SerializedName("ca_articulo_hasta")
    private int caArticuloHasta;

    public PkListaPrecioDetalle(int idLista, int idArticulo, int caArticuloDesde, int caArticuloHasta) {
        this.idLista = idLista;
        this.idArticulo = idArticulo;
        this.caArticuloDesde = Math.max(caArticuloDesde, 0);
        this.caArticuloHasta = Math.max(caArticuloHasta, 0);
    }

    public PkListaPrecioDetalle() {
    }

    public int getIdLista() {
        return this.idLista;
    }

    public void setIdLista(int idLista) {
        this.idLista = idLista;
    }

    public int getIdArticulo() {
        return this.idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public int getCaArticuloDesde() {
        return caArticuloDesde;
    }

    public void setCaArticuloDesde(int caArticuloDesde) {
        this.caArticuloDesde = caArticuloDesde;
    }

    public int getCaArticuloHasta() {
        return caArticuloHasta;
    }

    public void setCaArticuloHasta(int caArticuloHasta) {
        this.caArticuloHasta = caArticuloHasta;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + (int) (this.idArticulo ^ (this.idArticulo >>> 32));
        result = prime * result + (int) (this.idLista ^ (this.idLista >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof PkListaPrecioDetalle))
            return false;
        PkListaPrecioDetalle other = (PkListaPrecioDetalle) obj;
        if (this.idArticulo != other.idArticulo)
            return false;
        if (this.idLista != other.idLista)
            return false;
        return true;
    }

}
