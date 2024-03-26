package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IComprasImpuestosDao;
import com.ar.vgmsistemas.database.dao.entity.ComprasImpuestosBd;
import com.ar.vgmsistemas.database.dao.entity.key.PkComprasImpuestosBd;
import com.ar.vgmsistemas.entity.Compra;
import com.ar.vgmsistemas.entity.ComprasImpuestos;
import com.ar.vgmsistemas.entity.key.PkComprasImpuestos;
import com.ar.vgmsistemas.repository.IComprasImpuestosRepository;

import java.util.ArrayList;
import java.util.List;

public class ComprasImpuestosRepositoryImpl implements IComprasImpuestosRepository {
    private final AppDataBase _db;

    private IComprasImpuestosDao _comprasImpuestosDao;

    public ComprasImpuestosRepositoryImpl(AppDataBase db) {
        this._db = db;
        if (db != null) {
            this._comprasImpuestosDao = db.comprasImpuestosDao();
        }
    }

    @Override
    public PkComprasImpuestos create(ComprasImpuestos mComprasImpuesto) throws Exception {
        mComprasImpuesto.setIdMovil(null);
        _comprasImpuestosDao.create(mappingToDb(mComprasImpuesto));
        return mComprasImpuesto.getId();
    }

    @Override
    public ComprasImpuestos recoveryByID(PkComprasImpuestos pkComprasImpuestos) throws Exception {
        return mappingToDto(_comprasImpuestosDao.recoveryByID(pkComprasImpuestos.getIdProveedor(), pkComprasImpuestos.getIdFcncnd(),
                pkComprasImpuestos.getIdPuntoVenta(), pkComprasImpuestos.getIdNumero(),
                pkComprasImpuestos.getIdImpuesto(), pkComprasImpuestos.getIdTipoab(), pkComprasImpuestos.getIdSecuencia()));
    }

    @Override
    public List<ComprasImpuestos> recoveryAll() throws Exception {
        List<ComprasImpuestosBd> listadoComprasImpuestos = _comprasImpuestosDao.recoveryAll();
        List<ComprasImpuestos> comprasImpuestos = new ArrayList<>();
        if (!listadoComprasImpuestos.isEmpty()) {
            for (ComprasImpuestosBd item : listadoComprasImpuestos) {
                comprasImpuestos.add(mappingToDto(item));
            }
        }
        return comprasImpuestos;
    }

    @Override
    public void update(ComprasImpuestos mComprasImpuesto) throws Exception {
        _comprasImpuestosDao.update(mappingToDb(mComprasImpuesto));
    }

    @Override
    public void delete(ComprasImpuestos mComprasImpuesto) throws Exception {
        _comprasImpuestosDao.delete(mappingToDb(mComprasImpuesto));
    }

    @Override
    public void delete(PkComprasImpuestos pkComprasImpuestos) throws Exception {
        _comprasImpuestosDao.delete(_comprasImpuestosDao.recoveryByID(pkComprasImpuestos.getIdProveedor(),
                pkComprasImpuestos.getIdFcncnd(), pkComprasImpuestos.getIdPuntoVenta(), pkComprasImpuestos.getIdNumero(),
                pkComprasImpuestos.getIdImpuesto(), pkComprasImpuestos.getIdTipoab(), pkComprasImpuestos.getIdSecuencia()));
    }

    public void anularComprasImpuestoByCompra(Compra mCompra) throws Exception {
        _comprasImpuestosDao.anularComprasImpuestoByCompra(mCompra.getId().getIdProveedor(),
                mCompra.getId().getIdFcncnd(), mCompra.getId().getIdPuntoVenta(), mCompra.getId().getIdNumero());
    }

    public List<ComprasImpuestos> recoveryByEgreso(Compra egreso) throws Exception {
        List<ComprasImpuestosBd> listadoComprasImpuestos = _comprasImpuestosDao.recoveryByEgreso(egreso.getId().getIdProveedor(),
                egreso.getId().getIdFcncnd(), egreso.getId().getIdPuntoVenta(), egreso.getId().getIdNumero());
        List<ComprasImpuestos> comprasImpuestos = new ArrayList<>();
        if (!listadoComprasImpuestos.isEmpty()) {
            for (ComprasImpuestosBd item : listadoComprasImpuestos) {
                comprasImpuestos.add(mappingToDto(item));
            }
        }
        return comprasImpuestos;
    }

