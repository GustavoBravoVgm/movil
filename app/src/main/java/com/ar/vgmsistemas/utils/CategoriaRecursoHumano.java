package com.ar.vgmsistemas.utils;

import android.content.Context;

import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.bo.RecursoHumanoBo;
import com.ar.vgmsistemas.entity.RecursoHumano;
import com.ar.vgmsistemas.repository.RepositoryFactory;

public class CategoriaRecursoHumano {

    public static final int VENDEDOR = 1;
    public static final int VENDEDOR_HACIENDA = 100;
    public static final int REPARTIDOR = 101;
    public static final int COBRADOR = 102;
    public static final int DEFAULT = VENDEDOR;

    public static boolean isVendedorDeHacienda() {
        boolean result;
        result = TipoEmpresaCode.isHacienda() && PreferenciaBo.getInstance().getPreferencia().getIdCategoria() == VENDEDOR_HACIENDA;
        return result;
    }

    public static boolean isRepartidorDeHacienda() {
        boolean result;
        //result = TipoEmpresaCode.isHacienda() && PreferenciaBo.getInstance().getPreferencia().getIdCategoria() == REPARTIDOR;
        result = PreferenciaBo.getInstance().getPreferencia().getIdCategoria() == REPARTIDOR;
        return result;
    }

    public static boolean isRepartidor(RepositoryFactory repoFactory) throws Exception {
        boolean result;
        RecursoHumanoBo _recursoHumanoBo = new RecursoHumanoBo(repoFactory);

        //Me traigo el vendedor que se configura en el celular y obtengo su categoria
        RecursoHumano recursoHumanoVendedor;
        recursoHumanoVendedor = _recursoHumanoBo.recoveryByID(PreferenciaBo.getInstance().getPreferencia().getIdVendedor());
        int categoria = recursoHumanoVendedor.getCategoria();
        //Comparo la categoria del vendedor configurado en el movil con el que se configura en el Application Properties
        result = PreferenciaBo.getInstance().getPreferencia().getCategoriasEmpRepartidor() == categoria;
        return result;
    }

    public static int getIdCategoriaRecursoHumano() {
        return PreferenciaBo.getInstance().getPreferencia().getIdCategoria();

    }

    public static int getCategoriaRRHH(Context context) {
        return PreferenciaBo.getInstance().getPreferencia(context).getIdCategoria();
    }


}
