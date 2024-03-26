package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.INegocioDao;
import com.ar.vgmsistemas.database.dao.entity.NegocioBd;
import com.ar.vgmsistemas.entity.Negocio;
import com.ar.vgmsistemas.repository.INegocioRepository;

import java.util.List;

public class NegocioRepositoryImpl implements INegocioRepository {
    private INegocioDao _negocioDao;

    public NegocioRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._negocioDao = db.negocioDao();
        }
    }

    public Integer create(Negocio negocio) throws Exception {
        return null;
    }

    public void delete(Negocio entity) throws Exception {

    }

    @Override
    public void delete(Integer id) throws Exception {

    }

    public List<Negocio> recoveryAll() throws Exception {
        return null;
    }

    @Override
    public Negocio recoveryByID(Integer id) throws Exception {
        return mappingToDto(_negocioDao.recoveryById(id));
    }

    public void update(Negocio negocio) throws Exception {

    }

    @Override
    public Negocio mappingToDto(NegocioBd negocioBd) {
        if (negocioBd == null) return null;
        return new Negocio(negocioBd.getId(), negocioBd.getDescripcion());
    }

    @Override
    public Negocio mappingToDto(Integer id) throws Exception {
        if (id == null) return null;
        return mappingToDto(_negocioDao.recoveryById(id));
    }
}
