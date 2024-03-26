package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.ar.vgmsistemas.database.dao.entity.RetencionBd;

import java.util.List;

@Dao
public interface IRetencionDao {
    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    void create(RetencionBd retencion) throws Exception;

    @Query(value = "SELECT COUNT(*) " +
            "FROM retenciones " +
            "WHERE  retenciones.id_fcnc = :idDocumento AND " +
            "       retenciones.id_tipoab = :idLetra AND " +
            "       retenciones.id_ptovta = :idCliente AND " +
            "       retenciones.id_numdoc = :numero")
    int existeRetencion(String idDocumento, String idLetra, Long numero, int idCliente)
            throws Exception;

    @Query(value = "SELECT retenciones.* " +
            "FROM retenciones " +
            "WHERE  retenciones.id_fcnc = :idDocumento AND " +
            "       retenciones.id_tipoab = :idLetra AND " +
            "       retenciones.id_ptovta = :puntoVenta AND " +
            "       retenciones.id_numdoc = :idNumeroDocumento")
    RetencionBd recoveryByID(String idDocumento, String idLetra, int puntoVenta, long idNumeroDocumento)
            throws Exception;

    @Transaction
    @Query(value = "DELETE FROM retenciones " +
            "WHERE  retenciones.id_entrega = :id")
    void deleteByEntrega(Integer id) throws Exception;


    @Query(value = "SELECT retenciones.* " +
            "FROM   retenciones " +
            "       INNER JOIN entrega " +
            "           ON entrega.id_entrega = retenciones.id_entrega " +
            "       INNER JOIN plancta " +
            "           ON plancta.id_plancta = retenciones.id_plancta_retencion " +
            "WHERE  retenciones.id_entrega = :id")
    List<RetencionBd> recoveryByEntrega(Integer id) throws Exception;

}
