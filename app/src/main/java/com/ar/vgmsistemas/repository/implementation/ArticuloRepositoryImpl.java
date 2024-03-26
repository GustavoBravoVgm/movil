package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IArticuloDao;
import com.ar.vgmsistemas.database.dao.IMarcaDao;
import com.ar.vgmsistemas.database.dao.INegocioDao;
import com.ar.vgmsistemas.database.dao.IProveedorDao;
import com.ar.vgmsistemas.database.dao.IRubroDao;
import com.ar.vgmsistemas.database.dao.IStockDao;
import com.ar.vgmsistemas.database.dao.ISubrubroDao;
import com.ar.vgmsistemas.database.dao.entity.ArticuloBd;
import com.ar.vgmsistemas.database.dao.entity.result.ArticuloResultBd;
import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.ListaPrecio;
import com.ar.vgmsistemas.entity.Marca;
import com.ar.vgmsistemas.entity.Negocio;
import com.ar.vgmsistemas.entity.Proveedor;
import com.ar.vgmsistemas.entity.Rubro;
import com.ar.vgmsistemas.entity.Subrubro;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.entity.key.PkRubro;
import com.ar.vgmsistemas.entity.key.PkSubrubro;
import com.ar.vgmsistemas.helper.TipoOperacion;
import com.ar.vgmsistemas.repository.IArticuloRepository;
import com.ar.vgmsistemas.utils.Formatter;

import java.util.ArrayList;
import java.util.List;

public class ArticuloRepositoryImpl implements IArticuloRepository {
    private final AppDataBase _db;
    //DAO's
    private IArticuloDao _articuloDao;
    private ISubrubroDao _subrubroDao;
    private IRubroDao _rubroDao;
    private INegocioDao _negocioDao;
    private IMarcaDao _marcaDao;
    private IProveedorDao _proveedorDao;
    private IStockDao _stockDao;

    public ArticuloRepositoryImpl(AppDataBase db) {
        this._db = db;
        if (db != null) {
            this._articuloDao = db.articuloDao();
            this._subrubroDao = db.subrubroDao();
            this._rubroDao = db.rubroDao();
            this._negocioDao = db.negocioDao();
            this._marcaDao = db.marcaDao();
            this._stockDao = db.stockDao();
            this._proveedorDao = db.proveedorDao();
        }
    }

    public Integer create(Articulo articulo) throws Exception {
        return null;
    }

    public List<Articulo> recoveryAll() throws Exception {
        List<ArticuloResultBd> listadoArticulosBd = _articuloDao.recoveryAll();
        List<Articulo> articulos = new ArrayList<>();
        if (!listadoArticulosBd.isEmpty()) {
            for (ArticuloResultBd item : listadoArticulosBd) {
                articulos.add(mappingToDto(item));
            }
        }
        return articulos;
    }

    public List<Articulo> recoveryAll(ListaPrecio listaPrecioXDefecto) throws Exception {
        List<ArticuloResultBd> listadoArticulosBd = _articuloDao.recoveryAll(listaPrecioXDefecto.getId());
        List<Articulo> articulos = new ArrayList<>();
        if (!listadoArticulosBd.isEmpty()) {
            for (ArticuloResultBd item : listadoArticulosBd) {
                articulos.add(mappingToDto(item));
            }
        }
        return articulos;
    }

    public List<Articulo> recoveryCantidadesAcumuladasPorCliente(Cliente cliente) throws Exception {
        List<ArticuloResultBd> listadoArticulosBd;
        if (cliente != null) {
            listadoArticulosBd = _articuloDao.recoveryCantidadesAcumuladasPorCliente(cliente.getId().getIdSucursal(),
                    cliente.getId().getIdCliente(),
                    cliente.getId().getIdComercio());
        } else {
            listadoArticulosBd = _articuloDao.recoveryCantidadesAcumuladasPorCliente(0, 0, 0);
        }
        List<Articulo> articulos = new ArrayList<>();
        if (!listadoArticulosBd.isEmpty()) {
            for (ArticuloResultBd item : listadoArticulosBd) {
                articulos.add(mappingToDto(item));
            }
        }
        return articulos;
    }

    public void updateStock(VentaDetalle ventaDetalle) throws Exception {
        int tipoOperacion = -1;
        if (ventaDetalle.getTipoOperacion() == TipoOperacion.delete) {
            tipoOperacion = 1;
        }
        _stockDao.updateStock(ventaDetalle.getArticulo().getId(), ventaDetalle.getCantidad(), tipoOperacion);
    }

