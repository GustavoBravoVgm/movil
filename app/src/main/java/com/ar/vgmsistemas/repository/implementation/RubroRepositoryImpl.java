package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IRubroDao;
import com.ar.vgmsistemas.database.dao.entity.NegocioBd;
import com.ar.vgmsistemas.database.dao.entity.RubroBd;
import com.ar.vgmsistemas.entity.Negocio;
import com.ar.vgmsistemas.entity.Rubro;
import com.ar.vgmsistemas.entity.key.PkRubro;
import com.ar.vgmsistemas.repository.IRubroRepository;

import java.util.ArrayList;
import java.util.List;

public class RubroRepositoryImpl implements IRubroRepository {
    private final AppDataBase _db;
    private IRubroDao _rubroDao;

    public RubroRepositoryImpl(AppDataBase db) {
        this._db = db;
        if (db != null) {
            this._rubroDao = db.rubroDao();
        }
    }

    public PkRubro create(Rubro entity) throws Exception {
        return null;
    }

    public void delete(Rubro entity) throws Exception {

    }

    public void delete(PkRubro id) throws Exception {

    }

    public List<Rubro> recoveryAll() throws Exception {
        List<RubroBd> listadoRubroBd = _rubroDao.recoveryAll();
        List<Rubro> rubros = new ArrayList<>();
        if (!listadoRubroBd.isEmpty()) {
            for (RubroBd item : listadoRubroBd) {
                rubros.add(mappingToDto(item));
            }
        }
        return rubros;
    }

    public Rubro recoveryByID(PkRubro id) throws Exception {
        return mappingToDto(_rubroDao.recoveryByID(id.getIdNegocio(), id.getIdRubro()));
    }

    public void update(Rubro entity) throws Exception {

    }

    @Override
    public Rubro mappingToDto(RubroBd rubroBd) throws Exception {
        Rubro rubro = new Rubro();
        if (rubroBd != null) {
            PkRubro idRubro = new PkRubro(rubroBd.getId().getIdRubro(), rubroBd.getId().getIdNegocio());
            rubro.setId(idRubro);
            rubro.setDescripcion(rubroBd.getDescripcion());
            //cargo negocio
            NegocioBd negocioBd = this._db.negocioDao().recoveryById(rubroBd.getId().getIdNegocio());
            Negocio negocio = new Negocio();
            if (negocioBd != null) {
                negocio.setId(negocioBd.getId());
                negocio.setDescripcion(negocioBd.getDescripcion());
            }
            rubro.setNegocio(negocio);
        }
        return rubro;
    }

    @Override
    public Rubro mappingToDtoSinNegocio(RubroBd rubroBd) {
        if (rubroBd != null) {
            PkRubro idRubro = new PkRubro(rubroBd.getId().getIdRubro(), rubroBd.getId().getIdNegocio());
            Rubro rubro = new Rubro();
            rubro.setId(idRubro);
            rubro.setDescripcion(rubroBd.getDescripcion());
            return rubro;
        }
        return null;
    }

}
