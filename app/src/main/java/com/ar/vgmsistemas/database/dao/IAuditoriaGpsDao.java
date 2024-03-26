package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.AuditoriaGpsBd;

import java.util.List;

@Dao
public interface IAuditoriaGpsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long create(AuditoriaGpsBd entity) throws Exception; /*puede mandar una o varias entidades*/

    @Query(value = "SELECT COUNT(*) " +
            "FROM   auditoria_gps " +
            "WHERE  auditoria_gps.id_movil = :idMovil")
    long existsAuditoria(String idMovil) throws Exception;

    @Query(value = "SELECT COUNT(*) " +
            "FROM   auditoria_gps " +
            "WHERE  auditoria_gps.fe_registro_movil BETWEEN :fechaAnterior AND :fechaRegistroMovil AND " +
            "       auditoria_gps.ti_accion = :tipoAccion ")
    long existsAuditoria(String fechaAnterior, String fechaRegistroMovil, String tipoAccion) throws Exception;

    @Query(value = "SELECT auditoria_gps.* " +
            "FROM   auditoria_gps" +
            "       INNER JOIN movimientos " +
            "       ON auditoria_gps.id_movil = movimientos.id_movil " +
            "WHERE  movimientos.fe_sincronizacion is null")
    List<AuditoriaGpsBd> recoveryAEnviar() throws Exception;

}
