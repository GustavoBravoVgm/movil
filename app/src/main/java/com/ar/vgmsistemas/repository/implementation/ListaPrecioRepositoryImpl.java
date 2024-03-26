package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IListaPrecioDao;
import com.ar.vgmsistemas.database.dao.entity.ListaPrecioBd;
import com.ar.vgmsistemas.entity.ListaPrecio;
import com.ar.vgmsistemas.repository.IListaPrecioRepository;

import java.util.ArrayList;
import java.util.List;

public class ListaPrecioRepositoryImpl implements IListaPrecioRepository {
    private IListaPrecioDao _listaPrecioDao;

    public ListaPrecioRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._listaPrecioDao = db.listaPrecioDao();
        }
    }

    public Integer create(ListaPrecio listaPrecio) throws Exception {
        return null;
    }

    public void delete(ListaPrecio entity) throws Exception {

    }

    public void delete(Integer id) throws Exception {

    }

    public List<ListaPrecio> recoveryAll() throws Exception {
        List<ListaPrecioBd> listadoListaPreciosBd = _listaPrecioDao.recoveryAll();
        List<ListaPrecio> listasDePrecios = new ArrayList<>();
        if (!listadoListaPreciosBd.isEmpty()) {
            for (ListaPrecioBd item : listadoListaPreciosBd) {
                listasDePrecios.add(mappingToDto(item));
            }
        }
        return listasDePrecios;
    }

    public List<ListaPrecio> recoveryAllSeleccionable() throws Exception {
        List<ListaPrecioBd> listadoListaPreciosBd = _listaPrecioDao.recoveryAllSeleccionable();
        List<ListaPrecio> listasDePrecios = new ArrayList<>();
        if (!listadoListaPreciosBd.isEmpty()) {
            for (ListaPrecioBd item : listadoListaPreciosBd) {
                listasDePrecios.add(mappingToDto(item));
            }
        }
        return listasDePrecios;
    }

    public ListaPrecio recoveryByID(Integer id) throws Exception {
        return mappingToDto(_listaPrecioDao.recoveryByID(id));
    }


    public void update(ListaPrecio listaPrecio) throws Exception {
    }

    @Override
    public ListaPrecio mappingToDto(ListaPrecioBd listaPrecioBd) {
        if (listaPrecioBd != null) {
            ListaPrecio listaPrecio = new ListaPrecio();
            listaPrecio.setId(listaPrecioBd.getId());
            listaPrecio.setDescripcion(listaPrecioBd.getDescripcion());
            listaPrecio.setTipoLista(listaPrecioBd.getTipoLista());
            listaPrecio.setListaBase(listaPrecioBd.getListaBase() == null
                    ? listaPrecioBd.getId()
                    : listaPrecioBd.getListaBase());
            listaPrecio.setIdOrigenPrecio(listaPrecioBd.getIdOrigenPrecio());
            listaPrecio.setSnSeleccionable(listaPrecioBd.getSnSeleccionable() == null
                    ? "N"
                    : listaPrecioBd.getSnSeleccionable());
            listaPrecio.setSnMovil(listaPrecioBd.getSnMovil() == null
                    ? "N"
                    : listaPrecioBd.getSnMovil());
            return listaPrecio;
        }
        return null;
    }

}
