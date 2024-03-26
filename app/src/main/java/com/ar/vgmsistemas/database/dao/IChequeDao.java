package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.ar.vgmsistemas.database.dao.entity.ChequeBd;

import java.util.List;

@Dao
public interface IChequeDao {

    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    void create(ChequeBd cheque) throws Exception;

    @Query(value = "SELECT cheques.* " +
            "FROM   cheques" +
            "       LEFT JOIN banco " +
            "           ON cheques.id_bancogirado_cheque = banco.id_bancogirado " +
            "WHERE  cheques.id_sucursal = :idSucursal AND " +
            "       cheques.id_cliente = :idCliente AND " +
            "       cheques.fe_cheque > :fechaActualConvertida")
    List<ChequeBd> recoveryByCliente(int idSucursal, int idCliente, String fechaActualConvertida) throws Exception;

    @Query(value = "SELECT cheques.* " +
            "FROM   cheques" +
            "       INNER JOIN banco " +
            "           ON cheques.id_bancogirado_cheque = banco.id_bancogirado " +
            "       INNER JOIN entrega " +
            "           ON entrega.id_entrega = cheques.id_entrega " +
            "WHERE  cheques.id_entrega = :idEntrega")
    List<ChequeBd> recoveryByEntrega(int idEntrega) throws Exception;

    @Delete
    void delete(ChequeBd entity) throws Exception;

    @Query(value = "DELETE FROM cheques " +
            "WHERE cheques.id_entrega = :idEntrega")
    void deleteByEntrega(int idEntrega) throws Exception;

    @Query(value = "SELECT cheques.* " +
            "FROM   cheques" +
            "       INNER JOIN banco " +
            "           ON cheques.id_bancogirado_cheque = banco.id_bancogirado " +
            "WHERE  cheques.id_bancogirado_cheque = :idBanco AND " +
            "       cheques.id_cheque = :numeroCheque AND " +
            "       cheques.id_postal = :idPostal AND " +
            "       cheques.id_sucursal = :sucursal AND " +
            "       cheques.id_nrocuenta = :nroCuenta")
    ChequeBd recoveryByID(int idBanco, long numeroCheque, int idPostal, int sucursal, long nroCuenta) throws Exception;

    @Query(value = "SELECT cheques.* FROM cheques")
    List<ChequeBd> recoveryAll() throws Exception;

}
