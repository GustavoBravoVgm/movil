package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IMotivoAutorizacionDao;
import com.ar.vgmsistemas.database.dao.entity.MotivoAutorizacionBd;
import com.ar.vgmsistemas.entity.MotivoAutorizacion;
import com.ar.vgmsistemas.repository.IMotivosAutorizacionRepository;

import java.util.ArrayList;
import java.util.List;

public class MotivoAutorizacionRepositoryImpl implements IMotivosAutorizacionRepository {

    private IMotivoAutorizacionDao _motivoAutorizacionDao;

    public MotivoAutorizacionRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._motivoAutorizacionDao = db.motivoAutorizacionDao();
        }
    }

    @Override
    public Long create(MotivoAutorizacion entity) throws Exception {
        return null;
    }

    @Override
    public MotivoAutorizacion recoveryByID(Long id) throws Exception {
        return null;
    }

    @Override
    public List<MotivoAutorizacion> recoveryForCtaCte() throws Exception {
        List<MotivoAutorizacionBd> listadoMotivosBd = _motivoAutorizacionDao.recoveryForCtaCte();
        List<MotivoAutorizacion> motivos = new ArrayList<>();
        if (!listadoMotivosBd.isEmpty()) {
            for (MotivoAutorizacionBd item : listadoMotivosBd) {
                motivos.add(mappingToDto(item));
            }
        }
        return motivos;
    }

    @Override
    public List<MotivoAutorizacion> recoveryForPedidoRentable() throws Exception {
        List<MotivoAutorizacionBd> listadoMotivosBd = _motivoAutorizacionDao.recoveryForPedidoRentable();
        List<MotivoAutorizacion> motivos = new ArrayList<>();
        if (!listadoMotivosBd.isEmpty()) {
            for (MotivoAutorizacionBd item : listadoMotivosBd) {
                motivos.add(mappingToDto(item));
            }
        }
        return motivos;
    }

    @Override
    public MotivoAutorizacion recoveryForPedidoRentableSinAutorizacion() throws Exception {
        return mappingToDto(_motivoAutorizacionDao.recoveryForPedidoRentableSinAutorizacion());
    }

    @Override
    public List<MotivoAutorizacion> recoveryAll() throws Exception {
        List<MotivoAutorizacionBd> listadoMotivosBd = _motivoAutorizacionDao.recoveryAll();
        List<MotivoAutorizacion> motivos = new ArrayList<>();
        if (!listadoMotivosBd.isEmpty()) {
            for (MotivoAutorizacionBd item : listadoMotivosBd) {
                motivos.add(mappingToDto(item));
            }
        }
        return motivos;
    }

    @Override
    public void update(MotivoAutorizacion entity) throws Exception {

    }

    @Override
    public void delete(MotivoAutorizacion entity) throws Exception {

    }

    @Override
    public void delete(Long id) throws Exception {

    }

    @Override
    public MotivoAutorizacion mappingToDto(MotivoAutorizacionBd motivoAutorizacionBd) {
        MotivoAutorizacion motivo = new MotivoAutorizacion();
        if (motivoAutorizacionBd != null) {

            motivo.setId(motivoAutorizacionBd.getId());
            motivo.setMotivo(motivoAutorizacionBd.getMotivo());
            motivo.setTiAutorizacion(motivoAutorizacionBd.getTiAutorizacion() == null
                    ? MotivoAutorizacion.TI_AUTORIZACION_CUENTA_CORRIENTE
                    : motivoAutorizacionBd.getTiAutorizacion());
        }
        return motivo;
    }

    private MotivoAutorizacionBd mappingToDb(MotivoAutorizacion motivoAutorizacion) {
        if (motivoAutorizacion != null) {
            MotivoAutorizacionBd motivo = new MotivoAutorizacionBd();

            motivo.setId(motivoAutorizacion.getId());
            motivo.setMotivo(motivoAutorizacion.getMotivo());
            motivo.setTiAutorizacion(motivoAutorizacion.getTiAutorizacion());

            return motivo;
        }
        return null;
    }
}
