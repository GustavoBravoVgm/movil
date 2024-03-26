package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.StockBd;

import java.util.List;

@Dao
public interface IStockDao {
    @Query(value = "SELECT stock.* " +
            "FROM stock " +
            "WHERE stock.id_articulos = :idArticulos")
    StockBd recoveryByID(int idArticulos) throws Exception;

    @Query(value = "SELECT stock.* " +
            "FROM stock ")
    List<StockBd> recoveryAll() throws Exception;


    @Query(value = "UPDATE stock " +
            "SET    stock = (IFNULL(stock,0.00) + (:cantidad * :tipoOperacion)) " +
            "WHERE  stock.id_articulos = :idArticulo ")
    void updateStock(int idArticulo, double cantidad, int tipoOperacion) throws Exception;


    @Query(value = "UPDATE stock " +
            "SET    stock = (stock + :cantidad ) " +
            "WHERE  stock.id_articulos = :idArticulo ")
    void updateStock(int idArticulo, double cantidad) throws Exception;

}
