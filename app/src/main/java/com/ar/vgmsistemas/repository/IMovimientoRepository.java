package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.MovimientoBd;
import com.ar.vgmsistemas.entity.HojaDetalle;
import com.ar.vgmsistemas.entity.Movimiento;

import java.text.ParseException;
import java.util.List;


public interface IMovimientoRepository extends IGenericRepository<Movimiento, Integer> {

    int getCantidadPedidosEnviados() throws Exception;

    int getCantidadPedidosSinEnviar() throws Exception;

    int getCantidadNoPedidosEnviados() throws Exception;

    int getCantidadNoPedidosSinEnviar() throws Exception;

    int getCantidadPedidosAnuladosEnviados() throws Exception;

    int getCantidadPedidosAnuladosSinEnviar() throws Exception;

    int getCantidadErroresSinEnviar() throws Exception;

    //int getCantidadUbicacionesSinEnviar() throws Exception;

    void updateFechaSincronizacion(Object object) throws Exception;

    void updateFechasSincronizacion(List<HojaDetalle> object) throws Exception;

    void updateFechaSincronizacion() throws Exception;

    void updateFechaAnulacion(Object object) throws Exception;

    void updateFechaSincronizacionReenvio() throws Exception;

    boolean isDatosPendientesEnvio() throws Exception;

    Movimiento getMovimiento(String idMovil) throws Exception;

    int getCantidadClientesEnviados() throws Exception;

    int getCantidadClientesSinEnviar() throws Exception;

    int getCantidadRecibosEnviados() throws Exception;

    int getCantidadRecibosSinEnviar() throws Exception;

    int getCantidadPedidosAnulados() throws Exception;

    void updateIsModificado(Movimiento movimiento) throws Exception;

    void updateIdMovil(Movimiento movimiento) throws Exception;

    int getCantidadUbicacionesGeograficasSinEnviar() throws Exception;

    int getCantidadUbicacionesGeograficasEnviadas() throws Exception;

    List<Movimiento> getMovimientosPendientes() throws Exception;


    //Para la Lista de Clientes

    //se pasan a la interfaz ICantidadMovimientosDao
    //int getCantidadPedidos(int idSucursal, int idCliente, int idComercio) throws Exception;
    // int getCantidadNoAtenciones(int idSucursal, int idCliente, int idComercio) throws Exception;

    Movimiento recoveryByIdMovil(String idMovil) throws Exception;

    int getCantidadHojasDetalleSinEnviar() throws Exception;

    int getCantidadHojasDetalleEnviados() throws Exception;

    int getCantidadEgresosSinEnviar() throws Exception;

    int getCantidadEgresosEnviados() throws Exception;

    int getCantidadEntregasSinEnviar() throws Exception;

    int getCantidadEntregasEnviadas() throws Exception;

    Movimiento mappingToDto(MovimientoBd movimientoBd) throws ParseException;
}
