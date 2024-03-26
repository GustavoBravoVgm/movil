package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.ListaPrecioBd;

import java.util.List;

@Dao
public interface IListaPrecioDao {
    @Query(value = "SELECT listasPrecios.* " +
            "FROM   listasPrecios " +
            "WHERE  listasPrecios.sn_palm = 'S' " +
            "ORDER BY listasPrecios.de_lista ASC ")
    List<ListaPrecioBd> recoveryAll() throws Exception;

    @Query(value = "SELECT listasPrecios.* " +
            "FROM   listasPrecios " +
            "WHERE  listasPrecios.id_lista = :idLista ")
    ListaPrecioBd recoveryByID(int idLista) throws Exception;

    @Query(value = "SELECT listasPrecios.* " +
            "FROM   listasPrecios " +
            "WHERE  listasPrecios.sn_palm = 'S' AND " +
            "       listasPrecios.sn_seleccionable_movil = 'S'" +
            "ORDER BY listasPrecios.de_lista ASC ")
    List<ListaPrecioBd> recoveryAllSeleccionable() throws Exception;

}
