package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ar.vgmsistemas.database.dao.entity.MovimientoBd;

import java.util.List;

@Dao
public interface IMovimientoDao {
    /***creates****/
    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    void create(MovimientoBd entity) throws Exception;


    /***gets****/
    @Query(value = "SELECT movimientos.* " +
            "FROM   movimientos " +
            "WHERE  movimientos.id_movimiento = :id")
    MovimientoBd recoveryByID(Integer id) throws Exception;

    @Query(value = "SELECT movimientos.* " +
            "FROM movimientos")
    List<MovimientoBd> recoveryAll() throws Exception;

    @Query(value = "SELECT movimientos.* " +
            "FROM   movimientos " +
            "WHERE  movimientos.fe_sincronizacion IS NULL AND " +
            "       movimientos.fe_anulacion IS NOT NULL")
    List<MovimientoBd> recoveryPendientes() throws Exception;

    /*Incluye lo que era antes Movimiento getMovimiento(String idMovil) throws Exception;*/
    @Query(value = "SELECT movimientos.* " +
            "FROM   movimientos " +
            "WHERE  movimientos.id_movil = :idMovil " +
            "LIMIT 1")
    MovimientoBd recoveryByIdMovil(String idMovil) throws Exception;

    @Query(value = "SELECT movimientos.* " +
            "FROM   movimientos " +
            "WHERE  movimientos.fe_sincronizacion IS NULL")
    List<MovimientoBd> getMovimientosPendientes() throws Exception;

    /***deletes****/
    @Delete
    void delete(MovimientoBd movimiento) throws Exception;

    @Query(value = "DELETE FROM   movimientos " +
            "WHERE  movimientos.id_movimiento = :id")
    void delete(Integer id) throws Exception;

    /***counters enviados****/
    /*El Método engloba:
    int getCantidadPedidosEnviados() throws Exception;-->de_tabla = 'ventas'
    int getCantidadNoPedidosEnviados() throws Exception;-->de_tabla = 'No_pedidos'
    int getCantidadHojasDetalleEnviados() throws Exception; --> de_tabla = 'hoja_detalle'
    int getCantidadEgresosEnviados() throws Exception;-->de_tabla = 'compra'
    int getCantidadEntregasEnviadas() throws Exception;-->de_tabla = 'entrega_rendicion'
    int getCantidadClientesEnviados() throws Exception;-->de_tabla = 'comercio'
    int getCantidadRecibosEnviados() throws Exception;-->de_tabla = 'talon_recibo'
    trabajar los métodos particulares en una capa externa
    * */
    @Query(value = "SELECT COUNT(*) " +
            "FROM   movimientos " +
            "WHERE  movimientos.fe_sincronizacion IS NOT NULL AND " +
            "       movimientos.de_tabla = :deTabla AND " +
            "       movimientos.ti_movimiento IS NOT 'B' AND " +
            "       movimientos.fe_anulacion IS NULL")
    int getCantidadEnviadosPorTabla(String deTabla) throws Exception;

    @Query(value = "SELECT COUNT(*) " +
            "FROM   movimientos " +
            "WHERE  movimientos.fe_sincronizacion IS NOT NULL AND " +
            "       movimientos.de_tabla = 'ventas' AND " +
            "       movimientos.fe_anulacion IS NOT NULL")
    int getCantidadPedidosAnuladosEnviados() throws Exception;

    /***counters no enviados****/
    /*El Método engloba:
    int getCantidadAuditoriasGpsSinEnviar() throws Exception;-->de_tabla = 'auditoria_gps'
    int getCantidadPedidosSinEnviar() throws Exception;-->de_tabla = 'ventas'
    int getCantidadNoPedidosSinEnviar() throws Exception;-->de_tabla = 'No_pedidos'
    int getCantidadHojasDetalleSinEnviar() throws Exception; --> de_tabla = 'hoja_detalle'
    int getCantidadEgresosSinEnviar() throws Exception;-->de_tabla = 'compra'
    int getCantidadEntregasSinEnviar() throws Exception;-->de_tabla = 'entrega_rendicion'
    int getCantidadErroresSinEnviar() throws Exception;-->de_tabla = 'error_movil'
    int getCantidadRecibosSinEnviar() throws Exception;-->de_tabla = 'talon_recibo'
    int getCantidadClientesSinEnviar() throws Exception;-->de_tabla = 'comercio'
    trabajar los métodos particulares en una capa externa
    * */
    @Query(value = "SELECT COUNT(*) " +
            "FROM   movimientos " +
            "WHERE  movimientos.fe_sincronizacion IS NULL AND " +
            "       movimientos.de_tabla = :deTabla AND " +
            "       movimientos.sn_modificado IS NOT 'S' AND " +
            "       movimientos.fe_anulacion IS NULL")
    int getCantidadPorTablaSinEnviar(String deTabla) throws Exception;

