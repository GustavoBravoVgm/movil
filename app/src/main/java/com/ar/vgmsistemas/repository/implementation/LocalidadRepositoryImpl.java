package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.ILocalidadDao;
import com.ar.vgmsistemas.database.dao.IProvinciaDao;
import com.ar.vgmsistemas.database.dao.entity.LocalidadBd;
import com.ar.vgmsistemas.entity.Localidad;
import com.ar.vgmsistemas.repository.ILocalidadRepository;

import java.util.ArrayList;
import java.util.List;

public class LocalidadRepositoryImpl implements ILocalidadRepository {
    private final AppDataBase _db;

    private ILocalidadDao _localidadDao;

    private IProvinciaDao _provinciaDao;

    public LocalidadRepositoryImpl(AppDataBase db) {
        this._db = db;
        if (db != null) {
            this._localidadDao = db.localidadDao();
            this._provinciaDao = db.provinciaDao();
        }
    }

    public Integer create(Localidad localidad) throws Exception {
        return null;
    }

    public void delete(Localidad entity) throws Exception {

    }

    public void delete(Integer id) throws Exception {

    }

    public List<Localidad> recoveryAll() throws Exception {
        List<LocalidadBd> listadoLocalidadesBd = _localidadDao.recoveryAll();
        List<Localidad> localidades = new ArrayList<>();
        if (!listadoLocalidadesBd.isEmpty()) {
            for (LocalidadBd item : listadoLocalidadesBd) {
                localidades.add(mappingToDto(item));
            }
        }
        return localidades;
    }

    public List<Localidad> recoveryByProvincia(int idProvincia) throws Exception {
        List<LocalidadBd> listadoLocalidadesBd = _localidadDao.recoveryByProvincia(idProvincia);
        List<Localidad> localidades = new ArrayList<>();
        if (!listadoLocalidadesBd.isEmpty()) {
            for (LocalidadBd item : listadoLocalidadesBd) {
                localidades.add(mappingToDto(item));
            }
        }
        return localidades;
    }

    public Localidad recoveryByID(Integer id) throws Exception {
        return null;
    }

    public void update(Localidad localidad) throws Exception {

    }

    @Override
    public Localidad mappingToDto(LocalidadBd localidadBd) throws Exception {
        Localidad localidad = new Localidad();
        if (localidadBd != null) {
            localidad.setId(localidadBd.getId());
            localidad.setDescripcion(localidadBd.getDescripcion());
            localidad.setCodigoPostal(localidadBd.getCodigoPostal() == null ? 0 : localidadBd.getCodigoPostal());
            //cargo provincia
            ProvinciaRepositoryImpl provinciaRepository = new ProvinciaRepositoryImpl(_db);
            if (localidadBd.getIdProvincia() > -1) {
                localidad.setProvincia(provinciaRepository.mappingToDto(_provinciaDao.recoveryByID(localidadBd.getIdProvincia())));
            }
        }
        return localidad;

    }
}
