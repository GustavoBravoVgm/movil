package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ar.vgmsistemas.database.dao.entity.CompraBd;

import java.util.List;

@Dao
public interface ICompraDao {

    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    void create(CompraBd... entity) throws Exception;
    //PkCompraBd create(CompraBd... entity) throws Exception;

    @Query(value = "SELECT compra.* " +
            "FROM   compra " +
            "WHERE  compra.id_proveedor = :idProveedor AND " +
            "       compra.id_fcncnd = :idFcncnd AND " +
            "       compra.id_ptovta = :idPuntoVenta AND " +
            "       compra.id_numero = :idNumero  ")
    CompraBd recoveryByID(int idProveedor, String idFcncnd, int idPuntoVenta, int idNumero) throws Exception;

    @Query(value = "SELECT compra.* " +
            "FROM   compra")
    List<CompraBd> recoveryAll() throws Exception;

    @Update(onConflict = OnConflictStrategy.ROLLBACK)
    void update(CompraBd... entity) throws Exception;

    @Query(value = "UPDATE compra SET sn_anulado = 'S' " +
            "WHERE  compra.id_proveedor = :idProveedor AND " +
            "       compra.id_fcncnd = :idFcncnd AND " +
            "       compra.id_ptovta = :idPuntoVenta AND " +
            "       compra.id_numero = :idNumero  ")
    void delete(int idProveedor, String idFcncnd, int idPuntoVenta, int idNumero) throws Exception;

    @Delete
    void delete(CompraBd entity) throws Exception;

    @Query(value = "SELECT compra.* " +
            "FROM   compra " +
            "       INNER JOIN movimientos " +
            "           ON movimientos.id_movil = compra.id_movil " +
            "WHERE  movimientos.fe_anulacion IS NULL AND " +
            "       movimientos.fe_sincronizacion IS NULL")
    List<CompraBd> recoveryNoEnviados() throws Exception;

    @Query(value = "SELECT COUNT(*) " +
            "FROM   compra " +
            "       INNER JOIN movimientos " +
            "           ON movimientos.id_movil = compra.id_movil " +
            "WHERE  compra.id_movil = :idMovil AND " +
            "       compra.sn_anulado = 'N' AND " +
            "       movimientos.fe_sincronizacion IS NULL")
    int isEnviado(String idMovil) throws Exception;/* si es  1 NO fue enviado*/

    @Query(value = "SELECT compra.* " +
            "FROM   compra " +
            "WHERE  compra.id_hoja = :idHoja AND " +
            "       compra.id_sucursal_hoja = :idSucursal ")
    List<CompraBd> recoverEgresosByHoja(int idHoja, int idSucursal) throws Exception;


    @Query(value = "SELECT compra.* " +
            "FROM   compra " +
            "WHERE  compra.id_proveedor = :idProveedor AND " +
            "       compra.id_fcncnd = :idFcncnd AND " +
            "       compra.id_ptovta = :idPuntoVenta AND " +
            "       compra.id_numero = :idNumero  ")
    CompraBd existsEgreso(int idProveedor, String idFcncnd, int idPuntoVenta, int idNumero) throws Exception;
}
