package com.ar.vgmsistemas.repository.implementation;

import android.location.Location;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.INoPedidoDao;
import com.ar.vgmsistemas.database.dao.entity.MovimientoBd;
import com.ar.vgmsistemas.database.dao.entity.NoPedidoBd;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.Movimiento;
import com.ar.vgmsistemas.entity.NoPedido;
import com.ar.vgmsistemas.repository.INoPedidoRepository;
import com.ar.vgmsistemas.utils.Formatter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NoPedidoRepositoryImpl implements INoPedidoRepository {
    private final AppDataBase _db;

    private INoPedidoDao _noPedidoDao;

    public NoPedidoRepositoryImpl(AppDataBase db) {
        this._db = db;
        if (db != null) {
            this._noPedidoDao = db.noPedidoDao();
        }
    }


    public List<NoPedido> recoveryAll() throws Exception {
        List<NoPedidoBd> listadoNoPedidos = _noPedidoDao.recovery();
        List<NoPedido> noPedidos = new ArrayList<>();
        if (!listadoNoPedidos.isEmpty()) {
            for (NoPedidoBd item : listadoNoPedidos) {
                noPedidos.add(mappingToDto(item));
            }
        }
        return noPedidos;
    }

    public List<NoPedido> recoveryPedidosSinEnviar() throws Exception {
        List<NoPedidoBd> listadoNoPedidos = _noPedidoDao.recoveryPedidosSinEnviar();
        List<NoPedido> noPedidos = new ArrayList<>();
        if (!listadoNoPedidos.isEmpty()) {
            for (NoPedidoBd item : listadoNoPedidos) {
                noPedidos.add(mappingToDto(item));
            }
        }
        return noPedidos;
    }

    public Long create(NoPedido noPedido) throws Exception {
        return _noPedidoDao.create(mappingToDb(noPedido));
    }

    public void delete(NoPedido noPedido) throws Exception {
        _noPedidoDao.delete(noPedido.getId());
    }

    public void delete(Long id) throws Exception {

    }

    public NoPedido recoveryByID(Long id) throws Exception {
        return null;
    }

    public void update(NoPedido noPedido) throws Exception {
        _noPedidoDao.update(mappingToDb(noPedido));
    }

    public int getCantidadPedidosSinEnviar() throws Exception {
        return _noPedidoDao.getCantidadPedidosSinEnviar();
    }

    public void updateFechaSincronizacion(NoPedido noPedido) throws Exception {
        noPedido.setFechaSincronizacion(Calendar.getInstance().getTime());
        _noPedidoDao.update(mappingToDb(noPedido));
    }

    public int getCantidadNoPedidos(Cliente cliente) throws Exception {
        return _noPedidoDao.getCantidadNoPedidos(cliente.getId().getIdSucursal(),
                cliente.getId().getIdCliente(), cliente.getId().getIdComercio());
    }

    public NoPedido mappingToDto(NoPedidoBd noPedidoBd) throws Exception {
        NoPedido noPedido = new NoPedido();
        if (noPedidoBd != null) {

            noPedido.setId(noPedidoBd.getId());
            noPedido.setFechaNoPedido(noPedidoBd.getFechaNoPedido() == null
                    ? null
                    : Formatter.convertToDateTimeTwo(noPedidoBd.getFechaNoPedido()));
            noPedido.setObservacion(noPedidoBd.getObservacion());
            //cargo motivo no pedidos
            MotivoNoPedidoRepositoryImpl motivoNoPedidoRepository = new MotivoNoPedidoRepositoryImpl(this._db);
            noPedido.setMotivoNoPedido(motivoNoPedidoRepository.mappingToDto(
                    this._db.motivoNoPedidoDao().recoveryByID(noPedidoBd.getIdMotivo())));
            //cargo cliente
            ClienteRepositoryImpl clienteRepository = new ClienteRepositoryImpl(this._db);
            noPedido.setCliente(clienteRepository.mappingToDto(this._db.clienteDao().recoveryByID(noPedidoBd.getIdSucursal(),
                    noPedidoBd.getIdCliente(), noPedidoBd.getIdComercio())));
            //cargo vendedor
            VendedorRepositoryImpl vendedorRepository = new VendedorRepositoryImpl(this._db);
            noPedido.setVendedor(vendedorRepository.mappingToDto(this._db.vendedorDao().recoveryByID(noPedidoBd.getIdVendedor())));
            noPedido.setFechaSincronizacion(noPedidoBd.getFechaSincronizacion() == null
                    ? null
                    : Formatter.convertToDate(noPedidoBd.getFechaSincronizacion()));
            noPedido.setIdMovil(noPedidoBd.getIdMovil());
            noPedido.setSnAnulo(noPedidoBd.getSnAnulo());
            noPedido.setFechaRegistroMovil(noPedidoBd.getFechaRegistroMovil() == null
                    ? null
                    : Formatter.convertToDateTimeTwo(noPedidoBd.getFechaRegistroMovil()));
        }
        return noPedido;
    }

    private NoPedidoBd mappingToDb(NoPedido noPedido) throws ParseException {
        if (noPedido != null) {
            NoPedidoBd noPedidoBd = new NoPedidoBd();

            //noPedidoBd.setId(noPedido.getId());
            noPedidoBd.setFechaNoPedido(noPedido.getFechaNoPedido() == null
                    ? null
                    : Formatter.formatDateTimeToString(noPedido.getFechaNoPedido()));
            noPedidoBd.setObservacion(noPedido.getObservacion());
            noPedidoBd.setIdMotivo(noPedido.getMotivoNoPedido().getId());
            noPedidoBd.setIdSucursal(noPedido.getCliente().getId().getIdSucursal());
            noPedidoBd.setIdCliente(noPedido.getCliente().getId().getIdCliente());
            noPedidoBd.setIdComercio(noPedido.getCliente().getId().getIdComercio());
            noPedidoBd.setIdVendedor(noPedido.getVendedor().getIdVendedor());
            noPedidoBd.setIdMovil(noPedido.getIdMovil());
            noPedidoBd.setSnAnulo(noPedido.getSnAnulo() == null
                    ? null
                    : noPedido.getSnAnulo());
            noPedidoBd.setFechaSincronizacion(noPedido.getFechaSincronizacion() == null
                    ? null
                    : Formatter.formatJulianDate(noPedido.getFechaSincronizacion()));
            noPedidoBd.setFechaRegistroMovil(noPedido.getFechaRegistroMovil() == null
                    ? null
                    : Formatter.formatDateTimeToString(noPedido.getFechaRegistroMovil()));

            return noPedidoBd;
        }
        return null;
    }

    public void createNoPedidoTransaction(NoPedido noPedido, Location location) throws Exception {

        NoPedidoBd noPedidoBd = mappingToDb(noPedido);
        //Registro el movimiento con location y cliente del movimiento
        Movimiento movimiento = new Movimiento();
        movimiento.setTabla(NoPedido.TABLE);
        movimiento.setIdMovil(noPedido.getIdMovil());
        movimiento.setTipo(Movimiento.ALTA);
        //Location
        if (location != null) {
            movimiento.setLocation(location);
        }
        //Setear el cliente
        movimiento.setIdSucursal(noPedido.getCliente().getId().getIdSucursal());
        movimiento.setIdCliente(noPedido.getCliente().getId().getIdCliente());
        movimiento.setIdComercio(noPedido.getCliente().getId().getIdComercio());
        Date fechaMovimiento = Calendar.getInstance().getTime();
        movimiento.setFechaMovimiento(fechaMovimiento);
        MovimientoRepositoryImpl movimientoRepository = new MovimientoRepositoryImpl(this._db);
        MovimientoBd movimientoBd = movimientoRepository.mappingToDb(movimiento);

        this._db.createNoPedidoTransaction(noPedidoBd, movimientoBd);
    }

}
