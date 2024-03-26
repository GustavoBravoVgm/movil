package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.ICategoriaFiscalDao;
import com.ar.vgmsistemas.database.dao.entity.CategoriaFiscalBd;
import com.ar.vgmsistemas.entity.CategoriaFiscal;
import com.ar.vgmsistemas.repository.ICategoriaFiscalRepository;
import com.ar.vgmsistemas.utils.Formatter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaFiscalRepositoryImpl implements ICategoriaFiscalRepository {
    // DAO
    private ICategoriaFiscalDao _categoriaFiscalDao;

    public CategoriaFiscalRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._categoriaFiscalDao = db.categoriaFiscalDao();
        }
    }

    public String create(CategoriaFiscal categoria) throws Exception {
        return null;
    }

    public void delete(CategoriaFiscal entity) throws Exception {
    }

    public void delete(String id) throws Exception {

    }

    public List<CategoriaFiscal> recoveryAll() throws Exception {
        List<CategoriaFiscalBd> listadoCategoriasFiscalBd = _categoriaFiscalDao.recoveryAll();
        List<CategoriaFiscal> categorias = new ArrayList<>();
        if (!listadoCategoriasFiscalBd.isEmpty()) {
            for (CategoriaFiscalBd item : listadoCategoriasFiscalBd) {
                categorias.add(mappingToDto(item));
            }
        }
        return categorias;
    }

    public CategoriaFiscal recoveryByID(String id) throws Exception {
        return mappingToDto(_categoriaFiscalDao.recoveryByID(id));
    }

    public void update(CategoriaFiscal categoriaFiscal) throws Exception {
    }

    @Override
    public CategoriaFiscal mappingToDto(CategoriaFiscalBd categoriaFiscalBd) throws ParseException {
        CategoriaFiscal categoriaFiscal = new CategoriaFiscal();
        if (categoriaFiscalBd != null) {
            categoriaFiscal.setId(categoriaFiscalBd.getId());
            categoriaFiscal.setDescripcion(categoriaFiscalBd.getDescripcion() == null ? "" : categoriaFiscalBd.getDescripcion());
            categoriaFiscal.setSnBn(categoriaFiscalBd.getSnBn() == null ? "N" : categoriaFiscalBd.getSnBn());
            categoriaFiscal.setTipoComprobanteLetra(categoriaFiscalBd.getTipoComprobanteLetra());
            categoriaFiscal.setFechaVigenciaLetra((categoriaFiscalBd.getFechaVigenciaLetra() == null ||
                    categoriaFiscalBd.getFechaVigenciaLetra().equalsIgnoreCase(""))
                    ? null
                    : Formatter.convertToDate(categoriaFiscalBd.getFechaVigenciaLetra()));
        }
        return categoriaFiscal;
    }
}
