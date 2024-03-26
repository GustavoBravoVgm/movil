package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.ComercioLoginBd;
import com.ar.vgmsistemas.entity.ComercioLogin;
import com.ar.vgmsistemas.entity.key.PkCliente;
import com.ar.vgmsistemas.entity.key.PkComercioLogin;

import java.text.ParseException;

public interface IComercioLoginRepository extends IGenericRepository<ComercioLogin, PkComercioLogin> {

    ComercioLogin recoveryByCliente(PkCliente pkCliente) throws Exception;

    ComercioLogin mappingToDto(ComercioLoginBd comercioLoginBd) throws ParseException;
}
