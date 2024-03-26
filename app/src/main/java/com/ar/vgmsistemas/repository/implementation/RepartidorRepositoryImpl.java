package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IRepartidorDao;
import com.ar.vgmsistemas.database.dao.entity.RepartidorBd;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.RecursoHumano;
import com.ar.vgmsistemas.entity.Repartidor;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.repository.IRepartidorRepository;

import java.util.ArrayList;
import java.util.List;

public class RepartidorRepositoryImpl implements IRepartidorRepository {
    private final AppDataBase _db;
    private IRepartidorDao _repartidorDao;

    public RepartidorRepositoryImpl(AppDataBase db) {
        this._db = db;
        if (db != null) {
            this._repartidorDao = db.repartidorDao();
        }
    }

    public Integer create(Repartidor repartidor) throws Exception {
        return null;
    }

    public void delete(Repartidor entity) throws Exception {

    }

    public void delete(Integer id) throws Exception {

    }

    public Repartidor recoveryByCliente(Cliente cliente) throws Exception {
        return mappingToDto(_repartidorDao.recoveryByCliente(cliente.getId().getIdSucursal(),
                cliente.getId().getIdCliente(), cliente.getId().getIdComercio()));
    }


    public Repartidor recoveryByVenta(Venta venta) throws Exception {
        return mappingToDto(_repartidorDao.recoveryByVenta(venta.getId().getIdDocumento(),
                venta.getId().getIdLetra(), venta.getId().getPuntoVenta(),
                venta.getId().getIdNumeroDocumento()));
    }


    public List<Repartidor> recoveryAll() throws Exception {
        List<RepartidorBd> listadoRepartidores = _repartidorDao.recoveryAll();
        List<Repartidor> repartidores = new ArrayList<>();
        if (!listadoRepartidores.isEmpty()) {
            for (RepartidorBd item : listadoRepartidores) {
                repartidores.add(mappingToDto(item));
            }
        }
        return repartidores;
    }

    public Repartidor recoveryByID(Integer id) throws Exception {
        return null;
    }

    public void update(Repartidor entity) throws Exception {

    }

    @Override
    public Repartidor mappingToDto(RepartidorBd repartidorBd) throws Exception {
        if (repartidorBd != null) {
            Repartidor repartidor = new Repartidor();/*new Repartidor(repartidorBd.getId(), repartidorBd.getSnMovil());*/
            //cargo rrhh
            RecursoHumanoRepositoryImpl recursoHumanoRepository = new RecursoHumanoRepositoryImpl(this._db);
            RecursoHumano rh = recursoHumanoRepository.mappingToDto(this._db.recursoHumanoDao().recoveryByID(repartidorBd.getId()));
            repartidor.setNombre(rh.getNombre());
            repartidor.setApellido(rh.getApellido());
            repartidor.setDomicilio(rh.getDomicilio());
            repartidor.setNumeroDocumento(rh.getNumeroDocumento());
            repartidor.setId(repartidorBd.getId());
            return repartidor;
        }
        return new Repartidor();
    }
}