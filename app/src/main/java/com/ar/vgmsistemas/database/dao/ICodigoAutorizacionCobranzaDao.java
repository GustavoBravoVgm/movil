package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.CodigoAutorizacionCobranzaBd;

@Dao
public interface ICodigoAutorizacionCobranzaDao {

    @Query(value = "SELECT * " +
            "FROM   codigos_autorizacion " +
            "WHERE  codigos_autorizacion.codigo = :codigo ")
    CodigoAutorizacionCobranzaBd recoveryByCodigo(String codigo) throws Exception;

    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    void create(CodigoAutorizacionCobranzaBd codigo) throws Exception;


}
