package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ar.vgmsistemas.database.dao.entity.EntregaRendicionBd;

import java.util.List;

@Dao
public interface IEntregaRendicionDao {
    @Insert
    void create(EntregaRendicionBd entity) throws Exception;
    //PkEntregaRendicionBd create(EntregaRendicionBd entity) throws Exception;

    @Query(value = "SELECT entrega_rendicion.* " +
            "FROM   entrega_rendicion   " +
            "WHERE  entrega_rendicion.id_veces = :idVeces AND " +
            "       entrega_rendicion.id_legajo = :idLegajo AND " +
            "       entrega_rendicion.fe_entrega = :feEntrega")
    EntregaRendicionBd recoveryByID(int idVeces, int idLegajo, String feEntrega) throws Exception;

    @Query(value = "SELECT entrega_rendicion.* FROM entrega_rendicion")
    List<EntregaRendicionBd> recoveryAll() throws Exception;

    @Update
    void update(EntregaRendicionBd entity) throws Exception;

    @Delete
    void delete(EntregaRendicionBd entity) throws Exception;

    @Query(value = "DELETE FROM  entrega_rendicion " +
            "WHERE  entrega_rendicion.id_veces = :idVeces AND " +
            "       entrega_rendicion.id_legajo = :idLegajo AND " +
            "       entrega_rendicion.fe_entrega = :feEntrega")
    void delete(int idVeces, int idLegajo, String feEntrega) throws Exception;

    @Query(value = "SELECT entrega_rendicion.* " +
            "FROM   entrega_rendicion " +
            "       INNER JOIN movimientos " +
            "           ON  movimientos.id_movil = entrega_rendicion.id_movil " +
            "WHERE  movimientos.fe_sincronizacion IS NULL ")
    List<EntregaRendicionBd> recoveryNoEnviadas() throws Exception;
}
