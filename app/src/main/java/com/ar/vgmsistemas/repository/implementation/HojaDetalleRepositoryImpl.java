package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IHojaDetalleDao;
import com.ar.vgmsistemas.database.dao.entity.HojaDetalleBd;
import com.ar.vgmsistemas.database.dao.entity.key.PkHojaDetalleBd;
import com.ar.vgmsistemas.entity.HojaDetalle;
import com.ar.vgmsistemas.entity.key.PkHojaDetalle;
import com.ar.vgmsistemas.repository.IHojaDetalleRepository;
import com.ar.vgmsistemas.utils.Formatter;

import java.util.ArrayList;
import java.util.List;

public class HojaDetalleRepositoryImpl implements IHojaDetalleRepository {
    private final AppDataBase _db;

    private IHojaDetalleDao _hojaDetalleDao;


    public HojaDetalleRepositoryImpl(AppDataBase db) {
        this._db = db;
        if (db != null) {
            this._hojaDetalleDao = db.hojaDetalleDao();
        }
    }

    @Override
    public PkHojaDetalle create(HojaDetalle entity) throws Exception {
        return null;
    }

    @Override
    public HojaDetalle recoveryByID(PkHojaDetalle id) throws Exception {
        return mappingToDto(_hojaDetalleDao.recoveryByID(id.getIdSucursal(), id.getIdHoja(), id.getIdFcnc(),
                id.getIdTipoab(), id.getIdPtovta(), id.getIdNumdoc()));
    }

    @Override
    public void update(HojaDetalle mHojaDetalle) throws Exception {
        _hojaDetalleDao.update(mappingToDb(mHojaDetalle));
    }

    @Override
    public void delete(HojaDetalle entity) throws Exception {

    }

    @Override
    public void delete(PkHojaDetalle id) throws Exception {

    }

    @Override
    public List<HojaDetalle> recoveryByHoja(int idSucursal, int idHoja) throws Exception {
        List<HojaDetalleBd> listadoHojaDetalle = _hojaDetalleDao.recoveryByHoja(idSucursal, idHoja);
        List<HojaDetalle> hojasDetalles = new ArrayList<>();
        if (!listadoHojaDetalle.isEmpty()) {
            for (HojaDetalleBd item : listadoHojaDetalle) {
                hojasDetalles.add(mappingToDto(item));
            }
        }
        return hojasDetalles;
    }

    @Override
    public List<HojaDetalle> recoveryChequesByHoja(int idSucursal, int idHoja) throws Exception {
        List<HojaDetalleBd> listadoHojaDetalle = _hojaDetalleDao.recoveryChequesByHoja(idSucursal, idHoja);
        if (!listadoHojaDetalle.isEmpty()) {
            List<HojaDetalle> hojasDetalles = new ArrayList<>();
            for (HojaDetalleBd item : listadoHojaDetalle) {
                hojasDetalles.add(mappingToDto(item));
            }
            return hojasDetalles;
        }
        return null;
    }

    @Override
    public List<HojaDetalle> recoveryByHojaConEntrega(int idSucursal, int idHoja) throws Exception {
        List<HojaDetalleBd> listadoHojaDetalle = _hojaDetalleDao.recoveryByHojaConEntrega(idSucursal, idHoja);
        List<HojaDetalle> hojasDetalles = new ArrayList<>();
        if (!listadoHojaDetalle.isEmpty()) {
            for (HojaDetalleBd item : listadoHojaDetalle) {
                hojasDetalles.add(mappingToDto(item));
            }
        }
        return hojasDetalles;
    }

    public HojaDetalle recoveryByIDConEntrega(PkHojaDetalle id) throws Exception {
        return mappingToDto(_hojaDetalleDao.recoveryByIdConEntrega(id.getIdSucursal(), id.getIdHoja(), id.getIdFcnc(),
                id.getIdTipoab(), id.getIdPtovta(), id.getIdNumdoc()));
    }

    public List<HojaDetalle> recoveryAEnviar() throws Exception {
        List<HojaDetalleBd> listadoHojaDetalle = _hojaDetalleDao.recoveryAEnviar();
        List<HojaDetalle> hojasDetalles = new ArrayList<>();
        if (!listadoHojaDetalle.isEmpty()) {
            for (HojaDetalleBd item : listadoHojaDetalle) {
                hojasDetalles.add(mappingToDto(item));
            }
        }
        return hojasDetalles;
    }

    @Override
    public void updateState(HojaDetalle detalle, String state) throws Exception {
        detalle.setTiEstado(state);
        _hojaDetalleDao.update(mappingToDb(detalle));
    }

