package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.ar.vgmsistemas.database.dao.entity.PagoEfectivoBd;

import java.util.List;

@Dao
public interface IPagoEfectivoDao {
    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    Long create(PagoEfectivoBd pagoEfectivo) throws Exception;

    @Transaction
    @Query(value = "DELETE FROM pagosEfectivo " +
            "WHERE  pagosEfectivo.id_entrega = :id")
    void deleteByEntrega(Integer id) throws Exception;

    @Query(value = "SELECT pagosEfectivo.* " +
            "FROM   pagosEfectivo " +
            "       INNER JOIN entrega " +
            "           ON entrega.id_entrega = pagosEfectivo.id_entrega " +
            "       INNER JOIN valores " +
            "           ON valores.id_valor = pagosEfectivo.id_valor " +
            "WHERE  entrega.id_entrega = :id")
    List<PagoEfectivoBd> recoveryByEntrega(Integer id) throws Exception;
}
