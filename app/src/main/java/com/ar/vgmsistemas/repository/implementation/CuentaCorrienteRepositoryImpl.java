package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.ICuentaCorrienteDao;
import com.ar.vgmsistemas.database.dao.entity.CuentaCorrienteBd;
import com.ar.vgmsistemas.database.dao.entity.key.PkCuentaCorrienteBd;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.CuentaCorriente;
import com.ar.vgmsistemas.entity.Recibo;
import com.ar.vgmsistemas.entity.key.PkCuentaCorriente;
import com.ar.vgmsistemas.repository.ICuentaCorrienteRepository;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.utils.Matematica;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CuentaCorrienteRepositoryImpl implements ICuentaCorrienteRepository {
    private final AppDataBase _db;

    private ICuentaCorrienteDao _cuentaCorrienteDao;


    public CuentaCorrienteRepositoryImpl(AppDataBase db) {
        this._db = db;
        if (db != null) {
            this._cuentaCorrienteDao = db.cuentaCorrienteDao();
        }
    }

    @Override
    public PkCuentaCorriente create(CuentaCorriente cuentaCorriente, Recibo recibo) throws Exception {
        cuentaCorriente.setIdRecibo(recibo.getId().getIdRecibo());
        cuentaCorriente.setIdRecPtoVta(recibo.getId().getIdPuntoVenta());
        _cuentaCorrienteDao.create(mappingToDb(cuentaCorriente));
        return cuentaCorriente.getId();
    }

    public PkCuentaCorriente create(CuentaCorriente cuentaCorriente) throws Exception {
        _cuentaCorrienteDao.create(mappingToDb(cuentaCorriente));
        return cuentaCorriente.getId();
    }

    public CuentaCorriente recoveryByID(PkCuentaCorriente id) throws Exception {
        return null;
    }

    public List<CuentaCorriente> recoveryByCliente(Cliente cliente) throws Exception {
        List<CuentaCorrienteBd> listadoCuentaCorrientes = _cuentaCorrienteDao.recoveryByCliente(cliente.getId().getIdSucursal(),
                cliente.getId().getIdCliente());
        List<CuentaCorriente> cuentasCorrientes = new ArrayList<>();
        if (!listadoCuentaCorrientes.isEmpty()) {
            for (CuentaCorrienteBd item : listadoCuentaCorrientes) {
                cuentasCorrientes.add(mappingToDto(item));
            }
        }
        return cuentasCorrientes;
    }

    public List<CuentaCorriente> recoveryByRecibo(Recibo recibo) throws Exception {
        List<CuentaCorrienteBd> listadoCuentaCorrientes = _cuentaCorrienteDao.recoveryByRecibo(recibo.getId().getIdPuntoVenta(),
                recibo.getId().getIdRecibo());
        List<CuentaCorriente> cuentasCorrientes = new ArrayList<>();
        if (!listadoCuentaCorrientes.isEmpty()) {
            for (CuentaCorrienteBd item : listadoCuentaCorrientes) {
                cuentasCorrientes.add(mappingToDto(item));
            }
        }
        return cuentasCorrientes;
    }

    public List<CuentaCorriente> recoveryAll() throws Exception {
        List<CuentaCorrienteBd> listadoCuentaCorrientes = _cuentaCorrienteDao.recoveryAll();
        List<CuentaCorriente> cuentasCorrientes = new ArrayList<>();
        if (!listadoCuentaCorrientes.isEmpty()) {
            for (CuentaCorrienteBd item : listadoCuentaCorrientes) {
                cuentasCorrientes.add(mappingToDto(item));
            }
        }
        return cuentasCorrientes;
    }

    public void update(CuentaCorriente cuentaCorriente) throws Exception {
        _cuentaCorrienteDao.update(mappingToDb(cuentaCorriente));
    }

    public void delete(CuentaCorriente entity) throws Exception {

    }

    public void delete(PkCuentaCorriente id) throws Exception {

    }

    private boolean isCuentaCorrientePendiente(CuentaCorriente cuentaCorriente) {

        double totalCuota = Matematica.Round(cuentaCorriente.getTotalCuota(), 2);
        double totalPagado = Matematica.Round(cuentaCorriente.getTotalPagado(), 2);
        double totalNotaCredito = Matematica.Round(cuentaCorriente.getTotalNotaCredito(), 2);
        double totalACuenta = Matematica.Round(totalPagado + totalNotaCredito, 2);

        return (totalACuenta < totalCuota);
    }

    public double getTotalSaldo(Cliente cliente, String snClienteUnico) throws Exception {
        double total;
        total = _cuentaCorrienteDao.getTotalSaldo(cliente.getId().getIdSucursal(), cliente.getId().getIdCliente(), snClienteUnico);
        return total;
    }

    public double getTotalSaldoVencido(Cliente cliente, String snClienteUnico) throws Exception {
        double total;
        total = _cuentaCorrienteDao.getTotalSaldoVencido(cliente.getId().getIdSucursal(),
                cliente.getId().getIdCliente(), snClienteUnico);
        return total;
    }

    public int getNumeroAdelanto() throws Exception {
        int numeroAdelanto;
        numeroAdelanto = _cuentaCorrienteDao.getNumeroAdelanto(CuentaCorriente.DOCUMENTO_ANTICIPO);
        return numeroAdelanto;
    }

    @Override
    public CuentaCorriente mappingToDto(CuentaCorrienteBd cuentaCorrienteBd) throws Exception {
        CuentaCorriente ctaCte = new CuentaCorriente();
        if (cuentaCorrienteBd != null) {
            PkCuentaCorriente id = new PkCuentaCorriente(cuentaCorrienteBd.getId().getIdDocumento(),
                    cuentaCorrienteBd.getId().getIdLetra(), cuentaCorrienteBd.getId().getPuntoVenta(),
                    cuentaCorrienteBd.getId().getIdNumeroDocumento(), cuentaCorrienteBd.getId().getCuota());

            ctaCte.setId(id);
            //cargo vendedor
            VendedorRepositoryImpl vendedorRepository = new VendedorRepositoryImpl(this._db);
            ctaCte.setVendedor(vendedorRepository.mappingToDto(this._db.vendedorDao().recoveryByID(cuentaCorrienteBd.getIdVendedor())));
            //importe
            ctaCte.setTotalCuota(cuentaCorrienteBd.getTotalCuota());
            ctaCte.setTotalPagado(cuentaCorrienteBd.getTotalPagado());
            ctaCte.setTotalNotaCredito(cuentaCorrienteBd.getTotalNotaCredito());
            ctaCte.setSigno(cuentaCorrienteBd.getSigno());
            ctaCte.setSaldoMovil(cuentaCorrienteBd.getSaldoMovil());
            //cargo condicion venta
            CondicionVentaRepositoryImpl condicionVentaRepository = new CondicionVentaRepositoryImpl(this._db);
            ctaCte.setCondicionVenta(condicionVentaRepository.mappingToDto(this._db.condicionVentaDao().recoveryByID(
                    cuentaCorrienteBd.getIdCondicionVenta())));
            ctaCte.setFechaVenta((cuentaCorrienteBd.getFechaVenta() == null || cuentaCorrienteBd.getFechaVenta().equalsIgnoreCase(""))
                    ? null
                    : Formatter.convertToDate(cuentaCorrienteBd.getFechaVenta()));
            ctaCte.setIdMovil(cuentaCorrienteBd.getIdMovil());
            //cargo documentos
            DocumentoRepositoryImpl documentoRepository = new DocumentoRepositoryImpl(this._db);
            ctaCte.setDocumento(documentoRepository.mappingToDto(this._db.documentoDao().recoveryByID(cuentaCorrienteBd.getId().getIdDocumento(),
                    cuentaCorrienteBd.getId().getIdLetra(), cuentaCorrienteBd.getId().getPuntoVenta())));
            //cargo cliente
            ClienteRepositoryImpl clienteRepository = new ClienteRepositoryImpl(this._db);
            ctaCte.setCliente(clienteRepository.mappingToDto(this._db.clienteDao().recoveryByID(cuentaCorrienteBd.getIdSucCliente(),
                    cuentaCorrienteBd.getIdCliente(), cuentaCorrienteBd.getIdComercio())));
            ctaCte.setIdNumDocFiscal(cuentaCorrienteBd.getIdNumDocFiscal());
            ctaCte.setCodAutCtaCte(cuentaCorrienteBd.getDeCodAutRepMovilCtaCte());
        }
        return ctaCte;
    }

    public CuentaCorrienteBd mappingToDb(CuentaCorriente cuentaCorriente) throws ParseException {
        if (cuentaCorriente != null) {
            PkCuentaCorrienteBd id = new PkCuentaCorrienteBd();
            id.setIdDocumento(cuentaCorriente.getId().getIdDocumento());
            id.setIdLetra(cuentaCorriente.getId().getIdLetra());
            id.setPuntoVenta(cuentaCorriente.getId().getPuntoVenta());
            id.setIdNumeroDocumento(cuentaCorriente.getId().getIdNumeroDocumento());
            id.setCuota(cuentaCorriente.getId().getCuota());

            CuentaCorrienteBd ctaCteBd = new CuentaCorrienteBd();
            ctaCteBd.setId(id);
            ctaCteBd.setIdVendedor(cuentaCorriente.getVendedor().getIdVendedor());
            ctaCteBd.setTotalCuota(cuentaCorriente.getTotalCuota());
            ctaCteBd.setTotalPagado(cuentaCorriente.getTotalPagado());
            ctaCteBd.setTotalNotaCredito(cuentaCorriente.getTotalNotaCredito());
            ctaCteBd.setSigno(cuentaCorriente.getSigno());
            ctaCteBd.setSaldoMovil(cuentaCorriente.getSaldoMovil());
            ctaCteBd.setIdCondicionVenta(cuentaCorriente.getCondicionVenta().getId());
            ctaCteBd.setFechaVenta(Formatter.formatJulianDate(cuentaCorriente.getFechaVenta()));
            ctaCteBd.setIdMovil(cuentaCorriente.getIdMovil());
            ctaCteBd.setIdSucCliente(cuentaCorriente.getCliente().getId().getIdSucursal());
            ctaCteBd.setIdCliente(cuentaCorriente.getCliente().getId().getIdCliente());
            ctaCteBd.setIdComercio(cuentaCorriente.getCliente().getId().getIdComercio());
            ctaCteBd.setIdNumDocFiscal(cuentaCorriente.getIdNumDocFiscal());
            ctaCteBd.setDeCodAutRepMovilCtaCte(cuentaCorriente.getDeCodAutRepMovilCtaCte());
            ctaCteBd.setIdRecibo(cuentaCorriente.getIdRecibo());
            ctaCteBd.setIdRecPtoVta(cuentaCorriente.getIdRecPtoVta());

            return ctaCteBd;
        }
        return null;
    }
}
