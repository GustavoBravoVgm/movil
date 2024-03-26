package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IBancoDao;
import com.ar.vgmsistemas.database.dao.entity.BancoBd;
import com.ar.vgmsistemas.entity.Banco;
import com.ar.vgmsistemas.repository.IBancoRepository;

import java.util.ArrayList;
import java.util.List;

public class BancoRepositoryImpl implements IBancoRepository {
    private IBancoDao _bancoDao;

    public BancoRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._bancoDao = db.bancoDao();
        }
    }

    public Integer create(Banco entity) throws Exception {
        return null;
    }

    public Banco recoveryByID(Integer id) throws Exception {
        return null;
    }

    public List<Banco> recoveryAll() throws Exception {
        List<BancoBd> listadoBancoBd = _bancoDao.recoveryAll();
        List<Banco> bancos = new ArrayList<>();
        if (!listadoBancoBd.isEmpty()) {
            for (BancoBd item : listadoBancoBd) {
                bancos.add(mappingToDto(item));
            }
        }
        return bancos;
    }

    public void update(Banco entity) throws Exception {

    }

    public void delete(Banco entity) throws Exception {

    }

    public void delete(Integer id) throws Exception {

    }

    @Override
    public Banco mappingToDto(BancoBd bancoBd) {
        Banco banco = new Banco();
        if (bancoBd != null) {
            banco.setId(bancoBd.getId());
            banco.setDenominacion(bancoBd.getDenominacion());
        }
        return banco;
    }
}
