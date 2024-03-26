package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IAccionesComDetalleDao;
import com.ar.vgmsistemas.database.dao.entity.AccionesComDetalleBd;
import com.ar.vgmsistemas.entity.AccionesComDetalle;
import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.entity.key.PkAccionesComDetalle;
import com.ar.vgmsistemas.entity.key.PkCliente;
import com.ar.vgmsistemas.helper.TipoOperacion;
import com.ar.vgmsistemas.repository.IAccionesComDetalleRepository;

import java.util.ArrayList;
import java.util.List;

public class AccionesComDetalleRepositoryImpl implements IAccionesComDetalleRepository {

    private IAccionesComDetalleDao _accionesComDetalleDao;

    public AccionesComDetalleRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._accionesComDetalleDao = db.accionesComDetalleDao();
        }
    }

    @Override
    public PkAccionesComDetalle create(AccionesComDetalle entity) throws Exception {
        return null;
    }

    @Override
    public AccionesComDetalle recoveryByID(PkAccionesComDetalle id) throws Exception {
        return mappingToDto(_accionesComDetalleDao.recoveryByID(id.getIdAccionesCom(), id.getIdAccionesComDetalle()));
    }

    @Override
    public List<AccionesComDetalle> recoveryAll() throws Exception {
        return null;
    }

    @Override
    public void update(AccionesComDetalle entity) throws Exception {

    }

    @Override
    public void delete(AccionesComDetalle entity) throws Exception {

    }

    @Override
    public void delete(PkAccionesComDetalle pkAccionesComDetalle) throws Exception {

    }


    @Override
    public AccionesComDetalle recoveryAccionPorArticulo(VentaDetalle ventaDetalle, PkCliente pkCliente, String origen) throws Exception {

        return mappingToDto(_accionesComDetalleDao.recoveryAccionPorArticulo(
                ventaDetalle.getArticulo().getId(),
                ventaDetalle.getCantidad(), ventaDetalle.getArticulo().getProveedor().getIdProveedor(),
                pkCliente.getIdSucursal(), pkCliente.getIdCliente(), pkCliente.getIdComercio(),
                origen));
    }

    @Override
    public AccionesComDetalle recoveryAccionPorMarca(VentaDetalle ventaDetalle, PkCliente pkCliente, String origen) throws Exception {
        return mappingToDto(_accionesComDetalleDao.recoveryAccionPorMarca(
                ventaDetalle.getArticulo().getMarca().getId(),
                ventaDetalle.getCantidad(), ventaDetalle.getArticulo().getProveedor().getIdProveedor(),
                pkCliente.getIdSucursal(), pkCliente.getIdCliente(), pkCliente.getIdComercio(),
                origen));
    }

    @Override
    public AccionesComDetalle recoveryAccionPorSubrubro(VentaDetalle ventaDetalle, PkCliente pkCliente, String origen) throws Exception {
        return mappingToDto(_accionesComDetalleDao.recoveryAccionPorSubrubro(
                ventaDetalle.getArticulo().getSubrubro().getId().getIdNegocio(),
                ventaDetalle.getArticulo().getSubrubro().getId().getIdRubro(), ventaDetalle.getArticulo().getSubrubro().getId().getIdSubrubro(),
                ventaDetalle.getCantidad(), ventaDetalle.getArticulo().getProveedor().getIdProveedor(),
                pkCliente.getIdSucursal(), pkCliente.getIdCliente(), pkCliente.getIdComercio(),
                origen));
    }

    @Override
    public AccionesComDetalle recoveryAccionPorRubro(VentaDetalle ventaDetalle, PkCliente pkCliente, String origen) throws Exception {
        return mappingToDto(_accionesComDetalleDao.recoveryAccionPorRubro(
                ventaDetalle.getArticulo().getSubrubro().getId().getIdNegocio(),
                ventaDetalle.getArticulo().getSubrubro().getId().getIdRubro(),
                ventaDetalle.getCantidad(), ventaDetalle.getArticulo().getProveedor().getIdProveedor(),
                pkCliente.getIdSucursal(), pkCliente.getIdCliente(), pkCliente.getIdComercio(),
                origen));
    }

    @Override
    public AccionesComDetalle recoveryAccionPorNegocio(VentaDetalle ventaDetalle, PkCliente pkCliente, String origen) throws Exception {
        return mappingToDto(_accionesComDetalleDao.recoveryAccionPorNegocio(
                ventaDetalle.getArticulo().getSubrubro().getId().getIdNegocio(),
                ventaDetalle.getCantidad(), ventaDetalle.getArticulo().getProveedor().getIdProveedor(),
                pkCliente.getIdSucursal(), pkCliente.getIdCliente(), pkCliente.getIdComercio(),
                origen));
    }

    //Recupera todas las opciones de acciones comerciales por articulo sin filtrar por la cantidad del articulo requerido
    public List<AccionesComDetalle> recoveryAllPorArticulo(Articulo articulo, PkCliente pkCliente, String origen) throws Exception {
        List<AccionesComDetalleBd> accionesComDetalleBdList = _accionesComDetalleDao.recoveryAllPorArticulo(
                articulo.getId(), articulo.getProveedor().getIdProveedor(),
                pkCliente.getIdSucursal(), pkCliente.getIdCliente(), pkCliente.getIdComercio(),
                origen);
        List<AccionesComDetalle> listadoAccionesComDetalle = new ArrayList<>();
        if (!accionesComDetalleBdList.isEmpty()) {
            for (AccionesComDetalleBd item : accionesComDetalleBdList) {
                listadoAccionesComDetalle.add(mappingToDto(item));
            }
        }
        return listadoAccionesComDetalle;
    }


    @Override
    public List<AccionesComDetalle> recoveryAllPorMarca(Articulo articulo, PkCliente pkCliente, String origen) throws Exception {
        List<AccionesComDetalleBd> accionesComDetalleBdList = _accionesComDetalleDao.recoveryAllPorMarca(
                articulo.getMarca().getId(),
                articulo.getProveedor().getIdProveedor(),
                pkCliente.getIdSucursal(), pkCliente.getIdCliente(), pkCliente.getIdComercio(),
                origen);
        List<AccionesComDetalle> listadoAccionesComDetalle = new ArrayList<>();
        if (!accionesComDetalleBdList.isEmpty()) {
            for (AccionesComDetalleBd item : accionesComDetalleBdList) {
                listadoAccionesComDetalle.add(mappingToDto(item));
            }
        }
        return listadoAccionesComDetalle;
    }


    @Override
    public List<AccionesComDetalle> recoveryAllPorSubrubro(Articulo articulo, PkCliente pkCliente, String origen) throws Exception {
        List<AccionesComDetalleBd> accionesComDetalleBdList = _accionesComDetalleDao.recoveryAllPorSubrubro(
                articulo.getSubrubro().getId().getIdNegocio(),
                articulo.getSubrubro().getId().getIdRubro(), articulo.getSubrubro().getId().getIdSubrubro(),
                articulo.getProveedor().getIdProveedor(),
                pkCliente.getIdSucursal(), pkCliente.getIdCliente(), pkCliente.getIdComercio(),
                origen);
        List<AccionesComDetalle> listadoAccionesComDetalle = new ArrayList<>();
        if (!accionesComDetalleBdList.isEmpty()) {
            for (AccionesComDetalleBd item : accionesComDetalleBdList) {
                listadoAccionesComDetalle.add(mappingToDto(item));
            }
        }
        return listadoAccionesComDetalle;
    }

    @Override
    public List<AccionesComDetalle> recoveryAllPorRubro(Articulo articulo, PkCliente pkCliente, String origen) throws Exception {
        List<AccionesComDetalleBd> accionesComDetalleBdList = _accionesComDetalleDao.recoveryAllPorRubro(articulo.getSubrubro().getId().getIdNegocio(),
                articulo.getSubrubro().getId().getIdRubro(),
                articulo.getProveedor().getIdProveedor(),
                pkCliente.getIdSucursal(), pkCliente.getIdCliente(), pkCliente.getIdComercio(),
                origen);
        List<AccionesComDetalle> listadoAccionesComDetalle = new ArrayList<>();
        if (!accionesComDetalleBdList.isEmpty()) {
            for (AccionesComDetalleBd item : accionesComDetalleBdList) {
                listadoAccionesComDetalle.add(mappingToDto(item));
            }
        }
        return listadoAccionesComDetalle;
    }

    @Override
    public List<AccionesComDetalle> recoveryAllPorNegocio(Articulo articulo, PkCliente pkCliente, String origen) throws Exception {
        List<AccionesComDetalleBd> accionesComDetalleBdList = _accionesComDetalleDao.recoveryAllPorNegocio(articulo.getSubrubro().getId().getIdNegocio(),
                articulo.getProveedor().getIdProveedor(),
                pkCliente.getIdSucursal(), pkCliente.getIdCliente(), pkCliente.getIdComercio(),
                origen);
        List<AccionesComDetalle> listadoAccionesComDetalle = new ArrayList<>();
        if (!accionesComDetalleBdList.isEmpty()) {
            for (AccionesComDetalleBd item : accionesComDetalleBdList) {
                listadoAccionesComDetalle.add(mappingToDto(item));
            }
        }
        return listadoAccionesComDetalle;
    }

    @Override
    public void updateCaVendidaAccom(VentaDetalle ventaDetalle) throws Exception {
        int tipoOperacion = -1;
        if (ventaDetalle.getTipoOperacion() == TipoOperacion.delete) {
            tipoOperacion = 1;
        }
        AccionesComDetalleBd accionesComDetalleBd;

        //Accion CONJUNTA
        if (ventaDetalle.getIdAccionComDetalleEmp() != 0 && ventaDetalle.getIdAccionComDetalleProveedor() != 0) {
            accionesComDetalleBd = _accionesComDetalleDao.recoveryByID(ventaDetalle.getIdAccionEmp(), ventaDetalle.getIdAccionComDetalleEmp());
            if (accionesComDetalleBd != null) {
                if (accionesComDetalleBd.getCaVendida() == null || accionesComDetalleBd.getCaVendida() < 0D) {
                    accionesComDetalleBd.setCaVendida(0D);
                }
                /*actualizo con cantidades*/
                accionesComDetalleBd.setCaVendida(accionesComDetalleBd.getCaVendida() + tipoOperacion * ventaDetalle.getCantidad());

                _accionesComDetalleDao.updateAccionesComDetalle(accionesComDetalleBd);
            }
        }

        //Accion de la EMPRESA
        if (ventaDetalle.getIdAccionComDetalleEmp() != 0) {
            accionesComDetalleBd = _accionesComDetalleDao.recoveryByID(ventaDetalle.getIdAccionEmp(), ventaDetalle.getIdAccionComDetalleEmp());
            if (accionesComDetalleBd != null) {
                if (accionesComDetalleBd.getCaVendida() == null || accionesComDetalleBd.getCaVendida() < 0D) {
                    accionesComDetalleBd.setCaVendida(0D);
                }
                /*actualizo con cantidades*/
                accionesComDetalleBd.setCaVendida(accionesComDetalleBd.getCaVendida() + tipoOperacion * ventaDetalle.getCantidad());

                _accionesComDetalleDao.updateAccionesComDetalle(accionesComDetalleBd);
            }
        }

        //Accion del proveedor
        if (ventaDetalle.getIdAccionComDetalleProveedor() != 0) {
            accionesComDetalleBd = _accionesComDetalleDao.recoveryByID(ventaDetalle.getIdAccionesCom(), ventaDetalle.getIdAccionComDetalleProveedor());
            if (accionesComDetalleBd != null) {
                if (accionesComDetalleBd.getCaVendida() == null || accionesComDetalleBd.getCaVendida() < 0D) {
                    accionesComDetalleBd.setCaVendida(0D);
                }
                /*actualizo con cantidades*/
                accionesComDetalleBd.setCaVendida(accionesComDetalleBd.getCaVendida() + tipoOperacion * ventaDetalle.getCantidad());

                _accionesComDetalleDao.updateAccionesComDetalle(accionesComDetalleBd);
            }
        }
    }

    public AccionesComDetalle mappingToDto(AccionesComDetalleBd accionComDetalleBd) {
        if (accionComDetalleBd != null) {
            AccionesComDetalle accionesComDetalle = new AccionesComDetalle();
            PkAccionesComDetalle id = new PkAccionesComDetalle(accionComDetalleBd.getId().getIdAccionesCom(),
                    accionComDetalleBd.getId().getIdAccionesComDetalle());
            accionesComDetalle.setId(id);
            accionesComDetalle.setIdProveedor(accionComDetalleBd.getIdProveedor());
            accionesComDetalle.setIdNegocio(accionComDetalleBd.getIdNegocio());
            accionesComDetalle.setIdRubro(accionComDetalleBd.getIdRubro());
            accionesComDetalle.setIdSubrubro(accionComDetalleBd.getIdSubrubro());
            accionesComDetalle.setIdMarca(accionComDetalleBd.getIdMarca());
            accionesComDetalle.setIdArticulo(accionComDetalleBd.getIdArticulo());
            accionesComDetalle.setTaDto(accionComDetalleBd.getTaDto());
            accionesComDetalle.setCaMaxima(accionComDetalleBd.getCaMaxima());
            accionesComDetalle.setCaVendida(accionesComDetalle.getCaVendida());
            accionesComDetalle.setTaDtoBcerrado(accionComDetalleBd.getTaDtoBCerrado());
            accionesComDetalle.setRgLimiteInf(accionComDetalleBd.getRgLimiteInf());
            accionesComDetalle.setRgLimiteSup(accionComDetalleBd.getRgLimiteSup());
            accionesComDetalle.setTaDtoEmpresa(accionComDetalleBd.getTaDtoEmpresa());
            return accionesComDetalle;
        }
        return null;
    }
}
