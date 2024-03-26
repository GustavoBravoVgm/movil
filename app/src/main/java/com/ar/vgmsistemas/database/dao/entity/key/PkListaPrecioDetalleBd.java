package com.ar.vgmsistemas.database.dao.entity.key;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import java.io.Serializable;

public class PkListaPrecioDetalleBd implements Serializable {

    @Ignore
    private static final long serialVersionUID = 3385978229048993236L;

    @ColumnInfo(name = "id_lista")
    private int idLista;

    @ColumnInfo(name = "id_articulos")
    private int idArticulo;

    @ColumnInfo(name = "ca_articulo_desde")
    private int caArticuloDesde;

    @ColumnInfo(name = "ca_articulo_hasta")
    private int caArticuloHasta;

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
        if (!(obj instanceof PkListaPrecioDetalleBd))
            return false;
        PkListaPrecioDetalleBd other = (PkListaPrecioDetalleBd) obj;
        if (this.idArticulo != other.idArticulo)
            return false;
        if (this.idLista != other.idLista)
            return false;
        return true;
    }

}
