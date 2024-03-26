package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IHojaDao;
import com.ar.vgmsistemas.database.dao.entity.HojaBd;
import com.ar.vgmsistemas.entity.Hoja;
import com.ar.vgmsistemas.repository.IHojaRepository;
import com.ar.vgmsistemas.utils.Formatter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class HojaRepositoryImpl implements IHojaRepository {

    private IHojaDao _hojaDao;

    public HojaRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._hojaDao = db.hojaDao();
        }
    }

    @Override
    public Long create(Hoja entity) throws Exception {
        return null;
    }

    @Override
    public Hoja recoveryByID(Long id) throws Exception {
        return null;
    }

    @Override
    public List<Hoja> recoveryAll() throws Exception {
        List<HojaBd> listadoHojas = _hojaDao.recoveryAll();
        List<Hoja> hojas = new ArrayList<>();
        if (!listadoHojas.isEmpty()) {
            for (HojaBd item : listadoHojas) {
                hojas.add(mappingToDto(item));
            }
        }
        return hojas;
    }

    @Override
    public void update(Hoja entity) throws Exception {
    }

    @Override
    public void delete(Hoja entity) throws Exception {
    }

    @Override
    public void delete(Long id) throws Exception {
    }

    @Override
    public void updateTotalAnulado(int idSucursal, int idHoja, double totalAnulado) throws Exception {
        _hojaDao.updateTotalAnulado(idSucursal, idHoja, totalAnulado);
    }

    @Override
    public void updateTotalNoEntregado(int idSucursal, int idHoja, double prPendiente) throws Exception {
        _hojaDao.updateTotalNoEntregado(idSucursal, idHoja, prPendiente);
    }

    @Override
    public void updateState(int idSucursal, int idHoja, int state) throws Exception {
        _hojaDao.updateState(idSucursal, idHoja, state);
    }

    @Override
    public String getSnRendida(int idSucursal, int idHoja) throws Exception {
        return _hojaDao.getSnRendida(idSucursal, idHoja);
    }

    @Override
    public List<Hoja> recoveryBySucursal(int idSucursal) throws Exception {
        List<HojaBd> listadoHojas = _hojaDao.recoveryBySucursal(idSucursal);
        List<Hoja> hojas = new ArrayList<>();
        if (!listadoHojas.isEmpty()) {
            for (HojaBd item : listadoHojas) {
                hojas.add(mappingToDto(item));
            }
        }
        return hojas;
    }

    @Override
    public Hoja mappingToDto(HojaBd hojaBd) throws ParseException {
        Hoja hoja = new Hoja();
        if (hojaBd != null) {
            hoja.setIdHoja(hojaBd.getIdHoja());
            hoja.setIdSucursal(hojaBd.getIdSucursal());
            hoja.setFeHoja(hojaBd.getFeHoja() == null
                    ? null
                    : Formatter.convertToDate(hojaBd.getFeHoja()));
            hoja.setIdDeposito(hojaBd.getIdDeposito());
            hoja.setPrContado(hojaBd.getPrContado());
            hoja.setPrFiado(hojaBd.getPrFiado());
            hoja.setPrPendiente(hojaBd.getPrPendiente());
            hoja.setPrAnulado(hojaBd.getPrAnulado());
            hoja.setPrNc(hojaBd.getPrNc());
            hoja.setPrTotal(hojaBd.getPrTotal());
            hoja.setTiEstado(hojaBd.getTiEstado());
            hoja.setIsRendida(hojaBd.getIsRendida());

        }
        return hoja;
    }

}
