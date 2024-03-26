package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "stock")
public class StockBd {

    /*Atributos/ columnas en bd*/
    @ColumnInfo(name = "id_articulos")
    @PrimaryKey
    private int idArticulos;

    @ColumnInfo(name = "stock")
    private double stock;

    public int getIdArticulos() {
        return idArticulos;
    }

    public void setIdArticulos(int idArticulos) {
        this.idArticulos = idArticulos;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }
}
