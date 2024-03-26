package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.ArticuloBd;
import com.ar.vgmsistemas.database.dao.entity.result.ArticuloResultBd;
import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.ListaPrecio;
import com.ar.vgmsistemas.entity.VentaDetalle;

import java.util.List;

public interface IArticuloRepository extends IGenericRepository<Articulo, Integer> {

    void updateStock(VentaDetalle ventaDetalle) throws Exception;

    void updateStock(Articulo articulo, double stock) throws Exception;

    List<Articulo> recoveryCantidadesAcumuladasPorCliente(Cliente c) throws Exception;

    List<Articulo> recoveryCantidadesPorCliente(Cliente c) throws Exception;

    List<Articulo> recoveryAll(ListaPrecio listaPrecioXDefecto) throws Exception;

    boolean isArticuloInSubrubro(int idArticulo, int idSubrubro) throws Exception;

    double recoveryStockById(int idArticulo) throws Exception;

    List<Articulo> recoveryAllByCliente(Cliente c) throws Exception;

    Articulo mappingToDto(ArticuloResultBd articuloBd) throws Exception;

    Articulo mappingToDtoSinClases(ArticuloBd articuloBd) throws Exception;

}
