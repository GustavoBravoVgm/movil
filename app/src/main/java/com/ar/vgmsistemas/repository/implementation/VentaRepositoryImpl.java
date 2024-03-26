package com.ar.vgmsistemas.repository.implementation;

import android.location.Location;

import com.ar.vgmsistemas.bo.VentaBo;
import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IVentaDao;
import com.ar.vgmsistemas.database.dao.entity.CodigoAutorizacionCobranzaBd;
import com.ar.vgmsistemas.database.dao.entity.MovimientoBd;
import com.ar.vgmsistemas.database.dao.entity.VentaBd;
import com.ar.vgmsistemas.database.dao.entity.VentaDetalleBd;
import com.ar.vgmsistemas.database.dao.entity.key.PkVentaBd;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.CodigoAutorizacionCobranza;
import com.ar.vgmsistemas.entity.Movimiento;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.entity.key.PkCodigoAutCobranza;
import com.ar.vgmsistemas.entity.key.PkVenta;
import com.ar.vgmsistemas.entity.key.PkVentaDetalle;
import com.ar.vgmsistemas.helper.TipoOperacion;
import com.ar.vgmsistemas.repository.IVentaRepository;
import com.ar.vgmsistemas.utils.Formatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class VentaRepositoryImpl implements IVentaRepository {
    private final AppDataBase _db;

    private IVentaDao _ventaDao;

    public VentaRepositoryImpl(AppDataBase db) {
        this._db = db;
        if (db != null) {
            this._ventaDao = db.ventaDao();
        }
    }

    public PkVenta create(Venta venta) throws Exception {
        venta.setAnulo("N");
        _ventaDao.create(mappingToDb(venta));
        return venta.getId();
    }

    public List<Venta> recoveryByRepartidor(int idRepartidor) throws Exception {
        List<VentaBd> listadoVentas = _ventaDao.recoveryByRepartidor(idRepartidor);
        List<Venta> ventas = new ArrayList<>();
        if (!listadoVentas.isEmpty()) {
            for (VentaBd item : listadoVentas) {
                ventas.add(mappingToDto(item));
            }
        }
        return ventas;
    }

    public Venta getCredito(PkVenta id) throws Exception {
        return mappingToDto(_ventaDao.getCredito(id.getIdDocumento(), id.getIdLetra(), id.getPuntoVenta(),
                id.getIdNumeroDocumento()));
    }

    public List<Venta> recoveryByDocumento(String tipoDocumento, int estado) throws Exception {
        List<VentaBd> listadoVentas = _ventaDao.recoveryByDocumento(tipoDocumento, estado);
        List<Venta> ventas = new ArrayList<>();
        if (!listadoVentas.isEmpty()) {
            for (VentaBd item : listadoVentas) {
                ventas.add(mappingToDto(item));
            }
        }
        return ventas;
    }

    public List<Venta> recoveryVentas(Date fechaDesde, Date fechaHasta, int idVendedor)
            throws Exception {
        List<VentaBd> listadoVentas = _ventaDao.recoveryVentas(Formatter.formatJulianDate(fechaDesde),
                Formatter.formatJulianDate(fechaHasta), idVendedor);
        List<Venta> ventas = new ArrayList<>();
        if (!listadoVentas.isEmpty()) {
            for (VentaBd item : listadoVentas) {
                ventas.add(mappingToDto(item));
            }
        }
        return ventas;
    }

    public List<Venta> recoveryByPeriodo(Date fechaDesde, Date fechaHasta, int idVendedor) throws Exception {
        List<VentaBd> listadoVentas = _ventaDao.recoveryByPeriodo(Formatter.formatJulianDate(fechaDesde),
                Formatter.formatJulianDate(fechaHasta), idVendedor);
        List<Venta> ventas = new ArrayList<>();
        if (!listadoVentas.isEmpty()) {
            for (VentaBd item : listadoVentas) {
                ventas.add(mappingToDto(item));
            }
        }
        return ventas;
    }

    public List<Venta> recoveryByCliente(Cliente cliente, String tipoDocumento) throws Exception {
        List<VentaBd> listadoVentas = _ventaDao.recoveryByCliente(cliente.getId().getIdSucursal(), cliente.getId().getIdCliente(),
                cliente.getId().getIdComercio(), tipoDocumento);
        List<Venta> ventas = new ArrayList<>();
        if (!listadoVentas.isEmpty()) {
            for (VentaBd item : listadoVentas) {
                ventas.add(mappingToDto(item));
            }
        }
        return ventas;
    }

    public Venta recoveryByID(PkVenta id) throws Exception {
        return mappingToDto(_ventaDao.recoveryByID(id.getIdDocumento(), id.getIdLetra(), id.getPuntoVenta(),
                id.getIdNumeroDocumento()));
    }

    public void delete(Venta venta) throws Exception {
        _ventaDao.delete(venta.getId().getIdDocumento(), venta.getId().getIdLetra(),
                venta.getId().getPuntoVenta(), venta.getId().getIdNumeroDocumento());
    }

    public void updateByIdMovil(Venta venta) throws Exception {
        _ventaDao.updateByIdMovil(venta.getIdMovil(),
                venta.getCondicionVenta().getId(),
                venta.getSubtotal(),
                venta.getTotalRenta(),
                venta.getTotalIva21(),
                venta.getTotalIva105(),
                venta.getTotal(),
                venta.getTasaDescuento(),
                venta.getPrecioBonificacion(),
                venta.getTasaDescuentoCondicionVenta(),
                venta.getPrecioBonificacionCondicionVenta(),
                venta.getCliente().getCondicionRenta().getTasaDgr(),
                Formatter.formatDateWs(venta.getFechaRegistro()),
                venta.getTotalExento(),
                venta.getTasaDirsc(),
                venta.getTotalDirsc(),
                venta.getTotalImpuestoInterno(),
                venta.getTotalIvaNoCategorizado(),
                venta.getTotalPorArticulo(),
                Formatter.formatJulianDate(venta.getFechaVenta()));
    }

    public void update(Venta venta) throws Exception {
        _ventaDao.update(mappingToDb(venta));
    }

    public void updateFechaSincronizacion(Venta venta) throws Exception {
        venta.setFechaSincronizacion(Calendar.getInstance().getTime());
        _ventaDao.update(mappingToDb(venta));
    }

    public int getCantidadVentasNoEnviadas(boolean isPosterior) throws Exception {
        return _ventaDao.getCantidadVentasNoEnviadas(isPosterior ? "S" : "N");
    }

    public int getCantidadVentas(Cliente cliente) throws Exception {
        return _ventaDao.getCantidadVentas(cliente.getId().getIdSucursal(),
                cliente.getId().getIdCliente(), cliente.getId().getIdComercio());
    }

    public boolean isEnviado(Venta venta) throws Exception {
        return _ventaDao.isEnviado(venta.getIdMovil()) <= 0;
    }

    public boolean isGenerado(Venta venta) throws Exception {
        return _ventaDao.isGenerado(venta.getIdMovil()) > 0;
    }

    public void delete(PkVenta id) throws Exception {
        VentaBd miVenta = _ventaDao.recoveryByID(id.getIdDocumento(), id.getIdLetra(), id.getPuntoVenta(),
                id.getIdNumeroDocumento());
        _ventaDao.delete(miVenta);
    }

    public List<Venta> recoveryAll() throws Exception {
        return null;
    }

    @Override
    public int getCantidadVentasPosteriores() throws Exception {
        return _ventaDao.getCantidadVentasPosteriores();
    }

    @Override
    public void setGenerado(String idDocumento, String idLetra, int puntoVenta, long idNumeroDocumento) throws Exception {
        _ventaDao.setGenerado(idDocumento, idLetra, puntoVenta, idNumeroDocumento);
    }

    public Venta mappingToDto(VentaBd ventaBd) throws Exception {
        Venta venta = new Venta();
        if (ventaBd != null) {
            PkVenta id = new PkVenta(ventaBd.getId().getIdDocumento(), ventaBd.getId().getIdLetra(),
                    ventaBd.getId().getPuntoVenta(), ventaBd.getId().getIdNumeroDocumento());

            venta.setId(id);
            //cargo condicion venta
            CondicionVentaRepositoryImpl condicionVentaRepository = new CondicionVentaRepositoryImpl(this._db);
            venta.setCondicionVenta(condicionVentaRepository.mappingToDto(this._db.condicionVentaDao().recoveryByID(ventaBd.getIdCondicionVenta())));
            //cargo repartidor
            RepartidorRepositoryImpl repartidorRepository = new RepartidorRepositoryImpl(this._db);
            venta.setRepartidor(repartidorRepository.mappingToDto(this._db.repartidorDao().recoveryByID(ventaBd.getIdRepartidor())));
            //cargo cliente
            ClienteRepositoryImpl clienteRepository = new ClienteRepositoryImpl(this._db);
            venta.setCliente(clienteRepository.mappingToDto(this._db.clienteDao().recoveryByID(ventaBd.getIdSucursal(),
                    ventaBd.getIdCliente(), ventaBd.getIdComercio())));
            //cargo documento
            DocumentoRepositoryImpl documentoRepository = new DocumentoRepositoryImpl(this._db);
            venta.setDocumento(documentoRepository.mappingToDto(this._db.documentoDao().recoveryByID(ventaBd.getId().getIdDocumento(),
                    ventaBd.getId().getIdLetra(), ventaBd.getId().getPuntoVenta())));
            venta.setSubtotal(ventaBd.getSubtotal());
            venta.setTasaRenta(ventaBd.getTasaRenta());
            venta.setTotalRenta(ventaBd.getTotalRenta());
            venta.setTotalIva21(ventaBd.getTotalIva21());
            venta.setTotalIva105(ventaBd.getTotalIva105());
            venta.setTotal(ventaBd.getTotal());
            venta.setTasaDescuento(ventaBd.getTasaDescuento());
            venta.setPrecioBonificacion(ventaBd.getPrecioBonificacion());
            venta.setTasaDescuentoCondicionVenta(ventaBd.getTasaDescuentoCondicionVenta());
            venta.setPrecioBonificacionCondicionVenta(ventaBd.getPrecioBonificacionCondicionVenta());
            venta.setFechaRegistro(ventaBd.getFechaRegistro() == null
                    ? null
                    : Formatter.convertToDateTimeTwo(ventaBd.getFechaRegistro()));
            venta.setTotalExento(ventaBd.getTotalExento());
            //cargo vendedor
            VendedorRepositoryImpl vendedorRepository = new VendedorRepositoryImpl(this._db);
            venta.setVendedor(vendedorRepository.mappingToDto(this._db.vendedorDao().recoveryByID(ventaBd.getIdVendedor())));
            venta.setTasaDirsc(ventaBd.getTasaDirsc());
            venta.setTotalDirsc(ventaBd.getTotalDirsc());
            venta.setTotalImpuestoInterno(ventaBd.getTotalImpuestoInterno());
            venta.setFechaSincronizacion(ventaBd.getFechaSincronizacion() == null
                    ? null
                    : Formatter.convertToDate(ventaBd.getFechaSincronizacion()));
            venta.setIdMovil(ventaBd.getIdMovil());
            venta.setFechaVenta(ventaBd.getFechaVenta() == null
                    ? null
                    : Formatter.convertToDate(ventaBd.getFechaVenta()));
            venta.setPie(ventaBd.getPie());
            venta.setCodigoAutorizacion(ventaBd.getCodigoAutorizacion());
            venta.setAnulo(ventaBd.getAnulo() == null
                    ? "N"
                    : ventaBd.getAnulo());
            venta.setCodigoAutorizacionAccionComercial(ventaBd.getCodigoAutorizacionAccionComercial());
            venta.setTotalIvaNoCategorizado(ventaBd.getTotalIvaNoCategorizado());
            venta.setFechaEntrega(ventaBd.getFechaEntrega() == null
                    ? null
                    : Formatter.convertToDate(ventaBd.getFechaEntrega()));
            venta.setTotalPorArticulo(ventaBd.getTotalPorArticulo());
            venta.setIdPedidoDoc(ventaBd.getIdPedidoDoc());
            venta.setIdPedidoTipoAb(ventaBd.getIdPedidoTipoAb());
            venta.setIdPedidoPtoVta(ventaBd.getIdPedidoPtoVta());
            venta.setIdPedidoNum(ventaBd.getIdPedidoNum());
            venta.setTiTurno(ventaBd.getTiTurno());
            venta.setIdHojaIntegrado(ventaBd.getIdHojaIntegrado());
            venta.setDeCliente(ventaBd.getDeCliente());
            //cargo motivo autorizacion
            MotivoAutorizacionRepositoryImpl motivoAutorizacionRepository = new MotivoAutorizacionRepositoryImpl(this._db);
            venta.setMotivoAutorizacion(motivoAutorizacionRepository.mappingToDto(this._db.motivoAutorizacionDao().recoveryByID(ventaBd.getIdMotivoAutoriza())));
            venta.setIdNumDocFiscal(ventaBd.getIdNumDocFiscal());
            venta.setSnGenerado(ventaBd.getSnGenerado());
            venta.setIdFcFcnc(ventaBd.getIdFcFcnc());
            venta.setIdFcTipoab(ventaBd.getIdFcTipoab());
            venta.setIdFcPtovta(ventaBd.getIdFcPtovta());
            venta.setIdFcNumdoc(ventaBd.getIdFcNumdoc());
            venta.setIdMotivoAutorizaPedr(ventaBd.getIdMotivoAutorizaPedr());
            venta.setIsMaxAccomSuperado(ventaBd.getIsMaxAccomSuperado());
            venta.setIdMotivoRechazoNC(ventaBd.getIdMotivoRechazoNC());
            venta.setTiNC(ventaBd.getTiNC());

        }
        return venta;
    }

    public VentaBd mappingToDb(Venta venta) throws Exception {
        if (venta != null) {
            PkVentaBd id = new PkVentaBd();

            id.setIdDocumento(venta.getId().getIdDocumento());
            id.setIdLetra(venta.getId().getIdLetra());
            id.setPuntoVenta(venta.getId().getPuntoVenta());
            id.setIdNumeroDocumento(venta.getId().getIdNumeroDocumento());

            VentaBd ventaBd = new VentaBd();

            ventaBd.setId(id);
            ventaBd.setIdCondicionVenta(venta.getCondicionVenta().getId());
            ventaBd.setIdRepartidor(venta.getRepartidor().getId());
            ventaBd.setIdSucursal(venta.getCliente().getId().getIdSucursal());
            ventaBd.setIdCliente(venta.getCliente().getId().getIdCliente());
            ventaBd.setIdComercio(venta.getCliente().getId().getIdComercio());
            ventaBd.setSubtotal(venta.getSubtotal());
            ventaBd.setTasaRenta(venta.getTasaRenta());
            ventaBd.setTotalRenta(venta.getTotalRenta());
            ventaBd.setTotalIva21(venta.getTotalIva21());
            ventaBd.setTotalIva105(venta.getTotalIva105());
            ventaBd.setTotal(venta.getTotal());
            ventaBd.setTasaDescuento(venta.getTasaDescuento());
            ventaBd.setPrecioBonificacion(venta.getPrecioBonificacion());
            ventaBd.setTasaDescuentoCondicionVenta(venta.getTasaDescuentoCondicionVenta());
            ventaBd.setPrecioBonificacionCondicionVenta(venta.getPrecioBonificacionCondicionVenta());
            ventaBd.setFechaRegistro(venta.getFechaRegistro() == null
                    ? null
                    : Formatter.formatDateTimeToString(venta.getFechaRegistro()));
            ventaBd.setTotalExento(venta.getTotalExento());
            ventaBd.setIdVendedor(venta.getVendedor().getIdVendedor());
            ventaBd.setTasaDirsc(venta.getTasaDirsc());
            ventaBd.setTotalDirsc(venta.getTotalDirsc());
            ventaBd.setTotalImpuestoInterno(venta.getTotalImpuestoInterno());
            ventaBd.setFechaSincronizacion(venta.getFechaSincronizacion() == null
                    ? null
                    : Formatter.formatJulianDate(venta.getFechaSincronizacion()));
            ventaBd.setIdMovil(venta.getIdMovil());
            ventaBd.setFechaVenta(venta.getFechaVenta() == null
                    ? null
                    : Formatter.formatJulianDate(venta.getFechaVenta()));
            ventaBd.setPie(venta.getPie());
            ventaBd.setCodigoAutorizacion(venta.getCodigoAutorizacion());
            ventaBd.setAnulo(venta.getAnulo() == null
                    ? "N"
                    : venta.getAnulo());
            ventaBd.setCodigoAutorizacionAccionComercial(venta.getCodigoAutorizacionAccionComercial());
            ventaBd.setTotalIvaNoCategorizado(venta.getTotalIvaNoCategorizado());
            ventaBd.setFechaEntrega(venta.getFechaEntrega() == null
                    ? null
                    : Formatter.formatJulianDate(venta.getFechaEntrega()));
            ventaBd.setTotalPorArticulo(venta.getTotalPorArticulo());
            ventaBd.setIdPedidoDoc(venta.getIdPedidoDoc());
            ventaBd.setIdPedidoTipoAb(venta.getIdPedidoTipoAb());
            ventaBd.setIdPedidoPtoVta(venta.getIdPedidoPtoVta());
            ventaBd.setIdPedidoNum(venta.getIdPedidoNum());
            ventaBd.setTiTurno(venta.getTiTurno());
            ventaBd.setIdHojaIntegrado(venta.getIdHojaIntegrado());
            ventaBd.setDeCliente(venta.getCliente().getRazonSocial());
            ventaBd.setIdMotivoAutoriza(venta.getMotivoAutorizacion() == null
                    ? null
                    : venta.getMotivoAutorizacion().getId());
            ventaBd.setIdNumDocFiscal(venta.getIdNumDocFiscal());
            ventaBd.setSnGenerado(venta.getSnGenerado());
            ventaBd.setIdFcFcnc(venta.getIdFcFcnc());
            ventaBd.setIdFcTipoab(venta.getIdFcTipoab());
            ventaBd.setIdFcPtovta(venta.getIdFcPtovta());
            ventaBd.setIdFcNumdoc(venta.getIdFcNumdoc());
            ventaBd.setIdMotivoAutorizaPedr(venta.getIdMotivoAutorizaPedr());
            ventaBd.setIsMaxAccomSuperado(venta.getIsMaxAccomSuperado());
            ventaBd.setIdMotivoRechazoNC(venta.getIdMotivoRechazoNC());
            ventaBd.setTiNC(venta.getTiNC());

            return ventaBd;
        }
        return null;
    }

    public boolean createVentaTransaction(Venta venta, Location location) throws Exception {
        //creo la venta
        venta.setAnulo("N");
        VentaRepositoryImpl ventaRepository = new VentaRepositoryImpl(this._db);
        VentaBd ventaBd = ventaRepository.mappingToDb(venta);
        //Actualizo numero de documento: se hace en el metodo de AppDatabase.
        //agrego venta detalle
        Iterator<VentaDetalle> iterator = venta.getDetalles().iterator();
        int secuencia = 1;
        VentaDetalleRepositoryImpl ventaDetalleRepository = new VentaDetalleRepositoryImpl(this._db);
        List<VentaDetalleBd> ventaDetalleBds = new ArrayList<>();
        while (iterator.hasNext()) {
            VentaDetalle ventaDetalle = iterator.next();
            ventaDetalle.setAnulo("N");
            ventaDetalleBds.add(createVentaDetalleBd(venta, ventaDetalle, secuencia, ventaDetalleRepository));
            secuencia++;
            for (VentaDetalle detalle : ventaDetalle.getDetalleCombo()) {
                detalle.setIdCombo(ventaDetalle.getId().toString());
                detalle.setAnulo("N");
                ventaDetalleBds.add(createVentaDetalleBd(venta, detalle, secuencia, ventaDetalleRepository));
                secuencia++;
            }
        }

        //Registro el movimiento con location y cliente del movimiento
        Movimiento movimiento = new Movimiento();
        movimiento.setTabla(Venta.TABLE);
        movimiento.setIdMovil(venta.getIdMovil());
        movimiento.setTipo(Movimiento.ALTA);
        //Location
        if (location != null) {
            movimiento.setLocation(location);
        }
        //Setear el cliente
        movimiento.setIdSucursal(venta.getCliente().getId().getIdSucursal());
        movimiento.setIdCliente(venta.getCliente().getId().getIdCliente());
        movimiento.setIdComercio(venta.getCliente().getId().getIdComercio());
        Date fechaMovimiento = Calendar.getInstance().getTime();
        movimiento.setFechaMovimiento(fechaMovimiento);
        MovimientoRepositoryImpl movimientoRepository = new MovimientoRepositoryImpl(this._db);
        MovimientoBd movimientoBd = movimientoRepository.mappingToDb(movimiento);

        // Registro el codigo de autorizacion para que no se vuelva a utilizar
        CodigoAutorizacionCobranzaBd codigoAutorizacionCobranzaBd = null;
        if (venta.getCodigoAutorizacion() != null) {
            CodigoAutorizacionCobranza codigo = new CodigoAutorizacionCobranza();
            PkCodigoAutCobranza id = new PkCodigoAutCobranza();
            id.setIdDocumento("CA");
            id.setIdLetra(venta.getId().getIdLetra());
            id.setPuntoVenta(venta.getId().getPuntoVenta());
            id.setIdNumeroDocumento(venta.getId().getIdNumeroDocumento());
            codigo.setId(id);
            codigo.setCodigo(venta.getCodigoAutorizacion());
            CodigoAutorizacionCobranzaRepositoryImpl codigoAutorizacionCobranzaRepository =
                    new CodigoAutorizacionCobranzaRepositoryImpl(this._db);
            codigoAutorizacionCobranzaBd = codigoAutorizacionCobranzaRepository.mappingToDb(codigo);
        }

        return this._db.createVentaTransaction(ventaBd, ventaDetalleBds, movimientoBd, codigoAutorizacionCobranzaBd);
    }

    private VentaDetalleBd createVentaDetalleBd(Venta venta, VentaDetalle ventaDetalle, int secuencia,
                                                VentaDetalleRepositoryImpl ventaDetalleRepository) throws Exception {
        PkVentaDetalle id = new PkVentaDetalle();
        id.setSecuencia(secuencia);
        id.setIdDocumento(venta.getId().getIdDocumento());
        id.setIdLetra(venta.getId().getIdLetra());
        id.setIdNumeroDocumento(venta.getId().getIdNumeroDocumento());
        id.setPuntoVenta(venta.getId().getPuntoVenta());
        ventaDetalle.setIdMovil(venta.getIdMovil());
        ventaDetalle.setId(id);
        ventaDetalle.setTipoOperacion(TipoOperacion.insert);

        return ventaDetalleRepository.mappingToDb(ventaDetalle);
    }

    public void updateVentaTransaction(Venta venta, Location location, String idMovilOriginal) throws Exception {
        ////Actualizo el movimiento de alta registrado anteriormente
        MovimientoRepositoryImpl movimientoRepository = new MovimientoRepositoryImpl(this._db);
        Movimiento movimientoOriginal = movimientoRepository.getMovimiento(idMovilOriginal);
        movimientoOriginal.setIsModificado(true);
        MovimientoBd movimientoBdOriginal = movimientoRepository.mappingToDb(movimientoOriginal);

        ////actualizo la cabecera de venta
        VentaRepositoryImpl ventaRepository = new VentaRepositoryImpl(this._db);
        VentaBd ventaBd = ventaRepository.mappingToDb(venta);

        /*tratamiento de ventas detalle guardadas en bd*****/
        // Recupero de la base de datos los detalles originales del pedidos para actualizar el stock
        // ya que los detalles de venta tienen las modificaciones
        VentaDetalleRepositoryImpl ventaDetalleRepository = new VentaDetalleRepositoryImpl(this._db);
        List<VentaDetalle> ventasDetalle = ventaDetalleRepository.recoveryByVenta(venta);
        List<VentaDetalleBd> ventaDetalleBdsActualizar = new ArrayList<>();
        for (int i = 0; i < ventasDetalle.size(); i++) {
            VentaDetalle ventaDetalle = ventasDetalle.get(i);
            ventaDetalle.setTipoOperacion(TipoOperacion.delete);
            ventaDetalleBdsActualizar.add(ventaDetalleRepository.mappingToDb(ventaDetalle));
        }

        /*tratamiento de ventas detalle que se van a guardar en bd*****/
        //agrego venta detalle nuevas
        Iterator<VentaDetalle> iterator = venta.getDetalles().iterator();
        int secuencia = 1;
        List<VentaDetalleBd> ventaDetalleBdsNuevo = new ArrayList<>();
        while (iterator.hasNext()) {
            VentaDetalle ventaDetalle = iterator.next();
            ventaDetalle.setAnulo("N");
            ventaDetalleBdsNuevo.add(createVentaDetalleBd(venta, ventaDetalle, secuencia, ventaDetalleRepository));
            secuencia++;
            for (VentaDetalle detalle : ventaDetalle.getDetalleCombo()) {
                detalle.setIdCombo(ventaDetalle.getId().toString());
                detalle.setAnulo("N");
                ventaDetalleBdsNuevo.add(createVentaDetalleBd(venta, detalle, secuencia, ventaDetalleRepository));
                secuencia++;
            }
        }

        ////genero nuevo movimiento por la modificación
        Movimiento movimientoNuevo = new Movimiento();
        movimientoNuevo.setTabla(Venta.TABLE);
        movimientoNuevo.setIdMovil(venta.getIdMovil());
        movimientoNuevo.setTipo(Movimiento.MODIFICACION);
        //Location
        if (location != null) {
            movimientoNuevo.setLocation(location);
        }
        //Setear el cliente
        movimientoNuevo.setIdSucursal(venta.getCliente().getId().getIdSucursal());
        movimientoNuevo.setIdCliente(venta.getCliente().getId().getIdCliente());
        movimientoNuevo.setIdComercio(venta.getCliente().getId().getIdComercio());
        Date fechaMovimiento = Calendar.getInstance().getTime();
        movimientoNuevo.setFechaMovimiento(fechaMovimiento);
        MovimientoBd movimientoBdNuevo = movimientoRepository.mappingToDb(movimientoNuevo);

        this._db.updateVentaTransaction(ventaBd, ventaDetalleBdsActualizar, ventaDetalleBdsNuevo, movimientoBdOriginal, movimientoBdNuevo);
    }

    public boolean anularVentaTransaction(Venta venta, Location location, int modo) throws Exception {
        VentaBd ventaBd = mappingToDb(venta);
        MovimientoBd movimientoBd = null;
        if (modo == VentaBo.ANULAR_VENTA_MODO_ENVIAR) {
            //Registro el movimiento con location y cliente del movimiento
            Movimiento movimiento = new Movimiento();
            movimiento.setTabla(Venta.TABLE);
            movimiento.setIdMovil(venta.getIdMovil());
            movimiento.setTipo(Movimiento.BAJA);
            //Location
            if (location != null) {
                movimiento.setLocation(location);
            }
            //Setear el cliente
            movimiento.setIdSucursal(venta.getCliente().getId().getIdSucursal());
            movimiento.setIdCliente(venta.getCliente().getId().getIdCliente());
            movimiento.setIdComercio(venta.getCliente().getId().getIdComercio());
            Date fechaMovimiento = Calendar.getInstance().getTime();
            movimiento.setFechaMovimiento(fechaMovimiento);
            MovimientoRepositoryImpl movimientoRepository = new MovimientoRepositoryImpl(this._db);
            movimientoBd = movimientoRepository.mappingToDb(movimiento);
        }

        /*tratamiento de ventas detalle guardadas en bd*****/
        // Recupero de la base de datos los detalles originales del pedidos para actualizar el stock
        // ya que los detalles de venta tienen las modificaciones
        VentaDetalleRepositoryImpl ventaDetalleRepository = new VentaDetalleRepositoryImpl(this._db);
        List<VentaDetalle> ventasDetalle = ventaDetalleRepository.recoveryByVenta(venta);
        List<VentaDetalleBd> ventaDetalleBds = new ArrayList<>();
        for (int i = 0; i < ventasDetalle.size(); i++) {
            VentaDetalle ventaDetalle = ventasDetalle.get(i);
            ventaDetalle.setTipoOperacion(TipoOperacion.delete);
            ventaDetalleBds.add(ventaDetalleRepository.mappingToDb(ventaDetalle));
        }

        String fechaAnulacion = Formatter.formatDateTimeToString(Calendar.getInstance().getTime());
        return this._db.anularVentaTransaction(ventaBd, movimientoBd, ventaDetalleBds, fechaAnulacion);
    }
}
