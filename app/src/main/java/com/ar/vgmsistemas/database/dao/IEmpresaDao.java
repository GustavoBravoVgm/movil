package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.EmpresaBd;

import java.util.List;

@Dao
public interface IEmpresaDao {
    @Query(value = "SELECT empresas.* " +
            "FROM empresas")
    List<EmpresaBd> recoveryAll() throws Exception;

    @Query(value = "SELECT empresas.* " +
            "FROM   empresas " +
            "WHERE  empresas.id_empresa = :id")
    EmpresaBd recoveryByID(int id) throws Exception;
}
