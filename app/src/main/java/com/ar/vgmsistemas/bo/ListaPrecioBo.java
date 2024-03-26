package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.ListaPrecio;
import com.ar.vgmsistemas.repository.IListaPrecioRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.List;

public class ListaPrecioBo {
    private final IListaPrecioRepository _listaPrecioRepository;

    public ListaPrecioBo(RepositoryFactory repoFactory) {
        this._listaPrecioRepository = repoFactory.getListaPrecioRepository();
    }

    public List<ListaPrecio> recoveryAll() throws Exception {
        return _listaPrecioRepository.recoveryAll();
    }

    public ListaPrecio recoveryById(int id) throws Exception {
        return _listaPrecioRepository.recoveryByID(id);
    }

    public List<ListaPrecio> recoveryAllSeleccionable() throws Exception {
        return _listaPrecioRepository.recoveryAllSeleccionable();
    }

}
