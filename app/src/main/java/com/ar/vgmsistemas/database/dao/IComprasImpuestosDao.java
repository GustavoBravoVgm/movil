package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ar.vgmsistemas.database.dao.entity.ComprasImpuestosBd;

import java.util.List;

@Dao
public interface IComprasImpuestosDao {

    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    void create(ComprasImpuestosBd... entity) throws Exception;
    //PkComprasImpuestosBd create(ComprasImpuestosBd... entity) throws Exception;

    @Query(value = "SELECT compras_impuestos.* " +
            "FROM   compras_impuestos " +
            "WHERE  compras_impuestos.id_proveedor = :idProveedor AND " +
            "       compras_impuestos.id_fcncnd = :idFcncnd AND " +
            "       compras_impuestos.id_ptovta = :idPuntoVenta AND " +
            "       compras_impuestos.id_numero = :idNumero AND " +
            "       compras_impuestos.id_impuesto = :idImpuesto AND " +
            "       compras_impuestos.id_tipoab = :idTipoab AND " +
            "       compras_impuestos.id_secuencia = :idSecuencia ")
    ComprasImpuestosBd recoveryByID(int idProveedor, String idFcncnd, int idPuntoVenta, int idNumero,
                                    int idImpuesto, String idTipoab, int idSecuencia) throws Exception;

    @Query(value = "SELECT compras_impuestos.* " +
            "FROM compras_impuestos")
    List<ComprasImpuestosBd> recoveryAll() throws Exception;

    @Update(onConflict = OnConflictStrategy.ROLLBACK)
    void update(ComprasImpuestosBd... entity) throws Exception;

    @Delete
    void delete(ComprasImpuestosBd miCompraImpuesto) throws Exception;

    @Query(value = "UPDATE compras_impuestos SET sn_anulado = 'S' " +
            "WHERE  compras_impuestos.id_proveedor = :idProveedor AND " +
            "       compras_impuestos.id_fcncnd = :idFcncnd AND " +
            "       compras_impuestos.id_ptovta = :idPuntoVenta AND " +
            "       compras_impuestos.id_numero = :idNumero")
    void anularComprasImpuestoByCompra(int idProveedor, String idFcncnd, Integer idPuntoVenta, int idNumero) throws Exception;

    @Query(value = "SELECT compras_impuestos.*" +
            "FROM   compras_impuestos  " +
            "       INNER JOIN compra " +
            "       ON  compra.id_proveedor = compras_impuestos.id_proveedor AND " +
            "           compra.id_fcncnd = compras_impuestos.id_fcncnd AND " +
            "           compra.id_ptovta = compras_impuestos.id_ptovta AND " +
            "           compra.id_numero = compras_impuestos.id_numero " +
            "WHERE  compras_impuestos.id_proveedor = :idProveedor AND " +
            "       compras_impuestos.id_fcncnd = :idFcncnd AND " +
            "       compras_impuestos.id_ptovta = :idPuntoVenta AND " +
            "       compras_impuestos.id_numero = :idNumero")
    List<ComprasImpuestosBd> recoveryByEgreso(int idProveedor, String idFcncnd, Integer idPuntoVenta, int idNumero) throws Exception;

    @Query(value = "DELETE FROM compras_impuestos " +
            "WHERE  compras_impuestos.id_proveedor = :idProveedor AND " +
            "       compras_impuestos.id_fcncnd = :idFcncnd AND " +
            "       compras_impuestos.id_ptovta = :idPuntoVenta AND " +
            "       compras_impuestos.id_numero = :idNumero")
    void deleteComprasImpuestosByCompra(int idProveedor, String idFcncnd, Integer idPuntoVenta, int idNumero) throws Exception;

}
