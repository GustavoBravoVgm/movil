package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.Cheque;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.repository.IChequeRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;

import java.util.List;


public class ChequeBo {
    private static final String TAG = ChequeBo.class.getCanonicalName();

    private final IChequeRepository _chequeRepository;

    public ChequeBo(RepositoryFactory repoFactory) {
        _chequeRepository = repoFactory.getChequeRepository();
    }

    public boolean guardar(Cheque cheque) throws Exception {
        try {
            _chequeRepository.create(cheque);//el rollback se maneja en el dao
            return true;
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "create", e);
            throw e;
        }
    }

    public boolean deleteByEntrega(Cheque cheque) throws Exception {
        try {
            _chequeRepository.deleteByEntrega(cheque);
            return true;
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "create", e);
            throw e;
        }
    }

    public List<Cheque> getChequesByCliente(Cliente cliente) throws Exception {
        return _chequeRepository.recoveryByCliente(cliente);
    }

}
