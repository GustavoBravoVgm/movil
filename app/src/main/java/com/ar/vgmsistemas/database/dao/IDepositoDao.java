package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.ar.vgmsistemas.database.dao.entity.DepositoBd;

import java.util.List;

@Dao
public interface IDepositoDao {
    @Transaction
    @Query(value = "SELECT depositos.* " +
            "FROM depositos")
    List<DepositoBd> recoveryAll() throws Exception;

    @Transaction
    @Query(value = "SELECT depositos.* " +
            "FROM   depositos " +
            "WHERE  depositos.id_deposito = :id")
    DepositoBd recoveryByID(Integer id) throws Exception;

    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    Long create(DepositoBd deposito) throws Exception;

    @Transaction
    @Query(value = "SELECT depositos.* " +
            "FROM   depositos " +
            "       INNER JOIN entrega " +
            "           ON  entrega.id_entrega = depositos.id_entrega " +
            "       INNER JOIN banco " +
            "           ON banco.id_bancogirado = depositos.id_bancogirado_deposito " +
            "WHERE  depositos.id_entrega = :id")
    List<DepositoBd> recoveryByEntrega(Integer id) throws Exception;

    @Transaction
    @Query(value = "DELETE FROM depositos " +
            "WHERE  depositos.id_entrega = :id")
    void deleteByEntrega(Integer id) throws Exception;
}
