package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IProveedorDao;
import com.ar.vgmsistemas.database.dao.entity.ProveedorBd;
import com.ar.vgmsistemas.entity.Proveedor;
import com.ar.vgmsistemas.repository.IProveedorRepository;

import java.util.ArrayList;
import java.util.List;


public class ProveedorRepositoryImpl implements IProveedorRepository {
    private IProveedorDao _proveedorDao;

    public ProveedorRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._proveedorDao = db.proveedorDao();
        }
    }

    @Override
    public Integer create(Proveedor entity) throws Exception {
        return null;
    }

    @Override
    public Proveedor recoveryByID(Integer id) throws Exception {
        return mappingToDto(_proveedorDao.recoveryByID(id));
    }

    @Override
    public List<Proveedor> recoveryAll() throws Exception {
        return null;
    }

    @Override
    public void update(Proveedor entity) throws Exception {
    }

    @Override
    public void delete(Proveedor entity) throws Exception {
    }

    @Override
    public void delete(Integer id) throws Exception {
    }

    @Override
    public List<Proveedor> recoveryProveedoresTipoGasto() throws Exception {
        List<ProveedorBd> listadoProveedoresBd = _proveedorDao.recoveryProveedoresTipoGasto();
        List<Proveedor> proveedores = new ArrayList<>();
        if (!listadoProveedoresBd.isEmpty()) {
            for (ProveedorBd item : listadoProveedoresBd) {
                proveedores.add(mappingToDto(item));
            }
        }
        return proveedores;
    }

    @Override
    public List<Proveedor> recoveryProveedoresTipoGastoBySucursal(int idSucursal) throws Exception {
        List<ProveedorBd> listadoProveedoresBd = _proveedorDao.recoveryProveedoresTipoGastoBySucursal(idSucursal);
        List<Proveedor> proveedores = new ArrayList<>();
        if (!listadoProveedoresBd.isEmpty()) {
            for (ProveedorBd item : listadoProveedoresBd) {
                proveedores.add(mappingToDto(item));
            }
        }
        return proveedores;
    }

    @Override
    public Proveedor mappingToDto(ProveedorBd proveedorBd) {
        return new Proveedor(proveedorBd.getIdProveedor(), proveedorBd.getDeProveedor(), proveedorBd.getIdPlancta(),
                proveedorBd.getIdSucursal(), proveedorBd.getTiProveedor(), proveedorBd.getNuCuit());
    }
}
