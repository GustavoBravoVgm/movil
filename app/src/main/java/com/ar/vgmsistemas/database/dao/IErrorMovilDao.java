package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.ar.vgmsistemas.database.dao.entity.ErrorMovilBd;

import java.util.List;

@Dao
public interface IErrorMovilDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    void create(ErrorMovilBd entity) throws Exception;

    @Query(value = "SELECT error_movil.* FROM error_movil")
    List<ErrorMovilBd> recoveryAll() throws Exception;

    @Update(onConflict = OnConflictStrategy.ROLLBACK)
    void update(ErrorMovilBd entity) throws Exception;

    @Query(value = "SELECT error_movil.* " +
            "FROM   error_movil " +
            "       INNER JOIN movimientos " +
            "           ON movimientos.id_movil = error_movil.id_movil " +
            "WHERE  movimientos.fe_sincronizacion IS NULL")
    List<ErrorMovilBd> recoveryAEnviar() throws Exception;
}
