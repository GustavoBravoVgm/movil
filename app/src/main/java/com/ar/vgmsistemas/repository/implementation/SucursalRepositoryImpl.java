package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.ISucursalDao;
import com.ar.vgmsistemas.database.dao.entity.SucursalBd;
import com.ar.vgmsistemas.entity.Sucursal;
import com.ar.vgmsistemas.repository.ISucursalRepository;

import java.util.ArrayList;
import java.util.List;

public class SucursalRepositoryImpl implements ISucursalRepository {

    private ISucursalDao _sucursalDao;

    public SucursalRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._sucursalDao = db.sucursalDao();
        }
    }

    @Override
    public Integer create(Sucursal entity) throws Exception {
        return null;
    }

    @Override
    public Sucursal recoveryByID(Integer id) throws Exception {
        return mappingToDto(_sucursalDao.recoveryByID(id));
    }

    @Override
    public List<Sucursal> recoveryAll() throws Exception {
        List<SucursalBd> listadoSucursalBd = _sucursalDao.recoveryAll();
        List<Sucursal> sucursales = new ArrayList<>();
        if (!listadoSucursalBd.isEmpty()) {
            for (SucursalBd item : listadoSucursalBd) {
                sucursales.add(mappingToDto(item));
            }
        }
        return sucursales;
    }

    @Override
    public void update(Sucursal entity) throws Exception {

    }

    @Override
    public void delete(Sucursal entity) throws Exception {

    }

    @Override
    public void delete(Integer id) throws Exception {

    }

    @Override
    public Sucursal mappingToDto(SucursalBd sucursalBd) {
        return new Sucursal(sucursalBd.getIdSucursal(), sucursalBd.getDeSucursal(), sucursalBd.getTaRentabilidadGlobal(),
                sucursalBd.getTiControlAccom());
    }
}
