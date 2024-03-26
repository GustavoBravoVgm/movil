package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.PromocionRequisitoBd;

import java.util.List;

@Dao
public interface IPromocionRequisitoDao {
    @Query(value = "SELECT promo_requisitos.* " +
            "FROM promo_requisitos")
    List<PromocionRequisitoBd> recoveryAll() throws Exception;

    @Query(value = "SELECT promo_requisitos.* " +
            "FROM promo_requisitos " +
            "WHERE promo_requisitos.id_promo = :id")
    PromocionRequisitoBd recoveryByID(Integer id) throws Exception;

    @Query(value = "SELECT promo_requisitos.* " +
            "FROM promo_requisitos " +
            "WHERE promo_requisitos.id_promo = :idArticulo " +
            "LIMIT 1")
    PromocionRequisitoBd recoveryRequisitos(int idArticulo) throws Exception;

}
