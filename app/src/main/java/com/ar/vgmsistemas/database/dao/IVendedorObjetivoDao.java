package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.ar.vgmsistemas.database.dao.entity.VendedorObjetivoBd;

import java.util.List;

@Dao
public interface IVendedorObjetivoDao {
    @Transaction
    @Query(value = "SELECT vend_objetivos.* " +
            "FROM vend_objetivos " +
            "WHERE vend_objetivos.id_objetivo = :idObjetivo")
    VendedorObjetivoBd recoveryByID(int idObjetivo) throws Exception;

    @Query(value = "SELECT vend_objetivos.* " +
            "FROM vend_objetivos ")
    List<VendedorObjetivoBd> recoveryAll() throws Exception;
}