    @Override
    public void updateValoresEntrega(HojaDetalle detalle) {
        detalle.setPrPagado(0d);
    }

    @Override
    public boolean isEnviado(HojaDetalle detalle) throws Exception {
        if (detalle.getIdMovil() != null) {
            String fechaSincronizacion = _hojaDetalleDao.isEnviado(detalle.getIdMovil());
            // si no tiene fecha de sincronizacion significa que no esta enviado
            return fechaSincronizacion != null && !fechaSincronizacion.equals("");
        }
        return false;
    }

    @Override
    public int recoveryIdEntrega(HojaDetalle detalle) throws Exception {
        int result;
        result = _hojaDetalleDao.recoveryIdEntrega(detalle.getId().getIdSucursal(),
                detalle.getId().getIdHoja(), detalle.getId().getIdFcnc(),
                detalle.getId().getIdTipoab(), detalle.getId().getIdPtovta(),
                detalle.getId().getIdPtovta());

        return result;
    }

    @Override
    public List<HojaDetalle> recoveryBySucursal(int idSucursal) throws Exception {
        List<HojaDetalleBd> listadoHojaDetalle = _hojaDetalleDao.recoveryBySucursal(idSucursal);
        List<HojaDetalle> hojasDetalles = new ArrayList<>();
        if (!listadoHojaDetalle.isEmpty()) {
            for (HojaDetalleBd item : listadoHojaDetalle) {
                hojasDetalles.add(mappingToDto(item));
            }
        }
        return hojasDetalles;
    }

    public List<HojaDetalle> recoveryByIdEntrega(int idEntrega) throws Exception {
        List<HojaDetalleBd> listadoHojaDetalle = _hojaDetalleDao.recoveryByIdEntrega(idEntrega);
        List<HojaDetalle> hojasDetalles = new ArrayList<>();
        if (!listadoHojaDetalle.isEmpty()) {
            for (HojaDetalleBd item : listadoHojaDetalle) {
                hojasDetalles.add(mappingToDto(item));
            }
        }
        return hojasDetalles;
    }

    public List<HojaDetalle> recoveryAEnviarUnicaEntrega() throws Exception {
        List<HojaDetalleBd> listadoHojaDetalle = _hojaDetalleDao.recoveryAEnviarUnicaEntrega();
        List<HojaDetalle> hojasDetalles = new ArrayList<>();
        if (!listadoHojaDetalle.isEmpty()) {
            for (HojaDetalleBd item : listadoHojaDetalle) {
                hojasDetalles.add(mappingToDto(item));
            }
        }
        return hojasDetalles;
    }

    @Override
    public List<HojaDetalle> recoveryAll() throws Exception {
        List<HojaDetalleBd> listadoHojaDetalle = _hojaDetalleDao.recoveryAll();
        List<HojaDetalle> hojasDetalles = new ArrayList<>();
        if (!listadoHojaDetalle.isEmpty()) {
            for (HojaDetalleBd item : listadoHojaDetalle) {
                hojasDetalles.add(mappingToDto(item));
            }
        }
        return hojasDetalles;
    }

