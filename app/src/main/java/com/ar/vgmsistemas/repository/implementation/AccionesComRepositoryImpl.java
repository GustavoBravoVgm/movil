package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IAccionesComDao;
import com.ar.vgmsistemas.database.dao.entity.AccionesComBd;
import com.ar.vgmsistemas.entity.AccionesCom;
import com.ar.vgmsistemas.repository.IAccionesComRepository;

import java.util.List;


public class AccionesComRepositoryImpl implements IAccionesComRepository {

    private IAccionesComDao _accionesComDao;

    public AccionesComRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._accionesComDao = db.accionesComDao();
        }
    }

    @Override
    public Integer create(AccionesCom entity) throws Exception {
        return null;
    }

    @Override
    public AccionesCom recoveryByID(Integer id) throws Exception {
        return this.mappingToDto(_accionesComDao.recoveryByID(id));
    }

    @Override
    public List<AccionesCom> recoveryAll() throws Exception {
        return null;
    }

    @Override
    public void update(AccionesCom entity) throws Exception {

    }

    @Override
    public void delete(AccionesCom entity) throws Exception {

    }

    @Override
    public void delete(Integer integer) throws Exception {

    }

    @Override
    public List<AccionesCom> recoveryByPeriodo() {
        return null;
    }

    public AccionesCom mappingToDto(AccionesComBd accionesComBd) {
        return new AccionesCom(accionesComBd.getIdAccionesCom(),
                accionesComBd.getSnConCodigos(), accionesComBd.getTiOrigen(),
                accionesComBd.getIdTipoAcciones());
    }
}