    @Override
    public void updateStock(Articulo articulo, double stock) throws Exception {
        _articuloDao.updateStock(articulo.getId(), stock);
    }

    public Articulo recoveryByID(Integer id) throws Exception {
        return mappingToDto(_articuloDao.recoveryByID(id));
    }

    public void update(Articulo entity) throws Exception {

    }


    public void delete(Articulo entity) throws Exception {

    }

    @Override
    public void delete(Integer id) throws Exception {

    }

    @Override
    public boolean isArticuloInSubrubro(int idArticulo, int idSubrubro) throws Exception {
        boolean exists = false;
        int resul = _articuloDao.isArticuloInSubrubro(idArticulo, idSubrubro);
        if (resul == 1) {
            exists = true;
        }
        return exists;
    }

    @Override
    public List<Articulo> recoveryCantidadesPorCliente(Cliente cliente) throws Exception {
        List<ArticuloResultBd> listadoArticulosBd;
        if (cliente != null) {
            listadoArticulosBd = _articuloDao.recoveryCantidadesPorCliente(cliente.getId().getIdSucursal(),
                    cliente.getId().getIdCliente(), cliente.getId().getIdComercio());
        } else {
            listadoArticulosBd = _articuloDao.recoveryCantidadesPorCliente(0, 0, 0);
        }
        List<Articulo> articulos = new ArrayList<>();
        if (!listadoArticulosBd.isEmpty()) {
            for (ArticuloResultBd item : listadoArticulosBd) {
                articulos.add(mappingToDto(item));
            }
        }
        return articulos;
    }

    @Override
    public double recoveryStockById(int idArticulo) throws Exception {
        return _articuloDao.recoveryStockById(idArticulo);
    }

    @Override
    public List<Articulo> recoveryAllByCliente(Cliente cliente) throws Exception {
        List<ArticuloResultBd> listadoArticulosBd;
        if (cliente != null) {
            listadoArticulosBd = _articuloDao.recoveryAllByCliente(cliente.getId().getIdSucursal(),
                    cliente.getId().getIdCliente(), cliente.getId().getIdComercio());
        } else {
            listadoArticulosBd = _articuloDao.recoveryAllByCliente(0, 0, 0);
        }
        List<Articulo> articulos = new ArrayList<>();
        if (!listadoArticulosBd.isEmpty()) {
            for (ArticuloResultBd item : listadoArticulosBd) {
                articulos.add(mappingToDto(item));
            }
        }
        return articulos;
    }


