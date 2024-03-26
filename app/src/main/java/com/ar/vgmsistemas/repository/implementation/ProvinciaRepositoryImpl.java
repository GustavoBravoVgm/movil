package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IProvinciaDao;
import com.ar.vgmsistemas.database.dao.entity.ProvinciaBd;
import com.ar.vgmsistemas.entity.Provincia;
import com.ar.vgmsistemas.repository.IProvinciaRepository;

import java.util.ArrayList;
import java.util.List;

public class ProvinciaRepositoryImpl implements IProvinciaRepository {

    private IProvinciaDao _provinciaDao;

    public ProvinciaRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._provinciaDao = db.provinciaDao();
        }
    }

    public Integer create(Provincia provincia) throws Exception {
        return null;
    }

    public void delete(Provincia entity) throws Exception {

    }

    public void delete(Integer id) throws Exception {

    }

    public List<Provincia> recoveryAll() throws Exception {
        List<ProvinciaBd> listadoProvincias = _provinciaDao.recoveryAll();
        List<Provincia> provincias = new ArrayList<>();
        if (!listadoProvincias.isEmpty()) {
            for (ProvinciaBd item : listadoProvincias) {
                provincias.add(mappingToDto(item));
            }
        }
        return provincias;
    }


    public Provincia recoveryByID(Integer id) throws Exception {
        return mappingToDto(_provinciaDao.recoveryByID(id));
    }

    public void update(Provincia provincia) throws Exception {

    }

    public Provincia mappingToDto(ProvinciaBd provinciaBd) {
        if (provinciaBd != null) {
            return new Provincia(provinciaBd.getId(), provinciaBd.getDescripcion());
        }
        return new Provincia();
    }

}
