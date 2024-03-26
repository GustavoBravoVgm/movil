package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.ICondicionVentaDao;
import com.ar.vgmsistemas.database.dao.entity.CondicionVentaBd;
import com.ar.vgmsistemas.entity.CondicionVenta;
import com.ar.vgmsistemas.repository.ICondicionVentaRepository;

import java.util.ArrayList;
import java.util.List;

public class CondicionVentaRepositoryImpl implements ICondicionVentaRepository {
    private ICondicionVentaDao _condicionVentaDao;

    public CondicionVentaRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._condicionVentaDao = db.condicionVentaDao();
        }
    }

    public String create(CondicionVenta condicionVenta) throws Exception {
        return null;
    }

    public List<CondicionVenta> recoveryAll() throws Exception {
        List<CondicionVentaBd> listadoCondicionesVentasBd = _condicionVentaDao.recoveryAll();
        List<CondicionVenta> condicionesVentas = new ArrayList<>();
        if (!listadoCondicionesVentasBd.isEmpty()) {
            for (CondicionVentaBd item : listadoCondicionesVentasBd) {
                condicionesVentas.add(mappingToDto(item));
            }
        }
        return condicionesVentas;
    }

    public CondicionVenta recoveryByID(String id) throws Exception {
        return mappingToDto(_condicionVentaDao.recoveryByID(id));
    }

    public void update(CondicionVenta condicionVenta) throws Exception {
    }

    public void delete(CondicionVenta entity) throws Exception {

    }

    public void delete(String id) throws Exception {

    }

    @Override
    public CondicionVenta mappingToDto(CondicionVentaBd condicionVentaBd) {
        if (condicionVentaBd != null) {
            return new CondicionVenta(condicionVentaBd.getId(), condicionVentaBd.getDescripcion(),
                    (condicionVentaBd.getSnControlFiado().equalsIgnoreCase(CondicionVenta.FIADO)),
                    condicionVentaBd.getDiasAtraso(), condicionVentaBd.getTasaDescuento(), condicionVentaBd.getNuCuotas());
        }
        return new CondicionVenta();
    }
}
