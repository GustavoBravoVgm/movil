package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.ICondicionDirscDao;
import com.ar.vgmsistemas.database.dao.entity.CondicionDirscBd;
import com.ar.vgmsistemas.entity.CondicionDirsc;
import com.ar.vgmsistemas.entity.key.PkCliente;
import com.ar.vgmsistemas.repository.ICondicionDirscRepository;

import java.util.List;


public class CondicionDirscRepositoryImpl implements ICondicionDirscRepository {
    private final AppDataBase _db;

    private ICondicionDirscDao _condicionDirscDao;

    public CondicionDirscRepositoryImpl(AppDataBase db) {
        this._db = db;
        if (db != null) {
            this._condicionDirscDao = db.condicionDirscDao();
        }
    }

    public CondicionDirsc recoveryByCliente(PkCliente id) throws Exception {
        return mappingToDto(_condicionDirscDao.recoveryByCliente(id.getIdSucursal(), id.getIdCliente(), id.getIdComercio()));
    }

    public Integer create(CondicionDirsc entity) throws Exception {
        return null;
    }

    public void delete(CondicionDirsc entity) throws Exception {

    }

    public void delete(Integer id) throws Exception {

    }

    public List<CondicionDirsc> recoveryAll() throws Exception {
        return null;
    }

    public CondicionDirsc recoveryByID(Integer id) throws Exception {
        return null;
    }

    public void update(CondicionDirsc entity) throws Exception {

    }

    @Override
    public CondicionDirsc mappingToDto(CondicionDirscBd condicionDirscBd) throws Exception {
        if (condicionDirscBd != null) {
            CondicionDirsc condicionDirsc = new CondicionDirsc(condicionDirscBd.getId(),
                    condicionDirscBd.getDescripcion(),
                    condicionDirscBd.getTasaImpuesto());
            //cargo provincia
            ProvinciaRepositoryImpl provinciaRepository = new ProvinciaRepositoryImpl(_db);
            condicionDirsc.setProvincia(provinciaRepository.mappingToDto(this._db.provinciaDao().recoveryByID(condicionDirscBd.getIdProvincia())));
            return condicionDirsc;
        }
        return new CondicionDirsc();
    }
}
