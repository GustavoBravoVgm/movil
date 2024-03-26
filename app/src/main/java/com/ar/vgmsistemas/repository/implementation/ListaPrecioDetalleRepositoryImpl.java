package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IListaPrecioDetalleDao;
import com.ar.vgmsistemas.database.dao.entity.ListaPrecioDetalleBd;
import com.ar.vgmsistemas.entity.ListaPrecio;
import com.ar.vgmsistemas.entity.ListaPrecioDetalle;
import com.ar.vgmsistemas.entity.Subrubro;
import com.ar.vgmsistemas.entity.key.PkListaPrecioDetalle;
import com.ar.vgmsistemas.repository.IListaPrecioDetalleRepository;
import com.ar.vgmsistemas.utils.Formatter;

import java.util.ArrayList;
import java.util.List;

public class ListaPrecioDetalleRepositoryImpl implements IListaPrecioDetalleRepository {
    private final AppDataBase _db;
    private IListaPrecioDetalleDao _listaPrecioDetalleDao;


    public ListaPrecioDetalleRepositoryImpl(AppDataBase db) {
        this._db = db;
        if (db != null) {
            this._listaPrecioDetalleDao = db.listaPrecioDetalleDao();
        }
    }

    public PkListaPrecioDetalle create(ListaPrecioDetalle entity) throws Exception {
        return null;
    }


    public void delete(ListaPrecioDetalle entity) throws Exception {
    }


    public void delete(PkListaPrecioDetalle id) throws Exception {
    }


    public List<ListaPrecioDetalle> recoveryAll() throws Exception {
        return null;
    }


    public List<ListaPrecioDetalle> recoveryByArticulo(int idArticulo) throws Exception {
        List<ListaPrecioDetalleBd> listadoBd = _listaPrecioDetalleDao.recoveryByArticulo(idArticulo);
        List<ListaPrecioDetalle> listasPreciosDetalles = new ArrayList<>();
        if (!listadoBd.isEmpty()) {
            for (ListaPrecioDetalleBd item : listadoBd) {
                listasPreciosDetalles.add(mappingToDto(item));
            }
        }
        return listasPreciosDetalles;
    }

    public ListaPrecioDetalle recoveryByID(PkListaPrecioDetalle id) throws Exception {
        return mappingToDto(_listaPrecioDetalleDao.recoveryByID(id.getIdLista(), id.getIdArticulo(),
                id.getCaArticuloDesde(), id.getCaArticuloHasta()));
    }


    @Override
    public List<ListaPrecioDetalle> recoveryByArticuloAndLista(int idLista, int idArticulo)
            throws Exception {
        List<ListaPrecioDetalleBd> listadoBd = _listaPrecioDetalleDao.recoveryByArticuloAndLista(idLista, idArticulo);
        List<ListaPrecioDetalle> listasPreciosDetalles = new ArrayList<>();
        if (!listadoBd.isEmpty()) {
            for (ListaPrecioDetalleBd item : listadoBd) {
                listasPreciosDetalles.add(mappingToDto(item));
            }
        }
        return listasPreciosDetalles;
    }

    public void update(ListaPrecioDetalle entity) throws Exception {
    }

    @Override
    public List<ListaPrecioDetalle> recoveryByListaPrecio(ListaPrecio listaPrecio, Integer idProveedor)
            throws Exception {
        List<ListaPrecioDetalleBd> listadoBd = _listaPrecioDetalleDao.recoveryByListaPrecio(listaPrecio.getId(),
                idProveedor, listaPrecio.getTipoLista());
        List<ListaPrecioDetalle> listasPreciosDetalles = new ArrayList<>();
        if (!listadoBd.isEmpty()) {
            for (ListaPrecioDetalleBd item : listadoBd) {
                listasPreciosDetalles.add(mappingToDto(item));
            }
        }
        return listasPreciosDetalles;
    }

    @Override
    public List<ListaPrecioDetalle> recoveryByListaPrecioSubrubro(ListaPrecio listaPrecio,
                                                                  Subrubro subrubro, Integer idProveedor)
            throws Exception {
        List<ListaPrecioDetalleBd> listadoBd = _listaPrecioDetalleDao.recoveryByListaPrecioSubrubro(
                listaPrecio.getId(), idProveedor, subrubro.getId().getIdSubrubro(), listaPrecio.getTipoLista());
        List<ListaPrecioDetalle> listasPreciosDetalles = new ArrayList<>();
        if (!listadoBd.isEmpty()) {
            for (ListaPrecioDetalleBd item : listadoBd) {
                listasPreciosDetalles.add(mappingToDto(item));
            }
        }
        return listasPreciosDetalles;
    }


