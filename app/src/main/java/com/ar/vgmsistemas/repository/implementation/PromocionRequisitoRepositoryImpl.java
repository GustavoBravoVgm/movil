package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IPromocionRequisitoDao;
import com.ar.vgmsistemas.database.dao.entity.PromocionRequisitoBd;
import com.ar.vgmsistemas.entity.PromocionRequisito;
import com.ar.vgmsistemas.repository.IPromocionRequisitoRepository;

public class PromocionRequisitoRepositoryImpl implements IPromocionRequisitoRepository {
    private IPromocionRequisitoDao _promocionRequisitoDao;

    public PromocionRequisitoRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._promocionRequisitoDao = db.promocionRequisitoDao();
        }
    }

    public PromocionRequisito recoveryRequisitos(int idArticulo) throws Exception {
        return mappingToDto(_promocionRequisitoDao.recoveryRequisitos(idArticulo));
    }

    public PromocionRequisito mappingToDto(PromocionRequisitoBd promocionRequisitoBd) {
        PromocionRequisito promocionRequisito = new PromocionRequisito();
        if (promocionRequisitoBd != null) {

            promocionRequisito.setId(promocionRequisitoBd.getId());
            promocionRequisito.setCantArticulos(promocionRequisitoBd.getCantArticulos());
            promocionRequisito.setCantBultos(promocionRequisitoBd.getCantBultos());
            promocionRequisito.setCantUnidades(promocionRequisitoBd.getCantUnidades());
            promocionRequisito.setIdArticulos(promocionRequisitoBd.getIdArticulos());
            promocionRequisito.setIdArticulos1(promocionRequisitoBd.getIdArticulos1());
            promocionRequisito.setIdArticulos2(promocionRequisitoBd.getIdArticulos2());
            promocionRequisito.setCaArticulosIngreso(promocionRequisitoBd.getCaArticulosIngreso());
            promocionRequisito.setTipoRequisito(promocionRequisitoBd.getTipoRequisito());
        }
        return promocionRequisito;
    }
}
