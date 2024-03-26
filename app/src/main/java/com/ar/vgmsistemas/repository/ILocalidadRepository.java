package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.LocalidadBd;
import com.ar.vgmsistemas.entity.Localidad;

import java.util.List;

public interface ILocalidadRepository extends IGenericRepository<Localidad, Integer> {

    List<Localidad> recoveryByProvincia(int idProvincia) throws Exception;

    Localidad mappingToDto(LocalidadBd localidadBd) throws Exception;

}
