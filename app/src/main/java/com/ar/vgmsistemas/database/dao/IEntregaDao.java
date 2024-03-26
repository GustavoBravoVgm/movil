package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.ar.vgmsistemas.database.dao.entity.EntregaBd;

import java.util.List;

@Dao
public interface IEntregaDao {
    @Query(value = "SELECT  entrega.* FROM entrega")
    List<EntregaBd> recoveryAll() throws Exception;

    @Transaction
    @Query(value = "SELECT  entrega.* " +
            "FROM   entrega " +
            "WHERE  entrega.id_entrega = :idEntrega ")
    EntregaBd recoveryByID(int idEntrega) throws Exception;

    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    void create(EntregaBd miEntrega) throws Exception;

    @Delete
    void delete(EntregaBd miEntrega) throws Exception;

    @Query(value = "DELETE FROM entrega " +
            "WHERE entrega.id_entrega = :idEntrega")
    void delete(int idEntrega) throws Exception;

    @Query(value = "SELECT IFNULL(max(entrega.id_entrega),0)  " +
            "FROM entrega ")
    int maxIdEntrega() throws Exception;

}
