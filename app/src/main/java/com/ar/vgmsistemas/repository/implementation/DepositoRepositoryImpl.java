package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IDepositoDao;
import com.ar.vgmsistemas.database.dao.entity.DepositoBd;
import com.ar.vgmsistemas.entity.Deposito;
import com.ar.vgmsistemas.entity.Entrega;
import com.ar.vgmsistemas.repository.IDepositoRepository;
import com.ar.vgmsistemas.utils.Formatter;

import java.util.ArrayList;
import java.util.List;

public class DepositoRepositoryImpl implements IDepositoRepository {
    private final AppDataBase _db;
    private IDepositoDao _depositoDao;

    public DepositoRepositoryImpl(AppDataBase db) {
        this._db = db;
        if (db != null) {
            this._depositoDao = db.depositoDao();
        }
    }

    @Override
    public Long create(Deposito deposito) throws Exception {
        return _depositoDao.create(mappingToDb(deposito));
    }

    @Override
    public Deposito recoveryByID(Long aLong) throws Exception {
        return null;
    }

    @Override
    public List<Deposito> recoveryByEntrega(Entrega entrega) throws Exception {
        List<DepositoBd> listadoDepositosBd = _depositoDao.recoveryByEntrega(entrega.getId());
        List<Deposito> depositos = new ArrayList<>();
        if (!listadoDepositosBd.isEmpty()) {
            for (DepositoBd item : listadoDepositosBd) {
                depositos.add(mappingToDto(item));
            }
        }
        return depositos;
    }

    @Override
    public List<Deposito> recoveryAll() throws Exception {

        return null;
    }

    @Override
    public void update(Deposito entity) throws Exception {
    }

    @Override
    public void delete(Deposito entity) throws Exception {
    }

    @Override
    public void delete(Long aLong) throws Exception {

    }

    public void deleteByEntrega(Deposito entity) throws Exception {
        _depositoDao.deleteByEntrega(entity.getEntrega().getId());
    }

    public Deposito mappingToDto(DepositoBd depositoBd) throws Exception {
        Deposito deposito = new Deposito();
        if (depositoBd != null) {
            deposito.setId(depositoBd.getId());
            deposito.setFechaDepositoMovil((depositoBd.getFechaDepositoMovil() == null || depositoBd.getFechaDepositoMovil().equalsIgnoreCase(""))
                    ? null
                    : Formatter.convertToDate(depositoBd.getFechaDepositoMovil()));
            deposito.setNumeroComprobante(depositoBd.getNumeroComprobante());
            deposito.setImporte(depositoBd.getImporte());
            deposito.setCantidadCheques(depositoBd.getCantidadCheques());
            //cargo banco
            BancoRepositoryImpl bancoRepository = new BancoRepositoryImpl(_db);
            deposito.setBanco(bancoRepository.mappingToDto(this._db.bancoDao().recoveryByID(depositoBd.getIdBancoGirado())));
            //cargo entrega
            EntregaRepositoryImpl entregaRepository = new EntregaRepositoryImpl(_db);
            deposito.setEntrega(entregaRepository.mappingToDto(this._db.entregaDao().recoveryByID(depositoBd.getIdEntrega())));
        }
        return deposito;
    }

    public DepositoBd mappingToDb(Deposito deposito) throws Exception {
        if (deposito != null) {
            DepositoBd depositoBd = new DepositoBd();

            depositoBd.setId(deposito.getId());
            depositoBd.setFechaDepositoMovil(Formatter.formatJulianDate(deposito.getFechaDepositoMovil()));
            depositoBd.setNumeroComprobante(deposito.getNumeroComprobante());
            depositoBd.setImporte(deposito.getImporte());
            depositoBd.setCantidadCheques(deposito.getCantidadCheques());
            depositoBd.setIdBancoGirado(deposito.getBanco().getId());
            depositoBd.setIdEntrega(deposito.getEntrega().getId());
            return depositoBd;
        }
        return null;
    }
}
