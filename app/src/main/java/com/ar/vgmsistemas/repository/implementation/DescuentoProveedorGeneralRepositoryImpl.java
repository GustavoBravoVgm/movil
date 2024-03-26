package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IDescuentoProveedorGeneralDao;
import com.ar.vgmsistemas.database.dao.entity.DescuentoProveedorGeneralBd;
import com.ar.vgmsistemas.entity.DescuentoProveedorGeneral;
import com.ar.vgmsistemas.repository.IDescuentoProveedorGeneralRepository;

import java.util.ArrayList;
import java.util.List;

public class DescuentoProveedorGeneralRepositoryImpl implements IDescuentoProveedorGeneralRepository {

    private IDescuentoProveedorGeneralDao _descuentoProveedorGeneralDao;

    public DescuentoProveedorGeneralRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._descuentoProveedorGeneralDao = db.descuentoProveedorGeneralDao();
        }
    }

    @Override
    public List<DescuentoProveedorGeneral> recoveryGenerales() throws Exception {
        List<DescuentoProveedorGeneralBd> listadoDescuentos = _descuentoProveedorGeneralDao.recoveryGenerales();
        List<DescuentoProveedorGeneral> descuentos = new ArrayList<>();
        if (!listadoDescuentos.isEmpty()) {
            for (DescuentoProveedorGeneralBd item : listadoDescuentos) {
                descuentos.add(mappingToDto(item));
            }
        }
        return descuentos;
    }

    @Override
    public Long create(DescuentoProveedorGeneral entity) throws Exception {
        return null;
    }

    @Override
    public DescuentoProveedorGeneral recoveryByID(Long aLong) throws Exception {
        return null;
    }

    @Override
    public List<DescuentoProveedorGeneral> recoveryAll() throws Exception {
        return null;
    }

    @Override
    public void update(DescuentoProveedorGeneral entity) throws Exception {

    }

    @Override
    public void delete(DescuentoProveedorGeneral entity) throws Exception {

    }

    @Override
    public void delete(Long aLong) throws Exception {

    }

    @Override
    public DescuentoProveedorGeneral mappingToDto(DescuentoProveedorGeneralBd descuentoProveedorBd) {
        DescuentoProveedorGeneral descuento = new DescuentoProveedorGeneral();
        if (descuentoProveedorBd != null) {

            descuento.setIdDescuentoProveedor(descuentoProveedorBd.getIdDescuentoProveedor());
            descuento.setIdProveedor(descuentoProveedorBd.getIdProveedor());
            descuento.setIdNegocio(descuentoProveedorBd.getIdNegocio());
            descuento.setIdRubro(descuentoProveedorBd.getIdRubro());
            descuento.setIdSubrubro(descuentoProveedorBd.getIdSubrubro());
            descuento.setIdArticulo(descuentoProveedorBd.getIdArticulo());
            descuento.setTasaDescuento(descuentoProveedorBd.getTasaDescuento());
            descuento.setPrioridad(descuentoProveedorBd.getPrioridad());
            descuento.setIdMarca(descuentoProveedorBd.getIdMarca());

        }
        return descuento;
    }
}
