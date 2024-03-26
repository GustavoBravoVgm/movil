package com.ar.vgmsistemas.database.dao.entity.key;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import java.io.Serializable;

public class PkObjetivoVentaBd implements Serializable {

    @Ignore
    private static final long serialVersionUID = -6438945780718911018L;
    @ColumnInfo(name = "id_articulos")
    private int idArticulos;
    @ColumnInfo(name = "id_sucursal")
    private int idSucursal;
    @ColumnInfo(name = "id_cliente")
    private int idCliente;
    @ColumnInfo(name = "id_comercio")
    private int idComercio;
    @ColumnInfo(name = "id_objetivo")
    private int idObjetivo;

    public PkObjetivoVentaBd() {
    }

    public int getIdArticulos() {
        return idArticulos;
    }

    public void setIdArticulos(int idArticulos) {
        this.idArticulos = idArticulos;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdComercio() {
        return idComercio;
    }

    public void setIdComercio(int idComercio) {
        this.idComercio = idComercio;
    }

    public int getIdObjetivo() {
        return idObjetivo;
    }

    public void setIdObjetivo(int idObjetivo) {
        this.idObjetivo = idObjetivo;
    }

}
