package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IImpuestoDao;
import com.ar.vgmsistemas.database.dao.entity.ImpuestoBd;
import com.ar.vgmsistemas.entity.Impuesto;
import com.ar.vgmsistemas.repository.IImpuestoRepository;

import java.util.ArrayList;
import java.util.List;

public class ImpuestoRepositoryImpl implements IImpuestoRepository {

    private IImpuestoDao _impuestoDao;

    public ImpuestoRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._impuestoDao = db.impuestoDao();
        }
    }

    @Override
    public Integer create(Impuesto entity) throws Exception {
        return null;
    }

    @Override
    public Impuesto recoveryByID(Integer id) throws Exception {
        return mappingToDto(_impuestoDao.recoveryByID(id));
    }

    @Override
    public List<Impuesto> recoveryAll() throws Exception {
        List<ImpuestoBd> listadoImpuestosBd = _impuestoDao.recoveryAll();
        List<Impuesto> impuestos = new ArrayList<>();
        if (!listadoImpuestosBd.isEmpty()) {
            for (ImpuestoBd item : listadoImpuestosBd) {
                impuestos.add(mappingToDto(item));
            }
        }
        return impuestos;
    }

    @Override
    public void update(Impuesto entity) throws Exception {

    }

    @Override
    public void delete(Impuesto entity) throws Exception {

    }

    @Override
    public void delete(Integer id) throws Exception {
    }

    @Override
    public Impuesto mappingToDto(ImpuestoBd impuestoBd) {
        Impuesto impuesto = new Impuesto();
        if (impuestoBd != null) {

            impuesto.setId(impuestoBd.getId());
            impuesto.setDescripcion(impuestoBd.getDescripcion());
            impuesto.setTiImpuesto(impuestoBd.getTiImpuesto());
            impuesto.setTaImpuesto(impuestoBd.getTaImpuesto() == null ? 0d : impuestoBd.getTaImpuesto());
        }
        return impuesto;
    }
}
