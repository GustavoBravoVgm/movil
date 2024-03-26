package com.ar.vgmsistemas.repository;


import com.ar.vgmsistemas.database.dao.entity.HojaBd;
import com.ar.vgmsistemas.entity.Hoja;

import java.text.ParseException;
import java.util.List;

public interface IHojaRepository extends IGenericRepository<Hoja, Long> {
    void updateTotalAnulado(int idSucursal, int idHoja, double totalAnulado) throws Exception;

    void updateTotalNoEntregado(int idSucursal, int idHoja, double prPendiente) throws Exception;

    void updateState(int idSucursal, int idHoja, int state)throws Exception;

    String getSnRendida(int idSucursal, int idHoja) throws Exception;

    List<Hoja> recoveryBySucursal(int idSucursal) throws Exception;

    Hoja mappingToDto(HojaBd hojaBd) throws ParseException;

}
