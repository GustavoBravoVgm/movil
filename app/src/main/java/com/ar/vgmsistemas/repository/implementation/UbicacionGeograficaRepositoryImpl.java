package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IUbicacionGeograficaDao;
import com.ar.vgmsistemas.database.dao.entity.UbicacionGeograficaBd;
import com.ar.vgmsistemas.entity.UbicacionGeografica;
import com.ar.vgmsistemas.repository.IUbicacionGeograficaRepository;
import com.ar.vgmsistemas.utils.Formatter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UbicacionGeograficaRepositoryImpl implements IUbicacionGeograficaRepository {
    private IUbicacionGeograficaDao _ubicacionGeograficaDao;

    public UbicacionGeograficaRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._ubicacionGeograficaDao = db.ubicacionGeograficaDao();
        }
    }

    @Override
    public Integer create(UbicacionGeografica ubicacionGeografica) throws Exception {
        if (ubicacionGeografica.getId() == 0) {
            ubicacionGeografica.setId(_ubicacionGeograficaDao.maxIdUbicacionGeo() + 1);
        }
        _ubicacionGeograficaDao.create(mappingToDb(ubicacionGeografica));
        return ubicacionGeografica.getId();
    }

    @Override
    public UbicacionGeografica recoveryByID(Integer id) throws Exception {
        return mappingToDto(_ubicacionGeograficaDao.recoveryByID(id));

    }

    @Override
    public List<UbicacionGeografica> recoveryAll() throws Exception {
        return recoveryAll();
    }


    @Override
    public void update(UbicacionGeografica ubicacionGeografica) throws Exception {
        _ubicacionGeograficaDao.update(mappingToDb(ubicacionGeografica));
    }

    @Override
    public void delete(UbicacionGeografica ubicacionGeografica) throws Exception {
        _ubicacionGeograficaDao.delete(mappingToDb(ubicacionGeografica));
    }

    @Override
    public void delete(Integer id) throws Exception {
        _ubicacionGeograficaDao.delete(id);
    }

    @Override
    public UbicacionGeografica recoveryByIdMovil(String idMovil) throws Exception {
        return mappingToDto(_ubicacionGeograficaDao.recoveryByIdMovil(idMovil));
    }

    @Override
    public List<UbicacionGeografica> recoveryNoEnviados() throws Exception {
        List<UbicacionGeograficaBd> listadoUbicacionBd = _ubicacionGeograficaDao.recoveryNoEnviados();
        List<UbicacionGeografica> ubicaciones = new ArrayList<>();
        if (!listadoUbicacionBd.isEmpty()) {
            for (UbicacionGeograficaBd item : listadoUbicacionBd) {
                ubicaciones.add(mappingToDto(item));
            }
        }
        return ubicaciones;
    }

    @Override
    public void updateFechaSincronizacion(int idLegajo, Date fechaPosicionRecibida) throws Exception {
        Date fechaSincronizacion = Calendar.getInstance().getTime();
        _ubicacionGeograficaDao.updateFechaSincronizacion(idLegajo, Formatter.formatDateWs(fechaPosicionRecibida), Formatter.formatDateWs(fechaSincronizacion));
    }

    @Override
    public void marcarUbicacionesComoEnviadas() throws Exception {
        Date fechaActual = Calendar.getInstance().getTime();
        _ubicacionGeograficaDao.marcarUbicacionesComoEnviadas(Formatter.formatDateWs(fechaActual));
    }

    @Override
    public void reenvioUbicaciones(String idMovil) throws Exception {
        _ubicacionGeograficaDao.reenvioUbicaciones(idMovil);
    }

    @Override
    public UbicacionGeografica mappingToDto(UbicacionGeograficaBd ubicacionGeograficaBd) throws ParseException {
        UbicacionGeografica ubicacion = new UbicacionGeografica();
        if (ubicacionGeograficaBd != null) {

            ubicacion.setId(ubicacionGeograficaBd.getId());
            ubicacion.setIdLegajo(ubicacionGeograficaBd.getIdLegajo());
            ubicacion.setFechaPosicionMovil(ubicacionGeograficaBd.getFechaPosicionMovil() == null
                    ? null
                    : Formatter.convertToDateTimeTwo(ubicacionGeograficaBd.getFechaPosicionMovil()));
            ubicacion.setLatitud(ubicacionGeograficaBd.getLatitud());
            ubicacion.setLongitud(ubicacionGeograficaBd.getLongitud());
            ubicacion.setPrecision(ubicacionGeograficaBd.getPrecision());
            ubicacion.setVelocidad(ubicacionGeograficaBd.getVelocidad());
            ubicacion.setAltitud(ubicacionGeograficaBd.getAltitud());
            ubicacion.setTipoOperacion(ubicacionGeograficaBd.getTipoOperacion());
            ubicacion.setFechaSincronizacion(ubicacionGeograficaBd.getFechaSincronizacion() == null
                    ? null
                    : Formatter.convertToDateTime(ubicacionGeograficaBd.getFechaSincronizacion()));
            ubicacion.setDeviceId(ubicacionGeograficaBd.getDeviceId());
            ubicacion.setEstadoProveedor(ubicacionGeograficaBd.getEstadoProveedor());
        }
        return ubicacion;
    }

    private UbicacionGeograficaBd mappingToDb(UbicacionGeografica ubicacionGeografica) throws ParseException {
        if (ubicacionGeografica != null) {
            UbicacionGeograficaBd ubicacion = new UbicacionGeograficaBd();

            ubicacion.setId(ubicacionGeografica.getId());
            ubicacion.setIdLegajo(ubicacionGeografica.getIdLegajo());
            ubicacion.setFechaPosicionMovil(ubicacionGeografica.getFechaPosicionMovil() == null
                    ? null
                    : Formatter.formatDateTimeToString(ubicacionGeografica.getFechaPosicionMovil()));
            ubicacion.setLatitud(ubicacionGeografica.getLatitud());
            ubicacion.setLongitud(ubicacionGeografica.getLongitud());
            ubicacion.setPrecision(ubicacionGeografica.getPrecision());
            ubicacion.setVelocidad(ubicacionGeografica.getVelocidad());
            ubicacion.setAltitud(ubicacionGeografica.getAltitud());
            ubicacion.setTipoOperacion(ubicacionGeografica.getTipoOperacion());
            ubicacion.setFechaSincronizacion(ubicacionGeografica.getFechaSincronizacion() == null
                    ? null
                    : Formatter.formatDateTimeToString(ubicacionGeografica.getFechaSincronizacion()));
            ubicacion.setDeviceId(ubicacionGeografica.getDeviceId());
            ubicacion.setEstadoProveedor(ubicacionGeografica.getEstadoProveedor());
            return ubicacion;
        }
        return null;

    }
}
