package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IReciboDetalleDao;
import com.ar.vgmsistemas.database.dao.entity.ReciboDetalleBd;
import com.ar.vgmsistemas.entity.Recibo;
import com.ar.vgmsistemas.entity.ReciboDetalle;
import com.ar.vgmsistemas.repository.IReciboDetalleRepository;

import java.util.ArrayList;
import java.util.List;

public class ReciboDetalleRepositoryImpl implements IReciboDetalleRepository {
    private final AppDataBase _db;

    private IReciboDetalleDao _reciboDetalleDao;

    public ReciboDetalleRepositoryImpl(AppDataBase db) {
        this._db = db;
        if (db != null) {
            this._reciboDetalleDao = db.reciboDetalleDao();
        }
    }

    public Integer create(ReciboDetalle reciboDetalle) throws Exception {
        //return reciboDetalleDao.create(mappingToDb(reciboDetalle));
        _reciboDetalleDao.create(mappingToDb(reciboDetalle));
        return reciboDetalle.getId();
    }

    @Override
    public ReciboDetalle recoveryByID(Integer integer) throws Exception {
        return null;
    }

    public List<ReciboDetalle> recoveryByRecibo(Recibo recibo) throws Exception {
        List<ReciboDetalleBd> listadoRecibosDetalle = _reciboDetalleDao.recoveryByRecibo(recibo.getId().getIdPuntoVenta(),
                recibo.getId().getIdRecibo());
        List<ReciboDetalle> detalles = new ArrayList<>();
        if (!listadoRecibosDetalle.isEmpty()) {
            for (ReciboDetalleBd item : listadoRecibosDetalle) {
                detalles.add(mappingToDto(item));
            }
        }
        return detalles;
    }

    public List<ReciboDetalle> recoveryAll() throws Exception {
        return null;
    }

    public void update(ReciboDetalle entity) throws Exception {
    }

    public void delete(ReciboDetalle entity) throws Exception {
    }

    @Override
    public void delete(Integer integer) throws Exception {

    }

    public ReciboDetalle mappingToDto(ReciboDetalleBd reciboDetalleBd) throws Exception {
        ReciboDetalle detalle = new ReciboDetalle();
        if (reciboDetalleBd != null) {
            detalle.setId(reciboDetalleBd.getId());
            //cargo cuenta corriente
            CuentaCorrienteRepositoryImpl cuentaCorrienteRepository = new CuentaCorrienteRepositoryImpl(this._db);
            detalle.setCuentaCorriente(cuentaCorrienteRepository.mappingToDto(this._db.cuentaCorrienteDao().recoveryByID(
                    reciboDetalleBd.getIdDocumento(), reciboDetalleBd.getIdLetra(), reciboDetalleBd.getPuntoVenta(),
                    reciboDetalleBd.getIdNumeroDocumento(), reciboDetalleBd.getCuota())));
            //cargo recibo
            ReciboRepositoryImpl reciboRepository = new ReciboRepositoryImpl(this._db);
            detalle.setRecibo(reciboRepository.mappingToDto(this._db.reciboDao().recoveryById(reciboDetalleBd.getIdRecPvta(),
                    reciboDetalleBd.getIdRecibo())));

            detalle.setImportePagado(reciboDetalleBd.getImportePagado());
            detalle.setSaldoMovil(reciboDetalleBd.getSaldoMovil());
            detalle.setTaDtoRecibo(reciboDetalleBd.getTaDtoRecibo());
            detalle.setIdPago(reciboDetalleBd.getIdPago());
            detalle.setPrPago(reciboDetalleBd.getPrPago());
            detalle.setPrNc(reciboDetalleBd.getPrNc());
            detalle.setPrSaldo(reciboDetalleBd.getPrSaldo());
            detalle.setIdLegajo(reciboDetalleBd.getIdLegajo());
        }
        return detalle;
    }


    public ReciboDetalleBd mappingToDb(ReciboDetalle detalle1) {
        if (detalle1 != null) {
            ReciboDetalleBd reciboDetalleBd = new ReciboDetalleBd();

            reciboDetalleBd.setId(detalle1.getId());

            reciboDetalleBd.setIdDocumento(detalle1.getCuentaCorriente().getId().getIdDocumento());
            reciboDetalleBd.setIdLetra(detalle1.getCuentaCorriente().getId().getIdLetra());
            reciboDetalleBd.setPuntoVenta(detalle1.getCuentaCorriente().getId().getPuntoVenta());
            reciboDetalleBd.setIdNumeroDocumento(detalle1.getCuentaCorriente().getId().getIdNumeroDocumento());
            reciboDetalleBd.setCuota(detalle1.getCuentaCorriente().getId().getCuota());

            reciboDetalleBd.setIdRecPvta(detalle1.getRecibo().getId().getIdPuntoVenta());
            reciboDetalleBd.setIdRecibo(detalle1.getRecibo().getId().getIdRecibo());

            reciboDetalleBd.setImportePagado(detalle1.getImportePagado());
            reciboDetalleBd.setSaldoMovil(detalle1.getSaldoMovil());
            reciboDetalleBd.setTaDtoRecibo(detalle1.getTaDtoRecibo());
            reciboDetalleBd.setIdPago(detalle1.getIdPago());
            reciboDetalleBd.setPrPago(detalle1.getPrPago());
            reciboDetalleBd.setPrNc(detalle1.getPrNc());
            reciboDetalleBd.setPrSaldo(detalle1.getPrSaldo());
            reciboDetalleBd.setIdLegajo(detalle1.getIdLegajo());


            return reciboDetalleBd;
        }
        return null;
    }
}
