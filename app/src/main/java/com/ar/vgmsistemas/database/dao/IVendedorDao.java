package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.VendedorBd;

import java.util.List;

@Dao
public interface IVendedorDao {

    @Query(value = "SELECT vendedor.* " +
            "FROM   vendedor " +
            "       INNER JOIN rrhh " +
            "           ON  rrhh.id_legajo = vendedor.id_vendedor " +
            "WHERE vendedor.id_vendedor = :idVendedor")
    VendedorBd recoveryByID(Integer idVendedor) throws Exception;

    @Query(value = "SELECT vendedor.* " +
            "FROM vendedor")
    List<VendedorBd> recoveryAll() throws Exception;

    @Query(value = "SELECT vendedor.* " +
            "FROM   vendedor " +
            "       INNER JOIN rrhh " +
            "           ON  rrhh.id_legajo = vendedor.id_vendedor " +
            "WHERE  rrhh.id_legajo = :nombreUsuario AND " +
            "       rrhh.de_password = :password")
    VendedorBd recoveryByLogin(String nombreUsuario, String password) throws Exception;

    @Query(value = "SELECT vendedor.* " +
            "FROM   vendedor " +
            "       INNER JOIN ventas " +
            "           ON  ventas.id_vendedor = vendedor.id_vendedor " +
            "WHERE  ventas.id_fcnc = :idDocumento AND " +
            "       ventas.id_tipoab = :idLetra AND " +
            "       ventas.id_ptovta = :puntoVenta AND " +
            "       ventas.id_numdoc = :idNumeroDocumento")
    VendedorBd getVendedorVenta(String idDocumento, String idLetra, int puntoVenta,
                                long idNumeroDocumento) throws Exception;

}
