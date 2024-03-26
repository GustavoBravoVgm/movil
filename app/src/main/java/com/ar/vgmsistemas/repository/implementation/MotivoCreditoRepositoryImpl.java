package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IMotivoCreditoDao;
import com.ar.vgmsistemas.database.dao.entity.MotivoCreditoBd;
import com.ar.vgmsistemas.entity.MotivoCredito;
import com.ar.vgmsistemas.repository.IMotivoCreditoRepository;

import java.util.ArrayList;
import java.util.List;

public class MotivoCreditoRepositoryImpl implements IMotivoCreditoRepository {
    private IMotivoCreditoDao _motivoCreditoDao;

    public MotivoCreditoRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._motivoCreditoDao = db.motivoCreditoDao();
        }
    }

    @Override
    public Integer create(MotivoCredito entity) throws Exception {
        return null;
    }

    @Override
    public MotivoCredito recoveryByID(Integer id) throws Exception {
        return null;
    }

    @Override
    public List<MotivoCredito> recoveryAll() throws Exception {
        List<MotivoCreditoBd> listadosMotivosBd = _motivoCreditoDao.recoveryAll();
        List<MotivoCredito> motivos = new ArrayList<>();
        if (!listadosMotivosBd.isEmpty()) {
            for (MotivoCreditoBd item : listadosMotivosBd) {
                motivos.add(mappingToDto(item));
            }
        }
        return motivos;
    }

    @Override
    public void update(MotivoCredito entity) throws Exception {
    }

    @Override
    public void delete(MotivoCredito entity) throws Exception {
    }

    @Override
    public void delete(Integer id) throws Exception {
    }

    public MotivoCredito mappingToDto(MotivoCreditoBd motivoCreditoBd) {
        MotivoCredito motivo = new MotivoCredito();
        if (motivoCreditoBd != null) {

            motivo.setIdMotivoRechazoNC(motivoCreditoBd.getIdMotivoRechazoNC());
            motivo.setDescripcion(motivoCreditoBd.getDescripcion());
        }
        return motivo;
    }
}
