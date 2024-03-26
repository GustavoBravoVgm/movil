package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.ICondicionRentaDao;
import com.ar.vgmsistemas.database.dao.entity.CondicionRentaBd;
import com.ar.vgmsistemas.database.dao.entity.ProvinciaBd;
import com.ar.vgmsistemas.entity.CondicionRenta;
import com.ar.vgmsistemas.entity.Provincia;
import com.ar.vgmsistemas.entity.key.PkCliente;
import com.ar.vgmsistemas.repository.ICondicionRentaRepository;

import java.util.List;

public class CondicionRentaRepositoryImpl implements ICondicionRentaRepository {
    private final AppDataBase _db;
    private ICondicionRentaDao _condicionRentaDao;

    public CondicionRentaRepositoryImpl(AppDataBase db) {
        this._db = db;
        if (db != null) {
            this._condicionRentaDao = db.condicionRentaDao();
        }
    }


    public CondicionRenta recoveryByCliente(PkCliente id) throws Exception {
        return mappingToDto(_condicionRentaDao.recoveryByCliente(id.getIdSucursal(), id.getIdCliente(), id.getIdComercio()));
    }

    public Integer create(CondicionRenta condicionRenta) throws Exception {
        return null;
    }

    public List<CondicionRenta> recoveryAll() throws Exception {
        return null;
    }

    public CondicionRenta recoveryByID(Integer id) throws Exception {
        return null;
    }

    public void update(CondicionRenta condicionRenta) throws Exception {
    }

    public void delete(CondicionRenta entity) throws Exception {
    }

    public void delete(Integer id) throws Exception {
    }

    @Override
    public CondicionRenta mappingToDto(CondicionRentaBd condicionRentaBd) throws Exception {
        if (condicionRentaBd != null) {
            CondicionRenta condicionRenta = new CondicionRenta(condicionRentaBd.getId(),
                    condicionRentaBd.getDescripcion(), condicionRentaBd.getTasaDgr(), condicionRentaBd.getTipoCalculo(),
                    condicionRentaBd.getMontoMinimoImpuestoDgr(), condicionRentaBd.getSnAplicaANc(),
                    condicionRentaBd.getMontoMinimoARetener());
            //cargo provincia
            ProvinciaBd provBd = this._db.provinciaDao().recoveryByID(condicionRentaBd.getIdProvincia());
            condicionRenta.setProvincia(new Provincia(provBd.getId(), provBd.getDescripcion()));
            return condicionRenta;
        } else return new CondicionRenta();
    }
}