    public Articulo mappingToDto(ArticuloResultBd articuloBd) throws Exception {
        Articulo articulo = new Articulo();
        articulo.setId(articuloBd.getId());

        //Negocio
        Negocio negocio = new Negocio();
        negocio.setId(articuloBd.getIdNegocio());
        negocio.setDescripcion(articuloBd.getDescripcionNegocio());

        //Rubro
        PkRubro pkRubro = new PkRubro();
        pkRubro.setIdNegocio(articuloBd.getIdNegocio());
        pkRubro.setIdRubro(articuloBd.getIdRubro());
        Rubro rubro = new Rubro();
        rubro.setId(pkRubro);
        rubro.setDescripcion(articuloBd.getDescripcionRubro());
        rubro.setNegocio(negocio);

        //Subrubro
        PkSubrubro pkSubrubro = new PkSubrubro();
        pkSubrubro.setIdSubrubro(articuloBd.getIdSubrubro());
        pkSubrubro.setIdRubro(articuloBd.getIdRubro());
        pkSubrubro.setIdNegocio(articuloBd.getIdNegocio());

        Subrubro subrubro = new Subrubro();
        subrubro.setId(pkSubrubro);
        subrubro.setDescripcion(articuloBd.getDescripcionSubrubro());
        subrubro.setRubro(rubro);

        /*cargo subrubro*/
        articulo.setSubrubro(subrubro);

        /*cargo marca*/
        Marca marca = new Marca();
        marca.setId(articuloBd.getIdLinea());
        marca.setDescripcion(articuloBd.getDescripcionMarca());
        articulo.setMarca(marca);

        articulo.setDescripcion(articuloBd.getDescripcion());
        articulo.setTasaIva(articuloBd.getTasaIva());
        articulo.setCodigoEmpresa(articuloBd.getCodigoEmpresa());
        articulo.setStock(articuloBd.getStock() == null ? 0d : articuloBd.getStock());
        articulo.setPromocion(articuloBd.getSnPromocion() != null &&
                !articuloBd.getSnPromocion().equalsIgnoreCase("N"));
        articulo.setImpuestoInterno(articuloBd.getImpuestoInterno());
        articulo.setCritico(articuloBd.getSnCritico() != null &&
                !articuloBd.getSnCritico().equalsIgnoreCase("N"));
        articulo.setPesable(articuloBd.getSnPesable() != null &&
                !articuloBd.getSnPesable().equalsIgnoreCase("N"));
        articulo.setSnUnidad(articuloBd.getSnUnidad());
        articulo.setTasaImpuestoInterno(articuloBd.getTasaImpuestoInterno() == null ? 0d : articuloBd.getTasaImpuestoInterno());
        articulo.setCantidadKilos(articuloBd.getCantidadKilos());
        articulo.setPrecioBase(articuloBd.getPrecioBase() == null ? 0d : articuloBd.getPrecioBase());

        /*cargo proveedor*/
        Proveedor proveedor = new Proveedor();
        proveedor.setIdProveedor(articuloBd.getIdProveedor());
        proveedor.setDeProveedor(articuloBd.getDeProveedor());
        proveedor.setTiProveedor(articuloBd.getTiProveedor());
        proveedor.setIdPlancta(articuloBd.getIdPlancta());
        proveedor.setIdSucursal(articuloBd.getIdSucursal());
        proveedor.setNuCuit(articuloBd.getNuCuit());
        articulo.setProveedor(proveedor);

        articulo.setPrecioCosto(articuloBd.getPrecioCosto() == null ? 0d : articuloBd.getPrecioCosto());
        articulo.setTasaCosto(articuloBd.getTasaCosto() == null ? 0d : articuloBd.getTasaCosto());
        articulo.setSnKit(articuloBd.getSnKit());
        articulo.setAlcohol(articuloBd.getSnAlcohol() != null &&
                !articuloBd.getSnAlcohol().equalsIgnoreCase("N"));
        articulo.setUnidadPorBulto(articuloBd.getUnidadPorBulto());

        articulo.setCantidadVendida(articuloBd.getCantidadVendida() == null
                ? 0
                : articuloBd.getCantidadVendida());
        articulo.setFechaUltimaVenta(articuloBd.getFechaUltimaVenta() == null
                ? null
                : Formatter.convertToDateWs(articuloBd.getFechaUltimaVenta()));
        return articulo;
    }

    @Override
    public Articulo mappingToDtoSinClases(ArticuloBd articuloBd) {
        Articulo articulo = new Articulo();
        articulo.setId(articuloBd.getId());
        articulo.setDescripcion(articuloBd.getDescripcion());
        articulo.setTasaIva(articuloBd.getTasaIva());
        articulo.setCodigoEmpresa(articuloBd.getCodigoEmpresa());
        articulo.setStock(articuloBd.getStock());
        articulo.setPromocion(articuloBd.getSnPromocion() != null &&
                !articuloBd.getSnPromocion().equalsIgnoreCase("N"));
        articulo.setImpuestoInterno(articuloBd.getImpuestoInterno());
        articulo.setCritico(articuloBd.getSnCritico() != null &&
                !articuloBd.getSnCritico().equalsIgnoreCase("N"));
        articulo.setPesable(articuloBd.getSnPesable() != null &&
                !articuloBd.getSnPesable().equalsIgnoreCase("N"));
        articulo.setSnUnidad(articuloBd.getSnUnidad());
        articulo.setTasaImpuestoInterno(articuloBd.getTasaImpuestoInterno());
        articulo.setCantidadKilos(articuloBd.getCantidadKilos());
        articulo.setPrecioBase(articuloBd.getPrecioBase());
        articulo.setPrecioCosto(articuloBd.getPrecioCosto());
        articulo.setTasaCosto(articuloBd.getTasaCosto());
        articulo.setSnKit(articuloBd.getSnKit());
        articulo.setAlcohol(articuloBd.getSnAlcohol() != null &&
                !articuloBd.getSnAlcohol().equalsIgnoreCase("N"));
        articulo.setUnidadPorBulto(articuloBd.getUnidadPorBulto());
        return articulo;
    }
}
