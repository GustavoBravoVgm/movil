package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.CantidadesMovimiento;
import com.ar.vgmsistemas.entity.Movimiento;
import com.ar.vgmsistemas.repository.ICantidadesMovimientoRepository;
import com.ar.vgmsistemas.repository.IMovimientoRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MovimientoBo {

    private final IMovimientoRepository _movimientoRepository;
    private final ICantidadesMovimientoRepository _cantidadesMovimientoRepository;
    //private final IControlesMovilRepository _controlesMovilRepository;
    private RepositoryFactory _repoFactory;
    private final UbicacionGeograficaBo ubicacionGeograficaBo;


    public MovimientoBo(RepositoryFactory repoFactory) {
        this._cantidadesMovimientoRepository = repoFactory.getCantidadesMovimientoRepository();
        this._movimientoRepository = repoFactory.getMovimientoRepository();
        this._repoFactory = repoFactory;
        this.ubicacionGeograficaBo = new UbicacionGeograficaBo(repoFactory);
    }

    public boolean isDatosPendientesEnvio() throws Exception {
        return _movimientoRepository.isDatosPendientesEnvio();
    }

    public void create(final Movimiento movimiento) throws Exception {
        _movimientoRepository.create(movimiento);
    }

    //TODO Se llama solo desde FrmSincronizacion
    public int getCantidadPedidosEnviados() throws Exception {
        return _movimientoRepository.getCantidadPedidosEnviados();
    }

    /*TODO
     * Se llama desde:
     *  MovimientoBo - existsMovimientosEnviados
     *  FrmSincronizacion - loadData
     *  FrmAdministracionBaseDatos - validarDatosPendientes
     */
    public int getCantidadPedidosSinEnviar() throws Exception {
        return _movimientoRepository.getCantidadPedidosSinEnviar();
    }

    //TODO FrmSincronizacion - loadData
    public int getCantidadNoPedidosEnviados() throws Exception {
        return _movimientoRepository.getCantidadNoPedidosEnviados();
    }

    /*
     * TODO
     * TaskManager - enviarDatos
     * FrmSincronizacion - loadData
     * FrmAdministracionBaseDatos - validarDatosPendientes
     */
    public int getCantidadNoPedidosSinEnviar() throws Exception {
        return _movimientoRepository.getCantidadNoPedidosSinEnviar();
    }

    //TODO Se llama desde FrmSincronizacion - loadData
    public int getCantidadPedidosAnulados() throws Exception {
        return _movimientoRepository.getCantidadPedidosAnulados();
    }

    public int getCantidadPedidosAnuladosEnviados() throws Exception {
        return _movimientoRepository.getCantidadPedidosAnuladosEnviados();
    }

    public int getCantidadPedidosAnuladosSinEnviar() throws Exception {
        return _movimientoRepository.getCantidadPedidosAnuladosSinEnviar();
    }

    public int getCantidadHojasDetalleSinEnviar() throws Exception {
        return _movimientoRepository.getCantidadHojasDetalleSinEnviar();
    }

    public int getCantidadHojasDetalleEnviadas() throws Exception {
        return _movimientoRepository.getCantidadHojasDetalleEnviados();
    }

    public Movimiento getMovimiento(String idMovil) throws Exception {
        return _movimientoRepository.getMovimiento(idMovil);
    }

    public void delete(Movimiento movimiento) throws Exception {
        _movimientoRepository.delete(movimiento);
    }

    //TODO FrmSincronizacion - loadData
    public int getCantidadClientesNoEnviados() throws Exception {
        return _movimientoRepository.getCantidadClientesSinEnviar();
    }

    //TODO FrmSincronizacion - loadData
    public int getCantidadClientesEnviados() throws Exception {
        return _movimientoRepository.getCantidadClientesEnviados();
    }

    //TODO FrmSincronizacion - loadData()
    public int getCantidadRecibosEnviados() throws Exception {
        return _movimientoRepository.getCantidadRecibosEnviados();
    }

    /*
     * TODO
     * TaskManager - enviarDatos
     * FrmSincronizacion - loadData
     * FrmAdministracionBaseDatos - validarDatosPendientes
     */
    public int getCantidadRecibosSinEnviar() throws Exception {
        return _movimientoRepository.getCantidadRecibosSinEnviar();
    }

    //TODO FrmSincronizacion - loadData
    public int getCantidadUbicacionesGeograficasSinEnviar() throws Exception {
        return _movimientoRepository.getCantidadUbicacionesGeograficasSinEnviar();
    }

    //TODO FrmSincronizacion - loadData
    public int getCantidadUbicacionesGeograficasEnviadas() throws Exception {
        return _movimientoRepository.getCantidadUbicacionesGeograficasEnviadas();
    }

    public int getCantidadEgresosEnviados() throws Exception {
        return _movimientoRepository.getCantidadEgresosEnviados();
    }

    public int getCantidadEgresosSinEnviar() throws Exception {
        return _movimientoRepository.getCantidadEgresosSinEnviar();
    }

    public int getCantidadEntregasEnviadas() throws Exception {
        return _movimientoRepository.getCantidadEntregasEnviadas();
    }

    public int getCantidadEntregasSinEnviar() throws Exception {
        return _movimientoRepository.getCantidadEntregasSinEnviar();
    }

    public int getCantidadErroresSinEnviar() throws Exception {
        return _movimientoRepository.getCantidadErroresSinEnviar();
    }

    public Movimiento recoveryByIdMovil(String idMovil) throws Exception {
        return _movimientoRepository.recoveryByIdMovil(idMovil);
    }

    /**
     * Antes de realizar el update en la tabla movimientos
     * de las VENTAS llamo al metodo que realiza el backUp del archivo de
     * base de datos
     */
    public void reenvioPedidos() throws Exception {
        if (this._repoFactory.dataBaseExists()) {
            this._repoFactory.backupDb();
            try {
                _movimientoRepository.updateFechaSincronizacionReenvio();
                ubicacionGeograficaBo.reenvioUbicaciones();
            } catch (Exception e) {
                throw new Exception("Error al marcar para reenvio movimientos y ubicaciones");
            }
        } else {
            throw new Exception("La base de datos no existe");
        }
    }

	
	/*
	 Si HAY PEDIDOS PENDIENTES DE ENVIAR, entonces ->False
	 Si HAY PEDIDOS ENVIADOS, entonces->True
	 */

    public boolean existsMovimientosEnviados() throws Exception {
        boolean existsMovimientos = false;
        int cantidadPedidosPendientes = this.getCantidadPedidosSinEnviar();
        //Valido que no existan pedidos pendientes de envio
        if (cantidadPedidosPendientes == 0) {
            int cantidadPedidosEnviados = this.getCantidadPedidosEnviados();
            //Valido que existan pedidos ya enviados para marcarlos para reenvio
            if (cantidadPedidosEnviados > 0) {
                //Retorno true si: NO HAY PEDIDOS PENDIENTES y HAY PEDIDOS ENVIADOS
                existsMovimientos = true;
            }
        }

        return existsMovimientos;
    }

    public int getCantidadPedidosCliente(int idSucursal, int idCliente, int idComercio) throws Exception {
        return _cantidadesMovimientoRepository.getCantidadPedidos(idSucursal, idCliente, idComercio);
    }

    public int getCantidadNoPedidosCliente(int idSucursal, int idCliente, int idComercio) throws Exception {
        return _cantidadesMovimientoRepository.getCantidadNoAtenciones(idSucursal, idCliente, idComercio);
    }

    //Tarea #3266
    public List<CantidadesMovimiento> recoveryCantidadesClientes() throws Exception {
        return _cantidadesMovimientoRepository.recoveryAll();
    }

}