    /**/

    @Query(value = "SELECT COUNT(*) " +
            "FROM   ubicacion_geografica " +
            "WHERE  ubicacion_geografica.fe_sincronizacion IS NULL")
    int getCantidadUbicacionGeograficaSinEnviar() throws Exception;

    @Query(value = "SELECT COUNT(*) " +
            "FROM   movimientos " +
            "WHERE  movimientos.fe_sincronizacion IS NULL AND " +
            "       movimientos.de_tabla = 'ventas' AND " +
            "       movimientos.fe_anulacion IS NOT NULL")
    int getCantidadPedidosAnuladosSinEnviar() throws Exception;

    /***Otros counters***/
    @Query(value = "SELECT COUNT(*) " +
            "FROM   movimientos " +
            "WHERE  movimientos.fe_anulacion IS NOT NULL AND" +
            "       movimientos.sn_modificado IS NOT 'S' AND " +
            "       movimientos.de_tabla = 'ventas'")
    int getCantidadPedidosAnulados() throws Exception;

    /***Se los pasa a su correspondiente interfaz, v_cantidad_movimientos.***/
    //int getCantidadPedidos(int idSucursal, int idCliente, int idComercio) throws Exception
    //int getCantidadNoAtenciones(int idSucursal, int idCliente, int idComercio) throws Exception


    /***counters enviados y no enviados tabla ubicacion_geografica se lleva a su correspondiente interfaz****/
    //int getCantidadUbicacionesGeograficasSinEnviar() throws Exception
    //int getCantidadUbicacionesGeograficasEnviadas() throws Exception

    /***updates****/
    /*El Método engloba:
    void updateIsModificado(int id, String snModificado) throws Exception;
    void updateIdMovil(int id, String idMovil) throws Exception;
    void updateFechaSincronizacion() throws Exception;
    trabajar los métodos particulares en una capa externa
    * */
    @Update(onConflict = OnConflictStrategy.ROLLBACK)
    void update(MovimientoBd entity) throws Exception;

    @Query(value = "UPDATE movimientos " +
            "SET fe_sincronizacion = :fechaSincronizacion " +
            "WHERE movimientos.id_movil = :idMovil")
    void updateFechaSincronizacion(String idMovil, String fechaSincronizacion) throws Exception;

    @Query(value = "UPDATE movimientos " +
            "SET fe_anulacion = :fechaAnulacion " +
            "WHERE movimientos.id_movil = :idMovil")
    void updateFechaAnulacion(String idMovil, String fechaAnulacion) throws Exception;

    @Query(value = "UPDATE movimientos " +
            "SET fe_sincronizacion = null " +
            "WHERE movimientos.de_tabla = :deTabla")
    void updateFechaSincronizacionReenvio(String deTabla) throws Exception;

    @Query(value = "UPDATE movimientos " +
            "SET fe_sincronizacion = :fechaSincronizacion " +
            "WHERE movimientos.id_movimiento IS NOT NULL")
    void updateFechaSincronizacion(String fechaSincronizacion) throws Exception;

    @Query(value = "SELECT COUNT(*) " +
            "FROM   movimientos " +
            "WHERE  movimientos.fe_sincronizacion IS NULL AND " +
            "       (movimientos.fe_anulacion IS NULL OR " +
            "       movimientos.ti_movimiento = 'B')")
    int cantDatosPendientesEnvio() throws Exception;

    @Query(value = "SELECT IFNULL(max(id_movimiento),0)  " +
            "FROM movimientos ")
    int maxIdMovimiento() throws Exception;

}
