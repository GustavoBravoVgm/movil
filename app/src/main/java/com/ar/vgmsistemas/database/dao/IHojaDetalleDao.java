package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

import com.ar.vgmsistemas.database.dao.entity.HojaDetalleBd;

import java.util.List;

@Dao
public interface IHojaDetalleDao {
    @Query(value = "SELECT hoja_detalle.* " +
            "FROM hoja_detalle " +
            "WHERE  hoja_detalle.id_sucursal = :idSucursal AND " +
            "       hoja_detalle.id_hoja = :idHoja AND " +
            "       hoja_detalle.id_fcnc = :idFcnc AND " +
            "       hoja_detalle.id_tipoab = :idTipoab AND " +
            "       hoja_detalle.id_ptovta = :idPtovta AND " +
            "       hoja_detalle.id_numdoc = :idNumdoc")
    HojaDetalleBd recoveryByID(int idSucursal, int idHoja, String idFcnc, String idTipoab,
                               int idPtovta, long idNumdoc) throws Exception;

    /*
    void updateState(HojaDetalle detalle, String state) throws Exception;
    void updateValoresEntrega(HojaDetalle detalle) throws Exception;
    * Se puede reducir en  update(HojaDetalle entity)*/
    @Update
    void update(HojaDetalleBd entity) throws Exception;

    @Query(value = "SELECT hoja_detalle.* " +
            "FROM hoja_detalle " +
            "WHERE  hoja_detalle.id_sucursal = :idSucursal AND " +
            "       hoja_detalle.id_hoja = :idHoja ")
    List<HojaDetalleBd> recoveryByHoja(int idSucursal, int idHoja) throws Exception;

    @Query(value = "SELECT hoja_detalle.* " +
            "FROM   hoja_detalle " +
            "       INNER JOIN entrega " +
            "           ON  entrega.id_entrega = hoja_detalle.id_entrega_detalle " +
            "       INNER JOIN cheques " +
            "           ON  cheques.id_entrega = entrega.id_entrega " +
            "       INNER JOIN banco " +
            "           ON  banco.id_bancogirado = cheques.id_bancogirado_cheque " +
            "WHERE  hoja_detalle.id_sucursal = :idSucursal AND " +
            "       hoja_detalle.id_hoja = :idHoja ")
    List<HojaDetalleBd> recoveryChequesByHoja(int idSucursal, int idHoja) throws Exception;

    @Query(value = "SELECT hoja_detalle.* " +
            "FROM   hoja_detalle " +
            "       INNER JOIN entrega " +
            "           ON  entrega.id_entrega = hoja_detalle.id_entrega_detalle " +
            "WHERE  hoja_detalle.id_sucursal = :idSucursal AND " +
            "       hoja_detalle.id_hoja = :idHoja AND" +
            "       hoja_detalle.id_entrega_detalle IS NOT NULL")
    List<HojaDetalleBd> recoveryByHojaConEntrega(int idSucursal, int idHoja) throws Exception;


    @Query(value = "SELECT hoja_detalle.* " +
            "FROM   hoja_detalle " +
            "       INNER JOIN entrega " +
            "           ON  entrega.id_entrega = hoja_detalle.id_entrega_detalle " +
            "WHERE  hoja_detalle.id_sucursal = :idSucursal AND " +
            "       hoja_detalle.id_hoja = :idHoja AND" +
            "       hoja_detalle.id_fcnc = :idFcnc AND " +
            "       hoja_detalle.id_tipoab = :idTipoab AND " +
            "       hoja_detalle.id_ptovta = :idPtovta AND " +
            "       hoja_detalle.id_numdoc = :idNumdoc AND " +
            "       hoja_detalle.id_entrega_detalle IS NOT NULL")
    HojaDetalleBd recoveryByIdConEntrega(int idSucursal, int idHoja, String idFcnc,
                                         String idTipoab, int idPtovta, long idNumdoc)
            throws Exception;

    @Query(value = "SELECT hoja_detalle.* " +
            "FROM   hoja_detalle " +
            "       INNER JOIN movimientos " +
            "           ON  movimientos.id_movil = hoja_detalle.id_movil " +
            "WHERE  movimientos.de_tabla = 'hoja_detalle' AND " +
            "       movimientos.fe_sincronizacion IS NULL AND" +
            "       hoja_detalle.id_entrega_detalle IS NULL")
    List<HojaDetalleBd> recoveryAEnviar() throws Exception;

    @Query(value = "SELECT COUNT(*) " +
            "FROM   hoja_detalle " +
            "       INNER JOIN movimientos " +
            "           ON  movimientos.id_movil = hoja_detalle.id_movil " +
            "WHERE  hoja_detalle.id_sucursal = :idSucursal AND " +
            "       hoja_detalle.id_hoja = :idHoja AND " +
            "       hoja_detalle.id_fcnc = :idFcnc AND " +
            "       hoja_detalle.id_tipoab = :idTipoab AND " +
            "       hoja_detalle.id_ptovta = :idPtovta AND " +
            "       hoja_detalle.id_numdoc = :idNumdoc AND " +
            "       movimientos.fe_sincronizacion IS NULL")
    int isEnviado(int idSucursal, int idHoja, String idFcnc, String idTipoab, int idPtovta, long idNumdoc) throws Exception;

    @Query(value = "SELECT IFNULL(movimientos.fe_sincronizacion,'') " +
            "FROM   hoja_detalle " +
            "       INNER JOIN movimientos " +
            "           ON  movimientos.id_movil = hoja_detalle.id_movil " +
            "WHERE  hoja_detalle.id_movil = :idMovil")
    String isEnviado(String idMovil) throws Exception;


    @Query(value = "SELECT hoja_detalle.id_entrega_detalle " +
            "FROM   hoja_detalle " +
            "WHERE  hoja_detalle.id_sucursal = :idSucursal AND " +
            "       hoja_detalle.id_hoja = :idHoja AND " +
            "       hoja_detalle.id_fcnc = :idFcnc AND " +
            "       hoja_detalle.id_tipoab = :idTipoab AND " +
            "       hoja_detalle.id_ptovta = :idPtovta AND " +
            "       hoja_detalle.id_numdoc = :idNumdoc ")
    int recoveryIdEntrega(int idSucursal, int idHoja, String idFcnc, String idTipoab, int idPtovta, long idNumdoc) throws Exception;

    @Query(value = "SELECT hoja_detalle.* " +
            "FROM   hoja_detalle " +
            "WHERE  hoja_detalle.id_sucursal = :idSucursal")
    List<HojaDetalleBd> recoveryBySucursal(int idSucursal) throws Exception;

    @Query(value = "SELECT hoja_detalle.* " +
            "FROM   hoja_detalle " +
            "WHERE  hoja_detalle.id_entrega_detalle = :idEntrega")
    List<HojaDetalleBd> recoveryByIdEntrega(int idEntrega) throws Exception;


    @Query(value = "SELECT hoja_detalle.* " +
            "FROM   hoja_detalle " +
            "       INNER JOIN movimientos " +
            "           ON  movimientos.id_movil = hoja_detalle.id_movil " +
            "WHERE  movimientos.de_tabla = 'hoja_detalle' AND " +
            "       hoja_detalle.id_entrega_detalle IS NOT NULL AND " +
            "       movimientos.fe_sincronizacion IS NULL " +
            "GROUP BY hoja_detalle.id_entrega_detalle")
    List<HojaDetalleBd> recoveryAEnviarUnicaEntrega() throws Exception;

    @Query(value = "SELECT hoja_detalle.* " +
            "FROM   hoja_detalle")
    List<HojaDetalleBd> recoveryAll() throws Exception;
}
