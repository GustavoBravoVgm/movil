package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.ITipoMonedaDao;
import com.ar.vgmsistemas.database.dao.entity.TipoMonedaBd;
import com.ar.vgmsistemas.entity.TipoMoneda;
import com.ar.vgmsistemas.repository.ITipoMonedaRepository;

import java.util.ArrayList;
import java.util.List;

public class TipoMonedaRepositoryImpl implements ITipoMonedaRepository {
    private ITipoMonedaDao _tipoMonedaDao;

    public TipoMonedaRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._tipoMonedaDao = db.tipoMonedaDao();
        }
    }

    public Integer create(TipoMoneda entity) throws Exception {
        return null;
    }

    public TipoMoneda recoveryByID(Integer id) throws Exception {
        return null;
    }

    public List<TipoMoneda> recoveryAll() throws Exception {
        List<TipoMonedaBd> listadoTiposMonedasBd = _tipoMonedaDao.recoveryAll();
        List<TipoMoneda> tiposDeMonedas = new ArrayList<>();
        if (!listadoTiposMonedasBd.isEmpty()) {
            for (TipoMonedaBd item : listadoTiposMonedasBd) {
                tiposDeMonedas.add(this.mappingToDto(item));
            }
        }
        return tiposDeMonedas;
    }

    public void update(TipoMoneda entity) throws Exception {

    }

    public void delete(TipoMoneda entity) throws Exception {

    }

    public void delete(Integer id) throws Exception {

    }

    @Override
    public TipoMoneda mappingToDto(TipoMonedaBd tipoMonedaBd) {
        TipoMoneda tipoMoneda = new TipoMoneda();
        if (tipoMonedaBd != null) {
            tipoMoneda.setId(tipoMonedaBd.getId());
            tipoMoneda.setDescripcion(tipoMonedaBd.getDescripcion());
            tipoMoneda.setCotizacion(tipoMonedaBd.getCotizacion());
            tipoMoneda.setMonedaCurso(tipoMonedaBd.getSnMonedaCorriente() != null &&
                    tipoMonedaBd.getSnMonedaCorriente().equalsIgnoreCase("S"));
        }
        return tipoMoneda;
    }
}
