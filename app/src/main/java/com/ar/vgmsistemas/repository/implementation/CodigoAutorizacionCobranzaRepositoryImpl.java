package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.ICodigoAutorizacionCobranzaDao;
import com.ar.vgmsistemas.database.dao.entity.CodigoAutorizacionCobranzaBd;
import com.ar.vgmsistemas.database.dao.entity.key.PkCodigoAutCobranzaBd;
import com.ar.vgmsistemas.entity.CodigoAutorizacionCobranza;
import com.ar.vgmsistemas.entity.key.PkCodigoAutCobranza;
import com.ar.vgmsistemas.repository.ICodigoAutorizacionCobranzaRepository;

import java.util.List;


public class CodigoAutorizacionCobranzaRepositoryImpl implements ICodigoAutorizacionCobranzaRepository {
    private ICodigoAutorizacionCobranzaDao _codigoAutorizacionCobranzaDao;

    public CodigoAutorizacionCobranzaRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._codigoAutorizacionCobranzaDao = db.codigoAutorizacionCobranzaDao();
        }
    }

    public CodigoAutorizacionCobranza recoveryByCodigo(String codigo) throws Exception {
        return mappingToDto(_codigoAutorizacionCobranzaDao.recoveryByCodigo(codigo));
    }

    @Override
    public PkCodigoAutCobranza create(CodigoAutorizacionCobranza codigo) throws Exception {
        _codigoAutorizacionCobranzaDao.create(mappingToDb(codigo));
        return codigo.getId();
    }

    @Override
    public CodigoAutorizacionCobranza recoveryByID(PkCodigoAutCobranza id) throws Exception {
        return null;
    }

    @Override
    public List<CodigoAutorizacionCobranza> recoveryAll() throws Exception {
        return null;
    }

    @Override
    public void update(CodigoAutorizacionCobranza entity) throws Exception {
    }

    @Override
    public void delete(CodigoAutorizacionCobranza entity) throws Exception {
    }

    @Override
    public void delete(PkCodigoAutCobranza id) throws Exception {
    }

    @Override
    public CodigoAutorizacionCobranza mappingToDto(CodigoAutorizacionCobranzaBd codigoAutorizacionCobranzaBd) {
        if (codigoAutorizacionCobranzaBd != null) {
            PkCodigoAutCobranza id = new PkCodigoAutCobranza(codigoAutorizacionCobranzaBd.getId().getIdDocumento(),
                    codigoAutorizacionCobranzaBd.getId().getIdLetra(), codigoAutorizacionCobranzaBd.getId().getPuntoVenta(),
                    codigoAutorizacionCobranzaBd.getId().getIdNumeroDocumento());
            return new CodigoAutorizacionCobranza(id, codigoAutorizacionCobranzaBd.getCodigo());
        }
        return null;
    }

    public CodigoAutorizacionCobranzaBd mappingToDb(CodigoAutorizacionCobranza codigoAutorizacionCobranza) {
        PkCodigoAutCobranzaBd id = new PkCodigoAutCobranzaBd();
        id.setIdDocumento(codigoAutorizacionCobranza.getId().getIdDocumento());
        id.setIdLetra(codigoAutorizacionCobranza.getId().getIdLetra());
        id.setPuntoVenta(codigoAutorizacionCobranza.getId().getPuntoVenta());
        id.setIdNumeroDocumento(codigoAutorizacionCobranza.getId().getPuntoVenta());
        CodigoAutorizacionCobranzaBd codigo = new CodigoAutorizacionCobranzaBd();
        codigo.setId(id);
        codigo.setCodigo(codigoAutorizacionCobranza.getCodigo());
        return codigo;
    }
}
