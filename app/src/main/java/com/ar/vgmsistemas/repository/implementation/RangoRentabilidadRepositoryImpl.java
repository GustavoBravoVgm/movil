package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IRangoRentabilidadDao;
import com.ar.vgmsistemas.database.dao.entity.RangoRentabilidadBd;
import com.ar.vgmsistemas.entity.RangoRentabilidad;
import com.ar.vgmsistemas.entity.key.PkCliente;
import com.ar.vgmsistemas.repository.IRangoRentabilidadRepository;

import java.util.ArrayList;
import java.util.List;

public class RangoRentabilidadRepositoryImpl implements IRangoRentabilidadRepository {
    private IRangoRentabilidadDao _rangoRentabilidadDao;

    public RangoRentabilidadRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._rangoRentabilidadDao = db.rangoRentabilidadDao();
        }
    }

    @Override
    public Integer create(RangoRentabilidad entity) throws Exception {
        return null;
    }

    @Override
    public RangoRentabilidad recoveryByID(Integer integer) throws Exception {
        return null;
    }

    public List<RangoRentabilidad> recoveryAll() throws Exception {
        List<RangoRentabilidadBd> listadoRangos = _rangoRentabilidadDao.recoveryAll();
        List<RangoRentabilidad> rangos = new ArrayList<>();
        if (!listadoRangos.isEmpty()) {
            for (RangoRentabilidadBd item : listadoRangos) {
                rangos.add(mappingToDto(item));
            }
        }
        return rangos;
    }

    @Override
    public void update(RangoRentabilidad entity) throws Exception {

    }

    @Override
    public void delete(RangoRentabilidad entity) throws Exception {

    }

    @Override
    public void delete(Integer integer) throws Exception {

    }

    @Override
    public List<Integer> recoveryProveedoresPR() throws Exception {
        return _rangoRentabilidadDao.recoveryProveedoresPR();
    }

    @Override
    public List<RangoRentabilidad> recoveryRangosByPIdProveedor(int idProveedor, PkCliente idCliente)
            throws Exception {
        List<RangoRentabilidadBd> listadoRangos = _rangoRentabilidadDao.recoveryRangosByPIdProveedor(idProveedor,
                idCliente.getIdSucursal(), idCliente.getIdCliente(), idCliente.getIdComercio());
        if (!listadoRangos.isEmpty()) {
            List<RangoRentabilidad> rangos = new ArrayList<>();
            for (RangoRentabilidadBd item : listadoRangos) {
                rangos.add(mappingToDto(item));
            }
            return rangos;
        }
        return null;
    }

    @Override
    public List<RangoRentabilidad> recoveryRangosBySucursal(int idSucursal, PkCliente idCliente)
            throws Exception {
        List<RangoRentabilidadBd> listadoRangos = _rangoRentabilidadDao.recoveryRangosBySucursal(idSucursal,
                idCliente.getIdSucursal(), idCliente.getIdCliente(), idCliente.getIdComercio());
        if (!listadoRangos.isEmpty()) {
            List<RangoRentabilidad> rangos = new ArrayList<>();
            for (RangoRentabilidadBd item : listadoRangos) {
                rangos.add(mappingToDto(item));
            }
            return rangos;
        }
        return null;
    }

    public RangoRentabilidad mappingToDto(RangoRentabilidadBd rangoRentabilidadBd) {
        RangoRentabilidad rango = new RangoRentabilidad();
        if (rangoRentabilidadBd != null) {

            rango.setId(rangoRentabilidadBd.getId());
            rango.setIdSucursal(rangoRentabilidadBd.getIdSucursal());
            rango.setIdProveedor(rangoRentabilidadBd.getIdProveedor());
            rango.setTaDesde(rangoRentabilidadBd.getTaDesde());
            rango.setTaHasta(rangoRentabilidadBd.getTaHasta());
            rango.setSnRentable(rangoRentabilidadBd.getSnRentable() == null
                    ? "N"
                    : rangoRentabilidadBd.getSnRentable());
            rango.setDescripcion(rangoRentabilidadBd.getDescripcion());
            rango.setIdGrupoClie(rangoRentabilidadBd.getIdGrupoClie());
        }
        return rango;
    }
}
