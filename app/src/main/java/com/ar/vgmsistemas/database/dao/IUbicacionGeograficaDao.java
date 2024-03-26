package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ar.vgmsistemas.database.dao.entity.UbicacionGeograficaBd;

import java.util.List;

@Dao
public interface IUbicacionGeograficaDao {
    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    void create(UbicacionGeograficaBd ubicacionGeografica) throws Exception;

    @Query(value = "SELECT * " +
            "FROM ubicacion_geografica " +
            "WHERE ubicacion_geografica.id = :id")
    UbicacionGeograficaBd recoveryByID(Integer id) throws Exception;

    @Query(value = "SELECT * " +
            "FROM ubicacion_geografica ")
    List<UbicacionGeograficaBd> recoveryAll() throws Exception;

    /*se debe usar para el metodo void update(UbicacionGeografica ubicacionGeografica) throws Exception*/
    @Update(onConflict = OnConflictStrategy.ROLLBACK)
    void update(UbicacionGeograficaBd entity) throws Exception;

    @Delete
    void delete(UbicacionGeograficaBd... entity) throws Exception;

    @Query(value = "DELETE FROM ubicacion_geografica " +
            "WHERE ubicacion_geografica.id = :id")
    void delete(Integer id) throws Exception;

    @Query(value = "SELECT * " +
            "FROM ubicacion_geografica " +
            "WHERE ubicacion_geografica.id_movil = :idMovil " +
            "LIMIT 1")
    UbicacionGeograficaBd recoveryByIdMovil(String idMovil) throws Exception;

    @Query(value = "SELECT ubicacion_geografica.* " +
            "FROM   ubicacion_geografica " +
            "WHERE  ubicacion_geografica.fe_sincronizacion IS NULL")
    List<UbicacionGeograficaBd> recoveryNoEnviados() throws Exception;

    @Query(value = "UPDATE ubicacion_geografica " +
            "SET    fe_sincronizacion = :fechaSincronizacion " +
            "WHERE  ubicacion_geografica.id_legajo = :idLegajo AND " +
            "       ubicacion_geografica.fe_posicion = :fechaPosicionRecibida")
    void updateFechaSincronizacion(int idLegajo, String fechaPosicionRecibida,
                                   String fechaSincronizacion) throws Exception;

    @Query(value = "UPDATE ubicacion_geografica " +
            "SET    fe_sincronizacion = :fechaSincronizacion " +
            "WHERE  ubicacion_geografica.fe_sincronizacion IS NULL")
    void marcarUbicacionesComoEnviadas(String fechaSincronizacion) throws Exception;

    @Query(value = "UPDATE ubicacion_geografica " +
            "SET    fe_sincronizacion = NULL " +
            "WHERE  ubicacion_geografica.id_movil = :idMovil")
    void reenvioUbicaciones(String idMovil) throws Exception;

    @Query(value = "SELECT IFNULL(max(id),0)  " +
            "FROM ubicacion_geografica ")
    int maxIdUbicacionGeo() throws Exception;
}
