package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IMarcaDao;
import com.ar.vgmsistemas.database.dao.entity.MarcaBd;
import com.ar.vgmsistemas.entity.Marca;
import com.ar.vgmsistemas.repository.IMarcaRepository;

import java.util.ArrayList;
import java.util.List;


public class MarcaRepositoryImpl implements IMarcaRepository {
    private IMarcaDao _marcaDao;

    public MarcaRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._marcaDao = db.marcaDao();
        }
    }

    public Integer create(Marca marca) throws Exception {
        return null;
    }

    public void delete(Marca entity) throws Exception {

    }

    public void delete(Integer id) throws Exception {

    }

    public List<Marca> recoveryAll() throws Exception {
        List<MarcaBd> listadoMarcasBd = _marcaDao.recoveryAll();
        List<Marca> marcas = new ArrayList<>();
        if (!listadoMarcasBd.isEmpty()) {
            for (MarcaBd item : listadoMarcasBd) {
                marcas.add(mappingToDto(item));
            }
        }
        return marcas;
    }

    public Marca recoveryByID(Integer id) throws Exception {
        return mappingToDto(_marcaDao.recoveryByID(id));
    }

    public void update(Marca marca) throws Exception {
    }

    @Override
    public Marca mappingToDto(MarcaBd marcaBd) {
        return new Marca(marcaBd.getId(), marcaBd.getDescripcion());
    }
}
