package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IErrorMovilDao;
import com.ar.vgmsistemas.database.dao.entity.ErrorMovilBd;
import com.ar.vgmsistemas.entity.ErrorMovil;
import com.ar.vgmsistemas.repository.IErrorRepository;
import com.ar.vgmsistemas.utils.Formatter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ErrorMovilRepositoryImpl implements IErrorRepository {
    private IErrorMovilDao _errorMovilDao;

    public ErrorMovilRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._errorMovilDao = db.errorMovilDao();
        }
    }

    public Integer create(ErrorMovil error) throws Exception {
        _errorMovilDao.create(mappingToDb(error));
        return error.getId();
    }


    public ErrorMovil recoveryByID(Integer id) throws Exception {
        return null;
    }


    public List<ErrorMovil> recoveryAll() throws Exception {
        List<ErrorMovilBd> listadoErroresBd = _errorMovilDao.recoveryAll();
        List<ErrorMovil> errores = new ArrayList<>();
        if (!listadoErroresBd.isEmpty()) {
            for (ErrorMovilBd item : listadoErroresBd) {
                errores.add(mappingToDto(item));
            }
        }
        return errores;
    }


    public void update(ErrorMovil errorMovil) throws Exception {
        _errorMovilDao.update(mappingToDb(errorMovil));
    }


    public void delete(ErrorMovil entity) throws Exception {

    }


    public void delete(Integer id) throws Exception {

    }

    @Override
    public List<ErrorMovil> recoveryAEnviar() throws Exception {
        List<ErrorMovilBd> listadoErroresBd = _errorMovilDao.recoveryAEnviar();
        if (!listadoErroresBd.isEmpty()) {
            List<ErrorMovil> errores = new ArrayList<>();
            for (ErrorMovilBd item : listadoErroresBd) {
                errores.add(mappingToDto(item));
            }
            return errores;
        }
        return null;
    }

    public ErrorMovil mappingToDto(ErrorMovilBd errorMovilBd) throws ParseException {
        ErrorMovil errorMovil = new ErrorMovil();
        if (errorMovilBd != null) {

            errorMovil.setId(errorMovilBd.getId());
            errorMovil.setError(errorMovilBd.getError());
            errorMovil.setVersion(errorMovilBd.getVersion());
            errorMovil.setFechaRegistro(errorMovilBd.getFechaRegistro() == null
                    ? null
                    : Formatter.convertToDate(errorMovilBd.getFechaRegistro()));
            errorMovil.setIdVendedor(errorMovilBd.getIdVendedor());
            errorMovil.setIdMovil(errorMovilBd.getIdMovil());
        }
        return errorMovil;
    }

    private ErrorMovilBd mappingToDb(ErrorMovil errorMovil) {
        if (errorMovil != null) {
            ErrorMovilBd errorMovilBd = new ErrorMovilBd();

            errorMovilBd.setId(errorMovil.getId());
            errorMovilBd.setError(errorMovil.getError());
            errorMovilBd.setVersion(errorMovil.getVersion());
            errorMovilBd.setFechaRegistro(Formatter.formatDate(errorMovil.getFechaRegistro()));
            errorMovilBd.setIdVendedor(errorMovil.getIdVendedor());
            errorMovilBd.setIdMovil(errorMovil.getIdMovil());

            return errorMovilBd;
        }
        return null;

    }

}
