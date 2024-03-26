package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.ICompraDao;
import com.ar.vgmsistemas.database.dao.entity.CompraBd;
import com.ar.vgmsistemas.database.dao.entity.key.PkCompraBd;
import com.ar.vgmsistemas.entity.Compra;
import com.ar.vgmsistemas.entity.key.PkCompra;
import com.ar.vgmsistemas.repository.ICompraRepository;
import com.ar.vgmsistemas.utils.Formatter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CompraRepositoryImpl implements ICompraRepository {
    private final AppDataBase _db;

    private ICompraDao _compraDao;

    public CompraRepositoryImpl(AppDataBase db) {
        this._db = db;
        if (db != null) {
            this._compraDao = db.compraDao();
        }
    }

    @Override
    public PkCompra create(Compra mCompra) throws Exception {
        mCompra.setFeIva(mCompra.getFeFactura());
        mCompra.setSnAnulo(Compra.NO);

        _compraDao.create(mappingToDb(mCompra));
        return mCompra.getId();
    }

    @Override
    public Compra recoveryByID(PkCompra pkCompra) throws Exception {
        Compra compra = mappingToDto(_compraDao.recoveryByID(pkCompra.getIdProveedor(), pkCompra.getIdFcncnd(),
                pkCompra.getIdPuntoVenta(), pkCompra.getIdNumero()));
        //recupero comprasImpuesto
        ComprasImpuestosRepositoryImpl comprasImpuestosRepository = new ComprasImpuestosRepositoryImpl(this._db);
        compra.setComprasImpuestos(comprasImpuestosRepository.recoveryByEgreso(compra));
        return compra;
    }

    @Override
    public List<Compra> recoveryAll() throws Exception {
        List<CompraBd> listadoCompras = _compraDao.recoveryAll();
        List<Compra> compras = new ArrayList<>();
        if (!listadoCompras.isEmpty()) {
            for (CompraBd item : listadoCompras) {
                Compra compra = mappingToDto(item);
                //recupero comprasImpuesto
                ComprasImpuestosRepositoryImpl comprasImpuestosRepository = new ComprasImpuestosRepositoryImpl(this._db);
                compra.setComprasImpuestos(comprasImpuestosRepository.recoveryByEgreso(compra));
                compras.add(compra);
            }
        }
        return compras;
    }

    @Override
    public void update(Compra entity) throws Exception {
        _compraDao.update(mappingToDb(entity));
    }

    @Override
    public void delete(Compra entity) throws Exception {
        _compraDao.delete(entity.getId().getIdProveedor(), entity.getId().getIdFcncnd(),
                entity.getId().getIdPuntoVenta(), entity.getId().getIdNumero());
    }

    @Override
    public void delete(PkCompra pkCompra) throws Exception {
        _compraDao.delete(_compraDao.recoveryByID(pkCompra.getIdProveedor(),
                pkCompra.getIdFcncnd(), pkCompra.getIdPuntoVenta(), pkCompra.getIdNumero()));
    }

    public List<Compra> recoveryNoEnviados() throws Exception {
        List<CompraBd> listadoCompras = _compraDao.recoveryNoEnviados();
        List<Compra> compras = new ArrayList<>();
        if (!listadoCompras.isEmpty()) {
            for (CompraBd item : listadoCompras) {
                Compra compra = mappingToDto(item);
                //recupero comprasImpuesto
                ComprasImpuestosRepositoryImpl comprasImpuestosRepository = new ComprasImpuestosRepositoryImpl(this._db);
                compra.setComprasImpuestos(comprasImpuestosRepository.recoveryByEgreso(compra));
                compras.add(compra);
            }
        }
        return compras;
    }

    public boolean isEnviado(Compra egreso) throws Exception {
        return _compraDao.isEnviado(egreso.getIdMovil()) <= 0;
    }

    @Override
    public List<Compra> recoverEgresosByHoja(int idHoja, int idSucursal) throws Exception {
        List<CompraBd> listadoCompras = _compraDao.recoverEgresosByHoja(idHoja, idSucursal);
        List<Compra> compras = new ArrayList<>();
        if (!listadoCompras.isEmpty()) {
            for (CompraBd item : listadoCompras) {
                Compra compra = mappingToDto(item);
                //recupero comprasImpuesto
                ComprasImpuestosRepositoryImpl comprasImpuestosRepository = new ComprasImpuestosRepositoryImpl(this._db);
                compra.setComprasImpuestos(comprasImpuestosRepository.recoveryByEgreso(compra));
                compras.add(compra);
            }
        }
        return compras;
    }

    @Override
    public boolean existsEgreso(PkCompra id) throws Exception {
        return _compraDao.existsEgreso(id.getIdProveedor(), id.getIdFcncnd(), id.getIdPuntoVenta(),
                id.getIdNumero()) != null;
    }

    @Override
    public Compra mappingToDto(CompraBd compraBd) throws ParseException {
        Compra compra = new Compra();
        if (compraBd != null) {
            PkCompra id = new PkCompra(compraBd.getId().getIdProveedor(), compraBd.getId().getIdFcncnd(),
                    compraBd.getId().getIdPuntoVenta(), compraBd.getId().getIdNumero());
            compra.setId(id);
            compra.setPrSubtotal(compraBd.getPrSubtotal());
            compra.setTaDto(compraBd.getTaDto());
            compra.setPrDto(compraBd.getPrDto());
            compra.setPrGravado(compraBd.getPrGravado());
            compra.setPrExento(compraBd.getPrExento());
            compra.setPrPercIngrBruto(compraBd.getPrPercIngrBruto());
            compra.setPrPercIva(compraBd.getPrPercIva());
            compra.setPrIpInterno(compraBd.getPrIpInterno());
            compra.setPrIva(compraBd.getPrIva());
            compra.setPrCompra(compraBd.getPrCompra());
            compra.setFeFactura((compraBd.getFeFactura() == null || compraBd.getFeFactura().equalsIgnoreCase(""))
                    ? null
                    : Formatter.convertToDate(compraBd.getFeFactura()));
            compra.setFeIngreso((compraBd.getFeIngreso() == null || compraBd.getFeIngreso().equalsIgnoreCase(""))
                    ? null
                    : Formatter.convertToDate(compraBd.getFeIngreso()));
            compra.setFeIva((compraBd.getFeIva() == null || compraBd.getFeIva().equalsIgnoreCase(""))
                    ? null
                    : Formatter.convertToDate(compraBd.getFeIva()));
            compra.setIdMovil(compraBd.getIdMovil());
            compra.setIdPlancta(compraBd.getIdPlancta());
            compra.setNuCuit(compraBd.getNuCuit());
            compra.setIdLetra(compraBd.getIdLetra());
            compra.setIdSucProv(compraBd.getIdSucProv());
            compra.setDeConcepto(compraBd.getDeConcepto());
            compra.setSnAnulo(compraBd.getSnAnulo());
            compra.setIdHoja(compraBd.getIdHoja());
            compra.setIdSucursal(compraBd.getIdSucursal());
        }
        return compra;
    }

    private CompraBd mappingToDb(Compra compra) throws Exception {
        if (compra != null) {
            PkCompraBd id = new PkCompraBd();

            id.setIdProveedor(compra.getId().getIdProveedor());
            id.setIdFcncnd(compra.getId().getIdFcncnd());
            id.setIdPuntoVenta(compra.getId().getIdPuntoVenta());
            id.setIdPuntoVenta(compra.getId().getIdNumero());

            CompraBd compraBd = new CompraBd();

            compraBd.setId(id);
            compraBd.setPrSubtotal(compra.getPrSubtotal());
            compraBd.setTaDto(compra.getTaDto());
            compraBd.setPrDto(compra.getPrDto());
            compraBd.setPrGravado(compra.getPrGravado());
            compraBd.setPrExento(compra.getPrExento());
            compraBd.setPrPercIngrBruto(compra.getPrPercIngrBruto());
            compraBd.setPrPercIva(compra.getPrPercIva());
            compraBd.setPrIpInterno(compra.getPrIpInterno());
            compraBd.setPrIva(compra.getPrIva());
            compraBd.setPrCompra(compra.getPrCompra());
            compraBd.setFeFactura(Formatter.formatDateTimeToString(compra.getFeFactura()));
            compraBd.setFeIngreso(Formatter.formatDateTimeToString(compra.getFeIngreso()));
            compraBd.setFeIva(Formatter.formatJulianDate(compra.getFeIva()));
            compraBd.setIdMovil(compra.getIdMovil());
            compraBd.setIdPlancta(compra.getIdPlancta());
            compraBd.setNuCuit(compra.getNuCuit());
            compraBd.setIdLetra(compra.getIdLetra());
            compraBd.setIdSucProv(compra.getIdSucProv());
            compraBd.setDeConcepto(compra.getDeConcepto());
            compraBd.setSnAnulo(compra.getSnAnulo());
            compraBd.setIdHoja(compra.getIdHoja());
            compraBd.setIdSucursal(compra.getIdSucursal());

            return compraBd;
        }
        return null;
    }
}
