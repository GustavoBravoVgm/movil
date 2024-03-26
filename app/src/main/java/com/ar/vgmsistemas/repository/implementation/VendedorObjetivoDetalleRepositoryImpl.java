package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IVendedorObjetivoDetalleDao;
import com.ar.vgmsistemas.database.dao.entity.VendedorObjetivoDetalleBd;
import com.ar.vgmsistemas.entity.VendedorObjetivo;
import com.ar.vgmsistemas.entity.VendedorObjetivoDetalle;
import com.ar.vgmsistemas.entity.key.PkVendedorObjetivoDetalle;
import com.ar.vgmsistemas.repository.IVendedorObjetivoDetalleRepository;

import java.util.ArrayList;
import java.util.List;

public class VendedorObjetivoDetalleRepositoryImpl implements IVendedorObjetivoDetalleRepository {
    private final AppDataBase _db;

    private IVendedorObjetivoDetalleDao _vendedorObjetivoDetalleDao;

    public VendedorObjetivoDetalleRepositoryImpl(AppDataBase db) {
        this._db = db;
        if (db != null) {
            this._vendedorObjetivoDetalleDao = db.vendedorObjetivoDetalleDao();
        }
    }

    public List<VendedorObjetivoDetalle> recoveryDetalles(VendedorObjetivo objetivo) throws Exception {
        List<VendedorObjetivoDetalleBd> listadoVendObjDet = _vendedorObjetivoDetalleDao.recoveryDetalles(objetivo.getIdObjetivo());
        List<VendedorObjetivoDetalle> objetivosDetallesPorVendedor = new ArrayList<>();
        if (!listadoVendObjDet.isEmpty()) {
            for (VendedorObjetivoDetalleBd item : listadoVendObjDet) {
                objetivosDetallesPorVendedor.add(mappingToDto(item));
            }
        }
        return objetivosDetallesPorVendedor;
    }

    public VendedorObjetivoDetalle mappingToDto(VendedorObjetivoDetalleBd vendedorObjetivoDetalleBd) throws Exception {
        VendedorObjetivoDetalle vendedorObjetivoDetalle = new VendedorObjetivoDetalle();
        if (vendedorObjetivoDetalleBd != null) {
            PkVendedorObjetivoDetalle id = new PkVendedorObjetivoDetalle(vendedorObjetivoDetalleBd.getId().getIdObjetivo(),
                    vendedorObjetivoDetalleBd.getId().getIdSecuencia());

            vendedorObjetivoDetalle.setId(id);
            //cargo negocio
            NegocioRepositoryImpl negocioRepository = new NegocioRepositoryImpl(this._db);
            vendedorObjetivoDetalle.setNegocio(negocioRepository.mappingToDto(this._db.negocioDao().recoveryById(
                    vendedorObjetivoDetalleBd.getIdNegocio())));
            //cargo rubro
            RubroRepositoryImpl rubroRepository = new RubroRepositoryImpl(this._db);
            vendedorObjetivoDetalle.setRubro(rubroRepository.mappingToDtoSinNegocio(this._db.rubroDao().recoveryByID(
                    vendedorObjetivoDetalleBd.getIdNegocio(), vendedorObjetivoDetalleBd.getIdSegmento())));
            //cargo subrubro
            SubrubroRepositoryImpl subrubroRepository = new SubrubroRepositoryImpl(this._db);
            vendedorObjetivoDetalle.setSubrubro(subrubroRepository.mappingToDtoSinRubro(this._db.subrubroDao().recoveryByID(
                    vendedorObjetivoDetalleBd.getIdNegocio(), vendedorObjetivoDetalleBd.getIdSegmento(),
                    vendedorObjetivoDetalleBd.getIdSubrubro())));
            //cargo marca
            MarcaRepositoryImpl marcaRepository = new MarcaRepositoryImpl(this._db);
            vendedorObjetivoDetalle.setLinea(marcaRepository.mappingToDto(this._db.marcaDao().recoveryByID(
                    vendedorObjetivoDetalleBd.getIdLinea())));
            //cargo proveedor
            ProveedorRepositoryImpl proveedorRepository = new ProveedorRepositoryImpl(this._db);
            vendedorObjetivoDetalle.setProveedor(proveedorRepository.mappingToDto(this._db.proveedorDao().recoveryByID(
                    vendedorObjetivoDetalleBd.getIdProveedor())));
            //cargo articulo
            ArticuloRepositoryImpl articuloRepository = new ArticuloRepositoryImpl(this._db);
            vendedorObjetivoDetalle.setArticulo(articuloRepository.mappingToDtoSinClases(this._db.articuloDao().recoveryByIDSinCruce(
                    vendedorObjetivoDetalleBd.getIdArticulo())));
            vendedorObjetivoDetalle.setCaArticulos(vendedorObjetivoDetalleBd.getCaArticulos());
            vendedorObjetivoDetalle.setCaKilos(vendedorObjetivoDetalleBd.getCaKilos());
            vendedorObjetivoDetalle.setTaCobertura(vendedorObjetivoDetalleBd.getTaCobertura());
            vendedorObjetivoDetalle.setCaRestante(vendedorObjetivoDetalleBd.getCaRestante());
            vendedorObjetivoDetalle.setLogrado(vendedorObjetivoDetalleBd.getLogrado());
        }
        return vendedorObjetivoDetalle;
    }
}
