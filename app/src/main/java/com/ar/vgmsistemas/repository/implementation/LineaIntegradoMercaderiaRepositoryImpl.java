package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.entity.LineaIntegradoMercaderiaBd;
import com.ar.vgmsistemas.entity.LineaIntegradoMercaderia;

public class LineaIntegradoMercaderiaRepositoryImpl {
    private final AppDataBase _db;

    public LineaIntegradoMercaderiaRepositoryImpl(AppDataBase db) {
        this._db = db;
    }

    public LineaIntegradoMercaderia mappingToDto(LineaIntegradoMercaderiaBd
                                                         lineaIntegradoMercaderiaBd) throws Exception {
        LineaIntegradoMercaderia listado = new LineaIntegradoMercaderia();
        if (lineaIntegradoMercaderiaBd != null) {

            //cargo articulos
            ArticuloRepositoryImpl articuloRepository = new ArticuloRepositoryImpl(this._db);
            listado.setArticulo(articuloRepository.mappingToDto(this._db.articuloDao().recoveryByID(
                    lineaIntegradoMercaderiaBd.getIdArticulo())));
            listado.setCantidad(lineaIntegradoMercaderiaBd.getCantidad());
            listado.setIdHoja(lineaIntegradoMercaderiaBd.getIdHoja());
            listado.setTiEstado(lineaIntegradoMercaderiaBd.getTiEstado());
            listado.setPrPagado(lineaIntegradoMercaderiaBd.getPrPagado());
            listado.setIdMovil(lineaIntegradoMercaderiaBd.getIdMovil());
        }
        return listado;
    }
}
