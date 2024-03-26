package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

import com.ar.vgmsistemas.database.dao.entity.HojaBd;

import java.util.List;

@Dao
public interface IHojaDao {
    @Query(value = "SELECT hoja.* FROM hoja")
    List<HojaBd> recoveryAll() throws Exception;


    @Query(value = "SELECT hoja.* " +
            "FROM hoja " +
            "WHERE  hoja.id_sucursal = :idSucursal AND " +
            "       hoja.id_hoja = :idHoja")
    List<HojaBd> recoveryByID(int idHoja, int idSucursal) throws Exception;

    @Query(value = "UPDATE hoja SET pr_anulado = :totalAnulado " +
            "WHERE hoja.id_sucursal = :idSucursal AND " +
            "       hoja.id_hoja = :idHoja")
    void updateTotalAnulado(int idSucursal, int idHoja, double totalAnulado) throws Exception;

    @Query(value = "UPDATE hoja SET pr_pendiente = :prPendiente " +
            "WHERE hoja.id_sucursal = :idSucursal AND " +
            "       hoja.id_hoja = :idHoja")
    void updateTotalNoEntregado(int idSucursal, int idHoja, double prPendiente) throws Exception;

    @Query(value = "UPDATE hoja SET ti_estado = :state " +
            "WHERE hoja.id_sucursal = :idSucursal AND " +
            "       hoja.id_hoja = :idHoja")
    void updateState(int idSucursal, int idHoja, int state) throws Exception;

    /*
    void updateTotalAnulado(int idHoja, int idSucursal, double totalAnulado) throws Exception;
    void updateTotalNoEntregado(int idHoja, int idSucursal, double prPendiente) throws Exception;
    void updateState(int idHoja, int idSucursal, int state) throws Exception;
    * Se puede reducir en  update(Hoja entity)*/

    @Update
    void update(HojaBd entity) throws Exception;

    @Query(value = "SELECT hoja.sn_rendida " +
            "FROM   hoja " +
            "WHERE  hoja.id_sucursal = :idSucursal AND " +
            "       hoja.id_hoja = :idHoja")
    String getSnRendida(int idSucursal, int idHoja) throws Exception;

    @Query(value = "SELECT hoja.* " +
            "FROM   hoja " +
            "WHERE  hoja.id_sucursal = :idSucursal ")
    List<HojaBd> recoveryBySucursal(int idSucursal) throws Exception;
}
