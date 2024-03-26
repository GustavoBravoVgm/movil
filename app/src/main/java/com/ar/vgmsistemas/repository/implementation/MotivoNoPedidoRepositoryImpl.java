package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IMotivoNoPedidoDao;
import com.ar.vgmsistemas.database.dao.entity.MotivoNoPedidoBd;
import com.ar.vgmsistemas.entity.MotivoNoPedido;
import com.ar.vgmsistemas.repository.IMotivoNoPedidoRepository;

import java.util.ArrayList;
import java.util.List;

public class MotivoNoPedidoRepositoryImpl implements IMotivoNoPedidoRepository {

    private IMotivoNoPedidoDao _motivoNoPedidoDao;

    public MotivoNoPedidoRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._motivoNoPedidoDao = db.motivoNoPedidoDao();
        }
    }

    public Long create(MotivoNoPedido motivo) throws Exception {
        return null;
    }

    public void delete(MotivoNoPedido entity) throws Exception {

    }

    public void delete(Long id) throws Exception {

    }

    public List<MotivoNoPedido> recoveryAll() throws Exception {
        List<MotivoNoPedidoBd> listadoMotivosBd = _motivoNoPedidoDao.recoveryAll();
        List<MotivoNoPedido> motivos = new ArrayList<>();
        if (!listadoMotivosBd.isEmpty()) {
            for (MotivoNoPedidoBd item : listadoMotivosBd) {
                motivos.add(mappingToDto(item));
            }
        }
        return motivos;
    }

    public MotivoNoPedido recoveryByID(Long id) throws Exception {
        return null;
    }

    public void update(MotivoNoPedido motivo) throws Exception {
    }

    @Override
    public MotivoNoPedido mappingToDto(MotivoNoPedidoBd motivoNoPedidoBd) {
        MotivoNoPedido motivo = new MotivoNoPedido();
        if (motivoNoPedidoBd != null) {
            motivo.setId(motivoNoPedidoBd.getId());
            motivo.setDescripcion(motivoNoPedidoBd.getDescripcion());
        }
        return motivo;
    }
}
