package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IEntregaDao;
import com.ar.vgmsistemas.database.dao.entity.EntregaBd;
import com.ar.vgmsistemas.entity.Entrega;
import com.ar.vgmsistemas.repository.IEntregaRepository;

import java.util.List;

public class EntregaRepositoryImpl implements IEntregaRepository {
    private final AppDataBase _db;
    private IEntregaDao _entregaDao;

    public EntregaRepositoryImpl(AppDataBase db) {
        this._db = db;
        if (db != null) {
            this._entregaDao = db.entregaDao();
        }
    }

    public Integer create(Entrega entrega) throws Exception {
        int maxId = this._entregaDao.maxIdEntrega();
        entrega.setId(maxId + 1);
        this._entregaDao.create(mappingToDb(entrega));
        return entrega.getId();
    }

    public Entrega recoveryByID(Integer id) throws Exception {
        Entrega miEntrega = mappingToDto(this._entregaDao.recoveryByID(id));
        cargarListasEnEntregas(miEntrega);
        return miEntrega;
    }

    public List<Entrega> recoveryAll() throws Exception {
        return null;
    }

    public void update(Entrega entity) throws Exception {

    }

    @Override
    public void delete(Entrega entity) throws Exception {
        this._entregaDao.delete(this.mappingToDb(entity));
    }

    @Override
    public void delete(Integer id) throws Exception {
        this._entregaDao.delete(id);
    }

    @Override
    public Entrega mappingToDto(EntregaBd entregaBd) {
        return new Entrega(entregaBd.getId(), entregaBd.getPrEfectivoEntrega(), entregaBd.getPrChequesEntrega(),
                entregaBd.getPrRetencionesEntrega(), entregaBd.getPrDepositoEntrega());
    }

    @Override
    public EntregaBd mappingToDb(Entrega entrega) {
        EntregaBd entregaBd = new EntregaBd();
        entregaBd.setId(entrega.getId());
        entregaBd.setPrEfectivoEntrega(entrega.getPrEfectivoEntrega());
        entregaBd.setPrChequesEntrega(entrega.getPrChequesEntrega());
        entregaBd.setPrRetencionesEntrega(entrega.getPrRetencionesEntrega());
        entregaBd.setPrDepositoEntrega(entrega.getPrDepositoEntrega());
        return entregaBd;
    }

    private void cargarListasEnEntregas(Entrega entrega) throws Exception {
        PagoEfectivoRepositoryImpl pagoEfectivoRepo = new PagoEfectivoRepositoryImpl(_db);
        ChequeRepositoryImpl chequeRepo = new ChequeRepositoryImpl(_db);
        RetencionRepositoryImpl retencionRepo = new RetencionRepositoryImpl(_db);
        DepositoRepositoryImpl depositoRepo = new DepositoRepositoryImpl(_db);

        entrega.setPagosEfectivo(pagoEfectivoRepo.recoveryByEntrega(entrega));
        entrega.setCheques(chequeRepo.recoveryByEntrega(entrega));
        entrega.setRetenciones(retencionRepo.recoveryByEntrega(entrega));
        entrega.setDepositos(depositoRepo.recoveryByEntrega(entrega));
    }

}
