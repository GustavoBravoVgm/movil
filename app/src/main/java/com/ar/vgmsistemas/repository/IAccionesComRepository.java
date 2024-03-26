package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.entity.AccionesCom;

import java.util.List;

public interface IAccionesComRepository extends IGenericRepository<AccionesCom, Integer> {
    List<AccionesCom> recoveryByPeriodo() throws Exception;
}
