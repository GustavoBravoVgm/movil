package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IPromocionDetalleDao;
import com.ar.vgmsistemas.database.dao.entity.PromocionDetalleBd;
import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.entity.PromocionDetalle;
import com.ar.vgmsistemas.entity.key.PkPromocionDetalle;
import com.ar.vgmsistemas.repository.IPromocionRepository;

import java.util.ArrayList;
import java.util.List;

public class PromocionRepositoryImpl implements IPromocionRepository {
    private final AppDataBase _db;

    private IPromocionDetalleDao _promocionDetalleDao;

    public PromocionRepositoryImpl(AppDataBase db) {
        this._db = db;
        if (db != null) {
            this._promocionDetalleDao = db.promocionDetalleDao();
        }
    }

    public Integer create(PromocionDetalle entity) throws Exception {
        return null;
    }

    public PromocionDetalle recoveryByID(Integer id) throws Exception {
        return null;
    }

    public List<PromocionDetalle> recoveryAll() throws Exception {
        return null;
    }

    public void update(PromocionDetalle entity) throws Exception {
    }

    public void delete(PromocionDetalle entity) throws Exception {
    }

    public void delete(Integer id) throws Exception {
    }

    public List<PromocionDetalle> recoveryItemsByItem(Articulo articuloItem) throws Exception {
        List<PromocionDetalleBd> listadoPromocionDetalle = _promocionDetalleDao.recoveryItemsByItem(articuloItem.getId());
        List<PromocionDetalle> promociones = new ArrayList<>();
        if (!listadoPromocionDetalle.isEmpty()) {
            for (PromocionDetalleBd item : listadoPromocionDetalle) {
                promociones.add(mappingToDto(item));
            }
        }
        return promociones;
    }

    public List<PromocionDetalle> recoveryItems(Articulo articulo) throws Exception {
        List<PromocionDetalleBd> listadoPromocionDetalle = _promocionDetalleDao.recoveryItems(articulo.getId());
        List<PromocionDetalle> promociones = new ArrayList<>();
        if (!listadoPromocionDetalle.isEmpty()) {
            for (PromocionDetalleBd item : listadoPromocionDetalle) {
                promociones.add(mappingToDto(item));
            }
        }
        return promociones;
    }

    public PromocionDetalle mappingToDto(PromocionDetalleBd promocionDetalleBd) throws Exception {
        PromocionDetalle promocionDetalle = new PromocionDetalle();
        if (promocionDetalleBd != null) {
            PkPromocionDetalle id = new PkPromocionDetalle(promocionDetalleBd.getId().getIdPromo(),
                    promocionDetalleBd.getId().getIdArticulos(), promocionDetalleBd.getId().getIdSecuencia());

            promocionDetalle.setId(id);
            promocionDetalle.setCantidad(promocionDetalleBd.getCantidad());
            promocionDetalle.setPrecioCombo(promocionDetalleBd.getPrecioCombo());
            //cargo articulo
            ArticuloRepositoryImpl articuloRepository = new ArticuloRepositoryImpl(this._db);
            Articulo articulo = articuloRepository.mappingToDto(this._db.articuloDao().recoveryByID(promocionDetalleBd.getId().getIdArticulos()));

            promocionDetalle.setArticulo(articulo);
        }
        return promocionDetalle;
    }
}
