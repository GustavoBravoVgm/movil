package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IImpresoraDao;
import com.ar.vgmsistemas.database.dao.entity.ImpresoraBd;
import com.ar.vgmsistemas.entity.Impresora;
import com.ar.vgmsistemas.repository.IImpresoraRepository;

import java.util.ArrayList;
import java.util.List;

public class ImpresoraRepositoryImpl implements IImpresoraRepository {

    private IImpresoraDao _impresoraDao;

    public ImpresoraRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._impresoraDao = db.impresoraDao();
        }
    }

    @Override
    public Integer create(Impresora entity) throws Exception {
        return null;
    }

    @Override
    public Impresora recoveryByID(Integer id) throws Exception {
        return mappingToDto(_impresoraDao.recoveryByID(id));
    }

    @Override
    public List<Impresora> recoveryAll() throws Exception {
        List<ImpresoraBd> listadoImpresorasBd = _impresoraDao.recoveryAll();
        List<Impresora> impresoras = new ArrayList<>();
        if (!listadoImpresorasBd.isEmpty()) {
            for (ImpresoraBd item : listadoImpresorasBd) {
                impresoras.add(mappingToDto(item));
            }
        }
        return impresoras;
    }

    @Override
    public void update(Impresora entity) throws Exception {

    }

    @Override
    public void delete(Impresora entity) throws Exception {

    }

    @Override
    public void delete(Integer integer) throws Exception {

    }

    public Impresora mappingToDto(ImpresoraBd impresoraBd) {
        Impresora impresora = new Impresora();
        if (impresoraBd != null) {
            impresora.setId(impresoraBd.getId());
            impresora.setDescripcion(impresoraBd.getDescripcion());
            impresora.setTamanioHoja(impresoraBd.getTamanioHoja());

        }
        return impresora;
    }
}
