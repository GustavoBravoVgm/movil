package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.ISubrubroDao;
import com.ar.vgmsistemas.database.dao.entity.NegocioBd;
import com.ar.vgmsistemas.database.dao.entity.RubroBd;
import com.ar.vgmsistemas.database.dao.entity.SubrubroBd;
import com.ar.vgmsistemas.entity.Negocio;
import com.ar.vgmsistemas.entity.Rubro;
import com.ar.vgmsistemas.entity.Subrubro;
import com.ar.vgmsistemas.entity.key.PkRubro;
import com.ar.vgmsistemas.entity.key.PkSubrubro;
import com.ar.vgmsistemas.repository.ISubrubroRepository;

import java.util.ArrayList;
import java.util.List;

public class SubrubroRepositoryImpl implements ISubrubroRepository {
    private final AppDataBase _db;

    private ISubrubroDao _subrubroDao;

    public SubrubroRepositoryImpl(AppDataBase db) {
        this._db = db;
        if (db != null) {
            this._subrubroDao = db.subrubroDao();
        }
    }

    public PkSubrubro create(Subrubro entity) throws Exception {
        return null;
    }

    public void delete(Subrubro entity) throws Exception {

    }

    public void delete(PkSubrubro id) throws Exception {

    }

    public List<Subrubro> recoveryAll() throws Exception {
        List<SubrubroBd> listadoSubrubroBd = _subrubroDao.recoveryAll();
        List<Subrubro> subrubros = new ArrayList<>();
        if (!listadoSubrubroBd.isEmpty()) {
            for (SubrubroBd item : listadoSubrubroBd) {
                subrubros.add(mappingToDto(item));
            }
        }
        return subrubros;
    }

    public Subrubro recoveryByID(PkSubrubro id) throws Exception {
        return mappingToDto(_subrubroDao.recoveryByID(id.getIdNegocio(), id.getIdRubro(), id.getIdSubrubro()));
    }

    public List<Subrubro> recoveryByRubro(Rubro rubro) throws Exception {
        List<SubrubroBd> listadoSubrubroBd = _subrubroDao.recoveryByRubro(rubro.getId().getIdNegocio(),
                rubro.getId().getIdRubro());
        List<Subrubro> subrubros = new ArrayList<>();
        if (!listadoSubrubroBd.isEmpty()) {
            for (SubrubroBd item : listadoSubrubroBd) {
                subrubros.add(mappingToDto(item));
            }
        }
        return subrubros;
    }

    public void update(Subrubro entity) throws Exception {

    }

    public Subrubro mappingToDto(SubrubroBd subrubroBd) throws Exception {
        Subrubro subrubro = new Subrubro();
        if (subrubroBd != null) {
            PkSubrubro idSubrubro = new PkSubrubro(subrubroBd.getId().getIdSubrubro(), subrubroBd.getId().getIdNegocio(),
                    subrubroBd.getId().getIdRubro());
            subrubro.setId(idSubrubro);
            subrubro.setDescripcion(subrubroBd.getDescripcion());
            //cargo rubro
            PkRubro idRubro = new PkRubro(subrubroBd.getId().getIdRubro(), subrubroBd.getId().getIdNegocio());
            RubroBd rubroBd = this._db.rubroDao().recoveryByID(subrubroBd.getId().getIdRubro(), subrubroBd.getId().getIdNegocio());
            Rubro rubro = new Rubro();
            if (rubroBd != null) {
                rubro.setId(idRubro);
                rubro.setDescripcion(subrubroBd.getDescripcion());
                //cargo negocio
                NegocioBd negocioBd = this._db.negocioDao().recoveryById(subrubroBd.getId().getIdNegocio());
                Negocio negocio = new Negocio();
                if (negocioBd != null) {
                    negocio.setId(negocioBd.getId());
                    negocio.setDescripcion(negocioBd.getDescripcion());
                }
                rubro.setNegocio(negocio);
            }
            subrubro.setRubro(rubro);
        }
        return subrubro;
    }

    @Override
    public Subrubro mappingToDtoSinRubro(SubrubroBd subrubroBd) {
        if (subrubroBd != null) {
            PkSubrubro idSubrubro = new PkSubrubro(subrubroBd.getId().getIdSubrubro(), subrubroBd.getId().getIdNegocio(),
                    subrubroBd.getId().getIdRubro());
            Subrubro subrubro = new Subrubro();
            subrubro.setId(idSubrubro);
            subrubro.setDescripcion(subrubroBd.getDescripcion());
            return subrubro;
        }
        return null;
    }

}
