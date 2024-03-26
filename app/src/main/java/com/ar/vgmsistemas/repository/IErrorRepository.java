package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.entity.ErrorMovil;

import java.util.List;

public interface IErrorRepository extends IGenericRepository<ErrorMovil, Integer> {

    List<ErrorMovil> recoveryAEnviar() throws Exception;

}
