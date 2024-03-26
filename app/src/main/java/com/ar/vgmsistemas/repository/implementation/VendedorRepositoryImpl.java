package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IVendedorDao;
import com.ar.vgmsistemas.database.dao.entity.VendedorBd;
import com.ar.vgmsistemas.entity.RecursoHumano;
import com.ar.vgmsistemas.entity.Vendedor;
import com.ar.vgmsistemas.entity.key.PkVenta;
import com.ar.vgmsistemas.repository.IVendedorRepository;

import java.util.List;

public class VendedorRepositoryImpl implements IVendedorRepository {
    private final AppDataBase _db;
    private IVendedorDao _vendedorDao;

    public VendedorRepositoryImpl(AppDataBase db) {
        this._db = db;
        if (db != null) {
            this._vendedorDao = db.vendedorDao();
        }
    }

    public Integer create(Vendedor entity) throws Exception {
        return null;
    }

    public void delete(Vendedor entity) throws Exception {

    }

    public void delete(Integer id) throws Exception {

    }

    public Vendedor recoveryByLogin(String nombreUsuario, String password) throws Exception {
        return mappingToDto(_vendedorDao.recoveryByLogin(nombreUsuario, password));
    }

    public List<Vendedor> recoveryAll() throws Exception {
        return null;
    }

    public Vendedor recoveryByID(Integer id) throws Exception {
        return mappingToDto(_vendedorDao.recoveryByID(id));
    }

    public void update(Vendedor entity) throws Exception {
    }

    @Override
    public Vendedor getVendedorVenta(PkVenta pkVenta) throws Exception {
        return mappingToDto(_vendedorDao.getVendedorVenta(pkVenta.getIdDocumento(), pkVenta.getIdLetra(), pkVenta.getPuntoVenta(),
                pkVenta.getIdNumeroDocumento()));
    }

    @Override
    public Vendedor mappingToDto(VendedorBd vendedorBd) throws Exception {
        Vendedor vendedor = new Vendedor();
        if (vendedorBd != null) {
            vendedor.setIdVendedor(vendedorBd.getId());
            vendedor.setTiControlPedidoRentable(vendedorBd.getTiControlPedidoRentable());
            //cargo rrhh
            RecursoHumanoRepositoryImpl recursoHumanoRepository = new RecursoHumanoRepositoryImpl(_db);
            RecursoHumano rh = recursoHumanoRepository.mappingToDto(this._db.recursoHumanoDao().recoveryByID(vendedorBd.getId()));
            vendedor.setNombre(rh.getNombre());
            vendedor.setApellido(rh.getApellido());
            vendedor.setDomicilio(rh.getDomicilio());
            vendedor.setNumeroDocumento(rh.getNumeroDocumento());
        }
        return vendedor;
    }
}
