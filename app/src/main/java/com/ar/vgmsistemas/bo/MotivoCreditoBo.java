package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.MotivoCredito;
import com.ar.vgmsistemas.repository.IMotivoCreditoRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;

import java.util.ArrayList;
import java.util.List;

public class MotivoCreditoBo {
    private static final int SIN_MOTIVO = -1;
    private final IMotivoCreditoRepository _motivoCreditoRepository;

    public MotivoCreditoBo(RepositoryFactory repoFactory) {
        this._motivoCreditoRepository = repoFactory.getMotivoCreditoRepository();
    }

    /***
     * devuelve el listado de motivos de credito, con el primer elemento
     * significando que no hay seleccion
     */
    public List<MotivoCredito> recoveryAll() throws Exception {
        List<MotivoCredito> motivoCreditos = new ArrayList<>();
        motivoCreditos.addAll(_motivoCreditoRepository.recoveryAll());
        if (motivoCreditos.size() == 0) {
            throw new Exception(ErrorManager.MotivosCreditoNoConfigurados);
        }
        return motivoCreditos;
    }

    public static List<String> parseToListArray(List<MotivoCredito> creditos) {
        List<String> motivosCreditos = new ArrayList<>();

        for (MotivoCredito credito : creditos) {
            String motivo = credito.getDescripcion();
            motivosCreditos.add(motivo);
        }
        return motivosCreditos;
    }

    public static MotivoCredito getEmptyMotivo() {
        MotivoCredito credito = new MotivoCredito();
        credito.setDescripcion("SIN MOTIVO");
        credito.setIdMotivoRechazoNC(SIN_MOTIVO);
        return credito;
    }
}
