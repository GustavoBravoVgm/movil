package com.ar.vgmsistemas.repository;

import android.location.Location;

import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.entity.key.PkVenta;

import java.util.Date;
import java.util.List;

public interface IVentaRepository extends IGenericRepository<Venta, PkVenta> {

    public static final int ESTADO_ENVIADAS = 1;
    public static final int ESTADO_NO_ENVIADAS = 2;
    public static final int ESTADO_A_ENVIAR = 4;
    public static final int ESTADO_TODAS = 0;
    public static final int ESTADO_ANULADAS = 3;

    /**
     * Recupera los documentos por tipo de documento
     *
     * @param tipoDocumento tipo de documento
     * @param estado        estado de las ventas a recuperar, ESTADO_ENVIADA, ESTADO_NO_ENVIADA, TODAS
     * @return lista de ventas
     */
    List<Venta> recoveryByDocumento(String tipoDocumento, int estado) throws Exception;

    List<Venta> recoveryByCliente(Cliente cliente, String tipoDocumento) throws Exception;

    void updateFechaSincronizacion(Venta venta) throws Exception;

    void updateByIdMovil(Venta venta) throws Exception;

    int getCantidadVentas(Cliente cliente) throws Exception;

    int getCantidadVentasNoEnviadas(boolean isPosterior) throws Exception;

    List<Venta> recoveryVentas(Date fechaDesde, Date fechaHasta, int idVendedor) throws Exception;

    boolean isEnviado(Venta venta) throws Exception;

    int getCantidadVentasPosteriores() throws Exception;

    boolean isGenerado(Venta venta) throws Exception;

    void setGenerado(String idDocumento, String idLetra, int puntoVenta, long idNumeroDocumento) throws Exception;

    List<Venta> recoveryByRepartidor(int idRepartidor) throws Exception;

    List<Venta> recoveryByPeriodo(Date fechaDesde, Date fechaHasta, int idVendedor) throws Exception;

    Venta getCredito(PkVenta pkVenta) throws Exception;

    boolean createVentaTransaction(Venta venta, Location location) throws Exception;

    void updateVentaTransaction(Venta venta, Location location, String idMovilOriginal) throws Exception;

    boolean anularVentaTransaction(Venta venta, Location location, int modo) throws Exception;

}
