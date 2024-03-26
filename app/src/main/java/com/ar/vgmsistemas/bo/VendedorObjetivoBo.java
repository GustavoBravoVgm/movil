package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.VendedorObjetivo;
import com.ar.vgmsistemas.entity.VendedorObjetivoDetalle;
import com.ar.vgmsistemas.repository.IVendedorObjetivoRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.ArrayList;
import java.util.List;

public class VendedorObjetivoBo {
    private final IVendedorObjetivoRepository _vendedorObjetivoRepository;

    public VendedorObjetivoBo(RepositoryFactory repoFactory) {
        this._vendedorObjetivoRepository = repoFactory.getVendedorObjetivoRepository();
    }

    public List<VendedorObjetivo> recoveryAll() throws Exception {
        try {
            return _vendedorObjetivoRepository.recoveryAll();
        } catch (Exception exception) {
            return new ArrayList<VendedorObjetivo>();
        }
    }

    public static boolean isCompleted(VendedorObjetivo objetivo) {

        for (VendedorObjetivoDetalle detalle : objetivo.getObjetivosDetalle()) {

            if (!VendedorObjetivoDetalleBo.isCompleted(detalle, objetivo.getTiCategoria())) {
                return false;
            }
        }
        return true;
    }
}
