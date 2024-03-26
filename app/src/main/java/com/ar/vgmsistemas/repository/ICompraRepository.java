package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.CompraBd;
import com.ar.vgmsistemas.entity.Compra;
import com.ar.vgmsistemas.entity.key.PkCompra;

import java.text.ParseException;
import java.util.List;

public interface ICompraRepository extends IGenericRepository<Compra, PkCompra> {

    List<Compra> recoveryNoEnviados() throws Exception;

    boolean isEnviado(Compra egreso) throws Exception;

    List<Compra> recoverEgresosByHoja(int idHoja, int idSucursal) throws Exception;

    boolean existsEgreso(PkCompra id) throws Exception;

    Compra mappingToDto(CompraBd compraBd) throws ParseException;
}
