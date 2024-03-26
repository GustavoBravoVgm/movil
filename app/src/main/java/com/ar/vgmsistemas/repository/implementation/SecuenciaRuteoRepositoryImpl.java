package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.ISecuenciaRuteoDao;
import com.ar.vgmsistemas.database.dao.entity.SecuenciaRuteoBd;
import com.ar.vgmsistemas.database.dao.entity.result.SecuenciaRuteoResultBd;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.Localidad;
import com.ar.vgmsistemas.entity.SecuenciaRuteo;
import com.ar.vgmsistemas.entity.key.PkSecuenciaRuteo;
import com.ar.vgmsistemas.repository.ISecuenciaRuteoRepository;

import java.util.ArrayList;
import java.util.List;

public class SecuenciaRuteoRepositoryImpl implements ISecuenciaRuteoRepository {
    private final AppDataBase _db;
    private ISecuenciaRuteoDao _secuenciaRuteoDao;

    public SecuenciaRuteoRepositoryImpl(AppDataBase db) {
        this._db = db;
        if (db != null) {
            this._secuenciaRuteoDao = db.secuenciaRuteoDao();
        }
    }

    @Override
    public PkSecuenciaRuteo create(SecuenciaRuteo entity) throws Exception {
        return null;
    }

    @Override
    public SecuenciaRuteo recoveryByID(PkSecuenciaRuteo pkSecuenciaRuteo) throws Exception {
        return null;
    }

    @Override
    public List<SecuenciaRuteo> recoveryAll() throws Exception {
        return null;
    }

    @Override
    public void update(SecuenciaRuteo entity) throws Exception {

    }

    @Override
    public void delete(SecuenciaRuteo entity) throws Exception {

    }

    @Override
    public void delete(PkSecuenciaRuteo pkSecuenciaRuteo) throws Exception {

    }

    @Override
    public List<SecuenciaRuteo> recoveryRutasByDia(int dia, int idLocalidad,
                                                   int idSucursal) throws Exception {
        List<SecuenciaRuteoResultBd> listadoSecuencias = _secuenciaRuteoDao.recoveryRutasByDia(dia, idLocalidad, idSucursal);
        List<SecuenciaRuteo> secuencias = new ArrayList<>();
        if (!listadoSecuencias.isEmpty()) {
            for (SecuenciaRuteoResultBd item : listadoSecuencias) {
                secuencias.add(mappingToDtoResult(item));
            }
        }
        return secuencias;
    }


    /*public SecuenciaRuteo mappingToDto(SecuenciaRuteoBd secuenciaRuteoBd) throws Exception {
        SecuenciaRuteo secuenciaRuteo = new SecuenciaRuteo();
        if (secuenciaRuteoBd != null) {
            PkSecuenciaRuteo id = new PkSecuenciaRuteo(secuenciaRuteoBd.getId().getIdVendedor(),
                    secuenciaRuteoBd.getId().getIdSucursal(), secuenciaRuteoBd.getId().getIdCliente(),
                    secuenciaRuteoBd.getId().getIdComercio());
            secuenciaRuteo.setId(id);
            secuenciaRuteo.setDia(secuenciaRuteoBd.getDia());
            secuenciaRuteo.setNumeroOrden(secuenciaRuteoBd.getNumeroOrden());
            //cargo cliente
            ClienteRepositoryImpl clienteRepository = new ClienteRepositoryImpl(this._db);
            secuenciaRuteo.setCliente(clienteRepository.mappingToDto(this._db.clienteDao().recoveryByID(secuenciaRuteoBd.getId().getIdSucursal(),
                    secuenciaRuteoBd.getId().getIdCliente(), secuenciaRuteoBd.getId().getIdComercio())));
            return secuenciaRuteo;
        }
        return secuenciaRuteo;

    }*/

    public SecuenciaRuteo mappingToDtoResult(SecuenciaRuteoResultBd secuenciaRuteoResultBd) throws Exception {
        SecuenciaRuteo secuenciaRuteo = new SecuenciaRuteo();
        if (secuenciaRuteoResultBd != null) {
            PkSecuenciaRuteo id = new PkSecuenciaRuteo(secuenciaRuteoResultBd.getIdVendedor(),
                    secuenciaRuteoResultBd.getIdSucursal(), secuenciaRuteoResultBd.getIdCliente(),
                    secuenciaRuteoResultBd.getIdComercio());
            secuenciaRuteo.setId(id);
            secuenciaRuteo.setDia(secuenciaRuteoResultBd.getDia());
            secuenciaRuteo.setNumeroOrden(secuenciaRuteoResultBd.getNumeroOrden());
            //cargo localidad
            LocalidadRepositoryImpl localidadRepository = new LocalidadRepositoryImpl(this._db);
            Localidad localidad;
            localidad = localidadRepository.mappingToDto(this._db.localidadDao().recoveryByID(secuenciaRuteoResultBd.getIdPostal()));
            //cargo cliente
            ClienteRepositoryImpl clienteRepository = new ClienteRepositoryImpl(this._db);
            Cliente cliente;
            cliente = clienteRepository.mappingToDto(this._db.clienteDao().recoveryByID(
                    secuenciaRuteoResultBd.getIdSucursal(), secuenciaRuteoResultBd.getIdCliente(),
                    secuenciaRuteoResultBd.getIdComercio()));
            if (cliente != null) {
                cliente.setLocalidad(localidad);
            }
            secuenciaRuteo.setCliente(cliente);

            return secuenciaRuteo;
        }
        return secuenciaRuteo;

    }
}