    @Override
    public List<ListaPrecioDetalle> recoveryByArticuloMovil(int idArticulo) throws Exception {
        List<ListaPrecioDetalleBd> listadoBd = _listaPrecioDetalleDao.recoveryByArticuloMovil(idArticulo);
        List<ListaPrecioDetalle> listasPreciosDetalles = new ArrayList<>();
        if (!listadoBd.isEmpty()) {
            for (ListaPrecioDetalleBd item : listadoBd) {
                listasPreciosDetalles.add(mappingToDto(item));
            }
        }
        return listasPreciosDetalles;
    }

    @Override
    public List<ListaPrecioDetalle> recoveryById(int idArticulo, int idLista) throws Exception {
        List<ListaPrecioDetalleBd> listadoBd = _listaPrecioDetalleDao.recoveryById(idArticulo, idLista);
        List<ListaPrecioDetalle> listasPreciosDetalles = new ArrayList<>();
        if (!listadoBd.isEmpty()) {
            for (ListaPrecioDetalleBd item : listadoBd) {
                listasPreciosDetalles.add(mappingToDto(item));
            }
        }
        return listasPreciosDetalles;
    }

    @Override
    public ListaPrecioDetalle recoveryById(int idArticulo, int idLista, int caArticuloDesde,
                                           int caArticuloHasta) throws Exception {
        return mappingToDto(_listaPrecioDetalleDao.recoveryByID(idLista, idArticulo, caArticuloDesde, caArticuloHasta));
    }

    @Override
    public List<ListaPrecioDetalle> recoveryPromocionesYCombos() throws Exception {
        List<ListaPrecioDetalleBd> listadoBd = _listaPrecioDetalleDao.recoveryPromocionesYCombos();
        List<ListaPrecioDetalle> listasPreciosDetalles = new ArrayList<>();
        if (!listadoBd.isEmpty()) {
            for (ListaPrecioDetalleBd item : listadoBd) {
                listasPreciosDetalles.add(mappingToDto(item));
            }
        }
        return listasPreciosDetalles;
    }

    @Override
    public ListaPrecioDetalle mappingToDto(ListaPrecioDetalleBd listaPrecioDetalleBd) throws Exception {
        ListaPrecioDetalle listaPrecioDetalle = new ListaPrecioDetalle();
        if (listaPrecioDetalleBd != null) {
            PkListaPrecioDetalle id = new PkListaPrecioDetalle(listaPrecioDetalleBd.getId().getIdLista(),
                    listaPrecioDetalleBd.getId().getIdArticulo(), listaPrecioDetalleBd.getId().getCaArticuloDesde(),
                    listaPrecioDetalleBd.getId().getCaArticuloHasta());
            listaPrecioDetalle.setId(id);
            listaPrecioDetalle.setPrecioFinal(listaPrecioDetalleBd.getPrecioFinal());
            listaPrecioDetalle.setPrecioSinIva(listaPrecioDetalleBd.getPrecioSinIva());
            listaPrecioDetalle.setCaLista(listaPrecioDetalleBd.getCaLista());
            listaPrecioDetalle.setCaVendido(listaPrecioDetalleBd.getCaVendido());
            listaPrecioDetalle.setSnMovil(listaPrecioDetalleBd.getSnMovil());
            listaPrecioDetalle.setFechaVigenciaDesde(listaPrecioDetalleBd.getFechaVigenciaDesde() == null
                    ? null
                    : Formatter.convertToDate(listaPrecioDetalleBd.getFechaVigenciaDesde()));
            listaPrecioDetalle.setFechaVigenciaHasta(listaPrecioDetalleBd.getFechaVigenciaHasta() == null
                    ? null
                    : Formatter.convertToDate(listaPrecioDetalleBd.getFechaVigenciaHasta()));
            listaPrecioDetalle.setIsTodosLosVendedores(listaPrecioDetalleBd.getSnTodosLosVendedores() == null ||
                    listaPrecioDetalleBd.getSnTodosLosVendedores().equalsIgnoreCase("S"));
            listaPrecioDetalle.setTasaDtoProveedor(listaPrecioDetalleBd.getTasaDtoProveedor());
            listaPrecioDetalle.setTasaDtoCliente(listaPrecioDetalleBd.getTasaDtoCliente());
            //cargo articulos
            ArticuloRepositoryImpl articuloRepository = new ArticuloRepositoryImpl(this._db);
            listaPrecioDetalle.setArticulo(articuloRepository.mappingToDto(
                    this._db.articuloDao().recoveryByID(listaPrecioDetalleBd.getId().getIdArticulo())));
            //cargo lista precios
            ListaPrecioRepositoryImpl listaPrecioRepository = new ListaPrecioRepositoryImpl(this._db);
            listaPrecioDetalle.setListaPrecio(listaPrecioRepository.mappingToDto(this._db.listaPrecioDao().recoveryByID(
                    listaPrecioDetalleBd.getId().getIdLista())));
        }
        return listaPrecioDetalle;
    }
}
