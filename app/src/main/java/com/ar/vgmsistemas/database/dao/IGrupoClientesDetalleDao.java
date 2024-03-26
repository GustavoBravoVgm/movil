package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.GrupoClientesDetalleBd;

import java.util.List;

@Dao
public interface IGrupoClientesDetalleDao {

    @Query(value = "SELECT grupo_clientes_detalle.* " +
            "FROM grupo_clientes_detalle " +
            "WHERE grupo_clientes_detalle.id_grupo_clie = :idGrupoClie AND " +
            "grupo_clientes_detalle.id_grupo_clie_detalle = :idGrupoClieDetalle")
    GrupoClientesDetalleBd recoveryByID(int idGrupoClie, int idGrupoClieDetalle) throws Exception;

    @Query(value = "SELECT grupo_clientes_detalle.* " +
            "FROM grupo_clientes_detalle " +
            "WHERE grupo_clientes_detalle.id_grupo_clie = :idGrupoClie")
    List<GrupoClientesDetalleBd> recoveryByIDGrupoCliente(int idGrupoClie) throws Exception;
}
