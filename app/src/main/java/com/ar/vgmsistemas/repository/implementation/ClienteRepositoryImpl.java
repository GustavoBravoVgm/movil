package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IClienteDao;
import com.ar.vgmsistemas.database.dao.entity.ClienteBd;
import com.ar.vgmsistemas.database.dao.entity.key.PkClienteBd;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.key.PkCliente;
import com.ar.vgmsistemas.repository.IClienteRepository;
import com.ar.vgmsistemas.utils.Formatter;

import java.util.ArrayList;
import java.util.List;

public class ClienteRepositoryImpl implements IClienteRepository {
    private final AppDataBase _db;
    //DAO's
    private IClienteDao _clienteDao;

    public ClienteRepositoryImpl(AppDataBase db) {
        this._db = db;
        if (db != null) {
            this._clienteDao = db.clienteDao();
        }
    }

    public PkCliente create(Cliente cliente) throws Exception {
        return null;
    }

    public List<Cliente> recoveryAll() throws Exception {
        List<ClienteBd> listadoClientes = _clienteDao.recoveryAll();
        List<Cliente> clientes = new ArrayList<>();
        if (!listadoClientes.isEmpty()) {
            for (ClienteBd item : listadoClientes) {
                clientes.add(mappingToDto(item));
            }
        }
        return clientes;
    }

    @Override
    public List<Cliente> recoveryAllGroupByCteSuc(int idLocalidad, int idSucursalCte) throws Exception {
        List<ClienteBd> listadoClientes = _clienteDao.recoveryAllGroupByCteSuc(idLocalidad, idSucursalCte);
        List<Cliente> clientes = new ArrayList<>();
        if (!listadoClientes.isEmpty()) {
            for (ClienteBd item : listadoClientes) {
                clientes.add(mappingToDto(item));
            }
        }
        return clientes;
    }

    public Cliente recoveryByID(PkCliente id) throws Exception {
        return mappingToDto(_clienteDao.recoveryByID(id.getIdSucursal(), id.getIdCliente(), id.getIdComercio()));
    }

    public void update(Cliente cliente) throws Exception {
        _clienteDao.update(mappingToDb(cliente));
    }

    public void updateLimiteDisonibilidad(Cliente cliente) throws Exception {
        _clienteDao.update(mappingToDb(cliente));
    }

    public void delete(Cliente entity) throws Exception {
    }

    public void delete(PkCliente id) throws Exception {
    }

    public List<Cliente> recoveryNoEnviados() throws Exception {
        List<ClienteBd> listadoClientes = _clienteDao.recoveryNoEnviados();
        List<Cliente> clientes = new ArrayList<>();
        if (!listadoClientes.isEmpty()) {
            for (ClienteBd item : listadoClientes) {
                clientes.add(mappingToDto(item));
            }
        }
        return clientes;
    }

    @Override
    public int recoveryVendedorCliente(Cliente cliente) throws Exception {
        int idVendedor;
        idVendedor = _clienteDao.recoveryVendedorCliente(cliente.getId().getIdSucursal(),
                cliente.getId().getIdCliente(), cliente.getId().getIdComercio());
        return idVendedor;
    }

