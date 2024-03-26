package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IComercioLoginDao;
import com.ar.vgmsistemas.database.dao.entity.ComercioLoginBd;
import com.ar.vgmsistemas.entity.ComercioLogin;
import com.ar.vgmsistemas.entity.key.PkCliente;
import com.ar.vgmsistemas.entity.key.PkComercioLogin;
import com.ar.vgmsistemas.repository.IComercioLoginRepository;
import com.ar.vgmsistemas.utils.Formatter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ComercioLoginRepositoryImpl implements IComercioLoginRepository {
    private IComercioLoginDao _comercioLoginDao;

    public ComercioLoginRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._comercioLoginDao = db.comercioLoginDao();
        }
    }

    @Override
    public ComercioLogin recoveryByCliente(PkCliente pkCliente) throws Exception {
        return mappingToDto(_comercioLoginDao.recoveryByCliente(pkCliente.getIdSucursal(), pkCliente.getIdCliente(),
                pkCliente.getIdComercio()));
    }

    @Override
    public PkComercioLogin create(ComercioLogin entity) throws Exception {
        return null;
    }

    @Override
    public ComercioLogin recoveryByID(PkComercioLogin pkComercioLogin) throws Exception {
        return null;
    }

    public List<ComercioLogin> recoveryAll() throws Exception {
        List<ComercioLoginBd> listadoComercioLoginBd = _comercioLoginDao.recoveryAll();
        List<ComercioLogin> comerciosLogin = new ArrayList<>();
        if (!listadoComercioLoginBd.isEmpty()) {
            for (ComercioLoginBd item : listadoComercioLoginBd) {
                comerciosLogin.add(mappingToDto(item));
            }
        }
        return comerciosLogin;
    }

    @Override
    public void update(ComercioLogin entity) throws Exception {

    }

    @Override
    public void delete(ComercioLogin entity) throws Exception {

    }

    @Override
    public void delete(PkComercioLogin pkComercioLogin) throws Exception {

    }

    public void delete(PkCliente id) throws Exception {

    }

    @Override
    public ComercioLogin mappingToDto(ComercioLoginBd comercioLoginBd) throws ParseException {
        ComercioLogin comercioLogin = new ComercioLogin();
        if (comercioLoginBd != null) {
            PkComercioLogin id = new PkComercioLogin(comercioLoginBd.getId().getIdSucursal(),
                    comercioLoginBd.getId().getIdCliente(), comercioLoginBd.getId().getIdComercio());

            comercioLogin.setMiId(comercioLoginBd.getMiId());
            comercioLogin.setId(id);
            comercioLogin.setUsuario(comercioLoginBd.getUsuario() == null ? "" : comercioLoginBd.getUsuario());
            comercioLogin.setPassword(comercioLoginBd.getPassword() == null ? "" : comercioLoginBd.getPassword());
            comercioLogin.setFechaCreacion((comercioLoginBd.getFechaCreacion() == null ||
                    comercioLoginBd.getFechaCreacion().equalsIgnoreCase(""))
                    ? null :
                    Formatter.convertToDate(comercioLoginBd.getFechaCreacion()));
            comercioLogin.setTipoLogin(comercioLoginBd.getTipoLogin() == null ? "" : comercioLoginBd.getTipoLogin());
        }
        return comercioLogin;
    }
}

