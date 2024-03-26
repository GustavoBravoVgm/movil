package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.ReciboDetalleBd;

import java.util.List;

@Dao
public interface IReciboDetalleDao {
    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    void create(ReciboDetalleBd reciboDetalle) throws Exception;

    @Query(value = "SELECT pagos.*" +
            "FROM   pagos " +
            "       INNER JOIN documentos_cuenta " +
            "           ON  pagos.id_fcnc = documentos_cuenta.id_fcnc " +
            "WHERE  pagos.id_recpvta = :idPuntoVenta AND " +
            "       pagos.id_recibo = :idRecibo")
    List<ReciboDetalleBd> recoveryByRecibo(int idPuntoVenta, long idRecibo) throws Exception;
}