    public void deleteComprasImpuestosByCompra(Compra mCompra) throws Exception {
        _comprasImpuestosDao.deleteComprasImpuestosByCompra(mCompra.getId().getIdProveedor(),
                mCompra.getId().getIdFcncnd(), mCompra.getId().getIdPuntoVenta(), mCompra.getId().getIdNumero());
    }

    public ComprasImpuestos mappingToDto(ComprasImpuestosBd comprasImpuestosBd) throws Exception {
        ComprasImpuestos comprasImpuestos = new ComprasImpuestos();
        if (comprasImpuestosBd != null) {
            PkComprasImpuestos id = new PkComprasImpuestos();
            id.setIdProveedor(comprasImpuestosBd.getId().getIdProveedor());
            id.setIdFcncnd(comprasImpuestosBd.getId().getIdFcncnd());
            id.setIdPuntoVenta(comprasImpuestosBd.getId().getIdPuntoVenta());
            id.setIdNumero(comprasImpuestosBd.getId().getIdNumero());
            id.setIdImpuesto(comprasImpuestosBd.getId().getIdImpuesto());
            id.setIdTipoab(comprasImpuestosBd.getId().getIdTipoab());
            id.setIdSecuencia(comprasImpuestosBd.getId().getIdSecuencia());

            comprasImpuestos.setId(id);
            comprasImpuestos.setPrImpGravado(comprasImpuestosBd.getPrImpGravado());
            comprasImpuestos.setTaImpuesto(comprasImpuestosBd.getTaImpuesto());
            comprasImpuestos.setPrImpuesto(comprasImpuestosBd.getPrImpuesto());
            comprasImpuestos.setIdMovil(comprasImpuestosBd.getIdMovil());
            comprasImpuestos.setSnAnulado(comprasImpuestosBd.getSnAnulado());

            //cargo impuestos
            ImpuestoRepositoryImpl impuestoRepository = new ImpuestoRepositoryImpl(_db);
            comprasImpuestos.setImpuesto(impuestoRepository.mappingToDto(this._db.impuestoDao().recoveryByID(
                    comprasImpuestosBd.getId().getIdImpuesto())));
        }
        return comprasImpuestos;
    }

    private ComprasImpuestosBd mappingToDb(ComprasImpuestos comprasImpuestos) {
        if (comprasImpuestos != null) {
            PkComprasImpuestosBd id = new PkComprasImpuestosBd();
            id.setIdProveedor(comprasImpuestos.getId().getIdProveedor());
            id.setIdFcncnd(comprasImpuestos.getId().getIdFcncnd());
            id.setIdPuntoVenta(comprasImpuestos.getId().getIdPuntoVenta());
            id.setIdNumero(comprasImpuestos.getId().getIdNumero());
            id.setIdImpuesto(comprasImpuestos.getId().getIdImpuesto());
            id.setIdTipoab(comprasImpuestos.getId().getIdTipoab());
            id.setIdSecuencia(comprasImpuestos.getId().getIdSecuencia());

            ComprasImpuestosBd comprasImpuestosBd = new ComprasImpuestosBd();
            comprasImpuestosBd.setId(id);
            comprasImpuestosBd.setPrImpGravado(comprasImpuestos.getPrImpGravado());
            comprasImpuestosBd.setTaImpuesto(comprasImpuestos.getTaImpuesto());
            comprasImpuestosBd.setPrImpuesto(comprasImpuestos.getPrImpuesto());
            comprasImpuestosBd.setIdMovil(comprasImpuestos.getIdMovil());
            comprasImpuestosBd.setSnAnulado(comprasImpuestos.getSnAnulado());

            return comprasImpuestosBd;
        }
        return null;
    }
}
