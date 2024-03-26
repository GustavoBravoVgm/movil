package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.LineaIntegradoMercaderia;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.List;


public class LineaIntegradoMercaderiaBo {
    private final VentaBo ventaBo;

    public LineaIntegradoMercaderiaBo(RepositoryFactory repoFactory) {
        this.ventaBo = new VentaBo(repoFactory);
    }

    public List<LineaIntegradoMercaderia> getLineas() throws Exception {
        return ventaBo.getLineas();
    }
}
