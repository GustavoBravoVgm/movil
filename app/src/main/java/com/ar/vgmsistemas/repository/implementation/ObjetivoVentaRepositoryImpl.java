package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IObjetivoVentaDao;
import com.ar.vgmsistemas.database.dao.entity.ObjetivoVentaBd;
import com.ar.vgmsistemas.database.dao.entity.key.PkObjetivoVentaBd;
import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.ObjetivoVenta;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.entity.key.PkObjetivoVenta;
import com.ar.vgmsistemas.repository.IObjetivoVentaRepository;

import java.util.ArrayList;
import java.util.List;


public class ObjetivoVentaRepositoryImpl implements IObjetivoVentaRepository {
    private final AppDataBase _db;
    private IObjetivoVentaDao _objetivoVentaDao;

    public ObjetivoVentaRepositoryImpl(AppDataBase db) {
        this._db = db;
        if (db != null) {
            this._objetivoVentaDao = db.objetivoVentaDao();
        }
    }

    public List<ObjetivoVenta> recoveryByCliente(Cliente cliente) throws Exception {
        List<ObjetivoVentaBd> listadoObjetivos = _objetivoVentaDao.recoveryByCliente(cliente.getId().getIdSucursal(),
                cliente.getId().getIdCliente(), cliente.getId().getIdComercio());
        List<ObjetivoVenta> objetivos = new ArrayList<>();
        if (!listadoObjetivos.isEmpty()) {
            for (ObjetivoVentaBd item : listadoObjetivos) {
                objetivos.add(mappingToDto(item));
            }
        }
        return objetivos;
    }

    public ObjetivoVenta recoveryByID(PkObjetivoVenta id) throws Exception {
        return mappingToDto(_objetivoVentaDao.recoveryByID(id.getIdArticulos(), id.getIdSucursal(),
                id.getIdCliente(), id.getIdComercio(), id.getIdObjetivo()));
    }

    public List<ObjetivoVenta> recoveryAll() throws Exception {
        return null;
    }

    @Override
    public PkObjetivoVenta create(ObjetivoVenta entity) throws Exception {
        return null;
    }

    @Override
    public void update(ObjetivoVenta entity) throws Exception {
    }

    @Override
    public void delete(ObjetivoVenta entity) throws Exception {
    }

    @Override
    public void delete(PkObjetivoVenta id) throws Exception {
    }

    @Override
    public List<ObjetivoVenta> recoveryByClienteArticulo(Cliente cliente, Articulo articulo) {
        return null;
    }

    @Override
    public void updateCantidadVendida(VentaDetalle ventaDetalle, Cliente cliente, int signo) throws Exception {
        ObjetivoVenta mObjVta = mappingToDto(_objetivoVentaDao.recoveryByID(ventaDetalle.getArticulo().getId(), cliente.getId().getIdSucursal(),
                cliente.getId().getIdCliente(), cliente.getId().getIdComercio(), 0));
        if (mObjVta != null && mObjVta.getId() != null) {
            double cantidadVendida = mObjVta.getCantidadVendida() + (ventaDetalle.getCantidad() * signo);
            mObjVta.setCantidadVendida(cantidadVendida);
            _objetivoVentaDao.update(mappingToDb(mObjVta));
        }
    }

    @Override
    public List<ObjetivoVenta> recoveryNoCumplidosByCliente(Cliente cliente) throws Exception {
        List<ObjetivoVentaBd> listadoObjetivos = _objetivoVentaDao.recoveryNoCumplidosByCliente(cliente.getId().getIdSucursal(),
                cliente.getId().getIdCliente(), cliente.getId().getIdComercio());
        List<ObjetivoVenta> objetivos = new ArrayList<>();
        if (!listadoObjetivos.isEmpty()) {
            for (ObjetivoVentaBd item : listadoObjetivos) {
                objetivos.add(mappingToDto(item));
            }
        }
        return objetivos;
    }

    @Override
    public int getCantNoCumplidosByCliente(Cliente cliente) throws Exception {
        return _objetivoVentaDao.getCantNoCumplidosByCliente(cliente.getId().getIdSucursal(),
                cliente.getId().getIdCliente(), cliente.getId().getIdComercio());
    }

    public ObjetivoVenta mappingToDto(ObjetivoVentaBd objetivoVentaBd) throws Exception {
        ObjetivoVenta objetivoVenta = new ObjetivoVenta();
        if (objetivoVentaBd != null) {
            PkObjetivoVenta id = new PkObjetivoVenta(objetivoVentaBd.getId().getIdArticulos(),
                    objetivoVentaBd.getId().getIdSucursal(), objetivoVentaBd.getId().getIdCliente(),
                    objetivoVentaBd.getId().getIdComercio(), objetivoVentaBd.getId().getIdObjetivo());
            objetivoVenta.setId(id);
            objetivoVenta.setCantidad(objetivoVentaBd.getCantidad());
            objetivoVenta.setCantidadVendida(objetivoVentaBd.getCantidadVendida());
            //cargo cliente
            ClienteRepositoryImpl clienteRepository = new ClienteRepositoryImpl(this._db);
            Cliente cliente = clienteRepository.mappingToDto(this._db.clienteDao().recoveryByID(objetivoVentaBd.getId().getIdSucursal(),
                    objetivoVentaBd.getId().getIdCliente(), objetivoVentaBd.getId().getIdComercio()));
            objetivoVenta.setCliente(cliente);
            //cargo articulos
            ArticuloRepositoryImpl articuloRepository = new ArticuloRepositoryImpl(this._db);
            Articulo articulo = articuloRepository.mappingToDto(this._db.articuloDao().recoveryByID(objetivoVentaBd.getId().getIdArticulos()));
            objetivoVenta.setArticulo(articulo);
        }
        return objetivoVenta;
    }

    private ObjetivoVentaBd mappingToDb(ObjetivoVenta objetivoVenta) {
        if (objetivoVenta != null && objetivoVenta.getId() != null) {
            PkObjetivoVentaBd id = new PkObjetivoVentaBd();

            id.setIdArticulos(objetivoVenta.getId().getIdArticulos());
            id.setIdSucursal(objetivoVenta.getId().getIdSucursal());
            id.setIdCliente(objetivoVenta.getId().getIdCliente());
            id.setIdComercio(objetivoVenta.getId().getIdComercio());
            id.setIdObjetivo(objetivoVenta.getId().getIdObjetivo());

            ObjetivoVentaBd objetivo = new ObjetivoVentaBd();
            objetivo.setId(id);
            objetivo.setCantidad(objetivoVenta.getCantidad());
            objetivo.setCantidadVendida(objetivoVenta.getCantidadVendida());
            return objetivo;
        }
        return null;
    }
}
