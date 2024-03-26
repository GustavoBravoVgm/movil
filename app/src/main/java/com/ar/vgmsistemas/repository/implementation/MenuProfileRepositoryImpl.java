package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.entity.VisibleItemMenu;
import com.ar.vgmsistemas.utils.CategoriaRecursoHumano;
import com.ar.vgmsistemas.utils.ItemMenuNames;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


@SuppressWarnings("unused")
public class MenuProfileRepositoryImpl {

    //MENU PRINCIPAL
    private static final boolean[] MENU_PRINCIPAL_VENDEDOR = {true, true, false, /*rendicion false,*/ true, true, true, true, true};
    private static final boolean[] MENU_PRINCIPAL_VENDEDOR_HACIENDA = {true, true, false, /*false,*/ true, true, true, true, true};
    private static final boolean[] MENU_PRINCIPAL_REPARTIDOR = {true, true, true, /*true,*/ true, true, true, true, true};
    private static final boolean[] MENU_PRINCIPAL_COBRADOR = {true, true, false, /*false,*/ false, true, true, true, true};

    //DIALOGO CLIENTES
    private static final boolean[] DIALOGO_CLIENTES_VENDEDOR = {true, true, true, true, true, true, true, true, true/*, true*/};
    private static final boolean[] DIALOGO_CLIENTES_VENDEDOR_HACIENDA = {true, true, true, true, false, true, true, true, false/*, false*/};
    private static final boolean[] DIALOGO_CLIENTES_REPARTIDOR = {true, true, true, true, false, true, true, true, false/*, false*/};
    private static final boolean[] DIALOGO_CLIENTES_COBRADOR = {false, true, true, false, false, true, true, true, false/*, false*/};

    //INFORMES Y ESTADISTICAS
    private static final boolean[] INFORMES_ESTADISTICAS_VENDEDOR = {true, true, true, true};
    private static final boolean[] INFORMES_ESTADISTICAS_VENDEDOR_HACIENDA = {true, true, false, false};
    private static final boolean[] INFORMES_ESTADISTICAS_REPARTIDOR = {true, true, false, false};
    private static final boolean[] INFORMES_ESTADISTICAS_COBRADOR = {true, false, false, true};


    //menu principal
    public static final Map<Integer, boolean[]> menuPrincipal;

    static {
        Map<Integer, boolean[]> aMap = new TreeMap<>();
        //CategoriaRecursoHumano catRRHH = new CategoriaRecursoHumano();
        aMap.put(CategoriaRecursoHumano.VENDEDOR, MENU_PRINCIPAL_VENDEDOR);
        aMap.put(CategoriaRecursoHumano.VENDEDOR_HACIENDA, MENU_PRINCIPAL_VENDEDOR_HACIENDA);
        aMap.put(PreferenciaBo.getInstance().getPreferencia().getCategoriasEmpRepartidor(), MENU_PRINCIPAL_REPARTIDOR);
        aMap.put(CategoriaRecursoHumano.COBRADOR, MENU_PRINCIPAL_COBRADOR);
        menuPrincipal = Collections.unmodifiableMap(aMap);
    }


    public static final Map<Integer, boolean[]> dialogoClientes;

    static {
        Map<Integer, boolean[]> aMap = new TreeMap<>();
        aMap.put(CategoriaRecursoHumano.VENDEDOR, DIALOGO_CLIENTES_VENDEDOR);
        aMap.put(CategoriaRecursoHumano.VENDEDOR_HACIENDA, DIALOGO_CLIENTES_VENDEDOR_HACIENDA);
        aMap.put(PreferenciaBo.getInstance().getPreferencia().getCategoriasEmpRepartidor(), DIALOGO_CLIENTES_REPARTIDOR);
        aMap.put(CategoriaRecursoHumano.COBRADOR, DIALOGO_CLIENTES_COBRADOR);

        dialogoClientes = Collections.unmodifiableMap(aMap);
    }

    //informes y estadisticas
    public static final Map<Integer, boolean[]> informesYEstadisticas;

    static {
        Map<Integer, boolean[]> aMap = new TreeMap<>();
        aMap.put(CategoriaRecursoHumano.VENDEDOR, INFORMES_ESTADISTICAS_VENDEDOR);
        aMap.put(CategoriaRecursoHumano.VENDEDOR_HACIENDA, INFORMES_ESTADISTICAS_VENDEDOR_HACIENDA);
        aMap.put(PreferenciaBo.getInstance().getPreferencia().getCategoriasEmpRepartidor(), INFORMES_ESTADISTICAS_REPARTIDOR);
        aMap.put(CategoriaRecursoHumano.COBRADOR, INFORMES_ESTADISTICAS_COBRADOR);

        informesYEstadisticas = Collections.unmodifiableMap(aMap);
    }


    public static List<VisibleItemMenu> getPosicionesItemsValidos(boolean[] itemsValidos) {
        List<VisibleItemMenu> listadoItemsValidos = new ArrayList<>();
        for (int i = 0; i < itemsValidos.length; i++) {
            if (itemsValidos[i]) {
                listadoItemsValidos.add(ItemMenuNames.listVisibleItemMenus.get(i));
            }
        }
        return listadoItemsValidos;
    }

    public static List<Integer> getOpcionesValidos(boolean[] itemsValidos) {
        List<Integer> listadoItemsValidos = new ArrayList<>();
        for (int i = 0; i < itemsValidos.length; i++) {
            if (itemsValidos[i]) {
                listadoItemsValidos.add(i);
            }
        }
        return listadoItemsValidos;
    }
}
