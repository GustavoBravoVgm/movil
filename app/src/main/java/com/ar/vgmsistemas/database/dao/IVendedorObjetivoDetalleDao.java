package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.ar.vgmsistemas.database.dao.entity.VendedorObjetivoDetalleBd;

import java.util.List;

@Dao
public interface IVendedorObjetivoDetalleDao {
    @Query(value = "SELECT vend_objetivos_detalle.* " +
            "FROM   vend_objetivos_detalle")
    List<VendedorObjetivoDetalleBd> recoveryAll() throws Exception;

    @Transaction
    @Query(value = "SELECT vend_objetivos_detalle.* " +
            "FROM   vend_objetivos_detalle " +
            "WHERE  vend_objetivos_detalle.id_objetivo = :idObjetivo AND " +
            "       vend_objetivos_detalle.id_secuencia = :idSecuencia")
    VendedorObjetivoDetalleBd recoveryByID(int idObjetivo, int idSecuencia) throws Exception;

    @Query(value = "SELECT vend_objetivos_detalle.* " +
            "FROM   vend_objetivos_detalle " +
            "WHERE  vend_objetivos_detalle.id_objetivo = :idObjetivo ")
    List<VendedorObjetivoDetalleBd> recoveryDetalles(int idObjetivo) throws Exception;


}
