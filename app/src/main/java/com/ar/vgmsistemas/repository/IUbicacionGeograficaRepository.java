package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.UbicacionGeograficaBd;
import com.ar.vgmsistemas.entity.UbicacionGeografica;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface IUbicacionGeograficaRepository extends IGenericRepository<UbicacionGeografica, Integer> {

    UbicacionGeografica recoveryByIdMovil(String idMovil) throws Exception;

    List<UbicacionGeografica> recoveryNoEnviados() throws Exception;

    void updateFechaSincronizacion(int idLegajo, Date fechaPosicion) throws Exception;

    void marcarUbicacionesComoEnviadas() throws Exception;

    void reenvioUbicaciones(String idMovil) throws Exception;

    UbicacionGeografica mappingToDto(UbicacionGeograficaBd ubicacionGeograficaBd) throws ParseException;
}
