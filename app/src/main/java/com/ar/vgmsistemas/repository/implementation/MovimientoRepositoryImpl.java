package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IMovimientoDao;
import com.ar.vgmsistemas.database.dao.entity.MovimientoBd;
import com.ar.vgmsistemas.entity.AuditoriaGps;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.Compra;
import com.ar.vgmsistemas.entity.EntregaRendicion;
import com.ar.vgmsistemas.entity.ErrorMovil;
import com.ar.vgmsistemas.entity.HojaDetalle;
import com.ar.vgmsistemas.entity.Movimiento;
import com.ar.vgmsistemas.entity.NoPedido;
import com.ar.vgmsistemas.entity.Recibo;
import com.ar.vgmsistemas.entity.UbicacionGeografica;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.repository.IMovimientoRepository;
import com.ar.vgmsistemas.utils.Formatter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MovimientoRepositoryImpl implements IMovimientoRepository {

    private IMovimientoDao _movimientoDao;

    public MovimientoRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._movimientoDao = db.movimientoDao();
        }
    }

    public Integer create(Movimiento movimiento) throws Exception {
        Date fechaMovimiento = Calendar.getInstance().getTime();
        movimiento.setFechaMovimiento(fechaMovimiento);
        ////creo el nuevo movimiento
        if (movimiento.getId() == 0) {
            movimiento.setId(_movimientoDao.maxIdMovimiento() + 1);
        }
        _movimientoDao.create(mappingToDb(movimiento));
        return movimiento.getId();
    }

    public Movimiento recoveryByID(Integer id) throws Exception {
        return null;
    }


    public List<Movimiento> recoveryAll() throws Exception {
        List<MovimientoBd> listadoMovimientos = _movimientoDao.recoveryAll();
        List<Movimiento> movimientos = new ArrayList<>();
        if (!listadoMovimientos.isEmpty()) {
            for (MovimientoBd item : listadoMovimientos) {
                movimientos.add(mappingToDto(item));
            }
        }
        return movimientos;
    }

    public List<Movimiento> recoveryPendientes() throws Exception {
        List<MovimientoBd> listadoMovimientos = _movimientoDao.recoveryPendientes();
        List<Movimiento> movimientos = new ArrayList<>();
        if (!listadoMovimientos.isEmpty()) {
            for (MovimientoBd item : listadoMovimientos) {
                movimientos.add(mappingToDto(item));
            }
        }
        return movimientos;
    }

    public Movimiento recoveryByIdMovil(String idMovil) throws Exception {
        return mappingToDto(_movimientoDao.recoveryByIdMovil(idMovil));
    }

    public void update(Movimiento entity) throws Exception {

    }

    public void delete(Movimiento movimiento) throws Exception {
        _movimientoDao.delete(mappingToDb(movimiento));
    }

    public void delete(Integer id) throws Exception {
        _movimientoDao.delete(id);
    }

    /*Enviados*/
    public int getCantidadPedidosEnviados() throws Exception {
        return _movimientoDao.getCantidadEnviadosPorTabla(Venta.TABLE);
    }

    public int getCantidadNoPedidosEnviados() throws Exception {
        return _movimientoDao.getCantidadEnviadosPorTabla(NoPedido.TABLE);
    }

    @Override
    public int getCantidadHojasDetalleEnviados() throws Exception {
        return _movimientoDao.getCantidadEnviadosPorTabla(HojaDetalle.TABLE);
    }

    @Override
    public int getCantidadEgresosEnviados() throws Exception {
        return _movimientoDao.getCantidadEnviadosPorTabla(Compra.TABLE);
    }

    @Override
    public int getCantidadEntregasEnviadas() throws Exception {
        return _movimientoDao.getCantidadEnviadosPorTabla(EntregaRendicion.TABLE);
    }

    public int getCantidadPedidosAnuladosEnviados() throws Exception {
        return _movimientoDao.getCantidadPedidosAnuladosEnviados();
    }

    public int getCantidadClientesEnviados() throws Exception {
        //Por Tarea #3200
        return _movimientoDao.getCantidadEnviadosPorTabla(Cliente.TABLE);
    }

    public int getCantidadRecibosEnviados() throws Exception {
        return _movimientoDao.getCantidadEnviadosPorTabla(Recibo.TABLE);
    }

    public int getCantidadUbicacionesGeograficasEnviadas() throws Exception {
        //Por Tarea #3200
        return _movimientoDao.getCantidadEnviadosPorTabla(UbicacionGeografica.TABLE);
    }

    /*Sin Enviar*/
    public int getCantidadAuditoriasGpsSinEnviar() throws Exception {
        return _movimientoDao.getCantidadPorTablaSinEnviar(AuditoriaGps.TABLE);
    }

    public int getCantidadPedidosSinEnviar() throws Exception {
        return _movimientoDao.getCantidadPorTablaSinEnviar(Venta.TABLE);
    }

    public int getCantidadNoPedidosSinEnviar() throws Exception {
        return _movimientoDao.getCantidadPorTablaSinEnviar(NoPedido.TABLE);
    }

    public int getCantidadHojasDetalleSinEnviar() throws Exception {
        return _movimientoDao.getCantidadPorTablaSinEnviar(HojaDetalle.TABLE);
    }

    @Override
    public int getCantidadEgresosSinEnviar() throws Exception {
        return _movimientoDao.getCantidadPorTablaSinEnviar(Compra.TABLE);
    }

    @Override
    public int getCantidadEntregasSinEnviar() throws Exception {
        return _movimientoDao.getCantidadPorTablaSinEnviar(EntregaRendicion.TABLE);
    }

    public int getCantidadPedidosAnuladosSinEnviar() throws Exception {
        return _movimientoDao.getCantidadPedidosAnuladosSinEnviar();
    }

    @Override
    public int getCantidadErroresSinEnviar() throws Exception {
        return _movimientoDao.getCantidadPorTablaSinEnviar(ErrorMovil.TABLE);
    }

    public int getCantidadRecibosSinEnviar() throws Exception {
        return _movimientoDao.getCantidadPorTablaSinEnviar(Recibo.TABLE);
    }

    public int getCantidadClientesSinEnviar() throws Exception {
        return _movimientoDao.getCantidadPorTablaSinEnviar(Cliente.TABLE);
    }

    public int getCantidadUbicacionesGeograficasSinEnviar() throws Exception {
        //Por Tarea #3200
        return _movimientoDao.getCantidadUbicacionGeograficaSinEnviar();
    }

    public void updateFechaSincronizacion(Object object) throws Exception {
        String idMovil = null;
        if (object instanceof Venta)
            idMovil = ((Venta) object).getIdMovil();
        else if (object instanceof NoPedido)
            idMovil = ((NoPedido) object).getIdMovil();
        else if (object instanceof Cliente)
            idMovil = ((Cliente) object).getIdMovil();
        else if (object instanceof Recibo)
            idMovil = ((Recibo) object).getIdMovil();
        else if (object instanceof AuditoriaGps)
            idMovil = ((AuditoriaGps) object).getIdMovil();
        else if (object instanceof HojaDetalle)
            idMovil = ((HojaDetalle) object).getIdMovil();
        else if (object instanceof EntregaRendicion)
            idMovil = ((EntregaRendicion) object).getIdMovil();
        else if (object instanceof Compra)
            idMovil = ((Compra) object).getIdMovil();
        else if (object instanceof ErrorMovil)
            idMovil = ((ErrorMovil) object).getIdMovil();

        String fechaSincronizacion = Formatter.formatDateTimeToString(Calendar.getInstance().getTime());
        _movimientoDao.updateFechaSincronizacion(idMovil, fechaSincronizacion);
    }

    public void updateFechasSincronizacion(List<HojaDetalle> objects) throws Exception {
        for (Object object : objects) {
            String idMovil = null;
            if (object instanceof Venta)
                idMovil = ((Venta) object).getIdMovil();
            else if (object instanceof NoPedido)
                idMovil = ((NoPedido) object).getIdMovil();
            else if (object instanceof Cliente)
                idMovil = ((Cliente) object).getIdMovil();
            else if (object instanceof Recibo)
                idMovil = ((Recibo) object).getIdMovil();
            else if (object instanceof AuditoriaGps)
                idMovil = ((AuditoriaGps) object).getIdMovil();
            else if (object instanceof HojaDetalle)
                idMovil = ((HojaDetalle) object).getIdMovil();
            else if (object instanceof EntregaRendicion)
                idMovil = ((EntregaRendicion) object).getIdMovil();
            else if (object instanceof Compra)
                idMovil = ((Compra) object).getIdMovil();
            else if (object instanceof ErrorMovil)
                idMovil = ((ErrorMovil) object).getIdMovil();

            String fechaSincronizacion = Formatter.formatDateTimeToString(Calendar.getInstance().getTime());
            _movimientoDao.updateFechaSincronizacion(idMovil, fechaSincronizacion);
        }
    }

    public void updateFechaAnulacion(Object object) throws Exception {
        String idMovil = null;
        if (object instanceof Venta)
            idMovil = ((Venta) object).getIdMovil();
        else if (object instanceof NoPedido)
            idMovil = ((NoPedido) object).getIdMovil();
        else if (object instanceof Cliente)
            idMovil = ((Cliente) object).getIdMovil();
        else if (object instanceof Recibo)
            idMovil = ((Recibo) object).getIdMovil();
        else if (object instanceof Compra)
            idMovil = ((Compra) object).getIdMovil();

        String fechaAnulacion = Formatter.formatDateTimeToString(Calendar.getInstance().getTime());
        _movimientoDao.updateFechaAnulacion(idMovil, fechaAnulacion);
    }

    public void updateFechaSincronizacionReenvio() throws Exception {
        try {
            _movimientoDao.updateFechaSincronizacionReenvio(Venta.TABLE);
        } catch (Exception e) {
            throw new Exception("Error #2086");
        }
    }

    public void updateIsModificado(Movimiento movimiento) throws Exception {
        try {
            _movimientoDao.update(mappingToDb(movimiento));
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void updateIdMovil(Movimiento movimiento) throws Exception {
        try {
            _movimientoDao.update(mappingToDb(movimiento));
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public boolean isDatosPendientesEnvio() throws Exception {
        if (_movimientoDao != null) {
            int cantidad = _movimientoDao.cantDatosPendientesEnvio();
            return cantidad > 0;
        } else return false;
    }

    public Movimiento getMovimiento(String idMovil) throws Exception {
        return mappingToDto(_movimientoDao.recoveryByIdMovil(idMovil));
    }

    @Override
    public int getCantidadPedidosAnulados() throws Exception {
        //Por Tarea #3200
        return _movimientoDao.getCantidadPedidosAnulados();
    }

    @Override
    public List<Movimiento> getMovimientosPendientes() throws Exception {
        List<MovimientoBd> listadoMovimientos = _movimientoDao.getMovimientosPendientes();
        List<Movimiento> movimientos = new ArrayList<>();
        if (!listadoMovimientos.isEmpty()) {
            for (MovimientoBd item : listadoMovimientos) {
                movimientos.add(mappingToDto(item));
            }
        }
        return movimientos;
    }

    @Override
    public void updateFechaSincronizacion() throws Exception {
        String fechaSincronizacion = Formatter.formatDateTimeToString(Calendar.getInstance().getTime());
        _movimientoDao.updateFechaSincronizacion(fechaSincronizacion);
    }


    /* Estos metodos se pasan a la interfaz correspondiente int getCantidadPedidos(int idSucursal, int idCliente, int idComercio) throws Exception {
     int getCantidadNoAtenciones(int idSucursal, int idCliente, int idComercio) throws Exception {
    }*/

    @Override
    public Movimiento mappingToDto(MovimientoBd movimientoBd) throws ParseException {
        Movimiento movimiento = new Movimiento();
        if (movimientoBd != null) {
            movimiento.setId(movimientoBd.getId());
            movimiento.setTipo(movimientoBd.getTipo());
            movimiento.setTabla(movimientoBd.getTabla());
            movimiento.setIdMovil(movimientoBd.getIdMovil());
            movimiento.setFechaSincronizacion(movimientoBd.getFechaSincronizacion() == null
                    ? null
                    : Formatter.convertToDateWs(movimientoBd.getFechaSincronizacion()));
            movimiento.setFechaMovimiento(movimientoBd.getFechaMovimiento() == null
                    ? null
                    : Formatter.convertToDateTimeTwo(movimientoBd.getFechaMovimiento()));
            movimiento.setFechaAnulacion(movimientoBd.getFechaAnulacion() == null
                    ? null
                    : Formatter.convertToDateTimeTwo(movimientoBd.getFechaAnulacion()));
            movimiento.setIsModificado(movimientoBd.getSnModificado() != null &&
                    movimientoBd.getSnModificado().equalsIgnoreCase("S"));
            movimiento.setIdSucursal(movimientoBd.getIdSucursal());
            movimiento.setIdCliente(movimientoBd.getIdCliente());
            movimiento.setIdComercio(movimientoBd.getIdComercio());
        }
        return movimiento;
    }

    public MovimientoBd mappingToDb(Movimiento movimiento) throws ParseException {
        MovimientoBd movimientoBd = new MovimientoBd();

        movimientoBd.setId(movimiento.getId());
        movimientoBd.setTipo(movimiento.getTipo());
        movimientoBd.setTabla(movimiento.getTabla());
        movimientoBd.setIdMovil(movimiento.getIdMovil());
        movimientoBd.setFechaSincronizacion(movimiento.getFechaSincronizacion() == null
                ? null
                : Formatter.formatDateTimeToString(movimiento.getFechaSincronizacion()));
        movimientoBd.setFechaMovimiento(movimiento.getFechaMovimiento() == null
                ? null
                : Formatter.formatDateTimeToString(movimiento.getFechaMovimiento()));
        movimientoBd.setFechaAnulacion(movimiento.getFechaAnulacion() == null
                ? null
                : Formatter.formatDateTimeToString(movimiento.getFechaAnulacion()));
        movimientoBd.setSnModificado(movimiento.getIsModificado() ? "S" : null);
        movimientoBd.setIdSucursal(movimiento.getIdSucursal());
        movimientoBd.setIdCliente(movimiento.getIdCliente());
        movimientoBd.setIdComercio(movimiento.getIdComercio());
        if (movimiento.getLocation() != null) {
            movimientoBd.setNuLatitudMovimiento(movimiento.getLocation().getLatitude());
            movimientoBd.setNuLongitudMovimiento(movimiento.getLocation().getLongitude());
        }

        return movimientoBd;
    }
}
