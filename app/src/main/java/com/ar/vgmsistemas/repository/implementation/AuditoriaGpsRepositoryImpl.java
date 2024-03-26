package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IAuditoriaGpsDao;
import com.ar.vgmsistemas.database.dao.entity.AuditoriaGpsBd;
import com.ar.vgmsistemas.entity.AuditoriaGps;
import com.ar.vgmsistemas.repository.IAuditoriaGpsRepository;
import com.ar.vgmsistemas.utils.Formatter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AuditoriaGpsRepositoryImpl implements IAuditoriaGpsRepository {

    private IAuditoriaGpsDao _auditoriaGpsDao;

    public AuditoriaGpsRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._auditoriaGpsDao = db.auditoriaGpsDao();
        }
    }

    @Override
    public Long create(AuditoriaGps entity) throws Exception {
        return _auditoriaGpsDao.create(this.mappingToEntityDb(entity));
    }

    @Override
    public AuditoriaGps recoveryByID(Long id) throws Exception {
        return null;
    }

    @Override
    public List<AuditoriaGps> recoveryAll() throws Exception {
        return null;
    }

    @Override
    public void update(AuditoriaGps entity) throws Exception {

    }

    @Override
    public void delete(AuditoriaGps entity) throws Exception {

    }

    @Override
    public void delete(Long id) throws Exception {

    }

    @Override
    public boolean existsAuditoria(String idMovil) throws Exception {
        return _auditoriaGpsDao.existsAuditoria(idMovil) > 0;
    }

    @Override
    public boolean existsAuditoria(AuditoriaGps auditoriaGps) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(auditoriaGps.getFeRegistroMovil());
        calendar.add(Calendar.SECOND, -2);

        String sFeAnterior = Formatter.formatDateTimeToString(calendar.getTime());
        String sFeSiguiente = Formatter.formatDateTimeToString(auditoriaGps.getFeRegistroMovil());

        return (_auditoriaGpsDao.existsAuditoria(sFeAnterior, sFeSiguiente, auditoriaGps.getTiAccion()) > 0);
    }

    @Override
    public List<AuditoriaGps> recoveryAEnviar() throws Exception {
        List<AuditoriaGpsBd> listadoAuditoriaBd = _auditoriaGpsDao.recoveryAEnviar();
        List<AuditoriaGps> listadoAuditoria = new ArrayList<>();
        if (!listadoAuditoriaBd.isEmpty()) {
            for (AuditoriaGpsBd item : listadoAuditoriaBd) {
                listadoAuditoria.add(this.mappingToDto(item));
            }
        }
        return listadoAuditoria;
    }

    public AuditoriaGps mappingToDto(AuditoriaGpsBd auditoriaGpsBd) {
        AuditoriaGps auditoriaGps = new AuditoriaGps();
        if (auditoriaGps != null) {
            auditoriaGps.setIdAuditoria(auditoriaGps.getIdAuditoria());
            auditoriaGps.setIdVendedor(auditoriaGpsBd.getIdVendedor());
            try {
                auditoriaGps.setFeRegistroMovil(auditoriaGpsBd.getFeRegistroMovil() != null ?
                        Formatter.convertToDateTimeTwo(auditoriaGpsBd.getFeRegistroMovil()) : null);

            } catch (ParseException e) {
                auditoriaGps.setFeRegistroMovil(null);
            }
            try {
                auditoriaGps.setFeRegistroServidor(auditoriaGpsBd.getFeRegistroServidor() != null ?
                        Formatter.convertToDateTimeTwo(auditoriaGpsBd.getFeRegistroServidor()) : null);

            } catch (ParseException e) {
                auditoriaGps.setFeRegistroServidor(null);
            }
            auditoriaGps.setTiAccion(auditoriaGpsBd.getTiAccion());
            auditoriaGps.setIdMovil(auditoriaGpsBd.getIdMovil());
        }
        return auditoriaGps;
    }

    private AuditoriaGpsBd mappingToEntityDb(AuditoriaGps auditoriaGps) {
        AuditoriaGpsBd auditoriaGpsBd = new AuditoriaGpsBd();
        auditoriaGpsBd.setIdAuditoria(auditoriaGps.getIdAuditoria());
        auditoriaGpsBd.setIdVendedor(auditoriaGps.getIdVendedor());
        auditoriaGpsBd.setFeRegistroMovil(auditoriaGps.getFeRegistroMovil() != null ?
                Formatter.formatDateTimeToString(auditoriaGps.getFeRegistroMovil()) : null);
        auditoriaGpsBd.setFeRegistroServidor(auditoriaGps.getFeRegistroServidor() != null ?
                Formatter.formatDateTimeToString(auditoriaGps.getFeRegistroServidor()) : null);
        auditoriaGpsBd.setTiAccion(auditoriaGps.getTiAccion());
        auditoriaGpsBd.setIdMovil(auditoriaGps.getIdMovil());
        return auditoriaGpsBd;
    }
}
