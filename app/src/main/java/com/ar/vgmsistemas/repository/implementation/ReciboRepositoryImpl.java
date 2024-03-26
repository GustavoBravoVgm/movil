package com.ar.vgmsistemas.repository.implementation;

import android.location.Location;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IReciboDao;
import com.ar.vgmsistemas.database.dao.entity.ChequeBd;
import com.ar.vgmsistemas.database.dao.entity.ClienteBd;
import com.ar.vgmsistemas.database.dao.entity.CuentaCorrienteBd;
import com.ar.vgmsistemas.database.dao.entity.DepositoBd;
import com.ar.vgmsistemas.database.dao.entity.EntregaBd;
import com.ar.vgmsistemas.database.dao.entity.MovimientoBd;
import com.ar.vgmsistemas.database.dao.entity.PagoEfectivoBd;
import com.ar.vgmsistemas.database.dao.entity.ReciboBd;
import com.ar.vgmsistemas.database.dao.entity.ReciboDetalleBd;
import com.ar.vgmsistemas.database.dao.entity.RetencionBd;
import com.ar.vgmsistemas.database.dao.entity.key.PkReciboBd;
import com.ar.vgmsistemas.entity.Cheque;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.CuentaCorriente;
import com.ar.vgmsistemas.entity.Deposito;
import com.ar.vgmsistemas.entity.Movimiento;
import com.ar.vgmsistemas.entity.PagoEfectivo;
import com.ar.vgmsistemas.entity.Recibo;
import com.ar.vgmsistemas.entity.ReciboDetalle;
import com.ar.vgmsistemas.entity.Retencion;
import com.ar.vgmsistemas.entity.Vendedor;
import com.ar.vgmsistemas.entity.key.PkRecibo;
import com.ar.vgmsistemas.repository.IReciboRepository;
import com.ar.vgmsistemas.utils.Formatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ReciboRepositoryImpl implements IReciboRepository {
    private final AppDataBase _db;

    private IReciboDao _reciboDao;

    public ReciboRepositoryImpl(AppDataBase db) {
        this._db = db;
        if (db != null) {
            this._reciboDao = db.reciboDao();
        }
    }

    public PkRecibo create(Recibo recibo) throws Exception {
        _reciboDao.create(mappingToDb(recibo));
        return recibo.getId();
    }

    public Recibo recoveryByID(PkRecibo id) throws Exception {
        return null;
    }


    public List<Recibo> recoveryAll() throws Exception {
        List<ReciboBd> listadoRecibos = _reciboDao.recoveryAll();
        List<Recibo> recibos = new ArrayList<>();
        if (!listadoRecibos.isEmpty()) {
            for (ReciboBd item : listadoRecibos) {
                recibos.add(mappingToDto(item));
            }
        }
        return recibos;
    }


    public List<Recibo> recoveryDescargados() throws Exception {
        List<ReciboBd> listadoRecibos = _reciboDao.recoveryDescargados();
        if (!listadoRecibos.isEmpty()) {
            List<Recibo> recibos = new ArrayList<>();
            for (ReciboBd item : listadoRecibos) {
                recibos.add(mappingToDto(item));
            }
            return recibos;
        }
        return null;
    }

    public void update(Recibo recibo) throws Exception {
        recibo.setIdEstado(Recibo.ESTADO_IMPUTADO);
        _reciboDao.update(mappingToDb(recibo));
    }

    public void updateEstadoImputado(int idPuntoVenta, long ultimoIdNumeroRecibo) throws Exception {
        _reciboDao.updateEstadoImputado(idPuntoVenta, ultimoIdNumeroRecibo);
    }

    public void delete(Recibo entity) throws Exception {

    }

    public void delete(PkRecibo id) throws Exception {

    }

    public List<Integer> getPuntosVentaRecibo(Vendedor vendedor) throws Exception {
        return _reciboDao.getPuntosVentaRecibo(vendedor.getIdVendedor());
    }

    public int getSiguienteNumeroRecibo(int idPuntoVenta) throws Exception {
        return _reciboDao.getSiguienteNumeroRecibo(idPuntoVenta);
    }

    public boolean validateNumeroRecibo(long numeroRecibo) throws Exception {
        return (_reciboDao.validateNumeroRecibo(numeroRecibo) > 0);
    }


    public List<Recibo> recoveryNoEnviados() throws Exception {
        List<ReciboBd> listadoRecibos = _reciboDao.recoveryNoEnviados();
        List<Recibo> recibos = new ArrayList<>();
        if (!listadoRecibos.isEmpty()) {
            for (ReciboBd item : listadoRecibos) {
                recibos.add(mappingToDto(item));
            }
        }
        return recibos;
    }

    public List<Recibo> recoveryNoRendidos() throws Exception {
        List<Recibo> rr = new ArrayList<>();
        List<ReciboBd> listadoRecibos = _reciboDao.recoveryNoRendidos();
        if (!listadoRecibos.isEmpty()) {
            for (ReciboBd item : listadoRecibos) {
                rr.add(mappingToDto(item));
            }
        }
        List<Recibo> temp = new ArrayList<>();
        for (Recibo r : rr) {
            boolean find = false;
            for (Recibo t : temp) {
                if (t.getId().getIdRecibo() == r.getId().getIdRecibo() && t.getId().getIdPuntoVenta() == r.getId().getIdPuntoVenta()) {
                    find = true;
                    break;
                }
            }
            if (!find) {
                temp.add(r);
            }
        }
        return temp;
    }

    @Override
    public void updateResultEnvio(PkRecibo idRecibo, int resultado) throws Exception {
        _reciboDao.updateResultEnvio(idRecibo.getIdPuntoVenta(), idRecibo.getIdRecibo(), resultado);
    }

    @Override
    public List<Integer> getPuntosVentaRecibo(long sucCliente) {
        //int ptoVta = PreferenciaBo.getInstance().getPreferencia().getIdPuntoVentaPorDefectoRecibo();
        //consulto si existe por lo menos un documento de recibo con ese punto de venta, si es asi devuelvo el mismo ptovta, esto se hace asi para no tener que
        // redefinir toda la consulta
        List<Integer> result = _reciboDao.getPuntosVentaReciboPorSucCliente(sucCliente);
        try {
            if (result != null && result.isEmpty()) {
                return result;
            } else {
                return null;
            }
        } catch (Exception e) {
            //ErrorManager.manageException("ReciboRepositoryImpl", "getPuntosVentaRecibo", e);
        }
        return result;
    }

    @Override
    public Recibo mappingToDto(ReciboBd reciboBd) throws Exception {
        if (reciboBd != null) {
            PkRecibo id = new PkRecibo(reciboBd.getId().getIdPuntoVenta(), reciboBd.getId().getIdRecibo());

            Recibo recibo = new Recibo();
            recibo.setId(id);
            //cargo cliente
            ClienteRepositoryImpl clienteRepository = new ClienteRepositoryImpl(this._db);
            recibo.setCliente(clienteRepository.mappingToDto(this._db.clienteDao().recoveryByID(reciboBd.getIdSucursal(),
                    reciboBd.getIdCliente(), reciboBd.getIdComercio())));
            //cargo vendedor
            VendedorRepositoryImpl vendedorRepository = new VendedorRepositoryImpl(this._db);
            recibo.setVendedor(vendedorRepository.mappingToDto(this._db.vendedorDao().recoveryByID(reciboBd.getIdVendedor())));
            recibo.setFechaMovil(reciboBd.getFechaMovil() == null
                    ? null
                    : Formatter.convertToDate(reciboBd.getFechaMovil()));
            recibo.setTotal(reciboBd.getTotal() == null ? 0d : reciboBd.getTotal());
            recibo.setIdMovil(reciboBd.getIdMovil());
            String idMovil = reciboBd.getIdMovil();
            String fechaRegistro = null;
            if (idMovil != null && reciboBd.getFechaMovil() != null) {
                int desde = idMovil.indexOf(' ') + 1;
                int hasta = idMovil.indexOf('-', desde);
                fechaRegistro = reciboBd.getFechaMovil() + ' ' + idMovil.substring(desde, hasta);
            }
            recibo.setFechaRegistroMovil(reciboBd.getFechaRegistroMovil() == null
                    ? (fechaRegistro == null ? null : Formatter.convertToDateTime(fechaRegistro))
                    : Formatter.convertToDateTime(reciboBd.getFechaRegistroMovil()));
            //cargo entrega
            EntregaRepositoryImpl entregaRepository = new EntregaRepositoryImpl(this._db);
            recibo.setEntrega(entregaRepository.mappingToDto(this._db.entregaDao().recoveryByID(reciboBd.getIdEntrega())));
            recibo.setDeviceId(reciboBd.getDeviceId());
            recibo.setObservacion(reciboBd.getObservacion());
            recibo.setIdEstado(reciboBd.getIdEstado());
            recibo.setResultadoEnvio(reciboBd.getResultadoEnvio());
            return recibo;
        }
        return null;
    }

    @Override
    public void createReciboTransaction(Recibo recibo, Location location, boolean isReciboProvisorio,
                                        List<CuentaCorriente> listadoComprobantesGenerados) throws Exception {
        //Busco entrega a guardar
        EntregaRepositoryImpl entregaRepository = new EntregaRepositoryImpl(this._db);
        EntregaBd entregaBd = entregaRepository.mappingToDb(recibo.getEntrega());

        //Recibo
        ReciboRepositoryImpl reciboRepository = new ReciboRepositoryImpl(this._db);
        ReciboBd reciboBd = reciboRepository.mappingToDb(recibo);

        //Actualizo limite de disponibilidad en el cliente
        Cliente cliente = recibo.getCliente();
        ClienteBd clienteBd = null;
        if (cliente != null) {
            double limiteDisponibilidad = cliente.getLimiteDisponibilidad() + recibo.obtenerTotalPagos();
            cliente.setLimiteDisponibilidad(limiteDisponibilidad);
            ClienteRepositoryImpl clienteRepository = new ClienteRepositoryImpl(this._db);
            clienteBd = clienteRepository.mappingToDb(cliente);
        }

        ////Busco los comprobantes a generar(anticipos)
        CuentaCorrienteRepositoryImpl cuentaCorrienteRepository = new CuentaCorrienteRepositoryImpl(this._db);
        List<CuentaCorrienteBd> comprobantesGeneradosBdList = new ArrayList<>();
        if (listadoComprobantesGenerados.size() > 0) {
            for (CuentaCorriente item : listadoComprobantesGenerados) {
                item.setIdRecPtoVta(recibo.getId().getIdPuntoVenta());
                item.setIdRecibo(recibo.getId().getIdRecibo());
                comprobantesGeneradosBdList.add(cuentaCorrienteRepository.mappingToDb(item));
            }
        }

        ////Busco ReciboDetalle y CtaCte
        List<ReciboDetalleBd> reciboDetalleBdList = new ArrayList<>();
        List<CuentaCorrienteBd> cuentaCorrienteBdList = new ArrayList<>();
        ReciboDetalleRepositoryImpl reciboDetalleRepository = new ReciboDetalleRepositoryImpl(this._db);
        for (ReciboDetalle reciboDetalle : recibo.getDetalles()) {
            if (reciboDetalle.isSeActualizaEnBd()) {
                reciboDetalleBdList.add(reciboDetalleRepository.mappingToDb(reciboDetalle));
                cuentaCorrienteBdList.add(cuentaCorrienteRepository.mappingToDb(reciboDetalle.getCuentaCorriente()));
            }
        }

        ////Busco cheques
        Iterator<Cheque> cheques = recibo.getEntrega().getCheques().iterator();
        ChequeRepositoryImpl chequeRepository = new ChequeRepositoryImpl(this._db);
        List<ChequeBd> chequeBdList = new ArrayList<>();
        while (cheques.hasNext()) {
            Cheque cheque = cheques.next();
            cheque.setEntrega(recibo.getEntrega());
            chequeBdList.add(chequeRepository.mappingToDb(cheque));
        }

        ////Busco PagosEfectivo
        Iterator<PagoEfectivo> pagoEfectivoIterator = recibo.getEntrega().getPagosEfectivo().iterator();
        PagoEfectivoRepositoryImpl pagoEfectivoRepository = new PagoEfectivoRepositoryImpl(this._db);
        List<PagoEfectivoBd> pagoEfectivoBdList = new ArrayList<>();
        while (cheques.hasNext()) {
            PagoEfectivo pagoEfectivo = pagoEfectivoIterator.next();
            pagoEfectivo.setEntrega(recibo.getEntrega());
            pagoEfectivoBdList.add(pagoEfectivoRepository.mappingToDb(pagoEfectivo));
        }

        ////Busco Retenciones
        Iterator<Retencion> retenciones = recibo.getEntrega().getRetenciones().iterator();
        RetencionRepositoryImpl retencionRepository = new RetencionRepositoryImpl(this._db);
        List<RetencionBd> retencionBdList = new ArrayList<>();
        while (cheques.hasNext()) {
            Retencion retencion = retenciones.next();
            retencion.setEntrega(recibo.getEntrega());
            retencionBdList.add(retencionRepository.mappingToDb(retencion));
        }

        ////Busco Depositos
        Iterator<Deposito> depositos = recibo.getEntrega().getDepositos().iterator();
        DepositoRepositoryImpl depositoRepository = new DepositoRepositoryImpl(this._db);
        List<DepositoBd> depositoBdList = new ArrayList<>();
        while (cheques.hasNext()) {
            Deposito deposito = depositos.next();
            deposito.setEntrega(recibo.getEntrega());
            depositoBdList.add(depositoRepository.mappingToDb(deposito));
        }

        //Registro el movimiento con location y cliente del movimiento
        Movimiento movimiento = new Movimiento();
        movimiento.setTabla(Recibo.TABLE);
        movimiento.setIdMovil(recibo.getIdMovil());
        movimiento.setTipo(Movimiento.ALTA);
        //Location
        if (location != null) {
            movimiento.setLocation(location);
        }
        //Setear el cliente
        movimiento.setIdSucursal(recibo.getCliente().getId().getIdSucursal());
        movimiento.setIdCliente(recibo.getCliente().getId().getIdCliente());
        movimiento.setIdComercio(recibo.getCliente().getId().getIdComercio());
        Date fechaMovimiento = Calendar.getInstance().getTime();
        movimiento.setFechaMovimiento(fechaMovimiento);
        MovimientoRepositoryImpl movimientoRepository = new MovimientoRepositoryImpl(this._db);
        MovimientoBd movimientoBd = movimientoRepository.mappingToDb(movimiento);


        //genero todas las transacciones
        this._db.createReciboTransaction(reciboBd, isReciboProvisorio, entregaBd, clienteBd, comprobantesGeneradosBdList,
                reciboDetalleBdList, cuentaCorrienteBdList, chequeBdList, pagoEfectivoBdList,
                retencionBdList, depositoBdList, movimientoBd);

    }

    private ReciboBd mappingToDb(Recibo recibo) throws Exception {
        ReciboBd reciboBd = new ReciboBd();
        if (recibo != null) {
            PkReciboBd id = new PkReciboBd();

            id.setIdPuntoVenta(recibo.getId().getIdPuntoVenta());
            id.setIdRecibo(recibo.getId().getIdRecibo());
            reciboBd.setId(id);
            reciboBd.setIdSucursal(recibo.getCliente().getId().getIdSucursal());
            reciboBd.setIdCliente(recibo.getCliente().getId().getIdCliente());
            reciboBd.setIdComercio(recibo.getCliente().getId().getIdComercio());
            reciboBd.setIdVendedor(recibo.getVendedor().getIdVendedor());
            reciboBd.setFechaMovil(Formatter.formatJulianDate(recibo.getFechaMovil()));
            reciboBd.setTotal(recibo.getTotal());
            reciboBd.setIdMovil(recibo.getIdMovil());
            reciboBd.setFechaRegistroMovil(Formatter.formatDateTimeToString(recibo.getFechaRegistroMovil()));
            reciboBd.setIdEntrega(recibo.getEntrega().getId());
            reciboBd.setDeviceId(recibo.getDeviceId());
            reciboBd.setObservacion(recibo.getObservacion());
            reciboBd.setIdEstado(recibo.getIdEstado());
            reciboBd.setResultadoEnvio(recibo.getResultadoEnvio());
        }
        return reciboBd;
    }

}