    @Override
    public Cliente mappingToDto(ClienteBd clienteBd) throws Exception {
        Cliente cliente = new Cliente();
        if (clienteBd != null) {
            PkCliente id = new PkCliente(clienteBd.getId().getIdSucursal(), clienteBd.getId().getIdCliente(),
                    clienteBd.getId().getIdComercio());
            cliente.setId(id);
            cliente.setCuit(clienteBd.getCuit());
            cliente.setDomicilio(clienteBd.getDomicilio());
            cliente.setTelefono(clienteBd.getTelefono());
            cliente.setRazonSocial(clienteBd.getRazonSocial());
            cliente.setLatitud(clienteBd.getLatitud() == null
                    ? 0d
                    : clienteBd.getLatitud());
            cliente.setLongitud(clienteBd.getLongitud() == null
                    ? 0d
                    : clienteBd.getLongitud());
            cliente.setIdMovil(clienteBd.getIdMovil());
            cliente.setLimiteCredito(clienteBd.getLimiteCredito());
            cliente.setLimiteDisponibilidad(clienteBd.getLimiteDisponibilidad());
            cliente.setDescripcionRubro(clienteBd.getDescripcionRubro());
            cliente.setTasaDescuentoCliente(clienteBd.getTasaDescuentoCliente() == null ? 0d : clienteBd.getTasaDescuentoCliente());
            cliente.setTotalVentaAcumulada(clienteBd.getTotalVentaAcumulada() == null ? 0d : clienteBd.getTotalVentaAcumulada());
            cliente.setIsHabilitado((clienteBd.getSnHabilitado() != null && clienteBd.getSnHabilitado().equalsIgnoreCase("S")));
            cliente.setFeVencHabilitacionAlcohol(clienteBd.getFeVencHabilitacionAlcohol() == null ? null : Formatter.convertToDate(clienteBd.getFeVencHabilitacionAlcohol()));
            cliente.setHabilitado(clienteBd.getSnHabilitado().equalsIgnoreCase("S"));
            cliente.setCodAutCtaCte(clienteBd.getCodAutCtaCte() == null ? "" : clienteBd.getCodAutCtaCte());
            //cargo localidad
            LocalidadRepositoryImpl localidadRepository = new LocalidadRepositoryImpl(this._db);
            cliente.setLocalidad(localidadRepository.mappingToDto(this._db.localidadDao().recoveryByID(clienteBd.getIdPostal())));
            //cargo condición renta
            if (clienteBd.getIdDgr() != null) {
                CondicionRentaRepositoryImpl condicionRentaRepository = new CondicionRentaRepositoryImpl(this._db);
                cliente.setCondicionRenta(condicionRentaRepository.mappingToDto(this._db.condicionRentaDao().recoveryByID(clienteBd.getIdDgr())));
            }
            //cargo categoria fiscal
            if (clienteBd.getIdCategoriaFiscal() != null) {
                CategoriaFiscalRepositoryImpl categoriaFiscalRepository = new CategoriaFiscalRepositoryImpl(this._db);
                cliente.setCategoriaFiscal(categoriaFiscalRepository.mappingToDto(this._db.categoriaFiscalDao().recoveryByID(clienteBd.getIdCategoriaFiscal())));
            }
            //cargo condición venta
            if (clienteBd.getIdCondicionVenta() != null) {
                CondicionVentaRepositoryImpl condicionVentaRepository = new CondicionVentaRepositoryImpl(this._db);
                cliente.setCondicionVenta(condicionVentaRepository.mappingToDto(this._db.condicionVentaDao().recoveryByID(clienteBd.getIdCondicionVenta())));
            }
            //cargo repartidor
            if (clienteBd.getIdRepartidor() != null) {
                RepartidorRepositoryImpl repartidorRepository = new RepartidorRepositoryImpl(this._db);
                cliente.setRepartidor(repartidorRepository.mappingToDto(this._db.repartidorDao().recoveryByID(clienteBd.getIdRepartidor())));
            }
            //cargo lista de precios
            ListaPrecioRepositoryImpl listaPrecioRepository = new ListaPrecioRepositoryImpl(this._db);
            cliente.setListaPrecio(listaPrecioRepository.mappingToDto(this._db.listaPrecioDao().recoveryByID(clienteBd.getIdListaPrecio())));
            //cargo condicionDirsc
            if (clienteBd.getIdDirsc() != null) {
                CondicionDirscRepositoryImpl condicionDirscRepository = new CondicionDirscRepositoryImpl(this._db);
                cliente.setCondicionDirsc(condicionDirscRepository.mappingToDto(this._db.condicionDirscDao().recoveryByID(clienteBd.getIdDirsc())));
            }
            return cliente;
        }
        return cliente;
    }

    public ClienteBd mappingToDb(Cliente cliente) {
        if (cliente != null) {
            PkClienteBd id = new PkClienteBd();

            id.setIdSucursal(cliente.getId().getIdSucursal());
            id.setIdCliente(cliente.getId().getIdCliente());
            id.setIdComercio(cliente.getId().getIdComercio());

            ClienteBd clienteBd = new ClienteBd();

            clienteBd.setId(id);
            clienteBd.setIdPostal(cliente.getLocalidad() == null
                    ? null
                    : cliente.getLocalidad().getId());
            clienteBd.setIdDgr(cliente.getCondicionRenta() == null
                    ? null
                    : cliente.getCondicionRenta().getId());
            clienteBd.setIdCategoriaFiscal(cliente.getCategoriaFiscal() == null
                    ? null
                    : cliente.getCategoriaFiscal().getId());
            clienteBd.setIdCondicionVenta(cliente.getCondicionVenta() == null
                    ? null
                    : cliente.getCondicionVenta().getId());
            clienteBd.setIdRepartidor(cliente.getRepartidor() == null
                    ? null
                    : cliente.getRepartidor().getId());
            clienteBd.setIdListaPrecio(cliente.getListaPrecio().getId());
            clienteBd.setCuit(cliente.getCuit());
            clienteBd.setDomicilio(cliente.getDomicilio());
            clienteBd.setTelefono(clienteBd.getTelefono());
            clienteBd.setRazonSocial(cliente.getRazonSocial());
            clienteBd.setLatitud(cliente.getLatitud());
            clienteBd.setLongitud(cliente.getLongitud());
            clienteBd.setIdDirsc(cliente.getCondicionDirsc() == null
                    ? null
                    : cliente.getCondicionDirsc().getId());
            clienteBd.setIdMovil(cliente.getIdMovil());
            clienteBd.setLimiteCredito(cliente.getLimiteCredito());
            clienteBd.setLimiteDisponibilidad(cliente.getLimiteDisponibilidad());
            clienteBd.setDescripcionRubro(cliente.getDescripcionRubro());
            clienteBd.setTasaDescuentoCliente(cliente.getTasaDescuentoCliente());
            clienteBd.setTotalVentaAcumulada(cliente.getTotalVentaAcumulada());
            clienteBd.setSnHabilitacionAlcohol(cliente.getIsHabilitadoParaAlcohol()
                    ? "S"
                    : "N");
            clienteBd.setFeVencHabilitacionAlcohol(cliente.getFeVencHabilitacionAlcohol() == null
                    ? null
                    : Formatter.formatDate(cliente.getFeVencHabilitacionAlcohol()));
            clienteBd.setSnHabilitado(cliente.getIsHabilitado() ? "S" : "N");
            clienteBd.setCodAutCtaCte(clienteBd.getCodAutCtaCte());
            return clienteBd;
        }
        return null;
    }
}
