package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.Deposito;
import com.ar.vgmsistemas.entity.Entrega;
import com.ar.vgmsistemas.repository.IDepositoRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;

import java.util.List;

public class DepositoBo {

    private static final String TAG = DepositoBo.class.getCanonicalName();
    private final IDepositoRepository _depositoRepository;

    public DepositoBo(RepositoryFactory repoFactory) {
        this._depositoRepository = repoFactory.getDepositoRepository();
    }

    public boolean guardar(Deposito deposito) throws Exception {
        try {
            _depositoRepository.create(deposito);
            return true;
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "create", e);
            throw e;
        }
    }

    public boolean deleteByEntrega(Deposito deposito) throws Exception {
        try {
            _depositoRepository.deleteByEntrega(deposito);
            return true;
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "create", e);
            throw e;
        }
    }

    public List<Deposito> recoveryByEntrega(Entrega entrega) throws Exception {
        return _depositoRepository.recoveryByEntrega(entrega);
    }

}
