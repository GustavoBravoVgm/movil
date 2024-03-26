package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IVendedorObjetivoDao;
import com.ar.vgmsistemas.database.dao.entity.VendedorObjetivoBd;
import com.ar.vgmsistemas.entity.VendedorObjetivo;
import com.ar.vgmsistemas.repository.IVendedorObjetivoRepository;
import com.ar.vgmsistemas.utils.Formatter;

import java.util.ArrayList;
import java.util.List;

public class VendedorObjetivoRepositoryImpl implements IVendedorObjetivoRepository {
    private final AppDataBase _db;

    private IVendedorObjetivoDao _vendedorObjetivoDao;

    public VendedorObjetivoRepositoryImpl(AppDataBase db) {
        this._db = db;
        if (db != null) {
            this._vendedorObjetivoDao = db.vendedorObjetivoDao();
        }
    }

    public List<VendedorObjetivo> recoveryAll() throws Exception {
        List<VendedorObjetivoBd> listadosObjetivos = _vendedorObjetivoDao.recoveryAll();
        List<VendedorObjetivo> objetivos = new ArrayList<>();
        if (!listadosObjetivos.isEmpty()) {
            for (VendedorObjetivoBd item : listadosObjetivos) {
                objetivos.add(mappingToDto(item));
            }
        }
        return objetivos;
    }

    @Override
    public VendedorObjetivo mappingToDto(VendedorObjetivoBd vendedorObjetivoBd) throws Exception {
        VendedorObjetivo vendedorObjetivo = new VendedorObjetivo();
        if (vendedorObjetivoBd != null) {

            vendedorObjetivo.setIdObjetivo(vendedorObjetivoBd.getIdObjetivo());
            //cargo vendedor
            VendedorRepositoryImpl vendedorRepository = new VendedorRepositoryImpl(this._db);
            vendedorObjetivo.setVendedor(vendedorRepository.mappingToDto(this._db.vendedorDao().recoveryByID(vendedorObjetivoBd.getIdVendedor())));
            vendedorObjetivo.setTiObjetivo(vendedorObjetivoBd.getTiObjetivo());
            vendedorObjetivo.setTiCategoria(vendedorObjetivoBd.getTiCategoria());
            vendedorObjetivo.setFeDesde(vendedorObjetivoBd.getFeDesde() == null
                    ? null
                    : Formatter.convertToDate(vendedorObjetivoBd.getFeDesde()));
            vendedorObjetivo.setFeHasta(vendedorObjetivoBd.getFeHasta() == null
                    ? null
                    : Formatter.convertToDate(vendedorObjetivoBd.getFeHasta()));
            //cargo proveedor
            ProveedorRepositoryImpl proveedorRepository = new ProveedorRepositoryImpl(this._db);
            vendedorObjetivo.setProveedor(proveedorRepository.mappingToDto(
                    this._db.proveedorDao().recoveryByID(vendedorObjetivoBd.getIdProveedor())));
            vendedorObjetivo.setSnAplica(vendedorObjetivoBd.getSnAplica() != null &&
                    vendedorObjetivoBd.getSnAplica().equalsIgnoreCase("S"));
        }
        return vendedorObjetivo;
    }
}
