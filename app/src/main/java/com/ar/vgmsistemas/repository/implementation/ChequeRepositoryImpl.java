package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IChequeDao;
import com.ar.vgmsistemas.database.dao.entity.ChequeBd;
import com.ar.vgmsistemas.database.dao.entity.key.PkChequeBd;
import com.ar.vgmsistemas.entity.Cheque;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.Entrega;
import com.ar.vgmsistemas.entity.key.PkCheque;
import com.ar.vgmsistemas.repository.IChequeRepository;
import com.ar.vgmsistemas.utils.Formatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChequeRepositoryImpl implements IChequeRepository {
    private final AppDataBase _db;
    //DAO's
    private IChequeDao _chequeDao;

    public ChequeRepositoryImpl(AppDataBase db) {
        this._db = db;
        if (db != null) {
            this._chequeDao = db.chequeDao();
        }
    }

    public PkCheque create(Cheque cheque) throws Exception {
        _chequeDao.create(mappingToDb(cheque));
        return cheque.getId();
    }

    @Override
    public List<Cheque> recoveryByCliente(Cliente cliente) throws Exception {
        Date fechaActual = Calendar.getInstance().getTime();
        String fechaActualConvertida = Formatter.formatJulianDate(fechaActual);
        List<ChequeBd> listadoChequesBd = _chequeDao.recoveryByCliente(cliente.getId().getIdSucursal(),
                cliente.getId().getIdCliente(), fechaActualConvertida);
        List<Cheque> cheques = new ArrayList<>();
        if (!listadoChequesBd.isEmpty()) {
            for (ChequeBd item : listadoChequesBd) {
                cheques.add(mappingToDto(item));
            }
        }
        return cheques;
    }

    public List<Cheque> recoveryByEntrega(Entrega entrega) throws Exception {
        List<ChequeBd> listadoChequesBd = _chequeDao.recoveryByEntrega(entrega.getId());
        List<Cheque> cheques = new ArrayList<>();
        if (!listadoChequesBd.isEmpty()) {
            for (ChequeBd item : listadoChequesBd) {
                cheques.add(mappingToDto(item));
            }
        }
        return cheques;
    }

    public Cheque recoveryByID(PkCheque id) throws Exception {
        return null;
    }

    public List<Cheque> recoveryAll() throws Exception {
        return null;
    }

    public void update(Cheque entity) throws Exception {

    }

    public void delete(Cheque mCheque) throws Exception {
        _chequeDao.delete(mappingToDb(mCheque));
    }

    public void deleteByEntrega(Cheque mCheque) throws Exception {
        _chequeDao.deleteByEntrega(mCheque.getEntrega().getId());
    }

    public void delete(PkCheque id) throws Exception {

    }

    public Cheque mappingToDto(ChequeBd chequeBd) throws Exception {
        Cheque mCheque = new Cheque();
        if (chequeBd != null) {
            PkCheque id = new PkCheque(chequeBd.getId().getIdBanco(), chequeBd.getId().getNumeroCheque(),
                    chequeBd.getId().getIdPostal(), chequeBd.getId().getSucursal(), chequeBd.getId().getNroCuenta());

            mCheque.setId(id);
            mCheque.setIdEntrega(chequeBd.getIdEntrega());
            mCheque.setFechaChequeMovil((chequeBd.getFechaChequeMovil() == null ||
                    chequeBd.getFechaChequeMovil().equalsIgnoreCase(""))
                    ? null
                    : Formatter.convertToDate(chequeBd.getFechaChequeMovil()));
            mCheque.setImporte(chequeBd.getImporte());
            mCheque.setCuit(chequeBd.getCuit());
            mCheque.setIdCliente(chequeBd.getIdCliente());
            mCheque.setIdSucursalCliente(chequeBd.getIdSucursalCliente());
            mCheque.setTipoCheque((chequeBd.getTipoCheque() == null ||
                    chequeBd.getTipoCheque().equalsIgnoreCase(""))
                    ? "F"
                    : chequeBd.getTipoCheque());

            //cargo banco
            BancoRepositoryImpl bancoRepository = new BancoRepositoryImpl(this._db);
            mCheque.setBanco(bancoRepository.mappingToDto(this._db.bancoDao().recoveryByID(chequeBd.getId().getIdBanco())));
            //cargo localidades
            LocalidadRepositoryImpl localidadRepository = new LocalidadRepositoryImpl(this._db);
            mCheque.setLocalidad(localidadRepository.mappingToDto(this._db.localidadDao().recoveryByID(chequeBd.getId().getIdPostal())));
            //cargo entrega
            EntregaRepositoryImpl entregaRepository = new EntregaRepositoryImpl(this._db);
            mCheque.setEntrega(entregaRepository.mappingToDto(this._db.entregaDao().recoveryByID(chequeBd.getIdEntrega())));

        }
        return mCheque;
    }

    public ChequeBd mappingToDb(Cheque cheque) throws Exception {
        ChequeBd mChequeBd = new ChequeBd();
        if (cheque != null) {
            PkChequeBd id = new PkChequeBd();

            id.setIdBanco(cheque.getId().getIdBanco());
            id.setNumeroCheque(cheque.getId().getNumeroCheque());
            id.setIdPostal(cheque.getId().getIdPostal());
            id.setSucursal(cheque.getId().getSucursal());
            id.setNroCuenta(cheque.getId().getNroCuenta());

            mChequeBd.setId(id);
            mChequeBd.setIdEntrega(cheque.getEntrega().getId());
            mChequeBd.setFechaChequeMovil(Formatter.formatJulianDate(cheque.getFechaChequeMovil()));
            mChequeBd.setImporte(cheque.getImporte());
            mChequeBd.setCuit(cheque.getCuit());
            mChequeBd.setIdCliente(cheque.getIdCliente());
            mChequeBd.setIdSucursalCliente(cheque.getIdSucursalCliente());
            mChequeBd.setTipoCheque(cheque.getTipoCheque());
            return mChequeBd;
        } else return null;
    }
}
