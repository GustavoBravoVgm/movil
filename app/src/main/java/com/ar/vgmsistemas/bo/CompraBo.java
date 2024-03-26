package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.key.PkCompra;
import com.ar.vgmsistemas.repository.ICompraRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

public class CompraBo {

    private final ICompraRepository _compraRepository;

    public CompraBo(RepositoryFactory repoFactory) {
        _compraRepository = repoFactory.getCompraRepository();
    }

    public boolean existsEgreso(PkCompra pkCompra) throws Exception {
        return _compraRepository.existsEgreso(pkCompra);
    }

}
