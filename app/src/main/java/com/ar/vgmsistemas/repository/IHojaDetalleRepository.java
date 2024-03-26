package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.entity.HojaDetalle;
import com.ar.vgmsistemas.entity.key.PkHojaDetalle;

import java.util.List;

public interface IHojaDetalleRepository extends IGenericRepository<HojaDetalle, PkHojaDetalle> {
    List<HojaDetalle> recoveryByHoja(int idSucursal, int idHoja) throws Exception;

    HojaDetalle recoveryByIDConEntrega(PkHojaDetalle pkHojaDetalle) throws Exception;

    List<HojaDetalle> recoveryByHojaConEntrega(int idSucursal, int idHoja) throws Exception;

    List<HojaDetalle> recoveryChequesByHoja(int idSucursal, int idHoja) throws Exception;

    void updateState(HojaDetalle detalle, String state) throws Exception;

    List<HojaDetalle> recoveryAEnviar() throws Exception;

    boolean isEnviado(HojaDetalle detalle) throws Exception;

    int recoveryIdEntrega(HojaDetalle detalle) throws Exception;

    List<HojaDetalle> recoveryBySucursal(int idSucursal) throws Exception;

    List<HojaDetalle> recoveryAll() throws Exception;

    List<HojaDetalle> recoveryAEnviarUnicaEntrega() throws Exception;

    List<HojaDetalle> recoveryByIdEntrega(int idEntrega) throws Exception;

    void updateValoresEntrega(HojaDetalle detalle) throws Exception;

}
