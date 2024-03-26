package com.ar.vgmsistemas.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListaPrecio implements Serializable {


    public final static int TIPO_LISTA_BASE = 1;

    public final static int TIPO_LISTA_COMBOS_COMUNES = 3;

    public static final int TIPO_LISTA_LIBRE = 5;

    public final static int TIPO_LISTA_BASE_X_ART_LIBRE = 7;

    public final static int TIPO_LISTA_COMBOS_ESPECIALES = 8;

    public final static int TIPO_LISTA_BASE_X_CANTIDAD = 9;

    public final static String ID_ORIGENPRECIO_A = "A";

    private static final long serialVersionUID = -6274693907692995586L;

    @SerializedName("id")
    private int id;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("tipoLista")
    private int tipoLista;

    private int listaBase;

    private String idOrigenPrecio;

    private String snSeleccionable;

    private String snMovil;


    private List<ListaPrecioDetalle> _detalles = new ArrayList<>();


    public ListaPrecio() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null) descripcion = "";
        this.descripcion = descripcion;
    }

    public List<ListaPrecioDetalle> getDetalles() {
        return this._detalles;
    }

    public void setDetalles(List<ListaPrecioDetalle> detalles) {
        this._detalles = detalles;
    }

    @Override
    public String toString() {
        return this.descripcion;
    }

    public int getListaBase() {
        return this.listaBase;
    }

    public void setListaBase(int listaBase) {
        this.listaBase = listaBase;
    }

    @Override
    public boolean equals(Object o) {
        return this.hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        int result = 17;
        int c = (int) this.getId();
        result = 37 * result + c;
        return result;
    }

    public int getTipoLista() {
        return tipoLista;
    }

    public void setTipoLista(int tipoLista) {
        this.tipoLista = tipoLista;
    }

    public String getIdOrigenPrecio() {
        return idOrigenPrecio;
    }

    public void setIdOrigenPrecio(String idOrigenPrecio) {
        this.idOrigenPrecio = idOrigenPrecio;
    }

    public String getSnSeleccionable() {
        return snSeleccionable;
    }

    public void setSnSeleccionable(String snSeleccionable) {
        if (snSeleccionable == null) snSeleccionable = "N";
        this.snSeleccionable = snSeleccionable;
    }

    public String getSnMovil() {
        return snMovil;
    }

    public void setSnMovil(String snMovil) {
        if (snMovil == null) snMovil = "N";
        this.snMovil = snMovil;
    }
}
