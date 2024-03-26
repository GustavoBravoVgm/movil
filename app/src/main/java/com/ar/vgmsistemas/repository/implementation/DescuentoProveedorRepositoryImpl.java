package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IDescuentoProveedorDao;
import com.ar.vgmsistemas.database.dao.entity.DescuentoProveedorBd;
import com.ar.vgmsistemas.entity.DescuentoProveedor;
import com.ar.vgmsistemas.entity.key.PkCliente;
import com.ar.vgmsistemas.entity.key.PkDescuentoProveedor;
import com.ar.vgmsistemas.repository.IDescuentoProveedorRepository;

import java.util.ArrayList;
import java.util.List;

public class DescuentoProveedorRepositoryImpl implements IDescuentoProveedorRepository {

    private IDescuentoProveedorDao _descuentoProveedorDao;

    public DescuentoProveedorRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._descuentoProveedorDao = db.descuentoProveedorDao();
        }
    }

    @Override
    public Integer create(DescuentoProveedor descuentoProveedor) throws Exception {
        return null;
    }

    @Override
    public DescuentoProveedor recoveryByID(Integer id) throws Exception {
        return mappingToDto(_descuentoProveedorDao.recoveryByID(id));
    }

    @Override
    public List<DescuentoProveedor> recoveryAll() throws Exception {
        return null;
    }

    @Override
    public List<DescuentoProveedor> recoveryByCliente(PkCliente id) throws Exception {
        List<DescuentoProveedorBd> listadoDescuentos = _descuentoProveedorDao.recoveryByCliente(id.getIdSucursal(), id.getIdCliente());
        List<DescuentoProveedor> descuentos = new ArrayList<>();
        if (!listadoDescuentos.isEmpty()) {
            for (DescuentoProveedorBd item : listadoDescuentos) {
                descuentos.add(mappingToDto(item));
            }
        }
        return descuentos;
    }


    @Override
    public void update(DescuentoProveedor descuentoProveedor) throws Exception {
    }

    @Override
    public void delete(DescuentoProveedor descuentoProveedor) throws Exception {
    }

    @Override
    public void delete(Integer id) throws Exception {
    }

    @Override
    public DescuentoProveedor mappingToDto(DescuentoProveedorBd descuentoProveedorBd) {
        DescuentoProveedor descuentoProveedor = new DescuentoProveedor();
        if (descuentoProveedorBd != null) {
            PkDescuentoProveedor id = new PkDescuentoProveedor(descuentoProveedorBd.getId().getIdVendedor(),
                    descuentoProveedorBd.getId().getIdSucursal(), descuentoProveedorBd.getId().getIdCliente(),
                    descuentoProveedorBd.getId().getIdComercio(), descuentoProveedorBd.getId().getIdDescuentoProveedorCliente(),
                    descuentoProveedorBd.getId().getIdDescuentoProveedor());

            descuentoProveedor.setId(id);
            descuentoProveedor.setDescripcionDescuento(descuentoProveedorBd.getDescripcionDescuento());
            descuentoProveedor.setIdProveedor(descuentoProveedorBd.getIdProveedor());
            descuentoProveedor.setIdNegocio(descuentoProveedorBd.getIdNegocio());
            descuentoProveedor.setIdRubro(descuentoProveedorBd.getIdRubro());
            descuentoProveedor.setIdSubrubro(descuentoProveedorBd.getIdSubrubro());
            descuentoProveedor.setIdArticulo(descuentoProveedorBd.getIdArticulo());
            descuentoProveedor.setIdMarca(descuentoProveedorBd.getIdMarca());
            descuentoProveedor.setTasaDescuento(descuentoProveedorBd.getTasaDescuento());
            descuentoProveedor.setPrioridad(descuentoProveedorBd.getPrioridad());
        }
        return descuentoProveedor;
    }
}
