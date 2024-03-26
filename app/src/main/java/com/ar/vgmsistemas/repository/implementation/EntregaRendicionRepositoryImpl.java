package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IEntregaRendicionDao;
import com.ar.vgmsistemas.database.dao.entity.EntregaRendicionBd;
import com.ar.vgmsistemas.database.dao.entity.key.PkEntregaRendicionBd;
import com.ar.vgmsistemas.entity.EntregaRendicion;
import com.ar.vgmsistemas.entity.key.PkEntregaRendicion;
import com.ar.vgmsistemas.repository.IEntregaRendicionRepository;
import com.ar.vgmsistemas.utils.Formatter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class EntregaRendicionRepositoryImpl implements IEntregaRendicionRepository {

    private IEntregaRendicionDao _entregaRendicionDao;

    public EntregaRendicionRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._entregaRendicionDao = db.entregaRendicionDao();
        }
    }

    @Override
    public PkEntregaRendicion create(EntregaRendicion entity) throws Exception {
        _entregaRendicionDao.create(mappingToDb(entity));
        return entity.getId();
    }

    @Override
    public EntregaRendicion recoveryByID(PkEntregaRendicion pkEntregaRendicion) throws Exception {
        return mappingToDto(_entregaRendicionDao.recoveryByID(pkEntregaRendicion.getIdVeces(),
                pkEntregaRendicion.getIdLegajo(), Formatter.formatJulianDate(pkEntregaRendicion.getFeEntrega())));
    }

    @Override
    public List<EntregaRendicion> recoveryAll() throws Exception {
        List<EntregaRendicionBd> listadoNoEnviados = _entregaRendicionDao.recoveryAll();
        List<EntregaRendicion> entregas = new ArrayList<>();
        if (!listadoNoEnviados.isEmpty()) {
            for (EntregaRendicionBd item : listadoNoEnviados) {
                entregas.add(mappingToDto(item));
            }
        }
        return entregas;
    }

    @Override
    public void update(EntregaRendicion mEntregaRendicion) throws Exception {
        _entregaRendicionDao.update(mappingToDb(mEntregaRendicion));
    }

    @Override
    public void delete(EntregaRendicion mEntregaRendicion) throws Exception {
        _entregaRendicionDao.delete(mappingToDb(mEntregaRendicion));
    }

    @Override
    public void delete(PkEntregaRendicion pkEntregaRendicion) throws Exception {
        _entregaRendicionDao.delete(pkEntregaRendicion.getIdVeces(),
                pkEntregaRendicion.getIdLegajo(), Formatter.formatJulianDate(pkEntregaRendicion.getFeEntrega()));
    }

    public List<EntregaRendicion> recoveryNoEnviadas() throws Exception {
        List<EntregaRendicionBd> listadoNoEnviados = _entregaRendicionDao.recoveryNoEnviadas();
        List<EntregaRendicion> entregas = new ArrayList<>();
        if (!listadoNoEnviados.isEmpty()) {
            for (EntregaRendicionBd item : listadoNoEnviados) {
                entregas.add(mappingToDto(item));
            }
        }
        return entregas;
    }

    @Override
    public EntregaRendicion mappingToDto(EntregaRendicionBd entregaRendicionBd) throws ParseException {
        EntregaRendicion entrega = new EntregaRendicion();
        if (entregaRendicionBd != null) {
            PkEntregaRendicion id = new PkEntregaRendicion(entregaRendicionBd.getId().getIdVeces(),
                    entregaRendicionBd.getId().getIdLegajo(),
                    Formatter.convertToDate(entregaRendicionBd.getId().getFeEntrega()));

            entrega.setId(id);

            entrega.setPrBillete1(entregaRendicionBd.getPrBillete1());
            entrega.setPrBillete2(entregaRendicionBd.getPrBillete2());
            entrega.setPrBillete3(entregaRendicionBd.getPrBillete3());
            entrega.setPrBillete4(entregaRendicionBd.getPrBillete4());
            entrega.setPrBillete5(entregaRendicionBd.getPrBillete5());
            entrega.setPrBillete6(entregaRendicionBd.getPrBillete6());
            entrega.setPrBillete7(entregaRendicionBd.getPrBillete7());
            entrega.setPrBillete8(entregaRendicionBd.getPrBillete8());

            entrega.setCaBillete1(entregaRendicionBd.getCaBillete1());
            entrega.setCaBillete2(entregaRendicionBd.getCaBillete2());
            entrega.setCaBillete3(entregaRendicionBd.getCaBillete3());
            entrega.setCaBillete4(entregaRendicionBd.getCaBillete4());
            entrega.setCaBillete5(entregaRendicionBd.getCaBillete5());
            entrega.setCaBillete6(entregaRendicionBd.getCaBillete6());
            entrega.setCaBillete7(entregaRendicionBd.getCaBillete7());
            entrega.setCaBillete8(entregaRendicionBd.getCaBillete8());

            entrega.setPrEfectivo(entregaRendicionBd.getPrEfectivo());
            entrega.setPrMonedas(entregaRendicionBd.getPrMonedas());
            entrega.setPrTickets(entregaRendicionBd.getPrTickets());
            entrega.setPrCheques(entregaRendicionBd.getPrCheques());
            entrega.setPrGastos(entregaRendicionBd.getPrGastos());
            entrega.setIdSucursal(entregaRendicionBd.getIdSucursal());
            entrega.setIdSucentrega(entregaRendicionBd.getIdSucentrega());
            entrega.setIdMovil(entregaRendicionBd.getIdMovil());

        }
        return entrega;
    }

    private EntregaRendicionBd mappingToDb(EntregaRendicion entregaRendicion) {
        if (entregaRendicion != null) {
            PkEntregaRendicionBd id = new PkEntregaRendicionBd();

            id.setIdVeces(entregaRendicion.getId().getIdVeces());
            id.setIdLegajo(entregaRendicion.getId().getIdLegajo());
            id.setFeEntrega(Formatter.formatDateTimeToString(entregaRendicion.getId().getFeEntrega()));
            EntregaRendicionBd entregaBd = new EntregaRendicionBd();

            entregaBd.setId(id);

            entregaBd.setPrBillete1(entregaRendicion.getPrBillete1());
            entregaBd.setPrBillete2(entregaRendicion.getPrBillete2());
            entregaBd.setPrBillete3(entregaRendicion.getPrBillete3());
            entregaBd.setPrBillete4(entregaRendicion.getPrBillete4());
            entregaBd.setPrBillete5(entregaRendicion.getPrBillete5());
            entregaBd.setPrBillete6(entregaRendicion.getPrBillete6());
            entregaBd.setPrBillete7(entregaRendicion.getPrBillete7());
            entregaBd.setPrBillete8(entregaRendicion.getPrBillete8());

            entregaBd.setCaBillete1(entregaRendicion.getCaBillete1());
            entregaBd.setCaBillete2(entregaRendicion.getCaBillete2());
            entregaBd.setCaBillete3(entregaRendicion.getCaBillete3());
            entregaBd.setCaBillete4(entregaRendicion.getCaBillete4());
            entregaBd.setCaBillete5(entregaRendicion.getCaBillete5());
            entregaBd.setCaBillete6(entregaRendicion.getCaBillete6());
            entregaBd.setCaBillete7(entregaRendicion.getCaBillete7());
            entregaBd.setCaBillete8(entregaRendicion.getCaBillete8());

            entregaBd.setPrEfectivo(entregaRendicion.getPrEfectivo());
            entregaBd.setPrMonedas(entregaRendicion.getPrMonedas());
            entregaBd.setPrTickets(entregaRendicion.getPrTickets());
            entregaBd.setPrCheques(entregaRendicion.getPrCheques());
            entregaBd.setPrGastos(entregaRendicion.getPrGastos());
            entregaBd.setIdSucursal(entregaRendicion.getIdSucursal());
            entregaBd.setIdSucentrega(entregaRendicion.getIdSucentrega());
            entregaBd.setIdMovil(entregaRendicion.getIdMovil());

            return entregaBd;
        }
        return null;
    }
}
