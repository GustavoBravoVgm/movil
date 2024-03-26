package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.InfoBd;

import java.util.List;

@Dao
public interface IInfoDao {
    @Query(value = "SELECT info.* " +
            "FROM info " +
            "WHERE info.empresa = :empresa")
    InfoBd recoveryByID(String empresa) throws Exception;

    @Query(value = "SELECT info.* " +
            "FROM info ")
    List<InfoBd> recoveryAll() throws Exception;
}
