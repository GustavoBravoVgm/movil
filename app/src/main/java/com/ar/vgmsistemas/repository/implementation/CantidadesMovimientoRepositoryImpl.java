package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.ICantidadesMovimientosDao;
import com.ar.vgmsistemas.database.dao.entity.view.CantidadMovimientosViewBd;
import com.ar.vgmsistemas.entity.CantidadesMovimiento;
import com.ar.vgmsistemas.repository.ICantidadesMovimientoRepository;

import java.util.ArrayList;
import java.util.List;

public class CantidadesMovimientoRepositoryImpl implements ICantidadesMovimientoRepository {

    private ICantidadesMovimientosDao _cantidadesMovimientosDao;

    public CantidadesMovimientoRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._cantidadesMovimientosDao = db.cantidadesMovimientosDao();
        }
    }

    @Override
    public Integer create(CantidadesMovimiento entity) throws Exception {
        return null;
    }

    @Override
    public CantidadesMovimiento recoveryByID(Integer id) throws Exception {
        return null;
    }

    @Override
    public List<CantidadesMovimiento> recoveryAll() throws Exception {
        List<CantidadMovimientosViewBd> listadoCantMov = _cantidadesMovimientosDao.recoveryAll();
        List<CantidadesMovimiento> movimientos = new ArrayList<>();
        if (!listadoCantMov.isEmpty()) {
            for (CantidadMovimientosViewBd item : listadoCantMov) {
                movimientos.add(mappingToDto(item));
            }
        }
        return movimientos;
    }

    @Override
    public void update(CantidadesMovimiento entity) throws Exception {
    }

    @Override
    public void delete(CantidadesMovimiento entity) throws Exception {

    }

    @Override
    public void delete(Integer id) throws Exception {
    }

    @Override
    public int getCantidadNoAtenciones(int idSucursal, int idCliente, int idComercio) throws Exception {
        return _cantidadesMovimientosDao.getCantidadNoAtenciones(idSucursal, idCliente, idComercio);
    }

    @Override
    public int getCantidadPedidos(int idSucursal, int idCliente, int idComercio) throws Exception {
        return _cantidadesMovimientosDao.getCantidadPedidos(idSucursal, idCliente, idComercio);
    }

    @Override
    public CantidadesMovimiento mappingToDto(CantidadMovimientosViewBd cantidadesMovimientoViewBd) {
        return new CantidadesMovimiento(cantidadesMovimientoViewBd.getIdSucursal(), cantidadesMovimientoViewBd.getIdCliente(),
                cantidadesMovimientoViewBd.getIdComercio(), cantidadesMovimientoViewBd.getCaPedidos(),
                cantidadesMovimientoViewBd.getCaNoAtencion());
    }
}
