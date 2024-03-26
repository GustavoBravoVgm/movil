package com.ar.vgmsistemas.bo;

import android.content.Context;

import com.ar.vgmsistemas.entity.VisibleItemMenu;
import com.ar.vgmsistemas.repository.implementation.MenuProfileRepositoryImpl;
import com.ar.vgmsistemas.utils.CategoriaRecursoHumano;

import java.util.List;


public class MenuProfileBo {
    public List<VisibleItemMenu> getValidosMenuPrincipal(Context context) {
        //CategoriaRecursoHumano catRRHH = new CategoriaRecursoHumano();
        if (PreferenciaBo.getInstance().getPreferencia() == null)
            PreferenciaBo.getInstance().getPreferencia(context);
        boolean[] listado = MenuProfileRepositoryImpl.menuPrincipal.get(CategoriaRecursoHumano.getCategoriaRRHH(context));
        //boolean[] listado ;
        if (listado == null)
            listado = MenuProfileRepositoryImpl.menuPrincipal.get(CategoriaRecursoHumano.DEFAULT);

        return MenuProfileRepositoryImpl.getPosicionesItemsValidos(listado);
    }

    public List<VisibleItemMenu> getValidosInformesYEstadisticas(Context context) {
        boolean[] listado = MenuProfileRepositoryImpl.informesYEstadisticas.get(CategoriaRecursoHumano.getCategoriaRRHH(context));

        if (listado == null)
            listado = MenuProfileRepositoryImpl.informesYEstadisticas.get(CategoriaRecursoHumano.DEFAULT);

        return MenuProfileRepositoryImpl.getPosicionesItemsValidos(listado);
    }

    public List<Integer> getPosicionesValidasDialogoCliente(Context context) {
        boolean[] listado = MenuProfileRepositoryImpl.dialogoClientes.get(CategoriaRecursoHumano.getCategoriaRRHH(context));

        if (listado == null)
            listado = MenuProfileRepositoryImpl.dialogoClientes.get(CategoriaRecursoHumano.DEFAULT);

        return MenuProfileRepositoryImpl.getOpcionesValidos(listado);
    }

}
