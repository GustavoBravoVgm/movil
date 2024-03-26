package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IProveedorVendComercioDao;
import com.ar.vgmsistemas.database.dao.entity.ProveedorVendComercioBd;
import com.ar.vgmsistemas.entity.ProveedorVendComercio;
import com.ar.vgmsistemas.entity.key.PkProveedorVendComercio;
import com.ar.vgmsistemas.repository.IProveedorVendComercioRepository;

import java.util.ArrayList;
import java.util.List;

public class ProveedorVendComercioRepositoryImpl implements IProveedorVendComercioRepository {

    private IProveedorVendComercioDao _proveedorVendComercioDao;

    public ProveedorVendComercioRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._proveedorVendComercioDao = db.proveedorVendComercioDao();
        }
    }

    @Override
    public PkProveedorVendComercio create(ProveedorVendComercio entity) throws Exception {
        return null;
    }

    @Override
    public ProveedorVendComercio recoveryByID(PkProveedorVendComercio id) throws Exception {
        return mappingToDto(_proveedorVendComercioDao.recoveryByID(id.getIdProveedor(), id.getIdVendedor(),
                id.getIdSucursal(), id.getIdCliente(), id.getIdComercio()));
    }

    @Override
    public List<ProveedorVendComercio> recoveryAll() throws Exception {
        List<ProveedorVendComercioBd> listadoProvVendComer = _proveedorVendComercioDao.recoveryAll();
        List<ProveedorVendComercio> listados = new ArrayList<>();
        if (!listadoProvVendComer.isEmpty()) {
            for (ProveedorVendComercioBd item : listadoProvVendComer) {
                listados.add(mappingToDto(item));
            }
        }
        return listados;
    }

    @Override
    public void update(ProveedorVendComercio entity) throws Exception {

    }

    @Override
    public void delete(ProveedorVendComercio entity) throws Exception {

    }

    @Override
    public void delete(PkProveedorVendComercio pkProveedorVendComercio) throws Exception {

    }

    public ProveedorVendComercio mappingToDto(ProveedorVendComercioBd proveedorVendComercioBd) {
        ProveedorVendComercio proveedorVendComercio = new ProveedorVendComercio();
        if (proveedorVendComercioBd != null) {
            PkProveedorVendComercio id = new PkProveedorVendComercio(proveedorVendComercioBd.getId().getIdProveedor(),
                    proveedorVendComercioBd.getId().getIdVendedor(), proveedorVendComercioBd.getId().getIdSucursal(),
                    proveedorVendComercioBd.getId().getIdCliente(), proveedorVendComercioBd.getId().getIdComercio());
            proveedorVendComercio.setId(id);
        }
        return proveedorVendComercio;
    }

}
