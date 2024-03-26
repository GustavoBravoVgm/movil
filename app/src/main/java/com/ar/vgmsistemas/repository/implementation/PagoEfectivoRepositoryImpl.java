package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IPagoEfectivoDao;
import com.ar.vgmsistemas.database.dao.entity.PagoEfectivoBd;
import com.ar.vgmsistemas.entity.Entrega;
import com.ar.vgmsistemas.entity.PagoEfectivo;
import com.ar.vgmsistemas.repository.IPagoEfectivoRepository;

import java.util.ArrayList;
import java.util.List;

public class PagoEfectivoRepositoryImpl implements IPagoEfectivoRepository {
    private final AppDataBase _db;

    private IPagoEfectivoDao _pagoEfectivoDao;

    public PagoEfectivoRepositoryImpl(AppDataBase db) {
        this._db = db;
        if (db != null) {
            this._pagoEfectivoDao = db.pagoEfectivoDao();
        }
    }

    public Long create(PagoEfectivo pago) throws Exception {
        return _pagoEfectivoDao.create(mappingToDb(pago));
    }


    public PagoEfectivo recoveryByID(Long id) throws Exception {
        return null;
    }

    public List<PagoEfectivo> recoveryAll() throws Exception {
        return null;
    }

    public void update(PagoEfectivo entity) throws Exception {

    }

    public void delete(PagoEfectivo entity) throws Exception {
    }

    public void deleteByEntrega(PagoEfectivo entity) throws Exception {
        _pagoEfectivoDao.deleteByEntrega(entity.getEntrega().getId());
    }

    public void delete(Long id) throws Exception {

    }

    public List<PagoEfectivo> recoveryByEntrega(Entrega entrega) throws Exception {
        List<PagoEfectivoBd> listadoPagosEfectivoBd = _pagoEfectivoDao.recoveryByEntrega(entrega.getId());
        List<PagoEfectivo> pagosEfectivo = new ArrayList<>();
        if (!listadoPagosEfectivoBd.isEmpty()) {
            for (PagoEfectivoBd item : listadoPagosEfectivoBd) {
                pagosEfectivo.add(mappingToDto(item));
            }
        }
        return pagosEfectivo;
    }

    public PagoEfectivo mappingToDto(PagoEfectivoBd pagoEfectivoBd) throws Exception {
        PagoEfectivo pagoEfectivo = new PagoEfectivo();
        if (pagoEfectivoBd != null) {
            pagoEfectivo.setId(pagoEfectivoBd.getId());
            pagoEfectivo.setCotizacion(pagoEfectivoBd.getCotizacion());
            pagoEfectivo.setImporteMoneda(pagoEfectivoBd.getImporteMoneda());
            pagoEfectivo.setImporteMonedaBase(pagoEfectivoBd.getImporteMonedaBase());
            //cargo tipo moneda
            TipoMonedaRepositoryImpl tipoMonedaRepository = new TipoMonedaRepositoryImpl(this._db);
            pagoEfectivo.setTipoMoneda(tipoMonedaRepository.mappingToDto(this._db.tipoMonedaDao().recoveryByID(pagoEfectivoBd.getIdValor())));
            //cargo entrega
            EntregaRepositoryImpl entregaRepository = new EntregaRepositoryImpl(this._db);
            pagoEfectivo.setEntrega(entregaRepository.mappingToDto(this._db.entregaDao().recoveryByID(pagoEfectivoBd.getIdEntrega())));
        }
        return pagoEfectivo;
    }

    public PagoEfectivoBd mappingToDb(PagoEfectivo pagoEfectivo) {
        if (pagoEfectivo != null) {
            PagoEfectivoBd pagoEfectivoBd = new PagoEfectivoBd();

            pagoEfectivoBd.setId(pagoEfectivo.getId());
            pagoEfectivoBd.setCotizacion(pagoEfectivo.getCotizacion());
            pagoEfectivoBd.setImporteMoneda(pagoEfectivo.getImporteMoneda());
            pagoEfectivoBd.setImporteMonedaBase(pagoEfectivo.getImporteMonedaBase());

            pagoEfectivoBd.setIdEntrega(pagoEfectivo.getEntrega().getId());
            pagoEfectivoBd.setIdValor(pagoEfectivo.getTipoMoneda().getId());

            return pagoEfectivoBd;
        }
        return null;
    }

}
