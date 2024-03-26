package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.PromocionDetalleBd;

import java.util.List;

@Dao
public interface IPromocionDetalleDao {
    @Query(value = "SELECT * FROM promoxart")
    List<PromocionDetalleBd> recoveryAll() throws Exception;

    @Query(value = "SELECT promoxart.* " +
            "FROM   promoxart " +
            "WHERE  promoxart.id_promo = :idPromo AND " +
            "       promoxart.id_articulos = :idArticulos AND " +
            "       promoxart.id_secuencia = :idSecuencia")
    PromocionDetalleBd recoveryByID(int idPromo, int idArticulos, int idSecuencia) throws Exception;


    @Query(value = "SELECT promoxart.* " +
            "FROM   promoxart " +
            "WHERE  promoxart.id_promo = :idPromo ")
    List<PromocionDetalleBd> recoveryByIdPromo(int idPromo) throws Exception;

    @Query(value = "SELECT promoxart.* " +
            "FROM   promoxart " +
            "WHERE  promoxart.id_articulos = :idArticulo ")
    List<PromocionDetalleBd> recoveryItemsByItem(int idArticulo) throws Exception;

    @Query(value = "SELECT promoxart.* " +
            "FROM   promoxart " +
            "       INNER JOIN articulos " +
            "           ON articulos.id_articulos = promoxart.id_articulos " +
            "       INNER JOIN stock " +
            "           ON stock.id_articulos = promoxart.id_articulos " +
            "WHERE  promoxart.id_articulos = :idArticulo ")
    List<PromocionDetalleBd> recoveryItems(int idArticulo) throws Exception;

}
