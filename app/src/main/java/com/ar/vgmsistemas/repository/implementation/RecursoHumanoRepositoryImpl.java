package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IRecursoHumanoDao;
import com.ar.vgmsistemas.database.dao.entity.RecursoHumanoBd;
import com.ar.vgmsistemas.entity.RecursoHumano;
import com.ar.vgmsistemas.repository.IRecursoHumanoRepository;
import com.ar.vgmsistemas.utils.Formatter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class RecursoHumanoRepositoryImpl implements IRecursoHumanoRepository {

    private IRecursoHumanoDao _recursoHumanoDao;

    public RecursoHumanoRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._recursoHumanoDao = db.recursoHumanoDao();
        }
    }

    public Integer create(RecursoHumano recursoHumano) throws Exception {
        return null;
    }

    public void delete(RecursoHumano entity) throws Exception {
    }

    public void delete(Integer id) throws Exception {
    }

    public List<RecursoHumano> recoveryAll() throws Exception {
        List<RecursoHumanoBd> listadoRrHh = _recursoHumanoDao.recoveryAll();
        List<RecursoHumano> recursoHumanos = new ArrayList<>();
        if (!listadoRrHh.isEmpty()) {
            for (RecursoHumanoBd item : listadoRrHh) {
                recursoHumanos.add(mappingToDto(item));
            }
        }
        return recursoHumanos;
    }

    public RecursoHumano recoveryByID(Integer id) throws Exception {
        return mappingToDto(_recursoHumanoDao.recoveryByID(id));
    }

    public void update(RecursoHumano entity) throws Exception {

    }

    @Override
    public RecursoHumano mappingToDto(RecursoHumanoBd recursoHumanoBd) throws ParseException {
        RecursoHumano recursoHumano = new RecursoHumano();
        if (recursoHumanoBd != null) {

            recursoHumano.setId(recursoHumanoBd.getId());
            recursoHumano.setApellido(recursoHumanoBd.getApellido());
            recursoHumano.setNombre(recursoHumanoBd.getNombre());
            recursoHumano.setDomicilio(recursoHumanoBd.getDomicilio());
            recursoHumano.setNumeroDocumento(recursoHumanoBd.getNumeroDocumento());
            recursoHumano.setPassword(recursoHumanoBd.getPassword());
            recursoHumano.setHoraEntradaManana(recursoHumanoBd.getHoraEntradaManana() == null
                    ? null
                    : Formatter.convertToDateTimeTwo(recursoHumanoBd.getHoraEntradaManana()));
            recursoHumano.setHoraSalidaManana(recursoHumanoBd.getHoraSalidaManana() == null
                    ? null
                    : Formatter.convertToDateTimeTwo(recursoHumanoBd.getHoraSalidaManana()));
            recursoHumano.setHoraEntradaTarde(recursoHumanoBd.getHoraEntradaTarde() == null
                    ? null
                    : Formatter.convertToDateTimeTwo(recursoHumanoBd.getHoraEntradaTarde()));
            recursoHumano.setHoraSalidaTarde(recursoHumanoBd.getHoraSalidaTarde() == null
                    ? null
                    : Formatter.convertToDateTimeTwo(recursoHumanoBd.getHoraSalidaTarde()));
            recursoHumano.setIsActivo(recursoHumanoBd.getSnActivo() != null &&
                    recursoHumanoBd.getSnActivo().equalsIgnoreCase("S"));
            recursoHumano.setCategoria(recursoHumanoBd.getCategoria());
        }
        return recursoHumano;

    }

}