    public HojaDetalle mappingToDto(HojaDetalleBd hojaDetalleBd) throws Exception {
        HojaDetalle hojaDetalle = new HojaDetalle();
        if (hojaDetalleBd != null) {
            PkHojaDetalle id = new PkHojaDetalle(hojaDetalleBd.getId().getIdHoja(), hojaDetalleBd.getId().getIdSucursal(),
                    hojaDetalleBd.getId().getIdFcnc(), hojaDetalleBd.getId().getIdTipoab(), hojaDetalleBd.getId().getIdPtovta(),
                    hojaDetalleBd.getId().getIdNumdoc());

            hojaDetalle.setId(id);
            hojaDetalle.setFeVenta(hojaDetalleBd.getFeVenta() == null
                    ? null
                    : Formatter.convertToDate(hojaDetalleBd.getFeVenta()));
            //cargo vendedor
            VendedorRepositoryImpl vendedorRepository = new VendedorRepositoryImpl(this._db);
            hojaDetalle.setVendedor(vendedorRepository.mappingToDto(this._db.vendedorDao().recoveryByID(
                    hojaDetalleBd.getIdVendedor())));
            hojaDetalle.setPrTotal(hojaDetalleBd.getPrTotal());
            hojaDetalle.setPrPagado(hojaDetalleBd.getPrPagado());
            hojaDetalle.setPrNotaCredito(hojaDetalleBd.getPrNotaCredito());
            hojaDetalle.setTiEstado(hojaDetalleBd.getTiEstado());
            //cargo cliente
            ClienteRepositoryImpl clienteRepository = new ClienteRepositoryImpl(this._db);
            hojaDetalle.setCliente(clienteRepository.mappingToDto(this._db.clienteDao().recoveryByID(
                    hojaDetalleBd.getIdSucCliente(),
                    hojaDetalleBd.getIdCliente(), hojaDetalleBd.getIdComercio())));
            hojaDetalle.setIdMovil(hojaDetalleBd.getIdMovil());
            //cargo entrega
            EntregaRepositoryImpl entregaRepository = new EntregaRepositoryImpl(this._db);
            hojaDetalle.setEntrega(entregaRepository.mappingToDto(this._db.entregaDao().recoveryByID(
                    hojaDetalleBd.getIdEntregaDetalle())));
            hojaDetalle.setCodigoAutorizacion(hojaDetalleBd.getCodigoAutorizacion());
            //cargo motivo autorización
            MotivoAutorizacionRepositoryImpl motivoAutorizacionRepository = new MotivoAutorizacionRepositoryImpl(this._db);
            hojaDetalle.setMotivoAutorizacion(motivoAutorizacionRepository.mappingToDto(
                    this._db.motivoAutorizacionDao().recoveryByID(hojaDetalleBd.getIdMotivoAutorizacion())));
            hojaDetalle.setDeCodAutRepMovilCtaCte(hojaDetalleBd.getDeCodAutRepMovilCtaCte());
            //cargo condición venta
            CondicionVentaRepositoryImpl condicionVentaRepository = new CondicionVentaRepositoryImpl(this._db);
            hojaDetalle.setCondicionVenta(condicionVentaRepository.mappingToDto(
                    this._db.condicionVentaDao().recoveryByID(hojaDetalleBd.getIdCondicionVenta())));
            //cargo documento
            DocumentoRepositoryImpl documentoRepository = new DocumentoRepositoryImpl(this._db);
            hojaDetalle.setDocumento(documentoRepository.mappingToDto(this._db.documentoDao().recoveryByID(
                    hojaDetalleBd.getId().getIdFcnc(), hojaDetalleBd.getId().getIdTipoab(),
                    hojaDetalleBd.getId().getIdPtovta())));

        }
        return hojaDetalle;
    }

    private HojaDetalleBd mappingToDb(HojaDetalle hojaDetalle) {
        if (hojaDetalle != null) {
            PkHojaDetalleBd id = new PkHojaDetalleBd();

            id.setIdHoja(hojaDetalle.getId().getIdHoja());
            id.setIdSucursal(hojaDetalle.getId().getIdSucursal());
            id.setIdFcnc(hojaDetalle.getId().getIdFcnc());
            id.setIdTipoab(hojaDetalle.getId().getIdTipoab());
            id.setIdPtovta(hojaDetalle.getId().getIdPtovta());
            id.setIdNumdoc(hojaDetalle.getId().getIdNumdoc());

            HojaDetalleBd hojaDetalleBd = new HojaDetalleBd();
            hojaDetalleBd.setId(id);
            hojaDetalleBd.setFeVenta(Formatter.formatDate(hojaDetalle.getFeVenta()));
            hojaDetalleBd.setIdVendedor(hojaDetalle.getVendedor().getIdVendedor());
            hojaDetalleBd.setPrTotal(hojaDetalle.getPrTotal());
            hojaDetalleBd.setPrPagado(hojaDetalle.getPrPagado());
            hojaDetalleBd.setPrNotaCredito(hojaDetalle.getPrNotaCredito());
            hojaDetalleBd.setTiEstado(hojaDetalle.getTiEstado());
            hojaDetalleBd.setIdSucCliente(hojaDetalle.getCliente().getId().getIdSucursal());
            hojaDetalleBd.setIdCliente(hojaDetalle.getCliente().getId().getIdCliente());
            hojaDetalleBd.setIdComercio(hojaDetalle.getCliente().getId().getIdComercio());
            hojaDetalleBd.setIdMovil(hojaDetalle.getIdMovil());
            hojaDetalleBd.setIdEntregaDetalle(hojaDetalle.getEntrega().getId());
            hojaDetalleBd.setIdCondicionVenta(hojaDetalle.getCondicionVenta().getId());
            hojaDetalleBd.setIdMotivoAutorizacion(hojaDetalle.getMotivoAutorizacion().getId());
            hojaDetalleBd.setCodigoAutorizacion(hojaDetalle.getCodigoAutorizacion());
            hojaDetalleBd.setDeCodAutRepMovilCtaCte(hojaDetalle.getDeCodAutRepMovilCtaCte());
            return hojaDetalleBd;
        }
        return null;
    }
}
