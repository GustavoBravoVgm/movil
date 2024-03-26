package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.entity.Compra;
import com.ar.vgmsistemas.entity.ComprasImpuestos;
import com.ar.vgmsistemas.entity.key.PkComprasImpuestos;

import java.util.List;

public interface IComprasImpuestosRepository extends IGenericRepository<ComprasImpuestos, PkComprasImpuestos> {

    List<ComprasImpuestos> recoveryByEgreso(Compra egreso) throws Exception;

    void anularComprasImpuestoByCompra(Compra egreso) throws Exception;

    void deleteComprasImpuestosByCompra(Compra egreso) throws Exception;

}
