package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IRetencionDao;
import com.ar.vgmsistemas.database.dao.entity.RetencionBd;
import com.ar.vgmsistemas.database.dao.entity.key.PkVentaBd;
import com.ar.vgmsistemas.entity.Documento;
import com.ar.vgmsistemas.entity.Entrega;
import com.ar.vgmsistemas.entity.Retencion;
import com.ar.vgmsistemas.entity.key.PkVenta;
import com.ar.vgmsistemas.repository.IRetencionRepository;
import com.ar.vgmsistemas.utils.Formatter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class RetencionRepositoryImpl implements IRetencionRepository {
    private final AppDataBase _db;

    private IRetencionDao _retencionDao;

    public RetencionRepositoryImpl(AppDataBase db) {
        this._db = db;
        if (db != null) {
            this._retencionDao = db.retencionDao();
        }
    }

    public PkVenta create(Retencion retencion) throws Exception {
        _retencionDao.create(mappingToDb(retencion));
        return retencion.getId();
    }

    public boolean existeRetencion(Documento documento, Long numero, int idCliente) {
        int ret;
        try {
            ret = _retencionDao.existeRetencion(documento.getId().getIdDocumento(),
                    documento.getId().getIdLetra(), numero, idCliente);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return (ret > 0);
    }

    public Retencion recoveryByID(PkVenta id) throws Exception {
        return mappingToDto(_retencionDao.recoveryByID(id.getIdDocumento(), id.getIdLetra(), id.getPuntoVenta(),
                id.getIdNumeroDocumento()));
    }

    public List<Retencion> recoveryAll() throws Exception {
        return null;
    }

    public void update(Retencion entity) throws Exception {

    }

    public void delete(Retencion entity) throws Exception {

    }

    public void deleteByEntrega(Retencion entity) throws Exception {
        _retencionDao.deleteByEntrega(entity.getEntrega().getId());
    }

    public void delete(PkVenta id) throws Exception {

    }

    public List<Retencion> recoveryByEntrega(Entrega entrega) throws Exception {
        List<RetencionBd> listadoRetencionesBd = _retencionDao.recoveryByEntrega(entrega.getId());
        List<Retencion> retenciones = new ArrayList<>();
        if (!listadoRetencionesBd.isEmpty()) {
            for (RetencionBd item : listadoRetencionesBd) {
                retenciones.add(mappingToDto(item));
            }
        }
        return retenciones;
    }

    @Override
    public Retencion mappingToDto(RetencionBd retencionBd) throws Exception {
        Retencion retencion = new Retencion();
        if (retencionBd != null) {
            retencion.setId(new PkVenta(retencionBd.getId().getIdDocumento(),
                    retencionBd.getId().getIdLetra(), retencionBd.getId().getPuntoVenta(),
                    retencionBd.getId().getIdNumeroDocumento()));
            retencion.setObservacion(retencionBd.getObservacion());
            retencion.setImporte(retencionBd.getImporte());
            retencion.setFechaMovil(retencionBd.getFechaMovil() == null
                    ? null
                    : Formatter.convertToDate(retencionBd.getFechaMovil()));
            //cargo plan de cuenta
            PlanCuentaRepositoryImpl planCuentaRepository = new PlanCuentaRepositoryImpl(this._db);
            retencion.setPlanCuenta(planCuentaRepository.mappingToDto(this._db.planCuentaDao().recoveryByID(retencionBd.getIdPlanCtaRetencion())));
            //cargo entrega
            EntregaRepositoryImpl entregaRepository = new EntregaRepositoryImpl(this._db);
            retencion.setEntrega(entregaRepository.mappingToDto(this._db.entregaDao().recoveryByID(retencionBd.getIdEntrega())));
        }
        return retencion;
    }

    public RetencionBd mappingToDb(Retencion retencion) throws ParseException {
        if (retencion != null) {
            RetencionBd retencionBd = new RetencionBd();
            PkVentaBd id = new PkVentaBd();

            id.setIdDocumento(retencion.getId().getIdDocumento());
            id.setIdLetra(retencion.getId().getIdLetra());
            id.setPuntoVenta(retencion.getId().getPuntoVenta());
            id.setIdNumeroDocumento(retencion.getId().getIdNumeroDocumento());

            retencionBd.setId(id);
            retencionBd.setIdPlanCtaRetencion(retencion.getPlanCuenta().getId());
            retencionBd.setObservacion(retencion.getObservacion());
            retencionBd.setImporte(retencion.getImporte());
            retencionBd.setFechaMovil(retencion.getFechaMovil() == null
                    ? null
                    : Formatter.formatDateWs(retencion.getFechaMovil()));
            retencionBd.setIdEntrega(retencion.getEntrega().getId());
            return retencionBd;
        }
        return null;
    }
}
