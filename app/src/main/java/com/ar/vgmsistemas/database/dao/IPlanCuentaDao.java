package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.PlanCuentaBd;

import java.util.List;

@Dao
public interface IPlanCuentaDao {
    @Query(value = "SELECT plancta.* " +
            "FROM   plancta " +
            "WHERE plancta.id_categoria2 = :categoriaPlanCuenta")
    List<PlanCuentaBd> recoveryByDocumento(int categoriaPlanCuenta) throws Exception;


    @Query(value = "SELECT plancta.* " +
            "FROM   plancta " +
            "WHERE  plancta.sn_egreso_movil = 'S' OR" +
            "       EXISTS(SELECT 1 FROM proveedores" +
            "               WHERE proveedores.id_plancta = plancta.id_plancta)")
    List<PlanCuentaBd> recoveryForEgreso() throws Exception;

    @Query(value = "SELECT plancta.* " +
            "FROM   plancta " +
            "WHERE plancta.id_plancta = :id")
    PlanCuentaBd recoveryByID(Integer id) throws Exception;

}
